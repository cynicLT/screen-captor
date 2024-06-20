package org.cynic.domain;

import java.awt.*;

public final class Context {
  private Point start;
  private Boolean isPressed = Boolean.FALSE;

  public Point getStart() {
    return start;
  }

  public void setStart(Point start) {
    this.start = start;
  }

  public Boolean getPressed() {
    return isPressed;
  }

  public void setPressed(Boolean pressed) {
    isPressed = pressed;
  }
}
