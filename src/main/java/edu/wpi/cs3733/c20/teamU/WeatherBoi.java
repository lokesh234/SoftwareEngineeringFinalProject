package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.constants.Accuracy;
import com.github.prominence.openweathermap.api.constants.Language;
import com.github.prominence.openweathermap.api.constants.Unit;
import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import com.github.prominence.openweathermap.api.model.response.HourlyForecast;
import com.github.prominence.openweathermap.api.model.response.HourlyForecast.Forecast;
import com.github.prominence.openweathermap.api.model.response.Weather;
import com.github.prominence.openweathermap.api.*;

import java.util.List;

public class WeatherBoi {
  private String apiKey = "a697a406b377b5a3c6c25ac287a60bde";
  private OpenWeatherMapManager openWeatherMapManager;
  private WeatherRequester weatherRequester;
  private Weather response;

  public WeatherBoi() throws InvalidAuthTokenException, DataNotFoundException {
    this.openWeatherMapManager = new OpenWeatherMapManager(apiKey);
    this.weatherRequester = openWeatherMapManager.getWeatherRequester();
    this.response = weatherRequester.setLanguage(Language.ENGLISH).setUnitSystem(Unit.METRIC_SYSTEM).setAccuracy(Accuracy.ACCURATE).getByCityId("4956184");
  }

  /**
   * to request weather information again
   * @throws InvalidAuthTokenException
   * @throws DataNotFoundException
   */
  public void getRequestAgain() {
    try {
      this.openWeatherMapManager = new OpenWeatherMapManager(apiKey);
      this.weatherRequester = openWeatherMapManager.getWeatherRequester();
      this.response = weatherRequester.setLanguage(Language.ENGLISH).setUnitSystem(Unit.METRIC_SYSTEM).setAccuracy(Accuracy.ACCURATE).getByCityId("4956184");
    } catch (InvalidAuthTokenException | DataNotFoundException e) {
      System.out.println("getRequest() has failed!\n");
      e.printStackTrace();
    }
  }

  /**
   * function returns the forecasts of the next 5 days
   * @return list of Forecast
   * @throws InvalidAuthTokenException wrong API key
   * @throws DataNotFoundException improper location
   */
  public List<Forecast> getForecasts() throws InvalidAuthTokenException, DataNotFoundException {
    HourlyForecastRequester forecastRequester = openWeatherMapManager.getForecastRequester();
    HourlyForecast forecast = forecastRequester.setLanguage(Language.ENGLISH).setUnitSystem(Unit.METRIC_SYSTEM).setAccuracy(Accuracy.ACCURATE).getByCityId("4956184");
    return forecast.getForecasts();
  }

  /**
   * getter function to get the temperature
   * @return string of temperature
   */
  public String getTemp() {
    return Float.toString(response.getTemperature()) + " " + response.getTemperatureUnit();
  }

  /**
   * getter function to return weather
   * @return Weather response
   */
  public Weather getWeather() { return this.response; }

}
