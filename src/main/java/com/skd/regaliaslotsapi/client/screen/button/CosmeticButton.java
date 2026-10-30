/*
 * Copyright (c) 2018-2024 C4
 *
 * This file is part of RegaliaSlotsApi, a mod made for Minecraft.
 *
 * RegaliaSlotsApi is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RegaliaSlotsApi is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with RegaliaSlotsApi.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.skd.regaliaslotsapi.client.screen.button;

import javax.annotation.Nonnull;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.client.screen.RegaliaSlotsApiScreen;
import com.skd.regaliaslotsapi.common.network.client.CPacketToggleCosmetics;

public class CosmeticButton extends ImageButton implements IRegaliaSlotsApiWidget {

  public static final WidgetSprites OFF =
      new WidgetSprites(RegaliaSlotsApiResources.resource("cosmetic_off"),
                        RegaliaSlotsApiResources.resource("cosmetic_off_highlighted"));
  public static final WidgetSprites ON =
      new WidgetSprites(RegaliaSlotsApiResources.resource("cosmetic_on"),
                        RegaliaSlotsApiResources.resource("cosmetic_on_highlighted"));

  private final RegaliaSlotsApiScreen parentGui;

  public CosmeticButton(RegaliaSlotsApiScreen parentGui, int xIn, int yIn, int widthIn, int heightIn) {
    super(xIn, yIn, widthIn, heightIn, OFF,
          (button) -> {
            parentGui.getMenu().toggleCosmetics();
            ClientPacketDistributor.sendToServer(
                new CPacketToggleCosmetics(parentGui.getMenu().containerId));
          });
    this.parentGui = parentGui;
    this.setTooltip(Tooltip.create(Component.translatable("gui.curios.toggle.cosmetics")));
  }

  @Override
  public void extractContents(@Nonnull GuiGraphicsExtractor guiGraphics, int x, int y,
                              float partialTicks) {
    WidgetSprites sprites1;

    if (this.parentGui.getMenu().isViewingCosmetics) {
      sprites1 = ON;
    } else {
      sprites1 = OFF;
    }
    this.setX(this.parentGui.getGuiLeft() - 27);
    this.setY(this.parentGui.getGuiTop() - 18);
    Identifier resourcelocation = sprites1.get(this.isActive(), this.isHoveredOrFocused());
    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, resourcelocation, this.getX(), this.getY(),
                           this.width, this.height);
  }
}
