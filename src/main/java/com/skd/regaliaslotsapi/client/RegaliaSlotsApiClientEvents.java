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

package com.skd.regaliaslotsapi.client;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jspecify.annotations.NonNull;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiSlotTypes;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiTags;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.client.ICurioRenderer;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;
import com.skd.regaliaslotsapi.common.network.client.CPacketOpenRegaliaSlotsApi;

public class RegaliaSlotsApiClientEvents {

  @SubscribeEvent
  public <T extends Avatar & ClientAvatarEntity> void renderHand(
      final RenderArmEvent<@NonNull T> evt) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player != null) {
      PoseStack poseStack = evt.getPoseStack();
      poseStack.pushPose();
      AbstractClientPlayer clientPlayer = mc.player;
      EntityRenderer<? super AbstractClientPlayer, ?>
          entityRenderer = mc.getEntityRenderDispatcher().getRenderer(clientPlayer);
      EntityRenderState renderState = entityRenderer.createRenderState();

      if (renderState instanceof AvatarRenderState avatarRenderState) {
        RegaliaSlotsApi.getRegaliaSlotsApiInventory(clientPlayer)
            .ifPresent(handler -> handler.getRegaliaSlotsApi().forEach((id, stacksHandler) -> {
              IDynamicStackHandler stackHandler = stacksHandler.getStacks();
              IDynamicStackHandler cosmeticStacksHandler = stacksHandler.getCosmeticStacks();

              for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack stack = cosmeticStacksHandler.getStackInSlot(i);
                boolean cosmetic = true;
                NonNullList<Boolean> renderStates = stacksHandler.getRenders();
                boolean renderable = renderStates.size() > i && renderStates.get(i);

                if (stack.isEmpty() && renderable) {
                  stack = stackHandler.getStackInSlot(i);
                  cosmetic = false;
                }

                if (!stack.isEmpty()) {
                  SlotContext
                      slotContext = new SlotContext(id, clientPlayer, i, cosmetic, renderable);
                  ICurioRenderer.get(stack).renderFirstPersonHand(
                      stack,
                      slotContext,
                      evt.getArm(),
                      poseStack,
                      evt.getSubmitNodeCollector(),
                      avatarRenderState,
                      clientPlayer,
                      evt.getLightCoords()
                  );
                }
              }
            }));
      }
      poseStack.popPose();
    }
  }

  @SubscribeEvent
  public void onClientTick(ClientTickEvent.Post evt) {

    if (RegaliaSlotsApiKeyMappings.OPEN_CURIOS_INVENTORY.consumeClick() && Minecraft.getInstance()
        .isWindowActive()) {
      ClientPacketDistributor.sendToServer(new CPacketOpenRegaliaSlotsApi(ItemStack.EMPTY));
    }
  }

  @SubscribeEvent
  public void onAttributeTooltip(final AddAttributeTooltipsEvent evt) {
    AttributeTooltipContext context = evt.getContext();
    ItemStack stack = evt.getStack();
    GatherSkippedAttributeTooltipsEvent skipped =
        NeoForge.EVENT_BUS.post(new GatherSkippedAttributeTooltipsEvent(stack, context));

    if (skipped.isSkippingAll()) {
      return;
    }
    List<Component> attributesTooltip = new ArrayList<>();
    Player player = context.player();
    Set<String> slots = getItemStackSlots(stack, player).keySet();

    for (String identifier : slots) {
      SlotContext slotContext = new SlotContext(identifier, player, 0, false, true);
      Multimap<Holder<Attribute>, AttributeModifier> attributes = LinkedHashMultimap.create();
      ICurioItem.forEachModifier(stack, slotContext, attributes::put);
      attributes.values().removeIf(modifier -> skipped.isSkipped(modifier.id()));

      if (attributes.isEmpty()) {
        continue;
      }
      attributesTooltip.add(Component.empty());
      attributesTooltip.add(
          Component.translatable("curios.modifiers." + identifier).withStyle(ChatFormatting.GOLD));

      if (player != null) {
        AttributeUtil.applyTextFor(
            stack,
            attributesTooltip::add,
            attributes,
            AttributeTooltipContext.of(player, context, context.tooltipDisplay(), context.flag()));
      }
    }
    evt.addTooltipLines(
        RegaliaSlotsApi.getCurio(stack)
            .map(curio -> curio.getAttributesTooltip(attributesTooltip, context))
            .orElse(attributesTooltip)
            .toArray(new Component[0]));
  }

  @SubscribeEvent
  public void onTooltip(final ItemTooltipEvent evt) {
    ItemStack stack = evt.getItemStack();
    Player player = evt.getEntity();

    if (stack.isEmpty()) {
      return;
    }
    Map<String, ISlotType> slots = getItemStackSlots(stack, player);

    if (slots.isEmpty()) {
      return;
    }
    List<String> slotIds = slots.keySet().stream().toList();
    MutableComponent slotsTooltip =
        Component.translatable("curios.tooltip.slot").append(" ").withStyle(ChatFormatting.GOLD);

    for (int j = 0; j < slotIds.size(); j++) {
      String id = slotIds.get(j);
      String key = "curios.identifier." + id;
      MutableComponent type =
          Component.translatableWithFallback(
              key, id.substring(0, 1).toUpperCase(Locale.ROOT)
                  + id.substring(1).toLowerCase(Locale.ROOT));

      if (j < slotIds.size() - 1) {
        type = type.append(", ");
      }
      type = type.withStyle(ChatFormatting.YELLOW);
      slotsTooltip.append(type);
    }
    Item.TooltipContext context = evt.getContext();
    List<Component> toAdd = List.of(slotsTooltip);
    evt.getToolTip()
        .addAll(
            1,
            RegaliaSlotsApi.getCurio(stack)
                .map(curio -> curio.getSlotsTooltip(toAdd, context))
                .orElse(toAdd));
  }

  private static Map<String, ISlotType> getItemStackSlots(ItemStack stack, Player player) {
    Map<String, ISlotType> result = new LinkedHashMap<>();
    Map<String, ISlotType> map =
        player != null
            ? RegaliaSlotsApiSlotTypes.getItemSlotTypes(stack, player)
            : RegaliaSlotsApiSlotTypes.getItemSlotTypes(stack, FMLLoader.getCurrent().getDist().isClient());

    for (Map.Entry<String, ISlotType> entry : map.entrySet()) {
      ISlotType slotType = entry.getValue();

      // Avoid getting slots with "all" validators to solve tooltip bloat
      if (!slotType.getValidators().contains(RegaliaSlotsApiResources.resource("all"))) {
        result.put(entry.getKey(), slotType);
      }
    }
    String curio = RegaliaSlotsApiSlotTypes.Preset.CURIO.id();

    if (result.containsKey(curio)) {

      if (stack.is(RegaliaSlotsApiTags.CURIO)) {
        return Map.of(curio, result.get(curio));
      } else {
        result.remove(curio);
      }
    }
    return result;
  }
}
