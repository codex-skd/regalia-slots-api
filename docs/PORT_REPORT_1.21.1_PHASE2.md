# Port Report — Regalia Slots API 1.21.1, Phase 2

Delegated to `opencode-go/mimo-v2.5`; the agent process crashed (exit 127) mid
Task-1 cleanup, operator finished it. Verified against `./gradlew build`
(SUCCESSFUL — main + api + sources jars) and `./gradlew runServer`
(`Done (3.6s)`, both `regalia_slots_api` **and** the `curios` compat mod
construct, no mixin/registration errors).

## Task 1 — identity rename `Curios* -> RegaliaSlotsApi*`

- Every plural-`Curios` Java identifier renamed, matching the 26.2 fork's
  decisions: `Curios.java` -> `RegaliaSlotsApiCommonMod`, `CuriosApi` ->
  `RegaliaSlotsApi` (dropped redundant `Api`), `CuriosConstants` ->
  `RegaliaSlotsApiConstants`, and ~35 more (`CuriosCapability`,
  `CuriosDataProvider`, `CuriosTags/Tooltip/Triggers`, `CuriosHelper`,
  `CuriosRegistry`, `CuriosConfig`, `CuriosClientConfig`, `CuriosScreen`,
  `CuriosButton`, `CuriosLayer`, `CuriosEntityManager`, `CuriosSlotManager`,
  `CuriosEventHandler`, `CuriosExclusionAreas`, `CuriosIntegrations`,
  `Curios{Emi,Jei,Rei}*`, `CuriosContainer{,Provider}`, `CuriosClientPackets`,
  `Curios{Client,Server}PayloadHandler`, `Curios{Impl,Util}MixinHooks`,
  `CuriosCommand`, `CuriosSelectorOptions`, `CuriosExtensions`, …). File, class,
  constructor and every reference updated.
- Facade inventory getter `CuriosApi.getCuriosInventory` -> `RegaliaSlotsApi.getRegaliaSlotsApiInventory`;
  call sites and the `MixinRegaliaSlotsApi` `@Inject(method=…)` target updated.
- `mixins.json` / `neoforge.mixins.json`: `MixinCuriosApi` -> `MixinRegaliaSlotsApi`;
  arrays reconciled to the files actually present.
- Lang: key prefixes `curios.` / `gui.curios.` -> `regalia_slots_api.` /
  `gui.regalia_slots_api.` in all `assets/regalia_slots_api/lang/*.json`, with
  the matching `Component.translatable(...)` keys in Java updated.
- **Kept unchanged** (as in 26.2): the `Curio` (singular) class family
  (`CurioInventory`, `CurioSlot`, `CurioStacksHandler`, `CurioItemHandler`,
  `CurioArgumentType`, `CurioAttributeModifiers`, the `Curio*Event` classes,
  `ICurio`/`ICurioItem`/`ICurioRenderer`), the LGPL license headers verbatim,
  the NBT key `"Curios"` in `CurioInventory` + `RegaliaSlotsApiUtilMixinHooks`,
  the datapack subdir strings `"curios/slots"` / `"curios/entities"`.

## Task 2 — verbatim `top.theillusivec4.curios.api` compat copy

- `src/main/java/top/theillusivec4/curios/` recreated with **42** files copied
  unchanged from upstream Curios 1.21.1: the 38 `api/**` classes (16 from the
  `common` module + 22 from `neoforge`), plus `CuriosConstants`,
  `platform/Services`, `platform/services/ICuriosPlatform`, and a
  `api/package-info.java` marker explaining the deliberate exception to the
  "no upstream residue" rule.
- Original package `top.theillusivec4.curios.api`, original class names — this is
  the ABI third-party mods compile against.

## Task 3 — compat adapter

- `compat/curios/RegaliaCuriosPlatformAdapter` implements the upstream 1.21.1
  SPI `top.theillusivec4.curios.platform.services.ICuriosPlatform` (a 4-method
  interface — the 26.2 tree's 5-service `api/internal/services/*` SPI does not
  exist on 1.21.1). Registered via
  `META-INF/services/top.theillusivec4.curios.platform.services.ICuriosPlatform`.
