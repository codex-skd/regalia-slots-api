package com.skd.regaliaslotsapi.common.capability;

import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.transfer.CombinedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import com.skd.regaliaslotsapi.api.common.inventory.RegaliaSlotsApiResourceHandler;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;
import com.skd.regaliaslotsapi.impl.RegaliaSlotsApiRegistry;

public class CombinedRegaliaSlotsApiResourceHandler extends CombinedResourceHandler<ItemResource> {

  public static CombinedRegaliaSlotsApiResourceHandler from(final LivingEntity livingEntity) {
    CurioInventory inv = livingEntity.getData(RegaliaSlotsApiRegistry.INVENTORY.get());
    Map<String, ICurioStacksHandler> curios = inv.curios;
    RegaliaSlotsApiResourceHandler[] wrappers = new RegaliaSlotsApiResourceHandler[curios.size()];
    int index = 0;

    for (ICurioStacksHandler stacksHandler : curios.values()) {

      if (index < wrappers.length) {
        wrappers[index] = new RegaliaSlotsApiResourceHandler(stacksHandler.getStacks());
        index++;
      }
    }
    return new CombinedRegaliaSlotsApiResourceHandler(wrappers);
  }

  @SafeVarargs
  public CombinedRegaliaSlotsApiResourceHandler(ResourceHandler<ItemResource>... handlers) {
    super(handlers);
  }
}
