package com.skd.regaliaslotsapi.impl;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiPredicate;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiSlotTypes;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiTags;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.internal.services.IRegaliaSlotsApiSlots;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.data.IEntitiesData;
import com.skd.regaliaslotsapi.api.type.data.ISlotData;
import com.skd.regaliaslotsapi.common.data.RegaliaSlotsApiSlotResources;
import com.skd.regaliaslotsapi.common.data.EntitiesData;
import com.skd.regaliaslotsapi.common.data.SlotData;

public class RegaliaSlotsApiSlots implements IRegaliaSlotsApiSlots {

  private static final Map<Identifier, BiPredicate<SlotContext, ItemStack>> PREDICATES =
      Object2ObjectMaps.synchronize(new Object2ObjectArrayMap<>());

  private static RegaliaSlotsApiSlotResources getSidedSlots(boolean isClient) {
    return isClient ? RegaliaSlotsApiSlotResources.CLIENT : RegaliaSlotsApiSlotResources.SERVER;
  }

  @Override
  public Map<String, ISlotType> getSlotTypes(boolean isClient) {
    return getSidedSlots(isClient).getSlots();
  }

  @Override
  public Map<String, ISlotType> getSlotTypes(LivingEntity livingEntity) {
    return getSidedSlots(livingEntity.level().isClientSide()).getEntitySlots(
        livingEntity.getType());
  }

  @Override
  public Map<String, ISlotType> getSlotTypes(EntityType<?> entityType, boolean isClient) {
    return getSidedSlots(isClient).getEntitySlots(entityType);
  }

  @Override
  public Map<String, ISlotType> getSlotTypes(ItemStack stack, boolean isClient) {
    Map<String, ISlotType> results = new TreeMap<>();

    for (ISlotType value : getSidedSlots(isClient).getSlots().values()) {
      String key = value.getId();

      if (value.isItemValid(new SlotContext(key, null, 0, false, true), stack)) {
        results.put(key, value);
      }
    }

    if (!stack.is(RegaliaSlotsApiTags.GENERIC_EXCLUSIONS) && !results.isEmpty()) {
      String key = RegaliaSlotsApiSlotTypes.Preset.CURIO.id();
      results.put(key, ISlotType.get(key));
    }
    return results;
  }

  @Override
  public Map<String, ISlotType> getSlotTypes(ItemStack stack, LivingEntity livingEntity) {
    Map<String, ISlotType> results = new TreeMap<>();
    Map<String, ISlotType> slots = getSlotTypes(livingEntity);

    for (Map.Entry<String, ISlotType> entry : slots.entrySet()) {
      ISlotType value = entry.getValue();

      if (value.isItemValid(new SlotContext(entry.getKey(), livingEntity, 0, false, true), stack)) {
        results.put(entry.getKey(), value);
      }
    }

    if (!stack.is(RegaliaSlotsApiTags.GENERIC_EXCLUSIONS) && !results.isEmpty()) {
      String key = RegaliaSlotsApiSlotTypes.Preset.CURIO.id();
      results.put(key, ISlotType.get(key));
    }
    return results;
  }

  @Override
  public ISlotData getSlotData(String id) {
    return new SlotData(id, false);
  }

  @Override
  public IEntitiesData getEntitiesData() {
    return new EntitiesData();
  }

  @Override
  public void registerPredicate(Identifier resourceLocation,
                                BiPredicate<SlotContext, ItemStack> predicate) {
    PREDICATES.put(resourceLocation, predicate);
  }

  @Override
  public BiPredicate<SlotContext, ItemStack> getPredicate(Identifier resourceLocation) {
    return PREDICATES.get(resourceLocation);
  }

  @Override
  public Map<Identifier, BiPredicate<SlotContext, ItemStack>> getPredicates() {
    return ImmutableMap.copyOf(PREDICATES);
  }

  static {
    PREDICATES.put(RegaliaSlotsApiResources.resource("all"), (ctx, stack) -> true);
    PREDICATES.put(RegaliaSlotsApiResources.resource("none"), (ctx, stack) -> false);
    PREDICATES.put(RegaliaSlotsApiResources.resource("tag"),
                   (ctx, stack) -> {
                     String id = ctx.identifier();
                     TagKey<Item> tag1 =
                         ItemTags.create(RegaliaSlotsApiResources.resource(id));
                     return stack.is(tag1) || stack.is(RegaliaSlotsApiTags.CURIO);
                   });
  }
}
