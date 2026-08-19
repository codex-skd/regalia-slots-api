package com.skd.regaliaslotsapi.test.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.ItemIds;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.neoforge.common.conditions.NeoForgeConditions;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiDataProvider;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiTags;
import com.skd.regaliaslotsapi.api.common.DropRule;
import com.skd.regaliaslotsapi.api.type.data.ISlotData;
import com.skd.regaliaslotsapi.test.common.RegaliaSlotsApiTestIds;

public class RegaliaSlotsApiTestProvider extends RegaliaSlotsApiDataProvider {

  public RegaliaSlotsApiTestProvider(
      String modId,
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> registries) {
    super(modId, output, registries);
  }

  @Override
  public void generate(HolderLookup.Provider registries) {
    this.createSlot("never_slot")
        .size(4)
        .dropRule(DropRule.ALWAYS_KEEP)
        .operation("ADD")
        .addCosmetic(true)
        .addCondition(NeoForgeConditions.never());

    ISlotData testSlot = this.createSlot("slot_from_slots").size(2);
    this.tag(testSlot).add(ItemIds.DIAMOND);

    this.tag(RegaliaSlotsApiTags.HANDS).add(RegaliaSlotsApiTestIds.KNUCKLES);
    this.tag(RegaliaSlotsApiTags.NECKLACE).add(RegaliaSlotsApiTestIds.AMULET);
    this.tag(RegaliaSlotsApiTags.RING).add(RegaliaSlotsApiTestIds.RING);
    this.tag(RegaliaSlotsApiTags.HEAD).add(RegaliaSlotsApiTestIds.CROWN);

    this.createEntities("test_entities")
        .addPlayer()
        .addEntities(EntityTypes.WITHER_SKELETON)
        .addAllPresetSlots()
        .addSlots("inline_from_entities")
        .addSlots(this.getSlot("slot_from_entities").size(3))
        .addCondition(NeoForgeConditions.always());
  }
}
