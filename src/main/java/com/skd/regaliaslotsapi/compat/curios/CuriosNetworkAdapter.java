package com.skd.regaliaslotsapi.compat.curios;

import com.skd.regaliaslotsapi.api.internal.RegaliaSlotsApiServices;

public class CuriosNetworkAdapter
    implements top.theillusivec4.curios.api.internal.services.ICuriosNetwork {

  @Override
  public void breakCurioInSlot(top.theillusivec4.curios.api.SlotContext slotContext) {
    RegaliaSlotsApiServices.NETWORK.breakCurioInSlot(CuriosTypeBridge.toRegalia(slotContext));
  }
}
