package com.skd.regaliaslotsapi.compat.curios;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;

/**
 * Wraps a Regalia {@link ICurioStacksHandler} as the copied Curios
 * {@code top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler}. Backed live by the
 * wrapped instance - modifiers, stacks and render/active state are shared, not copied.
 */
public class StacksHandlerAdapter
    implements top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler {

  private final ICurioStacksHandler delegate;

  public StacksHandlerAdapter(ICurioStacksHandler delegate) {
    this.delegate = delegate;
  }

  public ICurioStacksHandler delegate() {
    return this.delegate;
  }

  @Override
  public top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler getStacks() {
    return RegaliaTypeBridge.wrap(this.delegate.getStacks());
  }

  @Override
  public top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler getCosmeticStacks() {
    return RegaliaTypeBridge.wrap(this.delegate.getCosmeticStacks());
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
  public top.theillusivec4.curios.api.common.DropRule getDropRule() {
    return RegaliaTypeBridge.toCurios(this.delegate.getDropRule());
  }

  @Override
  public int getSlots() {
    return this.delegate.getSlots();
  }

  @Override
  public int getBaseSize() {
    return this.delegate.getBaseSize();
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
  public String getIdentifier() {
    return this.delegate.getIdentifier();
  }

  @Override
  public Map<Identifier, AttributeModifier> getModifiers() {
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
  public Collection<AttributeModifier> getModifiersByOperation(
      AttributeModifier.Operation operation) {
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
  public void removeModifier(Identifier id) {
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
    if (other instanceof StacksHandlerAdapter adapter) {
      this.delegate.copyModifiers(adapter.delegate);
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
  public CompoundTag serializeNBT() {
    return this.delegate.serializeNBT();
  }

  @Override
  public void deserializeNBT(CompoundTag nbt) {
    this.delegate.deserializeNBT(nbt);
  }

  @Override
  public void serialize(@Nonnull ValueOutput output) {
    this.delegate.serialize(output);
  }

  @Override
  public void deserialize(@Nonnull ValueInput input) {
    this.delegate.deserialize(input);
  }
}
