package org.cynic;

import org.cynic.domain.ApplicationException;
import org.cynic.domain.Context;
import org.cynic.listener.CustomKeyListener;
import org.cynic.listener.CustomNativeMouseListener;
import org.cynic.service.CaptorService;
import org.cynic.service.OcrService;
import org.jnativehook.GlobalScreen;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Application {


  public static void main(String[] args) {
    try {
      SLF4JBridgeHandler.removeHandlersForRootLogger();
      SLF4JBridgeHandler.install();

      Context context = new Context();
      CaptorService captorService = new CaptorService();
      OcrService ocrService = new OcrService("eng+kaz+lit+rus");

      GlobalScreen.registerNativeHook();

      GlobalScreen.addNativeMouseListener(
              new CustomNativeMouseListener(
                      context,
                      captorService,
                      ocrService
              )
      );
      GlobalScreen.addNativeKeyListener(
              new CustomKeyListener(
                      context
              )
      );
    } catch (Exception e) {
      throw new ApplicationException("error.register-listener", e);
    }
  }
}
