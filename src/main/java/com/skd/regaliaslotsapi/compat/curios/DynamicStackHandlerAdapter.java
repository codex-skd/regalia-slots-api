package com.skd.regaliaslotsapi.compat.curios;

import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;

/**
 * Wraps a Regalia {@link IDynamicStackHandler} as the copied Curios
 * {@code top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler}. Reads/writes go
 * straight through to the wrapped handler - this holds no state of its own.
 */
public class DynamicStackHandlerAdapter
    implements top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler {

  private final IDynamicStackHandler delegate;

  public DynamicStackHandlerAdapter(IDynamicStackHandler delegate) {
    this.delegate = delegate;
  }

  public IDynamicStackHandler delegate() {
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

  @Override
  public void grow(int amount) {
    this.delegate.grow(amount);
  }

  @Override
  public void shrink(int amount) {
    this.delegate.shrink(amount);
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
  public int getSlotLimit(int slot) {
    return this.delegate.getSlotLimit(slot);
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return this.delegate.isItemValid(slot, stack);
  }

  @Override
  public void serialize(@Nonnull ValueOutput output) {
    this.delegate.serialize(output);
  }

  @Override
  public void deserialize(@Nonnull ValueInput input) {
    this.delegate.deserialize(input);
  }

  @Override
  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    return this.delegate.serializeNBT(provider);
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
    this.delegate.deserializeNBT(provider, nbt);
  }
}
