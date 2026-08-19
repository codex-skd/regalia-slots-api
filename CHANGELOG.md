# Changelog — Regalia Slots API

Registro de cambios de este fork. Para el historial completo de Curios API (mod original de TheIllusiveC4 del que procede este fork), ver el [CHANGELOG del proyecto original](https://github.com/TheIllusiveC4/Curios/blob/26.x/CHANGELOG.md).

## [0.0.0-beta.4] - 2026-08-19

### Añadido

- Capa de compatibilidad con la API de Curios (`com.skd.regaliaslotsapi.compat.curios`): mods de terceros que dependen del `modId` `curios` y el paquete `top.theillusivec4.curios.api.*` (ej. Sophisticated Backpacks) ahora reconocen e interactúan con las ranuras de Regalia Slots API sin necesidad de instalar Curios. Segundo `modId` lógico `curios` declarado en el mismo JAR, con capabilities (`curios:inventory`, `curios:item`) respaldadas en vivo por los datos reales de Regalia — sin duplicar estado.
- Ver `docs/WORKFLOW_REGALIA_SLOTS_API_26-2.md` (sección "Capa de compatibilidad Curios API") para huecos conocidos (comportamiento custom por item, renderizado de modelos) y la incompatibilidad intencional con el Curios real instalado a la vez.

## [0.0.0-beta.3] - 2026-08-19

### Cambiado

- Eliminados `COPYING` (texto GPL) y `LICENSE` (resumen corto) del repositorio y del JAR — se conserva únicamente `COPYING.LESSER` (texto de la LGPL, el mínimo que exige la licencia), enlazado desde `README.md`.

## [0.0.0-beta.2] - 2026-08-19

### Corregido

- Los 11 slots incorporados (anillo, collar, cabeza, etc.) mostraban un icono de "textura perdida" (recuadro morado/negro) en la GUI de inventario porque sus definiciones en `data/regalia_slots_api/curios/slots/*.json` seguían referenciando el namespace `curios:` (icono y validador) en vez de `regalia_slots_api:`.

## [0.0.0-beta.1] - 2026-08-19

### Añadido

- Port inicial a NeoForge 26.2.0.45-beta / Minecraft 26.2 como fork independiente de Curios API.
- Rebrand completo: `mod_id` `curios` → `regalia_slots_api`, paquete Java `top.theillusivec4.curios` → `com.skd.regaliaslotsapi`, clases `Curios*` → `RegaliaSlotsApi*`, assets/data `curios/` → `regalia_slots_api/`.
