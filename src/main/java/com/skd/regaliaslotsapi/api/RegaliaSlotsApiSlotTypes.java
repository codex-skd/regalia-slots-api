package com.skd.regaliaslotsapi.api;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLLoader;
import com.skd.regaliaslotsapi.api.internal.RegaliaSlotsApiServices;
import com.skd.regaliaslotsapi.api.type.ISlotType;

public final class RegaliaSlotsApiSlotTypes {

  public static ISlotType getSlotType(String id) {
    return getSlotType(id, FMLLoader.getCurrent().getDist().isClient());
  }

  public static ISlotType getSlotType(String id, boolean isClient) {
    return getSlotTypes(isClient).get(id);
  }

  public static Map<String, ISlotType> getSlotTypes() {
    return getSlotTypes(FMLLoader.getCurrent().getDist().isClient());
  }

  public static Map<String, ISlotType> getSlotTypes(boolean isClient) {
    return RegaliaSlotsApiServices.SLOTS.getSlotTypes(isClient);
  }

  public static Map<String, ISlotType> getItemSlotTypes(ItemStack stack, boolean isClient) {
    return RegaliaSlotsApiServices.SLOTS.getSlotTypes(stack, isClient);
  }

  public static Map<String, ISlotType> getItemSlotTypes(ItemStack stack,
                                                        LivingEntity livingEntity) {
    return RegaliaSlotsApiServices.SLOTS.getSlotTypes(stack, livingEntity);
  }

  public static Map<String, ISlotType> getDefaultPlayerSlotTypes(boolean isClient) {
    return getDefaultEntitySlotTypes(EntityTypes.PLAYER, isClient);
  }

  public static Map<String, ISlotType> getDefaultEntitySlotTypes(EntityType<?> entityType,
                                                                 boolean isClient) {
    return RegaliaSlotsApiServices.SLOTS.getSlotTypes(entityType, isClient);
  }

  public static Map<String, ISlotType> getDefaultEntitySlotTypes(LivingEntity livingEntity) {
    return RegaliaSlotsApiServices.SLOTS.getSlotTypes(livingEntity);
  }

  public static void registerPredicate(Identifier resourceLocation,
                                       BiPredicate<SlotContext, ItemStack> slotContent) {
    RegaliaSlotsApiServices.SLOTS.registerPredicate(resourceLocation, slotContent);
  }

  public static BiPredicate<SlotContext, ItemStack> getPredicate(
      Identifier resourceLocation) {
    return RegaliaSlotsApiServices.SLOTS.getPredicate(resourceLocation);
  }

  public static Map<Identifier, BiPredicate<SlotContext, ItemStack>> getPredicates() {
    return RegaliaSlotsApiServices.SLOTS.getPredicates();
  }

  public static boolean testPredicates(SlotContext slotContent, ItemStack stack,
                                       Set<Identifier> locations) {

    for (Identifier resourceLocation : locations) {
      BiPredicate<SlotContext, ItemStack> predicate = getPredicate(resourceLocation);

      if (predicate != null && predicate.test(slotContent, stack)) {
        return true;
      }
    }
    return false;
  }

  public enum Preset {
    /**
     * Items worn on the back, such as capes or backpacks.
     */
    BACK,
    /**
     * Items worn around the waist, such as belts or pouches.
     */
    BELT,
    /**
     * Items worn on the torso/chest, such as cloaks or shirts.
     */
    BODY,
    /**
     * Items worn around the wrist, such as bands or bracelets.
     */
    BRACELET,
    /**
     * Miscellaneous items that are not strongly associated with a specific body part or usage type.
     */
    CHARM,
    /**
     * Universal items that are able to equip or be equipped into any slot type.
     */
    CURIO,
    /**
     * Items worn on the feet, such as shoes or boots.
     */
    FEET,
    /**
     * Items worn on the hands, such as gloves or gauntlets.
     */
    HANDS,
    /**
     * Items worn on top of the head, such as crowns or hats.
     */
    HEAD,
    /**
     * Items worn around the neck, such as amulets or necklaces.
     */
    NECKLACE,
    /**
     * Items worn on the fingers, such as rings.
     */
    RING;

    public String id() {
      return this.toString().toLowerCase(Locale.ROOT);
    }
  }
}
