# Graph Report - 26.2  (2026-08-19)

## Corpus Check
- 196 files · ~165,219 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2047 nodes · 5031 edges · 152 communities (101 shown, 51 thin omitted)
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 247 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `5647ecfc`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Regalia Slots API Common Hooks
- Item Stack Handler
- Humanoid Model Layers
- Server Reload Listeners
- Advancement Sub Providers
- Crafting Menu
- Curio Stack Handler
- Attribute Handling
- Curio Stack Operations
- Curio Inventory Capability
- Item Properties
- Loot Item Conditions
- Regalia Slots API Item Handler
- JSON Resource Reload
- Block Drops Events
- Itemized Regalia Slots API Capability
- Slot Data
- Item Handler Modifiable
- Slot Context
- Player Avatar Rendering
- Regalia Slots API Renderer
- Slot Type
- Entities Data
- Block Tags Provider
- Command Build Context
- Level and Entity Slots
- Slot Item Handler
- ISlotData Entry
- Mixin Inventory
- Container Menu Provider
- Container Screen
- Client Configuration
- Curio Inventory
- Regalia Slots API Screen
- Page Button
- Dynamic Operations
- Amulet Item
- Regalia Slots API Client Mod
- Regalia Slots API Test Registry
- Equipment Slot Group
- Payload Handler
- Regalia Slots API Slot Types
- Recipe Book Screen
- Regalia Slots API Extensions
- Curio Argument Type
- Custom Packet Payloads
- Entities Data
- Regalia Slots API Tooltip
- IEntitiesData Entry
- Attribute Tooltip Events
- Damage and Drop Rules
- Curio Attributes
- Regalia Slot Data
- Regalia Resources
- Item Stack Sync
- Slot Overrides
- Data Generation
- Curio Drops
- Drop Rules
- Regalia Slot API
- Preset Slots
- Regalia GUI
- Slot Data Entry
- Mixin Injection
- GUI Handler
- Network Services
- Mod Plugin
- Cosmetic Buttons
- Render Layers
- Packet Destroy
- Modifiers Sync
- Regalia Slots Sync
- Entity Selector
- NBT Predicate
- Item Capabilities
- Data Sync
- Bonus Count
- Enchanted Count
- Test Armor
- Regalia Slots Open
- Vanilla Open
- Item Grabbed
- Living Entity
- Entity Capabilities
- Mod Event
- Curio Slot Extension
- Page Packet
- Quick Move Packet
- Sync Render Packet
- Slot Type
- Piglin AI
- Powder Snow Block
- Test Provider
- Permanent Slot Modifiers
- Slot Context
- AttributeModifier
- Curio Slot Interface
- Gradle Build Script
- Tag Operations
- Serialization Utilities
- Internal Build Methods
- Logo
- Empty Charm Slot
- Empty Cosmetic Slot
- Empty Curio Slot
- Empty Hands Slot
- Empty Head Slot
- Empty Necklace Slot
- Empty Ring Slot
- Curio Button
- Highlighted Curio Button
- Small Curio Button
- Mod Icon
- Small Highlighted Curio Button
- Amulet Texture
- Crown Texture
- Knuckles Texture
- Empty Back Slot
- Empty Belt Slot
- Empty Body Slot
- Empty Bracelet Slot
- Cosmetic Off
- Highlighted Cosmetic Off
- Cosmetic On
- Highlighted Cosmetic On
- Empty Back Slot
- Empty Belt Slot
- Empty Body Slot
- Empty Bracelet Slot
- Empty Charm Slot
- Empty Cosmetic Slot
- Empty Curio Slot
- Empty Feet Slot
- Empty Hands Slot
- Empty Head Slot
- Empty Necklace Slot
- Empty Ring Slot
- Amulet
- Crown
- Knuckles
- Amulet Texture
- Crown Texture
- Knuckles Texture
- Ring Texture
- API Key Mappings

