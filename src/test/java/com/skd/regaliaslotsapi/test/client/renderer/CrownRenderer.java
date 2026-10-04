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
import com.skd.regaliaslotsapi.test.client.model.CrownModel;

public class CrownRenderer implements ICurioRenderer.HumanoidRender {

  private static final Identifier CROWN_TEXTURE =
      Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID,
                                            "textures/entity/crown.png");
  private final CrownModel<HumanoidRenderState> model;

  public CrownRenderer() {
    this.model = new CrownModel<>(
        Minecraft.getInstance().getEntityModels().bakeLayer(RegaliaSlotsApiLayerDefinitions.CROWN));
  }

  @Override
  public HumanoidModel<HumanoidRenderState> getModel(ItemStack stack,
                                                               SlotContext slotContext) {
    return this.model;
  }

  @Override
  public Identifier getModelTexture(ItemStack stack, SlotContext slotContext) {
    return CROWN_TEXTURE;
  }
}
