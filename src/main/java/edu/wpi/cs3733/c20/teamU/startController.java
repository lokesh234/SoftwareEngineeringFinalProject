package edu.wpi.cs3733.c20.teamU;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class startController {

  private volatile long startTime;
  private volatile long currentTime;
  private volatile boolean runThread = true;

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
//            System.out.println(currentTime - startTime);
            if ((currentTime - startTime)
                > App
                .getTimeoutValue()) { // will go to openStartScene if the screen has not been touched within 60 secs
              App.getPrimaryStage().setScene(App.getStartScene());
              clearPop();
              runThread = false;
            }
          }
        };
        while (runThread) {
          try {
            Thread.sleep(1000);
//            System.out.println(App.getTimeoutValue());
          } catch (InterruptedException ex) {
            ex.printStackTrace();
          }
          Platform.runLater(runTask);
        }
      }
    });
    App.getPrimaryStage().addEventHandler(MOUSE_MOVED, eventM -> {
      startTime = System.currentTimeMillis();
//      System.out.println(startTime);
      if (App.getLoadedAdminGraph()) {
        App.getAdminNodeScene().addEventHandler(MOUSE_MOVED, event1 -> {
          if (!runThread) {
            App.getAdminNodeScene().removeEventHandler(MOUSE_MOVED, null);
          }
          startTime = System.currentTimeMillis();
        });
      }
      if (App.getLoadedPathFinding()) {
        App.getPathScene().addEventHandler(MOUSE_MOVED, event2 -> {
          if (!runThread) {
            App.getPathScene().removeEventHandler(MOUSE_MOVED, null);
          }
          startTime = System.currentTimeMillis();
        });
      }
      if (App.getLoadedAdminRequest()) {
        App.getAdminScene().addEventHandler(MOUSE_MOVED, event3 -> {
          if (!runThread) {
            App.getAdminScene().removeEventHandler(MOUSE_MOVED, null);
          }
          startTime = System.currentTimeMillis();
        });
      }
      if (App.getLoadedAdminEmployee()) {
        App.getAdminScene().addEventHandler(MOUSE_MOVED, event3 -> {
          if (!runThread) {
            App.getAdminScene().removeEventHandler(MOUSE_MOVED, null);
          }
          startTime = System.currentTimeMillis();
        });
      }
    });

    App.getHomeScene().addEventFilter(MOUSE_MOVED, event -> {
      if (!runThread) {
        App.getHomeScene().removeEventHandler(MOUSE_MOVED, null);
      }
      startTime = System.currentTimeMillis();
    });
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
  }
  @FXML
  private void initialize() {
//    startT.setDaemon(true);

  }

}