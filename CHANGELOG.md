# Changelog — Regalia Slots API

Registro de cambios de este fork. Para el historial completo de Curios API (mod original de TheIllusiveC4 del que procede este fork), ver el [CHANGELOG del proyecto original](https://github.com/TheIllusiveC4/Curios/blob/26.x/CHANGELOG.md).

## [1.1.1] - 2026-08-27

### Corregido

- Efecto de Supersalto II (Jump Boost II) del ítem `angelic_faher` de Reliquary no se aplicaba consistentemente cuando el ítem estaba en la ranura de Amuleto (necklace). La capa de compatibilidad Curios ahora delega correctamente `curioTick`, `onEquip`, `onUnequip`, `onStateChange`, `canEquip`, `canUnequip` y demás métodos de `ICurioItem` a través de `RegaliaCurioAdapter`, permitiendo que efectos activos por tick funcionen igual que en el Curios real.

## [1.1.0] - 2026-08-20

### Añadido

- Migración automática de datos al sustituir el Curios real por este mod: los items que un jugador tenía equipados con el Curios real ya no se pierden. Al primer login tras el cambio, se copian a la ranura equivalente de Regalia Slots API; lo que no encaja se devuelve al inventario normal en vez de perderse. Requiere quitar el jar del Curios real de `mods/` (no pueden coexistir, mismo `modId`). Verificado en partida real con datos reales de un jugador (mochila de Sophisticated Backpacks, talismán de Equivalent Legacy, pluma angelical de Reliquary).

## [1.0.0] - 2026-08-20

Primera versión estable. Consolida la capa de compatibilidad con Curios API introducida en 0.0.0-beta.4 tras pruebas en modpack real con Sophisticated Backpacks, Toms Storage, Reliquary, EvilCraft y Ascendant Attributes.

### Añadido

- Capa de compatibilidad con la API de Curios lista para producción: mods de terceros que dependen del `modId` `curios` reconocen e interactúan con las ranuras de Regalia Slots API sin instalar Curios.
- `RegaliaSlotsApiConfig.Common.slots` ahora trae por defecto las 11 ranuras preset (back, belt, body, bracelet, charm, curio, feet, hands, head, necklace, ring) asignadas a entidades tipo jugador, en vez de una lista vacía — evita que items de terceros con solo un tag `curios:<slot>` (sin `entities.json` propio) se queden sin ranura donde ir.

### Corregido

- El validador `regalia_slots_api:tag` de las ranuras base ahora también reconoce tags bajo el namespace `curios:` (además de `regalia_slots_api:`), para que items etiquetados por mods de terceros contra el Curios real se reconozcan igualmente.

### Cambiado

- NeoForge bump a `26.2.0.57` (coincide con la versión real del servidor de destino). Nueva rama `minecraft/26.2/neoforge-26.2.0.57/production` (con su `main` correspondiente); la rama `.../26.2.0.45-beta/production` queda como histórico.

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
