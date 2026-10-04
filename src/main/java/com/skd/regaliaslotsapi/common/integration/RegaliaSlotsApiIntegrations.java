package com.skd.regaliaslotsapi.common.integration;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import com.skd.regaliaslotsapi.common.integration.emi.RegaliaSlotsApiEmiIntegration;

public class RegaliaSlotsApiIntegrations {

  public static void setup(IEventBus eventBus) {

    if (ModList.get().isLoaded("emi")) {
      RegaliaSlotsApiEmiIntegration.setup(eventBus);
    }
  }
}
