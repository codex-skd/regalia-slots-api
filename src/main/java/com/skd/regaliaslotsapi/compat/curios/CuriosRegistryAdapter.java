package com.skd.regaliaslotsapi.compat.curios;

import net.minecraft.core.component.DataComponentType;

public class CuriosRegistryAdapter
    implements top.theillusivec4.curios.api.internal.services.ICuriosRegistry {

  @Override
  public DataComponentType<top.theillusivec4.curios.api.CurioAttributeModifiers> getAttributeModifierComponent() {
    return CuriosCompatMod.ATTRIBUTE_MODIFIERS.get();
  }
}
