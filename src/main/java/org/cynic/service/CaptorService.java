package org.cynic.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cynic.domain.ApplicationException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public final class CaptorService {
  private final Robot robot;

  public CaptorService() {
    try {
      this.robot = new Robot();
    } catch (AWTException e) {
      throw new ApplicationException("error.captor-service.create",
              e,
              Map.entry("message", ExceptionUtils.getRootCauseMessage(e))
      );
    }
  }

  public BufferedImage capture(Integer x, Integer y, Integer width, Integer height) {
    Rectangle rectangle = new Rectangle(x, y, width, height);

    return robot.createScreenCapture(rectangle);
  }
}
