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

package com.skd.regaliaslotsapi.mixin.core;

import java.util.Optional;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.triggers.Criterion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiTriggers;
import com.skd.regaliaslotsapi.api.SlotPredicate;
import com.skd.regaliaslotsapi.common.util.EquipCurioTrigger;

@Mixin(value = RegaliaSlotsApiTriggers.EquipBuilder.class, remap = false)
public class MixinRegaliaSlotsApiTriggersEquip {

  @Shadow
  private ItemPredicate.Builder itemPredicate;
  @Shadow
  private LocationPredicate.Builder locationPredicate;
  @Shadow
  private SlotPredicate.Builder slotPredicate;

  @Inject(at = @At("HEAD"), method = "build", cancellable = true)
  private void curios$equipAtLocation(
      CallbackInfoReturnable<Criterion<? extends CriterionTriggerInstance>> cir) {
    cir.setReturnValue(EquipCurioTrigger.INSTANCE.createCriterion(
        new EquipCurioTrigger.TriggerInstance(Optional.empty(),
            Optional.of(this.itemPredicate.build()), Optional.of(this.locationPredicate.build()),
            Optional.of(this.slotPredicate.build()))));
  }
}
