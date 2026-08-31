# Regalia Slots API (1.21.1) — Changelog

Branch `minecraft/1.21.1/neoforge-21.1.249/production`. History independent of the 26.2 branch.

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
