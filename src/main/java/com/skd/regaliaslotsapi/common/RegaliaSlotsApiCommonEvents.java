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

package com.skd.regaliaslotsapi.common;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.SlotAttribute;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.common.DropRule;
import com.skd.regaliaslotsapi.api.event.CurioChangeEvent;
import com.skd.regaliaslotsapi.api.event.CurioDropsEvent;
import com.skd.regaliaslotsapi.api.event.DropRulesEvent;
import com.skd.regaliaslotsapi.api.type.IRegaliaSlotsApiMenu;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.capability.ICurio;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.api.type.capability.IRegaliaSlotsApiItemHandler;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;
import com.skd.regaliaslotsapi.common.data.RegaliaSlotsApiSlotResources;
import com.skd.regaliaslotsapi.common.inventory.container.RegaliaSlotsApiMenu;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncRegaliaSlotsApi;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncData;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncModifiers;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncStack;
import com.skd.regaliaslotsapi.common.network.server.sync.SPacketSyncStack.HandlerType;
import com.skd.regaliaslotsapi.config.RegaliaSlotsApiConfig;
import com.skd.regaliaslotsapi.impl.RegaliaSlotsApiRegistry;

public class RegaliaSlotsApiCommonEvents {

  private static void handleDrops(String identifier, LivingEntity livingEntity,
                                  List<Pair<Predicate<ItemStack>, DropRule>> dropRules,
                                  NonNullList<Boolean> renders, IDynamicStackHandler stacks,
                                  boolean cosmetic, Collection<ItemEntity> drops,
                                  boolean keepInventory, LivingDropsEvent evt) {
    for (int i = 0; i < stacks.getSlots(); i++) {
      ItemStack stack = stacks.getStackInSlot(i);
      SlotContext slotContext = new SlotContext(identifier, livingEntity, i, cosmetic,
          renders.size() > i && renders.get(i));

      if (!stack.isEmpty()) {
        DropRule dropRuleOverride = null;

        for (Pair<Predicate<ItemStack>, DropRule> override : dropRules) {

          if (override.getFirst().test(stack)) {
            dropRuleOverride = override.getSecond();
          }
        }
        DropRule dropRule = dropRuleOverride != null ? dropRuleOverride : RegaliaSlotsApi.getCurio(stack)
            .map(curio -> curio.getDropRule(slotContext, evt.getSource(), evt.isRecentlyHit()))
            .orElse(DropRule.DEFAULT);

        if (dropRule == DropRule.DEFAULT) {
          ISlotType slotType = ISlotType.get(identifier);

          if (slotType != null) {
            dropRule = slotType.getDropRule();
          }
        }

        if ((dropRule == DropRule.DEFAULT && keepInventory) || dropRule == DropRule.ALWAYS_KEEP) {
          continue;
        }

        if (!EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP) &&
            dropRule != DropRule.DESTROY) {
          drops.add(getDroppedItem(stack, livingEntity));
        }
        stacks.setStackInSlot(i, ItemStack.EMPTY);
      }
    }
  }

  private static ItemEntity getDroppedItem(ItemStack droppedItem, LivingEntity livingEntity) {
    double d0 = livingEntity.getY() - 0.30000001192092896D + livingEntity.getEyeHeight();
    ItemEntity entityitem = new ItemEntity(livingEntity.level(), livingEntity.getX(), d0,
        livingEntity.getZ(), droppedItem);
    entityitem.setPickUpDelay(40);
    float f = livingEntity.level().getRandom().nextFloat() * 0.5F;
    float f1 = livingEntity.level().getRandom().nextFloat() * ((float) Math.PI * 2F);
    entityitem.setDeltaMovement((-Mth.sin(f1) * f), 0.20000000298023224D, (Mth.cos(f1) * f));
    return entityitem;
  }

  private static boolean handleMending(Player player, IDynamicStackHandler stacks,
                                       PlayerXpEvent.PickupXp evt) {
    Holder<Enchantment> mendingHolder =
        player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
            .getOrThrow(Enchantments.MENDING);
    for (int i = 0; i < stacks.getSlots(); i++) {
      ItemStack stack = stacks.getStackInSlot(i);

      if (!stack.isEmpty() && stack.getEnchantmentLevel(mendingHolder) > 0 && stack.isDamaged()) {
        evt.setCanceled(true);
        ExperienceOrb orb = evt.getOrb();
        player.takeXpDelay = 2;
        player.take(orb, 1);
        int value = orb.getValue();
        int toRepair = Math.min(value * 2, stack.getDamageValue());
        value -= toRepair / 2;
        stack.setDamageValue(stack.getDamageValue() - toRepair);

        if (value > 0) {
          player.giveExperiencePoints(value);
        }
        orb.remove(Entity.RemovalReason.KILLED);
        return true;
      }
    }
    return false;
  }

  @SubscribeEvent
  public void onTagsUpdated(final ServerAboutToStartEvent evt) {
    RegaliaSlotsApiSlotResources.SERVER.populateData();
  }

  @SubscribeEvent
  public void onDatapackSync(OnDatapackSyncEvent evt) {

    if (evt.getPlayer() == null) {
      PlayerList playerList = evt.getPlayerList();

      for (ServerPlayer player : playerList.getPlayers()) {
        SPacketSyncData.send(player);
        IRegaliaSlotsApiItemHandler inventory = RegaliaSlotsApi.getRegaliaSlotsApiInventoryOrNull(player);

        if (inventory != null) {
          inventory.loadDatapacks();
          PacketDistributor.sendToPlayersTrackingEntityAndSelf(player,
              new SPacketSyncRegaliaSlotsApi(player.getId(), inventory.getRegaliaSlotsApi()));

          if (player.containerMenu instanceof IRegaliaSlotsApiMenu curiosContainer) {
            curiosContainer.resetSlots();
          }
        }
      }
    } else {
      ServerPlayer mp = evt.getPlayer();
      SPacketSyncData.send(mp);
      IRegaliaSlotsApiItemHandler inventory = RegaliaSlotsApi.getRegaliaSlotsApiInventoryOrNull(mp);

      if (inventory != null) {
        inventory.loadDatapacks();
        PacketDistributor.sendToPlayer(mp,
            new SPacketSyncRegaliaSlotsApi(mp.getId(), inventory.getRegaliaSlotsApi()));

        if (mp.containerMenu instanceof IRegaliaSlotsApiMenu curiosContainer) {
          curiosContainer.resetSlots();
        }
      }
    }
  }

  @SubscribeEvent
  public void entityConstructing(EntityEvent.EntityConstructing evt) {
    Entity entity = evt.getEntity();

    if (entity instanceof LivingEntity livingEntity && !(entity instanceof Player)) {
      RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity).ifPresent(IRegaliaSlotsApiItemHandler::loadDatapacks);
    }
  }

  @SubscribeEvent
  public void entityJoinWorld(EntityJoinLevelEvent evt) {
    Entity entity = evt.getEntity();

    if (entity instanceof ServerPlayer serverPlayerEntity) {
      RegaliaSlotsApi.getRegaliaSlotsApiInventory(serverPlayerEntity).ifPresent(handler -> {
        ServerPlayer mp = (ServerPlayer) entity;
        PacketDistributor.sendToPlayer(mp, new SPacketSyncRegaliaSlotsApi(mp.getId(), handler.getRegaliaSlotsApi()));
      });
    }
  }

  @SubscribeEvent
  public void playerStartTracking(PlayerEvent.StartTracking evt) {
    Entity target = evt.getTarget();
    Player player = evt.getEntity();

    if (player instanceof ServerPlayer serverPlayer && target instanceof LivingEntity livingBase) {
      RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingBase).ifPresent(
          handler -> PacketDistributor.sendToPlayer(serverPlayer,
              new SPacketSyncRegaliaSlotsApi(target.getId(),
                  handler.getRegaliaSlotsApi())));
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void playerDrops(LivingDropsEvent evt) {
    LivingEntity livingEntity = evt.getEntity();

    if (!livingEntity.isSpectator()) {

      RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity).ifPresent(handler -> {
        Collection<ItemEntity> drops = evt.getDrops();
        Collection<ItemEntity> curioDrops = new ArrayList<>();
        Map<String, ICurioStacksHandler> curios = handler.getRegaliaSlotsApi();
        // todo: Fix looting levels when NeoForge has a new API or figure a workaround
        DropRulesEvent dropRulesEvent =
            new DropRulesEvent(livingEntity, handler, evt.getSource(), 0, evt.isRecentlyHit());
        NeoForge.EVENT_BUS.post(dropRulesEvent);
        List<Pair<Predicate<ItemStack>, DropRule>> dropRules = dropRulesEvent.getOverrides();
        boolean keepInventory = false;

        if (livingEntity instanceof Player
            && livingEntity.level() instanceof ServerLevel serverLevel) {
          keepInventory =
              serverLevel.getGameRules().get(GameRules.KEEP_INVENTORY);

          if (RegaliaSlotsApiConfig.SERVER.keepRegaliaSlotsApi.get() != RegaliaSlotsApiConfig.KeepRegaliaSlotsApi.DEFAULT) {
            keepInventory = RegaliaSlotsApiConfig.SERVER.keepRegaliaSlotsApi.get() == RegaliaSlotsApiConfig.KeepRegaliaSlotsApi.ON;
          }
        }
        boolean finalKeepInventory = keepInventory;
        curios.forEach((id, stacksHandler) -> {
          handleDrops(id, livingEntity, dropRules, stacksHandler.getRenders(),
              stacksHandler.getStacks(), false, curioDrops, finalKeepInventory, evt);
          handleDrops(id, livingEntity, dropRules, stacksHandler.getRenders(),
              stacksHandler.getCosmeticStacks(), true, curioDrops, finalKeepInventory, evt);
        });
        CurioDropsEvent dropsEvent = NeoForge.EVENT_BUS.post(
            new CurioDropsEvent(livingEntity, handler, evt.getSource(), curioDrops, 0,
                evt.isRecentlyHit()));

        if (!dropsEvent.isCanceled()) {
          drops.addAll(curioDrops);
        }
      });
    }
  }

  @SubscribeEvent
  public void playerXPPickUp(PlayerXpEvent.PickupXp evt) {
    Player player = evt.getEntity();

    if (!player.level().isClientSide()) {
      RegaliaSlotsApi.getRegaliaSlotsApiInventory(player).ifPresent(handler -> {
        Map<String, ICurioStacksHandler> curios = handler.getRegaliaSlotsApi();
        for (ICurioStacksHandler stacksHandler : curios.values()) {

          if (handleMending(player, stacksHandler.getStacks(), evt) || handleMending(player,
              stacksHandler.getCosmeticStacks(),
              evt)) {
            return;
          }
        }
      });
    }
  }

  @SubscribeEvent
  public void curioRightClick(PlayerInteractEvent.RightClickItem evt) {
    Player player = evt.getEntity();
    ItemStack stack = evt.getItemStack();
    RegaliaSlotsApi.getCurio(stack).ifPresent(
        curio -> RegaliaSlotsApi.getRegaliaSlotsApiInventory(player).ifPresent(handler -> {
          Map<String, ICurioStacksHandler> curios = handler.getRegaliaSlotsApi();
          Pair<IDynamicStackHandler, SlotContext> firstSlot = null;

          for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            IDynamicStackHandler stackHandler = entry.getValue().getStacks();
            NonNullList<Boolean> activeStates = entry.getValue().getActiveStates();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
              boolean active = activeStates.size() > i && activeStates.get(i);

              if (!active) {
                continue;
              }
              String id = entry.getKey();
              NonNullList<Boolean> renderStates = entry.getValue().getRenders();
              SlotContext slotContext = new SlotContext(id, player, i, false,
                  renderStates.size() > i && renderStates.get(
                      i));

              if (stackHandler.isItemValid(i, stack) && curio.canEquipFromUse(slotContext)) {
                ItemStack present = stackHandler.getStackInSlot(i);

                if (present.isEmpty()) {
                  stackHandler.setStackInSlot(i, stack.copy());
                  curio.onEquipFromUse(slotContext);

                  if (!player.isCreative()) {
                    int count = stack.getCount();
                    stack.shrink(count);
                  }
                  evt.setCancellationResult(
                      player.level().isClientSide() ? InteractionResult.SUCCESS :
                          InteractionResult.SUCCESS_SERVER);
                  evt.setCanceled(true);
                  return;
                } else if (firstSlot == null) {

                  if (stackHandler.extractItem(i, stack.getMaxStackSize(), true).getCount() ==
                      stack.getCount()) {
                    firstSlot = new Pair<>(stackHandler, slotContext);
                  }
                }
              }
            }
          }

          if (firstSlot != null) {
            IDynamicStackHandler stackHandler = firstSlot.getFirst();
            SlotContext slotContext = firstSlot.getSecond();
            int i = slotContext.index();
            ItemStack present = stackHandler.getStackInSlot(i);
            stackHandler.setStackInSlot(i, stack.copy());
            curio.onEquipFromUse(slotContext);
            player.setItemInHand(evt.getHand(), present.copy());
            evt.setCancellationResult(
                player.level().isClientSide() ? InteractionResult.SUCCESS :
                    InteractionResult.SUCCESS_SERVER);
            evt.setCanceled(true);
          }
        }));
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onBreakBlock(BlockDropsEvent event) {
    AtomicInteger experience = new AtomicInteger(event.getDroppedExperience());

    if (experience.get() <= 0 || !(event.getBreaker() instanceof LivingEntity entity)) {
      return;
    }

    RegaliaSlotsApi.getRegaliaSlotsApiInventory(entity).ifPresent(handler -> {
      for (Map.Entry<String, ICurioStacksHandler> entry : handler.getRegaliaSlotsApi().entrySet()) {
        IDynamicStackHandler stacks = entry.getValue().getStacks();
        NonNullList<Boolean> renderStates = entry.getValue().getRenders();

        for (int i = 0; i < stacks.getSlots(); i++) {
          SlotContext context = new SlotContext(entry.getKey(), entity, i, false,
              renderStates.size() > i && renderStates.get(i));

          experience.addAndGet(
              EnchantmentHelper.processBlockExperience(event.getLevel(), event.getTool(),
                  RegaliaSlotsApi.getCurio(stacks.getStackInSlot(i))
                      .map(curio -> curio.getFortuneLevel(
                          context, null)).orElse(0)));
        }
      }
    });

    event.setDroppedExperience(experience.get());
  }

  static Map<UUID, Pair<Long, Boolean>> enderManMaskCache = new HashMap<>();

  @SubscribeEvent
  public void enderManAnger(final EnderManAngerEvent evt) {
    // Check cached value first
    if (enderManMaskCache.size() > 500) {
      enderManMaskCache.clear();
    }
    Player player = evt.getPlayer();
    long gameTime = player.level().getGameTime();

    if (enderManMaskCache.containsKey(player.getUUID())) {
      var pair = enderManMaskCache.get(player.getUUID());

      if (pair.getFirst() == gameTime) {
        evt.setCanceled(pair.getSecond());
        return;
      }
    }
    RegaliaSlotsApi.getRegaliaSlotsApiInventory(player).ifPresent(handler -> {
      all:
      for (Map.Entry<String, ICurioStacksHandler> entry : handler.getRegaliaSlotsApi().entrySet()) {
        IDynamicStackHandler stacks = entry.getValue().getStacks();

        for (int i = 0; i < stacks.getSlots(); i++) {
          final int index = i;
          NonNullList<Boolean> renderStates = entry.getValue().getRenders();
          boolean hasMask = RegaliaSlotsApi.getCurio(stacks.getStackInSlot(i)).map(
                  curio -> curio.isEnderMask(new SlotContext(entry.getKey(), player, index, false,
                          renderStates.size() > index
                              && renderStates.get(index)),
                      evt.getEntity()))
              .orElse(false);

          if (hasMask) {
            enderManMaskCache.put(player.getUUID(), Pair.of(gameTime, true));
            evt.setCanceled(true);
            break all;
          }
        }
      }
    });
    enderManMaskCache.put(player.getUUID(), Pair.of(gameTime, false));
  }

  @SubscribeEvent
  public void tick(EntityTickEvent.Post evt) {
    Entity entity = evt.getEntity();
    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

    if (server != null && !server.isDedicatedServer()) {

      if (RegaliaSlotsApiSlotResources.CLIENT.getSlots().isEmpty()) {
        return;
      }
    }

    if (entity instanceof LivingEntity livingEntity) {
      if (livingEntity instanceof Player player
          && player.containerMenu instanceof RegaliaSlotsApiMenu curiosMenu) {
        curiosMenu.checkQuickMove();
      }

      RegaliaSlotsApi.getRegaliaSlotsApiInventory(livingEntity).ifPresent(handler -> {
        handler.handleInvalidStacks();
        Map<String, ICurioStacksHandler> curios = handler.getRegaliaSlotsApi();

        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
          ICurioStacksHandler stacksHandler = entry.getValue();
          String identifier = entry.getKey();
          IDynamicStackHandler stackHandler = stacksHandler.getStacks();
          IDynamicStackHandler cosmeticStackHandler = stacksHandler.getCosmeticStacks();
          NonNullList<Boolean> renderStates = stacksHandler.getRenders();

          for (int i = 0; i < stacksHandler.getSlots(); i++) {
            stacksHandler.updateActiveState(i);
            NonNullList<Boolean> activeStates = stacksHandler.getActiveStates();
            boolean functional = activeStates.size() > i && activeStates.get(i);
            SlotContext slotContext = new SlotContext(identifier, livingEntity, i, false,
                renderStates.size() > i && renderStates.get(
                    i));
            ItemStack stack = stackHandler.getStackInSlot(i);
            Optional<ICurio> currentCurio = RegaliaSlotsApi.getCurio(stack);

            if (functional && !stack.isEmpty()) {
              stack.inventoryTick(livingEntity.level(), livingEntity, null);
              currentCurio.ifPresent(curio -> curio.curioTick(slotContext));
            }

            if (!livingEntity.level().isClientSide()) {
              ItemStack prevStack = stackHandler.getPreviousStackInSlot(i);

              if (!ItemStack.matches(stack, prevStack)) {
                boolean flag = false;
                Optional<ICurio> prevCurio = RegaliaSlotsApi.getCurio(prevStack);
                syncRegaliaSlotsApi(livingEntity, stack, currentCurio, prevCurio, identifier, i, false,
                    renderStates.size() > i && renderStates.get(i), HandlerType.EQUIPMENT);

                if (functional) {
                  CurioChangeEvent changeEvent;

                  if (ItemStack.isSameItem(stack, prevStack)) {
                    flag = true;
                    changeEvent =
                        new CurioChangeEvent.State(livingEntity, slotContext, prevStack, stack);
                  } else {
                    changeEvent =
                        new CurioChangeEvent.Item(livingEntity, slotContext, prevStack, stack);
                  }
                  NeoForge.EVENT_BUS.post(changeEvent);
                  AttributeMap attributeMap = livingEntity.getAttributes();
                  final boolean isStateChange = flag;

                  if (!prevStack.isEmpty()) {
                    ICurioItem
                        .forEachModifier(prevStack, slotContext,
                            (attributeHolder, attributeModifier) -> {
                              if (attributeHolder.value() instanceof SlotAttribute slotAttribute) {
                                handler.removeSlotModifier(
                                    slotAttribute.id(),
                                    attributeModifier.id());
                              } else {
                                AttributeInstance instance =
                                    attributeMap.getInstance(attributeHolder);

                                if (instance != null) {
                                  instance.removeModifier(attributeModifier);
                                }
                              }
                            });
                    prevCurio.ifPresent(curio -> {

                      if (!isStateChange) {
                        curio.onUnequip(slotContext, stack);
                      }
                    });
                  }

                  if (!stack.isEmpty()) {
                    ICurioItem
                        .forEachModifier(stack, slotContext,
                            (attributeHolder, attributeModifier) -> {
                              if (attributeHolder.value() instanceof SlotAttribute slotAttribute) {
                                handler.addTransientSlotModifier(
                                    slotAttribute.id(),
                                    attributeModifier.id(), attributeModifier.amount(),
                                    attributeModifier.operation());
                              } else {
                                AttributeInstance instance =
                                    attributeMap.getInstance(attributeHolder);

                                if (instance != null) {
                                  instance.addOrUpdateTransientModifier(
                                      attributeModifier);
                                }
                              }
                            });
                    currentCurio.ifPresent(curio -> {

                      if (isStateChange) {
                        curio.onStateChange(slotContext, prevStack);
                      } else {
                        curio.onEquip(slotContext, prevStack);
                      }
                    });

                    if (livingEntity instanceof ServerPlayer) {
                      RegaliaSlotsApiRegistry.EQUIP_TRIGGER.get()
                          .trigger(slotContext, (ServerPlayer) livingEntity, stack);
                    }
                  }
                }
                stackHandler.setPreviousStackInSlot(i, stack.copy());
              }
              ItemStack cosmeticStack = cosmeticStackHandler.getStackInSlot(i);
              ItemStack prevCosmeticStack = cosmeticStackHandler.getPreviousStackInSlot(i);

              if (!ItemStack.matches(cosmeticStack, prevCosmeticStack)) {
                syncRegaliaSlotsApi(livingEntity, cosmeticStack, RegaliaSlotsApi.getCurio(cosmeticStack),
                    RegaliaSlotsApi.getCurio(prevCosmeticStack), identifier, i, true, true,
                    HandlerType.COSMETIC);
                cosmeticStackHandler.setPreviousStackInSlot(i, cosmeticStack.copy());
              }
            }
          }
        }

        if (!livingEntity.level().isClientSide()) {
          handler.clearCachedSlotModifiers();
          Set<ICurioStacksHandler> updates = handler.getUpdatingInventories();

          if (!updates.isEmpty()) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity,
                new SPacketSyncModifiers(
                    livingEntity.getId(),
                    updates));
            updates.clear();
          }
        }
      });
    }
  }

  @SubscribeEvent
  public void livingEquipmentChange(final LivingEquipmentChangeEvent evt) {
    RegaliaSlotsApi.getRegaliaSlotsApiInventory(evt.getEntity()).ifPresent(inv -> {
      ItemStack from = evt.getFrom();
      ItemStack to = evt.getTo();
      EquipmentSlot slot = evt.getSlot();

      if (!from.isEmpty()) {
        Multimap<String, AttributeModifier> slots = HashMultimap.create();
        from.forEachModifier(slot, (att, modifier) -> {
          if (att.value() instanceof SlotAttribute wrapper) {
            slots.putAll(wrapper.id(), Collections.singleton(modifier));
          }
        });
        inv.removeSlotModifiers(slots);
      }

      if (!to.isEmpty()) {
        Multimap<String, AttributeModifier> slots = HashMultimap.create();
        to.forEachModifier(slot, (att, modifier) -> {
          if (att.value() instanceof SlotAttribute wrapper) {
            slots.putAll(wrapper.id(), Collections.singleton(modifier));
          }
        });
        inv.addTransientSlotModifiers(slots);
      }
    });
  }

  private static void syncRegaliaSlotsApi(LivingEntity livingEntity, ItemStack stack,
                                 Optional<ICurio> currentCurio, Optional<ICurio> prevCurio,
                                 String identifier, int index, boolean cosmetic, boolean visible,
                                 HandlerType type) {
    SlotContext slotContext = new SlotContext(identifier, livingEntity, index, cosmetic, visible);
    boolean syncable = currentCurio.map(curio -> curio.canSync(slotContext)).orElse(false) ||
        prevCurio.map(curio -> curio.canSync(slotContext)).orElse(false);
    CompoundTag syncTag = syncable ?
        currentCurio.map(curio -> curio.writeSyncData(slotContext))
            .orElse(new CompoundTag()) :
        new CompoundTag();
    PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity,
        new SPacketSyncStack(livingEntity.getId(),
            identifier, index,
            stack, type.ordinal(),
            syncTag));
  }
}
