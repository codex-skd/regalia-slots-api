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

package com.skd.regaliaslotsapi.common;

import java.util.function.Supplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.skd.regaliaslotsapi.api.CurioAttributeModifiers;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.common.capability.CurioInventory;
import com.skd.regaliaslotsapi.common.inventory.container.RegaliaSlotsApiContainer;
import com.skd.regaliaslotsapi.common.util.EquipCurioTrigger;
import com.skd.regaliaslotsapi.common.util.SetCurioAttributesFunction;
import com.skd.regaliaslotsapi.server.command.CurioArgumentType;

public class RegaliaSlotsApiRegistry {

  private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
      DeferredRegister.create(
          NeoForgeRegistries.Keys.ATTACHMENT_TYPES, RegaliaSlotsApi.MODID);
  private static final DeferredRegister<CriterionTrigger<?>> CRITERION_TRIGGERS =
      DeferredRegister.create(Registries.TRIGGER_TYPE, RegaliaSlotsApi.MODID);
  // Compat: mods built against the real Curios API hard-code the trigger id
  // `curios:equip_curio` in their advancement JSON. We expose the same trigger
  // under the `curios` namespace so those advancements resolve. NeoForge allows
  // registering into another mod's namespace from our own RegisterEvent.
  private static final DeferredRegister<CriterionTrigger<?>> CURIOS_COMPAT_TRIGGERS =
      DeferredRegister.create(Registries.TRIGGER_TYPE, "curios");
  private static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES =
      DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, RegaliaSlotsApi.MODID);
  private static final DeferredRegister<MenuType<?>> MENU_TYPES =
      DeferredRegister.create(Registries.MENU, RegaliaSlotsApi.MODID);
  private static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS =
      DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, RegaliaSlotsApi.MODID);
  private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
      DeferredRegister.createDataComponents(RegaliaSlotsApi.MODID);

  public static final Supplier<ArgumentTypeInfo<?, ?>> CURIO_SLOT_ARGUMENT =
      ARGUMENT_TYPES.register("slot_type",
          () -> ArgumentTypeInfos.registerByClass(CurioArgumentType.class,
              SingletonArgumentInfo.contextFree(CurioArgumentType::slot)));

  public static final Supplier<MenuType<RegaliaSlotsApiContainer>> CURIO_MENU =
      MENU_TYPES.register("curios_container",
          () -> IMenuTypeExtension.create(RegaliaSlotsApiContainer::new));
  public static final Supplier<LootItemFunctionType<SetCurioAttributesFunction>> CURIO_ATTRIBUTES =
      LOOT_FUNCTIONS.register("set_curio_attributes",
          () -> new LootItemFunctionType<>(SetCurioAttributesFunction.CODEC));
  public static final Supplier<EquipCurioTrigger> EQUIP_TRIGGER =
      CRITERION_TRIGGERS.register("equip_curio", () -> EquipCurioTrigger.INSTANCE);
  // Distinct instance, exposed as `curios:equip_curio` for Curios-native datapacks/mods. It cannot
  // reuse EquipCurioTrigger.INSTANCE: MappedRegistry forbids the same value under two keys. Runtime
  // triggers fan out to both instances (see EquipCurioTrigger#fire).
  public static final Supplier<EquipCurioTrigger> CURIOS_EQUIP_TRIGGER =
      CURIOS_COMPAT_TRIGGERS.register("equip_curio", () -> EquipCurioTrigger.CURIOS_COMPAT_INSTANCE);

  public static final Supplier<AttachmentType<CurioInventory>> INVENTORY =
      ATTACHMENT_TYPES.register("inventory",
          () -> AttachmentType.serializable(CurioInventory::new)
              .copyOnDeath()
              .build());
  public static final Supplier<DataComponentType<CurioAttributeModifiers>>
      CURIO_ATTRIBUTE_MODIFIERS = DATA_COMPONENTS.register("attribute_modifiers",
      () -> DataComponentType.<CurioAttributeModifiers>builder()
          .persistent(CurioAttributeModifiers.CODEC)
          .networkSynchronized(CurioAttributeModifiers.STREAM_CODEC)
          .cacheEncoding()
          .build());

  public static void init(IEventBus eventBus) {
    ARGUMENT_TYPES.register(eventBus);
    MENU_TYPES.register(eventBus);
    LOOT_FUNCTIONS.register(eventBus);
    ATTACHMENT_TYPES.register(eventBus);
    CRITERION_TRIGGERS.register(eventBus);
    CURIOS_COMPAT_TRIGGERS.register(eventBus);
    DATA_COMPONENTS.register(eventBus);
  }
}
