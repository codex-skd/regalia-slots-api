# Changelog — Regalia Slots API

Registro de cambios de este fork. Para el historial completo de Curios API (mod original de TheIllusiveC4 del que procede este fork), ver el [CHANGELOG del proyecto original](https://github.com/TheIllusiveC4/Curios/blob/26.x/CHANGELOG.md).

## [0.0.0-beta.2] - 2026-08-19

### Corregido

- Los 11 slots incorporados (anillo, collar, cabeza, etc.) mostraban un icono de "textura perdida" (recuadro morado/negro) en la GUI de inventario porque sus definiciones en `data/regalia_slots_api/curios/slots/*.json` seguían referenciando el namespace `curios:` (icono y validador) en vez de `regalia_slots_api:`.

## [0.0.0-beta.1] - 2026-08-19

### Añadido

- Port inicial a NeoForge 26.2.0.45-beta / Minecraft 26.2 como fork independiente de Curios API.
- Rebrand completo: `mod_id` `curios` → `regalia_slots_api`, paquete Java `top.theillusivec4.curios` → `com.skd.regaliaslotsapi`, clases `Curios*` → `RegaliaSlotsApi*`, assets/data `curios/` → `regalia_slots_api/`.
