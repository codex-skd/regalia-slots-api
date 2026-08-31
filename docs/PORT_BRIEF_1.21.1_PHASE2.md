# Delegation brief — Regalia Slots API 1.21.1, Phase 2 (identity rename + Curios compat layer)

## Context

`regalia_slots_api` is a rebranded fork of **Curios API** (TheIllusiveC4, LGPL-3.0).
We are creating its **Minecraft 1.21.1 / NeoForge 21.1.249** version.

**Phase 1 is already done and committed**: upstream Curios 9.5.1+1.21.1 was
flattened (its `common/` + `neoforge/` modules merged into one NeoForge module),
the Java package `top.theillusivec4.curios` was renamed to `com.skd.regaliaslotsapi`,
and the mod id `curios` was renamed to `regalia_slots_api` across
assets/data/lang/mixins/services/toml. It **compiles** (`./gradlew build`) and
**loads** (`./gradlew runServer` reaches "Done"). 123 Java files. No code was
changed beyond the mechanical package rename — **class names are still `Curios*`**.

Your job is Phase 2: finish the identity rename and add the Curios compatibility
layer, keeping strict **Minecraft 1.21.1 API** (upstream Curios 1.21.1 is the
reference for what 1.21.1 API looks like — never introduce API from the 26.2
reference tree).

## Reference material (all inside `temp/ref/`, your sandbox blocks outside reads)

| Path | What it is | How to use it |
|---|---|---|
| `temp/ref/curios-1.21.1-common-java/` | Upstream Curios 1.21.1, `common` module Java | **1.21.1 API truth.** The pre-rename source of most of `src/main/java`. |
| `temp/ref/curios-1.21.1-neoforge-java/` | Upstream Curios 1.21.1, `neoforge` module Java | Same — the NeoForge half. |
| `temp/ref/curios-1.21.1-common-resources/`, `.../curios-1.21.1-neoforge-resources/` | Upstream Curios 1.21.1 resources | reference for the verbatim api package's needs, `pack.mcmeta`, services |
| `temp/ref/rsa-26.2-java/` | The **26.2** fork's full `com/skd/regaliaslotsapi` tree | **Structural reference only.** Shows the exact `Curios* -> RegaliaSlotsApi*` name decisions, the `compat/curios/` layer design, the fake-`curios`-mod wiring, `LegacyCurioMigration`. **DO NOT copy its API** — it targets Curios 26.2's architecture (5-service SPI, `ResourceHandler`, DataComponents) which does not exist in 1.21.1. |
| `temp/ref/rsa-26.2-resources/`, `.../rsa-26.2-templates/` | 26.2 fork resources + toml template | reference for `META-INF/services/*` file set, the second `[[mods]] modId="curios"` block, mixins.json shape |
| `temp/ref/RSA-26.2-WORKFLOW.md` | The 26.2 workflow doc | its sections "Nota de fork", "Capa de compatibilidad Curios API", "Warnings de deprecación pendientes" are the **spec** for the compat layer. Read them. |

`temp/` is gitignored — reference only, never edit it, never treat it as a deliverable.

## HARD CONSTRAINTS

1. **Target API = Minecraft 1.21.1 + NeoForge 21.1.249 + Java 21.** The current
   `src/main/java` already compiles on it. Anything you add or rename must stay
   on that API. The upstream Curios **1.21.1** trees in `temp/ref/` are the
   authority. The `rsa-26.2-java` tree is NOT — it is Curios 26.2 architecture.
   Do not pull in `net.minecraft.resources.Identifier`, `net.neoforged.neoforge.transfer.*`
   / `ResourceHandler`, `DataComponents`-based slot types, the
   `api/internal/services/*` 5-interface SPI, `AddClientReloadListenersEvent`,
   `getDeltaTracker()`, or any other 26.2-only symbol.
2. **Do NOT run git or gradle.** The operator builds and verifies.
3. **Preserve every LGPL license header verbatim.** Files carry a header block
   starting `* This file is part of Curios, a mod made for Minecraft.` — the word
   "Curios" inside these comment blocks is the upstream project name and **must
   not be renamed**. Only rename `Curios` when it is a Java identifier
   (class/method/field/variable name), never in a license header or a Javadoc
   sentence about the upstream project.
4. **Do NOT rename these string literals** (they are wire/NBT/format contracts
   that must match real Curios for compat):
   - the NBT key `"Curios"` in `CurioInventory` (`getList("Curios", ...)`,
     `compound.put("Curios", ...)`) and in `mixin/CuriosUtilMixinHooks`
     (`DSL.optionalFields("Curios", ...)`)
   - the datapack subdirectory strings `"curios/slots"`, `"curios/entities"`
   - the capability ids `curios:inventory` / `curios:item` (see Task 4)
5. Keep `Curio` (singular, no trailing "s") class names **as-is**:
   `CurioInventory`, `CurioSlot`, `CurioStacksHandler`, `CurioItemHandler`,
   `CurioArgumentType`, `CurioAttributeModifiers`, `CurioCanEquipEvent`,
   `CurioCanUnequipEvent`, `CurioChangeEvent`, `CurioDropsEvent`,
   `CurioAttributeModifierEvent`, interfaces `ICurio` / `ICurioItem` /
   `ICurioRenderer`, etc. Match the 26.2 tree: it also keeps these.
