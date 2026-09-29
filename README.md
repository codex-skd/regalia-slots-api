# Regalia Slots API

## Overview

Regalia Slots API is a flexible and expandable accessory/equipment API for users and developers. It provides functionality for developers to add extra accessory/equipment slots in a convenient and compatible manner, as well as giving users the ability to configure these slots to their preferences. Regalia Slots API does not add any content except for an inventory GUI and a couple of example items available through the Creative menu. Out of the box it grants the 11 built-in preset slots (back, belt, body, bracelet, charm, curio, feet, hands, head, necklace, ring) to player-like entities — configurable via the `slots` option, including down to none if you want fully on-demand slots instead.

Regalia Slots API also ships a built-in **Curios API compatibility layer**: third-party mods built against the real [Curios API](https://www.curseforge.com/minecraft/mc-mods/curios) (mod id `curios`, package `top.theillusivec4.curios.api`) are recognized and interoperate transparently, without requiring Curios itself to be installed. See `docs/WORKFLOW_REGALIA_SLOTS_API_1-21-1.md` for the compatibility layer's architecture and known limitations.

## Features

* **Expandable equipment slots through a central library.** New equipment slots can be made and managed easily through an identifier registry. Identical identifiers will be merged together to avoid functional redundancies and provide maximum compatibility to potential items, while unique identifiers can still be used to mark special types when appropriate.
* **Slots are only made on-demand.** There are no slots included by default, all slots are created only as needed. This reduces instances where one or more superfluous slots are present without any suitable items to go into the slot.
* **Slots are completely customizable and manipulable.** Slots can have custom backgrounds, different sizes, and can even be disabled or hidden by default. Developers can access functions to enable/disable a player's slots or add/remove a certain number of slots of a given type.
* **Flexible item-to-slot relations using the vanilla tag system.** Potential curios are selected through the vanilla tag system — categorizing items into slot types is as easy as creating a `.json` file in `data/regalia_slots_api/tags`. Items can be categorized into as many slot types as needed, and these settings can be overridden entirely.
* **Complete integration with other inventory mechanics.** Mending and Curses work with all applicable items equipped in the accessory slots.
* **Accessible from a single GUI.** Regalia Slots API comes with its own GUI accessible from the inventory that shows all of the available slots to a player. Developers can still provide their own GUIs for their mod-specific slots if they want.

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

Regalia Slots API is a fork of [Curios API](https://www.curseforge.com/minecraft/mc-mods/curios) by [TheIllusiveC4](https://github.com/TheIllusiveC4), ported and rebranded for NeoForge 21.1.249 / Minecraft 1.21.1. All credit for the original design and implementation goes to the original author.

Licensed under [LGPL-3.0-or-later](COPYING.LESSER), same as the upstream project.
