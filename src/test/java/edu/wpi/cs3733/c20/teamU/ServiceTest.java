package edu.wpi.cs3733.c20.teamU;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

  @Test
  public void testGetDate() {
    Service service = new Service("11-11-11", "1", "Tom", "MEDIC");
    assertEquals(service.getDate(), "11-11-11");
  }

  @Test
  public void testGetRequestID() {
    Service service = new Service("11-11-11", "1", "Tom", "MEDIC");
    assertEquals(service.getRequestID(), "1");
  }

  @Test
  public void testGetName() {
    Service service = new Service("11-11-11", "1", "Tom", "MEDIC");
    assertEquals(service.getName(), "Tom");
  }

  @Test
  public void testGetRequestType() {
    Service service = new Service("11-11-11", "1", "Tom", "MEDIC");
    assertEquals(service.getRequestType(), "MEDIC");
  }
}
