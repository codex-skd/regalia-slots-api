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

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

/**
 * This event is fired when the slot size is dynamically changed during gameplay through slot
 * modifiers.
 *
 * <p>This event is fired on both the client and the server.
 *
 * <br>{@link #types} contains the affected {@link com.skd.regaliaslotsapi.api.type.ISlotType}.
 *
 * <p>This event is fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.
 **/
public class SlotModifiersUpdatedEvent extends LivingEvent {

  private final Set<String> types;

  public SlotModifiersUpdatedEvent(LivingEntity livingEntity, Set<String> types) {
    super(livingEntity);
    this.types = types;
  }

  public Set<String> getTypes() {
    return ImmutableSet.copyOf(this.types);
  }
}
