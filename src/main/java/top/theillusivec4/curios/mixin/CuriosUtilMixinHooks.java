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
 * COMPAT NOTE: This class is a binary-compatibility shim for third-party mods
 * that mix into Curios' internal mixin hooks. It delegates to the renamed
 * RegaliaSlotsApiImplMixinHooks implementation.
 */

package top.theillusivec4.curios.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.skd.regaliaslotsapi.api.type.capability.ICuriosItemHandler;
import com.skd.regaliaslotsapi.mixin.RegaliaSlotsApiImplMixinHooks;
import com.skd.regaliaslotsapi.common.capability.CurioInventory;

public class CuriosUtilMixinHooks {

  public static final String CURIO_INVENTORY_KEY = "Curios";

  public static void loadCurioInventory(CompoundTag compound, LivingEntity livingEntity, HolderLookup.Provider provider) {
    ICuriosItemHandler handler = RegaliaSlotsApiImplMixinHooks.getCuriosInventory(livingEntity).orElse(null);
    if (handler instanceof CurioInventory inventory) {
      CompoundTag nbt = compound.getCompound(CURIO_INVENTORY_KEY);
      inventory.deserializeNBT(provider, nbt);
    }
  }

  public static void saveCurioInventory(CompoundTag compound, LivingEntity livingEntity, HolderLookup.Provider provider) {
    ICuriosItemHandler handler = RegaliaSlotsApiImplMixinHooks.getCuriosInventory(livingEntity).orElse(null);
    if (handler instanceof CurioInventory inventory) {
      CompoundTag nbt = inventory.serializeNBT(provider);
      ListTag list = nbt.getList(CURIO_INVENTORY_KEY, Tag.TAG_COMPOUND);
      compound.put(CURIO_INVENTORY_KEY, list);
    }
  }

  // ItemStack NBT access in 1.21.1 uses Data Components; these are stubs for binary compat
  // Third-party mods mixing into CuriosUtilMixinHooks for per-item NBT should use capabilities instead
  public static ListTag getCurioTag(ItemStack stack) {
    return new ListTag();
  }

  public static void setCurioTag(ItemStack stack, ListTag tag) {
    // no-op in 1.21.1 (Data Components)
  }

  public static boolean hasCurioTag(ItemStack stack) {
    return false;
  }
}