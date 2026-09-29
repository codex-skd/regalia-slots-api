package com.skd.regaliaslotsapi.test.data;

import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiTriggers;
import com.skd.regaliaslotsapi.api.SlotPredicate;
import com.skd.regaliaslotsapi.test.RegaliaSlotsApiTest;

public class RegaliaSlotsApiGenerator implements AdvancementSubProvider {


  @Override
  public void generate(@Nonnull HolderLookup.Provider registries,
                       @Nonnull Consumer<AdvancementHolder> saver) {
    Advancement.Builder.advancement()
        .addCriterion("test",
                      RegaliaSlotsApiTriggers.equip()
                          .withItem(ItemPredicate.Builder.item()
                                        .of(BuiltInRegistries.ITEM, Items.DIAMOND))
                          .withLocation(LocationPredicate.Builder.location()
                                            .setBiomes(HolderSet.direct(
                                                registries.lookupOrThrow(Registries.BIOME)
                                                    .getOrThrow(Biomes.BADLANDS))))
                          .withSlot(SlotPredicate.Builder.slot()
                                        .of("ring", "necklace")
                                        .withIndex(MinMaxBounds.Ints.between(0, 10)))
                          .build())
        .save(saver, Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "test"));
  }
}
