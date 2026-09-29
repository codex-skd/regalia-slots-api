/*
 * Copyright (c) 2018-2024 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package top.theillusivec4.curios.client.render;

import com.skd.regaliaslotsapi.client.render.RegaliaSlotsApiLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;

/**
 * Binary-compatibility shim so third-party mods (e.g. Epic Fight) that reference Curios' internal
 * render layer class resolve it and find it on the player renderer. This is the layer actually
 * registered on player renderers; all rendering logic lives in {@link RegaliaSlotsApiLayer}.
 */
public class CuriosLayer<T extends LivingEntity, M extends EntityModel<T>>
    extends RegaliaSlotsApiLayer<T, M> {

  public CuriosLayer(RenderLayerParent<T, M> renderer) {
    super(renderer);
  }
}
