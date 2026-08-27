# Graph Report - 26.2  (2026-08-27)

## Corpus Check
- 262 files · ~198,813 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 3180 nodes · 7324 edges · 229 communities (169 shown, 60 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 121 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `059bf99f`
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
- ICurioSlotExtension
- Curio Slot Extension
- Page Packet
- Quick Move Packet
- Sync Render Packet
- Slot Type
- Piglin AI
- Powder Snow Block
- Test Provider
- SlotContext
- Entry
- Permanent Slot Modifiers
- Slot Context
- AttributeModifier
- Curio Slot Interface
- Gradle Build Script
- Tag Operations
- Serialization Utilities
- DynamicStackHandlerAdapter
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
- ISlotType
- DropRule
- IEntitiesData.java
- ISlotData
- IDynamicStackHandler
- CurioChangeEvent
- SlotTypePredicate
- Override
- CuriosServices.java
- SlotTypePredicate
- CuriosCodecsAdapter.java
- SlotTypeAdapter
- ICurioSlotExtension
- RegaliaSlotsApiTest.java
- KnucklesModel
- CuriosSlotTypes.java
- CuriosTooltip
- ICuriosCodecs
- KnucklesRenderer.java
- IEntitiesData
- .matches
- .resource
- ICuriosSlots
- ICurioItem.java
- CurioCanUnequipEvent
- Preset
- Preset
- CurioCanEquipEvent
- CurioCanUnequipEvent
- RegaliaSlotsApiResourceHandler.java
- CuriosCompatMod.java
- CuriosExtensionsAdapter
- CurioDropsEvent
- Builder
- RegisterRegaliaSlotsApiExtensionsEvent
- RegaliaSlotsApiJeiPlugin.java
- RegaliaSlotsApiDataComponents.java
- .addPermanentSlotModifier
- EntitiesData.java
- .getSlotType
- CuriosResourceHandler
- CuriosDataComponents.java
- .CurioAttributeModifierEvent
- .readTag
- .build
- ICuriosExtensions
- SlotResult
- CuriosTriggers.java
- RegaliaSlotsApiTags.java
- CuriosCapability.java
- SPacketQuickMove
- .getFortuneLevel
- .loadInventory
- SPacketSyncActiveState
- SlotContext.java
- RegaliaSlotsApiKeyMappings.java
- .getFortuneLevel
- HandlerType
- CuriosCommonHooks.java
- .getAttributesTooltip
- .getAttributeModifierComponent
- .serialize
- ICuriosMenu
- ICuriosScreen.java
- .breakCurioInSlot
- RegaliaSlotsApiTestIds.java
- Regalia Slots API 1.1.1
- .CurioAttributeModifierEvent
- .serialize
- .apply

## God Nodes (most connected - your core abstractions)
1. `SlotContext` - 164 edges
2. `SlotContext` - 114 edges
3. `ICurioStacksHandler` - 81 edges
4. `ISlotType` - 76 edges
5. `IRegaliaSlotsApiItemHandler` - 62 edges
6. `ICuriosItemHandler` - 52 edges
7. `ISlotData` - 49 edges
8. `CurioStacksHandler` - 48 edges
9. `ISlotType` - 48 edges
10. `ICurioItem` - 45 edges

## Surprising Connections (you probably didn't know these)
- `CurioAttributeModifierEvent` --references--> `CurioAttributeModifiers`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/event/CurioAttributeModifierEvent.java → src/main/java/com/skd/regaliaslotsapi/api/CurioAttributeModifiers.java
- `RegaliaSlotsApiDataComponents` --references--> `CurioAttributeModifiers`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiDataComponents.java → src/main/java/com/skd/regaliaslotsapi/api/CurioAttributeModifiers.java
- `Entry` --references--> `SlotTypePredicate`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/CurioAttributeModifiers.java → src/main/java/com/skd/regaliaslotsapi/api/common/slot/SlotTypePredicate.java
- `RegaliaSlotsApiCapability` --references--> `ICurio`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiCapability.java → src/main/java/com/skd/regaliaslotsapi/api/type/capability/ICurio.java
- `RegaliaSlotsApiCapability` --references--> `IRegaliaSlotsApiItemHandler`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiCapability.java → src/main/java/com/skd/regaliaslotsapi/api/type/capability/IRegaliaSlotsApiItemHandler.java

## Import Cycles
- None detected.

## Communities (229 total, 60 thin omitted)

### Community 0 - "Regalia Slots API Common Hooks"
Cohesion: 0.20
Nodes (12): CurioAttributeModifierEvent, CurioAttributeModifiersBuilder, Attribute, AttributeModifier, Deprecated, Entry, Holder, Identifier (+4 more)

### Community 1 - "Item Stack Handler"
Cohesion: 0.24
Nodes (8): ItemStackHandler, DynamicStackHandler, CompoundTag, ItemStack, Nonnull, NonNullList, Override, Provider

### Community 2 - "Humanoid Model Layers"
Cohesion: 0.23
Nodes (9): CrownModel, LayerDefinition, ModelPart, CrownRenderer, HumanoidModel, HumanoidRenderState, Identifier, ItemStack (+1 more)

### Community 3 - "Server Reload Listeners"
Cohesion: 0.06
Nodes (33): AddServerReloadListenersEvent, CombinedResourceHandler, ConfigValue, Event, FMLCommonSetupEvent, IModBusEvent, RegisterCommandsEvent, RegisterPayloadHandlersEvent (+25 more)

### Community 4 - "Advancement Sub Providers"
Cohesion: 0.07
Nodes (34): AdvancementHolder, AdvancementSubProvider, ContextAwarePredicate, ItemPredicate, Items, LocationPredicate, SimpleCriterionTrigger, SimpleInstance (+26 more)

### Community 5 - "Crafting Menu"
Cohesion: 0.14
Nodes (14): AbstractCraftingMenu, CraftingContainer, RecipeBookType, ResultContainer, Container, FriendlyByteBuf, Inventory, ItemStack (+6 more)

### Community 6 - "Curio Stack Handler"
Cohesion: 0.08
Nodes (7): ICurioStacksHandler, AttributeModifier, CompoundTag, Deprecated, Identifier, NonNullList, Operation

### Community 7 - "Attribute Handling"
Cohesion: 0.14
Nodes (16): IRegaliaSlotsApiCodecs, Attribute, Codec, Entry, Holder, Internal, RegistryFriendlyByteBuf, StreamCodec (+8 more)

### Community 8 - "Curio Stack Operations"
Cohesion: 0.07
Nodes (25): Cache, IAttachmentHolder, CurioInventory, Identifier, ItemStack, LivingEntity, NonNullList, Override (+17 more)

### Community 9 - "Curio Inventory Capability"
Cohesion: 0.10
Nodes (18): CurioInventoryCapability, AttributeModifier, Identifier, IItemHandlerModifiable, Item, ItemStack, ItemStackHandler, ListTag (+10 more)

### Community 10 - "Item Properties"
Cohesion: 0.08
Nodes (19): ICurio, Attribute, AttributeModifier, Component, CompoundTag, DamageSource, Deprecated, EnderMan (+11 more)

### Community 11 - "Loot Item Conditions"
Cohesion: 0.12
Nodes (18): Attribute, AttributeModifier, Component, CompoundTag, DamageSource, Deprecated, EnderMan, Holder (+10 more)

### Community 12 - "Regalia Slots API Item Handler"
Cohesion: 0.50
Nodes (3): ItemAccess, ItemStack, Nullable

### Community 13 - "JSON Resource Reload"
Cohesion: 0.12
Nodes (17): JsonElement, ProfilerFiller, RegistryAccess, RegistryLookup, ResourceManager, SimpleJsonResourceReloadListener, Builder, Either (+9 more)

### Community 14 - "Block Drops Events"
Cohesion: 0.12
Nodes (19): BlockDropsEvent, EnderManAngerEvent, EntityConstructing, EntityJoinLevelEvent, LivingDropsEvent, LivingEquipmentChangeEvent, PickupXp, RightClickItem (+11 more)

### Community 15 - "Itemized Regalia Slots API Capability"
Cohesion: 0.13
Nodes (16): ItemizedRegaliaSlotsApiCapability, Attribute, AttributeModifier, Component, CompoundTag, DamageSource, EnderMan, Holder (+8 more)

### Community 16 - "Slot Data"
Cohesion: 0.10
Nodes (12): ISlotData, Entry, Either, EntityType, ICondition, Identifier, JsonObject, Override (+4 more)

### Community 17 - "Item Handler Modifiable"
Cohesion: 0.15
Nodes (11): DropRule, ALWAYS_DROP, ALWAYS_KEEP, DEFAULT, DESTROY, DropRulesEvent, DamageSource, ImmutableList (+3 more)

### Community 18 - "Slot Context"
Cohesion: 0.13
Nodes (15): SlotContext, ICurioItem, Attribute, AttributeModifier, Component, CompoundTag, DamageSource, EnderMan (+7 more)

### Community 19 - "Player Avatar Rendering"
Cohesion: 0.07
Nodes (40): RenderLayer, HumanoidRender, ICurioRenderer, AbstractClientPlayer, AvatarRenderState, Context, Deprecated, EntityModel (+32 more)

### Community 20 - "Regalia Slots API Renderer"
Cohesion: 0.22
Nodes (9): CurioChangeEvent, Item, Deprecated, Internal, ItemStack, LivingEntity, Nonnull, SlotContext (+1 more)

### Community 21 - "Slot Type"
Cohesion: 0.11
Nodes (7): ISlotType, Deprecated, EntityType, Identifier, ItemStack, Nullable, Override

### Community 22 - "Entities Data"
Cohesion: 0.14
Nodes (15): IEntitiesData, EntitiesData, EntitySlotEntry, Entry, Codec, Either, EntityType, ICondition (+7 more)

### Community 23 - "Block Tags Provider"
Cohesion: 0.13
Nodes (14): Block, CachedOutput, Item, Nonnull, Override, PackOutput, PathProvider, Provider (+6 more)

### Community 24 - "Command Build Context"
Cohesion: 0.24
Nodes (8): CommandBuildContext, CommandDispatcher, ItemInput, OnDatapackSyncEvent, CommandSourceStack, Identifier, ServerPlayer, RegaliaSlotsApiCommand

### Community 25 - "Level and Entity Slots"
Cohesion: 0.14
Nodes (7): IDynamicStackHandler, CompoundTag, Deprecated, ItemStack, Nonnull, Provider, ValueIOSerializable

### Community 26 - "Slot Item Handler"
Cohesion: 0.17
Nodes (9): SlotItemHandler, CurioSlot, Component, Deprecated, ItemStack, NonNullList, Override, Player (+1 more)

### Community 27 - "ISlotData Entry"
Cohesion: 0.13
Nodes (7): Entry, Either, EntityType, ICondition, Identifier, ResourceKey, TagKey

### Community 28 - "Mixin Inventory"
Cohesion: 0.24
Nodes (11): Container, CallbackInfoReturnable, Inject, Item, ItemStack, Mixin, Override, Player (+3 more)

### Community 29 - "Container Menu Provider"
Cohesion: 0.17
Nodes (11): AbstractContainerMenu, MenuProvider, Component, Inventory, Nonnull, Nullable, Override, Player (+3 more)

### Community 30 - "Container Screen"
Cohesion: 0.19
Nodes (10): AbstractContainerScreen, Pre, GuiGraphicsExtractor, Override, WidgetSprites, RegaliaSlotsApiButton, Pair, Post (+2 more)

### Community 31 - "Client Configuration"
Cohesion: 0.12
Nodes (12): BooleanValue, ButtonCorner, BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT, Client, Builder (+4 more)

### Community 32 - "Curio Inventory"
Cohesion: 0.09
Nodes (30): CuriosRendererRegistry, Deprecated, Item, HumanoidRender, ICurioRenderer, AbstractClientPlayer, AvatarRenderState, Context (+22 more)

### Community 33 - "Regalia Slots API Screen"
Cohesion: 0.19
Nodes (6): ItemSlotMouseAction, KeyEvent, MouseButtonEvent, GuiGraphicsExtractor, Override, RegaliaSlotsApiScreen

### Community 34 - "Page Button"
Cohesion: 0.18
Nodes (13): Button, GuiGraphicsExtractor, Identifier, Override, PageButton, Type, NEXT, PREVIOUS (+5 more)

### Community 35 - "Dynamic Operations"
Cohesion: 0.14
Nodes (16): DynamicOps, ProblemReporter, CompoundTag, Entity, Item, ItemStack, ListTag, LivingEntity (+8 more)

### Community 36 - "Amulet Item"
Cohesion: 0.20
Nodes (11): AmuletItem, Context, Identifier, ItemStack, LootContext, Nonnull, Override, PoseStack (+3 more)

### Community 37 - "Regalia Slots API Client Mod"
Cohesion: 0.14
Nodes (12): AddLayers, RegisterKeyMappingsEvent, RegisterMenuScreensEvent, RegisterRenderStateModifiersEvent, ContextKey, FMLClientSetupEvent, IEventBus, Mod (+4 more)

### Community 38 - "Regalia Slots API Test Registry"
Cohesion: 0.09
Nodes (20): DeferredItem, Item, CrownItem, ItemStack, Override, ItemStack, Override, KnucklesItem (+12 more)

### Community 39 - "Equipment Slot Group"
Cohesion: 0.15
Nodes (18): Attribute, AttributeModifier, Deprecated, EntityType, EquipmentSlotGroup, Holder, Identifier, Item (+10 more)

### Community 40 - "Payload Handler"
Cohesion: 0.18
Nodes (9): PayloadRegistrar, IPayloadContext, RegaliaSlotsApiClientPayloadHandler, NetworkHandler, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec (+1 more)

### Community 41 - "Regalia Slots API Slot Types"
Cohesion: 0.27
Nodes (5): EntityType, Identifier, ItemStack, LivingEntity, RegaliaSlotsApiSlotTypes

### Community 42 - "Recipe Book Screen"
Cohesion: 0.27
Nodes (9): Entry, Attribute, AttributeModifier, Codec, Deprecated, Holder, Identifier, RegistryFriendlyByteBuf (+1 more)

### Community 43 - "Regalia Slots API Extensions"
Cohesion: 0.10
Nodes (9): ICuriosItemHandler, AttributeModifier, Identifier, IItemHandlerModifiable, ListTag, LivingEntity, LootContext, Multimap (+1 more)

### Community 44 - "Curio Argument Type"
Cohesion: 0.22
Nodes (10): EntityType, Identifier, IEntitiesData, ISlotData, ISlotType, ItemStack, LivingEntity, Override (+2 more)

### Community 45 - "Custom Packet Payloads"
Cohesion: 0.11
Nodes (12): Entry, IEntitySlotEntry, Deprecated, Either, EntityType, ICondition, Internal, JsonObject (+4 more)

### Community 46 - "Entities Data"
Cohesion: 0.30
Nodes (7): EntityType, Identifier, ItemStack, LivingEntity, Override, SlotContext, RegaliaSlotsApiSlots

### Community 47 - "Regalia Slots API Tooltip"
Cohesion: 0.27
Nodes (6): IRegaliaSlotsApiSlots, EntityType, Identifier, Internal, ItemStack, LivingEntity

### Community 48 - "IEntitiesData Entry"
Cohesion: 0.33
Nodes (3): IRegaliaSlotsApiNetwork, Override, RegaliaSlotsApiNetwork

### Community 49 - "Attribute Tooltip Events"
Cohesion: 0.26
Nodes (8): AddAttributeTooltipsEvent, ItemTooltipEvent, RenderArmEvent, ItemStack, Player, Post, SubscribeEvent, RegaliaSlotsApiClientEvents

### Community 50 - "Damage and Drop Rules"
Cohesion: 0.26
Nodes (6): DropRulesEvent, DamageSource, ImmutableList, ItemStack, LivingEntity, Pair

### Community 51 - "Curio Attributes"
Cohesion: 0.08
Nodes (27): Attribute, AttributeModifier, Codec, Deprecated, Holder, Identifier, MutableComponent, Nonnull (+19 more)

### Community 52 - "Regalia Slot Data"
Cohesion: 0.13
Nodes (14): Buenas prácticas, Capa de compatibilidad Curios API (introducida en v0.0.0-beta.4, estable desde v1.0.0), Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Regalia Slots API (NeoForge), Flujo por tarea (+6 more)

### Community 53 - "Regalia Resources"
Cohesion: 0.31
Nodes (6): Category, KeyMapping, Identifier, Logger, RegaliaSlotsApiResources, RegaliaSlotsApiKeyMappings

### Community 54 - "Item Stack Sync"
Cohesion: 0.36
Nodes (7): CompoundTag, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncStack

### Community 55 - "Slot Overrides"
Cohesion: 0.29
Nodes (3): Identifier, Override, SlotType

### Community 56 - "Data Generation"
Cohesion: 0.11
Nodes (18): SlotContext, ICurio, Attribute, AttributeModifier, CompoundTag, DamageSource, Deprecated, EnderMan (+10 more)

### Community 57 - "Curio Drops"
Cohesion: 0.31
Nodes (4): CurioDropsEvent, DamageSource, ItemEntity, LivingEntity

### Community 58 - "Drop Rules"
Cohesion: 0.11
Nodes (10): AttributeModifier, CompoundTag, DropRule, Identifier, NonNullList, Operation, Override, ValueInput (+2 more)

### Community 59 - "Regalia Slot API"
Cohesion: 0.19
Nodes (6): Builder, Codec, Ints, RegistryFriendlyByteBuf, StreamCodec, SlotTypePredicate

### Community 60 - "Preset Slots"
Cohesion: 0.31
Nodes (8): Attribute, Codec, Entry, Holder, Override, RegistryFriendlyByteBuf, StreamCodec, RegaliaCodecsAdapter

### Community 61 - "Regalia GUI"
Cohesion: 0.23
Nodes (9): ArgumentType, CommandContext, DynamicCommandExceptionType, CurioArgumentType, CommandSourceStack, Override, StringReader, Suggestions (+1 more)

### Community 62 - "Slot Data Entry"
Cohesion: 0.20
Nodes (12): CurioAttributeModifierEvent, CurioAttributeModifiersBuilder, Attribute, AttributeModifier, Deprecated, Entry, Holder, Identifier (+4 more)

### Community 63 - "Mixin Injection"
Cohesion: 0.35
Nodes (8): CallbackInfo, ModifyArg, Inject, Mixin, Pair, Schema, TypeTemplate, MixinV1460

### Community 65 - "Network Services"
Cohesion: 0.16
Nodes (15): CuriosApi, Attribute, AttributeModifier, Deprecated, EntityType, EquipmentSlotGroup, Holder, Item (+7 more)

### Community 66 - "Mod Plugin"
Cohesion: 0.36
Nodes (6): CuriosTooltip, Component, Deprecated, ItemStack, LivingEntity, MutableComponent

### Community 67 - "Cosmetic Buttons"
Cohesion: 0.27
Nodes (6): ImageButton, CosmeticButton, GuiGraphicsExtractor, Override, WidgetSprites, IRegaliaSlotsApiWidget

### Community 69 - "Render Layers"
Cohesion: 0.08
Nodes (7): ICurioStacksHandler, AttributeModifier, CompoundTag, Deprecated, Identifier, NonNullList, Operation

### Community 70 - "Packet Destroy"
Cohesion: 0.26
Nodes (8): ICuriosCodecs, Attribute, Codec, Entry, Holder, Internal, RegistryFriendlyByteBuf, StreamCodec

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
Cohesion: 0.07
Nodes (37): LootItemConditionalFunction, Builder, Attribute, Codec, ContextKey, Holder, Identifier, ItemStack (+29 more)

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
Cohesion: 0.14
Nodes (5): IEntitiesData, Deprecated, Internal, JsonObject, Provider

### Community 80 - "Regalia Slots Open"
Cohesion: 0.39
Nodes (6): CPacketOpenRegaliaSlotsApi, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec

### Community 81 - "Vanilla Open"
Cohesion: 0.39
Nodes (6): CPacketOpenVanilla, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec

### Community 82 - "Item Grabbed"
Cohesion: 0.39
Nodes (6): ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketGrabbedItem

### Community 83 - "Living Entity"
Cohesion: 0.43
Nodes (5): CallbackInfoReturnable, Inject, Mixin, SuppressWarnings, MixinLivingEntity

### Community 84 - "Entity Capabilities"
Cohesion: 0.13
Nodes (15): ArgumentTypeInfo, CriterionTrigger, MenuType, ItemStack, RegaliaSlotsApiCommonHooks, CurioAttributeModifiers, CurioAttributeModifiers, IRegaliaSlotsApiRegistry (+7 more)

### Community 85 - "Mod Event"
Cohesion: 0.24
Nodes (10): CurioAttributeModifiers, Entry, Attribute, AttributeModifier, Codec, Deprecated, Holder, Identifier (+2 more)

### Community 86 - "ICurioSlotExtension"
Cohesion: 0.17
Nodes (7): DropRule, ALWAYS_DROP, ALWAYS_KEEP, DEFAULT, DESTROY, Builder, EntityType

### Community 87 - "Curio Slot Extension"
Cohesion: 0.18
Nodes (6): ICurioSlotExtension, Component, Deprecated, ItemStack, TooltipFlag, ICurioSlot

### Community 88 - "Page Packet"
Cohesion: 0.11
Nodes (17): [0.0.0-beta.1] - 2026-08-19, [0.0.0-beta.2] - 2026-08-19, [0.0.0-beta.3] - 2026-08-19, [0.0.0-beta.4] - 2026-08-19, [1.0.0] - 2026-08-20, [1.1.0] - 2026-08-20, [1.1.1] - 2026-08-27, Añadido (+9 more)

### Community 89 - "Quick Move Packet"
Cohesion: 0.33
Nodes (5): Adding to Your Project, Credits & License, Features, Overview, Regalia Slots API

### Community 90 - "Sync Render Packet"
Cohesion: 0.36
Nodes (6): Component, Deprecated, ItemStack, LivingEntity, MutableComponent, RegaliaSlotsApiTooltip

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

### Community 95 - "SlotContext"
Cohesion: 0.11
Nodes (14): ICurioItem, Attribute, AttributeModifier, Component, CompoundTag, DamageSource, EnderMan, Holder (+6 more)

### Community 96 - "Entry"
Cohesion: 0.12
Nodes (7): Entry, Either, EntityType, ICondition, Identifier, ResourceKey, TagKey

### Community 98 - "Slot Context"
Cohesion: 0.50
Nodes (3): ItemAccess, LivingEntity, Nullable

### Community 99 - "AttributeModifier"
Cohesion: 0.30
Nodes (4): AttributeModifier, Multimap, AttributeModifier, Multimap

### Community 100 - "Curio Slot Interface"
Cohesion: 0.10
Nodes (9): Item, ItemStack, LivingEntity, Nullable, Override, Tag, ValueInput, ValueOutput (+1 more)

### Community 101 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 102 - "Tag Operations"
Cohesion: 0.18
Nodes (10): AbstractRecipeBookScreen, EffectsInInventory, RecipeUpdateListener, ScreenPosition, IRegaliaSlotsApiScreen, Component, Identifier, Inventory (+2 more)

### Community 103 - "Serialization Utilities"
Cohesion: 0.19
Nodes (13): BlockTagCopyingItemTagProvider, BlockTagsProvider, CuriosBlockTagsProvider, CuriosItemTagsProvider, Block, Item, Nonnull, Override (+5 more)

### Community 104 - "DynamicStackHandlerAdapter"
Cohesion: 0.19
Nodes (8): DynamicStackHandlerAdapter, CompoundTag, ItemStack, Nonnull, Override, Provider, ValueInput, ValueOutput

### Community 105 - "Internal Build Methods"
Cohesion: 0.23
Nodes (5): Internal, RegaliaSlotsApiServices, IRegaliaSlotsApiExtensions, Item, Nullable

### Community 156 - "ISlotType"
Cohesion: 0.10
Nodes (12): CuriosSlotTypes, EntityType, Identifier, ItemStack, LivingEntity, ISlotType, Deprecated, EntityType (+4 more)

### Community 157 - "DropRule"
Cohesion: 0.28
Nodes (7): getSerializedName(), Nonnull, Override, getSerializedName(), Nonnull, Override, StringRepresentable

### Community 158 - "IEntitiesData.java"
Cohesion: 0.21
Nodes (7): Entry, IEntitySlotEntry, Either, EntityType, ICondition, ResourceKey, TagKey

### Community 159 - "ISlotData"
Cohesion: 0.11
Nodes (5): ISlotData, Deprecated, Internal, JsonObject, Provider

### Community 160 - "IDynamicStackHandler"
Cohesion: 0.18
Nodes (7): IItemHandlerModifiable, IDynamicStackHandler, CompoundTag, Deprecated, ItemStack, Nonnull, Provider

### Community 161 - "CurioChangeEvent"
Cohesion: 0.22
Nodes (9): CurioChangeEvent, Item, Deprecated, Internal, ItemStack, LivingEntity, Nonnull, SlotContext (+1 more)

### Community 162 - "SlotTypePredicate"
Cohesion: 0.21
Nodes (7): IRegaliaSlotsApiMenu, RegaliaSlotsApiClientPackets, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketBreak

### Community 163 - "Override"
Cohesion: 0.36
Nodes (4): HumanoidRenderState, LayerDefinition, ModelPart, KnucklesModel

### Community 164 - "CuriosServices.java"
Cohesion: 0.24
Nodes (5): CuriosServices, Internal, ICuriosNetwork, ICuriosRegistry, DataComponentType

### Community 165 - "SlotTypePredicate"
Cohesion: 0.19
Nodes (6): Builder, Codec, Ints, RegistryFriendlyByteBuf, StreamCodec, SlotTypePredicate

### Community 166 - "CuriosCodecsAdapter.java"
Cohesion: 0.27
Nodes (4): DataProvider, CuriosDataProvider, CachedOutput, PathProvider

### Community 167 - "SlotTypeAdapter"
Cohesion: 0.23
Nodes (5): DropRule, EntityType, Identifier, Override, SlotTypeAdapter

### Community 168 - "ICurioSlotExtension"
Cohesion: 0.18
Nodes (6): ICurioSlotExtension, Component, Deprecated, ItemStack, TooltipFlag, ICurioSlot

### Community 169 - "RegaliaSlotsApiTest.java"
Cohesion: 0.19
Nodes (8): BuildCreativeModeTabContentsEvent, RegisterLayerDefinitions, FMLClientSetupEvent, IEventBus, Logger, Mod, RegisterCapabilitiesEvent, RegaliaSlotsApiTest

### Community 170 - "KnucklesModel"
Cohesion: 0.39
Nodes (5): HumanoidModel, AmuletModel, HumanoidRenderState, LayerDefinition, ModelPart

### Community 171 - "CuriosSlotTypes.java"
Cohesion: 0.27
Nodes (7): IGuiContainerHandler, NonNull, Override, Rect2i, RegaliaSlotsApiContainerHandler, Rect2i, RegaliaSlotsApiExclusionAreas

### Community 172 - "CuriosTooltip"
Cohesion: 0.27
Nodes (6): OnPress, GuiGraphicsExtractor, Identifier, Override, WidgetSprites, RenderButton

### Community 173 - "ICuriosCodecs"
Cohesion: 0.52
Nodes (6): EntityCapability, Identifier, ItemCapability, ItemResource, ResourceHandler, RegaliaSlotsApiCapability

### Community 174 - "KnucklesRenderer.java"
Cohesion: 0.26
Nodes (9): HumanoidRender, ModelLayerLocation, RegaliaSlotsApiLayerDefinitions, HumanoidModel, HumanoidRenderState, Identifier, ItemStack, Override (+1 more)

### Community 175 - "IEntitiesData"
Cohesion: 0.23
Nodes (11): CustomPacketPayload, CPacketToggleCosmetics, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, CPacketToggleRender, Nonnull (+3 more)

### Community 176 - ".matches"
Cohesion: 0.29
Nodes (5): EntitySelectorParser, CompoundTag, Entity, ItemStack, RegaliaSlotsApiSelectorOptions

### Community 177 - ".resource"
Cohesion: 0.28
Nodes (7): CuriosResources, Identifier, Logger, CuriosTags, EntityType, Item, TagKey

### Community 178 - "ICuriosSlots"
Cohesion: 0.27
Nodes (6): ICuriosSlots, EntityType, Identifier, Internal, ItemStack, LivingEntity

### Community 179 - "ICurioItem.java"
Cohesion: 0.22
Nodes (5): CurioAttributeModifiers, DropRule, Identifier, SlotContext, RegaliaTypeBridge

### Community 180 - "CurioCanUnequipEvent"
Cohesion: 0.38
Nodes (6): ICurioItem, ICurioSlotExtension, Item, Nullable, Override, RegaliaExtensionsAdapter

### Community 181 - "Preset"
Cohesion: 0.17
Nodes (12): Preset, BACK, BELT, BODY, BRACELET, CHARM, CURIO, FEET (+4 more)

### Community 182 - "Preset"
Cohesion: 0.17
Nodes (12): Preset, BACK, BELT, BODY, BRACELET, CHARM, CURIO, FEET (+4 more)

### Community 183 - "CurioCanEquipEvent"
Cohesion: 0.36
Nodes (7): Attribute, AttributeModifier, DataComponentType, Holder, ItemStack, Nonnull, RegaliaSlotsApiDataComponents

### Community 184 - "CurioCanUnequipEvent"
Cohesion: 0.05
Nodes (25): LivingEvent, CurioCanEquipEvent, Deprecated, Internal, ItemStack, TriState, CurioCanUnequipEvent, Deprecated (+17 more)

### Community 185 - "RegaliaSlotsApiResourceHandler.java"
Cohesion: 0.33
Nodes (5): ItemResource, ItemStack, NullMarked, Override, RegaliaSlotsApiResourceHandler

### Community 187 - "CuriosCompatMod.java"
Cohesion: 0.22
Nodes (9): PlayerLoggedInEvent, CurioAttributeModifiers, DataComponentType, DeferredRegister, IEventBus, Mod, ModContainer, RegisterCapabilitiesEvent (+1 more)

### Community 188 - "CuriosExtensionsAdapter"
Cohesion: 0.29
Nodes (7): CPacketDestroy, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, Logger, RegaliaSlotsApiConstants

### Community 189 - "CurioDropsEvent"
Cohesion: 0.29
Nodes (5): ICancellableEvent, CurioDropsEvent, DamageSource, ItemEntity, LivingEntity

### Community 190 - "Builder"
Cohesion: 0.27
Nodes (4): Builder, Codec, Ints, SlotPredicate

### Community 191 - "RegisterRegaliaSlotsApiExtensionsEvent"
Cohesion: 0.42
Nodes (4): Item, Nullable, Override, RegaliaSlotsApiExtensions

### Community 192 - "RegaliaSlotsApiJeiPlugin.java"
Cohesion: 0.33
Nodes (7): IGuiHandlerRegistration, IModPlugin, JeiPlugin, Identifier, NonNull, Override, RegaliaSlotsApiJeiPlugin

### Community 194 - ".addPermanentSlotModifier"
Cohesion: 0.29
Nodes (4): Identifier, Operation, Identifier, Operation

### Community 197 - "CuriosResourceHandler"
Cohesion: 0.36
Nodes (6): ItemStacksResourceHandler, CuriosResourceHandler, ItemResource, ItemStack, NullMarked, Override

### Community 198 - "CuriosDataComponents.java"
Cohesion: 0.36
Nodes (7): CuriosDataComponents, Attribute, AttributeModifier, DataComponentType, Holder, ItemStack, Nonnull

### Community 203 - "ICuriosExtensions"
Cohesion: 0.36
Nodes (3): ICuriosExtensions, Item, Nullable

### Community 204 - "SlotResult"
Cohesion: 0.23
Nodes (6): Identifier, SlotResult, ItemAccess, ItemStack, Nullable, SlotResult

### Community 205 - "CuriosTriggers.java"
Cohesion: 0.20
Nodes (6): Builder, CuriosTriggers, EquipBuilder, Criterion, CriterionTriggerInstance, Nonnull

### Community 206 - "RegaliaSlotsApiTags.java"
Cohesion: 0.62
Nodes (4): EntityType, Item, TagKey, RegaliaSlotsApiTags

### Community 207 - "CuriosCapability.java"
Cohesion: 0.52
Nodes (6): CuriosCapability, EntityCapability, Identifier, ItemCapability, ItemResource, ResourceHandler

### Community 208 - "SPacketQuickMove"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketQuickMove

### Community 209 - ".getFortuneLevel"
Cohesion: 0.16
Nodes (7): IRegaliaSlotsApiItemHandler, Deprecated, Item, ItemStack, LivingEntity, Tag, ServerPlayer

### Community 211 - "SPacketSyncActiveState"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncActiveState

### Community 212 - "SlotContext.java"
Cohesion: 0.40
Nodes (3): ItemAccess, LivingEntity, Nullable

### Community 213 - "RegaliaSlotsApiKeyMappings.java"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncRender

### Community 215 - "HandlerType"
Cohesion: 0.50
Nodes (4): fromValue(), HandlerType, COSMETIC, EQUIPMENT

### Community 218 - ".getAttributeModifierComponent"
Cohesion: 0.40
Nodes (4): CurioAttributeModifiers, DataComponentType, Override, RegaliaRegistryAdapter

### Community 219 - ".serialize"
Cohesion: 0.60
Nodes (3): Accessor, AccessorEntity, Mixin

### Community 223 - ".breakCurioInSlot"
Cohesion: 0.40
Nodes (3): Override, SlotContext, RegaliaNetworkAdapter

### Community 224 - "RegaliaSlotsApiTestIds.java"
Cohesion: 0.80
Nodes (3): Item, ResourceKey, RegaliaSlotsApiTestIds

### Community 225 - "Regalia Slots API 1.1.1"
Cohesion: 0.50
Nodes (3): Fixes, Internal, Regalia Slots API 1.1.1

### Community 227 - ".serialize"
Cohesion: 0.50
Nodes (3): Deprecated, JsonObject, Provider

## Knowledge Gaps
- **118 isolated node(s):** `BACK`, `BELT`, `BODY`, `BRACELET`, `CHARM` (+113 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **60 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SlotContext` connect `Slot Context` to `Item Stack Handler`, `Humanoid Model Layers`, `Advancement Sub Providers`, `Curio Stack Operations`, `Curio Inventory Capability`, `Item Properties`, `Block Drops Events`, `Itemized Regalia Slots API Capability`, `Player Avatar Rendering`, `Regalia Slots API Renderer`, `Slot Type`, `Slot Item Handler`, `Container Menu Provider`, `Dynamic Operations`, `Amulet Item`, `Regalia Slots API Client Mod`, `Equipment Slot Group`, `Regalia Slots API Slot Types`, `Recipe Book Screen`, `Entities Data`, `Regalia Slots API Tooltip`, `IEntitiesData Entry`, `Attribute Tooltip Events`, `KnucklesRenderer.java`, `CurioCanUnequipEvent`, `Curio Slot Extension`, `Slot Context`?**
  _High betweenness centrality (0.150) - this node is a cross-community bridge._
- **Why does `ISlotType` connect `Slot Type` to `Regalia Slots API Common Hooks`, `Attribute Handling`, `Curio Stack Operations`, `Item Properties`, `JSON Resource Reload`, `Block Drops Events`, `Slot Context`, `Slot Item Handler`, `Equipment Slot Group`, `SlotTypeAdapter`, `Regalia Slots API Slot Types`, `Recipe Book Screen`, `Curio Argument Type`, `Entities Data`, `Regalia Slots API Tooltip`, `Attribute Tooltip Events`, `ICurioItem.java`, `Slot Overrides`, `Regalia Slot API`, `Regalia GUI`, `.getSlotType`, `Item Capabilities`, `.getFortuneLevel`, `ICurioSlotExtension`, `Sync Render Packet`?**
  _High betweenness centrality (0.098) - this node is a cross-community bridge._
- **Why does `SlotContext` connect `Data Generation` to `Curio Inventory`, `Network Services`, `CurioChangeEvent`, `CuriosServices.java`, `ICurioSlotExtension`, `Loot Item Conditions`, `SlotResult`, `.resource`, `ICuriosSlots`, `SlotContext.java`, `Mod Event`, `CurioCanUnequipEvent`, `ISlotType`, `Builder`, `SlotContext`?**
  _High betweenness centrality (0.087) - this node is a cross-community bridge._
- **Are the 22 inferred relationships involving `SlotContext` (e.g. with `.onAttributeTooltip()` and `.renderHand()`) actually correct?**
  _`SlotContext` has 22 INFERRED edges - model-reasoned connections that need verification._
- **What connects `BACK`, `BELT`, `BODY` to the rest of the system?**
  _118 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Server Reload Listeners` be split into smaller, more focused modules?**
  _Cohesion score 0.055218855218855216 - nodes in this community are weakly interconnected._
- **Should `Advancement Sub Providers` be split into smaller, more focused modules?**
  _Cohesion score 0.06638714185883997 - nodes in this community are weakly interconnected._