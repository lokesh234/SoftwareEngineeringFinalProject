package edu.wpi.cs3733.c20.teamU;


import Entity2.FoodRequest;
import edu.wpi.cs3733.c20.teamU.Administration.AdministrationWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.awt.Color;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {

  public static void main(String[] args) throws MalformedURLException {
    DatabaseWrapper.Initializer();
    AdministrationWrapper.ColorInitializer();
    App.launch(App.class, args);
  }
}