6. All code, comments, identifiers, your final report: **English**.

## TASK 1 — Identity rename `Curios* -> RegaliaSlotsApi*`

Rename the **plural** `Curios` identifier everywhere it is a Java identifier,
matching the exact decisions in `temp/ref/rsa-26.2-java/`:

- `Curios.java` (the `@Mod` class) -> `RegaliaSlotsApiCommonMod.java`, class
  `RegaliaSlotsApiCommonMod`. `@Mod(CuriosConstants.MOD_ID)` stays
  `@Mod(RegaliaSlotsApiConstants.MOD_ID)`.
- `CuriosConstants` -> `RegaliaSlotsApiConstants` (keep `MOD_ID = "regalia_slots_api"`;
  `MOD_NAME` string value `"Curios API"` may stay or become `"Regalia Slots API"` —
  match 26.2).
- `api/CuriosApi.java` -> `api/RegaliaSlotsApi.java`, class `RegaliaSlotsApi`
  (drop the redundant `Api`). Update the static facade name everywhere.
- Every other `Curios<Uppercase>` class/file -> `RegaliaSlotsApi<Uppercase>`
  (`CuriosCapability`, `CuriosDataProvider`, `CuriosTags`, `CuriosTooltip`,
  `CuriosTriggers`, `CuriosRendererRegistry`, `CuriosHelper`, `CuriosRegistry`,
  `CuriosConfig`, `CuriosClientConfig`, `CuriosButton`, `CuriosScreen`,
  `CuriosLayer`, `CuriosEntityManager`, `CuriosSlotManager`, `CuriosEventHandler`,
  `CuriosExclusionAreas`, `CuriosIntegrations`, `CuriosEmiIntegration`,
  `CuriosEmiPlugin`, `CuriosContainerHandler`, `CuriosJeiPlugin`,
  `CuriosReiPlugin`, `CuriosContainer`, `CuriosContainerProvider`,
  `CuriosClientPackets`, `CuriosClientPayloadHandler`,
  `CuriosServerPayloadHandler`, `CuriosImplMixinHooks`, `CuriosUtilMixinHooks`,
  `CuriosCommand`, `CuriosSelectorOptions`, `CuriosExtensions`, …). Rename the
  file, the class, the constructor, and every reference.
- Lowercase `curios` inside camelCase identifiers (variables, method names such
  as `getEquippedCurios` -> `getEquippedRegaliaSlotsApi`, fields like
  `curiosHandler`) — follow the 26.2 tree's choices. Public method signature
  changes are fine here since this is a fresh fork with no existing consumers on
  1.21.1.
- `src/main/resources/regalia_slots_api.mixins.json` and
  `regalia_slots_api.neoforge.mixins.json`: the `mixins` arrays list class
  names under `com.skd.regaliaslotsapi.mixin.core` — update any entry whose
  class you renamed (e.g. `MixinCuriosApi` -> `MixinRegaliaSlotsApi` if you
  rename that mixin; keep names consistent with the actual files).
- Lang files `assets/regalia_slots_api/lang/*.json`: keys are like
  `curios.slot.…`, `gui.curios.…`. Rename the key prefix to `regalia_slots_api.…`
  / `gui.regalia_slots_api.…` **and** update the matching
  `Component.translatable("…")` / translation-key constants in Java so they
  still resolve. Match whatever prefix the 26.2 lang files use.

After this task the project must still compile.

## TASK 2 — Verbatim `top.theillusivec4.curios.api` compat copy

Third-party mods that integrate with Curios import `top.theillusivec4.curios.api.*`
and check `ModList.isLoaded("curios")`. To let those mods work without the real
Curios installed, add back a **verbatim, unrenamed** copy of Curios 1.21.1's
`api` package:

- Create `src/main/java/top/theillusivec4/curios/api/…` containing the **38**
  api classes from upstream Curios 1.21.1: the 16 under
  `temp/ref/curios-1.21.1-common-java/top/theillusivec4/curios/api/` plus the 22
  under `temp/ref/curios-1.21.1-neoforge-java/top/theillusivec4/curios/api/`.
  Copy them **unchanged** — original package `top.theillusivec4.curios.api`,
  original class names (`CuriosApi`, `SlotContext`, `ICurio`, `ICuriosHelper`,
  `ISlotHelper`, `ISlotType`, `CuriosCapability`, the `event/*`, `extensions/*`,
  `type/**`, `client/*` classes, …).
- These api classes reference a few non-api upstream classes
  (`top.theillusivec4.curios.CuriosConstants`, `top.theillusivec4.curios.platform.Services`,
  `top.theillusivec4.curios.platform.services.ICuriosPlatform`). Add just those
  supporting classes verbatim too (under `top/theillusivec4/curios/…`), enough
  for the api package to compile. Keep the set minimal — do not copy the whole
  upstream `common`/`neoforge` implementation a second time.
