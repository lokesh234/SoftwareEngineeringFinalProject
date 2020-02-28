package edu.wpi.cs3733.c20.teamU;


import Entity2.FoodRequest;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.awt.Color;

public class Main {

  public static void main(String[] args) {
    DatabaseWrapper.Initializer();
    App.launch(App.class, args);
  }
}
