package com.skd.regaliaslotsapi.api.internal.services.client;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.ApiStatus;
import com.skd.regaliaslotsapi.api.client.ICurioRenderer;

@ApiStatus.Internal
public interface IRegaliaSlotsApiClientExtensions {

  void registerCurioRenderer(Item item, Supplier<ICurioRenderer> curioRenderer);

  ICurioRenderer getCurioRenderer(Item item);
}
