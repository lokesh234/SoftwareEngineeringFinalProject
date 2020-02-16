package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import edu.wpi.cs3733.c20.teamU.Database.Database;

import java.net.ProtocolException;

public class Main {

  public static void main(String[] args)
      throws InvalidAuthTokenException, DataNotFoundException {
//    WeatherBoi boi = new WeatherBoi(); // will be unblocked once the UI is up
    Database.UDBInitializer();
    App.launch(App.class, args);
  }
}
