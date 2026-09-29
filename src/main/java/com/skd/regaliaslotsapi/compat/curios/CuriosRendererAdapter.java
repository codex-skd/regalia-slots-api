package com.skd.regaliaslotsapi.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Adapts between the two identical-but-separate {@code ICurioRenderer} / {@code SlotContext}
 * type hierarchies so that third-party Curios renderers registered via
 * {@code top.theillusivec4.curios.api.client.CuriosRendererRegistry} are visible to
 * {@code com.skd.regaliaslotsapi.api.client.RegaliaSlotsApiRendererRegistry}.
 * <p>
 * The {@code render} method is generic, so it cannot be implemented with a lambda; anonymous
 * classes are used instead.
 */
public final class CuriosRendererAdapter {

  private CuriosRendererAdapter() {
  }

  /**
   * Adapts a {@code top.theillusivec4} renderer into a {@code com.skd} renderer.
   */
  public static com.skd.regaliaslotsapi.api.client.ICurioRenderer toRegalia(
      top.theillusivec4.curios.api.client.ICurioRenderer original) {

    return new com.skd.regaliaslotsapi.api.client.ICurioRenderer() {
      @Override
      public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
          com.skd.regaliaslotsapi.api.SlotContext slotContext, PoseStack matrixStack,
          RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light,
          float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
          float netHeadYaw, float headPitch) {
        original.render(stack,
            new top.theillusivec4.curios.api.SlotContext(slotContext.identifier(),
                slotContext.entity(), slotContext.index(), slotContext.cosmetic(),
                slotContext.visible()),
            matrixStack, renderLayerParent, renderTypeBuffer, light, limbSwing, limbSwingAmount,
            partialTicks, ageInTicks, netHeadYaw, headPitch);
      }
    };
  }

  /**
   * Adapts a {@code com.skd} renderer into a {@code top.theillusivec4} renderer.
   * Used only when callers still query the old {@code CuriosRendererRegistry.getRenderer()}.
   */
  public static top.theillusivec4.curios.api.client.ICurioRenderer toLegacy(
      com.skd.regaliaslotsapi.api.client.ICurioRenderer regalia) {

    return new top.theillusivec4.curios.api.client.ICurioRenderer() {
      @Override
      public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
          top.theillusivec4.curios.api.SlotContext slotContext, PoseStack matrixStack,
          RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light,
          float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
          float netHeadYaw, float headPitch) {
        regalia.render(stack,
            new com.skd.regaliaslotsapi.api.SlotContext(slotContext.identifier(),
                slotContext.entity(), slotContext.index(), slotContext.cosmetic(),
                slotContext.visible()),
            matrixStack, renderLayerParent, renderTypeBuffer, light, limbSwing, limbSwingAmount,
            partialTicks, ageInTicks, netHeadYaw, headPitch);
      }
    };
  }
}
