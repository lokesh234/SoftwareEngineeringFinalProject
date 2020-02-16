package edu.wpi.cs3733.c20.teamU;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import javax.print.DocFlavor;
import java.io.IOException;
import java.time.Instant;
import java.util.Timer;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;

public class HomeController {

  @FXML
  private Button test;
  @FXML
  private Button navButton;
  private long startTime;
  private long currentTime;
  SecurityController securityController;
  startController tartController;
  @FXML
  private GesturePane MapGes;
  private boolean startTimer = false;
//    Long startTime = System.currentTimeMillis();

  @FXML
  private void openLoginScene(ActionEvent e) {
    App.getPopup().getContent().add(App.getLogin());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getPopup().show(App.getPrimaryStage());
  }

  @FXML
  private void openStartScene() {
        /* App.getPrimaryStage().addEventHandler(MOUSE_MOVED, e -> {
            startTime = System.currentTimeMillis();
            System.out.println(startTime);
        });
         */
    App.getPrimaryStage().setScene(App.getStartScene());
  }

  @FXML
  private void openNavScene(ActionEvent e) throws IOException {
    App.getGraph().update();
    App.getPathfindController().drawNodes();
    App.getPrimaryStage().setScene(App.getPathScene());
  }

  @FXML
  private void openHelpScene(ActionEvent e) {
    App.getSecurityPop().getContent().add(App.getSecurity());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getSecurityController().addRequest();
    App.getSecurityPop().show(App.getPrimaryStage());
  }


  @FXML
  private void openHomeScene() {
//        App.getPrimaryStage().addEventHandler(MOUSE_MOVED, e -> {
//                    startTime = System.currentTimeMillis();
//        });
    App.getPrimaryStage().setScene(App.getStartScene());
    System.out.println("Hello");
  }

  int time = 0;
  Thread startT = new Thread(new Runnable() {
    @Override
    public void run() {
      Runnable runTask = new Runnable() {
        @Override
        public void run() {
//          checkTime();
          if (App.getChange()) {
            currentTime = System.currentTimeMillis();
            System.out.println(Math.abs(currentTime - startTime));
            if ((Math.abs(currentTime - startTime)) > 5000) {
              openStartScene();
            }
          }
        }
      };
      while (true) {
        try {
//          startTime = App.getTime();
          Thread.sleep(1);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
//        startTime = App.getTime();
        if(!App.getChange()) {
          System.out.println("here");
          Platform.runLater(() -> setTime());
//          startTime = System.currentTimeMillis();
          App.change(true);
        } else Platform.runLater(runTask);

      }
    }
  });

  private void setTime() {
    startTime = System.currentTimeMillis();
  }
  @FXML
  private void checkTime(ActionEvent event) {
//    App.getPrimaryStage().addEventHandler(MOUSE_MOVED, e -> {
//      startTime = System.currentTimeMillis();
//      System.out.println(startTime);
//    });
  }

  @FXML
  private void openRequestScene(ActionEvent e) {
    App.getRequestPop().getContent().add(App.getRequest());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getRequestPop().show(App.getPrimaryStage());
  }

  @FXML
  private void initialize() {
    MapGes.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
        Point2D pivotOnTarget = MapGes.targetPointAt(new Point2D(e.getX(), e.getY()))
            .orElse(MapGes.targetPointAtViewportCentre());
        // increment of scale makes more sense exponentially instead of linearly
        MapGes.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes.getCurrentScale(), pivotOnTarget);
      }
    });
    startT.setDaemon(true);
    startT.start();
  }
}