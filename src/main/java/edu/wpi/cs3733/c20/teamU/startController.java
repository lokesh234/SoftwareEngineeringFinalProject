package edu.wpi.cs3733.c20.teamU;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;

import com.sun.webkit.dom.KeyboardEventImpl;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.controlsfx.control.Notifications;

import java.awt.*;

public class startController {

  private volatile long startTime;
  private volatile long currentTime;
  private volatile boolean runThread = true;
  private double initialLocation = 0;
  private double newLocation = 0;
  @FXML private AnchorPane media;
  String path = startController.class.getResource("/SoftEngTutorial1.mp4").toString();
  MediaView mediaView;

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
//        for(int i = 0; i < media.getChildren().size(); i++) {
//          System.out.println(i);
//          System.out.println(media.getChildren().get(i).getId());
//        }
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
  }

  private void addToNode(javafx.scene.Node node) {
    media.getChildren().add(node);
  }

  private void removeFromNode(javafx.scene.Node node) {
    media.getChildren().remove(node);
  }

  @FXML
  private void initialize() {
//    startT.setDaemon(true);

  }

}