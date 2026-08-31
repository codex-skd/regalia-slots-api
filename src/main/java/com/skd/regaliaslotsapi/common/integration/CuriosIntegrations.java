package com.skd.regaliaslotsapi.common.integration;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import com.skd.regaliaslotsapi.common.integration.emi.CuriosEmiIntegration;

public class CuriosIntegrations {

  public static void setup(IEventBus eventBus) {

    if (ModList.get().isLoaded("emi")) {
      CuriosEmiIntegration.setup(eventBus);
    }
  }
}
