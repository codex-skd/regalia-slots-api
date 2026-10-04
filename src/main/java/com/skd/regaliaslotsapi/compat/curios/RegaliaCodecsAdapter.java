package com.skd.regaliaslotsapi.compat.curios;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import javax.annotation.Nonnull;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import top.theillusivec4.curios.api.CuriosResources;
import top.theillusivec4.curios.api.CuriosSlotTypes;
import top.theillusivec4.curios.api.SlotAttribute;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.data.IEntitiesData;
import top.theillusivec4.curios.api.type.data.ISlotData;

/**
 * Only {@link #slotAttributeCodec()} / {@link #slotAttributeStreamCodec()} are load-bearing:
 * {@code SlotAttribute} and {@code CurioAttributeModifiers} (both copied verbatim from Curios)
 * reference them from eager static field initializers.
 * <p>
 * {@link #slotTypeCodec()}, {@link #slotTypeStreamCodec()}, {@link #slotDataEntryCodec()} and
 * {@link #entitiesDataEntryCodec()} back Curios' own datapack-driven slot type loader and network
 * sync, which regalia_slots_api does not replicate (Regalia's own slot type system stays
 * authoritative). They return codecs that fail only if actually exercised, rather than porting
 * the concrete SlotType/SlotData/EntitiesData builder classes.
 */
public class RegaliaCodecsAdapter
    implements top.theillusivec4.curios.api.internal.services.ICuriosCodecs {

  private static <T> Codec<T> unsupported(String what) {
    return Codec.PASSTHROUGH.comapFlatMap(
        ignored -> DataResult.error(() -> "Curios compat: " + what + " is not supported"),
        ignored -> null);
  }

  @Override
  public Codec<ISlotType> slotTypeCodec() {
    return unsupported("ISlotType codec (native Curios slot type datapacks)");
  }

  @Override
  public Codec<ISlotData.Entry> slotDataEntryCodec() {
    return unsupported("ISlotData.Entry codec (datagen-only)");
  }

  @Override
  public Codec<IEntitiesData.Entry> entitiesDataEntryCodec() {
    return unsupported("IEntitiesData.Entry codec (datagen-only)");
  }

  @Override
  public Codec<Holder<Attribute>> slotAttributeCodec() {
    return Identifier.CODEC.xmap(
        resourceLocation -> {
          if (resourceLocation.getNamespace().startsWith(CuriosResources.MOD_ID)) {
            String key = resourceLocation.getPath();
            ISlotType slotType = ISlotType.get(key);

            if (slotType != null) {
              return SlotAttribute.getOrCreate(key);
            }
          }
          return SlotAttribute.getOrCreate(CuriosSlotTypes.Preset.CURIO.id());
        },
        attributeHolder -> {
          if (attributeHolder.value() instanceof SlotAttribute slotAttribute) {
            return CuriosResources.resource(slotAttribute.id());
          }
          return CuriosResources.resource(CuriosSlotTypes.Preset.CURIO.id());
        }
    );
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Holder<Attribute>> slotAttributeStreamCodec() {
    return new StreamCodec<>() {

      @Nonnull
      @Override
      public Holder<Attribute> decode(@Nonnull RegistryFriendlyByteBuf buffer) {
        Identifier resourceLocation = Identifier.STREAM_CODEC.decode(buffer);

        if (resourceLocation.getNamespace().equals(CuriosResources.MOD_ID)) {
          return SlotAttribute.getOrCreate(resourceLocation.getPath());
        }
        return BuiltInRegistries.ATTRIBUTE.getOrThrow(
            ResourceKey.create(Registries.ATTRIBUTE, resourceLocation));
      }

      @Override
      public void encode(@Nonnull RegistryFriendlyByteBuf buffer,
                         @Nonnull Holder<Attribute> value) {
        Identifier resourceLocation;

        if (value.value() instanceof SlotAttribute slotAttribute) {
          resourceLocation = slotAttribute.resourceLocation();
        } else {
          resourceLocation = BuiltInRegistries.ATTRIBUTE.getKey(value.value());
        }
        Identifier.STREAM_CODEC.encode(buffer, resourceLocation);
      }
    };
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, ISlotType> slotTypeStreamCodec() {
    return StreamCodec.unit(null);
  }
}
