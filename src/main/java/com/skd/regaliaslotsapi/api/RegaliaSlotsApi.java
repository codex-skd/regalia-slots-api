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

package com.skd.regaliaslotsapi.api;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLLoader;
import com.skd.regaliaslotsapi.api.internal.RegaliaSlotsApiServices;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.capability.ICurio;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.api.type.capability.IRegaliaSlotsApiItemHandler;

public final class RegaliaSlotsApi {

  /**
   * @deprecated Replaced by {@link RegaliaSlotsApiResources#MOD_ID} instead, due to the future replacement
   *     of this class and its methods.
   */
  @Deprecated(forRemoval = true)
  public static final String MODID = "regalia_slots_api";

  /**
   * Registers a {@link ICurioItem} instance to an item.
   * <br>
   * This will override any existing {@link ICurioItem} interfaces implemented on an item, unless
   * those items are registered at a higher than normal priority in {@link net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent}
   *
   * @param item  The item to register the ICurio instance to
   * @param curio The ICurio instance that provides curio behavior for the item
   */
  public static void registerCurio(Item item, ICurioItem curio) {
    RegaliaSlotsApiServices.EXTENSIONS.registerCurioItem(curio, item);
  }

  /**
   * Gets a {@link Optional} of the curio capability attached to the {@link ItemStack}.
   *
   * @param stack The {@link ItemStack} to get the curio capability from
   * @return {@link Optional} of the curio capability
   */
  public static Optional<ICurio> getCurio(ItemStack stack) {
    return Optional.ofNullable(getCurioOrNull(stack));
  }

  /**
   * Gets a {@link Optional} of the curio capability attached to the {@link ItemStack}.
   *
   * @param stack The {@link ItemStack} to get the curio capability from
   * @return {@link Optional} of the curio capability
   */
  public static ICurio getCurioOrNull(ItemStack stack) {
    return stack.getCapability(RegaliaSlotsApiCapability.ITEM);
  }

