package com.skd.regaliaslotsapi.api.internal.services;

import javax.annotation.Nullable;
import net.minecraft.world.item.Item;
import com.skd.regaliaslotsapi.api.extensions.ICurioSlotExtension;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;

public interface IRegaliaSlotsApiExtensions {

  void registerCurioItem(ICurioItem curio, Item... item);

  @Nullable
  ICurioItem getCurioItem(Item item);

  void registerSlotExtension(ICurioSlotExtension slotExtension, String... id);

  @Nullable
  ICurioSlotExtension getSlotExtension(String id);
}
