# CurseForge — Variables del proyecto

> Leído por `../../codex-docs/scripts/curseforge-upload.ps1`. Proyecto ya creado en CurseForge (ID `1659506`).

```
project_id = 1659506
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
game_versions = 9638, 9639, 11779, 10150
release_type = beta
```

## Proyecto

| Variable | Valor |
|---|---|
| `curseforge_project_id` | `1659506` |
| `mod_id` | `regalia_slots_api` |
| `display_name` | `Regalia Slots API` |
| `slug` / URL | `regalia-slots-api` |

> El proyecto de CurseForge es **compartido entre las versiones de Minecraft** de este mod
> (26.2 y 1.21.1). Cada JAR se sube al mismo `project_id`; CurseForge las separa por las
> game versions declaradas.

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | `ee776b0a-ee95-4850-b554-06be02a8657f` | Subir archivos JAR (token de cuenta, compartido) |
| Core (GET) | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`. Core: cabecera `x-api-key`.

## Versión actual (rama 1.21.1)

| Variable | Valor |
|----------|-------|
| `minecraft_version` | `1.21.1` |
| `neoforge_version` (loader) | `21.1.249` |
| `framework` | `neoforge` |
| `java_version` | `21` |
| `version` (`gradle.properties`, build propio del fork) | `0.0.0-beta.4` |
| `environment` | `Client`, `Server` (requerido en ambos) |

## Rama

```
minecraft/1.21.1/neoforge-21.1.249/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`. Ejemplo: `1.21.1-neoforge-0.0.0-beta.4`.

## Parámetros del upload

| Campo | Valor | Notas |
|-------|-------|-------|
| `displayName` | `Regalia Slots API (0.0.0-beta.4)` | `display_name (version)` — lo compone el script |
| `changelog` | HTML (contenido literal de `docs/curseforge/versions/<version>.md`) | No resumir |
| `changelogType` | `html` | |
| `releaseType` | `beta` | Segunda beta del port a 1.21.1 |
| `gameVersions` | `[9638, 9639, 11779, 10150]` | IDs: Client + Server + 1.21.1 + NeoForge |
| `JAR` | `regalia_slots_api-1.21.1-neoforge-21.1.249-0.0.0-beta.4.jar` | En `build/libs/` tras `./gradlew clean build` |

### IDs de `gameVersions` para 1.21.1

Verificados 2026-08-31 contra `GET https://minecraft.curseforge.com/api/game/versions`:

| Nombre | ID | gameVersionTypeID |
|--------|-----|--------|
| `Client` | `9638` | 75208 |
| `Server` | `9639` | 75208 |
| `1.21.1` | `11779` | 77784 |
| `NeoForge` | `10150` | 68441 |

> La API devuelve tres entradas `1.21.1` (`11779` typeId 77784, `12735` typeId 1, `16115`
> typeId 615). La correcta para NeoForge es **`11779`**.

## Entorno "Client & Server"

`game_versions` incluye `9638` (Client) y `9639` (Server) → CurseForge marca "Client & Server"
automáticamente, sin paso manual.

## Descripción del proyecto

Sin API para la descripción general. Se pega a mano desde la web (Edit Project → Description) el
HTML de `docs/curseforge/project_description.md`.

## Flujo completo

1. Proyecto ya creado (`project_id = 1659506`), compartido con la rama 26.2.
2. `./gradlew clean build`
3. Crear/actualizar `docs/curseforge/versions/<version>.md` (HTML)
4. Actualizar `CHANGELOG.md`
5. `git commit` + `git push`
6. `git tag -a 1.21.1-neoforge-<version> -m "v<version>: <descr>"` + `git push origin <tag>`
7. Subir JAR: `powershell -File ../../codex-docs/scripts/curseforge-upload.ps1` (desde este repo)
8. Verificar changelog con GET
9. Liberar manualmente desde la web si hace falta
10. Actualizar la descripción del proyecto en la web si cambió `project_description.md`
