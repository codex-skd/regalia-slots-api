package com.skd.regaliaslotsapi.common.integration.emi;

import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.config.SidebarSide;
import dev.emi.emi.config.SidebarTheme;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import com.skd.regaliaslotsapi.client.gui.RegaliaSlotsApiScreen;

public class RegaliaSlotsApiEmiIntegration {

  public static void setup(IEventBus eventBus) {
    NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, RegaliaSlotsApiEmiIntegration::renderCuriosBg);
  }

  public static void renderCuriosBg(final ContainerScreenEvent.Render.Background evt) {

    if (evt.getContainerScreen() instanceof RegaliaSlotsApiScreen curiosScreen
        && EmiConfig.leftSidebarTheme != SidebarTheme.TRANSPARENT) {
      EmiScreenManager.SidebarPanel panel = EmiScreenManager.getPanelFor(SidebarSide.LEFT);

      if (panel != null && panel.isVisible()) {
        Bounds bounds = panel.getBounds();
        int emiRight = bounds.right();
        int curiosLeft = curiosScreen.getGuiLeft() - curiosScreen.panelWidth;

        if (emiRight > curiosLeft) {
          Minecraft mc = Minecraft.getInstance();
          curiosScreen.renderBg(evt.getGuiGraphics(),
                                mc.getTimer().getGameTimeDeltaPartialTick(false), evt.getMouseX(),
                                evt.getMouseY());
        }
      }
    }
  }
}
