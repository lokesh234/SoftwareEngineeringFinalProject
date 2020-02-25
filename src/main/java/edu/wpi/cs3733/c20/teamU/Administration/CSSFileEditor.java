package edu.wpi.cs3733.c20.teamU.Administration;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class CSSFileEditor {
  String path;

  public CSSFileEditor(String path) {
    this.path = path;
  }

  public boolean createCSS() throws IOException {
    File file = new File(path);
    System.out.println(file.exists());
//    if (file.exists()) {
        Path FROM = Paths.get(path);
        Path TO = Paths.get(getClass().getResource("/light_theme").toString());
      System.out.println(FROM);
      System.out.println(TO);
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(FROM, TO, options);
        return true;
    //    }
    //    System.out.println("here");
    //    return false;
  }
}