## God Nodes (most connected - your core abstractions)
1. `SlotContext` - 166 edges
2. `ISlotType` - 69 edges
3. `ICurioStacksHandler` - 67 edges
4. `Item` - 63 edges
5. `IRegaliaSlotsApiItemHandler` - 57 edges
6. `ISlotData` - 49 edges
7. `CurioStacksHandler` - 48 edges
8. `ICurioItem` - 45 edges
9. `DropRule` - 42 edges
10. `CurioInventoryCapability` - 40 edges

## Surprising Connections (you probably didn't know these)
- `RegaliaSlotsApiRegistry` --references--> `CurioAttributeModifiers`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/impl/RegaliaSlotsApiRegistry.java → src/main/java/com/skd/regaliaslotsapi/api/CurioAttributeModifiers.java
- `RegaliaSlotsApiCapability` --references--> `ICurio`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiCapability.java → src/main/java/com/skd/regaliaslotsapi/api/type/capability/ICurio.java
- `RegaliaSlotsApiCapability` --references--> `IRegaliaSlotsApiItemHandler`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiCapability.java → src/main/java/com/skd/regaliaslotsapi/api/type/capability/IRegaliaSlotsApiItemHandler.java
- `RegaliaSlotsApiDataProvider` --references--> `IEntitiesData`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiDataProvider.java → src/main/java/com/skd/regaliaslotsapi/api/type/data/IEntitiesData.java
- `RegaliaSlotsApiDataProvider` --references--> `ISlotData`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiDataProvider.java → src/main/java/com/skd/regaliaslotsapi/api/type/data/ISlotData.java

## Import Cycles
- None detected.

## Communities (152 total, 51 thin omitted)

### Community 0 - "Regalia Slots API Common Hooks"
Cohesion: 0.05
Nodes (42): ItemStack, RegaliaSlotsApiCommonHooks, Builder, Codec, Ints, RegistryFriendlyByteBuf, StreamCodec, SlotTypePredicate (+34 more)

### Community 1 - "Item Stack Handler"
Cohesion: 0.06
Nodes (28): ItemStackHandler, LivingEvent, CurioCanEquipEvent, Deprecated, Internal, ItemStack, TriState, CurioCanUnequipEvent (+20 more)

### Community 2 - "Humanoid Model Layers"
Cohesion: 0.06
Nodes (34): BuildCreativeModeTabContentsEvent, HumanoidModel, ModelLayerLocation, RegisterLayerDefinitions, AmuletModel, HumanoidRenderState, LayerDefinition, ModelPart (+26 more)

### Community 3 - "Server Reload Listeners"
Cohesion: 0.06
Nodes (32): AddServerReloadListenersEvent, CombinedResourceHandler, ConfigValue, Event, FMLCommonSetupEvent, IModBusEvent, RegisterCommandsEvent, RegisterPayloadHandlersEvent (+24 more)

### Community 4 - "Advancement Sub Providers"
Cohesion: 0.06
Nodes (34): AdvancementHolder, AdvancementSubProvider, ContextAwarePredicate, ItemPredicate, Items, LocationPredicate, SimpleCriterionTrigger, SimpleInstance (+26 more)

### Community 5 - "Crafting Menu"
Cohesion: 0.09
Nodes (23): AbstractCraftingMenu, ArgumentTypeInfo, AttachmentType, CraftingContainer, CriterionTrigger, DeferredRegister, MenuType, RecipeBookType (+15 more)

### Community 6 - "Curio Stack Handler"
Cohesion: 0.08
Nodes (12): EntitySelectorParser, ICurioStacksHandler, AttributeModifier, CompoundTag, Deprecated, Identifier, Operation, CompoundTag (+4 more)

### Community 7 - "Attribute Handling"
Cohesion: 0.06
Nodes (40): Attribute, Internal, RegaliaSlotsApiServices, IRegaliaSlotsApiCodecs, Attribute, Codec, Entry, Holder (+32 more)

### Community 8 - "Curio Stack Operations"
Cohesion: 0.08
Nodes (21): Cache, IAttachmentHolder, CurioInventory, Identifier, ItemStack, LivingEntity, NonNullList, Override (+13 more)

