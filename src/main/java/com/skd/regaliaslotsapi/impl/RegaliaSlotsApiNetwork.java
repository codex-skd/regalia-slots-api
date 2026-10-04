package com.skd.regaliaslotsapi.impl;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.internal.services.IRegaliaSlotsApiNetwork;
import com.skd.regaliaslotsapi.common.network.server.SPacketBreak;

public class RegaliaSlotsApiNetwork implements IRegaliaSlotsApiNetwork {

  @Override
  public void breakCurioInSlot(SlotContext slotContext) {
    LivingEntity livingEntity = slotContext.entity();

    if (livingEntity != null) {
      PacketDistributor
          .sendToPlayersTrackingEntityAndSelf(livingEntity,
                                              new SPacketBreak(livingEntity.getId(),
                                                               slotContext.identifier(),
                                                               slotContext.index()));
    }
  }
}
