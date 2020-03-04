package edu.wpi.cs3733.c20.teamU;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;

import com.sun.webkit.dom.KeyboardEventImpl;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class startController<startC> {

  private volatile long startTime;
  private volatile long currentTime;
  private volatile boolean runThread = true;
  private double initialLocation = 0;
  private double newLocation = 0;
  @FXML private AnchorPane media;
  @FXML private Label time;
  private int hr, s, m;
  private Thread startC;
  private boolean startClock = true;
  String path = startController.class.getResource("/SoftEngTutorial1.mp4").toString();
  MediaView mediaView;

  @FXML
  private void openHomeScreen() {
    startClock = false;
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
                > App.getTimeoutValue() && runThread) { // will go to openStartScene if the screen has not been touched within 60 secs
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
    App.getPrimaryStage().addEventHandler(KeyEvent.KEY_TYPED, event -> {
      startTime = System.currentTimeMillis();
    });
    startTime = System.currentTimeMillis();
    App.getPrimaryStage().setScene(App.getHomeScene());
    App.getHome().setDisable(false);
    App.getHome().setOpacity(1);
    startT.setDaemon(true);
    startT.start();
  }

  javafx.event.EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (!media.getChildren().isEmpty()) {
        mediaView.getMediaPlayer().stop();
        media.getChildren().remove(mediaView);
      }
    }
  };

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
    if(App.getLoginScreenController() != null) App.getLoginScreenController().getPop().getContent().clear();
    if(App.getPathfindController() != null) App.getPathfindController().clearSelect();
    mediaView = new MediaView(new MediaPlayer(new Media(path)));
    addToNode(mediaView);
    mediaView.getMediaPlayer().play();
    mediaView.getMediaPlayer().setAutoPlay(true);
    mediaView.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
    startClock = true;
    clock();
  }

  private void addToNode(javafx.scene.Node node) {
    media.getChildren().add(node);
  }

  private void removeFromNode(javafx.scene.Node node) {
    media.getChildren().remove(node);
  }

  public void clock() {
    startC =
            new Thread(
                    new Runnable() {
                      @Override
                      public void run() {
                        Runnable incrementTime =
                                new Runnable() {
                                  @Override
                                  public void run() {
                                    //          System.out.println(setTime());
                                    time.setText(setClock());
                                  }
                                };
                        while (startClock) {
                          try {
                            Thread.sleep(1000);
                          } catch (InterruptedException ex) {
                            ex.printStackTrace();
                          }
                          Platform.runLater(incrementTime);
                        }
                      }
                    });
    startC.setDaemon(true);
    startC.start();
  }

  private void setTime() {
    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
  }

  /**
   * increments the time by 1 Adjusts for hours, mins, secs
   *
   * @return string of time HH : MM :: SS
   */
  private String setClock() {
    s++;
    if (s >= 60) {
      s = 0;
      m++;
      if (m >= 60) {
        m = 0;
        hr++;
        if (hr >= 24) {
          hr = 0;
        }
      }
    }
    return String.format("%1$02d:%2$02d:%3$02d", hr, m, s);
  }

  @FXML
  private void initialize() {
    setTime();
    clock();
  }

}