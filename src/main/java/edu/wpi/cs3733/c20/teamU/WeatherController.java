package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import com.jfoenix.controls.JFXButton;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.github.prominence.openweathermap.api.model.response.HourlyForecast;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class WeatherController {

  @FXML private Label time;
  @FXML private Label temp;
  @FXML private Label tempCel;
  @FXML private Label lastUpdate;
  @FXML private Label cloud;
  @FXML private Label nextDay1, nextDay2, nextDay3, nextDay4;
  @FXML private Label hi1, low1, hi2, low2, hi3, low3, hi4, low4;
  @FXML private ImageView now, day1, day2, day3, day4;
  @FXML private JFXButton back;
//  private Date nowInfo, day1Info, day2Info, day3Info, day4Info;
  private String clock;
  private WeatherBoi weatherBoi;
  private double tempC;
  private double tempF;
  private List<HourlyForecast.Forecast> weather;
  private int hr, m, s;

  /**
   * function to return #.##
   * @param temperature double type of temperature
   * @return formatted string of the temperature
   */
  private String getTempString(double temperature) {
    String tempCopy = Double.toString(temperature);
    return String.format("%.2s",tempCopy);
  }

  /**
   * function gets date month day, year
   * @return ex. March 20, 2020
   */
  private String getDate() {
    int year = LocalDateTime.now().getYear();
    int day = LocalDateTime.now().getDayOfMonth();
    String month =  LocalDateTime.now().getMonth().toString().substring(0, 1);
    String monthL = LocalDateTime.now().getMonth().toString().substring(1).toLowerCase();
    return month + monthL + " " + day + ", " + year;
  }

  private Effect setEffect(int temperature) {
    DropShadow dropShadow = new DropShadow();
    if(temperature < 20) dropShadow.setColor(Color.ALICEBLUE);
    else if(temperature >= 20 && temperature < 50) dropShadow.setColor(Color.AQUA);
    else if(temperature >=50 && temperature < 70) dropShadow.setColor(Color.BLUE);
    else if(temperature >= 70 && temperature < 85) dropShadow.setColor(Color.YELLOW);
    else dropShadow.setColor(Color.CRIMSON);
    return dropShadow;
  }

  /**
   * converts from Celsius to Fahrenheit
   * @param cel double type of celsius
   * @return double type of Fahrenheit
   */
  private double returnF(double cel) {
    return (9.0/5.0) * cel + 32;
  }

  /**
   * function to return back to home scene
   */
  @FXML
  private void returnHome() {
    App.getWeatherPop().getContent().clear();
    App.getHome().setOpacity(1);
    App.getHome().setDisable(false);
  }

  @FXML
  public void initialize() throws InvalidAuthTokenException, DataNotFoundException {
    weatherBoi = new WeatherBoi();
    weather = weatherBoi.getForecasts();
    clock = weatherBoi.getWeather().getDataCalculationDate().toString();

//    System.out.println(weather.get(8).getWeatherStates().toString());
    now.setImage(new Image(App.class.getResource(getWeather(weather.get(0).getWeatherStates().toString())).toString()));

//   day1.setImage(new Image(App.class.getResource(getWeather(weather.get(8).getWeatherStates().toString())).toString()));
 //   hi1.setText(getTempString(returnF(weather.get(4).getWeatherInfo().getMaximumTemperature())) + "\u00B0" + "F");
 //   low1.setText(getTempString(returnF(weather.get(8).getWeatherInfo().getMinimumTemperature())) + "\u00B0" + "F");
 //   day1.setEffect(setEffect((int)returnF(weather.get(4).getWeatherInfo().getMaximumTemperature())));

//    day2.setImage(new Image(App.class.getResource(getWeather(weather.get(15).getWeatherStates().toString())).toString()));
 //   hi2.setText(getTempString(returnF(weather.get(15).getWeatherInfo().getMaximumTemperature())) + "\u00B0" + "F");
  //  low2.setText(getTempString(returnF(weather.get(9).getWeatherInfo().getMinimumTemperature())) + "\u00B0" + "F");
  //  day2.setEffect(setEffect((int)returnF(weather.get(15).getWeatherInfo().getMaximumTemperature())));

    day3.setImage(new Image(App.class.getResource(getWeather(weather.get(23).getWeatherStates().toString())).toString()));
    hi3.setText(getTempString(returnF(weather.get(23).getWeatherInfo().getMaximumTemperature())) + "\u00B0" + "F");
    low3.setText(getTempString(returnF(weather.get(16).getWeatherInfo().getMinimumTemperature())) + "\u00B0" + "F");
    day3.setEffect(setEffect((int)returnF(weather.get(23).getWeatherInfo().getMaximumTemperature())));

    day4.setImage(new Image(App.class.getResource(getWeather(weather.get(31).getWeatherStates().toString())).toString()));
    hi4.setText(getTempString(returnF(weather.get(31).getWeatherInfo().getMaximumTemperature())) + "\u00B0" + "F");
    low4.setText(getTempString(returnF(weather.get(24).getWeatherInfo().getMinimumTemperature())) + "\u00B0" + "F");
    day4.setEffect(setEffect((int)returnF(weather.get(4).getWeatherInfo().getMaximumTemperature())));

    tempC = weatherBoi.getWeather().getTemperature();
    tempF = returnF(tempC);
    now.setEffect(setEffect((int)tempF));
    temp.setText(String.format("%.2s", tempF) + "\u00B0" + "F");
    tempCel.setText(String.format("%.4s", tempC) + "\u00B0" + "C");

    cloud.setText(weatherBoi.getWeather().getClouds().toString());
    nextDay1.setText(weather.get(8).getDataCalculationDate().toString().substring(0, 3));
    nextDay2.setText(weather.get(15).getDataCalculationDate().toString().substring(0, 3));
    nextDay3.setText(weather.get(23).getDataCalculationDate().toString().substring(0, 3));
    nextDay4.setText(weather.get(31).getDataCalculationDate().toString().substring(0, 3));

    time.setText(getDate());
    time.setEffect(new Glow());
    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
    lastUpdate.setText("Last updated: " + String.format("%1$02d:%2$02d:%3$02d", hr, m, s) + " EST");
  }

  /**
   * function to get the directory based on the conditions of the weather
   * @param weatherCheck String of the weather condition
   * @return String path directory
   */
  private String getWeather(String weatherCheck) {
//    System.out.println(weatherCheck);
    switch (weatherCheck) {
      case "[Weather: light rain]":
        return "/png_files/Weather_Icons/ShowerRain_Day.png";
      case "[Weather: moderate rain]":
        return "/png_files/Weather_Icons/ShowerRain_Day.png";
      case "[Weather: few clouds]":
        return "/png_files/Weather_Icons/FewClouds_Day.png";
      case "[Weather: scattered clouds]":
        return "/png_files/Weather_Icons/ScatteredClouds_Day.png";
      case "[Weather: clear sky]":
        return "/png_files/Weather_Icons/ClearSky_Day.png";
      case "[Weather: broken clouds]":
        return "/png_files/Weather_Icons/BrokenClouds_Day.png";
      case "[Weather: shower rain]":
        return "/png_files/Weather_Icons/ShowerRain_Day.png";
      case "[Weather: rain]":
        return "/png_files/Weather_Icons/Rain.png";
      case "[Weather: thunderstorm]":
        return "/png_files/Weather_Icons/Thunderstorm.png";
      case "[Weather: snow]":
        return "/png_files/Weather_Icons/Snow_Day.png";
      case "[Weather: light snow]":
        return "/png_files/Weather_Icons/Snow_Day.png";
      case "[Weather: overcast clouds]":
        return "/png_files/Weather_Icons/FewClouds_Day.png";
      case "[Weather: heavy intensity rain]":
        return "/png_files/Weather_Icons/Rain.png";
      default:
        return "noWeather";
    }
  }
}
