package com.skd.regaliaslotsapi.compat.curios;

import com.mojang.serialization.Codec;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.skd.regaliaslotsapi.RegaliaSlotsApiConstants;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.api.type.capability.IRegaliaSlotsApiItemHandler;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;
import com.skd.regaliaslotsapi.common.capability.CurioInventory;
import com.skd.regaliaslotsapi.common.inventory.CurioStacksHandler;

/**
 * One-time migration for players coming from a world that had the real Curios mod installed
 * (modId "curios") and is now switched to regalia_slots_api instead.
 * <p>
 * Both mods store the equipped items on the player as a NeoForge entity attachment, but under
 * different ids and, since regalia_slots_api is a renamed fork, a different internal NBT list key
 * ({@code "Curios"} in the real mod vs {@code "RegaliaSlotsApi"} here - see
 * {@code com.skd.regaliaslotsapi.common.capability.CurioInventory}). Removing Curios in favor of
 * this mod therefore leaves the old data as untouched, orphaned NBT that our own
 * {@code regalia_slots_api:inventory} attachment never looks at - from the player's perspective,
 * everything they had equipped simply vanishes.
 * <p>
 * This class registers a second, read-only attachment under the exact id the real Curios mod used
 * ({@code curios:inventory}), with a custom serializer that reads the original {@code "Curios"}
 * NBT key, and copies whatever it finds into the player's real inventory once, the first time they
 * log in after the switch. A separate marker attachment prevents re-running the copy on later
 * logins (which would undo intentional removals by re-inserting the old items).
 */
public final class LegacyCurioMigration {

  private LegacyCurioMigration() {
  }

  private static final DeferredRegister<AttachmentType<?>> LEGACY_ATTACHMENTS =
      DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "curios");

  private static final DeferredRegister<AttachmentType<?>> MIGRATION_ATTACHMENTS =
      DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, RegaliaSlotsApiResources.MOD_ID);

  static final java.util.function.Supplier<AttachmentType<CurioInventory>> LEGACY_INVENTORY =
      LEGACY_ATTACHMENTS.register("inventory", () -> AttachmentType
          .builder((IAttachmentHolder holder) -> new CurioInventory(holder))
          .serialize(new IAttachmentSerializer<CurioInventory>() {

            @Override
            public CurioInventory read(IAttachmentHolder holder, ValueInput input) {
              CurioInventory inventory = new CurioInventory(holder);
              Map<String, ICurioStacksHandler> map = new LinkedHashMap<>();
              ValueInput.ValueInputList list = input.childrenListOrEmpty("Curios");

              list.forEach(entry -> {
                for (String id : entry.keySet()) {

                  if (!id.isEmpty()) {
                    ICurioStacksHandler stacks = new CurioStacksHandler(inventory, id);
                    stacks.deserialize(entry.childOrEmpty(id));
                    map.put(id, stacks);
                  }
                }
              });
              inventory.replace(map);
              return inventory;
            }

            @Override
            public boolean write(CurioInventory attachment, ValueOutput output) {
              return false;
            }
          })
          .build());

  static final java.util.function.Supplier<AttachmentType<Boolean>> MIGRATED =
      MIGRATION_ATTACHMENTS.register("legacy_curios_migrated",
          () -> AttachmentType.builder(() -> Boolean.FALSE)
              .serialize(Codec.BOOL.fieldOf("migrated"))
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
    IRegaliaSlotsApiItemHandler ours = player.getCapability(RegaliaSlotsApiCapability.INVENTORY);

    if (ours == null) {
      return;
    }
    CurioInventory legacy = player.getData(LEGACY_INVENTORY);
    int migratedCount = 0;

    for (Map.Entry<String, ICurioStacksHandler> entry : legacy.asMap().entrySet()) {
      String slotId = entry.getKey();
      IDynamicStackHandler legacyStacks = entry.getValue().getStacks();
      var maybeOurs = ours.getStacksHandler(slotId);

      if (maybeOurs.isEmpty()) {

        for (int i = 0; i < legacyStacks.getSlots(); i++) {
          ItemStack stack = legacyStacks.getStackInSlot(i);

          if (!stack.isEmpty()) {
            ours.loseInvalidStack(stack);
          }
        }
        continue;
      }
      IDynamicStackHandler ourStacks = maybeOurs.get().getStacks();

      for (int i = 0; i < legacyStacks.getSlots(); i++) {
        ItemStack stack = legacyStacks.getStackInSlot(i);

        if (stack.isEmpty()) {
          continue;
        }

        if (i < ourStacks.getSlots() && ourStacks.getStackInSlot(i).isEmpty()) {
          ourStacks.setStackInSlot(i, stack);
          migratedCount++;
        } else {
          ours.loseInvalidStack(stack);
        }
      }
    }
    ours.handleInvalidStacks();

    if (migratedCount > 0) {
      RegaliaSlotsApiConstants.LOG.info(
          "Migrated {} legacy Curios item(s) for player {}", migratedCount, player.getGameProfile().name());
    }
  }
}
