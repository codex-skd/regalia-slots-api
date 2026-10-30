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

package com.skd.regaliaslotsapi.common.capability;

import com.google.common.cache.Cache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import com.skd.regaliaslotsapi.RegaliaSlotsApiCommonMod;
import com.skd.regaliaslotsapi.RegaliaSlotsApiConstants;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.SlotResult;
import com.skd.regaliaslotsapi.api.type.capability.IRegaliaSlotsApiItemHandler;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;
import com.skd.regaliaslotsapi.impl.RegaliaSlotsApiRegistry;

public class CurioInventoryCapability implements IRegaliaSlotsApiItemHandler {

  final CurioInventory curioInventory;
  final LivingEntity livingEntity;

  public CurioInventoryCapability(final LivingEntity livingEntity) {
    this.livingEntity = livingEntity;
    this.curioInventory = livingEntity.getData(RegaliaSlotsApiRegistry.INVENTORY.get());
  }

  @Override
  public void reset() {
    this.curioInventory.resetInventory();
  }

  @Override
  public int getSlots() {
    int totalSlots = 0;

    for (ICurioStacksHandler stacks : this.curioInventory.asMap().values()) {
      totalSlots += stacks.getSlots();
    }
    return totalSlots;
  }

  @Override
  public int getVisibleSlots() {
    int totalSlots = 0;

    for (ICurioStacksHandler stacks : this.curioInventory.asMap().values()) {

      if (stacks.isVisible()) {
        totalSlots += stacks.getSlots();
      }
    }
    return totalSlots;
  }

  @Override
  public Optional<ICurioStacksHandler> getStacksHandler(String identifier) {
    return Optional.ofNullable(this.curioInventory.asMap().get(identifier));
  }

  @Override
  public IItemHandlerModifiable getEquippedRegaliaSlotsApi() {
    Map<String, ICurioStacksHandler> curios = this.getRegaliaSlotsApi();
    IItemHandlerModifiable[] itemHandlers = new IItemHandlerModifiable[curios.size()];
    int index = 0;

    for (ICurioStacksHandler stacksHandler : curios.values()) {

      if (index < itemHandlers.length) {
        itemHandlers[index] = stacksHandler.getStacks();
        index++;
      }
    }
    return new CombinedInvWrapper(itemHandlers);
  }

  @Override
  public void setEquippedCurio(String identifier, int index, ItemStack stack) {
    Map<String, ICurioStacksHandler> curios = this.getRegaliaSlotsApi();
    ICurioStacksHandler stacksHandler = curios.get(identifier);

    if (stacksHandler != null) {
      IDynamicStackHandler stackHandler = stacksHandler.getStacks();

      if (index < stackHandler.getSlots()) {
        stackHandler.setStackInSlot(index, stack);
      }
    }
  }

  @Override
  public Optional<SlotResult> findFirstCurio(Item item) {
    return findFirstCurio(
        stack -> stack.getItem() == item, RegaliaSlotsApiCommonMod.itemCacheKey(item.getDefaultInstance()));
  }

  @Override
  public Optional<SlotResult> findFirstCurio(Predicate<ItemStack> filter) {
    return findFirstCurio(filter, "");
  }

  @Override
  public Optional<SlotResult> findFirstCurio(Predicate<ItemStack> filter, String cacheKey) {
    return findFirstCurio(filter, false, cacheKey);
  }

  @Override
  public Optional<SlotResult> findFirstCurio(Predicate<ItemStack> filter, boolean includeInactive,
                                             String cacheKey) {
    // Check cached value first
    long gameTime = this.livingEntity.level().getGameTime();
    Cache<String, Pair<Long, Optional<SlotResult>>> cache = this.curioInventory.firstCurioCache;

    if (!cacheKey.isEmpty()) {
      Pair<Long, Optional<SlotResult>> cached = cache.getIfPresent(cacheKey);

      if (cached != null && cached.getFirst() == gameTime) {
        return cached.getSecond();
      }
    }
    Map<String, ICurioStacksHandler> curios = this.getRegaliaSlotsApi();

    for (String id : curios.keySet()) {
      ICurioStacksHandler stacksHandler = curios.get(id);
      IDynamicStackHandler stackHandler = stacksHandler.getStacks();
      NonNullList<Boolean> activeStates = stacksHandler.getActiveStates();

      for (int i = 0; i < stackHandler.getSlots(); i++) {

        if (!includeInactive && activeStates.size() > i && !activeStates.get(i)) {
          continue;
        }
        ItemStack stack = stackHandler.getStackInSlot(i);

        if (!stack.isEmpty() && filter.test(stack)) {
          NonNullList<Boolean> renderStates = stacksHandler.getRenders();
          var ret =
              Optional.of(
                  new SlotResult(
                      new SlotContext(
                          id,
                          this.livingEntity,
                          i,
                          false,
                          renderStates.size() > i && renderStates.get(i)),
                      stack));
          cache.put(cacheKey, Pair.of(gameTime, ret));
          return ret;
        }
      }
    }
    cache.put(cacheKey, Pair.of(gameTime, Optional.empty()));
    return Optional.empty();
  }

