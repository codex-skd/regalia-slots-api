# Graph Report - .  (2026-08-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 2007 nodes · 5001 edges · 153 communities (104 shown, 49 thin omitted)
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 247 edges (avg confidence: 0.8)
- Token cost: 7,884 input · 2,512 output

## Graph Freshness
- Built from commit: `c1eb35bd`
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
- Render Buttons
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
- Living Render State
- Curio Slot Extension
- Page Packet
- Quick Move Packet
- Sync Render Packet
- Slot Type
- Piglin AI
- Powder Snow Block
- Test Provider
- Regalia Tags
- Crown Item
- Permanent Slot Modifiers
- Slot Context
- AttributeModifier
- Curio Slot Interface
- Gradle Build Script
- Tag Operations
- Serialization Utilities
- Regalia Slot Test IDs
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

## Communities (153 total, 49 thin omitted)

### Community 0 - "Regalia Slots API Common Hooks"
Cohesion: 0.06
Nodes (41): ItemStack, RegaliaSlotsApiCommonHooks, Builder, Codec, Ints, RegistryFriendlyByteBuf, StreamCodec, SlotTypePredicate (+33 more)

### Community 1 - "Item Stack Handler"
Cohesion: 0.06
Nodes (28): ItemStackHandler, LivingEvent, CurioCanEquipEvent, Deprecated, Internal, ItemStack, TriState, CurioCanUnequipEvent (+20 more)

### Community 2 - "Humanoid Model Layers"
Cohesion: 0.06
Nodes (34): BuildCreativeModeTabContentsEvent, HumanoidModel, ModelLayerLocation, RegisterLayerDefinitions, AmuletModel, HumanoidRenderState, LayerDefinition, ModelPart (+26 more)

### Community 3 - "Server Reload Listeners"
Cohesion: 0.06
Nodes (34): AddServerReloadListenersEvent, ArgumentTypeInfo, AttachmentType, CombinedResourceHandler, CriterionTrigger, DeferredRegister, FMLCommonSetupEvent, ItemStacksResourceHandler (+26 more)

### Community 4 - "Advancement Sub Providers"
Cohesion: 0.07
Nodes (34): AdvancementHolder, AdvancementSubProvider, ContextAwarePredicate, ItemPredicate, Items, LocationPredicate, SimpleCriterionTrigger, SimpleInstance (+26 more)

### Community 5 - "Crafting Menu"
Cohesion: 0.08
Nodes (26): AbstractCraftingMenu, ConfigValue, CraftingContainer, RecipeBookType, ResultContainer, Container, FriendlyByteBuf, Inventory (+18 more)

### Community 6 - "Curio Stack Handler"
Cohesion: 0.08
Nodes (13): EntitySelectorParser, ICurioStacksHandler, AttributeModifier, CompoundTag, Deprecated, Identifier, NonNullList, Operation (+5 more)

### Community 7 - "Attribute Handling"
Cohesion: 0.08
Nodes (30): Attribute, IRegaliaSlotsApiCodecs, Attribute, Codec, Entry, Holder, Internal, RegistryFriendlyByteBuf (+22 more)

### Community 8 - "Curio Stack Operations"
Cohesion: 0.13
Nodes (10): CurioStacksHandler, AttributeModifier, CompoundTag, Identifier, Multimap, NonNullList, Operation, Override (+2 more)

### Community 9 - "Curio Inventory Capability"
Cohesion: 0.12
Nodes (14): CurioInventoryCapability, AttributeModifier, Identifier, IItemHandlerModifiable, ItemStack, LivingEntity, LootContext, Multimap (+6 more)

### Community 10 - "Item Properties"
Cohesion: 0.08
Nodes (18): SoundEvent, ICurio, Attribute, AttributeModifier, Component, CompoundTag, DamageSource, Deprecated (+10 more)

### Community 11 - "Loot Item Conditions"
Cohesion: 0.14
Nodes (19): LootItemCondition, LootItemConditionalFunction, LootItemFunction, MapCodec, NumberProvider, Builder, Attribute, Codec (+11 more)

### Community 12 - "Regalia Slots API Item Handler"
Cohesion: 0.10
Nodes (8): IRegaliaSlotsApiItemHandler, Identifier, IItemHandlerModifiable, ItemStack, ListTag, LivingEntity, LootContext, Operation

### Community 13 - "JSON Resource Reload"
Cohesion: 0.11
Nodes (17): JsonElement, ProfilerFiller, RegistryAccess, RegistryLookup, ResourceManager, SimpleJsonResourceReloadListener, Builder, Either (+9 more)

