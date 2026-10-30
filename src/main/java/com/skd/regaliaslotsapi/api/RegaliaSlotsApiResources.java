package com.skd.regaliaslotsapi.api;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegaliaSlotsApiResources {

  public static final String MOD_ID = "regalia_slots_api";
  public static final String MOD_NAME = "RegaliaSlotsApi API";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

  public static Identifier resource(String path) {
    return Identifier.fromNamespaceAndPath(MOD_ID, path);
  }
}
