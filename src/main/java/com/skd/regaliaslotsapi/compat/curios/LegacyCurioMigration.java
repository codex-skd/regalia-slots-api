package com.skd.regaliaslotsapi.compat.curios;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.skd.regaliaslotsapi.RegaliaSlotsApiConstants;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.type.capability.ICuriosItemHandler;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;
import com.skd.regaliaslotsapi.common.inventory.CurioStacksHandler;

/**
 * One-time migration for players coming from a world previously played with the real Curios mod
 * (modId "curios") and now switched to regalia_slots_api instead.
 * <p>
 * Both mods store the equipped items on the player as a NeoForge entity attachment, but under
 * different ids and a different internal NBT list key ({@code "Curios"} in the real mod). Removing
 * Curios in favor of this mod leaves the old data as untouched, orphaned NBT that our own
 * {@code regalia_slots_api:inventory} attachment never reads - from the player's perspective,
 * everything they had equipped simply vanishes.
 * <p>
 * This class registers a second, read-only attachment under the exact id the real Curios mod used
 * ({@code curios:inventory}), with a serializer that reads the original {@code "Curios"} NBT key,
 * and copies whatever it finds into the player's real inventory once, the first time they log in
 * after the switch. A separate marker attachment prevents re-running the copy on later logins.
 */
public final class LegacyCurioMigration {

  private LegacyCurioMigration() {
  }

  private static final DeferredRegister<AttachmentType<?>> LEGACY_ATTACHMENTS =
      DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "curios");

  private static final DeferredRegister<AttachmentType<?>> MIGRATION_ATTACHMENTS =
      DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES,
          RegaliaSlotsApiConstants.MOD_ID);

  /**
   * Read-only attachment that stores the raw legacy Curios NBT under the {@code curios:inventory}
   * id. The serializer reads the original top-level {@code "Curios"} key (a {@link ListTag} of
   * compound tags) where each element is a compound tag with a single key (the slot id) mapping to
   * the slot's NBT data.
   */
  static final Supplier<AttachmentType<LegacyCurioData>> LEGACY_INVENTORY =
      LEGACY_ATTACHMENTS.register("inventory",
          () -> AttachmentType.serializable(LegacyCurioData::new)
              .build());

  /**
   * Per-player boolean marker that prevents re-running the migration on later logins.
   */
  static final Supplier<AttachmentType<Boolean>> MIGRATED =
      MIGRATION_ATTACHMENTS.register("legacy_curios_migrated",
          () -> AttachmentType.builder(() -> Boolean.FALSE)
              .serialize(Codec.BOOL)
              .build());

  static void register(IEventBus bus) {
    LEGACY_ATTACHMENTS.register(bus);
    MIGRATION_ATTACHMENTS.register(bus);
  }

  static void migrate(ServerPlayer player) {

    if (Boolean.TRUE.equals(player.getData(MIGRATED))) {
      return;
    }
    player.setData(MIGRATED, Boolean.TRUE);
    ICuriosItemHandler ours = player.getCapability(RegaliaSlotsApiCapability.INVENTORY);

    if (ours == null) {
      return;
    }
    LegacyCurioData legacyData = player.getData(LEGACY_INVENTORY);
    CompoundTag data = legacyData.getData();

    if (data.isEmpty()) {
      return;
    }
    ListTag tagList = data.getList("Curios", Tag.TAG_COMPOUND);
    int migratedCount = 0;

    for (int i = 0; i < tagList.size(); i++) {
      CompoundTag tag = tagList.getCompound(i);

      for (String slotId : tag.getAllKeys()) {

        if (slotId.isEmpty()) {
          continue;
        }
        CompoundTag slotData = tag.getCompound(slotId);
        CurioStacksHandler legacyStacks = new CurioStacksHandler(ours, slotId);
        legacyStacks.deserializeNBT(slotData);
        IDynamicStackHandler legacyStackHandler = legacyStacks.getStacks();
        var maybeOurs = ours.getStacksHandler(slotId);

        if (maybeOurs.isEmpty()) {

          for (int j = 0; j < legacyStackHandler.getSlots(); j++) {
            ItemStack stack = legacyStackHandler.getStackInSlot(j);

            if (!stack.isEmpty()) {
              ours.loseInvalidStack(stack);
            }
          }
          continue;
        }
        IDynamicStackHandler ourStacks = maybeOurs.get().getStacks();

        for (int j = 0; j < legacyStackHandler.getSlots(); j++) {
          ItemStack stack = legacyStackHandler.getStackInSlot(j);

          if (stack.isEmpty()) {
            continue;
          }

          if (j < ourStacks.getSlots() && ourStacks.getStackInSlot(j).isEmpty()) {
            ourStacks.setStackInSlot(j, stack);
            migratedCount++;
          } else {
            ours.loseInvalidStack(stack);
          }
        }
      }
    }
    ours.handleInvalidStacks();

    if (migratedCount > 0) {
      RegaliaSlotsApiConstants.LOG.info(
          "Migrated {} legacy Curios item(s) for player {}",
          migratedCount, player.getGameProfile().getName());
    }
  }

  /**
   * Simple holder for raw legacy Curios NBT data, read-only. The {@code serializeNBT} method
   * returns an empty compound tag because this attachment is never written back to disk.
   */
  public static class LegacyCurioData implements net.neoforged.neoforge.common.util.INBTSerializable<CompoundTag> {

    private CompoundTag data = new CompoundTag();

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
      return new CompoundTag();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
      this.data = nbt;
    }

    public CompoundTag getData() {
      return this.data;
    }
  }
}
