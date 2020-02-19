package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.github.prominence.openweathermap.api.model.response.HourlyForecast;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;

public class WeatherController {

  @FXML private Label time;
  @FXML private Label temp;
  @FXML private ImageView now;
  @FXML private ImageView day1;
  @FXML private ImageView day2;
  @FXML private ImageView day3;
  @FXML private ImageView day4;
  @FXML private ImageView day5;
  private WeatherBoi weatherBoi;
  private List<HourlyForecast.Forecast> weather;

  public void setWeatherFields() throws InvalidAuthTokenException, DataNotFoundException {
    weather = weatherBoi.getForecasts();
    //    System.out.println(weather.get(6).getWeatherStates().toString());
//    System.out.println(weatherBoi.getWeather().getWeatherDescription());

//    now.setImage(new Image(getClass().getResource(getWeather(weather.get(0).getWeatherStates().toString())).toString()));
//    day1.setImage(new Image(getClass().getResource(getWeather(weather.get(0).getWeatherStates().toString())).toString()));
//    day2.setImage(new Image(getClass().getResource(getWeather(weather.get(1).getWeatherStates().toString())).toString()));
//    day3.setImage(new Image(getClass().getResource(getWeather(weather.get(2).getWeatherStates().toString())).toString()));
//    day4.setImage(new Image(getClass().getResource(getWeather(weather.get(3).getWeatherStates().toString())).toString()));
//    day5.setImage(new Image(getClass().getResource(getWeather(weather.get(4).getWeatherStates().toString())).toString()));
  }
  //  private int hr;
  //  private int m;
  //  private int s;

  //  Thread startT = new Thread(new Runnable() {
  //    @Override
  //    public void run() {
  //      Runnable incrementTime = new Runnable() {
  //        @Override
  //        public void run() {
  ////          System.out.println(setTime());
  //          time.setText(setTime());
  //        }
  //      };
  //      while (true) {
  //        try {
  //          Thread.sleep(1000);
  //        } catch (InterruptedException ex) {
  //            ex.printStackTrace();
  //        }
  //        Platform.runLater(incrementTime);
  //      }
  //    }
  //  });

  //  /**
  //   * increments the time by 1
  //   * Adjusts for hours, mins, secs
  //   * @return string of time HH : MM :: SS
  //   */
  //  private String setTime() {
  //    s++;
  //    if(s >= 60) {
  //      s = 0;
  //      m++;
  //      if(m >= 60) {
  //        m = 0;
  //        hr++;
  //        if(hr >= 24) {
  //          hr = 0;
  //        }
  //      }
  //    }
  //    if(hr >= 12) return String.format("%1$02d : %2$02d : %3$02d PM", hr, m, s);
  //    else return String.format("%1$02d : %2$02d : %3$02d AM", hr, m, s);
  //  }

  /**
   * TODO: make more intuitive with system
   * @param weatherCheck
   * @return
   */
  private String getWeather(String weatherCheck) {
    switch (weatherCheck) {
      case "[Weather: light rain]":
        return "/png_files/Weather_Icons/Day_LightRain.png";
      case "[Weather: moderate rain]":
        return "/png_files/Weather_Icons/Day_LightRain.png";
      case "[Weather: few clouds]":
        return "/png_files/Weather_Icons/Cloudy_Day.png";
      case "[Weather: scattered clouds]":
        return "/png_files/Weather_Icons/Cloudy_Day.png";
      case "[Weather: clear sky]":
        return "/png_files/Weather_Icons/Day.png";
      case "[Weather: broken clouds]":
        return "/png_files/Weather_Icons/Cloudy_Day.png";
      case "[Weather: shower rain]":
        return "/png_files/Weather_Icons/Day_LightRain.png";
      case "[Weather: rain]":
        return "/png_files/Weather_Icons/HeavyRain.png";
      case "[Weather: thunderstorm]":
        return "/png_files/Weather_Icons/HeavyRain.png";
      case "[Weather: snow]":
        return "/png_files/Weather_Icons/Day_snow.png";
      case "[Weather: overcast clouds]":
        return "/png_files/Weather_Icons/Cloudy_Day.png";
      default:
        return "noWeather";
    }
  }

  @FXML
  public void initialize() throws InvalidAuthTokenException, DataNotFoundException {
        weatherBoi = new WeatherBoi();
    //    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    //    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    //    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
    //    startT.setDaemon(true);
    //    startT.start();
  }
}
