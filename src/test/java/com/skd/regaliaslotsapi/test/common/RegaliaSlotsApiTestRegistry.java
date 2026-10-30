/*
 * Copyright (c) 2018-2020 C4
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
 */

package com.skd.regaliaslotsapi.test.common;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.skd.regaliaslotsapi.test.RegaliaSlotsApiTest;
import com.skd.regaliaslotsapi.test.common.item.AmuletItem;
import com.skd.regaliaslotsapi.test.common.item.CrownItem;
import com.skd.regaliaslotsapi.test.common.item.KnucklesItem;
import com.skd.regaliaslotsapi.test.common.item.RingItem;
import com.skd.regaliaslotsapi.test.common.item.TestArmor;

public class RegaliaSlotsApiTestRegistry {

  private static final DeferredRegister.Items ITEMS =
      DeferredRegister.createItems(RegaliaSlotsApiTest.MODID);

  public static final DeferredItem<Item> RING =
      ITEMS.registerItem(RegaliaSlotsApiTestIds.RING.identifier().getPath(), RingItem::new,
          () -> new Item.Properties().stacksTo(1).durability(0));
  public static final DeferredItem<Item> AMULET =
      ITEMS.registerItem(RegaliaSlotsApiTestIds.AMULET.identifier().getPath(), AmuletItem::new,
          () -> new Item.Properties().stacksTo(1).durability(0));
  public static final DeferredItem<Item> CROWN =
      ITEMS.registerItem(RegaliaSlotsApiTestIds.CROWN.identifier().getPath(), CrownItem::new,
          () -> new Item.Properties().stacksTo(1).durability(2000));
  public static final DeferredItem<Item> KNUCKLES =
      ITEMS.registerItem(RegaliaSlotsApiTestIds.KNUCKLES.identifier().getPath(), KnucklesItem::new,
          () -> new Item.Properties().stacksTo(1));

  public static final DeferredItem<Item> TEST_ARMOR =
      ITEMS.registerItem("test_armor", TestArmor::new);

  public static void init(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
