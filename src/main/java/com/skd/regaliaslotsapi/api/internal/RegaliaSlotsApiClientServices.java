package com.skd.regaliaslotsapi.api.internal;

import org.jetbrains.annotations.ApiStatus;
import com.skd.regaliaslotsapi.api.internal.services.client.IRegaliaSlotsApiClientExtensions;

@ApiStatus.Internal
public class RegaliaSlotsApiClientServices {

  public static final IRegaliaSlotsApiClientExtensions EXTENSIONS =
      RegaliaSlotsApiServices.load(IRegaliaSlotsApiClientExtensions.class);
}
