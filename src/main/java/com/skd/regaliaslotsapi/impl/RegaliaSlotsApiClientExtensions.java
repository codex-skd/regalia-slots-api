package com.skd.regaliaslotsapi.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import com.skd.regaliaslotsapi.api.client.ICurioRenderer;
import com.skd.regaliaslotsapi.api.internal.services.client.IRegaliaSlotsApiClientExtensions;

public class RegaliaSlotsApiClientExtensions implements IRegaliaSlotsApiClientExtensions {

  private static final Map<Item, Supplier<ICurioRenderer>> REGISTERED_RENDERERS =
      new ConcurrentHashMap<>();
  private static final Map<Item, ICurioRenderer> LOADED_RENDERERS = new LinkedHashMap<>();

  @Override
  public void registerCurioRenderer(Item item, Supplier<ICurioRenderer> curioRenderer) {
    REGISTERED_RENDERERS.put(item, curioRenderer);
  }

  @Override
  public ICurioRenderer getCurioRenderer(Item item) {
    return LOADED_RENDERERS.get(item);
  }

  public static void loadRenderers() {

    for (Map.Entry<Item, Supplier<ICurioRenderer>> entry : REGISTERED_RENDERERS.entrySet()) {
      LOADED_RENDERERS.put(entry.getKey(), entry.getValue().get());
    }
  }
}