### Community 9 - "Curio Inventory Capability"
Cohesion: 0.12
Nodes (14): CurioInventoryCapability, AttributeModifier, Identifier, IItemHandlerModifiable, ItemStackHandler, ListTag, LivingEntity, Multimap (+6 more)

### Community 10 - "Item Properties"
Cohesion: 0.08
Nodes (18): SoundEvent, ICurio, Attribute, AttributeModifier, Component, CompoundTag, DamageSource, Deprecated (+10 more)

### Community 11 - "Loot Item Conditions"
Cohesion: 0.15
Nodes (19): LootItemCondition, LootItemConditionalFunction, LootItemFunction, MapCodec, NumberProvider, Builder, Attribute, Codec (+11 more)

### Community 12 - "Regalia Slots API Item Handler"
Cohesion: 0.12
Nodes (4): IRegaliaSlotsApiItemHandler, Identifier, ItemStack, Operation

### Community 13 - "JSON Resource Reload"
Cohesion: 0.11
Nodes (17): JsonElement, ProfilerFiller, RegistryAccess, RegistryLookup, ResourceManager, SimpleJsonResourceReloadListener, Builder, Either (+9 more)

### Community 14 - "Block Drops Events"
Cohesion: 0.12
Nodes (18): BlockDropsEvent, EnderManAngerEvent, EntityConstructing, EntityJoinLevelEvent, LivingDropsEvent, LivingEquipmentChangeEvent, PickupXp, RightClickItem (+10 more)

### Community 15 - "Itemized Regalia Slots API Capability"
Cohesion: 0.17
Nodes (8): ItemizedRegaliaSlotsApiCapability, Component, CompoundTag, ItemStack, LootContext, Nonnull, Override, TooltipContext

### Community 16 - "Slot Data"
Cohesion: 0.17
Nodes (4): JsonObject, Override, Provider, SlotData

### Community 17 - "Item Handler Modifiable"
Cohesion: 0.15
Nodes (13): IItemHandlerModifiable, ItemStacksResourceHandler, NullMarked, ItemResource, ItemStack, Override, RegaliaSlotsApiResourceHandler, IDynamicStackHandler (+5 more)

### Community 18 - "Slot Context"
Cohesion: 0.19
Nodes (6): SlotContext, ICurioItem, CompoundTag, ItemStack, LootContext, Nonnull

### Community 19 - "Player Avatar Rendering"
Cohesion: 0.17
Nodes (19): AbstractClientPlayer, AvatarRenderState, EntityModel, HumanoidArm, LivingEntityRenderState, HumanoidRender, Context, Deprecated (+11 more)

### Community 20 - "Regalia Slots API Renderer"
Cohesion: 0.16
Nodes (12): ICurioRenderer, Deprecated, RegaliaSlotsApiRendererRegistry, Item, Internal, RegaliaSlotsApiClientServices, IRegaliaSlotsApiClientExtensions, Internal (+4 more)

### Community 21 - "Slot Type"
Cohesion: 0.16
Nodes (6): ISlotType, EntityType, Identifier, ItemStack, Nullable, Override

### Community 22 - "Entities Data"
Cohesion: 0.20
Nodes (13): EntitiesData, EntitySlotEntry, Entry, Codec, Either, EntityType, ICondition, JsonObject (+5 more)

### Community 23 - "Block Tags Provider"
Cohesion: 0.19
Nodes (12): Block, BlockTagCopyingItemTagProvider, BlockTagsProvider, DataProvider, Override, PackOutput, Provider, TagKey (+4 more)

### Community 24 - "Command Build Context"
Cohesion: 0.31
Nodes (7): CommandBuildContext, CommandDispatcher, ItemInput, CommandSourceStack, Identifier, ServerPlayer, RegaliaSlotsApiCommand

### Community 25 - "Level and Entity Slots"
Cohesion: 0.43
Nodes (4): ItemAccess, ItemStack, Nullable, SlotResult

