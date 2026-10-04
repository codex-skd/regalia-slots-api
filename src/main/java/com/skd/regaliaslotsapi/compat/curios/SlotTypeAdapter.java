package com.skd.regaliaslotsapi.compat.curios;

import java.util.Set;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import com.skd.regaliaslotsapi.api.type.ISlotType;

/**
 * Wraps a Regalia Slots API {@link ISlotType} so it can be handed to code compiled against
 * the real Curios {@code top.theillusivec4.curios.api.type.ISlotType}. Backed live by the
 * wrapped instance - no data is copied.
 */
public class SlotTypeAdapter implements top.theillusivec4.curios.api.type.ISlotType {

  private final ISlotType delegate;

  public SlotTypeAdapter(ISlotType delegate) {
    this.delegate = delegate;
  }

  public ISlotType delegate() {
    return this.delegate;
  }

  @Override
  public Identifier getIcon() {
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
  public top.theillusivec4.curios.api.common.DropRule getDropRule() {
    return RegaliaTypeBridge.toCurios(this.delegate.getDropRule());
  }

  @Override
  public Set<Identifier> getValidators() {
    return this.delegate.getValidators();
  }

  @Override
  public Set<EntityType<?>> getDefaultEntityTypes() {
    return this.delegate.getDefaultEntityTypes();
  }

  @Override
  public String getIdentifier() {
    return this.delegate.getIdentifier();
  }
}