- This is an **intentional exception** to "no residue of the original mod":
  the `top.theillusivec4.curios` tree here is a deliberate binary-compat shim,
  not leftover rename debris. Add a short comment saying so at the top of
  `top/theillusivec4/curios/api/CuriosApi.java` (or a package-info).

## TASK 3 — Compat adapter: back the shim with the real implementation

The verbatim `top.theillusivec4.curios.api.CuriosApi` facade must return live
data from our `com.skd.regaliaslotsapi` implementation, not sit inert.

- Upstream Curios 1.21.1's SPI is a **single** ServiceLoader interface
  `top.theillusivec4.curios.platform.services.ICuriosPlatform`, plus the
  `CuriosApi` static facade delegating to `ICuriosHelper` / `ISlotHelper`
  obtained from it. (This is the 1.21.1 shape — the 26.2 tree's 5-interface
  `api/internal/services/*` SPI does **not** apply.)
- Create `src/main/java/com/skd/regaliaslotsapi/compat/curios/` with an
  `ICuriosPlatform` implementation (call it `RegaliaCuriosPlatformAdapter`) that
  delegates every call to the renamed `com.skd.regaliaslotsapi` runtime
  (`RegaliaSlotsApi` facade / helper / slot-helper). Register it via
  `src/main/resources/META-INF/services/top.theillusivec4.curios.platform.services.ICuriosPlatform`
  containing the adapter's FQN.
- Where an upstream `top.theillusivec4.curios.api` type and our
  `com.skd.regaliaslotsapi.api` type represent the same concept (`SlotContext`,
  `SlotResult`, `ICurio`, `ISlotType`, …), the adapter converts between them.
- **Known-gap policy (from the 26.2 workflow):** per-item custom `ICurio`/`ICurioItem`
  behaviour (sounds, glow, tooltips — ~20 methods), on-entity model rendering
  (`api/client/*`), and native Curios datapack codecs may be left as
  best-effort / default behaviour. Slot recognition and inventory read/write
  MUST work. Document every gap in the report.

## TASK 4 — Second logical mod id `curios`

- In `src/main/templates/META-INF/neoforge.mods.toml` add a second `[[mods]]`
  block with `modId="curios"` (plus its own `[[dependencies.curios]]` on
  neoforge + minecraft), mirroring
  `temp/ref/rsa-26.2-templates/META-INF/neoforge.mods.toml`. Set
  `version` to a 1.21.1-appropriate value (e.g. `"9.5.1+1.21.1"` — the upstream
  Curios version this shim is built against). Keep the explanatory comment about
  the mutual-exclusivity with real Curios.
- Add `com/skd/regaliaslotsapi/compat/curios/CuriosCompatMod.java` annotated
  `@Mod("curios")` that, on construction, registers the capabilities
  `curios:inventory` (entity) and `curios:item` (item) under **exactly those
  ids**, backed by our `RegaliaSlotsApiCapability` data (no duplicate state).
  Follow the 26.2 `compat/curios/` design, translated to 1.21.1 capability API
  (`EntityCapability.createVoid` / `ItemCapability.createVoid` + register in
  `RegisterCapabilitiesEvent`, the 1.21.1 way our own code already does it).

## TASK 5 — Legacy Curios data migration

Port `LegacyCurioMigration` from `temp/ref/rsa-26.2-java/com/skd/regaliaslotsapi/compat/curios/`
to 1.21.1:

- Register a **read-only** data attachment under the exact id the real Curios
  used for its inventory attachment, with an NBT reader that looks for the
  original top-level key `"Curios"` (our own serialisation uses a different
  outer key — the fork rename did not change the attachment id or the inner
  `CurioInventory` NBT key, but if the outer key differs, this bridge is what
  keeps pre-existing equipped items from being orphaned).
- On each player's first login after the swap, copy every stored curio into its
  equivalent slot; anything that does not fit (missing slot / already occupied)
  goes back to the normal inventory rather than being lost. A per-player marker
  (`regalia_slots_api:legacy_curios_migrated`) prevents re-copying.
- Use 1.21.1 attachment / login-event API (`AttachmentType`, `PlayerEvent.PlayerLoggedInEvent`),
  not the 26.2 variant.

## TASK 6 (already done by operator — for your awareness only)

`feet.json` slot + `data/regalia_slots_api/tags/entity_type/player_like.json`
are already added. You still need to make the config default grant the 11 preset
slots (`back, belt, body, bracelet, charm, curio, feet, hands, head, necklace,
ring`) to `player_like` entities — port that default from
`temp/ref/rsa-26.2-java/com/skd/regaliaslotsapi/config/RegaliaSlotsApiConfig.java`
into our (Curios-1.21.1-shaped) config class, adapting to its actual field type.

## Deliverable

1. Tasks 1–5 applied under `src/`; config default from Task 6 wired.
2. Project correct for `./gradlew build` on NeoForge 21.1.249 by inspection.
3. `docs/PORT_REPORT_1.21.1_PHASE2.md` (English): every file created / renamed /
   modified with the reason; every compat gap left as default/best-effort;
   anything where you guessed at the 1.21.1 API.

Work only inside `G:/Proyectos/Mods_Minecraft/regalia_slots_api/neoforge/1.21.1`.
