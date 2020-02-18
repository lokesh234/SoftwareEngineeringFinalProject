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

  @FXML
  private void openHomeScreen(ActionEvent event) {
    App.getPrimaryStage().addEventHandler(MOUSE_MOVED, e -> {
      App.change(false);
    });
    App.setTime(System.currentTimeMillis());
    App.change(true);
    App.getPrimaryStage().setScene(App.getHomeScene());
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
  }

}
