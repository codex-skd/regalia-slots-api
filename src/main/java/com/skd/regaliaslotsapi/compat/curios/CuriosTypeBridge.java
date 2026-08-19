package com.skd.regaliaslotsapi.compat.curios;

import java.util.LinkedHashMap;
import java.util.Map;
import com.skd.regaliaslotsapi.api.type.ISlotType;
import com.skd.regaliaslotsapi.api.type.inventory.ICurioStacksHandler;
import com.skd.regaliaslotsapi.api.type.inventory.IDynamicStackHandler;

/**
 * Converts between Regalia Slots API's own types and the copied Curios API types
 * (under {@code top.theillusivec4.curios}) so third-party mods compiled against the
 * real Curios API can interoperate with Regalia's data without either side duplicating
 * storage - every conversion here wraps the original Regalia instance rather than copying it.
 */
public final class CuriosTypeBridge {

  private CuriosTypeBridge() {
  }

  public static com.skd.regaliaslotsapi.api.SlotContext toRegalia(
      top.theillusivec4.curios.api.SlotContext ctx) {
    if (ctx == null) {
      return null;
    }
    return new com.skd.regaliaslotsapi.api.SlotContext(
        ctx.identifier(), ctx.entity(), ctx.index(), ctx.cosmetic(), ctx.visible());
  }

  public static top.theillusivec4.curios.api.SlotContext toCurios(
      com.skd.regaliaslotsapi.api.SlotContext ctx) {
    if (ctx == null) {
      return null;
    }
    return new top.theillusivec4.curios.api.SlotContext(
        ctx.identifier(), ctx.entity(), ctx.index(), ctx.cosmetic(), ctx.visible());
  }

  public static top.theillusivec4.curios.api.SlotResult toCurios(
      com.skd.regaliaslotsapi.api.SlotResult result) {
    if (result == null) {
      return null;
    }
    return new top.theillusivec4.curios.api.SlotResult(
        toCurios(result.slotContext()), result.stack());
  }

  public static com.skd.regaliaslotsapi.api.common.DropRule toRegalia(
      top.theillusivec4.curios.api.common.DropRule rule) {
    return com.skd.regaliaslotsapi.api.common.DropRule.values()[rule.ordinal()];
  }

  public static top.theillusivec4.curios.api.common.DropRule toCurios(
      com.skd.regaliaslotsapi.api.common.DropRule rule) {
    return top.theillusivec4.curios.api.common.DropRule.values()[rule.ordinal()];
  }

  public static top.theillusivec4.curios.api.type.ISlotType wrap(ISlotType slotType) {
    if (slotType == null) {
      return null;
    }
    return new SlotTypeAdapter(slotType);
  }

  public static Map<String, top.theillusivec4.curios.api.type.ISlotType> wrapSlotTypes(
      Map<String, ISlotType> slotTypes) {
    Map<String, top.theillusivec4.curios.api.type.ISlotType> result = new LinkedHashMap<>();
    slotTypes.forEach((id, slotType) -> result.put(id, wrap(slotType)));
    return result;
  }

  public static top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler wrap(
      IDynamicStackHandler handler) {
    if (handler == null) {
      return null;
    }
    return new DynamicStackHandlerAdapter(handler);
  }

  public static top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler wrap(
      ICurioStacksHandler handler) {
    if (handler == null) {
      return null;
    }
    return new StacksHandlerAdapter(handler);
  }

  public static Map<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> wrapStacksHandlers(
      Map<String, ICurioStacksHandler> handlers) {
    Map<String, top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler> result =
        new LinkedHashMap<>();
    handlers.forEach((id, handler) -> result.put(id, wrap(handler)));
    return result;
  }
}
