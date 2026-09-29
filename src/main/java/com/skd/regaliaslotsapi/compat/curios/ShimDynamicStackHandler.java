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
 * the Curios API {@code IDynamicStackHandler} interface. Delegates to the
 * parallel Regalia implementation via the wrapped delegate.
 */

package com.skd.regaliaslotsapi.compat.curios;

import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public final class ShimDynamicStackHandler implements top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler {

  private final com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler delegate;

  public ShimDynamicStackHandler(com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler delegate) {
    this.delegate = delegate;
  }

  public com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler unwrap() {
    return this.delegate;
  }

  @Override
  public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
    this.delegate.setStackInSlot(slot, stack);
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    return this.delegate.getStackInSlot(slot);
  }

  @Override
  public void setPreviousStackInSlot(int slot, @Nonnull ItemStack stack) {
    this.delegate.setPreviousStackInSlot(slot, stack);
  }

  @Override
  public ItemStack getPreviousStackInSlot(int slot) {
    return this.delegate.getPreviousStackInSlot(slot);
  }

  @Override
  public int getSlots() {
    return this.delegate.getSlots();
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    return this.delegate.insertItem(slot, stack, simulate);
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    return this.delegate.extractItem(slot, amount, simulate);
  }

  @Override
  public void grow(int amount) {
    this.delegate.grow(amount);
  }

  @Override
  public void shrink(int amount) {
    this.delegate.shrink(amount);
  }

  @Override
  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    return this.delegate.serializeNBT(provider);
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
    this.delegate.deserializeNBT(provider, nbt);
  }

  @Override
  public int getSlotLimit(int slot) {
    return this.delegate.getSlotLimit(slot);
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return this.delegate.isItemValid(slot, stack);
  }
}
