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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import com.skd.regaliaslotsapi.RegaliaSlotsApiConstants;
import com.skd.regaliaslotsapi.client.screen.RegaliaSlotsApiScreen;
import com.skd.regaliaslotsapi.common.network.client.CPacketPage;

public class PageButton extends Button implements IRegaliaSlotsApiWidget {

  private final RegaliaSlotsApiScreen parentGui;
  private final Type type;
  private static final Identifier CURIO_INVENTORY =
      Identifier.fromNamespaceAndPath(RegaliaSlotsApiConstants.MOD_ID,
                                      "textures/gui/curios/inventory.png");

  public PageButton(RegaliaSlotsApiScreen parentGui, int xIn, int yIn, int widthIn, int heightIn,
                    Type type) {
    super(xIn, yIn, widthIn, heightIn, CommonComponents.EMPTY,
          (button) -> ClientPacketDistributor.sendToServer(
              new CPacketPage(parentGui.getMenu().containerId, type == Type.NEXT)),
          DEFAULT_NARRATION);
    this.parentGui = parentGui;
    this.type = type;
  }

  @Override
  public void extractContents(@Nonnull GuiGraphicsExtractor guiGraphics, int x, int y,
                              float partialTicks) {
    int xText = type == Type.NEXT ? 43 : 32;
    int yText = 25;

    if (type == Type.NEXT) {
      this.setX(this.parentGui.getGuiLeft() - 17);
      this.active = this.parentGui.getMenu().currentPage + 1 < this.parentGui.getMenu().totalPages;
    } else {
      this.setX(this.parentGui.getGuiLeft() - 28);
      this.active = this.parentGui.getMenu().currentPage > 0;
    }

    if (!this.isActive()) {
      yText += 12;
    } else if (this.isHoveredOrFocused()) {
      xText += 22;
    }

    if (this.isHovered()) {
      List<ClientTooltipComponent> tooltip = new ArrayList<>();
      int currentPage = this.parentGui.getMenu().currentPage + 1;
      int totalPages = this.parentGui.getMenu().totalPages;
      tooltip.add(
          ClientTooltipComponent.create(
              Component.translatable("gui.curios.page", currentPage, totalPages)
                  .getVisualOrderText()));
      guiGraphics.tooltip(Minecraft.getInstance().font, tooltip, x, y,
                          DefaultTooltipPositioner.INSTANCE, null);
    }
    guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CURIO_INVENTORY, this.getX(), this.getY(), xText,
                     yText, this.width, this.height, 256, 256);
  }

  public enum Type {
    NEXT,
    PREVIOUS
  }
}
