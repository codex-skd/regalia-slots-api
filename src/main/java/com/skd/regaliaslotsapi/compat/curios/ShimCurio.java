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
 * the Curios API {@code ICurio} interface. Delegates to the parallel Regalia
 * implementation via the wrapped delegate.
 */

package com.skd.regaliaslotsapi.compat.curios;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public final class ShimCurio implements top.theillusivec4.curios.api.type.capability.ICurio {

  private final com.skd.regaliaslotsapi.api.type.capability.ICurio delegate;

  public ShimCurio(com.skd.regaliaslotsapi.api.type.capability.ICurio delegate) {
    this.delegate = delegate;
  }

  public com.skd.regaliaslotsapi.api.type.capability.ICurio unwrap() {
    return this.delegate;
  }

  private static com.skd.regaliaslotsapi.api.SlotContext toSkd(
      top.theillusivec4.curios.api.SlotContext ctx) {
    return new com.skd.regaliaslotsapi.api.SlotContext(
        ctx.identifier(), ctx.entity(), ctx.index(), ctx.cosmetic(), ctx.visible());
  }

  private static top.theillusivec4.curios.api.SlotContext toCurios(
      com.skd.regaliaslotsapi.api.SlotContext ctx) {
    return new top.theillusivec4.curios.api.SlotContext(
        ctx.identifier(), ctx.entity(), ctx.index(), ctx.cosmetic(), ctx.visible());
  }

  @Override
  public ItemStack getStack() {
    return this.delegate.getStack();
  }

  @Override
  public void curioTick(top.theillusivec4.curios.api.SlotContext slotContext) {
    this.delegate.curioTick(toSkd(slotContext));
  }

  @Override
  public void onEquip(top.theillusivec4.curios.api.SlotContext slotContext, ItemStack prevStack) {
    this.delegate.onEquip(toSkd(slotContext), prevStack);
  }

  @Override
  public void onUnequip(top.theillusivec4.curios.api.SlotContext slotContext, ItemStack newStack) {
    this.delegate.onUnequip(toSkd(slotContext), newStack);
  }

  @Override
  public boolean canEquip(top.theillusivec4.curios.api.SlotContext slotContext) {
    return this.delegate.canEquip(toSkd(slotContext));
  }

  @Override
  public boolean canUnequip(top.theillusivec4.curios.api.SlotContext slotContext) {
    return this.delegate.canUnequip(toSkd(slotContext));
  }

  @Override
  public List<Component> getSlotsTooltip(List<Component> tooltips, Item.TooltipContext context) {
    return this.delegate.getSlotsTooltip(tooltips, context);
  }

  @Override
  public List<Component> getSlotsTooltip(List<Component> tooltips) {
    return this.delegate.getSlotsTooltip(tooltips);
  }

  @Deprecated(forRemoval = true, since = "1.21")
  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      top.theillusivec4.curios.api.SlotContext slotContext, UUID uuid) {
    return this.delegate.getAttributeModifiers(toSkd(slotContext), uuid);
  }

  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      top.theillusivec4.curios.api.SlotContext slotContext, ResourceLocation id) {
    return this.delegate.getAttributeModifiers(toSkd(slotContext), id);
  }

  @Override
  public void onEquipFromUse(top.theillusivec4.curios.api.SlotContext slotContext) {
    this.delegate.onEquipFromUse(toSkd(slotContext));
  }

  @Nonnull
  @Override
  public top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo getEquipSound(
      top.theillusivec4.curios.api.SlotContext slotContext) {
    com.skd.regaliaslotsapi.api.type.capability.ICurio.SoundInfo skd =
        this.delegate.getEquipSound(toSkd(slotContext));
    return new top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo(
        skd.soundEvent(), skd.volume(), skd.pitch());
  }

  @Override
  public boolean canEquipFromUse(top.theillusivec4.curios.api.SlotContext slotContext) {
    return this.delegate.canEquipFromUse(toSkd(slotContext));
  }

  @Override
  public void curioBreak(top.theillusivec4.curios.api.SlotContext slotContext) {
    this.delegate.curioBreak(toSkd(slotContext));
  }

  @Override
  public boolean canSync(top.theillusivec4.curios.api.SlotContext slotContext) {
    return this.delegate.canSync(toSkd(slotContext));
  }

  @Nonnull
  @Override
  public CompoundTag writeSyncData(top.theillusivec4.curios.api.SlotContext slotContext) {
    return this.delegate.writeSyncData(toSkd(slotContext));
  }

  @Override
  public void readSyncData(top.theillusivec4.curios.api.SlotContext slotContext, CompoundTag compound) {
    this.delegate.readSyncData(toSkd(slotContext), compound);
  }

  @Nonnull
  @Override
  public top.theillusivec4.curios.api.type.capability.ICurio.DropRule getDropRule(
      top.theillusivec4.curios.api.SlotContext slotContext, DamageSource source, boolean recentlyHit) {
    return top.theillusivec4.curios.api.type.capability.ICurio.DropRule.valueOf(
        this.delegate.getDropRule(toSkd(slotContext), source, recentlyHit).name());
  }

  @Deprecated(forRemoval = true, since = "1.21.1")
  @Nonnull
  @Override
  public top.theillusivec4.curios.api.type.capability.ICurio.DropRule getDropRule(
      top.theillusivec4.curios.api.SlotContext slotContext, DamageSource source,
      int lootingLevel, boolean recentlyHit) {
    return top.theillusivec4.curios.api.type.capability.ICurio.DropRule.valueOf(
        this.delegate.getDropRule(toSkd(slotContext), source, lootingLevel, recentlyHit).name());
  }

  @Override
  public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context) {
    return this.delegate.getAttributesTooltip(tooltips, context);
  }

  @Override
  public List<Component> getAttributesTooltip(List<Component> tooltips) {
    return this.delegate.getAttributesTooltip(tooltips);
  }

  @Override
  public int getFortuneLevel(top.theillusivec4.curios.api.SlotContext slotContext, @Nullable LootContext lootContext) {
    return this.delegate.getFortuneLevel(toSkd(slotContext), lootContext);
  }

  @Override
  public int getLootingLevel(top.theillusivec4.curios.api.SlotContext slotContext, @Nullable LootContext lootContext) {
    return this.delegate.getLootingLevel(toSkd(slotContext), lootContext);
  }

  @Override
  public boolean makesPiglinsNeutral(top.theillusivec4.curios.api.SlotContext slotContext) {
    return this.delegate.makesPiglinsNeutral(toSkd(slotContext));
  }

  @Override
  public boolean canWalkOnPowderedSnow(top.theillusivec4.curios.api.SlotContext slotContext) {
    return this.delegate.canWalkOnPowderedSnow(toSkd(slotContext));
  }

  @Override
  public boolean isEnderMask(top.theillusivec4.curios.api.SlotContext slotContext, EnderMan enderMan) {
    return this.delegate.isEnderMask(toSkd(slotContext), enderMan);
  }
}
