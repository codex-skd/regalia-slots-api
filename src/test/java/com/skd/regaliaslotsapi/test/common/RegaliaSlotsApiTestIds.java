package com.skd.regaliaslotsapi.test.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import com.skd.regaliaslotsapi.test.RegaliaSlotsApiTest;

public final class RegaliaSlotsApiTestIds {

  public static final ResourceKey<Item> KNUCKLES = createKey("knuckles");
  public static final ResourceKey<Item> AMULET = createKey("amulet");
  public static final ResourceKey<Item> RING = createKey("ring");
  public static final ResourceKey<Item> CROWN = createKey("crown");

  private static ResourceKey<Item> createKey(String path) {
    return ResourceKey.create(Registries.ITEM,
        Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, path));
  }
}
