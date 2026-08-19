# Flujo de trabajo — Regalia Slots API (NeoForge)

> **Versión del workflow**: 1.18.0 (codex-docs)
> Este archivo pertenece al proyecto **Regalia Slots API**. Cambios aquí solo afectan a este proyecto.
> **Trabaja directamente con este archivo**: es el workflow operativo del mod, autocontenido. No leas `codex-docs/WORKFLOW_AGENT.md` ni `WORKFLOW_GENERIC.md` de forma rutinaria.
> On-demand (solo si la tarea lo necesita): `codex-docs/reference/CURSEFORGE.md` (formato HTML al publicar), `codex-docs/reference/GRAPHIFY.md` (backend LLM de Graphify), `codex-docs/reference/REPO_SETUP.md` (setup único de repo).

## Específico del mod

| Dato | Valor |
|---|---|
| Mod ID (`gradle.properties`) | `regalia_slots_api` |
| Clase principal | `RegaliaSlotsApiCommonMod` (cliente: `RegaliaSlotsApiClientMod`) |
| Display name | `Regalia Slots API` |
| Versiones de Minecraft | `26.2` |
| Rama | `minecraft/26.2/neoforge-26.2.0.57/production` (histórico: `minecraft/26.2/neoforge-26.2.0.45-beta/production`) |
| Framework | NeoForge (build propio `net.neoforged.moddev`, no genérico) |

## Nota de fork