### Community 14 - "Block Drops Events"
Cohesion: 0.12
Nodes (19): BlockDropsEvent, EnderManAngerEvent, EntityConstructing, EntityJoinLevelEvent, LivingDropsEvent, LivingEquipmentChangeEvent, PickupXp, RightClickItem (+11 more)

### Community 15 - "Itemized Regalia Slots API Capability"
Cohesion: 0.17
Nodes (8): ItemizedRegaliaSlotsApiCapability, Component, CompoundTag, ItemStack, LootContext, Nonnull, Override, TooltipContext

### Community 16 - "Slot Data"
Cohesion: 0.13
Nodes (3): ISlotData, Override, SlotData

### Community 17 - "Item Handler Modifiable"
Cohesion: 0.17
Nodes (10): IItemHandlerModifiable, IDynamicStackHandler, CompoundTag, Deprecated, ItemStack, Nonnull, Provider, ItemStackHandler (+2 more)

### Community 18 - "Slot Context"
Cohesion: 0.19
Nodes (6): SlotContext, ICurioItem, CompoundTag, ItemStack, LootContext, Nonnull

### Community 19 - "Player Avatar Rendering"
Cohesion: 0.25
Nodes (14): AbstractClientPlayer, AvatarRenderState, EntityModel, HumanoidArm, HumanoidRender, Context, HumanoidRenderState, Identifier (+6 more)

### Community 20 - "Regalia Slots API Renderer"
Cohesion: 0.17
Nodes (12): ICurioRenderer, Nonnull, Nullable, Deprecated, RegaliaSlotsApiRendererRegistry, Item, Internal, RegaliaSlotsApiClientServices (+4 more)

### Community 21 - "Slot Type"
Cohesion: 0.16
Nodes (7): ISlotType, Deprecated, EntityType, Identifier, ItemStack, Nullable, Override

### Community 22 - "Entities Data"
Cohesion: 0.20
Nodes (13): EntitiesData, EntitySlotEntry, Entry, Codec, Either, EntityType, ICondition, JsonObject (+5 more)

### Community 23 - "Block Tags Provider"
Cohesion: 0.17
Nodes (13): Block, BlockTagCopyingItemTagProvider, BlockTagsProvider, DataProvider, Nonnull, Override, PackOutput, Provider (+5 more)

### Community 24 - "Command Build Context"
Cohesion: 0.24
Nodes (8): CommandBuildContext, CommandDispatcher, ItemInput, OnDatapackSyncEvent, CommandSourceStack, Identifier, ServerPlayer, RegaliaSlotsApiCommand

### Community 25 - "Level and Entity Slots"
Cohesion: 0.18
Nodes (8): Level, Deprecated, LivingEntity, RegaliaSlotsApi, ItemAccess, ItemStack, Nullable, SlotResult

### Community 26 - "Slot Item Handler"
Cohesion: 0.16
Nodes (8): SlotItemHandler, CurioSlot, Component, Deprecated, ItemStack, NonNullList, Override, Player

### Community 27 - "ISlotData Entry"
Cohesion: 0.12
Nodes (7): Entry, Either, EntityType, ICondition, Identifier, ResourceKey, TagKey

### Community 28 - "Mixin Inventory"
Cohesion: 0.19
Nodes (12): Container, CallbackInfoReturnable, Inject, ItemStack, Mixin, Override, Player, TagKey (+4 more)

### Community 29 - "Container Menu Provider"
Cohesion: 0.18
Nodes (11): AbstractContainerMenu, MenuProvider, Component, Inventory, Nonnull, Nullable, Override, Player (+3 more)

### Community 30 - "Container Screen"
Cohesion: 0.16
Nodes (11): AbstractContainerScreen, Pre, FMLClientSetupEvent, GuiGraphicsExtractor, Override, WidgetSprites, RegaliaSlotsApiButton, Pair (+3 more)

### Community 31 - "Client Configuration"
Cohesion: 0.12
Nodes (12): BooleanValue, ButtonCorner, BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT, Client, Builder (+4 more)

### Community 32 - "Curio Inventory"
Cohesion: 0.16
Nodes (11): Cache, IAttachmentHolder, CurioInventory, Identifier, ItemStack, LivingEntity, NonNullList, Override (+3 more)

### Community 33 - "Regalia Slots API Screen"
Cohesion: 0.22
Nodes (4): ItemSlotMouseAction, GuiGraphicsExtractor, Override, RegaliaSlotsApiScreen

