package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;

import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
  @FXML private Label tempCel;
  @FXML private JFXButton refresh;
  @FXML private JFXButton back;
  @FXML private Label lastUpdate;
  @FXML private ImageView now;
  @FXML private ImageView day1;
  @FXML private ImageView day2;
  @FXML private ImageView day3;
  @FXML private ImageView day4;
  @FXML private Label hi1, low1, hi2, low2, hi3, low3, hi4, low4;
  @FXML private Label cloud;
  private String clock;
  private WeatherBoi weatherBoi;
  private double tempC;
  private double tempF;
  private List<HourlyForecast.Forecast> weather;
  private int hr, m, s;

  //TODO: refactor this code to be less jank...meanwhile works for iteration 2

  @FXML
  public void refreshData() throws InvalidAuthTokenException, DataNotFoundException {
    weather = weatherBoi.getForecasts();
//    weatherBoi.getRequestAgain();
//    clock = weatherBoi.getWeather().getDataCalculationDate().toString();
    //    System.out.println(weather.get(6).getWeatherStates().toString());
//    System.out.println(weatherBoi.getWeather().getWeatherDescription());
    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
    now.setImage(new Image(getClass().getResource(getWeather(weather.get(0).getWeatherStates().toString())).toString()));
    day1.setImage(new Image(getClass().getResource(getWeather(weather.get(8).getWeatherStates().toString())).toString()));
    day2.setImage(new Image(getClass().getResource(getWeather(weather.get(15).getWeatherStates().toString())).toString()));
    day3.setImage(new Image(getClass().getResource(getWeather(weather.get(23).getWeatherStates().toString())).toString()));
    day4.setImage(new Image(getClass().getResource(getWeather(weather.get(31).getWeatherStates().toString())).toString()));
    lastUpdate.setText("Last updated: " + String.format("%1$02d:%2$02d:%3$02d", hr, m, s) + " EST");
    time.setText(String.format("%1$02d:%2$02d:%3$02d", hr, m, s) + " EST");
  }

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
   * @param weatherCheck
   * @return
   */
  private String getWeather(String weatherCheck) {
//    System.out.println(weatherCheck);
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
  private void returnHome() {
    App.getPopup().getContent().clear();
    App.getHome().setOpacity(1);
    App.getHome().setDisable(false);
  }
  private String getTempString(double temperature) {
    String tempCopy = Double.toString(temperature);
//    System.out.println(String.format("%.2d",tempCopy));
    return String.format("%.2s",tempCopy);
  }

  private double returnF(double cel) {
    return (9.0/5.0) * cel + 32;
  }

  @FXML
  public void initialize() throws InvalidAuthTokenException, DataNotFoundException {
    weatherBoi = new WeatherBoi();
    weather = weatherBoi.getForecasts();
    clock = weatherBoi.getWeather().getDataCalculationDate().toString();

    // returns weather information every 24 hours....
    //TODO: change weather retrieval time to be at the peak of every day, not at the moment of...iteration 3 stuff
    now.setImage(new Image(getClass().getResource(getWeather(weather.get(0).getWeatherStates().toString())).toString()));

    day1.setImage(new Image(getClass().getResource(getWeather(weather.get(8).getWeatherStates().toString())).toString()));
//    hi1.setText(getTempString(returnF(weather.get(4).getWeatherInfo().getMaximumTemperature())) + "*F");
//    low1.setText(getTempString(returnF(weather.get(8).getWeatherInfo().getMinimumTemperature())) + "*F");

    day2.setImage(new Image(getClass().getResource(getWeather(weather.get(15).getWeatherStates().toString())).toString()));
//    hi2.setText(getTempString(returnF(weather.get(15).getWeatherInfo().getMaximumTemperature())) + "*F");
//    low2.setText(getTempString(returnF(weather.get(9).getWeatherInfo().getMinimumTemperature())) + "*F");

    day3.setImage(new Image(getClass().getResource(getWeather(weather.get(23).getWeatherStates().toString())).toString()));
//    hi3.setText(getTempString(returnF(weather.get(23).getWeatherInfo().getMaximumTemperature())) + "*F");
//    low3.setText(getTempString(returnF(weather.get(16).getWeatherInfo().getMinimumTemperature())) + "*F");

    day4.setImage(new Image(getClass().getResource(getWeather(weather.get(31).getWeatherStates().toString())).toString()));
//    hi4.setText(getTempString(returnF(weather.get(31).getWeatherInfo().getMaximumTemperature())) + "*F");
//    low4.setText(getTempString(returnF(weather.get(24).getWeatherInfo().getMinimumTemperature())) + "*F");

    tempC = weatherBoi.getWeather().getTemperature();
    tempF = returnF(tempC);
    temp.setText(String.format("%.2s", Double.toString(tempF)) + "*F");
    tempCel.setText(String.format("%.4s", Double.toString(tempC)) + "*C");
    cloud.setText(weatherBoi.getWeather().getClouds().toString());
    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
    lastUpdate.setText("Last updated: " + String.format("%1$02d:%2$02d:%3$02d", hr, m, s) + " EST");
    time.setText(String.format("%1$02d:%2$02d:%3$02d", hr, m, s) + " EST");
    //    startT.setDaemon(true);
    //    startT.start();
  }
}
