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

package com.skd.regaliaslotsapi;

import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.resource.VanillaServerListeners;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiSlotTypes;
import com.skd.regaliaslotsapi.api.extensions.RegisterRegaliaSlotsApiExtensionsEvent;
import com.skd.regaliaslotsapi.api.internal.RegaliaSlotsApiServices;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.common.RegaliaSlotsApiCommonEvents;
import com.skd.regaliaslotsapi.common.capability.CurioInventoryCapability;
import com.skd.regaliaslotsapi.common.capability.CombinedRegaliaSlotsApiResourceHandler;
import com.skd.regaliaslotsapi.common.capability.ItemizedRegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.common.data.RegaliaSlotsApiSlotResources;
import com.skd.regaliaslotsapi.common.integration.RegaliaSlotsApiIntegrations;
import com.skd.regaliaslotsapi.common.network.NetworkHandler;
import com.skd.regaliaslotsapi.config.RegaliaSlotsApiClientConfig;
import com.skd.regaliaslotsapi.config.RegaliaSlotsApiConfig;
import com.skd.regaliaslotsapi.impl.RegaliaSlotsApiRegistry;
import com.skd.regaliaslotsapi.server.command.RegaliaSlotsApiCommand;
import com.skd.regaliaslotsapi.server.command.RegaliaSlotsApiSelectorOptions;

@Mod(RegaliaSlotsApiConstants.MOD_ID)
public class RegaliaSlotsApiCommonMod {

  public RegaliaSlotsApiCommonMod(IEventBus eventBus, ModContainer modContainer) {
    RegaliaSlotsApiRegistry.init(eventBus);
    RegaliaSlotsApiIntegrations.setup(eventBus);
    eventBus.addListener(this::setup);
    eventBus.addListener(this::registerCaps);
    eventBus.addListener(this::registerPayloadHandler);
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
    NeoForge.EVENT_BUS.register(new RegaliaSlotsApiCommonEvents());
    ModLoader.postEventWrapContainerInModOrder(new RegisterRegaliaSlotsApiExtensionsEvent());
    evt.enqueueWork(RegaliaSlotsApiSelectorOptions::register);
  }

  private void registerCaps(RegisterCapabilitiesEvent evt) {

    for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {

      evt.registerEntity(RegaliaSlotsApiCapability.ITEM_HANDLER, entityType,
                         (entity, ctx) -> {

                           if (entity instanceof LivingEntity livingEntity) {

                             if (!RegaliaSlotsApiSlotTypes.getDefaultEntitySlotTypes(livingEntity)
                                 .isEmpty()) {
                               return CombinedRegaliaSlotsApiResourceHandler.from(livingEntity);
                             }
                           }
                           return null;
                         });

      evt.registerEntity(RegaliaSlotsApiCapability.INVENTORY, entityType,
                         (entity, ctx) -> {

                           if (entity instanceof LivingEntity livingEntity) {

                             if (!RegaliaSlotsApiSlotTypes.getDefaultEntitySlotTypes(livingEntity)
                                 .isEmpty()) {
                               return new CurioInventoryCapability(livingEntity);
                             }
                           }
                           return null;
                         });
    }

    for (Item item : BuiltInRegistries.ITEM) {
      evt.registerItem(RegaliaSlotsApiCapability.ITEM, (stack, ctx) -> {
        Item it = stack.getItem();
        ICurioItem curioItem = RegaliaSlotsApiServices.EXTENSIONS.getCurioItem(item);

        if (curioItem == null && it instanceof ICurioItem itemCurio) {
          curioItem = itemCurio;
        }

        if (curioItem != null && curioItem.hasCurioCapability(stack)) {
          return new ItemizedRegaliaSlotsApiCapability(curioItem, stack);
        }
        return null;
      }, item);
    }
  }

  private void registerCommands(RegisterCommandsEvent evt) {
    RegaliaSlotsApiCommand.register(evt.getDispatcher(), evt.getBuildContext());
  }

  private void reload(final AddServerReloadListenersEvent evt) {
    RegaliaSlotsApiSlotResources.SERVER = new RegaliaSlotsApiSlotResources(evt.getRegistryAccess());
    evt.addListener(RegaliaSlotsApiSlotResources.ID, RegaliaSlotsApiSlotResources.SERVER);
    evt.addDependency(VanillaServerListeners.LAST, RegaliaSlotsApiSlotResources.ID);
  }

  public static String itemCacheKey(ItemStack stack) {
    return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString() +
        (!stack.getComponents().isEmpty() ?
         stack.getComponents().stream().map(TypedDataComponent::toString)
             .reduce((s, s2) -> s + s2) : "");
  }
}
