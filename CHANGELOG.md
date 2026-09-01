# Regalia Slots API (1.21.1) — Changelog

Branch `minecraft/1.21.1/neoforge-21.1.249/production`. History independent of the 26.2 branch.

## [0.0.0-beta.5] - 2026-09-01

### Fixed

- **Entity slot assignment via tags never worked**: `data/<mod>/curios/entities/*.json` files using a tag
  reference in `"entities"` (e.g. `"#regalia_slots_api:player_like"`, the standard Curios pattern) crashed
  datapack loading with `ResourceLocationException: Non [a-z0-9_.-] character in namespace`.
  `RegaliaSlotsApiEntityManager.getSlotsForEntities` detected the leading `#` but passed the whole string
  (including `#`) to `ResourceLocation.parse`. Now strips the `#` before parsing the tag id, matching Curios.

## [0.0.0-beta.4] - 2026-09-01

### Fixed

- **Curios compatibility layer**: Added missing internal mixin hook classes (`CuriosImplMixinHooks`, `CuriosUtilMixinHooks`) at `top.theillusivec4.curios.mixin.*` so third-party mods (Iron's Spellbooks, etc.) that mix into Curios internals can find their target classes. Previously these mods crashed with `ClassNotFoundException: top.theillusivec4.curios.mixin.CuriosImplMixinHooks`.

### Technical

- New compat shim classes in `src/main/java/top/theillusivec4/curios/mixin/` delegating to the renamed `RegaliaSlotsApiImplMixinHooks` implementation.

## [0.0.0-beta.3] - 2026-09-01

### Fixed

- **ResourceLocation validation**: Fixed crash in `ResourceLocation.assertValidPath` when viewing item tooltips with slot identifiers containing invalid ResourceLocation characters (uppercase, spaces, special characters). The "tag" curio predicate now validates slot identifiers before creating ResourceLocations.

### Technical

- Added `ResourceLocation.isValidPath(id)` check in `RegaliaSlotsApiImplMixinHooks` static initializer for the "tag" predicate to prevent invalid path exceptions from third-party slot type identifiers.

## [0.0.0-beta.2] - 2026-08-31

### Fixed

- **Curios API compatibility**: Added mixin implementation for `top.theillusivec4.curios.api.CuriosApi` so other mods (Ars Nouveau, etc.) can properly access Curios inventory capabilities without spamming "Missing Curios API implementation!" errors in server logs.
- Other mods using `CuriosApi.getCuriosInventory()`, `CuriosApi.getCurio()`, `CuriosApi.getSlots()`, etc. now work correctly with Regalia Slots API as the Curios provider.

### Technical

- New mixin `MixinCuriosApi` targeting `top.theillusivec4.curios.api.CuriosApi` with adapter methods bridging Regalia's internal API to the Curios API types.
- Adapter methods in `RegaliaSlotsApiImplMixinHooks` for slot types, capabilities, predicates, and slot contexts.
- Made `ShimSlotType` constructor public for cross-package usage.

## [0.0.0-beta.1] - 2026-08-31

### Added

- **Initial port to Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21). Strategy: **re-fork**
  from the upstream Curios API 1.21.1 sources (9.5.1+1.21.1, NeoForge 21.1.60), flattened
  from multi-loader (`common/` + `neoforge/`) into a single NeoForge module and rebranded to
  the Regalia Slots API identity — not a back-port of the 26.2 fork (whose internal
  architecture is Curios 26.2's).
- **Curios compatibility layer**: verbatim `top.theillusivec4.curios.api` copy (1.21.1 shape,
  42 files), second logical `curios` mod id (`@Mod("curios")`) registering the
  `curios:inventory` / `curios:item` capabilities, `ICuriosPlatform` SPI adapter, and
  `LegacyCurioMigration` (first-login carry-over of items from a world that used the real
  Curios, keyed on the original `"Curios"` NBT compound).
- **11 preset slots** (back, belt, body, bracelet, charm, curio, feet, hands, head, necklace,
  ring) granted to `player_like` entities by default.

### Technical

- Package `top.theillusivec4.curios` → `com.skd.regaliaslotsapi`; class `Curios*` →
  `RegaliaSlotsApi*` (`Curio` singular, LGPL headers and NBT/datapack strings kept verbatim);
  mod id `curios` → `regalia_slots_api`.
- Build: RSA 26.2 `net.neoforged.moddev` shell retargeted to NeoForge 21.1.249 / Java 21
  (EMI/REI `compileOnly` re-enabled, `regalia_slots_api_test` sourceSet wiring dropped).
- `RegaliaCuriosPlatformAdapter.getItemStackSlots` wired via `ShimSlotType` so third-party
  mods resolving item→slot through the real Curios API entry point see Regalia's slots.
- Verified: `./gradlew build` OK (main + api + sources jars); `./gradlew runServer` reaches
  `Done`, both `regalia_slots_api` and `curios` mods load, no mixin/registration errors.
- Port detail: `docs/PORT_REPORT_1.21.1_PHASE2.md`.
