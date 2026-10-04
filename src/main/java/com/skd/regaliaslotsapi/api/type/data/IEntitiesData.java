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

package com.skd.regaliaslotsapi.api.type.data;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.ApiStatus;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiSlotTypes;
import com.skd.regaliaslotsapi.api.internal.RegaliaSlotsApiServices;

/**
 * Used in data generation to represent the entities data
 */
public interface IEntitiesData {

  IEntitiesData replace(boolean replace);

  IEntitiesData addPlayer();

  IEntitiesData addEntities(EntityType<?>... entityTypes);

  IEntitiesData addEntities(TagKey<EntityType<?>>... entityTag);

  IEntitiesData addAllPresetSlots();

  IEntitiesData addAllPresetSlotsExcept(RegaliaSlotsApiSlotTypes.Preset... presets);

  IEntitiesData addPresetSlots(RegaliaSlotsApiSlotTypes.Preset... preset);

  IEntitiesData addSlots(String... slots);

  IEntitiesData addSlots(boolean create, ISlotData... slotData);

  IEntitiesData addSlots(ISlotData... slotData);

  IEntitiesData addCondition(ICondition condition);

  @ApiStatus.Internal
  IEntitiesData.Entry build();

  @Deprecated(forRemoval = true)
  JsonObject serialize(HolderLookup.Provider provider);

  interface Entry {

    Codec<IEntitiesData.Entry> CODEC = RegaliaSlotsApiServices.CODECS.entitiesDataEntryCodec();

    boolean replace();

    List<Either<TagKey<EntityType<?>>, ResourceKey<EntityType<?>>>> entities();

    List<Either<String, IEntitySlotEntry>> slots();

    List<ICondition> conditions();
  }

  interface IEntitySlotEntry {

    boolean create();

    ISlotData.Entry slot();
  }
}
