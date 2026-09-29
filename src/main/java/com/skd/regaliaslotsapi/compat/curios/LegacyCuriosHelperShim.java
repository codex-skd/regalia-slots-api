/*
 * Copyright (c) 2018-2024 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 *
 * COMPAT NOTE: Binary-compatibility shim for third-party mods that still call
 * the deprecated {@code CuriosApi.getCuriosHelper()} accessor (e.g.
 * Supplementaries, L_Ender's Cataclysm). Delegates to the renamed
 * implementation via CuriosImplMixinHooks so those mods keep working against
 * Regalia Slots API.
 */

package com.skd.regaliaslotsapi.compat.curios;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.logging.log4j.util.TriConsumer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;
import top.theillusivec4.curios.mixin.CuriosImplMixinHooks;

@SuppressWarnings({"deprecation", "removal"})
public class LegacyCuriosHelperShim implements ICuriosHelper {

  @Override
  public Optional<ICurio> getCurio(ItemStack stack) {
    return CuriosImplMixinHooks.getCurio(stack);
  }

  @Override
  public Optional<ICuriosItemHandler> getCuriosHandler(@Nonnull LivingEntity livingEntity) {
    return CuriosImplMixinHooks.getCuriosInventory(livingEntity);
  }

  @Override
  public Set<String> getCurioTags(Item item) {
    return CuriosImplMixinHooks.getItemStackSlots(item.getDefaultInstance(),
        FMLLoader.getDist() == Dist.CLIENT).keySet();
  }

  @Override
  public Optional<IItemHandlerModifiable> getEquippedCurios(LivingEntity livingEntity) {
    return getCuriosHandler(livingEntity).map(ICuriosItemHandler::getEquippedCurios);
  }

  @Override
  public void setEquippedCurio(@Nonnull LivingEntity livingEntity, String identifier, int index,
                               ItemStack stack) {
    getCuriosHandler(livingEntity)
        .ifPresent(inv -> inv.setEquippedCurio(identifier, index, stack));
  }

  @Override
  public Optional<SlotResult> findFirstCurio(@Nonnull LivingEntity livingEntity, Item item) {
    return getCuriosHandler(livingEntity).flatMap(inv -> inv.findFirstCurio(item));
  }

  @Override
  public Optional<SlotResult> findFirstCurio(@Nonnull LivingEntity livingEntity,
                                             Predicate<ItemStack> filter) {
    return getCuriosHandler(livingEntity).flatMap(inv -> inv.findFirstCurio(filter));
  }

  @Override
  public List<SlotResult> findCurios(@Nonnull LivingEntity livingEntity, Item item) {
    return getCuriosHandler(livingEntity).map(inv -> inv.findCurios(item))
        .orElse(Collections.emptyList());
  }

  @Override
  public List<SlotResult> findCurios(@Nonnull LivingEntity livingEntity,
                                     Predicate<ItemStack> filter) {
    return getCuriosHandler(livingEntity).map(inv -> inv.findCurios(filter))
        .orElse(Collections.emptyList());
  }

  @Override
  public List<SlotResult> findCurios(@Nonnull LivingEntity livingEntity, String... identifiers) {
    return getCuriosHandler(livingEntity).map(inv -> inv.findCurios(identifiers))
        .orElse(Collections.emptyList());
  }

  @Override
  public Optional<SlotResult> findCurio(@Nonnull LivingEntity livingEntity, String identifier,
                                        int index) {
    return getCuriosHandler(livingEntity).flatMap(inv -> inv.findCurio(identifier, index));
  }

  @Nonnull
  @Override
  public Optional<ImmutableTriple<String, Integer, ItemStack>> findEquippedCurio(Item item,
                                                                                 @Nonnull
                                                                                 LivingEntity livingEntity) {
    return findEquippedCurio(stack -> stack.getItem() == item, livingEntity);
  }

  @Nonnull
  @Override
  public Optional<ImmutableTriple<String, Integer, ItemStack>> findEquippedCurio(
      Predicate<ItemStack> filter, @Nonnull LivingEntity livingEntity) {

    ImmutableTriple<String, Integer, ItemStack> result = getCuriosHandler(livingEntity)
        .map(handler -> {
          Map<String, ICurioStacksHandler> curios = handler.getCurios();

          for (String id : curios.keySet()) {
            ICurioStacksHandler stacksHandler = curios.get(id);
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            NonNullList<Boolean> activeStates = stacksHandler.getActiveStates();

            for (int i = 0; i < stackHandler.getSlots(); i++) {

              if (activeStates.size() > i && !activeStates.get(i)) {
                continue;
              }
              ItemStack stack = stackHandler.getStackInSlot(i);

              if (!stack.isEmpty() && filter.test(stack)) {
                return new ImmutableTriple<>(id, i, stack);
              }
            }
          }
          return new ImmutableTriple<>("", 0, ItemStack.EMPTY);
        }).orElse(new ImmutableTriple<>("", 0, ItemStack.EMPTY));

    return result.getLeft().isEmpty() ? Optional.empty() : Optional.of(result);
  }

  @Override
  public boolean isStackValid(SlotContext slotContext, ItemStack stack) {
    return CuriosImplMixinHooks.isStackValid(slotContext, stack);
  }

  @Override
  public void onBrokenCurio(String id, int index, LivingEntity damager) {
    CuriosImplMixinHooks.broadcastCurioBreakEvent(new SlotContext(id, damager, index, false, true));
  }

  @Override
  public void onBrokenCurio(SlotContext slotContext) {
    CuriosImplMixinHooks.broadcastCurioBreakEvent(slotContext);
  }

  @Override
  public void setBrokenCurioConsumer(Consumer<SlotContext> consumer) {
    // NO-OP: moved to internal code in Curios 1.20.1+
  }

  @Override
  public void setBrokenCurioConsumer(TriConsumer<String, Integer, LivingEntity> consumer) {
    // NO-OP: moved to internal code in Curios 1.20.1+
  }
}
