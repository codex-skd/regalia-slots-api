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

package com.skd.regaliaslotsapi.common.network.server;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.SlotAttribute;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;
import com.skd.regaliaslotsapi.common.inventory.container.RegaliaSlotsApiMenu;
import com.skd.regaliaslotsapi.common.inventory.container.RegaliaSlotsApiMenuProvider;
import com.skd.regaliaslotsapi.common.network.client.CPacketDestroy;
import com.skd.regaliaslotsapi.common.network.client.CPacketOpenRegaliaSlotsApi;
import com.skd.regaliaslotsapi.common.network.client.CPacketOpenVanilla;
import com.skd.regaliaslotsapi.common.network.client.CPacketPage;
import com.skd.regaliaslotsapi.common.network.client.CPacketToggleCosmetics;
import com.skd.regaliaslotsapi.common.network.client.CPacketToggleRender;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncRender;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncStack;

public class RegaliaSlotsApiServerPayloadHandler {

  private static final RegaliaSlotsApiServerPayloadHandler INSTANCE = new RegaliaSlotsApiServerPayloadHandler();

  public static RegaliaSlotsApiServerPayloadHandler getInstance() {
    return INSTANCE;
  }

  public void handlerToggleRender(final CPacketToggleRender data, final IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      Player player = ctx.player();
      RegaliaSlotsApi.getRegaliaSlotsApiInventory(player)
          .flatMap(handler -> handler.getStacksHandler(data.identifier()))
          .ifPresent(stacksHandler -> {
            NonNullList<Boolean> renderStatuses = stacksHandler.getRenders();

            if (renderStatuses.size() > data.index()) {
              boolean value = !renderStatuses.get(data.index());
              renderStatuses.set(data.index(), value);
              PacketDistributor.sendToPlayersTrackingEntityAndSelf(player,
                                                                   new SPacketSyncRender(
                                                                       player.getId(),
                                                                       data.identifier(),
                                                                       data.index(), value));
            }
          });
    });
  }

  public void handlePage(final CPacketPage data,
                         final IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      Player player = ctx.player();
      AbstractContainerMenu container = player.containerMenu;

      if (container instanceof RegaliaSlotsApiMenu && container.containerId == data.windowId()) {

        if (data.next()) {
          ((RegaliaSlotsApiMenu) container).nextPage();
        } else {
          ((RegaliaSlotsApiMenu) container).prevPage();
        }
      }
    });
  }

  public void handlerToggleCosmetics(final CPacketToggleCosmetics data,
                                     final IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      Player player = ctx.player();
      AbstractContainerMenu container = player.containerMenu;

      if (container instanceof RegaliaSlotsApiMenu && container.containerId == data.windowId()) {
        ((RegaliaSlotsApiMenu) container).toggleCosmetics();
      }
    });
  }

  public void handleOpenVanilla(final CPacketOpenVanilla data, final IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      Player player = ctx.player();

      if (player instanceof ServerPlayer serverPlayer) {
        ItemStack stack =
            player.isCreative() ? data.carried() : player.containerMenu.getCarried();
        player.containerMenu.setCarried(ItemStack.EMPTY);
        serverPlayer.doCloseContainer();

        if (!stack.isEmpty()) {
          player.inventoryMenu.setCarried(stack);
          PacketDistributor.sendToPlayer(serverPlayer, new SPacketGrabbedItem(stack));
        }
      }
    });
  }

  public void handleOpenRegaliaSlotsApi(final CPacketOpenRegaliaSlotsApi data, final IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      Player player = ctx.player();

      if (player instanceof ServerPlayer serverPlayer) {
        ItemStack stack =
            player.isCreative() ? data.carried() : player.containerMenu.getCarried();
        player.containerMenu.setCarried(ItemStack.EMPTY);
        player.openMenu(new RegaliaSlotsApiMenuProvider());

        if (!stack.isEmpty()) {
          player.containerMenu.setCarried(stack);
          PacketDistributor.sendToPlayer(serverPlayer, new SPacketGrabbedItem(stack));
        }
      }
    });
  }

  public void handleDestroyPacket(final CPacketDestroy data, final IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      Player player = ctx.player();
      RegaliaSlotsApi.getRegaliaSlotsApiInventory(player)
          .ifPresent(handler -> handler.getRegaliaSlotsApi().values().forEach(stacksHandler -> {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            IDynamicStackHandler cosmeticStackHandler = stacksHandler.getCosmeticStacks();
            String id = stacksHandler.getIdentifier();

            for (int i = stackHandler.getSlots() - 1; i >= 0; i--) {
              NonNullList<Boolean> renderStates = stacksHandler.getRenders();
              SlotContext slotContext = new SlotContext(id, player, i, false,
                                                        renderStates.size() > i && renderStates.get(
                                                            i));
              ItemStack stack = stackHandler.getStackInSlot(i);
              ICurioItem
                  .forEachModifier(stack, slotContext,
                                   (attributeHolder, attributeModifier) -> {
                                     if (attributeHolder.value() instanceof SlotAttribute slotAttribute) {
                                       handler.removeSlotModifier(
                                           slotAttribute.id(),
                                           attributeModifier.id());
                                     } else {
                                       AttributeInstance instance =
                                           player.getAttributes().getInstance(attributeHolder);

                                       if (instance != null) {
                                         instance.removeModifier(attributeModifier);
                                       }
                                     }
                                   });
              RegaliaSlotsApi.getCurio(stack)
                  .ifPresent(curio -> curio.onUnequip(slotContext, stack));
              stackHandler.setStackInSlot(i, ItemStack.EMPTY);
              PacketDistributor.sendToPlayersTrackingEntityAndSelf(player,
                                                                   new SPacketSyncStack(
                                                                       player.getId(), id, i,
                                                                       ItemStack.EMPTY,
                                                                       SPacketSyncStack.HandlerType.EQUIPMENT.ordinal(),
                                                                       new CompoundTag()));
              cosmeticStackHandler.setStackInSlot(i, ItemStack.EMPTY);
              PacketDistributor.sendToPlayersTrackingEntityAndSelf(player,
                                                                   new SPacketSyncStack(
                                                                       player.getId(), id, i,
                                                                       ItemStack.EMPTY,
                                                                       SPacketSyncStack.HandlerType.COSMETIC.ordinal(),
                                                                       new CompoundTag()));
            }
          }));
    });
  }
}