### Community 26 - "Slot Item Handler"
Cohesion: 0.05
Nodes (28): Accessor, OnPress, SlotItemHandler, ICurioSlotExtension, Component, Deprecated, ItemStack, TooltipFlag (+20 more)

### Community 28 - "Mixin Inventory"
Cohesion: 0.26
Nodes (10): Container, CallbackInfoReturnable, Inject, ItemStack, Mixin, Override, Player, TagKey (+2 more)

### Community 29 - "Container Menu Provider"
Cohesion: 0.18
Nodes (11): AbstractContainerMenu, MenuProvider, Component, Inventory, Nonnull, Nullable, Override, Player (+3 more)

### Community 30 - "Container Screen"
Cohesion: 0.16
Nodes (11): AbstractContainerScreen, Pre, FMLClientSetupEvent, GuiGraphicsExtractor, Override, WidgetSprites, RegaliaSlotsApiButton, Pair (+3 more)

### Community 31 - "Client Configuration"
Cohesion: 0.13
Nodes (12): BooleanValue, ButtonCorner, BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT, Client, Builder (+4 more)

### Community 32 - "Curio Inventory"
Cohesion: 0.13
Nodes (5): IEntitiesData, Deprecated, Internal, JsonObject, Provider

### Community 33 - "Regalia Slots API Screen"
Cohesion: 0.22
Nodes (4): ItemSlotMouseAction, GuiGraphicsExtractor, Override, RegaliaSlotsApiScreen

### Community 34 - "Page Button"
Cohesion: 0.18
Nodes (13): Button, GuiGraphicsExtractor, Identifier, Override, PageButton, Type, NEXT, PREVIOUS (+5 more)

### Community 35 - "Dynamic Operations"
Cohesion: 0.24
Nodes (10): DynamicOps, ProblemReporter, CompoundTag, Entity, ListTag, Pair, Schema, Tag (+2 more)

### Community 36 - "Amulet Item"
Cohesion: 0.22
Nodes (10): AmuletItem, Context, Identifier, ItemStack, LootContext, Nonnull, Override, PoseStack (+2 more)

### Community 37 - "Regalia Slots API Client Mod"
Cohesion: 0.17
Nodes (10): AddLayers, RegisterKeyMappingsEvent, RegisterMenuScreensEvent, RegisterRenderStateModifiersEvent, ContextKey, IEventBus, Mod, ModContainer (+2 more)

### Community 38 - "Regalia Slots API Test Registry"
Cohesion: 0.15
Nodes (9): DeferredItem, CrownItem, ItemStack, Override, ItemStack, Override, KnucklesItem, IEventBus (+1 more)

### Community 39 - "Equipment Slot Group"
Cohesion: 0.17
Nodes (15): EquipmentSlotGroup, Level, Attribute, AttributeModifier, Deprecated, EntityType, Holder, Identifier (+7 more)

### Community 40 - "Payload Handler"
Cohesion: 0.18
Nodes (9): PayloadRegistrar, IPayloadContext, RegaliaSlotsApiClientPayloadHandler, NetworkHandler, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec (+1 more)

### Community 41 - "Regalia Slots API Slot Types"
Cohesion: 0.30
Nodes (5): EntityType, Identifier, ItemStack, LivingEntity, RegaliaSlotsApiSlotTypes

### Community 42 - "Recipe Book Screen"
Cohesion: 0.15
Nodes (12): AbstractRecipeBookScreen, EffectsInInventory, KeyEvent, MouseButtonEvent, RecipeUpdateListener, ScreenPosition, IRegaliaSlotsApiScreen, Component (+4 more)

### Community 43 - "Regalia Slots API Extensions"
Cohesion: 0.29
Nodes (5): ItemStack, LivingEntity, LootContext, Player, RegaliaSlotsApiCommonMixinHooks

### Community 44 - "Curio Argument Type"
Cohesion: 0.23
Nodes (9): ArgumentType, CommandContext, DynamicCommandExceptionType, CurioArgumentType, CommandSourceStack, Override, StringReader, Suggestions (+1 more)

