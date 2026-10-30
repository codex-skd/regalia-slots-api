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

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.client.screen.RegaliaSlotsApiScreen;
import com.skd.regaliaslotsapi.common.network.client.CPacketOpenRegaliaSlotsApi;
import com.skd.regaliaslotsapi.common.network.client.CPacketOpenVanilla;

public class RegaliaSlotsApiButton extends ImageButton {

  public static final WidgetSprites BIG =
      new WidgetSprites(RegaliaSlotsApiResources.resource("button"),
                        RegaliaSlotsApiResources.resource("button_highlighted"));
  public static final WidgetSprites SMALL =
      new WidgetSprites(RegaliaSlotsApiResources.resource("button_small"),
                        RegaliaSlotsApiResources.resource("button_small_highlighted"));
  private final AbstractContainerScreen<?> parentGui;

  public RegaliaSlotsApiButton(AbstractContainerScreen<?> parentGui, int xIn, int yIn, int widthIn,
                      int heightIn,
                      WidgetSprites sprites) {
    super(xIn, yIn, widthIn, heightIn, sprites,
          (button) -> {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player != null) {
              ItemStack stack = mc.player.containerMenu.getCarried();
              mc.player.containerMenu.setCarried(ItemStack.EMPTY);

              if (parentGui instanceof RegaliaSlotsApiScreen curiosScreen) {
                mc.mouseHandler.mouseGrabbed = true;
                mc.player.clientSideCloseContainer();
                InventoryScreen inventoryScreen = new InventoryScreen(mc.player);
                mc.mouseHandler.mouseGrabbed = false;
                mc.gui.setScreen(inventoryScreen);
                inventoryScreen.xMouse = curiosScreen.oldMouseX;
                inventoryScreen.yMouse = curiosScreen.oldMouseY;
                mc.player.inventoryMenu.setCarried(stack);
                ClientPacketDistributor.sendToServer(new CPacketOpenVanilla(stack));
              } else {

                if (parentGui instanceof InventoryScreen inventory) {
                  RecipeBookComponent<?> recipeBookGui = inventory.recipeBookComponent;

                  if (recipeBookGui.isVisible()) {
                    recipeBookGui.toggleVisibility();
                  }
                }
                ClientPacketDistributor.sendToServer(new CPacketOpenRegaliaSlotsApi(stack));
              }
            }
          });
    this.parentGui = parentGui;
  }

  @Override
  public void extractContents(@Nonnull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY,
                           float partialTicks) {
    Pair<Integer, Integer> offsets =
        RegaliaSlotsApiScreen.getButtonOffset(parentGui instanceof CreativeModeInventoryScreen);
    this.setX(parentGui.getLeftPos() + offsets.getFirst() + 2);
    int yOffset = parentGui instanceof CreativeModeInventoryScreen ? 70 : 85;
    this.setY(parentGui.getTopPos() + offsets.getSecond() + yOffset);

    if (parentGui instanceof CreativeModeInventoryScreen gui) {
      boolean isInventoryTab = gui.isInventoryOpen();
      this.active = isInventoryTab;

      if (!isInventoryTab) {
        return;
      }
    }
    super.extractContents(guiGraphics, mouseX, mouseY, partialTicks);
  }
}
