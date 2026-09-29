package com.skd.regaliaslotsapi.common.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.widget.Bounds;
import net.minecraft.client.renderer.Rect2i;
import com.skd.regaliaslotsapi.client.gui.RegaliaSlotsApiScreen;
import com.skd.regaliaslotsapi.common.integration.RegaliaSlotsApiExclusionAreas;

@EmiEntrypoint
public class RegaliaSlotsApiEmiPlugin implements EmiPlugin {

  @Override
  public void register(EmiRegistry registry) {
    registry.addExclusionArea(RegaliaSlotsApiScreen.class, (screen, consumer) -> {
      for (Rect2i rect2i : RegaliaSlotsApiExclusionAreas.create(screen)) {
        consumer.accept(new Bounds(rect2i.getX(), rect2i.getY(), rect2i.getWidth(),
                                   rect2i.getHeight()));
      }
    });
  }
}
