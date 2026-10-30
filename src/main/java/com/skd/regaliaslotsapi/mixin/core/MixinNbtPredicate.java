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

import net.minecraft.advancements.predicates.NbtPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.TagValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.skd.regaliaslotsapi.mixin.RegaliaSlotsApiCommonMixinHooks;

@Mixin(NbtPredicate.class)
public class MixinNbtPredicate {

  @Inject(
      at = @At(
          value = "INVOKE",
          target = "net/minecraft/world/level/storage/TagValueOutput.buildResult()"
              + "Lnet/minecraft/nbt/CompoundTag;"),
      method = "getEntityTagToCompare",
      locals = LocalCapture.CAPTURE_FAILSOFT
  )
  private static void curios$mergeRegaliaSlotsApiInventory(Entity entity,
                                                  CallbackInfoReturnable<CompoundTag> cir,
                                                  ProblemReporter.ScopedCollector reporter,
                                                  TagValueOutput output) {
    RegaliaSlotsApiCommonMixinHooks.mergeRegaliaSlotsApiInventory(reporter, output.buildResult(), entity);
  }
}