### Community 34 - "Page Button"
Cohesion: 0.18
Nodes (13): Button, GuiGraphicsExtractor, Identifier, Override, PageButton, Type, NEXT, PREVIOUS (+5 more)

### Community 35 - "Dynamic Operations"
Cohesion: 0.21
Nodes (12): DynamicOps, ProblemReporter, CompoundTag, Entity, ItemStack, ListTag, LootContext, Pair (+4 more)

### Community 36 - "Amulet Item"
Cohesion: 0.22
Nodes (10): AmuletItem, Context, Identifier, ItemStack, LootContext, Nonnull, Override, PoseStack (+2 more)

### Community 37 - "Regalia Slots API Client Mod"
Cohesion: 0.17
Nodes (10): AddLayers, RegisterKeyMappingsEvent, RegisterMenuScreensEvent, RegisterRenderStateModifiersEvent, ContextKey, IEventBus, Mod, ModContainer (+2 more)

### Community 38 - "Regalia Slots API Test Registry"
Cohesion: 0.15
Nodes (9): DeferredItem, ItemStack, Override, KnucklesItem, ItemStack, Override, RingItem, IEventBus (+1 more)

### Community 39 - "Equipment Slot Group"
Cohesion: 0.26
Nodes (11): EquipmentSlotGroup, Attribute, AttributeModifier, EntityType, Holder, Identifier, ItemAttributeModifiers, ItemStack (+3 more)

### Community 40 - "Payload Handler"
Cohesion: 0.18
Nodes (9): PayloadRegistrar, IPayloadContext, RegaliaSlotsApiClientPayloadHandler, NetworkHandler, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec (+1 more)

### Community 41 - "Regalia Slots API Slot Types"
Cohesion: 0.23
Nodes (5): EntityType, Identifier, ItemStack, LivingEntity, RegaliaSlotsApiSlotTypes

### Community 42 - "Recipe Book Screen"
Cohesion: 0.15
Nodes (12): AbstractRecipeBookScreen, EffectsInInventory, KeyEvent, MouseButtonEvent, RecipeUpdateListener, ScreenPosition, IRegaliaSlotsApiScreen, Component (+4 more)

### Community 43 - "Regalia Slots API Extensions"
Cohesion: 0.24
Nodes (6): ICurioSlotExtension, IRegaliaSlotsApiExtensions, Nullable, Nullable, Override, RegaliaSlotsApiExtensions

### Community 44 - "Curio Argument Type"
Cohesion: 0.23
Nodes (9): ArgumentType, CommandContext, DynamicCommandExceptionType, CurioArgumentType, CommandSourceStack, Override, StringReader, Suggestions (+1 more)

### Community 45 - "Custom Packet Payloads"
Cohesion: 0.23
Nodes (11): CustomPacketPayload, CPacketToggleCosmetics, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, CPacketToggleRender, Nonnull (+3 more)

### Community 46 - "Entities Data"
Cohesion: 0.13
Nodes (5): IEntitiesData, Deprecated, Internal, JsonObject, Provider

### Community 47 - "Regalia Slots API Tooltip"
Cohesion: 0.36
Nodes (6): Component, Deprecated, ItemStack, LivingEntity, MutableComponent, RegaliaSlotsApiTooltip

### Community 48 - "IEntitiesData Entry"
Cohesion: 0.21
Nodes (7): Entry, IEntitySlotEntry, Either, EntityType, ICondition, ResourceKey, TagKey

### Community 49 - "Attribute Tooltip Events"
Cohesion: 0.26
Nodes (8): AddAttributeTooltipsEvent, ItemTooltipEvent, RenderArmEvent, ItemStack, Player, Post, SubscribeEvent, RegaliaSlotsApiClientEvents

### Community 50 - "Damage and Drop Rules"
Cohesion: 0.26
Nodes (6): ImmutableList, DropRulesEvent, DamageSource, ItemStack, LivingEntity, Pair

### Community 51 - "Curio Attributes"
Cohesion: 0.23
Nodes (9): Attribute, AttributeModifier, Component, DamageSource, EnderMan, Holder, Identifier, Multimap (+1 more)

### Community 52 - "Regalia Slot Data"
Cohesion: 0.33
Nodes (6): EntityType, Identifier, ItemStack, LivingEntity, Override, RegaliaSlotsApiSlots

### Community 53 - "Regalia Resources"
Cohesion: 0.28
Nodes (5): Identifier, Logger, RegaliaSlotsApiResources, Entry, Provider

### Community 54 - "Item Stack Sync"
Cohesion: 0.23
Nodes (11): fromValue(), HandlerType, COSMETIC, EQUIPMENT, CompoundTag, ItemStack, Nonnull, Override (+3 more)

