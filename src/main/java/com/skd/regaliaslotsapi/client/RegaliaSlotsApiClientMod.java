package com.skd.regaliaslotsapi.client;

import com.google.common.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.NonNullList;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.common.NeoForge;
import com.skd.regaliaslotsapi.RegaliaSlotsApiConstants;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.SlotResult;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;
import com.skd.regaliaslotsapi.client.screen.RegaliaSlotsApiScreen;
import com.skd.regaliaslotsapi.client.screen.RegaliaSlotsApiScreenEvents;
import com.skd.regaliaslotsapi.impl.RegaliaSlotsApiRegistry;
import com.skd.regaliaslotsapi.impl.RegaliaSlotsApiClientExtensions;

@Mod(value = RegaliaSlotsApiConstants.MOD_ID, dist = Dist.CLIENT)
public class RegaliaSlotsApiClientMod {

  public RegaliaSlotsApiClientMod(final IEventBus eventBus, final ModContainer modContainer) {
    eventBus.addListener(this::registerKeys);
    eventBus.addListener(this::setupClient);
    eventBus.addListener(this::registerMenuScreens);
    eventBus.addListener(this::addEntityLayers);
    eventBus.addListener(this::registerRenderStateModifiers);
  }

  private void registerKeys(final RegisterKeyMappingsEvent evt) {
    evt.registerCategory(RegaliaSlotsApiKeyMappings.CURIOS_KEY_CATEGORY);
    evt.register(RegaliaSlotsApiKeyMappings.OPEN_CURIOS_INVENTORY);
  }

  private void setupClient(final FMLClientSetupEvent evt) {
    NeoForge.EVENT_BUS.register(new RegaliaSlotsApiClientEvents());
    NeoForge.EVENT_BUS.register(new RegaliaSlotsApiScreenEvents());
  }

  private void registerMenuScreens(final RegisterMenuScreensEvent evt) {
    evt.register(RegaliaSlotsApiRegistry.CURIO_MENU.get(), RegaliaSlotsApiScreen::new);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void addEntityLayers(final EntityRenderersEvent.AddLayers evt) {
    EntityRendererProvider.Context context = evt.getContext();

    for (EntityType<?> entityType : evt.getEntityTypes()) {

      if (entityType != EntityTypes.PLAYER) {
        EntityRenderer<?, ?> renderer = evt.getRenderer(entityType);

        if (renderer instanceof LivingEntityRenderer livingRenderer) {
          livingRenderer.addLayer(new RegaliaSlotsApiLayer<>(livingRenderer, context));
        }
      }
    }

    for (PlayerModelType skin : evt.getSkins()) {
      AvatarRenderer<?> avatarRenderer = evt.getPlayerRenderer(skin);

      if (avatarRenderer != null) {
        avatarRenderer.addLayer(new RegaliaSlotsApiLayer<>(avatarRenderer, context));
      }
    }
    RegaliaSlotsApiClientExtensions.loadRenderers();
  }

  public static final ContextKey<List<SlotResult>> CUSTOM_RENDER =
      new ContextKey<>(RegaliaSlotsApiResources.resource("custom_render"));
  public static final ContextKey<List<SlotResult>> ARMOR_RENDER =
      new ContextKey<>(RegaliaSlotsApiResources.resource("armor_render"));
  public static final ContextKey<List<SlotResult>> HANDHELD_RENDER =
      new ContextKey<>(RegaliaSlotsApiResources.resource("handheld_render"));

  private void registerRenderStateModifiers(final RegisterRenderStateModifiersEvent evt) {
    evt.registerEntityModifier(
        new TypeToken<LivingEntityRenderer<? extends LivingEntity, LivingEntityRenderState, ?>>() {
        },
        (entity, renderState) -> {
          List<SlotResult> customSlots = new ArrayList<>();
          RegaliaSlotsApi.getRegaliaSlotsApiInventory(entity)
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
                        slotContext = new SlotContext(id, entity, i, cosmetic, renderable);
                    customSlots.add(new SlotResult(slotContext, stack));
                  }
                }
              }));
          renderState.setRenderData(CUSTOM_RENDER, customSlots);
        });
  }
}