  /**
   * Gets a {@link Optional} of the curio inventory capability attached to the entity.
   *
   * @param livingEntity The {@link LivingEntity} to get the curio inventory capability from
   * @return {@link Optional} of the curio inventory capability
   */
  public static Optional<IRegaliaSlotsApiItemHandler> getRegaliaSlotsApiInventory(LivingEntity livingEntity) {

    if (livingEntity != null) {
      return Optional.ofNullable(livingEntity.getCapability(RegaliaSlotsApiCapability.INVENTORY));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Gets a {@link Optional} of the curio inventory capability attached to the entity.
   *
   * @param livingEntity The {@link LivingEntity} to get the curio inventory capability from
   * @return {@link Optional} of the curio inventory capability
   */
  public static IRegaliaSlotsApiItemHandler getRegaliaSlotsApiInventoryOrNull(LivingEntity livingEntity) {
    return livingEntity.getCapability(RegaliaSlotsApiCapability.INVENTORY);
  }

  /**
   * Checks if the ItemStack is valid for a particular stack and slot context.
   *
   * @param slotContext Context about the slot that the ItemStack is being checked for
   * @param stack       The ItemStack in question
   * @return True if the ItemStack is valid for the slot, false otherwise
   */
  public static boolean isStackValid(SlotContext slotContext, ItemStack stack) {
    Map<String, ISlotType> results = new TreeMap<>();
    LivingEntity livingEntity = slotContext.entity();
    String id = slotContext.identifier();

    if (livingEntity != null) {
      results.putAll(RegaliaSlotsApiSlotTypes.getItemSlotTypes(stack, livingEntity));
    } else {
      results.putAll(RegaliaSlotsApiSlotTypes.getItemSlotTypes(
          stack, FMLLoader.getCurrent().getDist().isClient()));
    }
    return results.containsKey(id);
  }

  /**
   * Gets a {@link Identifier} based on the provided {@link SlotContext}.
   *
   * @param slotContext The SlotContext to base the {@link Identifier} on
   * @return The Identifier based on the SlotContext
   */
  public static Identifier getSlotId(SlotContext slotContext) {
    return RegaliaSlotsApiResources.resource(slotContext.identifier() + slotContext.index());
  }

  /**
   * Performs breaking behavior used in the runnable through {@link ItemStack#hurtAndBreak(int, ServerLevel, LivingEntity, Consumer<Item>)}}
   * <br>
   * This will be necessary in order to trigger break animations in curio slots
   * <br>
   * Example: { stack.hurtAndBreak(amount, level entity, item -> RegaliaSlotsApi.broadcastCurioBreakEvent(slotContext)); }
   *
   * @param slotContext Context about the slot that the curio is in
   */
  public static void broadcastCurioBreakEvent(SlotContext slotContext) {
    RegaliaSlotsApiServices.NETWORK.breakCurioInSlot(slotContext);
  }

  /**
   * Gets the registered slot type for the identifier, if it exists, on the given level.
   *
   * @param id    The slot type identifier
   * @param level The level for the slot type
   * @return The registered slot type or empty if it doesn't exist
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getSlotType(String, boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Optional<ISlotType> getSlot(String id, Level level) {
    return RegaliaSlotsApi.getSlot(id, level.isClientSide());
  }

  /**
   * Gets the registered slot type for the identifier, if it exists, on the given side.
   *
   * @param id       The slot type identifier
   * @param isClient True for client-side slots, false for server-side slots
   * @return The registered slot type or empty if it doesn't exist
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getSlotType(String, boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Optional<ISlotType> getSlot(String id, boolean isClient) {
    return Optional.ofNullable(RegaliaSlotsApi.getSlots(isClient).get(id));
  }

  /**
   * Gets all the registered slot types on the given level.
   *
   * @param level The level for the slot type
   * @return The registered slot types
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getSlotTypes(boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getSlots(Level level) {
    return RegaliaSlotsApi.getSlots(level.isClientSide());
  }

  /**
   * Gets all the registered slot types on the given side.
   *
   * @param isClient True for client-side slots, false for server-side slots
   * @return The registered slot types
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getSlotTypes(boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getSlots(boolean isClient) {
    return RegaliaSlotsApiSlotTypes.getSlotTypes(isClient);
  }

  /**
   * Gets all the registered slot types provided to player entities on the given level.
   *
   * @param level The level for the slot types
   * @return The slot types provided to player entities
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getDefaultPlayerSlotTypes(boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getPlayerSlots(Level level) {
    return RegaliaSlotsApi.getPlayerSlots(level.isClientSide());
  }

  /**
   * Gets all the registered slot types provided to player entities on the given side.
   *
   * @param isClient True for client-side slots, false for server-side slots
   * @return The slot types provided to player entities
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getDefaultPlayerSlotTypes(boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getPlayerSlots(boolean isClient) {
    return RegaliaSlotsApi.getEntitySlots(EntityTypes.PLAYER, isClient);
  }

  /**
   * Gets all the registered slot types provided the player entity.
   *
   * @param player The {@link Player} for the slot types
   * @return The slot types provided to the player entity
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getDefaultEntitySlotTypes(LivingEntity)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getPlayerSlots(Player player) {
    return RegaliaSlotsApi.getEntitySlots(player);
  }

  /**
   * Gets all the registered slot types provided to an entity.
   *
   * @param livingEntity The {@link LivingEntity} for the slot types
   * @return The slot types provided to the entity
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getDefaultEntitySlotTypes(LivingEntity)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getEntitySlots(LivingEntity livingEntity) {
    return livingEntity != null
           ? RegaliaSlotsApi.getEntitySlots(livingEntity.getType(), livingEntity.level()) : Map.of();
  }

  /**
   * Gets all the registered slot types provided to an entity type on the given level.
   *
   * @param type The entity type for the slot types
   * @return The slot types provided to the entity type
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getDefaultEntitySlotTypes(EntityType, boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getEntitySlots(EntityType<?> type, Level level) {
    return RegaliaSlotsApi.getEntitySlots(type, level.isClientSide());
  }

  /**
   * Gets all the registered slot types provided to an entity.
   *
   * @param type     The entity type for the slot types
   * @param isClient True for client-side slots, false for server-side slots
   * @return The slot types provided to the entity
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getDefaultEntitySlotTypes(EntityType, boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getEntitySlots(EntityType<?> type, boolean isClient) {
    return RegaliaSlotsApiSlotTypes.getDefaultEntitySlotTypes(type, isClient);
  }

  /**
   * Gets all the registered slot types for the provided ItemStack and level.
   *
   * @param stack The ItemStack for the slot types
   * @param level The level for the ItemStack
   * @return The slot types for the provided ItemStack
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getItemSlotTypes(ItemStack, boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getItemStackSlots(ItemStack stack, Level level) {
    return RegaliaSlotsApi.getItemStackSlots(stack, level.isClientSide());
  }

  /**
   * Gets all the registered slot types for the provided ItemStack and level.
   *
   * @param stack    The ItemStack for the slot types
   * @param isClient True for client-side slots, false for server-side slots
   * @return The slot types for the provided ItemStack
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getItemSlotTypes(ItemStack, boolean)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getItemStackSlots(ItemStack stack, boolean isClient) {
    return RegaliaSlotsApiSlotTypes.getItemSlotTypes(stack, isClient);
  }

  /**
   * Gets all the registered slot types for the provided ItemStack and entity.
   *
   * @param stack        The ItemStack for the slot types
   * @param livingEntity The entity with the slot types
   * @return The slot types for the provided ItemStack and entity
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getItemSlotTypes(ItemStack, LivingEntity)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<String, ISlotType> getItemStackSlots(ItemStack stack,
                                                         LivingEntity livingEntity) {
    return RegaliaSlotsApiSlotTypes.getItemSlotTypes(stack, livingEntity);
  }

  /**
   * Retrieves a map of attribute modifiers for the ItemStack.
   * <br>
   * Note that only the identifier is guaranteed to be present in the slot context. For instances
   * where the ItemStack may not be in a curio slot, such as when retrieving item tooltips, the
   * index is -1 and the wearer may be null.
   *
   * @param slotContext Context about the slot that the ItemStack is equipped in or may potentially
   *                    be equipped in
   * @param id          Slot-unique id
   * @param stack       The ItemStack in question
   * @return A map of attribute modifiers
   * @deprecated Use {@link ICurioItem#getAttributeModifiers(ItemStack)} to retrieve information
   *     about the attribute modifiers directly on a stack.
   */
  @Deprecated(forRemoval = true)
  public static Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      SlotContext slotContext, Identifier id, ItemStack stack) {
    Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
    ICurioItem.forEachModifier(stack, slotContext, modifiers::put);
    return modifiers;
  }

  /**
   * Adds a slot modifier to a specified attribute map.
   *
   * @param map        A {@link Multimap} of attributes to attribute modifiers
   * @param identifier The identifier of the slot to add the modifier onto
   * @param id         id associated with the modifier
   * @param amount     The amount of the modifier
   * @param operation  The operation of the modifier
   * @deprecated Use {@link CurioAttributeModifiers} to build the modifier on a stack directly.
   */
  @Deprecated(forRemoval = true)
  public static void addSlotModifier(Multimap<Holder<Attribute>, AttributeModifier> map,
                                     String identifier, Identifier id, double amount,
                                     AttributeModifier.Operation operation) {
    map.put(SlotAttribute.getOrCreate(identifier),
            new AttributeModifier(id, amount, operation));
  }

  /**
   * Adds a slot modifier to an ItemStack's tag data.
   *
   * @param stack      The ItemStack to add the modifier to
   * @param identifier The identifier of the slot to add the modifier onto
   * @param id         id associated with the modifier
   * @param amount     The amount of the modifier
   * @param operation  The operation of the modifier
   * @param slot       The slot that the ItemStack provides the modifier from
   * @deprecated Use {@link CurioAttributeModifiers} to build the modifier on a stack directly.
   */
  @Deprecated(forRemoval = true)
  public static void addSlotModifier(ItemStack stack, String identifier, Identifier id,
                                     double amount, AttributeModifier.Operation operation,
                                     String slot) {
    addModifier(stack, SlotAttribute.getOrCreate(identifier), id, amount, operation, slot);
  }

  /**
   * Creates an {@link ItemAttributeModifiers} with an added slot modifier.
   *
   * @param itemAttributeModifiers A {@link ItemAttributeModifiers} instance
   * @param identifier             The identifier of the slot to add the modifier onto
   * @param id                     id associated with the modifier
   * @param amount                 The amount of the modifier
   * @param operation              The operation of the modifier
   * @param slotGroup              The slot to provide the modifier from
   * @deprecated A replacement method is currently unavailable due to implementation issues with
   *     attempting to combine {@link SlotAttribute} with vanilla attributes in an
   *     {@link ItemAttributeModifiers} instance.
   */
  @Deprecated(forRemoval = true)
  public static ItemAttributeModifiers withSlotModifier(
      ItemAttributeModifiers itemAttributeModifiers, String identifier, Identifier id,
      double amount, AttributeModifier.Operation operation, EquipmentSlotGroup slotGroup) {
    return ItemAttributeModifiers.EMPTY;
  }

  /**
   * Adds an attribute modifier to an ItemStack's tag data.
   *
   * @param stack     The ItemStack to add the modifier to
   * @param attribute The attribute to add the modifier onto
   * @param id        id associated with the modifier
   * @param amount    The amount of the modifier
   * @param operation The operation of the modifier
   * @param slot      The slot that the ItemStack provides the modifier from
   * @deprecated Use {@link CurioAttributeModifiers} to build the modifier on a stack directly.
   */
  @Deprecated(forRemoval = true)
  public static void addModifier(ItemStack stack, Holder<Attribute> attribute, Identifier id,
                                 double amount, AttributeModifier.Operation operation,
                                 String slot) {
    RegaliaSlotsApiDataComponents
        .updateCurioAttributeModifiers(stack, curioAttributeModifiers -> curioAttributeModifiers
            .withModifierAdded(attribute, new AttributeModifier(id, amount, operation), slot));
  }

  /**
   * Registers a new predicate keyed to a {@link Identifier} for deciding which slots are
   * assigned to a given {@link ItemStack}.
   *
   * @param resourceLocation The unique {@link Identifier} of the validator
   * @param predicate        The predicate to register for a given stack and {@link SlotResult}
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#registerPredicate(Identifier, BiPredicate)}
   *     instead.
   */
  @Deprecated(forRemoval = true)
  public static void registerCurioPredicate(Identifier resourceLocation,
                                            Predicate<SlotResult> predicate) {
    RegaliaSlotsApiSlotTypes.registerPredicate(resourceLocation, (slotContext, stack) -> predicate.test(
        new SlotResult(slotContext, stack)));
  }

  /**
   * Gets an existing predicate, or empty if none found, keyed to a {@link Identifier} for
   * deciding which slots are assigned to a given {@link ItemStack}.
   *
   * @param resourceLocation The unique {@link Identifier} of the validator
   * @return An Optional of the predicate found for the Identifier, or empty otherwise
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getPredicate(Identifier)} instead.
   */
  @Deprecated(forRemoval = true)
  public static Optional<Predicate<SlotResult>> getCurioPredicate(
      Identifier resourceLocation) {
    BiPredicate<SlotContext, ItemStack> predicate = RegaliaSlotsApiSlotTypes.getPredicate(resourceLocation);

    if (predicate != null) {
      Predicate<SlotResult> result =
          slotResult -> predicate.test(slotResult.slotContext(), slotResult.stack());
      return Optional.of(result);
    }
    return Optional.empty();
  }

  /**
   * Gets all registered predicates deciding which slots are assigned to a given {@link ItemStack}.
   *
   * @return A map of the registered predicates keyed by {@link Identifier}
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#getPredicates()} instead.
   */
  @Deprecated(forRemoval = true)
  public static Map<Identifier, Predicate<SlotResult>> getCurioPredicates() {
    Map<Identifier, Predicate<SlotResult>> result = new LinkedHashMap<>();
    RegaliaSlotsApiSlotTypes.getPredicates().forEach((resourceLocation, predicate) -> {
      result.put(resourceLocation,
                 (slotResult) -> predicate.test(slotResult.slotContext(), slotResult.stack()));
    });
    return result;
  }

  /**
   * Evaluates a set of predicates to determine if a given {@link SlotResult} is a valid assignment.
   *
   * @param predicates A set of Identifiers representing the predicates to iterate
   * @param slotResult The SlotResult containing the {@link SlotContext} and {@link ItemStack}
   * @return True if any of the predicates pass, false otherwise
   * @deprecated Use {@link RegaliaSlotsApiSlotTypes#testPredicates(SlotContext, ItemStack, Set)} instead.
   */
  @Deprecated(forRemoval = true)
  public static boolean testCurioPredicates(Set<Identifier> predicates,
                                            SlotResult slotResult) {
    return RegaliaSlotsApiSlotTypes.testPredicates(slotResult.slotContext(), slotResult.stack(), predicates);
  }
}
