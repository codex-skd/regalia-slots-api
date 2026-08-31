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
 */

package com.skd.regaliaslotsapi.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import com.skd.regaliaslotsapi.RegaliaSlotsApiConstants;
import com.skd.regaliaslotsapi.api.CurioAttributeModifiers;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.SlotAttribute;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.SlotResult;
import com.skd.regaliaslotsapi.api.event.CurioAttributeModifierEvent;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.capability.ICurio;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.api.type.capability.ICuriosItemHandler;
import com.skd.regaliaslotsapi.common.RegaliaSlotsApiRegistry;
import com.skd.regaliaslotsapi.common.data.RegaliaSlotsApiEntityManager;
import com.skd.regaliaslotsapi.common.data.RegaliaSlotsApiSlotManager;
import com.skd.regaliaslotsapi.common.network.server.SPacketBreak;
import com.skd.regaliaslotsapi.compat.curios.ShimSlotType;

public class RegaliaSlotsApiImplMixinHooks {

  private static final Map<Item, ICurioItem> REGISTRY = new ConcurrentHashMap<>();

  public static void registerCurio(Item item, ICurioItem icurio) {
    REGISTRY.put(item, icurio);
  }

  public static Optional<ICurioItem> getCurioFromRegistry(Item item) {
    return Optional.ofNullable(REGISTRY.get(item));
  }

  public static Map<String, ISlotType> getSlots(boolean isClient) {
    RegaliaSlotsApiSlotManager slotManager = isClient ? RegaliaSlotsApiSlotManager.CLIENT : RegaliaSlotsApiSlotManager.SERVER;
    return slotManager.getSlots();
  }

  public static Map<String, ISlotType> getEntitySlots(EntityType<?> type, boolean isClient) {
    RegaliaSlotsApiEntityManager entityManager =
        isClient ? RegaliaSlotsApiEntityManager.CLIENT : RegaliaSlotsApiEntityManager.SERVER;
    return entityManager.getEntitySlots(type);
  }

  public static Map<String, ISlotType> getItemStackSlots(ItemStack stack, boolean isClient) {
    return filteredSlots(slotType -> {
      SlotContext slotContext = new SlotContext(slotType.getIdentifier(), null, 0, false, true);
      SlotResult slotResult = new SlotResult(slotContext, stack);
      return RegaliaSlotsApi.testCurioPredicates(slotType.getValidators(), slotResult);
    }, RegaliaSlotsApi.getSlots(isClient));
  }

  public static Map<String, ISlotType> getItemStackSlots(ItemStack stack,
                                                         LivingEntity livingEntity) {
    return filteredSlots(slotType -> {
      SlotContext slotContext =
          new SlotContext(slotType.getIdentifier(), livingEntity, 0, false, true);
      SlotResult slotResult = new SlotResult(slotContext, stack);
      return RegaliaSlotsApi.testCurioPredicates(slotType.getValidators(), slotResult);
    }, RegaliaSlotsApi.getEntitySlots(livingEntity));
  }

  private static Map<String, ISlotType> filteredSlots(Predicate<ISlotType> filter,
                                                      Map<String, ISlotType> map) {
    Map<String, ISlotType> result = new HashMap<>();

    for (Map.Entry<String, ISlotType> entry : map.entrySet()) {
      ISlotType slotType = entry.getValue();

      if (filter.test(slotType)) {
        result.put(entry.getKey(), slotType);
      }
    }
    return result;
  }

  public static Optional<ICurio> getCurio(ItemStack stack) {
    return Optional.ofNullable(stack.getCapability(RegaliaSlotsApiCapability.ITEM));
  }

  public static Optional<ICuriosItemHandler> getCuriosInventory(LivingEntity livingEntity) {

    if (livingEntity != null) {
      return Optional.ofNullable(livingEntity.getCapability(RegaliaSlotsApiCapability.INVENTORY));
    } else {
      return Optional.empty();
    }
  }

