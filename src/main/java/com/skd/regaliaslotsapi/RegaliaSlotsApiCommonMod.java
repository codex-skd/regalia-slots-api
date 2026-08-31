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

package com.skd.regaliaslotsapi;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.SlotTypeMessage;
import com.skd.regaliaslotsapi.api.client.RegaliaSlotsApiRendererRegistry;
import com.skd.regaliaslotsapi.api.extensions.RegisterRegaliaSlotsApiExtensionsEvent;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.client.ClientEventHandler;
import com.skd.regaliaslotsapi.client.RegaliaSlotsApiClientConfig;
import com.skd.regaliaslotsapi.client.IconHelper;
import com.skd.regaliaslotsapi.client.KeyRegistry;
import com.skd.regaliaslotsapi.client.gui.RegaliaSlotsApiScreen;
import com.skd.regaliaslotsapi.client.gui.GuiEventHandler;
import com.skd.regaliaslotsapi.client.render.RegaliaSlotsApiLayer;
import com.skd.regaliaslotsapi.common.RegaliaSlotsApiConfig;
import com.skd.regaliaslotsapi.common.RegaliaSlotsApiHelper;
import com.skd.regaliaslotsapi.common.RegaliaSlotsApiRegistry;
import com.skd.regaliaslotsapi.common.capability.CurioInventoryCapability;
import com.skd.regaliaslotsapi.common.capability.CurioItemHandler;
import com.skd.regaliaslotsapi.common.capability.ItemizedCurioCapability;
import com.skd.regaliaslotsapi.common.data.RegaliaSlotsApiEntityManager;
import com.skd.regaliaslotsapi.common.data.RegaliaSlotsApiSlotManager;
import com.skd.regaliaslotsapi.common.event.RegaliaSlotsApiEventHandler;
import com.skd.regaliaslotsapi.common.integration.RegaliaSlotsApiIntegrations;
import com.skd.regaliaslotsapi.common.network.NetworkHandler;
import com.skd.regaliaslotsapi.common.slottype.LegacySlotManager;
import com.skd.regaliaslotsapi.mixin.RegaliaSlotsApiImplMixinHooks;
import com.skd.regaliaslotsapi.server.SlotHelper;
import com.skd.regaliaslotsapi.server.command.CurioArgumentType;
import com.skd.regaliaslotsapi.server.command.RegaliaSlotsApiCommand;
import com.skd.regaliaslotsapi.server.command.RegaliaSlotsApiSelectorOptions;

@Mod(RegaliaSlotsApiConstants.MOD_ID)
public class RegaliaSlotsApiCommonMod {

  public RegaliaSlotsApiCommonMod(IEventBus eventBus, ModContainer modContainer) {
    RegaliaSlotsApiRegistry.init(eventBus);
    RegaliaSlotsApiIntegrations.setup(eventBus);
    eventBus.addListener(this::setup);
    eventBus.addListener(this::process);
    eventBus.addListener(this::registerCaps);
    eventBus.addListener(this::registerPayloadHandler);
    NeoForge.EVENT_BUS.addListener(this::serverAboutToStart);
    NeoForge.EVENT_BUS.addListener(this::serverStopped);
    NeoForge.EVENT_BUS.addListener(this::registerCommands);
    NeoForge.EVENT_BUS.addListener(this::reload);
    modContainer.registerConfig(ModConfig.Type.CLIENT, RegaliaSlotsApiClientConfig.CLIENT_SPEC);
    modContainer.registerConfig(ModConfig.Type.COMMON, RegaliaSlotsApiConfig.COMMON_SPEC);
    modContainer.registerConfig(ModConfig.Type.SERVER, RegaliaSlotsApiConfig.SERVER_SPEC);
  }

