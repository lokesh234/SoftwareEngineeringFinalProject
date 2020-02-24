package edu.wpi.cs3733.c20.teamU;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class HomeController {

  @FXML private JFXButton login;
  @FXML private Button navButton;
  @FXML private VBox oppo;
  @FXML private Label floorLabel;
  @FXML private Label time;
  @FXML private JFXButton weather;
  @FXML private AnchorPane N1;
  @FXML private AnchorPane N2;
  @FXML private AnchorPane N3;
  @FXML private AnchorPane N4;
  @FXML private AnchorPane N5;
  @FXML private GesturePane MapGes2;
  @FXML private GesturePane MapGes3;
  @FXML private GesturePane MapGes4;
  @FXML private GesturePane MapGes5;
  @FXML private GesturePane MapGes1;

  @FXML private AnchorPane NodesPane1, NodesPane2, NodesPane3, NodesPane4, NodesPane5;

  private WeatherController weatherController;
  private long startTime;
  private long currentTime;
  int floor = 4;
  private int hr;
  private int m;
  private int s;
  private int checker;


  public void setWeatherData(WeatherController weatherController1) {
    weatherController = weatherController1;
  }

  @FXML
  private void openLoginScene() {
    App.loadAdminRequests();
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getPopup().getContent().add(App.getLogin());
    App.getPopup().show(App.getPrimaryStage());
  }

  @FXML
  private void openStartScene() {
    App.getPrimaryStage().setScene(App.getStartScene());
  }

  @FXML
  private void openNavScene(ActionEvent e) throws IOException {
    App.loadPathfinding();
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
            if ((currentTime - startTime)
                > 60000) { // will go to openStartScene if the screen has not been touched within 60 secs
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
        if (!App.getChange()) {
          Platform.runLater(() -> setTime());
          App.change(true);
        } else {
          Platform.runLater(runTask);
        }

      }
    }
  });

  Thread startC = new Thread(new Runnable() {
    @Override
    public void run() {
      Runnable incrementTime = new Runnable() {
        @Override
        public void run() {
//          System.out.println(setTime());
          time.setText(setClock());
        }
      };
      while (true) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        Platform.runLater(incrementTime);
      }
    }
  });

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
  private void openWeather() {
    App.loadWeather();
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getWeatherPop().getContent().add(App.getWeather());
    App.getWeatherPop().show(App.getPrimaryStage());
  }

  @FXML
  private void openRequestScene(ActionEvent e) {
    App.loadAdminRequests();
    App.getRequestPop().getContent().add(App.getRequest());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getRequestPop().show(App.getPrimaryStage());
  }

  @FXML
  private void initialize() {
    NodesPane1.getChildren().add(new ImageView(App.getFloor1()));
    NodesPane2.getChildren().add(new ImageView(App.getFloor2()));
    NodesPane3.getChildren().add(new ImageView(App.getFloor3()));
    NodesPane4.getChildren().add(new ImageView(App.getFloor4()));
    NodesPane5.getChildren().add(new ImageView(App.getFloor5()));

    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
    startC.setDaemon(true);
    startC.start();
    MapGes1.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
        Point2D pivotOnTarget = MapGes1.targetPointAt(new Point2D(e.getX(), e.getY()))
            .orElse(MapGes1.targetPointAtViewportCentre());
        // increment of scale makes more sense exponentially instead of linearly
        MapGes1.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes1.getCurrentScale(), pivotOnTarget);
      }
    });
    MapGes2.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
        Point2D pivotOnTarget = MapGes2.targetPointAt(new Point2D(e.getX(), e.getY()))
            .orElse(MapGes2.targetPointAtViewportCentre());
        // increment of scale makes more sense exponentially instead of linearly
        MapGes2.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes2.getCurrentScale(), pivotOnTarget);
      }
    });
    MapGes3.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
        Point2D pivotOnTarget = MapGes3.targetPointAt(new Point2D(e.getX(), e.getY()))
            .orElse(MapGes3.targetPointAtViewportCentre());
        // increment of scale makes more sense exponentially instead of linearly
        MapGes3.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes3.getCurrentScale(), pivotOnTarget);
      }
    });
    MapGes4.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
        Point2D pivotOnTarget = MapGes4.targetPointAt(new Point2D(e.getX(), e.getY()))
            .orElse(MapGes4.targetPointAtViewportCentre());
        // increment of scale makes more sense exponentially instead of linearly
        MapGes4.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes4.getCurrentScale(), pivotOnTarget);
      }
    });
    MapGes5.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
        Point2D pivotOnTarget = MapGes5.targetPointAt(new Point2D(e.getX(), e.getY()))
            .orElse(MapGes5.targetPointAtViewportCentre());
        // increment of scale makes more sense exponentially instead of linearly
        MapGes5.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes5.getCurrentScale(), pivotOnTarget);
      }
    });
    MapGes1.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes1.getCurrentScale() - 3000, MapGes1.targetPointAtViewportCentre());
    MapGes2.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes2.getCurrentScale() - 3000, MapGes2.targetPointAtViewportCentre());
    MapGes3.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes3.getCurrentScale() - 3000, MapGes3.targetPointAtViewportCentre());
    MapGes4.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes4.getCurrentScale() - 3000, MapGes4.targetPointAtViewportCentre());
    MapGes5.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes5.getCurrentScale() - 3000, MapGes5.targetPointAtViewportCentre());
    oppo.getChildren().clear();
    oppo.getChildren().add(N4);
