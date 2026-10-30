package com.skd.regaliaslotsapi.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Bridges renderer registrations made by third-party mods through
 * {@code top.theillusivec4.curios.api.client.ICurioRenderer} into Regalia's own renderer registry,
 * so mods compiled against the real Curios API keep rendering their curios.
 */
public class RegaliaClientExtensionsAdapter
    implements top.theillusivec4.curios.api.internal.services.client.ICuriosClientExtensions {

  @Override
  public void registerCurioRenderer(
      Item item, Supplier<top.theillusivec4.curios.api.client.ICurioRenderer> curioRenderer) {
    com.skd.regaliaslotsapi.api.client.ICurioRenderer.register(item,
        () -> toRegalia(curioRenderer.get()));
  }

  @Nullable
  @Override
  public top.theillusivec4.curios.api.client.ICurioRenderer getCurioRenderer(Item item) {
    com.skd.regaliaslotsapi.api.client.ICurioRenderer renderer =
        com.skd.regaliaslotsapi.api.client.ICurioRenderer.getOrNull(item);
    return renderer == null ? null : toCurios(renderer);
  }

  private static com.skd.regaliaslotsapi.api.client.ICurioRenderer toRegalia(
      top.theillusivec4.curios.api.client.ICurioRenderer renderer) {
    return new com.skd.regaliaslotsapi.api.client.ICurioRenderer() {

      @Override
      public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
          ItemStack stack,
          com.skd.regaliaslotsapi.api.SlotContext slotContext,
          PoseStack poseStack,
          SubmitNodeCollector submitNodeCollector,
          int packedLight,
          S renderState,
          RenderLayerParent<S, M> renderLayerParent,
          EntityRendererProvider.Context context,
          float yRotation,
          float xRotation) {
        renderer.render(stack, RegaliaTypeBridge.toCurios(slotContext), poseStack,
            submitNodeCollector, packedLight, renderState, renderLayerParent, context, yRotation,
            xRotation);
      }

      @Override
      public void renderFirstPersonHand(
          ItemStack stack,
          com.skd.regaliaslotsapi.api.SlotContext slotContext,
          HumanoidArm arm,
          PoseStack poseStack,
          SubmitNodeCollector submitNodeCollector,
          AvatarRenderState avatarRenderState,
          AbstractClientPlayer clientPlayer,
          int packedLight) {
        renderer.renderFirstPersonHand(stack, RegaliaTypeBridge.toCurios(slotContext), arm,
            poseStack, submitNodeCollector, avatarRenderState, clientPlayer, packedLight);
      }
    };
  }

  private static top.theillusivec4.curios.api.client.ICurioRenderer toCurios(
      com.skd.regaliaslotsapi.api.client.ICurioRenderer renderer) {
    return new top.theillusivec4.curios.api.client.ICurioRenderer() {

      @Override
      public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
          ItemStack stack,
          top.theillusivec4.curios.api.SlotContext slotContext,
          PoseStack poseStack,
          SubmitNodeCollector submitNodeCollector,
          int packedLight,
          S renderState,
          RenderLayerParent<S, M> renderLayerParent,
          EntityRendererProvider.Context context,
          float yRotation,
          float xRotation) {
        renderer.render(stack, RegaliaTypeBridge.toRegalia(slotContext), poseStack,
            submitNodeCollector, packedLight, renderState, renderLayerParent, context, yRotation,
            xRotation);
      }

      @Override
      public void renderFirstPersonHand(
          ItemStack stack,
          top.theillusivec4.curios.api.SlotContext slotContext,
          HumanoidArm arm,
          PoseStack poseStack,
          SubmitNodeCollector submitNodeCollector,
          AvatarRenderState avatarRenderState,
          AbstractClientPlayer clientPlayer,
          int packedLight) {
        renderer.renderFirstPersonHand(stack, RegaliaTypeBridge.toRegalia(slotContext), arm,
            poseStack, submitNodeCollector, avatarRenderState, clientPlayer, packedLight);
      }
    };
  }
}
