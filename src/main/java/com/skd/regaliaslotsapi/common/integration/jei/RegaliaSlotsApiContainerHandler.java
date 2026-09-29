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

package com.skd.regaliaslotsapi.common.integration.jei;

import java.util.List;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;
import org.jspecify.annotations.NonNull;
import com.skd.regaliaslotsapi.client.screen.RegaliaSlotsApiScreen;
import com.skd.regaliaslotsapi.common.integration.RegaliaSlotsApiExclusionAreas;

public class RegaliaSlotsApiContainerHandler implements IGuiContainerHandler<RegaliaSlotsApiScreen> {

  @Override
  @NonNull
  public List<Rect2i> getGuiExtraAreas(@NonNull RegaliaSlotsApiScreen screen) {
    return RegaliaSlotsApiExclusionAreas.create(screen);
  }
}

