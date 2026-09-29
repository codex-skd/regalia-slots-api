/*
 * Copyright (c) 2018-2024 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package top.theillusivec4.curios.client.gui;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import top.theillusivec4.curios.api.client.ICuriosScreen;

/**
 * Binary-compatibility shim so third-party mods (e.g. Apothic Attributes) that reference Curios'
 * client screen can be class-loaded without a {@code NoClassDefFoundError}. This class exists
 * solely to satisfy reflection-driven type resolution at mod-loading time.
 *
 * <p>Regalia Slots API's real Curios GUI is
 * {@link com.skd.regaliaslotsapi.client.gui.RegaliaSlotsApiScreen}, which does <em>not</em> extend
 * this type. As a result, mods that perform an {@code instanceof CuriosScreen} check on the active
 * screen will always get {@code false} and their Curios-screen integration is silently skipped — a
 * known, accepted compatibility gap consistent with the other gaps documented in the workflow.</p>
 */
public abstract class CuriosScreen
    extends AbstractContainerScreen<AbstractContainerMenu>
    implements ICuriosScreen {

  protected CuriosScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
  }
}
