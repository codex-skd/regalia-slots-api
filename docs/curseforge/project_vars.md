# CurseForge — Variables del proyecto

> Leído por `codex-docs/scripts/curseforge-upload.ps1`. Proyecto ya creado en CurseForge (ID `1659506`) — pendiente de rellenar `api_token` y pegar la descripción (ver `codex-docs/docs/tareas_operador/TAREAS_OPERADOR.md`).

```
project_id = 1659506
api_token =
game_versions = 9638, 9639, 16498, 10150
release_type = beta
```

| Variable | Valor |
|---|---|
| `minecraft_version` | `26.2` |
| `framework` | `neoforge` |
| `neo_version` | `26.2.0.45-beta` |
| `java_version` | `21` |
| `mod_version` | En `gradle.properties` (clave `version`, build propio del fork) |
| `environment` | `Client`, `Server` (ambos IDs en `game_versions` → CurseForge marca "Client & Server" automáticamente) |
| `slug sugerido` | `regalia-slots-api` |

`api_token` es el token de cuenta CurseForge (compartido con el resto de mods) — copiar aquí una vez creado el proyecto.
