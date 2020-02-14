package edu.wpi.cs3733.c20.teamU;

import edu.wpi.cs3733.c20.teamU.Database.Database;

public class Main {

  public static void main(String[] args) {
    Database.UDBInitializer();
    App.launch(App.class, args);
  }
}
