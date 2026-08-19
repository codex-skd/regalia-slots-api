package com.skd.regaliaslotsapi.api.internal.services;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.ApiStatus;
import com.skd.regaliaslotsapi.api.SlotAttribute;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.data.IEntitiesData;
import com.skd.regaliaslotsapi.api.type.data.ISlotData;

@ApiStatus.Internal
public interface IRegaliaSlotsApiCodecs {

  Codec<ISlotType> slotTypeCodec();

  Codec<ISlotData.Entry> slotDataEntryCodec();

  Codec<IEntitiesData.Entry> entitiesDataEntryCodec();

  Codec<Holder<Attribute>> slotAttributeCodec();

  StreamCodec<RegistryFriendlyByteBuf, Holder<Attribute>> slotAttributeStreamCodec();

  StreamCodec<RegistryFriendlyByteBuf, ISlotType> slotTypeStreamCodec();
}
