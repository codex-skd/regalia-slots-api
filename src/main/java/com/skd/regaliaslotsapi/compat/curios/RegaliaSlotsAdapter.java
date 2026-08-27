package com.skd.regaliaslotsapi.compat.curios;

import java.util.Map;
import java.util.function.BiPredicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.skd.regaliaslotsapi.api.internal.RegaliaSlotsApiServices;

/**
 * Delegates slot-type queries and validator predicates to Regalia Slots API's own
 * {@link RegaliaSlotsApiServices#SLOTS}, wrapping the results as the copied Curios types so
 * third-party mods see the exact same slot layout Regalia itself uses.
 * <p>
 * {@link #getSlotData(String)} and {@link #getEntitiesData()} are datagen-only builder factories
 * in upstream Curios (not queried at runtime by other mods) and are intentionally not ported -
 * calling them throws {@link UnsupportedOperationException}.
 */
public class RegaliaSlotsAdapter
    implements top.theillusivec4.curios.api.internal.services.ICuriosSlots {

  @Override
  public Map<String, top.theillusivec4.curios.api.type.ISlotType> getSlotTypes(boolean isClient) {
    return RegaliaTypeBridge.wrapSlotTypes(RegaliaSlotsApiServices.SLOTS.getSlotTypes(isClient));
  }

  @Override
  public Map<String, top.theillusivec4.curios.api.type.ISlotType> getSlotTypes(
      LivingEntity livingEntity) {
    return RegaliaTypeBridge.wrapSlotTypes(RegaliaSlotsApiServices.SLOTS.getSlotTypes(livingEntity));
  }

  @Override
  public Map<String, top.theillusivec4.curios.api.type.ISlotType> getSlotTypes(
      EntityType<?> entityType, boolean isClient) {
    return RegaliaTypeBridge.wrapSlotTypes(
        RegaliaSlotsApiServices.SLOTS.getSlotTypes(entityType, isClient));
  }

  @Override
  public Map<String, top.theillusivec4.curios.api.type.ISlotType> getSlotTypes(ItemStack stack,
                                                                                 boolean isClient) {
    return RegaliaTypeBridge.wrapSlotTypes(RegaliaSlotsApiServices.SLOTS.getSlotTypes(stack, isClient));
  }

  @Override
  public Map<String, top.theillusivec4.curios.api.type.ISlotType> getSlotTypes(ItemStack stack,
                                                                                 LivingEntity livingEntity) {
    return RegaliaTypeBridge.wrapSlotTypes(
        RegaliaSlotsApiServices.SLOTS.getSlotTypes(stack, livingEntity));
  }

  @Override
  public top.theillusivec4.curios.api.type.data.ISlotData getSlotData(String id) {
    throw new UnsupportedOperationException(
        "Regalia compat: datagen-only ISlotData builder is not ported by regalia_slots_api");
  }

  @Override
  public top.theillusivec4.curios.api.type.data.IEntitiesData getEntitiesData() {
    throw new UnsupportedOperationException(
        "Regalia compat: datagen-only IEntitiesData builder is not ported by regalia_slots_api");
  }

  @Override
  public void registerPredicate(Identifier resourceLocation,
                                BiPredicate<top.theillusivec4.curios.api.SlotContext, ItemStack> predicate) {
    RegaliaSlotsApiServices.SLOTS.registerPredicate(resourceLocation,
        (ctx, stack) -> predicate.test(RegaliaTypeBridge.toCurios(ctx), stack));
  }

  @Override
  public BiPredicate<top.theillusivec4.curios.api.SlotContext, ItemStack> getPredicate(
      Identifier resourceLocation) {
    BiPredicate<com.skd.regaliaslotsapi.api.SlotContext, ItemStack> predicate =
        RegaliaSlotsApiServices.SLOTS.getPredicate(resourceLocation);

    if (predicate == null) {
      return null;
    }
    return (ctx, stack) -> predicate.test(RegaliaTypeBridge.toRegalia(ctx), stack);
  }

  @Override
  public Map<Identifier, BiPredicate<top.theillusivec4.curios.api.SlotContext, ItemStack>> getPredicates() {
    Map<Identifier, BiPredicate<com.skd.regaliaslotsapi.api.SlotContext, ItemStack>> predicates =
        RegaliaSlotsApiServices.SLOTS.getPredicates();
    Map<Identifier, BiPredicate<top.theillusivec4.curios.api.SlotContext, ItemStack>> result =
        new java.util.LinkedHashMap<>();
    predicates.forEach((id, predicate) -> result.put(id,
        (ctx, stack) -> predicate.test(RegaliaTypeBridge.toRegalia(ctx), stack)));
    return result;
  }
}
