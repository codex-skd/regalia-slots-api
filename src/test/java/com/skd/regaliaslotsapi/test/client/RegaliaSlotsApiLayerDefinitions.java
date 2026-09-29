package com.skd.regaliaslotsapi.test.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import com.skd.regaliaslotsapi.test.RegaliaSlotsApiTest;

public class RegaliaSlotsApiLayerDefinitions {

  public static final ModelLayerLocation CROWN =
      new ModelLayerLocation(Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "crown"),
          "crown");
  public static final ModelLayerLocation KNUCKLES =
      new ModelLayerLocation(Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "knuckles"),
          "knuckles");
  public static final ModelLayerLocation AMULET =
      new ModelLayerLocation(Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "amulet"),
          "amulet");

}
