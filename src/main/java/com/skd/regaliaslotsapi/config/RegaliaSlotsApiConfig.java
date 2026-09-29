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

package com.skd.regaliaslotsapi.config;

import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApi;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiResources;

public class RegaliaSlotsApiConfig {

  public static final ModConfigSpec SERVER_SPEC;
  public static final Server SERVER;
  public static final ModConfigSpec COMMON_SPEC;
  public static final Common COMMON;
  private static final String CONFIG_PREFIX = "gui." + RegaliaSlotsApiResources.MOD_ID + ".config.";

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
              Defaults to granting all built-in preset slots (back, belt, body, bracelet, charm,
              curio, feet, hands, head, necklace, ring) to player-like entities, so third-party
              items that only ship a curios: item tag (no entities.json of their own) have
              somewhere to go out of the box.
              """)
          .translation(CONFIG_PREFIX + "slots")
          .defineList("slots", List.of("id=back", "id=belt", "id=body", "id=bracelet", "id=charm",
              "id=curio", "id=feet", "id=hands", "id=head", "id=necklace", "id=ring"),
              s -> s instanceof String);

      builder.build();
    }
  }

  public static class Server {

    public ModConfigSpec.EnumValue<KeepRegaliaSlotsApi> keepRegaliaSlotsApi;
    public ModConfigSpec.IntValue minimumColumns;
    public ModConfigSpec.IntValue maxSlotsPerPage;

    public Server(ModConfigSpec.Builder builder) {
      keepRegaliaSlotsApi = builder.comment("""
              Sets behavior for keeping RegaliaSlotsApi items on death.
              ON - RegaliaSlotsApi items are kept on death
              DEFAULT - RegaliaSlotsApi items follow the keepInventory gamerule
              OFF - RegaliaSlotsApi items are dropped on death""")
          .translation(CONFIG_PREFIX + "keepRegaliaSlotsApi").defineEnum("keepRegaliaSlotsApi", KeepRegaliaSlotsApi.DEFAULT);

      builder.push("menu");

      minimumColumns = builder.comment("The minimum number of columns for the RegaliaSlotsApi menu.")
          .translation(CONFIG_PREFIX + "minimumColumns").defineInRange("minimumColumns", 1, 1, 8);

      maxSlotsPerPage = builder.comment("The maximum number of slots per page of the RegaliaSlotsApi menu.")
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