//    startT.setDaemon(true);
//    startT.start();
  }

  @FXML
  private void stateMachine(int floor) {
    switch (floor){
      case 1:
    oppo.getChildren().clear();
    oppo.getChildren().add(N1);
    floor = 1;
    floorLabel.setText("1");
    MapGes1.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes1.getCurrentScale() - 3000, MapGes1.targetPointAtViewportCentre());

    break;
    case 2:
    oppo.getChildren().clear();
    oppo.getChildren().add(N2);
    floor = 2;
    floorLabel.setText("2");
    MapGes2.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes2.getCurrentScale() - 30000, MapGes2.targetPointAtViewportCentre());
    break;
    case 3:
    oppo.getChildren().clear();
    oppo.getChildren().add(N3);
    floor = 3;
    floorLabel.setText("3");
    MapGes3.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes3.getCurrentScale() - 3000, MapGes3.targetPointAtViewportCentre());
    break;
    case 4:
    oppo.getChildren().clear();
    oppo.getChildren().add(N4);
    floor = 4;
    floorLabel.setText("4");
    MapGes4.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes4.getCurrentScale() - 3000, MapGes4.targetPointAtViewportCentre());
    break;
    case 5:
    oppo.getChildren().clear();
    oppo.getChildren().add(N5);
    floor = 5;
    floorLabel.setText("5");
    MapGes5.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes5.getCurrentScale() - 3000, MapGes5.targetPointAtViewportCentre());
    break;
  }
  }

  @FXML
  private void clickUp(ActionEvent e) {
    floor++;
    stateMachine(floor);
    if (floor > 5) {
      floor = 5;
    }
    checker = 1;
  }

  @FXML
  private void clickDown(ActionEvent e) {
    floor--;
    stateMachine(floor);
    if (floor < 1) {
      floor = 1;
    }
    checker = 2;
  }

  @FXML
  private void zoomIn() {
    ZoomInMachine(floor);
  }

  private int zoomCounter = 5;
  private double zoomed = 0;
  private void ZoomInMachine(int floor) {
    switch (floor) {
      case 1:
        if(zoomCounter <= 5 && zoomCounter > 0) {
          MapGes1.animate(Duration.millis(200))
                  .interpolateWith(Interpolator.EASE_BOTH)
                  .zoomBy(MapGes1.getCurrentScale(), MapGes1.targetPointAtViewportCentre());
          zoomed = MapGes1.getCurrentScale();
          zoomCounter--;
        }
        break;
      case 2:
        MapGes2.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes2.getCurrentScale(), MapGes2.targetPointAtViewportCentre());
        zoomed = MapGes2.getCurrentScale();
        zoomCounter--;
        break;
      case 3:
        MapGes3.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes3.getCurrentScale(), MapGes3.targetPointAtViewportCentre());
        zoomed = MapGes3.getCurrentScale();
        zoomCounter--;
        break;
      case 4:
        MapGes4.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes4.getCurrentScale(), MapGes4.targetPointAtViewportCentre());
        zoomed = MapGes4.getCurrentScale();
        zoomCounter--;
        break;
      case 5:
        MapGes5.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes5.getCurrentScale(), MapGes5.targetPointAtViewportCentre());
        zoomed = MapGes5.getCurrentScale();
        zoomCounter--;
        break;
    }
  }

  private void ZoomOutMachine(int floor) {
    switch (floor) {
      case 1:
        if(zoomCounter < 5 && zoomCounter >= 0 ) {
//          System.out.println(MapGes1.getCurrentScale());
          MapGes1.animate(Duration.millis(200))
                  .interpolateWith(Interpolator.EASE_BOTH)
                  .zoomTo(zoomed/2, MapGes1.targetPointAtViewportCentre());
          zoomed /= 2;
          //System.out.println(zoomed/2);
          zoomCounter++;
        }
//        System.out.println(MapGes1.getCurrentScale() - 5);
        break;
      case 2:
        MapGes2.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed/2, MapGes2.targetPointAtViewportCentre());
        zoomed /= 2;
        zoomCounter++;
        break;
      case 3:
        MapGes3.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed/2, MapGes3.targetPointAtViewportCentre());
        zoomed /= 2;
        zoomCounter++;
        break;
      case 4:
        MapGes4.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed/2, MapGes4.targetPointAtViewportCentre());
        zoomed /= 2;
        zoomCounter++;
        break;
      case 5:
        MapGes5.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed/2, MapGes5.targetPointAtViewportCentre());
        zoomed /= 2;
        zoomCounter++;
        break;
    }
  }


  @FXML
  private void zoomOut() {
    ZoomOutMachine(floor);
  }
}