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
 * the Curios API {@code ICuriosItemHandler} interface. Delegates to the
 * parallel Regalia implementation via the wrapped delegate.
 */

package com.skd.regaliaslotsapi.compat.curios;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public final class ShimCuriosItemHandler implements top.theillusivec4.curios.api.type.capability.ICuriosItemHandler {

  private final com.skd.regaliaslotsapi.api.type.capability.ICuriosItemHandler delegate;

  public ShimCuriosItemHandler(com.skd.regaliaslotsapi.api.type.capability.ICuriosItemHandler delegate) {
    this.delegate = delegate;
  }

  public com.skd.regaliaslotsapi.api.type.capability.ICuriosItemHandler unwrap() {
    return this.delegate;
  }

  private static com.skd.regaliaslotsapi.api.SlotResult toSkd(
      top.theillusivec4.curios.api.SlotResult sr) {
    return new com.skd.regaliaslotsapi.api.SlotResult(
        new com.skd.regaliaslotsapi.api.SlotContext(
            sr.slotContext().identifier(), sr.slotContext().entity(),
            sr.slotContext().index(), sr.slotContext().cosmetic(), sr.slotContext().visible()),
        sr.stack());
  }

  private static top.theillusivec4.curios.api.SlotResult toCurios(
      com.skd.regaliaslotsapi.api.SlotResult sr) {
    return new top.theillusivec4.curios.api.SlotResult(
        new top.theillusivec4.curios.api.SlotContext(
            sr.slotContext().identifier(), sr.slotContext().entity(),
            sr.slotContext().index(), sr.slotContext().cosmetic(), sr.slotContext().visible()),
        sr.stack());
  }

  @Override
  public Map<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> getCurios() {
    Map<String, com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler> skd = this.delegate.getCurios();
    java.util.HashMap<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> result =
        new java.util.HashMap<>(skd.size());
    for (Map.Entry<String, com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler> entry : skd.entrySet()) {
      result.put(entry.getKey(), new ShimCurioStacksHandler(entry.getValue()));
    }
    return result;
  }

  @Override
  public void setCurios(Map<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> map) {
    java.util.HashMap<String, com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler> skd =
        new java.util.HashMap<>(map.size());
    for (Map.Entry<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> entry : map.entrySet()) {
      if (entry.getValue() instanceof ShimCurioStacksHandler shim) {
        skd.put(entry.getKey(), shim.unwrap());
      }
    }
    this.delegate.setCurios(skd);
  }

  @Override
  public int getSlots() {
    return this.delegate.getSlots();
  }

  @Override
  public int getVisibleSlots() {
    return this.delegate.getVisibleSlots();
  }

  @Override
  public void reset() {
    this.delegate.reset();
  }

  @Override
  public Optional<top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> getStacksHandler(String identifier) {
    return this.delegate.getStacksHandler(identifier)
        .map(h -> new ShimCurioStacksHandler(h));
  }

  @Override
  public IItemHandlerModifiable getEquippedCurios() {
    return this.delegate.getEquippedCurios();
  }

  @Override
  public void setEquippedCurio(String identifier, int index, ItemStack stack) {
    this.delegate.setEquippedCurio(identifier, index, stack);
  }

  @Override
  public boolean isEquipped(Item item) {
    return this.delegate.isEquipped(item);
  }

  @Override
  public boolean isEquipped(Predicate<ItemStack> filter) {
    return this.delegate.isEquipped(filter::test);
  }

  @Override
  public boolean isSlotActive(String identifier, int index) {
    return this.delegate.isSlotActive(identifier, index);
  }

  @Override
  public void setSlotActive(String identifier, int index, boolean active) {
    this.delegate.setSlotActive(identifier, index, active);
  }

  @Override
  public void setSlotsActive(String identifier, boolean active) {
    this.delegate.setSlotsActive(identifier, active);
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(Item item) {
    return this.delegate.findFirstCurio(item).map(ShimCuriosItemHandler::toCurios);
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(Predicate<ItemStack> filter) {
    return this.delegate.findFirstCurio(filter::test).map(ShimCuriosItemHandler::toCurios);
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(Predicate<ItemStack> filter, String cacheKey) {
    return this.delegate.findFirstCurio(filter::test, cacheKey).map(ShimCuriosItemHandler::toCurios);
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(Predicate<ItemStack> filter, boolean includeInactive, String cacheKey) {
    return this.delegate.findFirstCurio(filter::test, includeInactive, cacheKey).map(ShimCuriosItemHandler::toCurios);
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(Item item) {
    return this.delegate.findCurios(item).stream()
        .map(ShimCuriosItemHandler::toCurios)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(Predicate<ItemStack> filter) {
    return this.delegate.findCurios(filter::test).stream()
        .map(ShimCuriosItemHandler::toCurios)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(Predicate<ItemStack> filter, boolean includeInactive, String cacheKey) {
    return this.delegate.findCurios(filter::test, includeInactive, cacheKey).stream()
        .map(ShimCuriosItemHandler::toCurios)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(String... identifiers) {
    return this.delegate.findCurios(identifiers).stream()
        .map(ShimCuriosItemHandler::toCurios)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(boolean includeInactive, String... identifiers) {
    return this.delegate.findCurios(includeInactive, identifiers).stream()
        .map(ShimCuriosItemHandler::toCurios)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findCurio(String identifier, int index) {
    return this.delegate.findCurio(identifier, index).map(ShimCuriosItemHandler::toCurios);
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findCurio(String identifier, int index, boolean includeInactive) {
    return this.delegate.findCurio(identifier, index, includeInactive).map(ShimCuriosItemHandler::toCurios);
  }

  @Override
  public LivingEntity getWearer() {
    return this.delegate.getWearer();
  }

  @Override
  public void loseInvalidStack(ItemStack stack) {
    this.delegate.loseInvalidStack(stack);
  }

  @Override
  public void handleInvalidStacks() {
    this.delegate.handleInvalidStacks();
  }

  @Override
  public int getFortuneLevel(@Nullable LootContext lootContext) {
    return this.delegate.getFortuneLevel(lootContext);
  }

  @Override
  public int getLootingLevel(@Nullable LootContext lootContext) {
    return this.delegate.getLootingLevel(lootContext);
  }

  @Override
  public ListTag saveInventory(boolean clear) {
    return this.delegate.saveInventory(clear);
  }

  @Override
  public void loadInventory(ListTag data) {
    this.delegate.loadInventory(data);
  }

  @Override
  public Set<top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> getUpdatingInventories() {
    return this.delegate.getUpdatingInventories().stream()
        .map(h -> (top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler) new ShimCurioStacksHandler(h))
        .collect(java.util.stream.Collectors.toSet());
  }

  @Override
  public void addTransientSlotModifier(String slot, ResourceLocation id, double amount, AttributeModifier.Operation operation) {
    this.delegate.addTransientSlotModifier(slot, id, amount, operation);
  }

  @Override
  public void addTransientSlotModifiers(Multimap<String, AttributeModifier> modifiers) {
    this.delegate.addTransientSlotModifiers(modifiers);
  }

  @Override
  public void addPermanentSlotModifier(String slot, ResourceLocation id, double amount, AttributeModifier.Operation operation) {
    this.delegate.addPermanentSlotModifier(slot, id, amount, operation);
  }

  @Override
  public void addPermanentSlotModifiers(Multimap<String, AttributeModifier> modifiers) {
    this.delegate.addPermanentSlotModifiers(modifiers);
  }

  @Override
  public void removeSlotModifier(String slot, ResourceLocation id) {
    this.delegate.removeSlotModifier(slot, id);
  }

  @Override
  public void removeSlotModifiers(Multimap<String, AttributeModifier> modifiers) {
    this.delegate.removeSlotModifiers(modifiers);
  }

  @Override
  public void clearSlotModifiers() {
    this.delegate.clearSlotModifiers();
  }

  @Override
  public Multimap<String, AttributeModifier> getModifiers() {
    return this.delegate.getModifiers();
  }

  @Override
  public Tag writeTag() {
    return this.delegate.writeTag();
  }

  @Override
  public void readTag(Tag tag) {
    this.delegate.readTag(tag);
  }

  @Override
  public void clearCachedSlotModifiers() {
    this.delegate.clearCachedSlotModifiers();
  }

  @Override
  public Set<String> getLockedSlots() {
    return this.delegate.getLockedSlots();
  }

  @Override
  public void unlockSlotType(String identifier, int amount, boolean visible, boolean cosmetic) {
    this.delegate.unlockSlotType(identifier, amount, visible, cosmetic);
  }

  @Override
  public void lockSlotType(String identifier) {
    this.delegate.lockSlotType(identifier);
  }

  @Override
  public void processSlots() {
    this.delegate.processSlots();
  }

  @Override
  public int getFortuneBonus() {
    return this.delegate.getFortuneBonus();
  }

  @Override
  public void growSlotType(String identifier, int amount) {
    this.delegate.growSlotType(identifier, amount);
  }

  @Override
  public void shrinkSlotType(String identifier, int amount) {
    this.delegate.shrinkSlotType(identifier, amount);
  }
}
