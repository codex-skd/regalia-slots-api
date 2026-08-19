package com.skd.regaliaslotsapi.compat.curios;

import it.unimi.dsi.fastutil.objects.Object2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.world.item.Item;

/**
 * Stores {@code CuriosApi.registerCurio}/{@code registerSlotExtension} registrations made by
 * third-party mods against the copied Curios API, in a registry separate from Regalia's own
 * ({@link com.skd.regaliaslotsapi.impl.RegaliaSlotsApiExtensions}) since the two sides use
 * distinct {@code ICurioItem}/{@code ICurioSlotExtension} interfaces.
 * <p>
 * Known gap: registrations made here are not yet consulted when Regalia hands out the
 * {@code curios:item} capability (see {@link CuriosCompatMod}) - items registered this way get
 * default curio behaviour rather than their custom logic. Bridging per-item custom behaviour
 * across the two parallel {@code ICurioItem} interfaces is deferred.
 */
public final class CuriosExtensionsAdapter
    implements top.theillusivec4.curios.api.internal.services.ICuriosExtensions {

  static final Map<Item, top.theillusivec4.curios.api.type.capability.ICurioItem> REGISTERED_ITEMS =
      Reference2ObjectMaps.synchronize(new Reference2ObjectOpenHashMap<>());
  static final Map<String, top.theillusivec4.curios.api.extensions.ICurioSlotExtension> SLOT_EXTENSIONS =
      Object2ReferenceMaps.synchronize(new Object2ReferenceOpenHashMap<>());

  @Override
  public void registerCurioItem(top.theillusivec4.curios.api.type.capability.ICurioItem curioItem,
                                Item... items) {

    if (items.length == 0) {
      throw new IllegalArgumentException("At least one item must be provided");
    }
    Objects.requireNonNull(curioItem, "Curio item must not be null");

    for (Item item : items) {
      Objects.requireNonNull(item, "Item must not be null");
      REGISTERED_ITEMS.put(item, curioItem);
    }
  }

  @Nullable
  @Override
  public top.theillusivec4.curios.api.type.capability.ICurioItem getCurioItem(Item item) {
    return REGISTERED_ITEMS.get(item);
  }

  @Override
  public void registerSlotExtension(top.theillusivec4.curios.api.extensions.ICurioSlotExtension extension,
                                    String... slotIds) {

    if (slotIds.length == 0) {
      throw new IllegalArgumentException("At least one slot must be provided");
    }
    Objects.requireNonNull(extension, "Slot extension must not be null");

    for (String id : slotIds) {
      SLOT_EXTENSIONS.put(id, extension);
    }
  }

  @Nullable
  @Override
  public top.theillusivec4.curios.api.extensions.ICurioSlotExtension getSlotExtension(String id) {
    return SLOT_EXTENSIONS.get(id);
  }
}
