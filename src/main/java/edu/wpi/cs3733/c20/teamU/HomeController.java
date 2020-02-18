package edu.wpi.cs3733.c20.teamU;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import javafx.animation.Interpolator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;

public class HomeController {

  @FXML private JFXButton login;
  @FXML private Button navButton;
  @FXML private GesturePane MapGes;
  private long startTime;
  private long currentTime;

    @FXML
    private void openLoginScene(ActionEvent e) {
        App.getPopup().getContent().add(App.getLogin());
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void openStartScene() {
        App.getPrimaryStage().setScene(App.getStartScene());
    }

    @FXML
    private void openNavScene(ActionEvent e) throws IOException {
        //App.getGraph().update();
        DatabaseWrapper.updateGraph();
        //App.getPathfindController().drawNodes();
        ServiceRequestWrapper.pathfindDrawNodes(App.getPathfindController());
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
  }

  Thread startT = new Thread(new Runnable() {
    @Override
    public void run() {
      Runnable runTask = new Runnable() {
        @Override
        public void run() {
          if (App.getChange()) {
            currentTime = System.currentTimeMillis();
//            System.out.println(currentTime - startTime);
            if ((currentTime - startTime) > 60000) { // will go to openStartScene if the screen has not been touched within 60 secs
              openStartScene();
            }
          }
        }
      };
      while (true) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        if(!App.getChange()) {
          Platform.runLater(() -> setTime());
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
//    MapGes.setOnMouseClicked(e -> {
//      if(e.isDragDetect()) setTime();
//      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
//        setTime();
//        Point2D pivotOnTarget = MapGes.targetPointAt(new Point2D(e.getX(), e.getY()))
//            .orElse(MapGes.targetPointAtViewportCentre());
//        // increment of scale makes more sense exponentially instead of linearly
//        MapGes.animate(Duration.millis(200))
//            .interpolateWith(Interpolator.EASE_BOTH)
//            .zoomBy(MapGes.getCurrentScale(), pivotOnTarget);
//      }
//    });
    startT.setDaemon(true);
    startT.start();
  }
}