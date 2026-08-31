<h1 align="center">Regalia Slots API</h1>
<p align="center"><em>A flexible and expandable accessory/equipment API for Minecraft 26.2</em></p>

<hr>

<h2>Overview</h2>
<p>Regalia Slots API provides functionality for developers to add extra accessory/equipment slots in a convenient and compatible manner, and gives users the ability to configure those slots to their preferences. By default, the mod adds no content beyond an inventory GUI — slots are created entirely on-demand by other mods that depend on this API.</p>

<h2>Features</h2>

<h3>Expandable equipment slots through a central library</h3>
<p>New equipment slots can be registered easily through an identifier registry. Identical identifiers merge together to avoid functional redundancy, while unique identifiers can still mark special slot types.</p>

<h3>Slots are only made on-demand</h3>
<p>No slots are included by default — every slot is created only as needed, avoiding superfluous empty slots.</p>

<h3>Slots are completely customizable</h3>
<p>Slots can have custom backgrounds, different sizes, and can be disabled or hidden by default. The API exposes functions to enable/disable a player's slots or add/remove slots of a given type.</p>

<h3>Flexible item-to-slot relations using the vanilla tag system</h3>
<p>Potential accessory items are selected through the vanilla tag system — categorizing an item is as simple as adding it to the appropriate <code>data/regalia_slots_api/tags</code> file.</p>

<h3>Complete integration with other inventory mechanics</h3>
<p>Mending and Curses work with items equipped in accessory slots, alongside various developer-facing helpers for integrating existing items and mechanics.</p>

<h3>Accessible from a single GUI</h3>
<p>A dedicated GUI accessible from the inventory shows all available slots to a player. Developers can still provide their own GUIs for mod-specific slots.</p>

<hr>

<table>
<tr><th>Requirement</th><th>Details</th></tr>
<tr><td>Minecraft</td><td>26.2</td></tr>
<tr><td>NeoForge</td><td>26.2.0.45-beta+</td></tr>
<tr><td>Side</td><td>Client &amp; Server</td></tr>
</table>

<hr>

<h2>Credits</h2>
<p>Regalia Slots API is a fork of <a href="https://www.curseforge.com/minecraft/mc-mods/curios">Curios API</a> by <strong>TheIllusiveC4</strong>, ported and rebranded for NeoForge 26.2. All credit for the original design and implementation goes to the original author. Licensed under LGPL-3.0-or-later, same as the upstream project.</p>

<p align="center"><em>Regalia Slots API — accessory slots, done right.</em></p>
