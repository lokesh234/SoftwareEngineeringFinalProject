package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import edu.wpi.cs3733.c20.teamU.Database.Database;

import java.net.ProtocolException;

public class Main {

  static WeatherBoi boi = new WeatherBoi();
  public static void main(String[] args) throws ProtocolException {
    Database.UDBInitializer();
    try {
      boi.getRequest();

    } catch (InvalidAuthTokenException | DataNotFoundException e) {
      e.printStackTrace();
    }
    App.launch(App.class, args);
  }
}
