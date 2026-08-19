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

package com.skd.regaliaslotsapi.common.network.client;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
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

public class RegaliaSlotsApiClientPayloadHandler {

  private static final RegaliaSlotsApiClientPayloadHandler INSTANCE = new RegaliaSlotsApiClientPayloadHandler();

  public static RegaliaSlotsApiClientPayloadHandler getInstance() {
    return INSTANCE;
  }

  private static void handle(final IPayloadContext ctx, Runnable handler) {
    ctx.enqueueWork(handler)
        .exceptionally(e -> {
          ctx.disconnect(Component.translatable("curios.networking.failed"));
          return null;
        });
  }

  public void handle(final SPacketQuickMove data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketPage data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketBreak data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketSyncRender data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketSyncModifiers data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketSyncData data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketSyncActiveState data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketSyncRegaliaSlotsApi data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketGrabbedItem data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }

  public void handle(final SPacketSyncStack data, final IPayloadContext ctx) {
    handle(ctx, () -> RegaliaSlotsApiClientPackets.handle(data));
  }
}
