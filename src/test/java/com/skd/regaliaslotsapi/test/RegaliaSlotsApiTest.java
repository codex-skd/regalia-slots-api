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

package com.skd.regaliaslotsapi.test;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.core.Holder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import com.skd.regaliaslotsapi.api.CurioAttributeModifiers;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiSlotTypes;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.client.ICurioRenderer;
import com.skd.regaliaslotsapi.api.common.DropRule;
import com.skd.regaliaslotsapi.api.event.CurioAttributeModifierEvent;
import com.skd.regaliaslotsapi.api.extensions.ICurioSlotExtension;
import com.skd.regaliaslotsapi.api.extensions.RegisterRegaliaSlotsApiExtensionsEvent;
import com.skd.regaliaslotsapi.api.type.capability.ICurio;
import com.skd.regaliaslotsapi.test.client.RegaliaSlotsApiLayerDefinitions;
import com.skd.regaliaslotsapi.test.client.model.AmuletModel;
import com.skd.regaliaslotsapi.test.client.model.CrownModel;
import com.skd.regaliaslotsapi.test.client.model.KnucklesModel;
import com.skd.regaliaslotsapi.test.client.renderer.CrownRenderer;
import com.skd.regaliaslotsapi.test.client.renderer.KnucklesRenderer;
import com.skd.regaliaslotsapi.test.common.RegaliaSlotsApiTestRegistry;
import com.skd.regaliaslotsapi.test.common.item.AmuletItem;
import com.skd.regaliaslotsapi.test.data.RegaliaSlotsApiGenerator;
import com.skd.regaliaslotsapi.test.data.RegaliaSlotsApiTestProvider;

@Mod(RegaliaSlotsApiTest.MODID)
public class RegaliaSlotsApiTest {

  public static final String MODID = "regalia_slots_api_test";
  public static final Logger LOGGER = LogManager.getLogger();

  public RegaliaSlotsApiTest(IEventBus eventBus) {
    RegaliaSlotsApiTestRegistry.init(eventBus);
    eventBus.addListener(this::clientSetup);
    eventBus.addListener(this::registerLayers);
    eventBus.addListener(this::creativeTab);
    eventBus.addListener(this::registerCaps);
    eventBus.addListener(this::gatherData);
    eventBus.addListener(this::registerSlotExtensions);
    NeoForge.EVENT_BUS.addListener(this::attributeModifier);
    RegaliaSlotsApiSlotTypes.registerPredicate(Identifier.fromNamespaceAndPath(MODID, "test"),
                                     (ctx, stack) -> stack.getItem() == Items.OAK_BOAT);
  }

  private void registerSlotExtensions(final RegisterRegaliaSlotsApiExtensionsEvent evt) {
    evt.registerSlotExtension(new ICurioSlotExtension() {
      @Override
      public ItemStack getDisplayStack(SlotContext slotContext, ItemStack defaultStack) {
        return Items.DIAMOND_AXE.getDefaultInstance();
      }
    }, "test");
  }

  private void gatherData(final GatherDataEvent.Client evt) {
    DataGenerator generator = evt.getGenerator();
    generator.addProvider(true, new AdvancementProvider(generator.getPackOutput(),
                                                        evt.getLookupProvider(),
                                                        List.of(new RegaliaSlotsApiGenerator())));
    generator.addProvider(true, new RegaliaSlotsApiTestProvider("regalia_slots_api_test", generator.getPackOutput(),
                                                       evt.getLookupProvider()));
  }

  private void registerCaps(final RegisterCapabilitiesEvent evt) {
    evt.registerItem(RegaliaSlotsApiCapability.ITEM, (stack, ctx) -> new ICurio() {

      @Override
      public ItemStack getStack() {
        return stack;
      }

      @Override
      public void curioTick(SlotContext slotContext) {
        LivingEntity livingEntity = slotContext.entity();

        if (livingEntity.level() instanceof ServerLevel serverLevel &&
            livingEntity.tickCount % 20 == 0) {
          livingEntity.addEffect(
              new MobEffectInstance(MobEffects.NIGHT_VISION, 300, -1, true, true));
          stack.hurtAndBreak(1, serverLevel, livingEntity,
                             item -> RegaliaSlotsApi.broadcastCurioBreakEvent(slotContext));
        }
      }
    }, RegaliaSlotsApiTestRegistry.CROWN.get());

    evt.registerItem(RegaliaSlotsApiCapability.ITEM, (stack, ctx) -> new ICurio() {

      @Override
      public void curioTick(SlotContext slotContext) {
        LivingEntity livingEntity = slotContext.entity();

        if (!livingEntity.level().isClientSide() && livingEntity.tickCount % 19 == 0) {
          livingEntity.addEffect(new MobEffectInstance(MobEffects.HASTE, 20, 0, true, true));
        }
      }

      @Override
      public ItemStack getStack() {
        return stack;
      }

      @Override
      public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
          SlotContext slotContext, Identifier id) {
        Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED,
                 new AttributeModifier(
                     Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "speed_bonus"), 0.1,
                     AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        atts.put(Attributes.ARMOR,
                 new AttributeModifier(
                     Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "armor_bonus"), 2,
                     AttributeModifier.Operation.ADD_VALUE));
        atts.put(Attributes.KNOCKBACK_RESISTANCE,
                 new AttributeModifier(
                     Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "knockback_resist"),
                     0.2,
                     AttributeModifier.Operation.ADD_VALUE));
        RegaliaSlotsApi.addSlotModifier(atts, "ring", id, 1, AttributeModifier.Operation.ADD_VALUE);
        RegaliaSlotsApi.addSlotModifier(atts, "curio", id, -1, AttributeModifier.Operation.ADD_VALUE);
        return atts;
      }

