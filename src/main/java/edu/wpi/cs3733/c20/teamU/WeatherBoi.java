package edu.wpi.cs3733.c20.teamU;

import com.github.prominence.openweathermap.api.constants.Accuracy;
import com.github.prominence.openweathermap.api.constants.Language;
import com.github.prominence.openweathermap.api.constants.Unit;
import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import com.github.prominence.openweathermap.api.model.response.Weather;
import lombok.var;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import com.alibaba.fastjson.*;
import com.github.prominence.openweathermap.api.*;

public class WeatherBoi {
  private String apiKey = "a697a406b377b5a3c6c25ac287a60bde";
  private OpenWeatherMapManager openWeatherMapManager;

  private static HttpURLConnection con;

  public WeatherBoi() {}

  public void getRequest() throws InvalidAuthTokenException, DataNotFoundException {
    OpenWeatherMapManager openWeatherMapManager = new OpenWeatherMapManager(apiKey);
    WeatherRequester weatherRequester = openWeatherMapManager.getWeatherRequester();
    Weather response = weatherRequester.setLanguage(Language.ENGLISH).setUnitSystem(Unit.METRIC_SYSTEM).setAccuracy(Accuracy.ACCURATE).getByCityId("4956184");
    System.out.println(response.getCityName() + response.getDataCalculationDate());

  }
}
