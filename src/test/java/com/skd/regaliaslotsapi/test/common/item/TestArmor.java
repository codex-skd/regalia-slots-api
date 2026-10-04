package com.skd.regaliaslotsapi.test.common.item;

import javax.annotation.Nonnull;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import com.skd.regaliaslotsapi.RegaliaSlotsApiConstants;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;

public class TestArmor extends Item {

  private static final Identifier ARMOR_ID =
      Identifier.fromNamespaceAndPath(RegaliaSlotsApiConstants.MOD_ID, "armor");

  public TestArmor(Properties pProperties) {
    super(pProperties.humanoidArmor(ArmorMaterials.GOLD, ArmorType.CHESTPLATE));
  }

  @Nonnull
  @Override
  public ItemAttributeModifiers getDefaultAttributeModifiers(@Nonnull ItemStack stack) {
    ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);
    EquipmentSlot slot = this.getEquipmentSlot(stack);

    if (slot != null) {
      modifiers = RegaliaSlotsApi.withSlotModifier(modifiers, "ring", ARMOR_ID, 1,
                                             AttributeModifier.Operation.ADD_VALUE,
                                             EquipmentSlotGroup.bySlot(slot));
      modifiers = RegaliaSlotsApi.withSlotModifier(modifiers, "necklace", ARMOR_ID, -3,
                                             AttributeModifier.Operation.ADD_VALUE,
                                             EquipmentSlotGroup.bySlot(slot));
    }
    return modifiers;
  }
}
