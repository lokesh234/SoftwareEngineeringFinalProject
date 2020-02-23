package edu.wpi.cs3733.c20.teamU;


import Entity2.FoodRequest;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;

public class Main {

  public static void main(String[] args) {
    DatabaseWrapper.Initializer();
    App.launch(App.class, args);
  }
}
