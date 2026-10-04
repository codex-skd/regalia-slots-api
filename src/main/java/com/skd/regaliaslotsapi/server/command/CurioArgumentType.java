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

package com.skd.regaliaslotsapi.server.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiSlotTypes;
import com.skd.regaliaslotsapi.api.type.ISlotType;

public class CurioArgumentType implements ArgumentType<String> {

  private static final Collection<String> EXAMPLES = Arrays.asList("ring", "head");
  private static final DynamicCommandExceptionType UNKNOWN_TYPE = new DynamicCommandExceptionType(
      type -> Component.translatable("argument.curios.type.unknown", type));

  public static CurioArgumentType slot() {
    return new CurioArgumentType();
  }

  public static String getSlot(CommandContext<CommandSourceStack> context, String name) {
    return context.getArgument(name, String.class);
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context,
                                                            SuggestionsBuilder builder) {
    return SharedSuggestionProvider.suggest(RegaliaSlotsApiSlotTypes.getSlotTypes().values().stream().map(
        ISlotType::getId), builder);
  }

  @Override
  public Collection<String> getExamples() {
    return EXAMPLES;
  }

  @Override
  public String parse(StringReader reader) throws CommandSyntaxException {
    String s = reader.readUnquotedString();

    if (RegaliaSlotsApiSlotTypes.getSlotType(s) == null) {
      throw UNKNOWN_TYPE.create(s);
    } else {
      return s;
    }
  }
}