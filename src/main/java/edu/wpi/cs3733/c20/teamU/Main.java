package edu.wpi.cs3733.c20.teamU;

import java.util.Timer;
import java.util.TimerTask;
import javax.xml.crypto.Data;
import java.util.Date;

public class Main {

  public static void main(String[] args) {
    Database.UDBInitializer();
    App.launch(App.class, args);
  }
}
