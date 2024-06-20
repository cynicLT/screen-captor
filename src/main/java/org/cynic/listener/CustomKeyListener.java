package org.cynic.listener;

import org.cynic.domain.Context;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

public final class CustomKeyListener extends NativeKeyAdapter {
  private final Context context;

  public CustomKeyListener(Context context) {
    this.context = context;
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    context.setPressed(NativeKeyEvent.VC_CONTROL == nativeKeyEvent.getKeyCode());
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    context.setPressed(!(NativeKeyEvent.VC_CONTROL == nativeKeyEvent.getKeyCode()));
  }
}
