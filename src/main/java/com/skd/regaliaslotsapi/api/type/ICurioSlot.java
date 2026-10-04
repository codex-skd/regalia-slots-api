package com.skd.regaliaslotsapi.api.type;

import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.extensions.ICurioSlotExtension;

/**
 * Representation of a curio slot in menus and user interfaces
 */
public interface ICurioSlot {

  default String getId() {
    return this.getSlotContext().identifier();
  }

  ICurioSlotExtension getSlotExtension();

  SlotContext getSlotContext();
}
