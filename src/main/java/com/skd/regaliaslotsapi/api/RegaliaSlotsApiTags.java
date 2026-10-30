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

package com.skd.regaliaslotsapi.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

/**
 * Collection of tags used by RegaliaSlotsApi.
 */
public final class RegaliaSlotsApiTags {

  /**
   * Items worn on the back, such as capes or backpacks.
   */
  public static final TagKey<Item> BACK = createItemTag(RegaliaSlotsApiSlotTypes.Preset.BACK);

  /**
   * Items worn around the waist, such as belts or pouches.
   */
  public static final TagKey<Item> BELT = createItemTag(RegaliaSlotsApiSlotTypes.Preset.BELT);

  /**
   * Items worn on the torso/chest, such as cloaks or shirts.
   */
  public static final TagKey<Item> BODY = createItemTag(RegaliaSlotsApiSlotTypes.Preset.BODY);

  /**
   * Items worn around the wrist, such as bands or bracelets.
   */
  public static final TagKey<Item> BRACELET = createItemTag(RegaliaSlotsApiSlotTypes.Preset.BRACELET);

  /**
   * Miscellaneous items that are not strongly associated with a specific body part or usage type.
   */
  public static final TagKey<Item> CHARM = createItemTag(RegaliaSlotsApiSlotTypes.Preset.CHARM);

  /**
   * Universal items that are able to equip or be equipped into any slot type.
   */
  public static final TagKey<Item> CURIO = createItemTag(RegaliaSlotsApiSlotTypes.Preset.CURIO);

  /**
   * Items worn on the feet, such as shoes or boots.
   */
  public static final TagKey<Item> FEET = createItemTag(RegaliaSlotsApiSlotTypes.Preset.FEET);

  /**
   * Items worn on the hands, such as gloves or gauntlets.
   */
  public static final TagKey<Item> HANDS = createItemTag(RegaliaSlotsApiSlotTypes.Preset.HANDS);

  /**
   * Items worn on top of the head, such as crowns or hats.
   */
  public static final TagKey<Item> HEAD = createItemTag(RegaliaSlotsApiSlotTypes.Preset.HEAD);

  /**
   * Items worn around the neck, such as amulets or necklaces.
   */
  public static final TagKey<Item> NECKLACE = createItemTag(RegaliaSlotsApiSlotTypes.Preset.NECKLACE);

  /**
   * Items worn on the fingers, such as rings.
   */
  public static final TagKey<Item> RING = createItemTag(RegaliaSlotsApiSlotTypes.Preset.RING);

  /**
   * Items that are to be specifically excluded from being accepted by the universal curio slot.
   *
   * <p>This is not the same concept as the one related to the {@link #CURIO} item tag. That tag is
   * for universal items being accepted into any slot type. This tag is related to universal slots
   * that can accept any curio item, which is what this tag is excluding items from.
   */
  public static final TagKey<Item> GENERIC_EXCLUSIONS = createItemTag("generic_exclusions");

  /**
   * Entity types that should be treated like players (including players themselves) for the
   * purposes of curio item classification, such as armor stands.
   */
  public static final TagKey<EntityType<?>> PLAYER_LIKE = createEntityTypeTag("player_like");

  /**
   * Creates an entity type tag key using the RegaliaSlotsApi namespace.
   *
   * @param id The path for the tag.
   * @return An entity type tag key.
   */
  public static TagKey<EntityType<?>> createEntityTypeTag(String id) {
    return TagKey.create(Registries.ENTITY_TYPE, RegaliaSlotsApiResources.resource(id));
  }

  /**
   * Creates an item tag key using the RegaliaSlotsApi namespace and associated with the given
   * {@link RegaliaSlotsApiSlotTypes.Preset}.
   *
   * <p>The path of each preset will be consistent with the result of
   * {@link RegaliaSlotsApiSlotTypes.Preset#id()}.
   *
   * @param preset The slot identifier
   * @return An item tag key.
   */
  public static TagKey<Item> createItemTag(RegaliaSlotsApiSlotTypes.Preset preset) {
    return createItemTag(preset.id());
  }

  /**
   * Creates an item tag key using the RegaliaSlotsApi namespace.
   *
   * @param id The path for the tag.
   * @return An item tag key.
   */
  public static TagKey<Item> createItemTag(String id) {
    return TagKey.create(Registries.ITEM, RegaliaSlotsApiResources.resource(id));
  }
}
