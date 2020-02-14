package edu.wpi.cs3733.c20.teamU;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import lombok.SneakyThrows;
import net.kurobako.gesturefx.GesturePane;


public class startController {

  @FXML
  Button start;
  long startTime;
  long currentTime = 0;
  boolean didEscape = false;

  Thread startT = new Thread(new Runnable() {
    @Override
    public void run() {
      Runnable runTask = new Runnable() {
        @Override
        public void run() {
          currentTime = System.currentTimeMillis();
          if ((currentTime - startTime) > 5000) {
//            didEscape = false;
//            setDaemon(false);
            openHomeScreen1();
          }
        }
      };
        while (true) {
          try {
            Thread.sleep(1);
          } catch (InterruptedException ex) {
          }
          Platform.runLater(runTask);

        }
    }

  });
//    Thread startT = new Thread(
//        () ->
//        {
//            System.out.println(System.currentTimeMillis());
////            long currentTime = System.currentTimeMillis();
////            if ((currentTime - startTime) > 5000) {
////                Platform.runLater(this::openHomeScreen1);
////            }
//        });

//  public void time(long currentTimetime) throws InterruptedException {
//    startT.sleep(currentTimetime);
//  }

  @FXML
  private void openHomeScreen(ActionEvent event) {
    App.getPrimaryStage().addEventHandler(MOUSE_MOVED, e -> {
//      startT.interrupt();
    });
    App.getPrimaryStage().setScene(App.getHomeScene());
    startTime = System.currentTimeMillis();
    System.out.println(startTime);
    didEscape = true;

  }

  private void openHomeScreen1() {
    App.getPrimaryStage().setScene(App.getStartScene());
  }

  @FXML
  private void initialize() {
//        MapGes.setOnMouseClicked(e -> {
//            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
//                Point2D pivotOnTarget = MapGes.targetPointAt(new Point2D(e.getX(), e.getY()))
//                    .orElse(MapGes.targetPointAtViewportCentre());
//                // increment of scale makes more sense exponentially instead of linearly
//                MapGes.animate(Duration.millis(200))
//                    .interpolateWith(Interpolator.EASE_BOTH)
//                    .zoomBy(MapGes.getCurrentScale(), pivotOnTarget);
//            }
//        });
    startT.setDaemon(true);
    startT.start();
  }

}
