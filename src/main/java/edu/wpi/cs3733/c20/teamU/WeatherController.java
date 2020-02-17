package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WeatherController {

  @FXML private Label time;
  @FXML private Label temp;
  private WeatherBoi weatherBoi;
  private int hr;
  private int m;
  private int s;

  Thread startT = new Thread(new Runnable() {
    @Override
    public void run() {
      Runnable incrementTime = new Runnable() {
        @Override
        public void run() {
//          updateTime(); // uncomment once the UI is better
          //System.out.println(setTime());
        }
      };
      while (true) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Platform.runLater(incrementTime);
      }
    }
  });

  private void updateTime() {
    time.setText(setTime());
  }

  private String setTime() {
    s++;
    if(s >= 60) {
      s = 0;
      m++;
      if(m >= 60) {
        m = 0;
        hr++;
        if(hr >= 24) {
          hr = 0;
        }
      }
    }
    return String.format("%1$02d : %2$02d : %3$02d", hr, m, s);
  }

  @FXML
  public void initialize() throws InvalidAuthTokenException, DataNotFoundException {
//    weatherBoi = new WeatherBoi(); //uncomment once ui is better
    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
//    System.out.println(hr + ":" + m + ":" + s);
    startT.setDaemon(true);
    startT.start();
  }
}
