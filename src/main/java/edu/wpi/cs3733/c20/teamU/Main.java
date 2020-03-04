package edu.wpi.cs3733.c20.teamU;


import Entity2.FoodRequest;
import edu.wpi.cs3733.c20.teamU.Administration.AdministrationWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import lombok.val;

import java.awt.Color;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws MalformedURLException {
    DatabaseWrapper.Initializer();
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<String> arr2 = new ArrayList<>();
    arr.add("STRING");
    arr2.add("x");
    DatabaseWrapper.generateNewDatabase("TESTX", arr, "xx");
    DatabaseWrapper.populateNewDatabase("TESTX", arr2);
    DatabaseWrapper.delType("TESTX");
    //AdministrationWrapper.ColorInitializer();
    //App.launch(App.class, args);
  }
}
