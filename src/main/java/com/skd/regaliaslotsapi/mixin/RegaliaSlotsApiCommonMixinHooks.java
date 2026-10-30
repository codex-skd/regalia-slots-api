/*
 * Copyright (c) 2018-2024 C4
 *
 * This file is part of RegaliaSlotsApi, a mod made for Minecraft.
 *
 * RegaliaSlotsApi is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RegaliaSlotsApi is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with RegaliaSlotsApi.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.skd.regaliaslotsapi.mixin;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.ArrayUtils;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;

public class RegaliaSlotsApiCommonMixinHooks {

  public static Pair<String, TypeTemplate>[] attachDataFixer(Schema schema,
                                                             Pair<String, TypeTemplate>[] original) {
    return ArrayUtils.add(original,
        Pair.of("neoforge:attachments",
            DSL.optionalFields("curios:inventory",
                DSL.optionalFields("RegaliaSlotsApi",
                    DSL.list(
                        DSL.optionalFields("StacksHandler",
                            DSL.optionalFields("Stacks",
                                DSL.optionalFields("Items",
                                    DSL.list(References.ITEM_STACK.in(schema))
                                )
                            )
                        )
                    )
                )
            )
        ));
  }

  public static boolean canNeutralizePiglins(LivingEntity livingEntity) {
    return RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity).map(handler -> {

      for (Map.Entry<String, ICurioStacksHandler> entry : handler.getRegaliaSlotsApi().entrySet()) {
        IDynamicStackHandler stacks = entry.getValue().getStacks();

        for (int i = 0; i < stacks.getSlots(); i++) {
          final int index = i;
          NonNullList<Boolean> renderStates = entry.getValue().getRenders();
          boolean canNeutralize =
              RegaliaSlotsApi.getCurio(stacks.getStackInSlot(i)).map(curio -> curio
                      .makesPiglinsNeutral(new SlotContext(entry.getKey(), livingEntity, index,
                          false,
                          renderStates.size() > index && renderStates.get(index))))
                  .orElse(false);

          if (canNeutralize) {
            return true;
          }
        }
      }
      return false;
    }).orElse(false);
  }

  public static boolean canWalkOnPowderSnow(LivingEntity livingEntity) {
    return RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity).map(handler -> {

      for (Map.Entry<String, ICurioStacksHandler> entry : handler.getRegaliaSlotsApi().entrySet()) {
        IDynamicStackHandler stacks = entry.getValue().getStacks();

        for (int i = 0; i < stacks.getSlots(); i++) {
          final int index = i;
          NonNullList<Boolean> renderStates = entry.getValue().getRenders();
          boolean canWalk =
              RegaliaSlotsApi.getCurio(stacks.getStackInSlot(i)).map(curio -> curio
                      .canWalkOnPowderedSnow(new SlotContext(entry.getKey(), livingEntity, index,
                          false,
                          renderStates.size() > index && renderStates.get(index))))
                  .orElse(false);

          if (canWalk) {
            return true;
          }
        }
      }
      return false;
    }).orElse(false);
  }

  public static int getFortuneLevel(LootContext lootContext) {
    Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);

    if (entity instanceof LivingEntity livingEntity) {
      return RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity)
          .map(handler -> handler.getFortuneLevel(lootContext)).orElse(0);
    }
    return 0;
  }

  public static int getLootingLevel(LootContext lootContext) {
    Entity entity = lootContext.getOptionalParameter(LootContextParams.ATTACKING_ENTITY);

    if (entity instanceof LivingEntity livingEntity) {
      return RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity)
          .map(handler -> handler.getLootingLevel(lootContext)).orElse(0);
    }
    return 0;
  }

  public static boolean isFreezeImmune(LivingEntity livingEntity) {
    return RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity).map(curios -> {
      IItemHandlerModifiable handler = curios.getEquippedRegaliaSlotsApi();

      for (int i = 0; i < handler.getSlots(); i++) {
        ItemStack stack = handler.getStackInSlot(i);

        if (stack.is(ItemTags.FREEZE_IMMUNE_WEARABLES)) {
          return true;
        }
      }
      return false;
    }).orElse(false);
  }

  public static void mergeRegaliaSlotsApiInventory(ProblemReporter reporter, CompoundTag output,
                                          Entity entity) {

    if (entity instanceof LivingEntity livingEntity) {
      ListTag list = output.getList("Inventory").orElse(null);

      if (list == null) {
        list = new ListTag();
        output.put("Inventory", list);
      }
      DynamicOps<Tag> ops = entity.registryAccess().createSerializationContext(NbtOps.INSTANCE);
      ListTag workingList = list;
      RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity).ifPresent(inv -> {
        IItemHandler handler = inv.getEquippedRegaliaSlotsApi();

        for (int i = 0; i < handler.getSlots(); i++) {
          ItemStack stack = handler.getStackInSlot(i);

          if (!stack.isEmpty()) {
            addListElement(workingList, ops, reporter, stack);
          }
        }
      });
    }
  }

  private static void addListElement(ListTag listTag, DynamicOps<Tag> ops, ProblemReporter reporter,
                                     ItemStack stack) {
    DataResult<Tag> encoded =
        ItemStackWithSlot.CODEC.encodeStart(ops, new ItemStackWithSlot(255, stack));
    switch (encoded) {
      case DataResult.Success<Tag> success:
        listTag.add(success.value());
        break;
      case DataResult.Error<Tag> error:
        reporter.report(
            new TagValueOutput.EncodeToListFailedProblem("RegaliaSlotsApi:Inventory", stack, error));
        Optional<Tag> partial = error.partialValue();
        Objects.requireNonNull(listTag);
        partial.ifPresent(listTag::add);
        break;
    }
  }

  public static boolean containsStack(Player player, ItemStack stack) {
    return RegaliaSlotsApi.getRegaliaSlotsApiInventory(player).flatMap(inv -> inv.findFirstCurio(
            stack2 -> !stack2.isEmpty() && ItemStack.isSameItemSameComponents(stack, stack2)))
        .isPresent();
  }

  public static boolean containsTag(Player player, TagKey<Item> tagKey) {
    return RegaliaSlotsApi.getRegaliaSlotsApiInventory(player).flatMap(
            inv -> inv.findFirstCurio(stack2 -> !stack2.isEmpty() && stack2.is(tagKey)))
        .isPresent();
  }

  public static boolean contains(Player player, Predicate<ItemStack> predicate) {
    return RegaliaSlotsApi.getRegaliaSlotsApiInventory(player).flatMap(inv -> inv.findFirstCurio(predicate))
        .isPresent();
  }
}
