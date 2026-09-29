/*
 * Copyright (c) 2018-2020 C4
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
 */

package com.skd.regaliaslotsapi.test.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.client.ICurioRenderer;
import com.skd.regaliaslotsapi.api.type.capability.ICurio;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import com.skd.regaliaslotsapi.test.RegaliaSlotsApiTest;
import com.skd.regaliaslotsapi.test.client.RegaliaSlotsApiLayerDefinitions;
import com.skd.regaliaslotsapi.test.client.model.AmuletModel;

public class AmuletItem extends Item implements ICurioItem, ICurioRenderer {

  private static final Identifier AMULET_TEXTURE =
      Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID,
                                      "textures/entity/amulet.png");
  private Object model;

  public AmuletItem(Item.Properties properties) {
    super(properties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    LivingEntity living = slotContext.entity();

    if (!living.level().isClientSide() && living.tickCount % 40 == 0) {
      living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0, true, true));
    }
  }

  @Nonnull
  @Override
  public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD.value(), 1.0f, 1.0f);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
    return true;
  }

  @Override
  public boolean isFoil(@Nonnull ItemStack stack) {
    return true;
  }

  @Override
  public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
    return 3;
  }

  @Override
  public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
      ItemStack stack, SlotContext slotContext, PoseStack poseStack,
      SubmitNodeCollector submitNodeCollector, int packedLight, S renderState,
      RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context,
      float yRotation, float xRotation) {

    if (this.model == null) {
      this.model = new AmuletModel(
          Minecraft.getInstance().getEntityModels().bakeLayer(RegaliaSlotsApiLayerDefinitions.AMULET));
    }

    if (this.model instanceof AmuletModel amuletModel) {

      if (renderState instanceof HumanoidRenderState humanoidRenderState) {
        amuletModel.setupAnim(humanoidRenderState);
        submitNodeCollector.submitModel(amuletModel, humanoidRenderState, poseStack,
                                        RenderTypes.armorCutoutNoCull(AMULET_TEXTURE), packedLight,
                                        OverlayTexture.NO_OVERLAY, 0, null);
      }
    }
  }
}
