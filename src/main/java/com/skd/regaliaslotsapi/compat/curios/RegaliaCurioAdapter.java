package com.skd.regaliaslotsapi.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
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
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosResources;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.common.DropRule;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class RegaliaCurioAdapter implements com.skd.regaliaslotsapi.api.type.capability.ICurio {

    private final ICurioItem delegate;
    private final ItemStack stack;

    public RegaliaCurioAdapter(ICurioItem delegate, ItemStack stack) {
        this.delegate = delegate;
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void curioTick(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        this.delegate.curioTick(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public void onEquip(com.skd.regaliaslotsapi.api.SlotContext slotContext, ItemStack prevStack) {
        this.delegate.onEquip(RegaliaTypeBridge.toCurios(slotContext), prevStack, this.stack);
    }

    @Override
    public void onStateChange(com.skd.regaliaslotsapi.api.SlotContext slotContext, ItemStack prevStack) {
        this.delegate.onStateChange(RegaliaTypeBridge.toCurios(slotContext), prevStack, this.stack);
    }

    @Override
    public void onUnequip(com.skd.regaliaslotsapi.api.SlotContext slotContext, ItemStack newStack) {
        this.delegate.onUnequip(RegaliaTypeBridge.toCurios(slotContext), newStack, this.stack);
    }

    @Override
    public boolean canEquip(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        return this.delegate.canEquip(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public boolean canUnequip(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        return this.delegate.canUnequip(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public List<Component> getSlotsTooltip(List<Component> tooltips, Item.TooltipContext context) {
        return this.delegate.getSlotsTooltip(tooltips, context, this.stack);
    }

    @Override
    public com.skd.regaliaslotsapi.api.CurioAttributeModifiers getDefaultCurioAttributeModifiers() {
        top.theillusivec4.curios.api.CurioAttributeModifiers curiosModifiers =
            this.delegate.getDefaultCurioAttributeModifiers(this.stack);
        return RegaliaTypeBridge.toRegalia(curiosModifiers);
    }

    @Override
    @Deprecated(forRemoval = true)
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            com.skd.regaliaslotsapi.api.SlotContext slotContext, Identifier id) {
        return this.delegate.getAttributeModifiers(
            RegaliaTypeBridge.toCurios(slotContext),
            RegaliaTypeBridge.toCuriosIdentifier(id),
            this.stack);
    }

    @Override
    @Nonnull
    public com.skd.regaliaslotsapi.api.type.capability.ICurio.SoundInfo getEquipSound(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo curiosSound =
            this.delegate.getEquipSound(RegaliaTypeBridge.toCurios(slotContext), this.stack);
        return new com.skd.regaliaslotsapi.api.type.capability.ICurio.SoundInfo(curiosSound.soundEvent(), curiosSound.volume(), curiosSound.pitch());
    }

    @Override
    public boolean canEquipFromUse(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        return this.delegate.canEquipFromUse(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public void curioBreak(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        this.delegate.curioBreak(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public boolean canSync(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        return this.delegate.canSync(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    @Nonnull
    public CompoundTag writeSyncData(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        return this.delegate.writeSyncData(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public void readSyncData(com.skd.regaliaslotsapi.api.SlotContext slotContext, CompoundTag compound) {
        this.delegate.readSyncData(RegaliaTypeBridge.toCurios(slotContext), compound, this.stack);
    }

    @Override
    @Nonnull
    public com.skd.regaliaslotsapi.api.common.DropRule getDropRule(com.skd.regaliaslotsapi.api.SlotContext slotContext, DamageSource source, boolean recentlyHit) {
        return RegaliaTypeBridge.toRegaliaDropRule(
            this.delegate.getDropRule(RegaliaTypeBridge.toCurios(slotContext), source, recentlyHit, this.stack));
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context) {
        return this.delegate.getAttributesTooltip(tooltips, context, this.stack);
    }

    @Override
    public int getFortuneLevel(com.skd.regaliaslotsapi.api.SlotContext slotContext, @Nullable LootContext lootContext) {
        return this.delegate.getFortuneLevel(RegaliaTypeBridge.toCurios(slotContext), lootContext, this.stack);
    }

    @Override
    public int getLootingLevel(com.skd.regaliaslotsapi.api.SlotContext slotContext, @Nullable LootContext lootContext) {
        return this.delegate.getLootingLevel(RegaliaTypeBridge.toCurios(slotContext), lootContext, this.stack);
    }

    @Override
    public boolean makesPiglinsNeutral(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        return this.delegate.makesPiglinsNeutral(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public boolean canWalkOnPowderedSnow(com.skd.regaliaslotsapi.api.SlotContext slotContext) {
        return this.delegate.canWalkOnPowderedSnow(RegaliaTypeBridge.toCurios(slotContext), this.stack);
    }

    @Override
    public boolean isEnderMask(com.skd.regaliaslotsapi.api.SlotContext slotContext, EnderMan enderMan) {
        return this.delegate.isEnderMask(RegaliaTypeBridge.toCurios(slotContext), enderMan, this.stack);
    }
}