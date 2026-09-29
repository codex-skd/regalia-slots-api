package com.skd.regaliaslotsapi.compat.curios;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.common.capability.CurioInventoryCapability;
import com.skd.regaliaslotsapi.common.capability.ItemizedCurioCapability;
import com.skd.regaliaslotsapi.mixin.RegaliaSlotsApiImplMixinHooks;

/**
 * Second logical mod declared in this same jar under modId "curios", so that
 * {@code ModList.get().isLoaded("curios")} returns true for third-party mods that only check for
 * the real Curios mod id (e.g. Sophisticated Backpacks).
 * <p>
 * This class owns the runtime wiring for the {@code curios:inventory} and {@code curios:item}
 * capabilities, both backed live by Regalia's own data via {@link CurioInventoryCapability} and
 * {@link ItemizedCurioCapability} - nothing is duplicated or resynced between the two mod ids.
 * <p>
 * The capability objects themselves are the verbatim ones declared by {@link CuriosCapability}
 * (the copied Curios API), so third-party mods compiled against real Curios resolve the exact
 * same {@code EntityCapability}/{@code ItemCapability} instances. We only register the providers,
 * wrapping Regalia's implementations in the {@code Shim*} adapters so the returned objects
 * implement the {@code top.theillusivec4.curios.*} interfaces those mods expect. Declaring our
 * own capability objects for the same ids would clash with {@code CuriosCapability}'s at
 * registration time ({@code IllegalStateException: ... existing type ...}).
 * <p>
 * Known limitation: if the real Curios mod is ever installed alongside this one, NeoForge will
 * fail to load with a duplicate mod id "curios" error. This is intentional - Curios and this
 * compatibility layer are mutually exclusive.
 */
@Mod("curios")
public class CuriosCompatMod {

  public CuriosCompatMod(IEventBus eventBus) {
    eventBus.addListener(this::registerCaps);
    LegacyCurioMigration.register(eventBus);
    NeoForge.EVENT_BUS.addListener(CuriosCompatMod::onPlayerLogin);
  }

  /**
   * Migrates equipped items from a world previously played with the real Curios mod, the first
   * time each player logs in after switching to this mod. See {@link LegacyCurioMigration}.
   */
  private static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent evt) {

    if (evt.getEntity() instanceof ServerPlayer serverPlayer) {
      LegacyCurioMigration.migrate(serverPlayer);
    }
  }

  private void registerCaps(RegisterCapabilitiesEvent evt) {

    for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
      evt.registerEntity(CuriosCapability.INVENTORY, entityType, (entity, ctx) -> {

        if (entity instanceof LivingEntity livingEntity) {

          if (!RegaliaSlotsApi.getEntitySlots(livingEntity).isEmpty()) {
            return new ShimCuriosItemHandler(new CurioInventoryCapability(livingEntity));
          }
        }
        return null;
      });

      evt.registerEntity(CuriosCapability.ITEM_HANDLER, entityType, (entity, ctx) -> {

        if (entity instanceof LivingEntity livingEntity) {

          if (!RegaliaSlotsApi.getEntitySlots(livingEntity).isEmpty()) {
            return new CurioInventoryCapability(livingEntity).getEquippedCurios();
          }
        }
        return null;
      });
    }

    for (Item item : BuiltInRegistries.ITEM) {
      evt.registerItem(CuriosCapability.ITEM, (stack, ctx) -> {
        ICurioItem curioItem =
            RegaliaSlotsApiImplMixinHooks.getCurioFromRegistry(item).orElse(null);
        Item it = stack.getItem();

        if (curioItem == null && it instanceof ICurioItem itemCurio) {
          curioItem = itemCurio;
        }

        if (curioItem != null && curioItem.hasCurioCapability(stack)) {
          return new ShimCurio(new ItemizedCurioCapability(curioItem, stack));
        }
        return null;
      }, item);
    }
  }
}
