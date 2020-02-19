package edu.wpi.cs3733.c20.teamU;


import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;

import java.net.ProtocolException;

public class Main {
    public static void main(String[] args) throws InvalidAuthTokenException, DataNotFoundException {
//        WeatherBoi boi = new WeatherBoi();
//    System.out.println(boi.getForecasts().get(0).getWeatherStates().get(0).toString());
//    System.out.println("YOLOSWAG\n");
    DatabaseWrapper.Initializer();
    App.launch(App.class, args);
  }
}