      @Nonnull
      @Override
      public DropRule getDropRule(SlotContext slotContext, DamageSource source,
                                  boolean recentlyHit) {
        return DropRule.ALWAYS_KEEP;
      }

      @Nonnull
      @Override
      public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD.value(), 1.0f, 1.0f);
      }

      @Override
      public boolean canEquipFromUse(SlotContext slot) {
        return true;
      }

      @Override
      public boolean makesPiglinsNeutral(SlotContext slotContext) {
        return true;
      }

      @Override
      public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan) {
        return true;
      }

      @Override
      public int getFortuneLevel(SlotContext slotContext, @Nullable LootContext lootContext) {
        return 3;
      }

      @Override
      public int getLootingLevel(SlotContext slotContext, @Nullable LootContext lootContext) {
        return 3;
      }
    }, RegaliaSlotsApiTestRegistry.RING.get());

    evt.registerItem(RegaliaSlotsApiCapability.ITEM, (stack, ctx) -> new ICurio() {

      @Override
      public ItemStack getStack() {
        return stack;
      }

      @Override
      public CurioAttributeModifiers getDefaultCurioAttributeModifiers() {
        return CurioAttributeModifiers.builder()
            .addModifier(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                    Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "knuckles"),
                    4,
                    AttributeModifier.Operation.ADD_VALUE)
            )
            .addSlotModifier(
                RegaliaSlotsApiSlotTypes.Preset.RING.id(),
                new AttributeModifier(
                    Identifier.fromNamespaceAndPath(RegaliaSlotsApiTest.MODID, "knuckles"),
                    2,
                    AttributeModifier.Operation.ADD_VALUE))
            .build();
      }

      @Override
      public int getLootingLevel(SlotContext slotContext, @Nullable LootContext lootContext) {
        return 10;
      }
    }, RegaliaSlotsApiTestRegistry.KNUCKLES.get());
  }

  private void attributeModifier(final CurioAttributeModifierEvent evt) {

//    if (evt.getSlotContext().identifier().equals("curio")) {
//      evt.clearModifiers();
//      evt.addModifier(Attributes.MAX_HEALTH,
//          new AttributeModifier(Identifier.withDefaultNamespace("test"), 10.0d,
//              AttributeModifier.Operation.ADD_VALUE));
//      evt.addModifier(SlotAttribute.getOrCreate("ring"),
//          new AttributeModifier(Identifier.withDefaultNamespace("test"), 1.0d,
//              AttributeModifier.Operation.ADD_VALUE));
//    }
  }

  private void creativeTab(final BuildCreativeModeTabContentsEvent evt) {

    if (evt.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
      Collection<ItemLike> items =
          List.of(RegaliaSlotsApiTestRegistry.AMULET.get(), RegaliaSlotsApiTestRegistry.CROWN.get(),
                  RegaliaSlotsApiTestRegistry.KNUCKLES.get(), RegaliaSlotsApiTestRegistry.RING.get());

      for (ItemLike item : items) {
        evt.accept(item);
      }
    }
  }

  private void clientSetup(final FMLClientSetupEvent evt) {
    ICurioRenderer.register(RegaliaSlotsApiTestRegistry.AMULET.get(),
                            () -> (AmuletItem) RegaliaSlotsApiTestRegistry.AMULET.get());
    ICurioRenderer.register(RegaliaSlotsApiTestRegistry.CROWN.get(), CrownRenderer::new);
    ICurioRenderer.register(RegaliaSlotsApiTestRegistry.KNUCKLES.get(), KnucklesRenderer::new);
  }

  private void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
    evt.registerLayerDefinition(RegaliaSlotsApiLayerDefinitions.CROWN, CrownModel::createLayer);
    evt.registerLayerDefinition(RegaliaSlotsApiLayerDefinitions.AMULET, AmuletModel::createLayer);
    evt.registerLayerDefinition(RegaliaSlotsApiLayerDefinitions.KNUCKLES, KnucklesModel::createLayer);
  }
}
