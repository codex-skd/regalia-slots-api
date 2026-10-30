package com.skd.regaliaslotsapi.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import com.skd.regaliaslotsapi.api.SlotResult;
import com.skd.regaliaslotsapi.api.type.capability.IRegaliaSlotsApiItemHandler;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;

/**
 * Wraps the live Regalia Slots API entity inventory ({@link IRegaliaSlotsApiItemHandler}) as
 * the copied Curios {@code top.theillusivec4.curios.api.type.capability.ICuriosItemHandler}, so
 * third-party mods reading/writing through the real Curios capability id see the exact same
 * equipped items Regalia itself uses - nothing is duplicated or resynced.
 */
public class RegaliaItemHandlerAdapter
    implements top.theillusivec4.curios.api.type.capability.ICuriosItemHandler {

  private final IRegaliaSlotsApiItemHandler delegate;

  public RegaliaItemHandlerAdapter(IRegaliaSlotsApiItemHandler delegate) {
    this.delegate = delegate;
  }

  private static List<top.theillusivec4.curios.api.SlotResult> wrapList(List<SlotResult> results) {
    return results.stream().map(RegaliaTypeBridge::toCurios).toList();
  }

  private static Optional<top.theillusivec4.curios.api.SlotResult> wrapOptional(
      Optional<SlotResult> result) {
    return result.map(RegaliaTypeBridge::toCurios);
  }

  @Override
  public Map<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> getCurios() {
    return RegaliaTypeBridge.wrapStacksHandlers(this.delegate.getRegaliaSlotsApi());
  }

  @Override
  public void setCurios(Map<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> map) {
    Map<String, ICurioStacksHandler> unwrapped = new java.util.LinkedHashMap<>();
    map.forEach((id, handler) -> {
      if (handler instanceof StacksHandlerAdapter adapter) {
        unwrapped.put(id, adapter.delegate());
      }
    });
    this.delegate.setRegaliaSlotsApi(unwrapped);
  }

  @Override
  public int getSlots() {
    return this.delegate.getSlots();
  }

  @Override
  public int getVisibleSlots() {
    return this.delegate.getVisibleSlots();
  }

  @Override
  public void reset() {
    this.delegate.reset();
  }

  @Override
  public Optional<top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> getStacksHandler(
      String identifier) {
    return this.delegate.getStacksHandler(identifier).map(RegaliaTypeBridge::wrap);
  }

  @Override
  public IItemHandlerModifiable getEquippedCurios() {
    return this.delegate.getEquippedRegaliaSlotsApi();
  }

  @Override
  public void setEquippedCurio(String identifier, int index, ItemStack stack) {
    this.delegate.setEquippedCurio(identifier, index, stack);
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(Item item) {
    return wrapOptional(this.delegate.findFirstCurio(item));
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(
      Predicate<ItemStack> filter) {
    return wrapOptional(this.delegate.findFirstCurio(filter));
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(
      Predicate<ItemStack> filter, String cacheKey) {
    return wrapOptional(this.delegate.findFirstCurio(filter, cacheKey));
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findFirstCurio(
      Predicate<ItemStack> filter, boolean includeInactive, String cacheKey) {
    return wrapOptional(this.delegate.findFirstCurio(filter, includeInactive, cacheKey));
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(Item item) {
    return wrapList(this.delegate.findRegaliaSlotsApi(item));
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(Predicate<ItemStack> filter) {
    return wrapList(this.delegate.findRegaliaSlotsApi(filter));
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(String... identifiers) {
    return wrapList(this.delegate.findRegaliaSlotsApi(identifiers));
  }

  @Override
  public List<top.theillusivec4.curios.api.SlotResult> findCurios(boolean includeInactive,
                                                                    String... identifiers) {
    return wrapList(this.delegate.findRegaliaSlotsApi(includeInactive, identifiers));
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findCurio(String identifier, int index) {
    return wrapOptional(this.delegate.findCurio(identifier, index));
  }

  @Override
  public Optional<top.theillusivec4.curios.api.SlotResult> findCurio(String identifier, int index,
                                                                       boolean includeInactive) {
    return wrapOptional(this.delegate.findCurio(identifier, index, includeInactive));
  }

  @Nullable
  @Override
  public LivingEntity getWearer() {
    return this.delegate.getWearer();
  }

  @Override
  public void loseInvalidStack(ItemStack stack) {
    this.delegate.loseInvalidStack(stack);
  }

  @Override
  public void handleInvalidStacks() {
    this.delegate.handleInvalidStacks();
  }

  @Override
  public int getFortuneLevel(@Nullable LootContext lootContext) {
    return this.delegate.getFortuneLevel(lootContext);
  }

  @Override
  public int getLootingLevel(@Nullable LootContext lootContext) {
    return this.delegate.getLootingLevel(lootContext);
  }

  @Override
  public ListTag saveInventory(boolean clear) {
    return this.delegate.saveInventory(clear);
  }

  @Override
  public void loadInventory(ListTag data) {
    this.delegate.loadInventory(data);
  }

  @Override
  public Set<top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> getUpdatingInventories() {
    Set<top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> result =
        new LinkedHashSet<>();
    this.delegate.getUpdatingInventories().forEach(handler -> result.add(RegaliaTypeBridge.wrap(handler)));
    return result;
  }

  @Override
  public void addTransientSlotModifier(String slot, Identifier id, double amount,
                                       AttributeModifier.Operation operation) {
    this.delegate.addTransientSlotModifier(slot, id, amount, operation);
  }

  @Override
  public void addTransientSlotModifiers(Multimap<String, AttributeModifier> modifiers) {
    this.delegate.addTransientSlotModifiers(modifiers);
  }

  @Override
  public void addPermanentSlotModifier(String slot, Identifier id, double amount,
                                       AttributeModifier.Operation operation) {
    this.delegate.addPermanentSlotModifier(slot, id, amount, operation);
  }

  @Override
  public void addPermanentSlotModifiers(Multimap<String, AttributeModifier> modifiers) {
    this.delegate.addPermanentSlotModifiers(modifiers);
  }

  @Override
  public void removeSlotModifier(String slot, Identifier id) {
    this.delegate.removeSlotModifier(slot, id);
  }

  @Override
  public void removeSlotModifiers(Multimap<String, AttributeModifier> modifiers) {
    this.delegate.removeSlotModifiers(modifiers);
  }

  @Override
  public void clearSlotModifiers() {
    this.delegate.clearSlotModifiers();
  }

  @Override
  public Multimap<String, AttributeModifier> getModifiers() {
    return this.delegate.getModifiers();
  }

  @Override
  public void loadDatapacks() {
    this.delegate.loadDatapacks();
  }

  @Override
  public Tag writeTag() {
    return this.delegate.writeTag();
  }

  @Override
  public void readTag(Tag tag) {
    this.delegate.readTag(tag);
  }

  @Override
  public void clearCachedSlotModifiers() {
    this.delegate.clearCachedSlotModifiers();
  }

  @Override
  public void serialize(@Nonnull ValueOutput output) {
    this.delegate.serialize(output);
  }

  @Override
  public void deserialize(@Nonnull ValueInput input) {
    this.delegate.deserialize(input);
  }
}