### Community 45 - "Custom Packet Payloads"
Cohesion: 0.21
Nodes (7): Entry, IEntitySlotEntry, Either, EntityType, ICondition, ResourceKey, TagKey

### Community 46 - "Entities Data"
Cohesion: 0.33
Nodes (6): EntityType, Identifier, ItemStack, LivingEntity, Override, RegaliaSlotsApiSlots

### Community 47 - "Regalia Slots API Tooltip"
Cohesion: 0.30
Nodes (6): IRegaliaSlotsApiSlots, EntityType, Identifier, Internal, ItemStack, LivingEntity

### Community 48 - "IEntitiesData Entry"
Cohesion: 0.33
Nodes (3): IRegaliaSlotsApiNetwork, Override, RegaliaSlotsApiNetwork

### Community 49 - "Attribute Tooltip Events"
Cohesion: 0.06
Nodes (36): AddAttributeTooltipsEvent, Category, IGuiHandlerRegistration, IModPlugin, ItemTooltipEvent, JeiPlugin, KeyMapping, RenderArmEvent (+28 more)

### Community 50 - "Damage and Drop Rules"
Cohesion: 0.26
Nodes (6): ImmutableList, DropRulesEvent, DamageSource, ItemStack, LivingEntity, Pair

### Community 51 - "Curio Attributes"
Cohesion: 0.23
Nodes (9): Attribute, AttributeModifier, Component, DamageSource, EnderMan, Holder, Identifier, Multimap (+1 more)

### Community 52 - "Regalia Slot Data"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Regalia Slots API (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 54 - "Item Stack Sync"
Cohesion: 0.23
Nodes (11): fromValue(), HandlerType, COSMETIC, EQUIPMENT, CompoundTag, ItemStack, Nonnull, Override (+3 more)

### Community 56 - "Data Generation"
Cohesion: 0.31
Nodes (4): CachedOutput, PathProvider, Nonnull, RegaliaSlotsApiDataProvider

### Community 57 - "Curio Drops"
Cohesion: 0.29
Nodes (5): ICancellableEvent, CurioDropsEvent, DamageSource, ItemEntity, LivingEntity

### Community 58 - "Drop Rules"
Cohesion: 0.21
Nodes (8): DropRule, ALWAYS_DROP, ALWAYS_KEEP, DEFAULT, DESTROY, Builder, EntityType, Identifier

### Community 59 - "Regalia Slot API"
Cohesion: 0.24
Nodes (4): NonNullList, ItemStack, LootContext, Post

### Community 60 - "Preset Slots"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketQuickMove

### Community 61 - "Regalia GUI"
Cohesion: 0.21
Nodes (7): IRegaliaSlotsApiMenu, RegaliaSlotsApiClientPackets, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketBreak

### Community 62 - "Slot Data Entry"
Cohesion: 0.42
Nodes (7): Entry, Either, EntityType, ICondition, Identifier, ResourceKey, TagKey

### Community 63 - "Mixin Injection"
Cohesion: 0.35
Nodes (8): CallbackInfo, ModifyArg, Inject, Mixin, Pair, Schema, TypeTemplate, MixinV1460

### Community 64 - "GUI Handler"
Cohesion: 0.27
Nodes (7): IGuiContainerHandler, NonNull, Override, Rect2i, RegaliaSlotsApiContainerHandler, Rect2i, RegaliaSlotsApiExclusionAreas

### Community 65 - "Network Services"
Cohesion: 0.40
Nodes (3): ItemStack, Override, RingItem

### Community 66 - "Mod Plugin"
Cohesion: 0.50
Nodes (4): getSerializedName(), Nonnull, Override, StringRepresentable

### Community 67 - "Cosmetic Buttons"
Cohesion: 0.31
Nodes (6): ImageButton, CosmeticButton, GuiGraphicsExtractor, Override, WidgetSprites, IRegaliaSlotsApiWidget

### Community 69 - "Render Layers"
Cohesion: 0.26
Nodes (8): RenderLayer, Nonnull, Context, Override, PoseStack, RenderLayerParent, SubmitNodeCollector, RegaliaSlotsApiLayer

