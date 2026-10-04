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
 * COMPAT NOTE: This class is a binary-compatibility shim for third-party mods
 * that mix into Curios' internal mixin hooks. It delegates to the renamed
 * RegaliaSlotsApiImplMixinHooks implementation.
 */

package top.theillusivec4.curios.mixin;

import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import com.skd.regaliaslotsapi.mixin.RegaliaSlotsApiImplMixinHooks;

public class CuriosImplMixinHooks {

  private static final Map<Item, ICurioItem> REGISTRY = new java.util.concurrent.ConcurrentHashMap<>();

  public static void registerCurio(Item item, ICurioItem icurio) {
    REGISTRY.put(item, icurio);
    RegaliaSlotsApiImplMixinHooks.registerCurio(item, (com.skd.regaliaslotsapi.api.type.capability.ICurioItem) icurio);
  }

  public static Optional<ICurioItem> getCurioFromRegistry(Item item) {
    return Optional.ofNullable(REGISTRY.get(item));
  }

  public static Map<String, ISlotType> getSlots(boolean isClient) {
    return RegaliaSlotsApiImplMixinHooks.getSlotsForCurios(isClient);
  }

  public static Map<String, ISlotType> getEntitySlots(EntityType<?> type, boolean isClient) {
    return RegaliaSlotsApiImplMixinHooks.getEntitySlotsForCurios(type, isClient);
  }

  public static Map<String, ISlotType> getItemStackSlots(ItemStack stack, boolean isClient) {
    return RegaliaSlotsApiImplMixinHooks.getItemStackSlotsForCurios(stack, isClient);
  }

  public static Map<String, ISlotType> getItemStackSlots(ItemStack stack, LivingEntity livingEntity) {
    return RegaliaSlotsApiImplMixinHooks.getItemStackSlotsForCurios(stack, livingEntity);
  }

  public static Optional<ICurio> getCurio(ItemStack stack) {
    return RegaliaSlotsApiImplMixinHooks.getCurioForCurios(stack);
  }

  public static Optional<ICuriosItemHandler> getCuriosInventory(LivingEntity livingEntity) {
    return RegaliaSlotsApiImplMixinHooks.getCuriosInventoryForCurios(livingEntity);
  }

  public static boolean isStackValid(SlotContext slotContext, ItemStack stack) {
    return RegaliaSlotsApiImplMixinHooks.isStackValidForCurios(slotContext, stack);
  }

  public static Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
    return RegaliaSlotsApiImplMixinHooks.getAttributeModifiersForCurios(slotContext, id, stack);
  }

  public static void addSlotModifier(Multimap<Holder<Attribute>, AttributeModifier> map, String identifier, ResourceLocation id, double amount, AttributeModifier.Operation operation) {
    RegaliaSlotsApiImplMixinHooks.addSlotModifier(map, identifier, id, amount, operation);
  }

  public static void addSlotModifier(ItemStack stack, String identifier, ResourceLocation id, double amount, AttributeModifier.Operation operation, String slot) {
    RegaliaSlotsApiImplMixinHooks.addSlotModifier(stack, identifier, id, amount, operation, slot);
  }

  public static void addModifier(ItemStack stack, Holder<Attribute> attribute, ResourceLocation id, double amount, AttributeModifier.Operation operation, String slot) {
    RegaliaSlotsApiImplMixinHooks.addModifier(stack, attribute, id, amount, operation, slot);
  }

  public static void registerCurioPredicate(ResourceLocation resourceLocation, Predicate<SlotResult> validator) {
    RegaliaSlotsApiImplMixinHooks.registerCurioPredicateForCurios(resourceLocation, validator);
  }

  public static Optional<Predicate<SlotResult>> getCurioPredicate(ResourceLocation resourceLocation) {
    return RegaliaSlotsApiImplMixinHooks.getCurioPredicateForCurios(resourceLocation);
  }

  public static Map<ResourceLocation, Predicate<SlotResult>> getCurioPredicates() {
    return RegaliaSlotsApiImplMixinHooks.getCurioPredicatesForCurios();
  }

  public static boolean testCurioPredicates(Set<ResourceLocation> predicates, SlotResult slotResult) {
    return RegaliaSlotsApiImplMixinHooks.testCurioPredicatesForCurios(predicates, slotResult);
  }

  public static ResourceLocation getSlotId(SlotContext slotContext) {
    return RegaliaSlotsApiImplMixinHooks.getSlotIdForCurios(slotContext);
  }

  public static void broadcastCurioBreakEvent(SlotContext slotContext) {
    RegaliaSlotsApiImplMixinHooks.broadcastCurioBreakEventForCurios(slotContext);
  }
}