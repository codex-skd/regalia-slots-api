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

package com.skd.regaliaslotsapi.api.internal;

import java.util.ServiceLoader;
import org.jetbrains.annotations.ApiStatus;
import com.skd.regaliaslotsapi.api.internal.services.IRegaliaSlotsApiCodecs;
import com.skd.regaliaslotsapi.api.internal.services.IRegaliaSlotsApiExtensions;
import com.skd.regaliaslotsapi.api.internal.services.IRegaliaSlotsApiNetwork;
import com.skd.regaliaslotsapi.api.internal.services.IRegaliaSlotsApiRegistry;
import com.skd.regaliaslotsapi.api.internal.services.IRegaliaSlotsApiSlots;

@ApiStatus.Internal
public class RegaliaSlotsApiServices {

  public static final IRegaliaSlotsApiCodecs CODECS = load(IRegaliaSlotsApiCodecs.class);
  public static final IRegaliaSlotsApiSlots SLOTS = load(IRegaliaSlotsApiSlots.class);
  public static final IRegaliaSlotsApiRegistry REGISTRY = load(IRegaliaSlotsApiRegistry.class);
  public static final IRegaliaSlotsApiExtensions EXTENSIONS = load(IRegaliaSlotsApiExtensions.class);
  public static final IRegaliaSlotsApiNetwork NETWORK = load(IRegaliaSlotsApiNetwork.class);

  public static <T> T load(Class<T> clazz) {
    return ServiceLoader.load(clazz)
        .findFirst()
        .orElseThrow(
            () -> new NullPointerException("Failed to load service for " + clazz.getName()));
  }
}
