package org.cynic.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cynic.domain.ApplicationException;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public final class OcrService {
  private final Tesseract tesseract;

  public OcrService(String language) {
    tesseract = new Tesseract();

    Optional.of(getClass())
            .map(Class::getClassLoader)
            .map(it -> it.getResource("tessdata"))
            .map(URL::getPath)
            .map(it -> StringUtils.removeStart(it, "/"))
            .ifPresent(tesseract::setDatapath);

    tesseract.setLanguage(language);
  }

  public String parseImage(BufferedImage image) {

    try {
      return tesseract.doOCR(image);
    } catch (TesseractException e) {
      throw new ApplicationException("error.ocr.parse", e,
              Map.entry("message", ExceptionUtils.getRootCauseMessage(e))
      );
    }
  }
}
