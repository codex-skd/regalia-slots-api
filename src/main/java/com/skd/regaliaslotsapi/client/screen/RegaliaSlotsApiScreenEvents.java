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

package com.skd.regaliaslotsapi.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.Slot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.lwjgl.glfw.GLFW;
import com.skd.regaliaslotsapi.client.screen.button.RegaliaSlotsApiButton;
import com.skd.regaliaslotsapi.common.network.client.CPacketDestroy;
import com.skd.regaliaslotsapi.config.RegaliaSlotsApiClientConfig;

public class RegaliaSlotsApiScreenEvents {

  @SubscribeEvent
  public void postScreenInit(final ScreenEvent.Init.Post evt) {
    Screen screen = evt.getScreen();

    if (!RegaliaSlotsApiClientConfig.CLIENT.enableButton.get()) {
      return;
    }

    if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
      AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
      boolean isCreative = screen instanceof CreativeModeInventoryScreen;
      Pair<Integer, Integer> offsets = RegaliaSlotsApiScreen.getButtonOffset(isCreative);
      int x = offsets.getFirst();
      int y = offsets.getSecond();
      int size = isCreative ? 8 : 10;
      int yOffset = isCreative ? 67 : 81;
      evt.addListener(
          new RegaliaSlotsApiButton(gui, gui.getLeftPos() + x - 2, gui.getTopPos() + y + yOffset, size, size,
                           isCreative ? RegaliaSlotsApiButton.SMALL : RegaliaSlotsApiButton.BIG));
    }
  }

  @SubscribeEvent
  public void preMouseClick(final ScreenEvent.MouseButtonPressed.Pre evt) {
    Window window = Minecraft.getInstance().getWindow();
    boolean isLeftShiftDown = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT);
    boolean isRightShiftDown = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    boolean isShiftDown = isLeftShiftDown || isRightShiftDown;

    if (!(evt.getScreen() instanceof CreativeModeInventoryScreen gui) || !isShiftDown
        || !gui.isInventoryOpen()) {
      return;
    }
    Slot destroyItemSlot = gui.destroyItemSlot;
    Slot slot = gui.getHoveredSlot();

    if (destroyItemSlot != null && slot == destroyItemSlot) {
      ClientPacketDistributor.sendToServer(new CPacketDestroy());
    }
  }
}