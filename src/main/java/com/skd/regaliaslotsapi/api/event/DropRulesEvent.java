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

package com.skd.regaliaslotsapi.api.event;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import com.skd.regaliaslotsapi.api.common.DropRule;
import com.skd.regaliaslotsapi.api.type.capability.IRegaliaSlotsApiItemHandler;

/**
 * This event is fired when an entity's death causes dropped curios to appear.
 *
 * <p>This event is fired whenever an entity dies and drops items in
 * {@link LivingEntity#die(DamageSource)}.
 *
 * <p>This event is fired inside the
 * {@link net.neoforged.neoforge.event.entity.living.LivingDropsEvent}.
 *
 * <br>{@link #source} contains the DamageSource that caused the drop to occur.
 * <br>{@link #recentlyHit}determines whether the entity doing the drop has recently been damaged.
 *
 * <p>This event is fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.
 **/
public class DropRulesEvent extends LivingEvent {

  private final DamageSource source;
  private final int lootingLevel;
  private final boolean recentlyHit;
  private final IRegaliaSlotsApiItemHandler curioHandler; // Curio handler for the entity
  private final List<Pair<Predicate<ItemStack>, DropRule>> overrides = new ArrayList<>();
  // List of drop rule overrides

  public DropRulesEvent(LivingEntity entity, IRegaliaSlotsApiItemHandler handler, DamageSource source,
                        int lootingLevel, boolean recentlyHit) {
    super(entity);
    this.source = source;
    this.lootingLevel = lootingLevel;
    this.recentlyHit = recentlyHit;
    this.curioHandler = handler;
  }

  public DamageSource getSource() {
    return source;
  }

  public int getLootingLevel() {
    return lootingLevel;
  }

  public boolean isRecentlyHit() {
    return recentlyHit;
  }

  public IRegaliaSlotsApiItemHandler getCurioHandler() {
    return curioHandler;
  }

  /**
   * Adds an override {@link DropRule} for the given
   * predicate. Each predicate will be applied to each ItemStack and those that pass will be given
   * the paired DropRule.
   *
   * @param predicate The ItemStack predicate to apply for the DropRule
   * @param dropRule  The DropRule to use as an override. This can be overridden further so there is
   *                  no guarantee for the final result.
   */
  public void addOverride(Predicate<ItemStack> predicate, DropRule dropRule) {
    overrides.add(new Pair<>(predicate, dropRule));
  }

  public ImmutableList<Pair<Predicate<ItemStack>, DropRule>> getOverrides() {
    return ImmutableList.copyOf(overrides);
  }
}
