package edu.wpi.cs3733.c20.teamU;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;

import java.awt.MouseInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;

public class startController {

  private volatile long startTime;
  private volatile long currentTime;
  private volatile boolean runThread = true;
  private double initialLocation = 0;
  private double newLocation = 0;

  @FXML
  private void openHomeScreen() {
    runThread = true;
    Thread startT = new Thread(new Runnable() {
      @Override
      public void run() {
        Runnable runTask = new Runnable() {
          @Override
          public void run() {
            currentTime = System.currentTimeMillis();
            if(initialLocation != newLocation) {
//              System.out.println("changed Location");
              startTime = System.currentTimeMillis();
              initialLocation = newLocation;
            }
//            System.out.println(initialLocation);
            if ((currentTime - startTime)
                > 5000 && runThread) { // will go to openStartScene if the screen has not been touched within 60 secs
              App.getPrimaryStage().setScene(App.getStartScene());
              clearPop();
              Notifications.create().text("Kiosk has timed out!").show();
              runThread = false;
//              System.out.println("hello");
            }
          }
        };
        while (runThread) {
          try {
            Thread.sleep(1000);
            newLocation = MouseInfo.getPointerInfo().getLocation().getX();
          } catch (InterruptedException ex) {
            ex.printStackTrace();
          }
          Platform.runLater(runTask);
        }
      }
    });
    initialLocation = MouseInfo.getPointerInfo().getLocation().getX();
    newLocation = MouseInfo.getPointerInfo().getLocation().getX();
    startTime = System.currentTimeMillis();
    App.getPrimaryStage().setScene(App.getHomeScene());
    App.getHome().setDisable(false);
    App.getHome().setOpacity(1);
    startT.setDaemon(true);
    startT.start();
  }

  private void clearPop() {
    App.getPopup().getContent().clear();
    App.getRequestPop().getContent().clear();
    App.getSecurityPop().getContent().clear();
    App.getMedicinePop().getContent().clear();
    App.getITPop().getContent().clear();
    App.getReligiousPop().getContent().clear();
    App.getExtTransportPop().getContent().clear();
    App.getIntTransportPop().getContent().clear();
    App.getTextDirectionsPop().getContent().clear();
    App.getSanRequestPop().getContent().clear();
    App.getLanguageSRPop().getContent().clear();
    App.getChoosePathPop().getContent().clear();
    App.getWeatherPop().getContent().clear();

  }
  @FXML
  private void initialize() {
//    startT.setDaemon(true);

  }

}