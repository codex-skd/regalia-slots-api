# Graph Report - 1.21.1  (2026-08-31)

## Corpus Check
- 214 files · ~138,294 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2544 nodes · 5904 edges · 141 communities (133 shown, 8 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 74 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `19d23639`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- CurioStacksHandler
- Deprecated
- Deprecated
- SlotResult
- RegaliaSlotsApiContainer
- ISlotData
- CurioSlot
- SlotContext
- SlotContext
- CurioAttributeModifierEvent
- LivingEvent
- ICurioStacksHandler
- ItemizedCurioCapability
- ICurioStacksHandler
- RegaliaSlotsApiCommand.java
- CurioInventoryCapability
- .getRegaliaSlotsApiInventory
- IDynamicStackHandler
- CuriosApi.java
- ICuriosItemHandler
- ICurio.java
- ICurio.java
- Attribute
- ICurioRenderer
- RegaliaSlotsApiCommonMod.java
- NetworkHandler.java
- RegaliaSlotsApi.java
- ICuriosPlatform
- ICuriosItemHandler
- ISlotData
- ICurio
- ICuriosHelper
- RegaliaSlotsApiHelper.java
- ICurio
- ICuriosHelper
- ISlotType
- RegaliaSlotsApiServerPayloadHandler.java
- RegaliaSlotsApiEventHandler.java
- SetCurioAttributesFunction.java
- RegaliaSlotsApiScreen
- SlotData
- MixinInventory.java
- RegaliaSlotsApiDataProvider
- ISlotType
- ShimSlotType
- ICurioRenderer
- CurioAttributeModifierEvent
- RegaliaSlotsApiClientConfig.java
- Type
- SlotType
- RegaliaSlotsApiUtilMixinHooks.java
- CuriosApi
- EquipCurioTrigger.java
- RegaliaSlotsApiSlotManager.java
- RegaliaSlotsApiEntityManager.java
- .getEntitySlots
- SlotHelper
- ISlotHelper
- ICuriosItemHandler.java
- ISlotHelper
- CustomPacketPayload
- LegacyCurioMigration.java
- ClientProxy
- CurioInventory
- Flujo de trabajo — Regalia Slots API (NeoForge)
- DropRulesEvent
- IIconHelper
- Builder
- RegaliaSlotsApiTooltip
- RegaliaSlotsApiClientPackets.java
- .getCurios
- CuriosTooltip
- DropRulesEvent
- .matches
- EntitiesData
- RegaliaSlotsApiSlotManager
- ICuriosPlatform
- RegaliaSlotsApiButton.java
- ClientEventHandler.java
- CurseForge — Variables del proyecto
- Delegation brief — Regalia Slots API 1.21.1, Phase 2 (identity rename + Curios compat layer)
- RegaliaSlotsApiScreen.java
- CurioDropsEvent
- RegaliaSlotsApiRegistry.java
- RegaliaSlotsApiExclusionAreas.java
- MixinV1460.java
- CurioDropsEvent
- SlotPredicate
- CuriosCompatMod.java
- NeoForgeCurios.java
- Builder
- RegaliaSlotsApiEmiIntegration.java
- RegaliaSlotsApiJeiPlugin.java
- RegaliaSlotsApiConstants.java
- SPacketSetIcons
- SPacketSyncCurios
- SPacketSyncData
- SPacketSyncModifiers
- CurioCanEquipEvent
- Port Report — Regalia Slots API 1.21.1, Phase 2
- CurioCanUnequipEvent
- Deprecated
- SPacketSyncStack
- MixinApplyBonusCount.java
- MixinEnchantedCountIncreaseFunction.java
- MixinRegaliaSlotsApiTriggers.java
- MixinRegaliaSlotsApiTriggersEquip.java
- CurioCanUnequipEvent
- CosmeticButton
- GuiEventHandler.java
- CPacketOpenVanilla
- SPacketGrabbedItem
- IIconHelper
- RegaliaSlotsApiEmiPlugin.java
- RegaliaSlotsApiContainerHandler.java
- ItemStack
- CPacketToggleRender
- SPacketQuickMove
- SPacketSyncRender
- MixinLivingEntity.java
- MixinPiglinAi.java
- MixinPowderSnowBlock.java
- MixinRegaliaSlotsApiDataProvider.java
- ItemStack
- Regalia Slots API
- RegaliaSlotsApiCapability.java
- AttributeModifier
- IEntitiesData.java
- CuriosCapability.java
- [0.0.0-beta.1] - 2026-08-31
- CuriosTags
- ResourceLocation
- gradlew
- Regalia Slots API - Icon Generation Prompt
- ICuriosMenu
- ICuriosScreen.java

## God Nodes (most connected - your core abstractions)
1. `SlotContext` - 135 edges
2. `ICuriosItemHandler` - 76 edges
3. `ICurioStacksHandler` - 72 edges
4. `SlotContext` - 70 edges
5. `ISlotType` - 62 edges
6. `ICuriosItemHandler` - 56 edges
7. `ICurio` - 51 edges
8. `CurioStacksHandler` - 48 edges
9. `RegaliaSlotsApiContainer` - 41 edges
10. `RegaliaSlotsApiScreen` - 38 edges

## Surprising Connections (you probably didn't know these)
- `RegaliaSlotsApiRegistry` --references--> `CurioAttributeModifiers`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/common/RegaliaSlotsApiRegistry.java → src/main/java/com/skd/regaliaslotsapi/api/CurioAttributeModifiers.java
- `RegaliaSlotsApi` --references--> `ICuriosHelper`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApi.java → src/main/java/com/skd/regaliaslotsapi/api/type/util/ICuriosHelper.java
- `RegaliaSlotsApi` --references--> `IIconHelper`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApi.java → src/main/java/com/skd/regaliaslotsapi/api/type/util/IIconHelper.java
- `RegaliaSlotsApi` --references--> `ISlotHelper`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApi.java → src/main/java/com/skd/regaliaslotsapi/api/type/util/ISlotHelper.java
- `RegaliaSlotsApiCapability` --references--> `ICurio`  [EXTRACTED]
  src/main/java/com/skd/regaliaslotsapi/api/RegaliaSlotsApiCapability.java → src/main/java/com/skd/regaliaslotsapi/api/type/capability/ICurio.java

## Import Cycles
- None detected.

## Communities (141 total, 8 thin omitted)

### Community 0 - "CurioStacksHandler"
Cohesion: 0.05
Nodes (23): ItemStackHandler, IDynamicStackHandler, CompoundTag, ItemStack, Nonnull, Provider, CosmeticCurioSlot, OnlyIn (+15 more)

### Community 1 - "Deprecated"
Cohesion: 0.06
Nodes (40): Builder, CurioAttributeModifiers, Entry, Attribute, AttributeModifier, Codec, Holder, RegistryFriendlyByteBuf (+32 more)

### Community 2 - "Deprecated"
Cohesion: 0.06
Nodes (38): Builder, CurioAttributeModifiers, Entry, Attribute, AttributeModifier, Codec, Holder, RegistryFriendlyByteBuf (+30 more)

### Community 3 - "SlotResult"
Cohesion: 0.09
Nodes (30): ItemStack, SlotResult, Attribute, AttributeModifier, CallbackInfo, CallbackInfoReturnable, EntityType, Holder (+22 more)

### Community 4 - "RegaliaSlotsApiContainer"
Cohesion: 0.07
Nodes (33): ConfigValue, CraftingContainer, EquipmentSlot, Recipe, RecipeBookMenu, RecipeBookType, RecipeHolder, RecipeInput (+25 more)

### Community 5 - "ISlotData"
Cohesion: 0.06
Nodes (20): DataProvider, CuriosDataProvider, CachedOutput, ExistingFileHelper, Nonnull, PackOutput, PathProvider, Provider (+12 more)

### Community 6 - "CurioSlot"
Cohesion: 0.06
Nodes (23): Accessor, OnPress, SlotItemHandler, ICurioSlotExtension, Component, ItemStack, TooltipFlag, Internal (+15 more)

### Community 7 - "SlotContext"
Cohesion: 0.12
Nodes (18): LivingEntity, SlotContext, ICurioItem, Attribute, AttributeModifier, Component, CompoundTag, DamageSource (+10 more)

### Community 8 - "SlotContext"
Cohesion: 0.12
Nodes (18): LivingEntity, SlotContext, ICurioItem, Attribute, AttributeModifier, Component, CompoundTag, DamageSource (+10 more)

### Community 9 - "CurioAttributeModifierEvent"
Cohesion: 0.11
Nodes (17): Event, IModBusEvent, CurioAttributeModifierEvent, Attribute, AttributeModifier, Holder, ItemStack, Multimap (+9 more)

### Community 10 - "LivingEvent"
Cohesion: 0.08
Nodes (17): LivingEvent, CurioCanEquipEvent, Deprecated, ItemStack, TriState, CurioChangeEvent, ItemStack, LivingEntity (+9 more)

### Community 11 - "ICurioStacksHandler"
Cohesion: 0.07
Nodes (8): ICurioStacksHandler, AttributeModifier, CompoundTag, Deprecated, DropRule, NonNullList, Operation, ResourceLocation

### Community 12 - "ItemizedCurioCapability"
Cohesion: 0.13
Nodes (17): ItemizedCurioCapability, Attribute, AttributeModifier, Component, CompoundTag, DamageSource, DropRule, EnderMan (+9 more)

### Community 13 - "ICurioStacksHandler"
Cohesion: 0.07
Nodes (8): ICurioStacksHandler, AttributeModifier, CompoundTag, Deprecated, DropRule, NonNullList, Operation, ResourceLocation

### Community 14 - "RegaliaSlotsApiCommand.java"
Cohesion: 0.12
Nodes (18): ArgumentType, CommandBuildContext, CommandContext, CommandDispatcher, DynamicCommandExceptionType, ItemInput, Item, TagKey (+10 more)

### Community 15 - "CurioInventoryCapability"
Cohesion: 0.15
Nodes (11): CurioInventoryCapability, AttributeModifier, IItemHandlerModifiable, ItemStackHandler, ListTag, LivingEntity, Multimap, Nullable (+3 more)

### Community 16 - ".getRegaliaSlotsApiInventory"
Cohesion: 0.12
Nodes (13): Clone, EntityConstructing, PickupXp, RightClickItem, Player, PlayerLoggedInEvent, Post, SubscribeEvent (+5 more)

### Community 17 - "IDynamicStackHandler"
Cohesion: 0.11
Nodes (12): IItemHandler, IItemHandlerModifiable, NotNull, CurioItemHandler, ItemStack, LivingEntity, Override, IDynamicStackHandler (+4 more)

### Community 18 - "CuriosApi.java"
Cohesion: 0.13
Nodes (16): Attribute, AttributeModifier, EntityType, EquipmentSlotGroup, Holder, Item, ItemAttributeModifiers, ItemStack (+8 more)

### Community 19 - "ICuriosItemHandler"
Cohesion: 0.10
Nodes (8): ICuriosItemHandler, IItemHandlerModifiable, ListTag, LivingEntity, LootContext, Operation, ResourceLocation, Tag

### Community 20 - "ICurio.java"
Cohesion: 0.12
Nodes (19): DropRule, ALWAYS_DROP, ALWAYS_KEEP, DEFAULT, DESTROY, Attribute, AttributeModifier, Component (+11 more)

### Community 21 - "ICurio.java"
Cohesion: 0.12
Nodes (19): DropRule, ALWAYS_DROP, ALWAYS_KEEP, DEFAULT, DESTROY, Attribute, AttributeModifier, Component (+11 more)

### Community 22 - "Attribute"
Cohesion: 0.13
Nodes (17): Attribute, AttributeModifier, Holder, MutableComponent, Nonnull, Override, SuppressWarnings, TooltipFlag (+9 more)

### Community 23 - "ICurioRenderer"
Cohesion: 0.13
Nodes (15): RenderLayer, ICurioRenderer, ItemStack, LivingEntity, MultiBufferSource, PoseStack, RenderLayerParent, SafeVarargs (+7 more)

### Community 24 - "RegaliaSlotsApiCommonMod.java"
Cohesion: 0.12
Nodes (14): AddReloadListenerEvent, FMLCommonSetupEvent, InterModProcessEvent, KeyMapping, ModContainer, RegisterCommandsEvent, RegisterPayloadHandlersEvent, ServerAboutToStartEvent (+6 more)

### Community 25 - "NetworkHandler.java"
Cohesion: 0.12
Nodes (14): PayloadRegistrar, IPayloadContext, RegaliaSlotsApiClientPayloadHandler, NetworkHandler, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec (+6 more)

### Community 26 - "RegaliaSlotsApi.java"
Cohesion: 0.21
Nodes (12): Attribute, AttributeModifier, EquipmentSlotGroup, Holder, Item, ItemAttributeModifiers, ItemStack, Logger (+4 more)

### Community 27 - "ICuriosPlatform"
Cohesion: 0.16
Nodes (13): EnderMan, ISlotType, ItemStack, LivingEntity, Override, Player, RegaliaCuriosPlatformAdapter, ICuriosPlatform (+5 more)

### Community 28 - "ICuriosItemHandler"
Cohesion: 0.14
Nodes (4): ICuriosItemHandler, Deprecated, LootContext, ScheduledForRemoval

### Community 29 - "ISlotData"
Cohesion: 0.11
Nodes (8): ISlotData, Deprecated, DropRule, ICondition, JsonObject, Operation, Provider, ResourceLocation

### Community 30 - "ICurio"
Cohesion: 0.13
Nodes (6): ICurio, CompoundTag, EnderMan, ItemStack, LivingEntity, LootContext

### Community 31 - "ICuriosHelper"
Cohesion: 0.27
Nodes (10): ICuriosHelper, Deprecated, IItemHandlerModifiable, ImmutableTriple, Item, ItemStack, LivingEntity, Nonnull (+2 more)

### Community 32 - "RegaliaSlotsApiHelper.java"
Cohesion: 0.23
Nodes (10): IItemHandlerModifiable, ImmutableTriple, Item, ItemStack, LivingEntity, Nonnull, Override, SlotContext (+2 more)

### Community 33 - "ICurio"
Cohesion: 0.13
Nodes (6): ICurio, EnderMan, ItemStack, LivingEntity, LootContext, SlotContext

### Community 34 - "ICuriosHelper"
Cohesion: 0.27
Nodes (10): ICuriosHelper, Deprecated, IItemHandlerModifiable, ImmutableTriple, Item, ItemStack, LivingEntity, Nonnull (+2 more)

### Community 35 - "ISlotType"
Cohesion: 0.14
Nodes (6): ISlotType, CompoundTag, Deprecated, DropRule, ResourceLocation, ScheduledForRemoval

### Community 36 - "RegaliaSlotsApiServerPayloadHandler.java"
Cohesion: 0.17
Nodes (11): AbstractContainerMenu, MenuProvider, Component, Inventory, Nonnull, Nullable, Override, Player (+3 more)

### Community 37 - "RegaliaSlotsApiEventHandler.java"
Cohesion: 0.15
Nodes (17): BlockDropsEvent, EnderManAngerEvent, EntityJoinLevelEvent, LivingDropsEvent, LivingEquipmentChangeEvent, OnDatapackSyncEvent, DropRule, ItemEntity (+9 more)

### Community 38 - "SetCurioAttributesFunction.java"
Cohesion: 0.18
Nodes (16): LootContextParam, LootItemCondition, LootItemConditionalFunction, MapCodec, NumberProvider, Attribute, Codec, Holder (+8 more)

### Community 39 - "RegaliaSlotsApiScreen"
Cohesion: 0.20
Nodes (5): GuiGraphics, ImageButton, Override, Slot, RegaliaSlotsApiScreen

### Community 40 - "SlotData"
Cohesion: 0.18
Nodes (8): DropRule, ICondition, JsonObject, Operation, Override, Provider, ResourceLocation, SlotData

### Community 41 - "MixinInventory.java"
Cohesion: 0.21
Nodes (12): Container, CallbackInfoReturnable, Inject, Item, ItemStack, Mixin, Override, Player (+4 more)

### Community 42 - "RegaliaSlotsApiDataProvider"
Cohesion: 0.19
Nodes (8): CachedOutput, ExistingFileHelper, Nonnull, PackOutput, PathProvider, Provider, RegaliaSlotsApiDataProvider, IEntitiesData

### Community 43 - "ISlotType"
Cohesion: 0.15
Nodes (6): ISlotType, CompoundTag, Deprecated, DropRule, ResourceLocation, ScheduledForRemoval

### Community 44 - "ShimSlotType"
Cohesion: 0.20
Nodes (6): ISlotType, CompoundTag, DropRule, Override, ResourceLocation, ShimSlotType

### Community 45 - "ICurioRenderer"
Cohesion: 0.19
Nodes (9): CuriosRendererRegistry, Item, ICurioRenderer, ItemStack, LivingEntity, MultiBufferSource, PoseStack, RenderLayerParent (+1 more)

### Community 46 - "CurioAttributeModifierEvent"
Cohesion: 0.33
Nodes (7): CurioAttributeModifierEvent, Attribute, AttributeModifier, Holder, ItemStack, Multimap, ResourceLocation

### Community 47 - "RegaliaSlotsApiClientConfig.java"
Cohesion: 0.13
Nodes (12): BooleanValue, ButtonCorner, BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT, Client, Builder (+4 more)

### Community 48 - "Type"
Cohesion: 0.18
Nodes (13): Button, GuiGraphics, Override, ResourceLocation, PageButton, Type, NEXT, PREVIOUS (+5 more)

### Community 49 - "SlotType"
Cohesion: 0.20
Nodes (3): CompoundTag, Override, SlotType

### Community 50 - "RegaliaSlotsApiUtilMixinHooks.java"
Cohesion: 0.17
Nodes (12): CompoundTag, Entity, Mixin, ModifyVariable, MixinNbtPredicate, CompoundTag, Entity, Item (+4 more)

### Community 51 - "CuriosApi"
Cohesion: 0.31
Nodes (5): CuriosApi, Deprecated, Level, LivingEntity, ScheduledForRemoval

### Community 52 - "EquipCurioTrigger.java"
Cohesion: 0.21
Nodes (13): ContextAwarePredicate, ItemPredicate, LocationPredicate, SimpleCriterionTrigger, SimpleInstance, EquipCurioTrigger, Codec, ItemStack (+5 more)

### Community 53 - "RegaliaSlotsApiSlotManager.java"
Cohesion: 0.18
Nodes (8): IMCMessage, Builder, JsonElement, JsonObject, ProfilerFiller, ResourceManager, Builder, LegacySlotManager

### Community 54 - "RegaliaSlotsApiEntityManager.java"
Cohesion: 0.21
Nodes (10): SimpleJsonResourceReloadListener, EntityType, Gson, JsonElement, JsonObject, ListTag, ProfilerFiller, ResourceLocation (+2 more)

### Community 55 - ".getEntitySlots"
Cohesion: 0.24
Nodes (7): Deprecated, EntityType, Level, LivingEntity, Nonnull, Player, ScheduledForRemoval

### Community 56 - "SlotHelper"
Cohesion: 0.32
Nodes (3): LivingEntity, Override, SlotHelper

### Community 57 - "ISlotHelper"
Cohesion: 0.35
Nodes (4): ISlotHelper, Deprecated, LivingEntity, ScheduledForRemoval

### Community 58 - "ICuriosItemHandler.java"
Cohesion: 0.15
Nodes (6): AttributeModifier, IItemHandlerModifiable, ListTag, LivingEntity, Multimap, Tag

### Community 59 - "ISlotHelper"
Cohesion: 0.35
Nodes (4): ISlotHelper, Deprecated, LivingEntity, ScheduledForRemoval

### Community 60 - "CustomPacketPayload"
Cohesion: 0.22
Nodes (12): CustomPacketPayload, CPacketOpenCurios, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, CPacketToggleCosmetics (+4 more)

### Community 61 - "LegacyCurioMigration.java"
Cohesion: 0.23
Nodes (9): AttachmentType, CompoundTag, DeferredRegister, IEventBus, Override, Provider, ServerPlayer, LegacyCurioData (+1 more)

### Community 62 - "ClientProxy"
Cohesion: 0.18
Nodes (9): AddLayers, EventBusSubscriber, FMLClientSetupEvent, Model, RegisterKeyMappingsEvent, RegisterMenuScreensEvent, ClientProxy, SubscribeEvent (+1 more)

### Community 63 - "CurioInventory"
Cohesion: 0.24
Nodes (10): Cache, INBTSerializable, CurioInventory, CompoundTag, ItemStack, NonNullList, Override, Pair (+2 more)

### Community 64 - "Flujo de trabajo — Regalia Slots API (NeoForge)"
Cohesion: 0.13
Nodes (14): Buenas prácticas, Capa de compatibilidad Curios API (introducida en v0.0.0-beta.4, estable desde v1.0.0), Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Regalia Slots API (NeoForge), Flujo por tarea (+6 more)

### Community 65 - "DropRulesEvent"
Cohesion: 0.27
Nodes (7): DropRulesEvent, DamageSource, DropRule, ImmutableList, ItemStack, LivingEntity, Tuple

### Community 66 - "IIconHelper"
Cohesion: 0.27
Nodes (7): IIconHelper, Deprecated, ResourceLocation, ScheduledForRemoval, IconHelper, Override, ResourceLocation

### Community 67 - "Builder"
Cohesion: 0.25
Nodes (3): Builder, DropRule, ResourceLocation

### Community 68 - "RegaliaSlotsApiTooltip"
Cohesion: 0.32
Nodes (5): Component, ItemStack, LivingEntity, MutableComponent, RegaliaSlotsApiTooltip

### Community 69 - "RegaliaSlotsApiClientPackets.java"
Cohesion: 0.20
Nodes (8): ICuriosMenu, Multimap, RegaliaSlotsApiClientPackets, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncActiveState

### Community 70 - ".getCurios"
Cohesion: 0.22
Nodes (5): Item, ItemStack, LootContext, SlotResult, ItemStack

### Community 71 - "CuriosTooltip"
Cohesion: 0.32
Nodes (5): CuriosTooltip, Component, ItemStack, LivingEntity, MutableComponent

### Community 72 - "DropRulesEvent"
Cohesion: 0.30
Nodes (7): DropRulesEvent, DamageSource, DropRule, ImmutableList, ItemStack, LivingEntity, Tuple

### Community 73 - ".matches"
Cohesion: 0.29
Nodes (5): EntitySelectorParser, CompoundTag, Entity, ItemStack, RegaliaSlotsApiSelectorOptions

### Community 74 - "EntitiesData"
Cohesion: 0.28
Nodes (6): EntitiesData, EntityType, ICondition, JsonObject, Override, Provider

### Community 75 - "RegaliaSlotsApiSlotManager"
Cohesion: 0.21
Nodes (4): Gson, ListTag, ResourceLocation, RegaliaSlotsApiSlotManager

### Community 76 - "ICuriosPlatform"
Cohesion: 0.28
Nodes (6): ICuriosPlatform, EnderMan, ItemStack, LivingEntity, Player, Services

### Community 77 - "RegaliaSlotsApiButton.java"
Cohesion: 0.24
Nodes (7): AbstractContainerScreen, ICuriosScreen, GuiGraphics, Override, WidgetSprites, RegaliaSlotsApiButton, Tuple

### Community 78 - "ClientEventHandler.java"
Cohesion: 0.30
Nodes (7): AddAttributeTooltipsEvent, ItemTooltipEvent, ClientEventHandler, ItemStack, Player, Post, SubscribeEvent

### Community 79 - "CurseForge — Variables del proyecto"
Cohesion: 0.17
Nodes (11): CurseForge — Variables del proyecto, Descripción del proyecto, Entorno "Client & Server", Flujo completo, IDs de `gameVersions` para 1.21.1, Parámetros del upload, Proyecto, Rama (+3 more)

### Community 80 - "Delegation brief — Regalia Slots API 1.21.1, Phase 2 (identity rename + Curios compat layer)"
Cohesion: 0.17
Nodes (11): Context, Delegation brief — Regalia Slots API 1.21.1, Phase 2 (identity rename + Curios compat layer), Deliverable, HARD CONSTRAINTS, Reference material (all inside `temp/ref/`, your sandbox blocks outside reads), TASK 1 — Identity rename `Curios* -> RegaliaSlotsApi*`, TASK 2 — Verbatim `top.theillusivec4.curios.api` compat copy, TASK 3 — Compat adapter: back the shim with the real implementation (+3 more)

### Community 81 - "RegaliaSlotsApiScreen.java"
Cohesion: 0.20
Nodes (9): EffectRenderingInventoryScreen, RecipeBookComponent, RecipeUpdateListener, ClickType, Component, Inventory, ItemStack, Nonnull (+1 more)

### Community 82 - "CurioDropsEvent"
Cohesion: 0.29
Nodes (5): ICancellableEvent, CurioDropsEvent, DamageSource, ItemEntity, LivingEntity

### Community 83 - "RegaliaSlotsApiRegistry.java"
Cohesion: 0.33
Nodes (9): ArgumentTypeInfo, CriterionTrigger, DataComponentType, MenuType, AttachmentType, DeferredRegister, IEventBus, LootItemFunctionType (+1 more)

### Community 84 - "RegaliaSlotsApiExclusionAreas.java"
Cohesion: 0.27
Nodes (7): ExclusionZones, REIClientPlugin, REIPluginClient, Rect2i, RegaliaSlotsApiExclusionAreas, Override, RegaliaSlotsApiReiPlugin

### Community 85 - "MixinV1460.java"
Cohesion: 0.35
Nodes (8): ModifyArg, CallbackInfo, Inject, Mixin, Pair, Schema, TypeTemplate, MixinV1460

### Community 86 - "CurioDropsEvent"
Cohesion: 0.31
Nodes (4): CurioDropsEvent, DamageSource, ItemEntity, LivingEntity

### Community 87 - "SlotPredicate"
Cohesion: 0.27
Nodes (4): Builder, Codec, Ints, SlotPredicate

### Community 88 - "CuriosCompatMod.java"
Cohesion: 0.27
Nodes (7): CuriosCompatMod, EntityCapability, IEventBus, ItemCapability, Mod, PlayerLoggedInEvent, RegisterCapabilitiesEvent

### Community 89 - "NeoForgeCurios.java"
Cohesion: 0.40
Nodes (6): EnderMan, ItemStack, LivingEntity, Override, Player, NeoForgeCurios

### Community 90 - "Builder"
Cohesion: 0.27
Nodes (4): Builder, Codec, Ints, SlotPredicate

### Community 91 - "RegaliaSlotsApiEmiIntegration.java"
Cohesion: 0.27
Nodes (5): Background, IEventBus, RegaliaSlotsApiEmiIntegration, IEventBus, RegaliaSlotsApiIntegrations

### Community 92 - "RegaliaSlotsApiJeiPlugin.java"
Cohesion: 0.33
Nodes (7): IGuiHandlerRegistration, IModPlugin, JeiPlugin, Nonnull, Override, ResourceLocation, RegaliaSlotsApiJeiPlugin

### Community 93 - "RegaliaSlotsApiConstants.java"
Cohesion: 0.29
Nodes (7): CPacketDestroy, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, Logger, RegaliaSlotsApiConstants

### Community 94 - "SPacketSetIcons"
Cohesion: 0.33
Nodes (7): FriendlyByteBuf, Nonnull, Override, RegistryFriendlyByteBuf, ResourceLocation, StreamCodec, SPacketSetIcons

### Community 95 - "SPacketSyncCurios"
Cohesion: 0.31
Nodes (7): CompoundTag, FriendlyByteBuf, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncCurios

### Community 96 - "SPacketSyncData"
Cohesion: 0.33
Nodes (7): FriendlyByteBuf, ListTag, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncData

### Community 97 - "SPacketSyncModifiers"
Cohesion: 0.31
Nodes (7): CompoundTag, FriendlyByteBuf, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncModifiers

### Community 98 - "CurioCanEquipEvent"
Cohesion: 0.36
Nodes (4): CurioCanEquipEvent, Deprecated, ItemStack, TriState

### Community 99 - "Port Report — Regalia Slots API 1.21.1, Phase 2"
Cohesion: 0.22
Nodes (8): Port Report — Regalia Slots API 1.21.1, Phase 2, Residual / cosmetic (not blocking), Task 1 — identity rename `Curios* -> RegaliaSlotsApi*`, Task 2 — verbatim `top.theillusivec4.curios.api` compat copy, Task 3 — compat adapter, Task 4 — second logical mod id `curios`, Task 5 — legacy Curios data migration, Task 6 — config default (11 preset slots)

### Community 100 - "CurioCanUnequipEvent"
Cohesion: 0.39
Nodes (3): CurioCanUnequipEvent, ItemStack, TriState

### Community 102 - "SPacketSyncStack"
Cohesion: 0.36
Nodes (7): CompoundTag, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncStack

### Community 103 - "MixinApplyBonusCount.java"
Cohesion: 0.39
Nodes (7): Enchantment, Holder, ItemStack, LootContext, Mixin, ModifyVariable, MixinApplyBonusCount

### Community 104 - "MixinEnchantedCountIncreaseFunction.java"
Cohesion: 0.39
Nodes (7): Enchantment, Holder, ItemStack, LootContext, Mixin, ModifyVariable, MixinEnchantedCountIncreaseFunction

### Community 105 - "MixinRegaliaSlotsApiTriggers.java"
Cohesion: 0.44
Nodes (6): Builder, CallbackInfoReturnable, Criterion, Inject, Mixin, MixinRegaliaSlotsApiTriggers

### Community 106 - "MixinRegaliaSlotsApiTriggersEquip.java"
Cohesion: 0.36
Nodes (7): Builder, CallbackInfoReturnable, Criterion, CriterionTriggerInstance, Inject, Mixin, MixinRegaliaSlotsApiTriggersEquip

### Community 107 - "CurioCanUnequipEvent"
Cohesion: 0.39
Nodes (3): CurioCanUnequipEvent, ItemStack, TriState

### Community 108 - "CosmeticButton"
Cohesion: 0.36
Nodes (5): ImageButton, CosmeticButton, GuiGraphics, Override, WidgetSprites

### Community 109 - "GuiEventHandler.java"
Cohesion: 0.39
Nodes (4): Pre, GuiEventHandler, Post, SubscribeEvent

### Community 110 - "CPacketOpenVanilla"
Cohesion: 0.39
Nodes (6): CPacketOpenVanilla, ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec

### Community 111 - "SPacketGrabbedItem"
Cohesion: 0.39
Nodes (6): ItemStack, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketGrabbedItem

### Community 112 - "IIconHelper"
Cohesion: 0.54
Nodes (4): IIconHelper, Deprecated, ResourceLocation, ScheduledForRemoval

### Community 113 - "RegaliaSlotsApiEmiPlugin.java"
Cohesion: 0.43
Nodes (5): EmiEntrypoint, EmiPlugin, EmiRegistry, Override, RegaliaSlotsApiEmiPlugin

### Community 114 - "RegaliaSlotsApiContainerHandler.java"
Cohesion: 0.43
Nodes (5): IGuiContainerHandler, Nonnull, Override, Rect2i, RegaliaSlotsApiContainerHandler

### Community 116 - "CPacketToggleRender"
Cohesion: 0.43
Nodes (5): CPacketToggleRender, Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec

### Community 117 - "SPacketQuickMove"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketQuickMove

### Community 118 - "SPacketSyncRender"
Cohesion: 0.43
Nodes (5): Nonnull, Override, RegistryFriendlyByteBuf, StreamCodec, SPacketSyncRender

### Community 119 - "MixinLivingEntity.java"
Cohesion: 0.43
Nodes (5): CallbackInfoReturnable, Inject, Mixin, SuppressWarnings, MixinLivingEntity

### Community 120 - "MixinPiglinAi.java"
Cohesion: 0.48
Nodes (5): CallbackInfoReturnable, Inject, LivingEntity, Mixin, MixinPiglinAi

### Community 121 - "MixinPowderSnowBlock.java"
Cohesion: 0.48
Nodes (5): CallbackInfoReturnable, Entity, Inject, Mixin, MixinPowderSnowBlock

### Community 122 - "MixinRegaliaSlotsApiDataProvider.java"
Cohesion: 0.52
Nodes (4): CallbackInfoReturnable, Inject, Mixin, MixinRegaliaSlotsApiDataProvider

### Community 124 - "Regalia Slots API"
Cohesion: 0.33
Nodes (5): Adding to Your Project, Credits & License, Features, Overview, Regalia Slots API

### Community 125 - "RegaliaSlotsApiCapability.java"
Cohesion: 0.60
Nodes (5): EntityCapability, IItemHandler, ItemCapability, ResourceLocation, RegaliaSlotsApiCapability

### Community 127 - "IEntitiesData.java"
Cohesion: 0.33
Nodes (3): ICondition, JsonObject, Provider

### Community 128 - "CuriosCapability.java"
Cohesion: 0.60
Nodes (5): CuriosCapability, EntityCapability, IItemHandler, ItemCapability, ResourceLocation

### Community 129 - "[0.0.0-beta.1] - 2026-08-31"
Cohesion: 0.40
Nodes (4): [0.0.0-beta.1] - 2026-08-31, Added, Regalia Slots API (1.21.1) — Changelog, Technical

### Community 130 - "CuriosTags"
Cohesion: 0.80
Nodes (3): CuriosTags, Item, TagKey

### Community 132 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **86 isolated node(s):** `HEAD`, `NECKLACE`, `BACK`, `BODY`, `BRACELET` (+81 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **8 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SlotContext` connect `SlotContext` to `CurioStacksHandler`, `SlotResult`, `CurioSlot`, `CurioAttributeModifierEvent`, `LivingEvent`, `ItemizedCurioCapability`, `CurioInventoryCapability`, `.getRegaliaSlotsApiInventory`, `ICurio.java`, `ICurioRenderer`, `NetworkHandler.java`, `RegaliaSlotsApi.java`, `ICurio`, `ICuriosHelper`, `RegaliaSlotsApiHelper.java`, `RegaliaSlotsApiServerPayloadHandler.java`, `RegaliaSlotsApiEventHandler.java`, `EquipCurioTrigger.java`, `.getCurios`, `ClientEventHandler.java`, `SlotPredicate`, `CurioCanUnequipEvent`?**
  _High betweenness centrality (0.251) - this node is a cross-community bridge._
- **Why does `ICuriosItemHandler` connect `ICuriosItemHandler` to `CurioStacksHandler`, `SlotResult`, `RegaliaSlotsApiContainer`, `ICurioStacksHandler`, `CurioInventoryCapability`, `.getRegaliaSlotsApiInventory`, `RegaliaSlotsApi.java`, `ICuriosHelper`, `RegaliaSlotsApiHelper.java`, `RegaliaSlotsApiEventHandler.java`, `LegacyCurioMigration.java`, `CurioInventory`, `DropRulesEvent`, `RegaliaSlotsApiClientPackets.java`, `CurioDropsEvent`, `CuriosCompatMod.java`, `Deprecated`, `ItemStack`, `RegaliaSlotsApiCapability.java`, `AttributeModifier`?**
  _High betweenness centrality (0.142) - this node is a cross-community bridge._
- **Why does `ICurio` connect `ICurio` to `RegaliaSlotsApiHelper.java`, `DropRulesEvent`, `ISlotType`, `Builder`, `RegaliaSlotsApiEventHandler.java`, `RegaliaSlotsApiClientPackets.java`, `SlotResult`, `SlotData`, `ICurioStacksHandler`, `ItemizedCurioCapability`, `.getRegaliaSlotsApiInventory`, `ISlotData`, `ICurio.java`, `RegaliaSlotsApiSlotManager.java`, `CuriosCompatMod.java`, `RegaliaSlotsApi.java`, `RegaliaSlotsApiCapability.java`, `ICuriosHelper`?**
  _High betweenness centrality (0.116) - this node is a cross-community bridge._
- **Are the 22 inferred relationships involving `SlotContext` (e.g. with `.onAttributeTooltip()` and `.render()`) actually correct?**
  _`SlotContext` has 22 INFERRED edges - model-reasoned connections that need verification._
- **What connects `HEAD`, `NECKLACE`, `BACK` to the rest of the system?**
  _86 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `CurioStacksHandler` be split into smaller, more focused modules?**
  _Cohesion score 0.054336468129571575 - nodes in this community are weakly interconnected._
- **Should `Deprecated` be split into smaller, more focused modules?**
  _Cohesion score 0.058018018018018015 - nodes in this community are weakly interconnected._