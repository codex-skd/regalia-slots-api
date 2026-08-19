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

package com.skd.regaliaslotsapi.common.network;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import com.skd.regaliaslotsapi.common.network.client.CPacketDestroy;
import com.skd.regaliaslotsapi.common.network.client.CPacketOpenRegaliaSlotsApi;
import com.skd.regaliaslotsapi.common.network.client.CPacketOpenVanilla;
import com.skd.regaliaslotsapi.common.network.client.CPacketPage;
import com.skd.regaliaslotsapi.common.network.client.CPacketToggleCosmetics;
import com.skd.regaliaslotsapi.common.network.client.CPacketToggleRender;
import com.skd.regaliaslotsapi.common.network.client.RegaliaSlotsApiClientPayloadHandler;
import com.skd.regaliaslotsapi.common.network.server.RegaliaSlotsApiServerPayloadHandler;
import com.skd.regaliaslotsapi.common.network.server.SPacketBreak;
import com.skd.regaliaslotsapi.common.network.server.SPacketGrabbedItem;
import com.skd.regaliaslotsapi.common.network.server.SPacketPage;
import com.skd.regaliaslotsapi.common.network.server.SPacketQuickMove;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncActiveState;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncRegaliaSlotsApi;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncData;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncModifiers;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncRender;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncStack;

public class NetworkHandler {

  public static void register(final PayloadRegistrar registrar) {
    //Client Packets
    registrar.playToServer(CPacketDestroy.TYPE, CPacketDestroy.STREAM_CODEC,
        RegaliaSlotsApiServerPayloadHandler.getInstance()::handleDestroyPacket);
    registrar.playToServer(CPacketOpenRegaliaSlotsApi.TYPE, CPacketOpenRegaliaSlotsApi.STREAM_CODEC,
        RegaliaSlotsApiServerPayloadHandler.getInstance()::handleOpenRegaliaSlotsApi);
    registrar.playToServer(CPacketOpenVanilla.TYPE, CPacketOpenVanilla.STREAM_CODEC,
        RegaliaSlotsApiServerPayloadHandler.getInstance()::handleOpenVanilla);
    registrar.playToServer(CPacketPage.TYPE, CPacketPage.STREAM_CODEC,
        RegaliaSlotsApiServerPayloadHandler.getInstance()::handlePage);
    registrar.playToServer(CPacketToggleRender.TYPE, CPacketToggleRender.STREAM_CODEC,
        RegaliaSlotsApiServerPayloadHandler.getInstance()::handlerToggleRender);
    registrar.playToServer(CPacketToggleCosmetics.TYPE, CPacketToggleCosmetics.STREAM_CODEC,
        RegaliaSlotsApiServerPayloadHandler.getInstance()::handlerToggleCosmetics);

    // Server Packets
    registrar.playToClient(SPacketSyncStack.TYPE, SPacketSyncStack.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketGrabbedItem.TYPE, SPacketGrabbedItem.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketSyncRegaliaSlotsApi.TYPE, SPacketSyncRegaliaSlotsApi.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketSyncData.TYPE, SPacketSyncData.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketSyncModifiers.TYPE, SPacketSyncModifiers.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketSyncRender.TYPE, SPacketSyncRender.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketSyncActiveState.TYPE, SPacketSyncActiveState.STREAM_CODEC,
                           RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketBreak.TYPE, SPacketBreak.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketPage.TYPE, SPacketPage.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
    registrar.playToClient(SPacketQuickMove.TYPE, SPacketQuickMove.STREAM_CODEC,
        RegaliaSlotsApiClientPayloadHandler.getInstance()::handle);
  }
}