### Community 70 - "Packet Destroy"
Cohesion: 0.29
Nodes (7): CPacketDestroy, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, Logger, RegaliaSlotsApiConstants

### Community 71 - "Modifiers Sync"
Cohesion: 0.31
Nodes (7): CompoundTag, FriendlyByteBuf, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncModifiers

### Community 72 - "Regalia Slots Sync"
Cohesion: 0.31
Nodes (7): CompoundTag, FriendlyByteBuf, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncRegaliaSlotsApi

### Community 73 - "Entity Selector"
Cohesion: 0.33
Nodes (8): CallbackInfoReturnable, CompoundTag, Entity, Inject, Mixin, ScopedCollector, TagValueOutput, MixinEntitySelectorOptions

### Community 74 - "NBT Predicate"
Cohesion: 0.33
Nodes (8): CallbackInfoReturnable, CompoundTag, Entity, Inject, Mixin, ScopedCollector, TagValueOutput, MixinNbtPredicate

### Community 75 - "Item Capabilities"
Cohesion: 0.33
Nodes (7): Attribute, AttributeModifier, DamageSource, EnderMan, Holder, Identifier, Multimap

### Community 76 - "Data Sync"
Cohesion: 0.33
Nodes (6): Nonnull, Override, RegistryFriendlyByteBuf, ServerPlayer, StreamCodec, SPacketSyncData

### Community 77 - "Bonus Count"
Cohesion: 0.39
Nodes (7): Enchantment, Holder, ItemStack, LootContext, Mixin, ModifyVariable, MixinApplyBonusCount

### Community 78 - "Enchanted Count"
Cohesion: 0.39
Nodes (7): Enchantment, Holder, ItemStack, LootContext, Mixin, ModifyVariable, MixinEnchantedCountIncreaseFunction

### Community 79 - "Test Armor"
Cohesion: 0.33
Nodes (6): Identifier, ItemAttributeModifiers, ItemStack, Nonnull, Override, TestArmor

### Community 80 - "Regalia Slots Open"
Cohesion: 0.39
Nodes (6): CPacketOpenRegaliaSlotsApi, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec

### Community 81 - "Vanilla Open"
Cohesion: 0.22
Nodes (12): CustomPacketPayload, CPacketOpenVanilla, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, CPacketToggleRender (+4 more)

### Community 82 - "Item Grabbed"
Cohesion: 0.39
Nodes (6): ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketGrabbedItem

### Community 83 - "Living Entity"
Cohesion: 0.43
Nodes (5): CallbackInfoReturnable, Inject, Mixin, SuppressWarnings, MixinLivingEntity

### Community 84 - "Entity Capabilities"
Cohesion: 0.52
Nodes (6): EntityCapability, ItemCapability, ResourceHandler, Identifier, ItemResource, RegaliaSlotsApiCapability

### Community 85 - "Mod Event"
Cohesion: 0.36
Nodes (5): CPacketToggleCosmetics, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec

### Community 87 - "Curio Slot Extension"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncActiveState

### Community 88 - "Page Packet"
Cohesion: 0.25
Nodes (7): [0.0.0-beta.1] - 2026-08-19, [0.0.0-beta.2] - 2026-08-19, [0.0.0-beta.3] - 2026-08-19, Añadido, Cambiado, Changelog — Regalia Slots API, Corregido

### Community 89 - "Quick Move Packet"
Cohesion: 0.33
Nodes (5): Adding to Your Project, Credits & License, Features, Overview, Regalia Slots API

### Community 90 - "Sync Render Packet"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncRender

### Community 91 - "Slot Type"
Cohesion: 0.50
Nodes (3): CLAUDE.md — regalia_slots_api (26.2), Prioridad de instrucciones, Workflow del mod

### Community 92 - "Piglin AI"
Cohesion: 0.48
Nodes (5): CallbackInfoReturnable, Inject, LivingEntity, Mixin, MixinPiglinAi