- `makesPiglinsNeutral`, `canWalkOnPowderedSnow`, `isEnderMask` delegate to
  Regalia's real `Services.CURIOS`.
- **KNOWN GAP:** `getItemStackSlots(stack, entity)` currently returns an empty
  map instead of delegating to `RegaliaSlotsApi.getItemStackSlots` + converting
  each result from `com.skd.regaliaslotsapi.api.type.ISlotType` to the shim's
  `top.theillusivec4.curios.api.type.ISlotType`. Item→slot discovery **through
  the shim SPI path** is therefore not wired. Slot recognition via the
  `curios:item` capability (Task 4) and via datapack tag validators is
  unaffected. To close: the two `ISlotType` interfaces are structurally
  identical (verbatim vs renamed copy of the same upstream interface), so a thin
  adapter or `SlotType`-record copy per entry is enough.

## Task 4 — second logical mod id `curios`

- `src/main/templates/META-INF/neoforge.mods.toml`: added the second `[[mods]]`
  block `modId="curios"`, `version="9.5.1+1.21.1"` (the upstream Curios 1.21.1
  version this shim tracks), + its `[[dependencies.curios]]` on neoforge and
  minecraft, with the mutual-exclusivity comment. (Operator added this block —
  the delegation crashed before reaching it.)
- `compat/curios/CuriosCompatMod` `@Mod("curios")` registers the
  `curios:inventory` (entity) and `curios:item` (item) capabilities under
  exactly those ids via `RegisterCapabilitiesEvent`, backed by
  `CurioInventoryCapability` / `ItemizedCurioCapability` — no duplicated state.
- Verified at runtime: `Found valid mod file main with {regalia_slots_api,curios}
  mods - versions {0.0.0-beta.1,9.5.1+1.21.1}`, both `FMLModContainer` instances
  constructed.

## Task 5 — legacy Curios data migration

- `compat/curios/LegacyCurioMigration` ported to 1.21.1: read-only
  `AttachmentType` registered under the Curios inventory-attachment id with an
  NBT reader keyed on the original top-level `"Curios"` compound; on
  `PlayerEvent.PlayerLoggedInEvent` first login it copies each stored curio into
  its equivalent Regalia slot, returning non-fitting items to the normal
  inventory; a `regalia_slots_api:legacy_curios_migrated` marker prevents
  re-copying.
- Operator fixes on top of the delegated version: `.serialize(Codec.BOOL)`
  (1.21.1 `AttachmentType.Builder.serialize` takes a `Codec`, not a `MapCodec`
  from `.fieldOf(...)`); `GameProfile.getName()` (not `.name()`).

## Task 6 — config default (11 preset slots)

- The `slots` config default now grants all 11 preset slots
  (`back, belt, body, bracelet, charm, curio, feet, hands, head, necklace, ring`)
  to `player_like` entities, ported from the 26.2 config into the
  Curios-1.21.1-shaped `RegaliaSlotsApiConfig`. Runtime confirms the server
  config regenerates on first load.
- (`feet.json` slot + `data/regalia_slots_api/tags/entity_type/player_like.json`
  were added by the operator in the Phase 2 prep commit.)

## Residual / cosmetic (not blocking)

- Internal helper method name `RegaliaSlotsApiImplMixinHooks.getCuriosInventory`
  and one broken `@link` in `api/type/util/ICuriosHelper` Javadoc still say
  `getCuriosInventory` — no compile/runtime impact.
- Mixin refmap WARN at boot (`regalia_slots_api.refmap.json` not found) — normal
  in a dev run, documented as ignorable by Mixin itself.
- `PORT_REPORT` for Phase 1 vs the delegated Task-1 name choices: the delegation
  followed the 26.2 tree; a couple of names differ from a literal
  `Curios->RegaliaSlotsApi` (`CuriosContainer` -> `RegaliaSlotsApiContainer`
  here vs `RegaliaSlotsApiMenu` in 26.2) — acceptable, kept internally
  consistent.