  public static boolean isStackValid(SlotContext slotContext, ItemStack stack) {
    String id = slotContext.identifier();
    LivingEntity entity = slotContext.entity();
    Map<String, ISlotType> map;

    if (entity != null) {
      map = getItemStackSlots(stack, entity);
    } else {
      map = getItemStackSlots(stack, FMLLoader.getDist() == Dist.CLIENT);
    }
    Set<String> slots = map.keySet();

    if (!slots.isEmpty()) {
      return id.equals("curio") || slots.contains(id) || slots.contains("curio");
    } else if (id.equals("curio")) {
      // If there are no slots available to confirm validity for the generic curio slot,
      // perform fallback checks

      // tags
      if (stack.getTags()
          .anyMatch(tagKey -> tagKey.location().getNamespace().equals(RegaliaSlotsApi.MODID))) {
        return true;
      }

      // predicates
      Map<String, ISlotType> allSlots = RegaliaSlotsApi.getSlots(false);
      SlotResult slotResult = new SlotResult(slotContext, stack);

      for (Map.Entry<String, ISlotType> entry : allSlots.entrySet()) {
        ISlotType slotType = entry.getValue();

        for (ResourceLocation validator : slotType.getValidators()) {

          if (RegaliaSlotsApi.getCurioPredicate(validator).map(val -> val.test(slotResult))
              .orElse(false)) {
            return true;
          }
        }
      }

      // capability
      return RegaliaSlotsApi.getCurio(stack).isPresent();
    }
    return false;
  }

