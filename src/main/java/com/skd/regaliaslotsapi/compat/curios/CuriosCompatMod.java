package com.skd.regaliaslotsapi.compat.curios;

import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiTags;
import com.skd.regaliaslotsapi.api.internal.RegaliaSlotsApiServices;
import com.skd.regaliaslotsapi.api.type.capability.IRegaliaSlotsApiItemHandler;

/**
 * Second logical mod declared in this same jar under modId "curios" (see
 * {@code src/main/templates/META-INF/neoforge.mods.toml}), so that
 * {@code ModList.get().isLoaded("curios")} returns true for third-party mods that only check for
 * the real Curios mod id (e.g. Sophisticated Backpacks).
 * <p>
 * This class owns the runtime wiring the copied {@code top.theillusivec4.curios.api} classes
 * need: the {@code curios:attribute_modifiers} data component and the {@code curios:inventory} /
 * {@code curios:item} capabilities, both backed live by Regalia's own data via
 * {@link CuriosItemHandlerAdapter} - nothing is duplicated or resynced between the two mod ids.
 * <p>
 * Known limitation: if the real Curios mod is ever installed alongside this one, NeoForge will
 * fail to load with a duplicate mod id "curios" error. This is intentional and not silently
 * worked around - Curios and this compatibility layer are mutually exclusive.
 */
@Mod("curios")
public class CuriosCompatMod {

  private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
      DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "curios");

  public static final Supplier<DataComponentType<top.theillusivec4.curios.api.CurioAttributeModifiers>>
      ATTRIBUTE_MODIFIERS = DATA_COMPONENTS.register("attribute_modifiers",
      () -> DataComponentType.<top.theillusivec4.curios.api.CurioAttributeModifiers>builder()
          .persistent(top.theillusivec4.curios.api.CurioAttributeModifiers.CODEC)
          .networkSynchronized(top.theillusivec4.curios.api.CurioAttributeModifiers.STREAM_CODEC)
          .cacheEncoding()
          .build());

  public CuriosCompatMod(IEventBus eventBus, ModContainer modContainer) {
    DATA_COMPONENTS.register(eventBus);
    eventBus.addListener(this::registerCaps);
    overrideTagPredicate();
    LegacyCurioMigration.register(eventBus);
    NeoForge.EVENT_BUS.addListener(CuriosCompatMod::onPlayerLogin);
    top.theillusivec4.curios.api.CuriosResources.LOG.info("Curios API compatibility layer active");
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

  /**
   * All of Regalia's own slot definitions (back.json, belt.json, ...) use the
   * "regalia_slots_api:tag" validator, which by default only accepts items in the
   * regalia_slots_api:&lt;slotId&gt; or regalia_slots_api:curio tags. Third-party mods that ship
   * Curios integration (e.g. Sophisticated Backpacks' data/curios/tags/item/back.json) tag their
   * items under the curios: namespace instead, so without this override those items are never
   * recognized as valid for any slot even though the slot itself and the entity assignment
   * (data/&lt;namespace&gt;/curios/entities/*.json) are read correctly by Regalia's own existing
   * data loader (RegaliaSlotsApiSlotResources kept folder="curios", unrenamed, so it already picks
   * up third-party curios/ datapack files).
   * <p>
   * This re-registers the same "regalia_slots_api:tag" predicate id, so it runs after Regalia's
   * own default registration (accessing RegaliaSlotsApiServices.SLOTS triggers class init of
   * RegaliaSlotsApiSlots first) and simply widens it - no core file is modified.
   */
  private static void overrideTagPredicate() {
    RegaliaSlotsApiServices.SLOTS.registerPredicate(
        RegaliaSlotsApiResources.resource("tag"),
        (slotContext, stack) -> {
          String id = slotContext.identifier();
          TagKey<Item> regaliaTag = ItemTags.create(RegaliaSlotsApiResources.resource(id));
          TagKey<Item> curiosTag = ItemTags.create(top.theillusivec4.curios.api.CuriosResources.resource(id));
          return stack.is(regaliaTag) || stack.is(RegaliaSlotsApiTags.CURIO)
              || stack.is(curiosTag) || stack.is(top.theillusivec4.curios.api.CuriosTags.CURIO);
        });
  }

  private void registerCaps(RegisterCapabilitiesEvent evt) {

    for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
      evt.registerEntity(top.theillusivec4.curios.api.CuriosCapability.INVENTORY, entityType,
          (entity, ctx) -> {

            if (entity instanceof LivingEntity livingEntity) {
              IRegaliaSlotsApiItemHandler handler =
                  livingEntity.getCapability(RegaliaSlotsApiCapability.INVENTORY);

              if (handler != null) {
                return new CuriosItemHandlerAdapter(handler);
              }
            }
            return null;
          });
    }

    for (Item item : BuiltInRegistries.ITEM) {
      evt.registerItem(top.theillusivec4.curios.api.CuriosCapability.ITEM, (stack, ctx) -> {

        if (RegaliaSlotsApiServices.EXTENSIONS.getCurioItem(item) != null
            || CuriosExtensionsAdapter.REGISTERED_ITEMS.containsKey(item)) {
          return (top.theillusivec4.curios.api.type.capability.ICurio) () -> stack;
        }
        return null;
      }, item);
    }
  }
}
