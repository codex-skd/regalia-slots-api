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
 */

package com.skd.regaliaslotsapi.common;

import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;

public class RegaliaSlotsApiConfig {

  public static final ModConfigSpec SERVER_SPEC;
  public static final Server SERVER;
  public static final ModConfigSpec COMMON_SPEC;
  public static final Common COMMON;
  private static final String CONFIG_PREFIX = "gui." + RegaliaSlotsApi.MODID + ".config.";

  static {
    final Pair<Server, ModConfigSpec> specPair = new ModConfigSpec.Builder()
        .configure(Server::new);
    SERVER_SPEC = specPair.getRight();
    SERVER = specPair.getLeft();
    final Pair<Common, ModConfigSpec> cspecPair = new ModConfigSpec.Builder()
        .configure(Common::new);
    COMMON_SPEC = cspecPair.getRight();
    COMMON = cspecPair.getLeft();
  }

  public static class Common {

    public ModConfigSpec.ConfigValue<List<? extends String>> slots;

    public Common(ModConfigSpec.Builder builder) {
      slots = builder.comment("""
              List of slots to create or modify.
              See documentation for syntax: https://docs.illusivesoulworks.com/curios/configuration#slot-configuration
              """)
          .translation(CONFIG_PREFIX + "slots")
          .defineList("slots", List.of(
              "id=back,decode=slot.back,order=0,icon=regalia_slots_api:slot/back",
              "id=belt,decode=slot.belt,order=1,icon=regalia_slots_api:slot/belt",
              "id=body,decode=slot.body,order=2,icon=regalia_slots_api:slot/body",
              "id=bracelet,decode=slot.bracelet,order=3,icon=regalia_slots_api:slot/bracelet",
              "id=charm,decode=slot.charm,order=4,icon=regalia_slots_api:slot/charm",
              "id=curio,decode=slot.curio,order=5,icon=regalia_slots_api:slot/curio",
              "id=feet,decode=slot.feet,order=6,icon=regalia_slots_api:slot/feet",
              "id=hands,decode=slot.hands,order=7,icon=regalia_slots_api:slot/hands",
              "id=head,decode=slot.head,order=8,icon=regalia_slots_api:slot/head",
              "id=necklace,decode=slot.necklace,order=9,icon=regalia_slots_api:slot/necklace",
              "id=ring,decode=slot.ring,order=10,icon=regalia_slots_api:slot/ring"
          ), s -> s instanceof String);

      builder.build();
    }
  }

  public static class Server {

    public ModConfigSpec.EnumValue<KeepRegaliaSlotsApi> keepRegaliaSlotsApi;
    public ModConfigSpec.IntValue minimumColumns;
    public ModConfigSpec.IntValue maxSlotsPerPage;

    public Server(ModConfigSpec.Builder builder) {
      keepRegaliaSlotsApi = builder.comment("""
              Sets behavior for keeping Regalia Slots Api items on death.
              ON - Regalia Slots Api items are kept on death
              DEFAULT - Regalia Slots Api items follow the keepInventory gamerule
              OFF - Regalia Slots Api items are dropped on death""")
          .translation(CONFIG_PREFIX + "keepRegaliaSlotsApi").defineEnum("keepRegaliaSlotsApi", KeepRegaliaSlotsApi.DEFAULT);

      builder.push("menu");

      minimumColumns = builder.comment("The minimum number of columns for the Regalia Slots Api menu.")
          .translation(CONFIG_PREFIX + "minimumColumns").defineInRange("minimumColumns", 1, 1, 8);

      maxSlotsPerPage = builder.comment("The maximum number of slots per page of the Regalia Slots Api menu.")
          .translation(CONFIG_PREFIX + "maxSlotsPerPage")
          .defineInRange("maxSlotsPerPage", 48, 1, 48);

      builder.pop();
      builder.build();
    }
  }

  public enum KeepRegaliaSlotsApi {
    ON,
    DEFAULT,
    OFF
  }
}
