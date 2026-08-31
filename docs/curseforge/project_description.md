<h1 align="center">&#128081; Regalia Slots API</h1>

<p align="center"><strong>A flexible, expandable accessory / equipment-slot API for players and developers. Fork of Curios API.</strong></p>

<p align="center">
<img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
<img src="https://img.shields.io/badge/minecraft-26.2%20%7C%201.21.1-blue?style=plastic" alt="Minecraft 26.2 and 1.21.1">
<img src="https://img.shields.io/badge/type-library-brightgreen?style=plastic" alt="Library">
<img src="https://img.shields.io/badge/license-LGPL--3.0-lightgrey?style=plastic" alt="LGPL-3.0-or-later">
</p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<table>
<tr>
<td width="65%">
<p>Regalia Slots API gives mod developers a central library for adding extra accessory / equipment slots in a convenient, compatible way, and gives players a single GUI to manage them. By default the mod adds <strong>no content</strong> beyond that inventory screen &mdash; every slot is created on demand by other mods that depend on this API.</p>

<p>If you were told to install this mod, it's because another mod on the pack depends on it. It also ships a <strong>Curios API compatibility layer</strong>, so mods built against Curios work against Regalia without the real Curios mod installed.</p>
</td>
<td width="35%" align="center">
<a href="https://codex.skdragons.com/" target="_blank"><img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="160"></a>
</td>
</tr>
</table>

<br>

<h2>&#127919; Features</h2>

<h3>&#129513; Expandable slots through a central registry</h3>
<p>New equipment slots are registered through an identifier registry. Identical identifiers merge to avoid redundancy; unique identifiers mark special slot types.</p>

<h3>&#128230; Slots are created on demand</h3>
<p>No slots exist by default &mdash; each is created only when a mod asks for it, so there are no superfluous empty slots.</p>

<h3>&#127912; Fully customizable slots</h3>
<p>Custom backgrounds, per-type sizes, hidden / disabled by default, per-player enable/disable and add/remove of slots of a given type.</p>

<h3>&#127991;&#65039; Tag-driven item&#8594;slot relations</h3>
<p>Accessory items are selected through the vanilla tag system &mdash; categorizing an item is just adding it to the right <code>data/regalia_slots_api/tags</code> file.</p>

<h3>&#128260; Integrates with vanilla mechanics</h3>
<p>Mending and Curses apply to items in accessory slots, plus developer helpers for wiring existing items and mechanics.</p>

<h3>&#129309; Curios API compatibility</h3>
<p>Bundles a verbatim copy of the Curios API and a second logical <code>curios</code> mod id, so mods that check <code>ModList.isLoaded("curios")</code> and import <code>top.theillusivec4.curios.api.*</code> recognize Regalia's slots. Existing worlds that used the real Curios keep their equipped items (first-login migration).</p>

<br>

<h2>&#129521; Mod Structure</h2>

<table>
<tr><th align="left">Area</th><th align="left">What it provides</th></tr>
<tr><td><code>api</code></td><td>Public API surface: slot types, slot context/result, the <code>ICurio</code> / <code>ICurioItem</code> capabilities, events, tags, tooltip and trigger helpers. Also published as a separate <code>-api</code> jar.</td></tr>
<tr><td><code>common</code></td><td>Slot and entity data managers, the curio inventory capability + stack handlers, the inventory / container menu, and the client&#8596;server sync packets.</td></tr>
<tr><td><code>client</code></td><td>The accessory GUI opened from the inventory (buttons, pages, cosmetic toggle) and the on-entity render layer.</td></tr>
<tr><td><code>compat/curios</code></td><td>The Curios compatibility layer: the <code>ICuriosPlatform</code> SPI adapter, the <code>@Mod("curios")</code> entry point registering the <code>curios:inventory</code> / <code>curios:item</code> capabilities, and the legacy Curios data migration.</td></tr>
<tr><td><code>top.theillusivec4.curios.api</code></td><td>Verbatim copy of the Curios API (matching the upstream version each build targets), for binary compatibility with mods compiled against the real Curios.</td></tr>
<tr><td><code>mixin</code> / <code>server</code></td><td>Small vanilla patches the slot logic needs, and the <code>/regalia_slots_api</code> command family.</td></tr>
</table>

<br>

<h2>&#128196; Requirements</h2>

<table>
<tr><td><strong>Minecraft / NeoForge</strong></td><td>see <em>Available Versions</em> below</td></tr>
<tr><td><strong>Java</strong></td><td>21+</td></tr>
<tr><td><strong>Side</strong></td><td>Client and Server (required on both)</td></tr>
<tr><td><strong>Incompatible with</strong></td><td>the real <a href="https://www.curseforge.com/minecraft/mc-mods/curios">Curios API</a> (shared <code>curios</code> mod id &mdash; install one or the other)</td></tr>
</table>

<br>

<h2>&#128230; Available Versions</h2>

<table>
<tr><th align="left">Minecraft</th><th align="left">NeoForge</th><th align="left">Java</th><th align="left">Latest build</th><th align="left">Status</th></tr>
<tr><td>26.2</td><td>26.2.0.45-beta+</td><td>21</td><td><code>1.1.3</code></td><td>Stable</td></tr>
<tr><td>1.21.1</td><td>21.1.249+</td><td>21</td><td><code>0.0.0-beta.1</code></td><td>Beta &mdash; re-fork port from upstream Curios 1.21.1</td></tr>
</table>

<p><em>Both versions share this CurseForge project. Pick the file that matches your Minecraft version &mdash; the API surface is the same across both.</em></p>

<br>

<h2>&#127918; How to Use</h2>

<ol>
<li>Install as a <strong>dependency</strong> of another mod that requires it, on both client and server.</li>
<li>Open the accessory screen from the button in your inventory.</li>
<li>Configure slots per preference in <code>config/regalia_slots_api-common.toml</code> &mdash; by default the 11 preset slots (back, belt, body, bracelet, charm, curio, feet, hands, head, necklace, ring) are granted to players.</li>
</ol>

<br>

---

<br>

<h2>&#128591; Credits &amp; License</h2>

<p>Regalia Slots API is a fork of <a href="https://www.curseforge.com/minecraft/mc-mods/curios">Curios API</a> by <strong>TheIllusiveC4</strong>, rebranded under a new mod id, package and assets. The <code>26.2</code> build targets the current NeoForge line; the <code>1.21.1</code> build is a re-fork from the upstream Curios 1.21.1 sources (see <em>Available Versions</em>). All credit for the original design and implementation goes to the original author. Licensed <strong>LGPL-3.0-or-later</strong>, same as upstream; the full license text ships in the jar (<code>COPYING.LESSER</code>).</p>

<p>Maintained by <strong>Stalking Dragons</strong>.</p>

<br>
<br>

<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons &mdash; Minecraft Modding</em>
</p>
