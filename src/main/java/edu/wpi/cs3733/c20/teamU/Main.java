package edu.wpi.cs3733.c20.teamU;


import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;

import java.net.ProtocolException;

public class Main {
    public static void main(String[] args) {
    DatabaseWrapper.Initializer();
    //App.launch(App.class, args);
  }
}
