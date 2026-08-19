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

package com.skd.regaliaslotsapi.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import com.skd.regaliaslotsapi.api.SlotResult;
import com.skd.regaliaslotsapi.api.client.ICurioRenderer;

public class RegaliaSlotsApiLayer<S extends LivingEntityRenderState, M extends EntityModel<? super S>>
    extends RenderLayer<S, M> {

  private final RenderLayerParent<S, M> renderLayerParent;
  private final EntityRendererProvider.Context context;

  public RegaliaSlotsApiLayer(RenderLayerParent<S, M> renderer,
                     EntityRendererProvider.Context context) {
    super(renderer);
    this.renderLayerParent = renderer;
    this.context = context;
  }

  @Override
  public void submit(@Nonnull PoseStack poseStack,
                     @Nonnull SubmitNodeCollector nodeCollector,
                     int packedLight,
                     @Nonnull S renderState,
                     float vertRot,
                     float horizRot) {
    poseStack.pushPose();
    List<SlotResult> slots =
        renderState.getRenderDataOrDefault(RegaliaSlotsApiClientMod.CUSTOM_RENDER, List.of());

    for (SlotResult slot : slots) {
      ICurioRenderer.get(slot.stack()).render(
          slot.stack(),
          slot.slotContext(),
          poseStack,
          nodeCollector,
          packedLight,
          renderState,
          this.renderLayerParent,
          this.context,
          vertRot,
          horizRot
      );
    }
    poseStack.popPose();
  }
}