### Community 56 - "Data Generation"
Cohesion: 0.29
Nodes (3): CachedOutput, PathProvider, RegaliaSlotsApiDataProvider

### Community 57 - "Curio Drops"
Cohesion: 0.29
Nodes (5): ICancellableEvent, CurioDropsEvent, DamageSource, ItemEntity, LivingEntity

### Community 58 - "Drop Rules"
Cohesion: 0.20
Nodes (9): DropRule, ALWAYS_DROP, ALWAYS_KEEP, DEFAULT, DESTROY, getSerializedName(), Nonnull, Override (+1 more)

### Community 59 - "Regalia Slot API"
Cohesion: 0.30
Nodes (6): IRegaliaSlotsApiSlots, EntityType, Identifier, Internal, ItemStack, LivingEntity

### Community 60 - "Preset Slots"
Cohesion: 0.17
Nodes (12): Preset, BACK, BELT, BODY, BRACELET, CHARM, CURIO, FEET (+4 more)

### Community 61 - "Regalia GUI"
Cohesion: 0.21
Nodes (7): IRegaliaSlotsApiMenu, RegaliaSlotsApiClientPackets, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketBreak

### Community 62 - "Slot Data Entry"
Cohesion: 0.27
Nodes (9): Entry, Either, EntityType, ICondition, Identifier, JsonObject, Provider, ResourceKey (+1 more)

### Community 63 - "Mixin Injection"
Cohesion: 0.35
Nodes (8): CallbackInfo, ModifyArg, Inject, Mixin, Pair, Schema, TypeTemplate, MixinV1460

### Community 64 - "GUI Handler"
Cohesion: 0.27
Nodes (7): IGuiContainerHandler, NonNull, Override, Rect2i, RegaliaSlotsApiContainerHandler, Rect2i, RegaliaSlotsApiExclusionAreas

### Community 65 - "Network Services"
Cohesion: 0.22
Nodes (5): Internal, RegaliaSlotsApiServices, IRegaliaSlotsApiNetwork, Override, RegaliaSlotsApiNetwork

### Community 66 - "Mod Plugin"
Cohesion: 0.33
Nodes (7): IGuiHandlerRegistration, IModPlugin, JeiPlugin, Identifier, NonNull, Override, RegaliaSlotsApiJeiPlugin

### Community 67 - "Cosmetic Buttons"
Cohesion: 0.27
Nodes (6): ImageButton, CosmeticButton, GuiGraphicsExtractor, Override, WidgetSprites, IRegaliaSlotsApiWidget

### Community 68 - "Render Buttons"
Cohesion: 0.31
Nodes (6): OnPress, GuiGraphicsExtractor, Identifier, Override, WidgetSprites, RenderButton

### Community 69 - "Render Layers"
Cohesion: 0.33
Nodes (7): RenderLayer, Context, Override, PoseStack, RenderLayerParent, SubmitNodeCollector, RegaliaSlotsApiLayer

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
Cohesion: 0.39
Nodes (6): CPacketOpenVanilla, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec

### Community 82 - "Item Grabbed"
Cohesion: 0.39
Nodes (6): ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketGrabbedItem

### Community 83 - "Living Entity"
Cohesion: 0.36
Nodes (5): CallbackInfoReturnable, Inject, Mixin, SuppressWarnings, MixinLivingEntity

### Community 84 - "Entity Capabilities"
Cohesion: 0.52
Nodes (6): EntityCapability, ItemCapability, ResourceHandler, Identifier, ItemResource, RegaliaSlotsApiCapability

### Community 85 - "Mod Event"
Cohesion: 0.43
Nodes (3): Event, IModBusEvent, RegisterRegaliaSlotsApiExtensionsEvent

### Community 86 - "Living Render State"
Cohesion: 0.38
Nodes (4): LivingEntityRenderState, Deprecated, LivingEntity, SuppressWarnings

### Community 87 - "Curio Slot Extension"
Cohesion: 0.33
Nodes (4): Component, Deprecated, ItemStack, TooltipFlag

### Community 88 - "Page Packet"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketPage

### Community 89 - "Quick Move Packet"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketQuickMove

### Community 90 - "Sync Render Packet"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncRender

### Community 91 - "Slot Type"
Cohesion: 0.48
Nodes (3): Builder, EntityType, Identifier

### Community 92 - "Piglin AI"
Cohesion: 0.48
Nodes (5): CallbackInfoReturnable, Inject, LivingEntity, Mixin, MixinPiglinAi

