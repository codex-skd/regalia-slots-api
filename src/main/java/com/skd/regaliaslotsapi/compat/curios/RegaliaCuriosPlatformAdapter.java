package com.skd.regaliaslotsapi.compat.curios;

import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.skd.regaliaslotsapi.platform.Services;
import top.theillusivec4.curios.platform.services.ICuriosPlatform;

/**
 * Service provider that bridges the {@code top.theillusivec4.curios.platform.services.ICuriosPlatform}
 * SPI to Regalia's own platform service, allowing third-party mods coded against the real Curios
 * API to resolve special item behaviour through the same entry point.
 */
public class RegaliaCuriosPlatformAdapter implements ICuriosPlatform {

  @Override
  public Map<String, top.theillusivec4.curios.api.type.ISlotType> getItemStackSlots(
      ItemStack stack, @Nullable LivingEntity livingEntity) {
    // Delegate to Regalia's real slot resolution, then wrap each result in the verbatim
    // Curios ISlotType the caller expects (the two interfaces are identical copies).
    return Services.CURIOS.getItemStackSlots(stack, livingEntity).entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> new ShimSlotType(e.getValue())));
  }

  @Override
  public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity livingEntity) {
    return Services.CURIOS.makesPiglinsNeutral(stack, livingEntity);
  }

  @Override
  public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity livingEntity) {
    return Services.CURIOS.canWalkOnPowderedSnow(stack, livingEntity);
  }

  @Override
  public boolean isEnderMask(ItemStack stack, Player player, EnderMan enderMan) {
    return Services.CURIOS.isEnderMask(stack, player, enderMan);
  }
}
