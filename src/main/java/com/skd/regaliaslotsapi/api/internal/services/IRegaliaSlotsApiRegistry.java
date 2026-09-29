package com.skd.regaliaslotsapi.api.internal.services;

import net.minecraft.core.component.DataComponentType;
import com.skd.regaliaslotsapi.api.CurioAttributeModifiers;

public interface IRegaliaSlotsApiRegistry {

  DataComponentType<CurioAttributeModifiers> getAttributeModifierComponent();
}