  @Override
  public List<SlotResult> findRegaliaSlotsApi(Item item) {
    return findRegaliaSlotsApi(stack -> stack.getItem() == item, false,
        RegaliaSlotsApiCommonMod.itemCacheKey(item.getDefaultInstance()));
  }

  @Override
  public List<SlotResult> findRegaliaSlotsApi(Predicate<ItemStack> filter) {
    return findRegaliaSlotsApi(filter, false, "");
  }

  @Override
  public List<SlotResult> findRegaliaSlotsApi(Predicate<ItemStack> filter, boolean includeInactive,
                                     String cacheKey) {
    // Check cached value first
    long gameTime = this.livingEntity.level().getGameTime();
    Cache<String, Pair<Long, List<SlotResult>>> cache = this.curioInventory.findRegaliaSlotsApiCache;

    if (!cacheKey.isEmpty()) {
      Pair<Long, List<SlotResult>> cached = cache.getIfPresent(cacheKey);

      if (cached != null && cached.getFirst() == gameTime) {
        return cached.getSecond();
      }
    }
    List<SlotResult> result = new ArrayList<>();
    Map<String, ICurioStacksHandler> curios = this.getRegaliaSlotsApi();

    for (String id : curios.keySet()) {
      ICurioStacksHandler stacksHandler = curios.get(id);
      IDynamicStackHandler stackHandler = stacksHandler.getStacks();
      NonNullList<Boolean> activeStates = stacksHandler.getActiveStates();

      for (int i = 0; i < stackHandler.getSlots(); i++) {

        if (!includeInactive && activeStates.size() > i && !activeStates.get(i)) {
          continue;
        }
        ItemStack stack = stackHandler.getStackInSlot(i);

        if (!stack.isEmpty() && filter.test(stack)) {
          NonNullList<Boolean> renderStates = stacksHandler.getRenders();
          result.add(
              new SlotResult(
                  new SlotContext(
                      id,
                      this.livingEntity,
                      i,
                      false,
                      renderStates.size() > i && renderStates.get(i)),
                  stack));
        }
      }
    }
    cache.put(cacheKey, Pair.of(gameTime, result));
    return result;
  }

  @Override
  public List<SlotResult> findRegaliaSlotsApi(String... identifiers) {
    return this.findRegaliaSlotsApi(false, identifiers);
  }

  @Override
  public List<SlotResult> findRegaliaSlotsApi(boolean includeInactive, String... identifiers) {
    List<SlotResult> result = new ArrayList<>();
    Set<String> ids = new HashSet<>(List.of(identifiers));
    Map<String, ICurioStacksHandler> curios = this.getRegaliaSlotsApi();

    for (String id : curios.keySet()) {

      if (ids.contains(id)) {
        ICurioStacksHandler stacksHandler = curios.get(id);
        IDynamicStackHandler stackHandler = stacksHandler.getStacks();
        NonNullList<Boolean> activeStates = stacksHandler.getActiveStates();

        for (int i = 0; i < stackHandler.getSlots(); i++) {

          if (!includeInactive && activeStates.size() > i && !activeStates.get(i)) {
            continue;
          }
          ItemStack stack = stackHandler.getStackInSlot(i);

          if (!stack.isEmpty()) {
            NonNullList<Boolean> renderStates = stacksHandler.getRenders();
            result.add(
                new SlotResult(
                    new SlotContext(
                        id,
                        this.livingEntity,
                        i,
                        false,
                        renderStates.size() > i && renderStates.get(i)),
                    stack));
          }
        }
      }
    }
    return result;
  }

  @Override
  public Optional<SlotResult> findCurio(String identifier, int index) {
    return this.findCurio(identifier, index, false);
  }

