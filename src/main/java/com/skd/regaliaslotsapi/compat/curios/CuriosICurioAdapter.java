package com.skd.regaliaslotsapi.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.common.DropRule;
import com.skd.regaliaslotsapi.api.CurioAttributeModifiers;
import com.skd.regaliaslotsapi.api.type.capability.ICurio;

/**
 * Wraps a Curios-API {@link top.theillusivec4.curios.api.type.capability.ICurio} instance that is
 * already bound to a specific {@link ItemStack} into the regalia {@link ICurio} interface.
 * <p>
 * Each method converts the regalia {@link SlotContext} via {@link RegaliaTypeBridge} and forwards
 * to the delegate. This is used as a belt-and-braces fallback in the native tick loop so that
 * items whose behaviour was registered through the copied Curios API still receive
 * {@code curioTick}/{@code onEquip}/{@code onUnequip}/{@code onStateChange} calls.
 */
public class CuriosICurioAdapter implements ICurio {

  private final top.theillusivec4.curios.api.type.capability.ICurio delegate;
  private final ItemStack stack;

  public CuriosICurioAdapter(top.theillusivec4.curios.api.type.capability.ICurio delegate,
                             ItemStack stack) {
    this.delegate = delegate;
    this.stack = stack;
  }

  @Override
  public ItemStack getStack() {
    return this.stack;
  }

  @Override
  public void curioTick(SlotContext slotContext) {
    this.delegate.curioTick(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public void onEquip(SlotContext slotContext, ItemStack prevStack) {
    this.delegate.onEquip(RegaliaTypeBridge.toCurios(slotContext), prevStack);
  }

  @Override
  public void onStateChange(SlotContext slotContext, ItemStack prevStack) {
    this.delegate.onStateChange(RegaliaTypeBridge.toCurios(slotContext), prevStack);
  }

  @Override
  public void onUnequip(SlotContext slotContext, ItemStack newStack) {
    this.delegate.onUnequip(RegaliaTypeBridge.toCurios(slotContext), newStack);
  }

  @Override
  public boolean canEquip(SlotContext slotContext) {
    return this.delegate.canEquip(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public boolean canUnequip(SlotContext slotContext) {
    return this.delegate.canUnequip(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public List<Component> getSlotsTooltip(List<Component> tooltips, Item.TooltipContext context) {
    return this.delegate.getSlotsTooltip(tooltips, context);
  }

  @Override
  public CurioAttributeModifiers getDefaultCurioAttributeModifiers() {
    top.theillusivec4.curios.api.CurioAttributeModifiers curiosModifiers =
        this.delegate.getDefaultCurioAttributeModifiers();
    return RegaliaTypeBridge.toRegalia(curiosModifiers);
  }

  @Override
  @Deprecated(forRemoval = true)
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      SlotContext slotContext, Identifier id) {
    return this.delegate.getAttributeModifiers(
        RegaliaTypeBridge.toCurios(slotContext),
        RegaliaTypeBridge.toCuriosIdentifier(id));
  }

  @Override
  @Nonnull
  public ICurio.SoundInfo getEquipSound(SlotContext slotContext) {
    top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo curiosSound =
        this.delegate.getEquipSound(RegaliaTypeBridge.toCurios(slotContext));
    return new ICurio.SoundInfo(curiosSound.soundEvent(), curiosSound.volume(), curiosSound.pitch());
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext) {
    return this.delegate.canEquipFromUse(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public void curioBreak(SlotContext slotContext) {
    this.delegate.curioBreak(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public boolean canSync(SlotContext slotContext) {
    return this.delegate.canSync(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  @Nonnull
  public CompoundTag writeSyncData(SlotContext slotContext) {
    return this.delegate.writeSyncData(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public void readSyncData(SlotContext slotContext, CompoundTag compound) {
    this.delegate.readSyncData(RegaliaTypeBridge.toCurios(slotContext), compound);
  }

  @Override
  @Nonnull
  public DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit) {
    return RegaliaTypeBridge.toRegaliaDropRule(
        this.delegate.getDropRule(RegaliaTypeBridge.toCurios(slotContext), source, recentlyHit));
  }

  @Override
  public List<Component> getAttributesTooltip(List<Component> tooltips,
                                              Item.TooltipContext context) {
    return this.delegate.getAttributesTooltip(tooltips, context);
  }

  @Override
  public int getFortuneLevel(SlotContext slotContext, @Nullable LootContext lootContext) {
    return this.delegate.getFortuneLevel(RegaliaTypeBridge.toCurios(slotContext), lootContext);
  }

  @Override
  public int getLootingLevel(SlotContext slotContext, @Nullable LootContext lootContext) {
    return this.delegate.getLootingLevel(RegaliaTypeBridge.toCurios(slotContext), lootContext);
  }

  @Override
  public boolean makesPiglinsNeutral(SlotContext slotContext) {
    return this.delegate.makesPiglinsNeutral(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public boolean canWalkOnPowderedSnow(SlotContext slotContext) {
    return this.delegate.canWalkOnPowderedSnow(RegaliaTypeBridge.toCurios(slotContext));
  }

  @Override
  public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan) {
    return this.delegate.isEnderMask(RegaliaTypeBridge.toCurios(slotContext), enderMan);
  }
}