Este mod es un **fork de [Curios API](https://www.curseforge.com/minecraft/mc-mods/curios) por TheIllusiveC4**, licenciado LGPL-3.0-or-later. Se ha renombrado por completo (mod_id, paquetes Java, assets, clases) para publicarse como proyecto independiente:

- Paquete Java: `top.theillusivec4.curios` → `com.skd.regaliaslotsapi`
- `mod_id`: `curios` → `regalia_slots_api`
- Clases: prefijo `Curios*` → `RegaliaSlotsApi*`
- Assets/data: `assets/curios/`, `data/curios/` → `assets/regalia_slots_api/`, `data/regalia_slots_api/`

**Atribución obligatoria** (requisito LGPL, no eliminar): créditos a TheIllusiveC4 en `README.md`, `docs/curseforge/project_description.md` y `neoforge.mods.toml` (campo `credits`, `license`). `COPYING` (texto GPL) y `LICENSE` (resumen corto) se eliminaron a petición del usuario (2026-08-19) por no ser propios — se conserva únicamente `COPYING.LESSER` (texto de la LGPL en sí, el mínimo que exige la licencia), enlazado desde `README.md` y empaquetado en el JAR (`sourcesJar`/`jar` en `build.gradle`).

**Build propio**: a diferencia del resto de mods del grupo (que usan la plantilla estándar de `net.neoforged.moddev` con claves `mod_version`/`mod_group_id`/`neo_version` en `gradle.properties`), este fork **conserva el sistema de build original de Curios** (claves `version`/`group`/`mod_id`/`neoforge_version`, generación de `neoforge.mods.toml` vía `generateModMetadata` con plantilla en `src/main/templates/`). No forzar la plantilla genérica sobre este build — respeta la arquitectura original para minimizar riesgo de romper la compilación.

- `base.archivesName` sí se ajustó a la convención del workspace (`<mod_id>-<mc>-neoforge-<neo_version>`) para que `curseforge-upload.ps1` encuentre el JAR — es el único punto donde se tocó el build propio.
- Los plugins Gradle originales `com.modrinth.minotaur` y `net.darkhax.curseforgegradle` (y las tasks `publishCurseForge`/`modrinth`/`publishMod`) se **eliminaron** de `build.gradle`: exigían JVM 25 para el propio proceso de Gradle (no solo el toolchain del proyecto) y bloqueaban cualquier build en esta máquina (JDK 21). No los usamos igualmente — la publicación va por `codex-docs/scripts/curseforge-upload.ps1`, como el resto de mods.

## Capa de compatibilidad Curios API (desde v0.0.0-beta.4)

Este mod es funcionalmente un fork 1:1 de Curios, pero los mods de terceros que integran con Curios (ej. Sophisticated Backpacks) comprueban específicamente el `modId` `curios` y usan las clases del paquete `top.theillusivec4.curios.api.*` — no reconocen `regalia_slots_api` como equivalente. Para que esos mods funcionen sin que el usuario instale el Curios real, se añadió una capa de compatibilidad en `com.skd.regaliaslotsapi.compat.curios`:

- **Excepción explícita a "sin residuos del mod original"** (ver Buenas prácticas): el árbol `src/main/java/top/theillusivec4/curios/` (solo el paquete `api`, copiado verbatim desde `lib_ext/Curios-26.x`) y las clases `Curios*` bajo `compat/curios/` son **intencionales**, no residuos — necesarias para compatibilidad binaria con mods compilados contra el Curios real. No eliminarlas pensando que son restos del rebrand.
- Segundo `modId` lógico `curios` declarado en el mismo JAR (`neoforge.mods.toml`), con su propio entrypoint `@Mod("curios")` (`CuriosCompatMod`) que registra las capabilities `curios:inventory`/`curios:item` con los mismos IDs que el Curios real, respaldadas en vivo por los datos de Regalia (`RegaliaSlotsApiCapability.INVENTORY`) — sin duplicar estado.
- Los 5 servicios `ServiceLoader` de Curios (`ICuriosRegistry`/`ICuriosSlots`/`ICuriosExtensions`/`ICuriosCodecs`/`ICuriosNetwork`) tienen adaptador propio en `compat/curios/`, registrado vía `META-INF/services/top.theillusivec4.curios.api.internal.services.*`.
- **Incompatible con el Curios real instalado a la vez** (mismo `modId` → NeoForge falla al cargar por duplicado): documentado y aceptado, no se intenta evitar en silencio.
- **Huecos conocidos** (no bloquean reconocimiento de ranura ni lectura/escritura del inventario): comportamiento *custom* por item (`ICurioItem`/`ICurio`, ~20 métodos: sonidos, glow, tooltips) no puenteado — items registrados como curio reciben comportamiento por defecto; renderizado de modelos en la entidad (`api/client/*`) no conectado; codecs de datapack nativo de Curios (`ISlotType`/`ISlotData`/`IEntitiesData`) son stubs (Regalia sigue siendo la única fuente de verdad para tipos de ranura).
- Referencia usada como fuente de verdad: `lib_ext/Curios-26.x` (fuente real de Curios, no decompilar el jar de referencia si el source está disponible ahí).

## Warnings de deprecación pendientes (NO tocar sin más contexto) `./gradlew.bat build` compila con ~46 warnings `[removal]` heredados de Curios (migración de `net.neoforged.neoforge.items.*` a la nueva `net.neoforged.neoforge.transfer.*`, más varios métodos propios de `RegaliaSlotsApi`/`ICurio`/etc.). Se investigó a fondo (2026-08-19): la propia Curios upstream tiene esa migración sin terminar en su propio código — uno de los métodos deprecados (`withSlotModifier`) ni siquiera funciona ya (`return ItemAttributeModifiers.EMPTY;`), y el reemplazo de `IItemHandler` por `ResourceHandler` requiere reimplementar la validación por slot (el `isValid` por defecto de `StacksResourceHandler` es `return true`, o sea que un wrapper genérico rompería el filtrado de items por tipo de slot). Un intento de "limpieza automática" (delegado a OpenCode) efectivamente rompió la validación de slots y cambió la firma pública de `getEquippedRegaliaSlotsApi()` — se revirtió. No reintentar hasta que Curios publique su propia migración completa (entonces portar su fix real, no inventar uno).

## Convenciones de nomenclatura

| Convención | Uso | Ejemplo |
|---|---|---|
| **snake_case** | `mod_id`, assets/, packages Java | `regalia_slots_api` |
| **PascalCase** | Clases Java principales | `RegaliaSlotsApiCommonMod` |
| **camelCase** | Variables, métodos, config keys | `regaliaSlotsApiConfig` |
| **Title Case** | Display name (README, CHANGELOG, docs, CurseForge) | `Regalia Slots API` |

## Organización y ramas

- Un repo GitLab por mod (`stalking-dragons/minecraft/regalia-slots-api`), una rama `minecraft/26.2/neoforge-26.2.0.45-beta/production` — este clon local trabaja en esa rama.
- Carpeta local: `regalia_slots_api/neoforge/26.2/`.
- `*/main` y CI/CD: setup único al crear el repo (`codex-docs/reference/REPO_SETUP.md`) — no releer ni modificar.

## Estructura del proyecto

`build.gradle` (build propio, ver nota de fork) · `gradle.properties` (`mod_id`, `mod_name`, `group`, `version`, `neoforge_version`) · `settings.gradle` · `src/main/java/com/skd/regaliaslotsapi/` · `src/main/resources/assets/regalia_slots_api/` · `src/main/templates/META-INF/neoforge.mods.toml` (plantilla) · `libs/` (versionado) · `lib_ext/` y `temp/` (no versionados) · `docs/` (WORKFLOW + curseforge/) · `CHANGELOG.md` · `README.md` · `graphify-out/` (versionado).

## Versionado

- Beta `0.0.0-beta.X` · Release `X.Y.Z` (SemVer: MAJOR breaking / MINOR feature / PATCH fix)
- Se define en `gradle.properties`: `version=0.0.0-beta.X` (clave `version`, no `mod_version` — build propio)
- JAR: `<mod_id>-<minecraft_version>-neoforge-<neoforge_version>-<version>.jar` vía `archivesName` en `build.gradle`

## Commits (Conventional Commits)

`<tipo>[<ámbito>]: <descripción>` + body opcional. Tipos: `feat` · `fix` · `refactor` · `docs` · `chore` · `style` · `perf` · `test`. El mensaje **debe incluir la versión** (`v<version>`):

```
git commit -m "feat: rebrand Curios API fork to Regalia Slots API

v0.0.0-beta.1"
```

## Tags (GitLab)

Cada subida a CurseForge crea un tag. Formato: beta `26.2-neoforge-beta.X` · release `26.2-neoforge-X.Y.Z`.

## Flujo por tarea

**1. Desarrollo**

```bash
git checkout minecraft/26.2/neoforge-26.2.0.45-beta/production
./gradlew.bat build
git add -A
git commit -m "feat: <descripción>

v<version>"
git push
```

**2. Preparar versión para CurseForge** — solo si el usuario confirma: bump `version` en `gradle.properties` → `./gradlew.bat clean build` → release notes en `docs/curseforge/versions/<version>.md` + actualizar `CHANGELOG.md` → commit `chore: bump version to <version>` → tag `26.2-neoforge-beta.X` → push → subir JAR (`codex-docs/scripts/curseforge-upload.ps1`) solo si el usuario confirma.

**3. Release estable** — `version=1.0.0` + commit + tag `26.2-neoforge-1.0.0`.

**4. Actualizar Knowledge Graph (Graphify)** — tras cada push a remoto:

```bash
GRAPHIFY="C:\Users\llagu\AppData\Local\Packages\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\LocalCache\local-packages\Python313\Scripts\graphify.exe"
"$GRAPHIFY" extract .              # 1ª vez
"$GRAPHIFY" update . --force       # tras cambios de código
git add graphify-out/ && git commit -m "chore: update knowledge graph" && git push
```

Leer siempre `GRAPH_REPORT.md`, nunca `graph.json`/`graph.html`. Sin copias fechadas.

## Buenas prácticas

- Un commit por cambio lógico · commit+push tras cada cambio funcional y de docs
- `clean build` siempre antes del JAR final · versionar antes de CurseForge · CHANGELOG al día
- Graphify actualizado tras cada release · nomenclatura consistente · sin basura en repo
- README en inglés siempre actualizado · **sin residuos del mod original** (paquetes `top/theillusivec4/`, clases `Curios*`, assets `curios/`) — excepto la capa de compatibilidad Curios API intencional, ver sección dedicada · **atribución de fork explícita** (README, project_description, `credits` en mods.toml) — no justifica código muerto ni assets huérfanos

## Idioma

| Ámbito | Idioma |
|---|---|
| Código fuente, logs, nombres técnicos, commits | Inglés (en-US) |
| README.md | Inglés (en-US) |
| Documentación interna (docs/, CHANGELOG, este archivo) | Castellano (es-ES) |
| CurseForge (descripción, release notes) | Inglés (en-US) |