  @Override
  public Optional<SlotResult> findCurio(String identifier, int index, boolean includeInactive) {
    Map<String, ICurioStacksHandler> curios = this.getRegaliaSlotsApi();
    ICurioStacksHandler stacksHandler = curios.get(identifier);

    if (stacksHandler != null) {
      IDynamicStackHandler stackHandler = stacksHandler.getStacks();
      NonNullList<Boolean> activeStates = stacksHandler.getActiveStates();

      if (index < stackHandler.getSlots()) {

        if (!includeInactive && activeStates.size() > index && !activeStates.get(index)) {
          return Optional.empty();
        }
        ItemStack stack = stackHandler.getStackInSlot(index);

        if (!stack.isEmpty()) {
          NonNullList<Boolean> renderStates = stacksHandler.getRenders();
          return Optional.of(
              new SlotResult(
                  new SlotContext(
                      identifier,
                      this.livingEntity,
                      index,
                      false,
                      renderStates.size() > index && renderStates.get(index)),
                  stack));
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public Map<String, ICurioStacksHandler> getRegaliaSlotsApi() {
    return Collections.unmodifiableMap(this.curioInventory.asMap());
  }

  @Override
  public void setRegaliaSlotsApi(Map<String, ICurioStacksHandler> curios) {
    this.curioInventory.replace(curios);
  }

  @Nullable
  @Override
  public LivingEntity getWearer() {
    return this.livingEntity;
  }

  @Override
  public void loseInvalidStack(ItemStack stack) {
    this.curioInventory.invalidStacks.add(stack);
  }

  @Override
  public void handleInvalidStacks() {

    if (this.livingEntity != null && !this.curioInventory.invalidStacks.isEmpty()) {

      if (this.livingEntity instanceof Player player) {
        this.curioInventory.invalidStacks.forEach(
            drop -> ItemHandlerHelper.giveItemToPlayer(player, drop));
      } else {
        this.curioInventory.invalidStacks.forEach(
            drop -> {

              if (this.livingEntity.level() instanceof ServerLevel serverLevel) {
                ItemEntity ent = this.livingEntity.spawnAtLocation(serverLevel, drop);
                RandomSource rand = this.livingEntity.getRandom();

                if (ent != null) {
                  ent.setDeltaMovement(
                      ent.getDeltaMovement()
                          .add(
                              (rand.nextFloat() - rand.nextFloat()) * 0.1F,
                              rand.nextFloat() * 0.05F,
                              (rand.nextFloat() - rand.nextFloat()) * 0.1F));
                }
              }
            });
      }
      this.curioInventory.invalidStacks = NonNullList.create();
    }
  }

  @Override
  public int getFortuneLevel(@Nullable LootContext lootContext) {
    int fortuneLevel = 0;
    for (Map.Entry<String, ICurioStacksHandler> entry : getRegaliaSlotsApi().entrySet()) {
      IDynamicStackHandler stacks = entry.getValue().getStacks();

      for (int i = 0; i < stacks.getSlots(); i++) {
        final int index = i;
        fortuneLevel +=
            RegaliaSlotsApi.getCurio(stacks.getStackInSlot(i))
                .map(
                    curio -> {
                      NonNullList<Boolean> renderStates = entry.getValue().getRenders();
                      return curio.getFortuneLevel(
                          new SlotContext(
                              entry.getKey(),
                              this.livingEntity,
                              index,
                              false,
                              renderStates.size() > index && renderStates.get(index)),
                          lootContext);
                    })
                .orElse(0);
      }
    }
    return fortuneLevel;
  }

  @Override
  public int getLootingLevel(@Nullable LootContext lootContext) {
    int lootingLevel = 0;
    for (Map.Entry<String, ICurioStacksHandler> entry : getRegaliaSlotsApi().entrySet()) {
      IDynamicStackHandler stacks = entry.getValue().getStacks();

      for (int i = 0; i < stacks.getSlots(); i++) {
        final int index = i;
        lootingLevel +=
            RegaliaSlotsApi.getCurio(stacks.getStackInSlot(i))
                .map(
                    curio -> {
                      NonNullList<Boolean> renderStates = entry.getValue().getRenders();
                      return curio.getLootingLevel(
                          new SlotContext(
                              entry.getKey(),
                              this.livingEntity,
                              index,
                              false,
                              renderStates.size() > index && renderStates.get(index)),
                          lootContext);
                    })
                .orElse(0);
      }
    }
    return lootingLevel;
  }

  @Override
  public ListTag saveInventory(boolean clear) {
    ListTag taglist = new ListTag();
    LivingEntity entity = this.livingEntity;

    if (entity != null) {
      try (
          ProblemReporter.ScopedCollector problemreporter$scopedcollector =
              new ProblemReporter.ScopedCollector(entity.problemPath(), RegaliaSlotsApiConstants.LOG)) {

        for (Map.Entry<String, ICurioStacksHandler> entry : this.curioInventory.asMap()
            .entrySet()) {
          CompoundTag tag = new CompoundTag();
          ICurioStacksHandler stacksHandler = entry.getValue();
          IDynamicStackHandler stacks = stacksHandler.getStacks();
          TagValueOutput tagvalueoutput =
              TagValueOutput.createWithContext(problemreporter$scopedcollector,
                  entity.registryAccess());
          stacks.serialize(tagvalueoutput);
          tag.put("Stacks", tagvalueoutput.buildResult());
          IDynamicStackHandler cosmetics = stacksHandler.getCosmeticStacks();
          tagvalueoutput =
              TagValueOutput.createWithContext(problemreporter$scopedcollector,
                  entity.registryAccess());
          cosmetics.serialize(tagvalueoutput);
          tag.put("Cosmetics", tagvalueoutput.buildResult());
          tag.putString("Identifier", entry.getKey());
          taglist.add(tag);

          if (clear) {

            for (int i = 0; i < stacks.getSlots(); i++) {
              stacks.setStackInSlot(i, ItemStack.EMPTY);
            }

            for (int i = 0; i < cosmetics.getSlots(); i++) {
              cosmetics.setStackInSlot(i, ItemStack.EMPTY);
            }
          }
        }
      }
    }
    return taglist;
  }

  @Override
  public void loadInventory(ListTag data) {
    LivingEntity entity = this.livingEntity;

    if (data != null && entity != null) {
      try (
          ProblemReporter.ScopedCollector problemreporter$scopedcollector =
              new ProblemReporter.ScopedCollector(entity.problemPath(), RegaliaSlotsApiConstants.LOG)) {

        for (int i = 0; i < data.size(); i++) {
          CompoundTag tag = data.getCompound(i).orElse(new CompoundTag());
          String identifier = tag.getString("Identifier").orElse("");
          ICurioStacksHandler stacksHandler = this.curioInventory.asMap().get(identifier);

          if (stacksHandler != null) {
            CompoundTag stacksData = tag.getCompound("Stacks").orElse(new CompoundTag());
            ItemStackHandler loaded = new ItemStackHandler();
            IDynamicStackHandler stacks = stacksHandler.getStacks();

            if (!stacksData.isEmpty()) {
              loaded.deserialize(
                  TagValueInput.create(problemreporter$scopedcollector, entity.registryAccess(),
                      stacksData));
              loadStacks(stacksHandler, loaded, stacks);
            }
            stacksData = tag.getCompound("Cosmetics").orElse(new CompoundTag());

            if (!stacksData.isEmpty()) {
              loaded.deserialize(
                  TagValueInput.create(problemreporter$scopedcollector, entity.registryAccess(),
                      stacksData));
              stacks = stacksHandler.getCosmeticStacks();
              loadStacks(stacksHandler, loaded, stacks);
            }
          }
        }
      }
    }
  }

  @Override
  public Set<ICurioStacksHandler> getUpdatingInventories() {
    return this.curioInventory.updates;
  }

  @Override
  public void addTransientSlotModifier(
      String slot, Identifier id, double amount, AttributeModifier.Operation operation) {
    Multimap<String, AttributeModifier> map = LinkedHashMultimap.create();
    map.put(slot, new AttributeModifier(id, amount, operation));
    this.addTransientSlotModifiers(map);
  }

  @Override
  public void addTransientSlotModifiers(Multimap<String, AttributeModifier> modifiers) {

    for (Map.Entry<String, Collection<AttributeModifier>> entry : modifiers.asMap().entrySet()) {
      String id = entry.getKey();

      for (AttributeModifier attributeModifier : entry.getValue()) {
        ICurioStacksHandler stacksHandler = this.curioInventory.asMap().get(id);

        if (stacksHandler != null) {
          stacksHandler.addTransientModifier(attributeModifier);
        }
      }
    }
  }

  @Override
  public void addPermanentSlotModifier(
      String slot, Identifier id, double amount, AttributeModifier.Operation operation) {
    Multimap<String, AttributeModifier> map = LinkedHashMultimap.create();
    map.put(slot, new AttributeModifier(id, amount, operation));
    this.addPermanentSlotModifiers(map);
  }

  @Override
  public void addPermanentSlotModifiers(Multimap<String, AttributeModifier> modifiers) {

    for (Map.Entry<String, Collection<AttributeModifier>> entry : modifiers.asMap().entrySet()) {
      String id = entry.getKey();

      for (AttributeModifier attributeModifier : entry.getValue()) {
        ICurioStacksHandler stacksHandler = this.curioInventory.asMap().get(id);

        if (stacksHandler != null) {
          stacksHandler.addPermanentModifier(attributeModifier);
        }
      }
    }
  }

  @Override
  public void removeSlotModifier(String slot, Identifier id) {
    Multimap<String, AttributeModifier> map = LinkedHashMultimap.create();
    map.put(slot, new AttributeModifier(id, 0, AttributeModifier.Operation.ADD_VALUE));
    this.removeSlotModifiers(map);
  }

  @Override
  public void removeSlotModifiers(Multimap<String, AttributeModifier> modifiers) {

    for (Map.Entry<String, Collection<AttributeModifier>> entry : modifiers.asMap().entrySet()) {
      String id = entry.getKey();

      for (AttributeModifier attributeModifier : entry.getValue()) {
        ICurioStacksHandler stacksHandler = this.curioInventory.asMap().get(id);

        if (stacksHandler != null) {
          stacksHandler.removeModifier(attributeModifier.id());
        }
      }
    }
  }

  @Override
  public void clearSlotModifiers() {

    for (Map.Entry<String, ICurioStacksHandler> entry : this.curioInventory.asMap().entrySet()) {
      entry.getValue().clearModifiers();
    }
  }

  private static final Identifier SIZE_SHIFT = RegaliaSlotsApiResources.resource("size_shift");

  @Override
  public void clearCachedSlotModifiers() {
    Multimap<String, AttributeModifier> slots = HashMultimap.create();
    boolean flag = false;
    Map<String, ICurioStacksHandler> inv = this.curioInventory.asMap();

    for (Map.Entry<String, ICurioStacksHandler> entry : inv.entrySet()) {
      entry.getValue().removeModifier(SIZE_SHIFT);
    }
  }

  @Override
  public Multimap<String, AttributeModifier> getModifiers() {
    Multimap<String, AttributeModifier> result = HashMultimap.create();

    for (Map.Entry<String, ICurioStacksHandler> entry : this.curioInventory.asMap().entrySet()) {
      result.putAll(entry.getKey(), entry.getValue().getModifiers().values());
    }
    return result;
  }

  @Override
  public void loadDatapacks() {
    this.curioInventory.loadInventoryConfiguration();
  }

  private void loadStacks(
      ICurioStacksHandler stacksHandler, ItemStackHandler loaded, IDynamicStackHandler stacks) {

    for (int j = 0; j < stacksHandler.getSlots() && j < loaded.getSlots(); j++) {
      ItemStack stack = stacks.getStackInSlot(j);
      ItemStack loadedStack = loaded.getStackInSlot(j);

      if (stack.isEmpty()) {
        stacks.setStackInSlot(j, loadedStack);
      } else {
        this.loseInvalidStack(stack);
      }
    }
  }

  @Override
  public Tag writeTag() {
    LivingEntity entity = this.livingEntity;

    if (entity != null) {
      try (
          ProblemReporter.ScopedCollector problemreporter$scopedcollector =
              new ProblemReporter.ScopedCollector(entity.problemPath(), RegaliaSlotsApiConstants.LOG)) {
        TagValueOutput tagvalueoutput =
            TagValueOutput.createWithContext(problemreporter$scopedcollector,
                entity.registryAccess());
        this.serialize(tagvalueoutput);
        return tagvalueoutput.buildResult();
      }
    }
    return new CompoundTag();
  }

  @Override
  public void readTag(Tag nbt) {
    LivingEntity entity = this.livingEntity;

    if (nbt instanceof CompoundTag tag && entity != null) {
      try (
          ProblemReporter.ScopedCollector problemreporter$scopedcollector =
              new ProblemReporter.ScopedCollector(entity.problemPath(), RegaliaSlotsApiConstants.LOG)) {
        this.deserialize(
            TagValueInput.create(problemreporter$scopedcollector, entity.registryAccess(), tag));
      }
    }
  }

  @Override
  public void serialize(@Nonnull ValueOutput output) {
    this.curioInventory.serialize(output);
  }

  @Override
  public void deserialize(@Nonnull ValueInput input) {
    this.curioInventory.deserialize(input);
  }
}
