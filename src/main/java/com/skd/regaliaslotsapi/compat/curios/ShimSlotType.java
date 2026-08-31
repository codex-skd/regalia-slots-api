package com.skd.regaliaslotsapi.compat.curios;

import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

/**
 * Adapts a Regalia {@link com.skd.regaliaslotsapi.api.type.ISlotType} to the verbatim
 * {@code top.theillusivec4.curios.api.type.ISlotType} that third-party mods compiled against the
 * real Curios API expect. The two interfaces are byte-identical copies of the same upstream
 * source (only the package and the nested {@code ICurio.DropRule} type differ), so every call
 * forwards directly; {@link #getDropRule()} maps between the two enums by name.
 */
final class ShimSlotType implements top.theillusivec4.curios.api.type.ISlotType {

  private final com.skd.regaliaslotsapi.api.type.ISlotType delegate;

  ShimSlotType(com.skd.regaliaslotsapi.api.type.ISlotType delegate) {
    this.delegate = delegate;
  }

  @Override
  public String getIdentifier() {
    return this.delegate.getIdentifier();
  }

  @Override
  public ResourceLocation getIcon() {
    return this.delegate.getIcon();
  }

  @Override
  public int getOrder() {
    return this.delegate.getOrder();
  }

  @Override
  public int getSize() {
    return this.delegate.getSize();
  }

  @Override
  public boolean useNativeGui() {
    return this.delegate.useNativeGui();
  }

  @Override
  public boolean hasCosmetic() {
    return this.delegate.hasCosmetic();
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
  public Set<ResourceLocation> getValidators() {
    return this.delegate.getValidators();
  }

  @Override
  public CompoundTag writeNbt() {
    return this.delegate.writeNbt();
  }

  @Override
  public int compareTo(top.theillusivec4.curios.api.type.ISlotType other) {
    int byOrder = Integer.compare(this.getOrder(), other.getOrder());
    return byOrder != 0 ? byOrder : this.getIdentifier().compareTo(other.getIdentifier());
  }
}
