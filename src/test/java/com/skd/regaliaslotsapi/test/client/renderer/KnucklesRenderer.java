package com.skd.regaliaslotsapi.test.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.client.ICurioRenderer;
import com.skd.regaliaslotsapi.test.RegaliaSlotsApiTest;
import com.skd.regaliaslotsapi.test.client.RegaliaSlotsApiLayerDefinitions;
import com.skd.regaliaslotsapi.test.client.model.KnucklesModel;

public class KnucklesRenderer implements ICurioRenderer.HumanoidRender {

  private static final Identifier KNUCKLES_TEXTURE =
      Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID,
                                            "textures/entity/knuckles.png");

  private final KnucklesModel model;

  public KnucklesRenderer() {
    this.model = new KnucklesModel(
        Minecraft.getInstance().getEntityModels().bakeLayer(RegaliaSlotsApiLayerDefinitions.KNUCKLES));
  }

  @Override
  public HumanoidModel<HumanoidRenderState> getModel(ItemStack stack,
                                                               SlotContext slotContext) {
    return this.model;
  }

  @Override
  public Identifier getModelTexture(ItemStack stack, SlotContext slotContext) {
    return KNUCKLES_TEXTURE;
  }
}
