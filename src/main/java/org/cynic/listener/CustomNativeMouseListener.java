package org.cynic.listener;

import org.cynic.domain.Context;
import org.cynic.service.CaptorService;
import org.cynic.service.OcrService;
import org.jnativehook.mouse.NativeMouseAdapter;
import org.jnativehook.mouse.NativeMouseEvent;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Optional;

public final class CustomNativeMouseListener extends NativeMouseAdapter {
  private static final Integer DELTA = 50;

  private final Context context;
  private final CaptorService captorService;
  private final OcrService ocrService;

  public CustomNativeMouseListener(Context context, CaptorService captorService, OcrService ocrService) {
    this.context = context;
    this.captorService = captorService;
    this.ocrService = ocrService;
  }

  @Override
  public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
    Optional.of(context.getPressed())
            .filter(Boolean.TRUE::equals)
            .ifPresent(it -> context.setStart(nativeMouseEvent.getPoint()));
  }

  @Override
  public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
    try {
      Point end = nativeMouseEvent.getPoint();
      Optional.ofNullable(context.getStart())
              .filter(it -> isPressed())
              .filter(it -> isNotEmpty(it, end))
              .map(it -> captorService.capture(
                              Math.min(it.x, end.x),
                              Math.min(it.y, end.y),
                              Math.max(it.x, end.x) - Math.min(it.x, end.x),
                              Math.max(it.y, end.y) - Math.min(it.y, end.y)
                      )
              )
              .map(ocrService::parseImage)
              .map(StringSelection::new)
              .ifPresent(it -> Toolkit.getDefaultToolkit()
                      .getSystemClipboard()
                      .setContents(it, null)
              );
    } finally {
      context.setPressed(Boolean.FALSE);
      context.setStart(null);
    }
  }

  private boolean isPressed() {
    return context.getPressed().equals(Boolean.TRUE);
  }

  private boolean isNotEmpty(Point start, Point end) {
    return Math.absExact(start.x - end.x) * Math.absExact(start.y - end.y) > DELTA;
  }
}