### Community 93 - "Powder Snow Block"
Cohesion: 0.48
Nodes (5): CallbackInfoReturnable, Entity, Inject, Mixin, MixinPowderSnowBlock

### Community 94 - "Test Provider"
Cohesion: 0.38
Nodes (4): Override, PackOutput, Provider, RegaliaSlotsApiTestProvider

### Community 98 - "Slot Context"
Cohesion: 0.50
Nodes (3): ItemAccess, LivingEntity, Nullable

### Community 99 - "AttributeModifier"
Cohesion: 0.15
Nodes (6): AttributeModifier, IItemHandlerModifiable, ListTag, LivingEntity, LootContext, Multimap

### Community 101 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 103 - "Serialization Utilities"
Cohesion: 0.21
Nodes (8): Deprecated, Either, EntityType, ICondition, JsonObject, Provider, ResourceKey, TagKey

## Knowledge Gaps
- **92 isolated node(s):** `BACK`, `BELT`, `BODY`, `BRACELET`, `CHARM` (+87 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **51 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SlotContext` connect `Slot Context` to `Regalia Slots API Common Hooks`, `Item Stack Handler`, `Humanoid Model Layers`, `Advancement Sub Providers`, `Curio Stack Operations`, `Item Properties`, `Block Drops Events`, `Itemized Regalia Slots API Capability`, `Player Avatar Rendering`, `Slot Type`, `Level and Entity Slots`, `Slot Item Handler`, `Amulet Item`, `Regalia Slots API Client Mod`, `Equipment Slot Group`, `Regalia Slots API Slot Types`, `Regalia Slots API Extensions`, `Entities Data`, `Regalia Slots API Tooltip`, `IEntitiesData Entry`, `Attribute Tooltip Events`, `Curio Attributes`, `Regalia Slot API`, `Regalia GUI`, `Item Capabilities`, `Slot Context`?**
  _High betweenness centrality (0.211) - this node is a cross-community bridge._
- **Why does `ISlotType` connect `Slot Type` to `Regalia Slots API Common Hooks`, `AttributeModifier`, `Drop Rules`, `Equipment Slot Group`, `Attribute Handling`, `Regalia Slots API Slot Types`, `Item Properties`, `Loot Item Conditions`, `Curio Stack Operations`, `JSON Resource Reload`, `Block Drops Events`, `Regalia Slots API Tooltip`, `Entities Data`, `Attribute Tooltip Events`, `Curio Argument Type`, `Curio Attributes`, `Slot Overrides`, `Slot Item Handler`?**
  _High betweenness centrality (0.104) - this node is a cross-community bridge._
- **Why does `Item` connect `Regalia Slots API Renderer` to `Item Stack Handler`, `Server Reload Listeners`, `Curio Inventory Capability`, `Item Properties`, `Regalia Slots API Item Handler`, `Player Avatar Rendering`, `Block Tags Provider`, `Slot Item Handler`, `Mixin Inventory`, `Dynamic Operations`, `Amulet Item`, `Regalia Slots API Test Registry`, `Equipment Slot Group`, `Regalia Slots API Extensions`, `Entities Data`, `Attribute Tooltip Events`, `Curio Attributes`, `Regalia Slot API`, `Slot Data Entry`, `Network Services`, `Render Layers`, `Item Capabilities`, `Test Armor`, `AttributeModifier`?**
  _High betweenness centrality (0.083) - this node is a cross-community bridge._
- **Are the 22 inferred relationships involving `SlotContext` (e.g. with `.onAttributeTooltip()` and `.renderHand()`) actually correct?**
  _`SlotContext` has 22 INFERRED edges - model-reasoned connections that need verification._
- **What connects `BACK`, `BELT`, `BODY` to the rest of the system?**
  _92 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Regalia Slots API Common Hooks` be split into smaller, more focused modules?**
  _Cohesion score 0.0516404581634634 - nodes in this community are weakly interconnected._
- **Should `Item Stack Handler` be split into smaller, more focused modules?**
  _Cohesion score 0.05961538461538462 - nodes in this community are weakly interconnected._