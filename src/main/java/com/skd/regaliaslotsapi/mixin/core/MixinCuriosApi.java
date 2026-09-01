package com.skd.regaliaslotsapi.mixin.core;

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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;
import com.skd.regaliaslotsapi.compat.curios.LegacyCuriosHelperShim;
import com.skd.regaliaslotsapi.mixin.RegaliaSlotsApiImplMixinHooks;

@Mixin(value = CuriosApi.class, remap = false)
public class MixinCuriosApi {

  private static final ICuriosHelper regalia$legacyCuriosHelper = new LegacyCuriosHelperShim();

  @Inject(at = @At("HEAD"), method = "getCuriosHelper", cancellable = true)
  private static void curios$getCuriosHelper(CallbackInfoReturnable<ICuriosHelper> cir) {
    cir.setReturnValue(regalia$legacyCuriosHelper);
  }

  @Inject(at = @At("HEAD"), method = "registerCurio", cancellable = true)
  private static void curios$registerCurio(Item item, ICurioItem icurio, CallbackInfo ci) {
    RegaliaSlotsApiImplMixinHooks.registerCurio(item, (com.skd.regaliaslotsapi.api.type.capability.ICurioItem) icurio);
    ci.cancel();
  }

  @Inject(at = @At("HEAD"), method = "getSlots(Z)Ljava/util/Map;", cancellable = true)
  private static void curios$getSlots(boolean isClient,
                                      CallbackInfoReturnable<Map<String, ISlotType>> cir) {
    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.getSlotsForCurios(isClient));
  }

  @Inject(at = @At("HEAD"), method = "getEntitySlots(Lnet/minecraft/world/entity/EntityType;Z)Ljava/util/Map;", cancellable = true)
  private static void curios$getEntitySlots(EntityType<?> type, boolean isClient,
                                            CallbackInfoReturnable<Map<String, ISlotType>> cir) {
    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.getEntitySlotsForCurios(type, isClient));
  }

  @Inject(at = @At("HEAD"), method = "getItemStackSlots(Lnet/minecraft/world/item/ItemStack;Z)Ljava/util/Map;", cancellable = true)
  private static void curios$getItemStackSlots(ItemStack stack, boolean isClient,
                                               CallbackInfoReturnable<Map<String, ISlotType>> cir) {

    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.getItemStackSlotsForCurios(stack, isClient));
  }

  @Inject(at = @At("HEAD"), method = "getItemStackSlots(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/Map;", cancellable = true)
  private static void curios$getItemStackSlots(ItemStack stack, LivingEntity livingEntity,
                                               CallbackInfoReturnable<Map<String, ISlotType>> cir) {
    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.getItemStackSlotsForCurios(stack, livingEntity));
  }

  @Inject(at = @At("HEAD"), method = "getCurio", cancellable = true)
  private static void curios$getCurio(ItemStack stack,
                                      CallbackInfoReturnable<Optional<ICurio>> cir) {
    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.getCurioForCurios(stack));
  }

  @Inject(at = @At("HEAD"), method = "getCuriosInventory", cancellable = true)
  private static void curios$getCuriosInventory(LivingEntity livingEntity,
                                                CallbackInfoReturnable<Optional<ICuriosItemHandler>> cir) {
    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.getCuriosInventoryForCurios(livingEntity));
  }

  @Inject(at = @At("HEAD"), method = "isStackValid", cancellable = true)
  private static void curios$isStackValid(SlotContext slotContext, ItemStack stack,
                                          CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.isStackValidForCurios(slotContext, stack));
  }

  @Inject(at = @At("HEAD"), method = "getAttributeModifiers", cancellable = true)
  private static void curios$getAttributeModifiers(SlotContext slotContext, ResourceLocation id,
                                                   ItemStack stack,
                                                   CallbackInfoReturnable<Multimap<Holder<Attribute>, AttributeModifier>> cir) {
    cir.setReturnValue(RegaliaSlotsApiImplMixinHooks.getAttributeModifiersForCurios(slotContext, id, stack));
  }

  @Inject(at = @At("HEAD"), method = "addSlotModifier(Lcom/google/common/collect/Multimap;Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)V", cancellable = true)
  private static void curios$addSlotModifier(Multimap<Holder<Attribute>, AttributeModifier> map,
                                             String identifier,
                                             ResourceLocation id, double amount,
                                             AttributeModifier.Operation operation,
                                             CallbackInfo ci) {
    RegaliaSlotsApiImplMixinHooks.addSlotModifier(map, identifier, id, amount, operation);
    ci.cancel();
  }

  @Inject(at = @At("HEAD"), method = "addSlotModifier(Lnet/minecraft/world/item/ItemStack;Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;Ljava/lang/String;)V", cancellable = true)
  private static void curios$addSlotModifier(ItemStack stack, String identifier,
                                             ResourceLocation id, double amount,
                                             AttributeModifier.Operation operation, String slot,
                                             CallbackInfo ci) {
    RegaliaSlotsApiImplMixinHooks.addSlotModifier(stack, identifier, id, amount, operation, slot);
    ci.cancel();
  }

  @Inject(at = @At("HEAD"), method = "addModifier", cancellable = true)
  private static void curios$addModifier(ItemStack stack, Holder<Attribute> attribute,
                                         ResourceLocation id, double amount,
                                         AttributeModifier.Operation operation, String slot,
                                         CallbackInfo ci) {
    RegaliaSlotsApiImplMixinHooks.addModifier(stack, attribute, id, amount, operation, slot);
    ci.cancel();
  }

  @Inject(at = @At("HEAD"), method = "registerCurioPredicate", cancellable = true)
  private static void curios$registerCurioPredicate(ResourceLocation resourceLocation,
                                                    Predicate<SlotResult> validator,
                                                    CallbackInfo ci) {
    RegaliaSlotsApiImplMixinHooks.registerCurioPredicateForCurios(resourceLocation, validator);
    ci.cancel();
  }

  @Inject(at = @At("HEAD"), method = "getCurioPredicate", cancellable = true)
  private static void curios$getCurioPredicate(ResourceLocation resourceLocation,
                                               CallbackInfoReturnable<Optional<Predicate<SlotResult>>> ci) {
    ci.setReturnValue(RegaliaSlotsApiImplMixinHooks.getCurioPredicateForCurios(resourceLocation));
  }

  @Inject(at = @At("HEAD"), method = "getCurioPredicates", cancellable = true)
  private static void curios$getCurioPredicates(
      CallbackInfoReturnable<Map<ResourceLocation, Predicate<SlotResult>>> ci) {
    ci.setReturnValue(RegaliaSlotsApiImplMixinHooks.getCurioPredicatesForCurios());
  }

  @Inject(at = @At("HEAD"), method = "testCurioPredicates", cancellable = true)
  private static void curios$testCurioPredicates(Set<ResourceLocation> predicates,
                                                 SlotResult slotResult,
                                                 CallbackInfoReturnable<Boolean> ci) {
    ci.setReturnValue(RegaliaSlotsApiImplMixinHooks.testCurioPredicatesForCurios(predicates, slotResult));
  }

  @Inject(at = @At("HEAD"), method = "getSlotId", cancellable = true)
  private static void curios$getUuid(SlotContext slotContext,
                                     CallbackInfoReturnable<ResourceLocation> ci) {
    ci.setReturnValue(RegaliaSlotsApiImplMixinHooks.getSlotIdForCurios(slotContext));
  }

  @Inject(at = @At("HEAD"), method = "broadcastCurioBreakEvent", cancellable = true)
  private static void curios$broadcastCurioBreakEvent(SlotContext slotContext, CallbackInfo ci) {
    RegaliaSlotsApiImplMixinHooks.broadcastCurioBreakEventForCurios(slotContext);
    ci.cancel();
  }
}