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
 * the Curios API {@code ICurioStacksHandler} interface. Delegates to the
 * parallel Regalia implementation via the wrapped delegate.
 */

package com.skd.regaliaslotsapi.compat.curios;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public final class ShimCurioStacksHandler implements top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler {

  private final com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler delegate;

  public ShimCurioStacksHandler(com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler delegate) {
    this.delegate = delegate;
  }

  public com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler unwrap() {
    return this.delegate;
  }

  @Override
  public top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler getStacks() {
    return new ShimDynamicStackHandler(this.delegate.getStacks());
  }

  @Override
  public top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler getCosmeticStacks() {
    return new ShimDynamicStackHandler(this.delegate.getCosmeticStacks());
  }

  @Override
  public NonNullList<Boolean> getRenders() {
    return this.delegate.getRenders();
  }

  @Override
  public NonNullList<Boolean> getActiveStates() {
    return this.delegate.getActiveStates();
  }

  @Override
  public void updateActiveState(int index) {
    this.delegate.updateActiveState(index);
  }

  @Override
  public boolean canToggleRendering() {
    return this.delegate.canToggleRendering();
  }

  @Override
  public top.theillusivec4.curios.api.type.capability.ICurio.DropRule getDropRule() {
    return top.theillusivec4.curios.api.type.capability.ICurio.DropRule.valueOf(
        this.delegate.getDropRule().name());
  }

  @Override
  public int getSlots() {
    return this.delegate.getSlots();
  }

  @Override
  public boolean isVisible() {
    return this.delegate.isVisible();
  }

  @Override
  public boolean hasCosmetic() {
    return this.delegate.hasCosmetic();
  }

  @Override
  public CompoundTag serializeNBT() {
    return this.delegate.serializeNBT();
  }

  @Override
  public void deserializeNBT(CompoundTag nbt) {
    this.delegate.deserializeNBT(nbt);
  }

  @Override
  public String getIdentifier() {
    return this.delegate.getIdentifier();
  }

  @Override
  public Map<ResourceLocation, AttributeModifier> getModifiers() {
    return this.delegate.getModifiers();
  }

  @Override
  public Set<AttributeModifier> getPermanentModifiers() {
    return this.delegate.getPermanentModifiers();
  }

  @Override
  public Set<AttributeModifier> getCachedModifiers() {
    return this.delegate.getCachedModifiers();
  }

  @Override
  public Collection<AttributeModifier> getModifiersByOperation(AttributeModifier.Operation operation) {
    return this.delegate.getModifiersByOperation(operation);
  }

  @Override
  public void addTransientModifier(AttributeModifier modifier) {
    this.delegate.addTransientModifier(modifier);
  }

  @Override
  public void addPermanentModifier(AttributeModifier modifier) {
    this.delegate.addPermanentModifier(modifier);
  }

  @Override
  public void removeModifier(ResourceLocation id) {
    this.delegate.removeModifier(id);
  }

  @Override
  public void clearModifiers() {
    this.delegate.clearModifiers();
  }

  @Override
  public void clearCachedModifiers() {
    this.delegate.clearCachedModifiers();
  }

  @Override
  public void copyModifiers(top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler other) {
    if (other instanceof ShimCurioStacksHandler shim) {
      this.delegate.copyModifiers(shim.delegate);
    }
  }

  @Override
  public void update() {
    this.delegate.update();
  }

  @Override
  public CompoundTag getSyncTag() {
    return this.delegate.getSyncTag();
  }

  @Override
  public void applySyncTag(CompoundTag tag) {
    this.delegate.applySyncTag(tag);
  }

  @Override
  public int getSizeShift() {
    return this.delegate.getSizeShift();
  }

  @Override
  public void grow(int amount) {
    this.delegate.grow(amount);
  }

  @Override
  public void shrink(int amount) {
    this.delegate.shrink(amount);
  }
}
