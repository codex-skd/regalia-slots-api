# Regalia Slots API

## Overview

Regalia Slots API is a flexible and expandable accessory/equipment API for users and developers. It provides functionality for developers to add extra accessory/equipment slots in a convenient and compatible manner, as well as giving users the ability to configure these slots to their preferences. Regalia Slots API does not add any content except for an inventory GUI and a couple of example items available through the Creative menu. Out of the box it grants the 11 built-in preset slots (back, belt, body, bracelet, charm, curio, feet, hands, head, necklace, ring) to player-like entities — configurable via the `slots` option, including down to none if you want fully on-demand slots instead.

Regalia Slots API also ships a built-in **Curios API compatibility layer**: third-party mods built against the real [Curios API](https://www.curseforge.com/minecraft/mc-mods/curios) (mod id `curios`, package `top.theillusivec4.curios.api`) are recognized and interoperate transparently, without requiring Curios itself to be installed. See `docs/WORKFLOW_REGALIA_SLOTS_API_26-2.md` for the compatibility layer's architecture and known limitations.

## Features

* **Expandable equipment slots through a central library.** New equipment slots can be made and managed easily through an identifier registry. Identical identifiers will be merged together to avoid functional redundancies and provide maximum compatibility to potential items, while unique identifiers can still be used to mark special types when appropriate.
* **Sensible default slots, fully configurable.** The 11 preset slots (back, belt, body, bracelet, charm, curio, feet, hands, head, necklace, ring) are granted to player-like entities out of the box. The `slots` option in the common config can trim them down to none, in which case slots are only created on demand by the mods and datapacks that ask for them.
* **Slots are completely customizable and manipulable.** Slots can have custom backgrounds, different sizes, and can even be disabled or hidden by default. Developers can access functions to enable/disable a player's slots or add/remove a certain number of slots of a given type.
* **Flexible item-to-slot relations using the vanilla tag system.** Potential curios are selected through the vanilla tag system — categorizing items into slot types is as easy as creating a `.json` file in `data/regalia_slots_api/tags`. Items can be categorized into as many slot types as needed, and these settings can be overridden entirely.
* **Complete integration with other inventory mechanics.** Mending and Curses work with all applicable items equipped in the accessory slots.
* **Accessible from a single GUI.** Regalia Slots API comes with its own GUI accessible from the inventory that shows all of the available slots to a player. Developers can still provide their own GUIs for their mod-specific slots if they want.
* **Built-in Curios API compatibility layer.** Mods compiled against the real Curios API work without Curios installed (see below).

## Differences from Curios API

Regalia Slots API is a rename-and-extend fork of Curios API 16.0.0 for Minecraft 26.2. The core slot, inventory, GUI, command, networking and JEI/REI code is the same as upstream (140 of the 157 matching source files are identical after the rename). What differs:

* **Identity.** Mod id `regalia_slots_api` (instead of `curios`), Java package `com.skd.regaliaslotsapi`, `RegaliaSlotsApi*` class names, and `regalia_slots_api` assets, data and config keys.
* **Default slots.** Upstream ships an empty `slots` config list; Regalia ships the 11 preset slots assigned to player-like entities, so items that only carry a `curios:` item tag still have somewhere to go.
* **Curios compatibility layer.** The jar also declares a second logical mod `curios` and bundles the original `top.theillusivec4.curios.api` classes, together with adapters for the registry, network, codecs, slots, item handler and client renderers. Third-party mods that check for Curios, or compile against its API, run without the real Curios, and items exposing the Curios `curios:item` capability are picked up as curios.
* **Legacy world migration.** Items equipped through the real Curios are moved into the matching Regalia slots on each player's first login after swapping the mods; anything that does not fit is returned to the normal inventory.

Regalia Slots API **cannot be installed together with the real Curios** (duplicate mod id `curios`); remove one before adding the other. See `docs/WORKFLOW_REGALIA_SLOTS_API_26-2.md` for the compatibility layer's architecture and known limitations.

## Adding to Your Project

```gradle
repositories {
    maven {
        name = "RegaliaSlotsApi"
        url = uri("https://gitlab.com/api/v4/projects/<project-id>/packages/maven")
    }
}

dependencies {
    runtimeOnly "com.skd.regaliaslotsapi:regalia_slots_api-neoforge:${version}"
    compileOnly "com.skd.regaliaslotsapi:regalia_slots_api-neoforge:${version}:api"
}
```

Replace `${version}` with the version of Regalia Slots API that you want to use.

## Credits & License

Regalia Slots API is a fork of [Curios API](https://www.curseforge.com/minecraft/mc-mods/curios) by [TheIllusiveC4](https://github.com/TheIllusiveC4), ported and rebranded for NeoForge 26.2 / Minecraft 26.2. All credit for the original design and implementation goes to the original author.

Licensed under **LGPL-3.0-or-later**, the same licence as the upstream project. The full texts
of both halves of that licence ship with this repository:

- [`COPYING`](COPYING) — the GNU General Public License, version 3
- [`COPYING.LESSER`](COPYING.LESSER) — the GNU Lesser General Public License, version 3

LGPL-3.0 is the GPL-3.0 plus the additional permissions in `COPYING.LESSER`, so both files
are required, not just the lesser one.