  public static Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      SlotContext slotContext, ResourceLocation id, ItemStack stack) {
    Multimap<Holder<Attribute>, AttributeModifier> multimap = LinkedHashMultimap.create();
    CurioAttributeModifiers attributemodifiers =
        stack.getOrDefault(RegaliaSlotsApiRegistry.CURIO_ATTRIBUTE_MODIFIERS, CurioAttributeModifiers.EMPTY);

    if (!attributemodifiers.modifiers().isEmpty()) {

      for (CurioAttributeModifiers.Entry modifier : attributemodifiers.modifiers()) {

        if (modifier.slot().equals(slotContext.identifier())) {
          ResourceLocation rl = modifier.attribute();
          AttributeModifier attributeModifier = modifier.modifier();

          if (rl != null) {
            AttributeModifier.Operation operation = attributeModifier.operation();
            double amount = attributeModifier.amount();

            if (rl.getNamespace().equals("regalia_slots_api")) {
              String identifier1 = rl.getPath();
              LivingEntity livingEntity = slotContext.entity();
              boolean clientSide = livingEntity == null || livingEntity.level().isClientSide();

              if (RegaliaSlotsApi.getSlot(identifier1, clientSide).isPresent()) {
                RegaliaSlotsApi.addSlotModifier(multimap, identifier1, id, amount, operation);
              }
            } else {
              Holder<Attribute> attribute =
                  BuiltInRegistries.ATTRIBUTE.getHolder(rl).orElse(null);

              if (attribute != null) {
                multimap.put(attribute, new AttributeModifier(id, amount, operation));
              }
            }
          }
        }
      }
    } else {
      multimap = getCurio(stack).map(curio -> curio.getAttributeModifiers(slotContext, id))
          .orElse(multimap);
    }
    CurioAttributeModifierEvent evt =
        new CurioAttributeModifierEvent(stack, slotContext, id, multimap);
    NeoForge.EVENT_BUS.post(evt);
    return LinkedHashMultimap.create(evt.getModifiers());
  }

  public static void addSlotModifier(Multimap<Holder<Attribute>, AttributeModifier> map,
                                     String identifier, ResourceLocation id, double amount,
                                     AttributeModifier.Operation operation) {
    map.put(SlotAttribute.getOrCreate(identifier),
        new AttributeModifier(id, amount, operation));
  }

  public static void addSlotModifier(ItemStack stack, String identifier, ResourceLocation id,
                                     double amount, AttributeModifier.Operation operation,
                                     String slot) {
    addModifier(stack, SlotAttribute.getOrCreate(identifier), id, amount, operation, slot);
  }

  public static void addModifier(ItemStack stack, Holder<Attribute> attribute, ResourceLocation id,
                                 double amount, AttributeModifier.Operation operation,
                                 String slot) {
    ResourceLocation rl;

    if (attribute.value() instanceof SlotAttribute wrapper) {
      rl = ResourceLocation.fromNamespaceAndPath(RegaliaSlotsApi.MODID, wrapper.getIdentifier());
    } else {
      rl = BuiltInRegistries.ATTRIBUTE.getKey(attribute.value());
    }

    CurioAttributeModifiers.Entry entry =
        new CurioAttributeModifiers.Entry(rl, new AttributeModifier(id, amount, operation), slot);
    CurioAttributeModifiers curioAttributeModifiers =
        stack.getOrDefault(RegaliaSlotsApiRegistry.CURIO_ATTRIBUTE_MODIFIERS, CurioAttributeModifiers.EMPTY);
    List<CurioAttributeModifiers.Entry> list = new ArrayList<>(curioAttributeModifiers.modifiers());
    list.add(entry);
    stack.set(RegaliaSlotsApiRegistry.CURIO_ATTRIBUTE_MODIFIERS,
        new CurioAttributeModifiers(list, curioAttributeModifiers.showInTooltip()));
  }

  public static void broadcastCurioBreakEvent(SlotContext slotContext) {
    LivingEntity livingEntity = slotContext.entity();

    if (livingEntity != null) {
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity,
          new SPacketBreak(livingEntity.getId(), slotContext.identifier(), slotContext.index()));
    }
  }

  private static final Map<String, UUID> UUIDS = new HashMap<>();

  public static ResourceLocation getSlotId(SlotContext slotContext) {
    String key = slotContext.identifier() + slotContext.index();
    return ResourceLocation.fromNamespaceAndPath(RegaliaSlotsApiConstants.MOD_ID, key);
  }


  private static final Map<ResourceLocation, Predicate<SlotResult>> SLOT_RESULT_PREDICATES =
      new HashMap<>();

  public static void registerCurioPredicate(ResourceLocation resourceLocation,
                                            Predicate<SlotResult> validator) {
    SLOT_RESULT_PREDICATES.putIfAbsent(resourceLocation, validator);
  }

  public static Optional<Predicate<SlotResult>> getCurioPredicate(
      ResourceLocation resourceLocation) {
    return Optional.ofNullable(SLOT_RESULT_PREDICATES.get(resourceLocation));
  }

  public static Map<ResourceLocation, Predicate<SlotResult>> getCurioPredicates() {
    return ImmutableMap.copyOf(SLOT_RESULT_PREDICATES);
  }

  public static boolean testCurioPredicates(Set<ResourceLocation> predicates,
                                            SlotResult slotResult) {

    for (ResourceLocation id : predicates) {

      if (RegaliaSlotsApi.getCurioPredicate(id).map(
          slotResultPredicate -> slotResultPredicate.test(slotResult)).orElse(false)) {
        return true;
      }
    }
    return false;
  }

  static {
    registerCurioPredicate(ResourceLocation.fromNamespaceAndPath(RegaliaSlotsApi.MODID, "all"),
        (slotResult) -> true);
    registerCurioPredicate(ResourceLocation.fromNamespaceAndPath(RegaliaSlotsApi.MODID, "none"),
        (slotResult) -> false);
    registerCurioPredicate(ResourceLocation.fromNamespaceAndPath(RegaliaSlotsApi.MODID, "tag"),
        (slotResult) -> {
          String id = slotResult.slotContext().identifier();
          if (!ResourceLocation.isValidPath(id)) {
            return false;
          }
          TagKey<Item> tag1 =
              ItemTags.create(ResourceLocation.fromNamespaceAndPath(RegaliaSlotsApi.MODID, id));
          TagKey<Item> tag2 =
              ItemTags.create(ResourceLocation.fromNamespaceAndPath(RegaliaSlotsApi.MODID, "curio"));
          ItemStack stack = slotResult.stack();
          return stack.is(tag1) || stack.is(tag2);
        });
  }

  // ============ Curios API Adapters ============

  public static Map<String, top.theillusivec4.curios.api.type.ISlotType> getSlotsForCurios(boolean isClient) {
    return getSlots(isClient).entrySet().stream()
        .collect(java.util.stream.Collectors.toMap(
            entry -> entry.getKey(),
            entry -> new ShimSlotType(entry.getValue())));
  }

  public static Map<String, top.theillusivec4.curios.api.type.ISlotType> getEntitySlotsForCurios(EntityType<?> type, boolean isClient) {
    return getEntitySlots(type, isClient).entrySet().stream()
        .collect(java.util.stream.Collectors.toMap(
            entry -> entry.getKey(),
            entry -> new ShimSlotType(entry.getValue())));
  }

  public static Map<String, top.theillusivec4.curios.api.type.ISlotType> getItemStackSlotsForCurios(ItemStack stack, boolean isClient) {
    return getItemStackSlots(stack, isClient).entrySet().stream()
        .collect(java.util.stream.Collectors.toMap(
            entry -> entry.getKey(),
            entry -> new ShimSlotType(entry.getValue())));
  }

  public static Map<String, top.theillusivec4.curios.api.type.ISlotType> getItemStackSlotsForCurios(ItemStack stack, LivingEntity livingEntity) {
    return getItemStackSlots(stack, livingEntity).entrySet().stream()
        .collect(java.util.stream.Collectors.toMap(
            entry -> entry.getKey(),
            entry -> new ShimSlotType(entry.getValue())));
  }

  public static Optional<top.theillusivec4.curios.api.type.capability.ICurio> getCurioForCurios(ItemStack stack) {
    return getCurio(stack).map(curio -> (top.theillusivec4.curios.api.type.capability.ICurio) curio);
  }

  public static Optional<top.theillusivec4.curios.api.type.capability.ICuriosItemHandler> getCuriosInventoryForCurios(LivingEntity livingEntity) {
    return getCuriosInventory(livingEntity).map(handler -> (top.theillusivec4.curios.api.type.capability.ICuriosItemHandler) handler);
  }

  public static boolean isStackValidForCurios(top.theillusivec4.curios.api.SlotContext slotContext, ItemStack stack) {
    return isStackValid(new com.skd.regaliaslotsapi.api.SlotContext(
        slotContext.identifier(), slotContext.entity(), slotContext.index(), slotContext.cosmetic(), slotContext.visible()), stack);
  }

  public static com.google.common.collect.Multimap<net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute>, net.minecraft.world.entity.ai.attributes.AttributeModifier> getAttributeModifiersForCurios(
      top.theillusivec4.curios.api.SlotContext slotContext, ResourceLocation id, ItemStack stack) {
    return getAttributeModifiers(new com.skd.regaliaslotsapi.api.SlotContext(
        slotContext.identifier(), slotContext.entity(), slotContext.index(), slotContext.cosmetic(), slotContext.visible()), id, stack);
  }

  public static void registerCurioPredicateForCurios(ResourceLocation resourceLocation,
      java.util.function.Predicate<top.theillusivec4.curios.api.SlotResult> validator) {
    registerCurioPredicate(resourceLocation, (com.skd.regaliaslotsapi.api.SlotResult sr) ->
        validator.test(new top.theillusivec4.curios.api.SlotResult(
            new top.theillusivec4.curios.api.SlotContext(
                sr.slotContext().identifier(), sr.slotContext().entity(), sr.slotContext().index(), sr.slotContext().cosmetic(), sr.slotContext().visible()),
            sr.stack())));
  }

  public static Optional<java.util.function.Predicate<top.theillusivec4.curios.api.SlotResult>> getCurioPredicateForCurios(ResourceLocation resourceLocation) {
    return getCurioPredicate(resourceLocation).map(p -> (top.theillusivec4.curios.api.SlotResult sr) ->
        p.test(new com.skd.regaliaslotsapi.api.SlotResult(
            new com.skd.regaliaslotsapi.api.SlotContext(
                sr.slotContext().identifier(), sr.slotContext().entity(), sr.slotContext().index(), sr.slotContext().cosmetic(), sr.slotContext().visible()),
            sr.stack())));
  }

  public static Map<ResourceLocation, java.util.function.Predicate<top.theillusivec4.curios.api.SlotResult>> getCurioPredicatesForCurios() {
    return getCurioPredicates().entrySet().stream()
        .collect(java.util.stream.Collectors.toMap(
            entry -> entry.getKey(),
            entry -> (top.theillusivec4.curios.api.SlotResult sr) ->
                entry.getValue().test(new com.skd.regaliaslotsapi.api.SlotResult(
                    new com.skd.regaliaslotsapi.api.SlotContext(
                        sr.slotContext().identifier(), sr.slotContext().entity(), sr.slotContext().index(), sr.slotContext().cosmetic(), sr.slotContext().visible()),
                    sr.stack()))));
  }

  public static boolean testCurioPredicatesForCurios(Set<ResourceLocation> predicates, top.theillusivec4.curios.api.SlotResult slotResult) {
    return testCurioPredicates(predicates, new com.skd.regaliaslotsapi.api.SlotResult(
        new com.skd.regaliaslotsapi.api.SlotContext(
            slotResult.slotContext().identifier(), slotResult.slotContext().entity(), slotResult.slotContext().index(), slotResult.slotContext().cosmetic(), slotResult.slotContext().visible()),
        slotResult.stack()));
  }

  public static ResourceLocation getSlotIdForCurios(top.theillusivec4.curios.api.SlotContext slotContext) {
    return getSlotId(new com.skd.regaliaslotsapi.api.SlotContext(
        slotContext.identifier(), slotContext.entity(), slotContext.index(), slotContext.cosmetic(), slotContext.visible()));
  }

  public static void broadcastCurioBreakEventForCurios(top.theillusivec4.curios.api.SlotContext slotContext) {
    broadcastCurioBreakEvent(new com.skd.regaliaslotsapi.api.SlotContext(
        slotContext.identifier(), slotContext.entity(), slotContext.index(), slotContext.cosmetic(), slotContext.visible()));
  }
}