### Community 93 - "Powder Snow Block"
Cohesion: 0.48
Nodes (5): CallbackInfoReturnable, Entity, Inject, Mixin, MixinPowderSnowBlock

### Community 94 - "Test Provider"
Cohesion: 0.38
Nodes (4): Override, PackOutput, Provider, RegaliaSlotsApiTestProvider

### Community 95 - "Regalia Tags"
Cohesion: 0.67
Nodes (3): EntityType, TagKey, RegaliaSlotsApiTags

### Community 96 - "Crown Item"
Cohesion: 0.40
Nodes (3): CrownItem, ItemStack, Override

### Community 97 - "Permanent Slot Modifiers"
Cohesion: 0.60
Nodes (3): Accessor, AccessorEntity, Mixin

### Community 98 - "Slot Context"
Cohesion: 0.50
Nodes (3): ItemAccess, LivingEntity, Nullable

### Community 101 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 103 - "Serialization Utilities"
Cohesion: 0.50
Nodes (3): Deprecated, JsonObject, Provider

### Community 152 - "API Key Mappings"
Cohesion: 0.67
Nodes (3): Category, KeyMapping, RegaliaSlotsApiKeyMappings

## Knowledge Gaps
- **70 isolated node(s):** `BACK`, `BELT`, `BODY`, `BRACELET`, `CHARM` (+65 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **49 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SlotContext` connect `Slot Context` to `Regalia Slots API Common Hooks`, `Item Stack Handler`, `Humanoid Model Layers`, `Advancement Sub Providers`, `Curio Stack Handler`, `Curio Stack Operations`, `Curio Inventory Capability`, `Item Properties`, `Block Drops Events`, `Itemized Regalia Slots API Capability`, `Item Handler Modifiable`, `Player Avatar Rendering`, `Slot Type`, `Level and Entity Slots`, `Slot Item Handler`, `Amulet Item`, `Regalia Slots API Client Mod`, `Equipment Slot Group`, `Regalia Slots API Slot Types`, `Attribute Tooltip Events`, `Curio Attributes`, `Regalia Slot Data`, `Regalia Resources`, `Regalia Slot API`, `Network Services`, `Item Capabilities`, `Curio Slot Extension`, `Slot Context`, `Curio Slot Interface`?**
  _High betweenness centrality (0.218) - this node is a cross-community bridge._
- **Why does `ISlotType` connect `Slot Type` to `Regalia Slots API Common Hooks`, `Attribute Handling`, `Item Properties`, `Loot Item Conditions`, `Regalia Slots API Item Handler`, `JSON Resource Reload`, `Block Drops Events`, `Level and Entity Slots`, `Slot Item Handler`, `Curio Inventory`, `Equipment Slot Group`, `Regalia Slots API Slot Types`, `Curio Argument Type`, `Regalia Slots API Tooltip`, `Attribute Tooltip Events`, `Curio Attributes`, `Regalia Slot Data`, `Slot Overrides`, `Regalia Slot API`, `Slot Type`?**
  _High betweenness centrality (0.106) - this node is a cross-community bridge._
- **Why does `Item` connect `Regalia Slots API Renderer` to `Item Stack Handler`, `Server Reload Listeners`, `Curio Stack Handler`, `Curio Inventory Capability`, `Item Properties`, `Regalia Slots API Item Handler`, `Player Avatar Rendering`, `Block Tags Provider`, `Level and Entity Slots`, `Mixin Inventory`, `Dynamic Operations`, `Amulet Item`, `Regalia Slots API Test Registry`, `Equipment Slot Group`, `Regalia Slots API Extensions`, `Attribute Tooltip Events`, `Curio Attributes`, `Regalia Slot Data`, `Slot Data Entry`, `Item Capabilities`, `Test Armor`, `Regalia Tags`, `Crown Item`, `Regalia Slot Test IDs`?**
  _High betweenness centrality (0.087) - this node is a cross-community bridge._
- **Are the 22 inferred relationships involving `SlotContext` (e.g. with `.onAttributeTooltip()` and `.renderHand()`) actually correct?**
  _`SlotContext` has 22 INFERRED edges - model-reasoned connections that need verification._
- **What connects `BACK`, `BELT`, `BODY` to the rest of the system?**
  _70 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Regalia Slots API Common Hooks` be split into smaller, more focused modules?**
  _Cohesion score 0.05562714776632302 - nodes in this community are weakly interconnected._
- **Should `Item Stack Handler` be split into smaller, more focused modules?**
  _Cohesion score 0.05961538461538462 - nodes in this community are weakly interconnected._