  private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
    NetworkHandler.register(evt.registrar("1.0"));
  }

  private void setup(FMLCommonSetupEvent evt) {
    RegaliaSlotsApi.setRegaliaSlotsApiHelper(new RegaliaSlotsApiHelper());
    NeoForge.EVENT_BUS.register(new RegaliaSlotsApiEventHandler());
    ModLoader.postEventWrapContainerInModOrder(new RegisterRegaliaSlotsApiExtensionsEvent());
    evt.enqueueWork(RegaliaSlotsApiSelectorOptions::register);
  }

  private void registerCaps(RegisterCapabilitiesEvent evt) {

    for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {

      evt.registerEntity(RegaliaSlotsApiCapability.ITEM_HANDLER, entityType,
          (entity, ctx) -> {

            if (entity instanceof LivingEntity livingEntity) {

              if (!RegaliaSlotsApi.getEntitySlots(livingEntity).isEmpty()) {
                return new CurioItemHandler(livingEntity);
              }
            }
            return null;
          });

      evt.registerEntity(RegaliaSlotsApiCapability.INVENTORY, entityType,
          (entity, ctx) -> {

            if (entity instanceof LivingEntity livingEntity) {

              if (!RegaliaSlotsApi.getEntitySlots(livingEntity).isEmpty()) {
                return new CurioInventoryCapability(livingEntity);
              }
            }
            return null;
          });
    }

    for (Item item : BuiltInRegistries.ITEM) {
      evt.registerItem(RegaliaSlotsApiCapability.ITEM, (stack, ctx) -> {
        Item it = stack.getItem();
        ICurioItem curioItem = RegaliaSlotsApiImplMixinHooks.getCurioFromRegistry(item).orElse(null);

        if (curioItem == null && it instanceof ICurioItem itemCurio) {
          curioItem = itemCurio;
        }

        if (curioItem != null && curioItem.hasCurioCapability(stack)) {
          return new ItemizedCurioCapability(curioItem, stack);
        }
        return null;
      }, item);
    }
  }

  private void process(InterModProcessEvent evt) {
    LegacySlotManager.buildImcSlotTypes(evt.getIMCStream(SlotTypeMessage.REGISTER_TYPE::equals),
        evt.getIMCStream(SlotTypeMessage.MODIFY_TYPE::equals));
  }

  private void serverAboutToStart(ServerAboutToStartEvent evt) {
    RegaliaSlotsApi.setSlotHelper(new SlotHelper());
    Set<String> slotIds = new HashSet<>();

    for (ISlotType value : RegaliaSlotsApiSlotManager.SERVER.getSlots().values()) {
      RegaliaSlotsApi.getSlotHelper().addSlotType(value);
      slotIds.add(value.getIdentifier());
    }
    CurioArgumentType.slotIds = slotIds;
  }

  private void serverStopped(ServerStoppedEvent evt) {
    RegaliaSlotsApi.setSlotHelper(null);
  }

  private void registerCommands(RegisterCommandsEvent evt) {
    RegaliaSlotsApiCommand.register(evt.getDispatcher(), evt.getBuildContext());
  }

  private void reload(final AddReloadListenerEvent evt) {
    RegaliaSlotsApiSlotManager.SERVER = new RegaliaSlotsApiSlotManager();
    evt.addListener(RegaliaSlotsApiSlotManager.SERVER);
    RegaliaSlotsApiEntityManager.SERVER = new RegaliaSlotsApiEntityManager();
    evt.addListener(RegaliaSlotsApiEntityManager.SERVER);
    evt.addListener(new SimplePreparableReloadListener<Void>() {
      @Nonnull
      @Override
      protected Void prepare(@Nonnull ResourceManager resourceManagerIn,
                             @Nonnull ProfilerFiller profilerIn) {
        return null;
      }

      @Override
      protected void apply(@Nonnull Void objectIn, @Nonnull ResourceManager resourceManagerIn,
                           @Nonnull ProfilerFiller profilerIn) {
        RegaliaSlotsApiEventHandler.dirtyTags = true;
      }
    });
  }

  @EventBusSubscriber(modid = RegaliaSlotsApiConstants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static class ClientProxy {

    @SubscribeEvent
    public static void registerKeys(final RegisterKeyMappingsEvent evt) {
      evt.register(KeyRegistry.openCurios);
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent evt) {
      RegaliaSlotsApi.setIconHelper(new IconHelper());
      NeoForge.EVENT_BUS.register(new ClientEventHandler());
      NeoForge.EVENT_BUS.register(new GuiEventHandler());
    }

    @SubscribeEvent
    public static void registerMenuScreens(final RegisterMenuScreensEvent evt) {
      evt.register(RegaliaSlotsApiRegistry.CURIO_MENU.get(), RegaliaSlotsApiScreen::new);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers evt) {

      for (PlayerSkin.Model skin : evt.getSkins()) {
        addPlayerLayer(evt, skin);
      }
      RegaliaSlotsApiRendererRegistry.load();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addPlayerLayer(EntityRenderersEvent.AddLayers evt, PlayerSkin.Model model) {
      EntityRenderer<? extends Player> renderer = evt.getSkin(model);

      if (renderer instanceof LivingEntityRenderer livingRenderer) {
        livingRenderer.addLayer(new RegaliaSlotsApiLayer<>(livingRenderer));
      }
    }
  }

  public static String itemCacheKey(ItemStack stack) {
    return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString() +
        (!stack.getComponents().isEmpty() ?
            stack.getComponents().stream().map(TypedDataComponent::toString)
                .reduce((s, s2) -> s + s2) : "");
  }
}
