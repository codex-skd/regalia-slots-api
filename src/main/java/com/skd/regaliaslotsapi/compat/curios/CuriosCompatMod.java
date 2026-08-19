package com.skd.regaliaslotsapi.compat.curios;

import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
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
    top.theillusivec4.curios.api.CuriosResources.LOG.info("Curios API compatibility layer active");
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
