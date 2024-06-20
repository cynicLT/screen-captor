package org.cynic.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary {
  User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

  // LoadCursor function loads the specified cursor resource from the executable (.exe) file associated with an application instance.
  Pointer LoadCursor(Pointer hInstance, int lpCursorName);

  // SetCursor function sets the cursor shape.
  Pointer SetCursor(Pointer hCursor);

  // Predefined cursor IDs
  int IDC_ARROW = 32512;
  int IDC_HAND = 32649;
}