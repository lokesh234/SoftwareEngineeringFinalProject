package edu.wpi.cs3733.c20.teamU;
import com.github.prominence.openweathermap.api.exception.DataNotFoundException;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherBoiTest {
  @Test
  public void testGetForecasts() throws InvalidAuthTokenException, DataNotFoundException {
    WeatherBoi weatherBoi = new WeatherBoi();
    assertEquals(weatherBoi.getForecasts().size(), 40);
  }
}
