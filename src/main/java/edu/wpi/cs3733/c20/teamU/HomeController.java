package edu.wpi.cs3733.c20.teamU;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.Administration.CSSFileEditor;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindController;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import com.jfoenix.controls.JFXNodesList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lombok.SneakyThrows;
import net.kurobako.gesturefx.GesturePane;

import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;

public class HomeController {

  @FXML
  private JFXNodesList menuOptions;

  @FXML
  private JFXButton login;
//  @FXML
//  private Button navButton;
  @FXML
  private VBox oppo;
  @FXML
  private Label floorLabel;
  @FXML
  private Label time;
  @FXML
  private JFXButton weather;
  @FXML
  private AnchorPane N1;
  @FXML
  private AnchorPane N2;
  @FXML
  private AnchorPane N3;
  @FXML
  private AnchorPane N4;
  @FXML
  private AnchorPane N5;
  @FXML
  private GesturePane MapGes2;
  @FXML
  private GesturePane MapGes3;
  @FXML
  private GesturePane MapGes4;
  @FXML
  private GesturePane MapGes5;
  @FXML
  private GesturePane MapGes1;

  @FXML private AnchorPane NodesPane1, NodesPane2, NodesPane3, NodesPane4, NodesPane5;
  @FXML private JFXButton upButton, downButton;
  @FXML private JFXButton upArrow, downArrow, leftArrow, rightArrow;
  @FXML private JFXButton hamburger, navButton, services, info, help;

  private WeatherController weatherController;
  private long startTime;
  private long currentTime;
  int floor = 4;
  private int hr;
  private int m;
  private int s;
  private int checker;
  private Speech speech = new Speech();
  private volatile boolean runThread = true;
  @FXML private Label voiceLabel, voiceLabelstart, voiceLabelend;

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

  EventHandler<MouseEvent> leftClick =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          switch (floor) {
            case 1:
              Point2D point2Dleft1 =
                  new Point2D(
                      (MapGes1.targetPointAtViewportCentre().getX() - 20),
                      MapGes1.targetPointAtViewportCentre().getY());
              MapGes1.centreOn(point2Dleft1);
              break;
            case 2:
              Point2D point2Dleft2 =
                  new Point2D(
                      (MapGes2.targetPointAtViewportCentre().getX() - 20),
                      MapGes2.targetPointAtViewportCentre().getY());
              MapGes2.centreOn(point2Dleft2);
              break;
            case 3:
              Point2D point2Dleft3 =
                  new Point2D(
                      (MapGes3.targetPointAtViewportCentre().getX() - 20),
                      MapGes3.targetPointAtViewportCentre().getY());
              MapGes3.centreOn(point2Dleft3);
              break;
            case 4:
              Point2D point2Dleft4 =
                  new Point2D(
                      (MapGes4.targetPointAtViewportCentre().getX() - 20),
                      MapGes4.targetPointAtViewportCentre().getY());
              MapGes4.centreOn(point2Dleft4);
              break;
            case 5:
              Point2D point2Dleft5 =
                  new Point2D(
                      (MapGes5.targetPointAtViewportCentre().getX() - 20),
                      MapGes5.targetPointAtViewportCentre().getY());
              MapGes5.centreOn(point2Dleft5);
              break;
          }
        }
      };

  EventHandler<MouseEvent> upClick =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          switch (floor) {
            case 1:
              Point2D point2Dleft1 =
                  new Point2D(
                      MapGes1.targetPointAtViewportCentre().getX(),
                      (MapGes1.targetPointAtViewportCentre().getY() - 20));
              MapGes1.centreOn(point2Dleft1);
              break;
            case 2:
              Point2D point2Dleft2 =
                  new Point2D(
                      MapGes2.targetPointAtViewportCentre().getX(),
                      (MapGes2.targetPointAtViewportCentre().getY() - 20));
              MapGes2.centreOn(point2Dleft2);
              break;
            case 3:
              Point2D point2Dleft3 =
                  new Point2D(
                      MapGes3.targetPointAtViewportCentre().getX(),
                      (MapGes3.targetPointAtViewportCentre().getY() - 20));
              MapGes3.centreOn(point2Dleft3);
              break;
            case 4:
              Point2D point2Dleft4 =
                  new Point2D(
                      MapGes4.targetPointAtViewportCentre().getX(),
                      (MapGes4.targetPointAtViewportCentre().getY() - 20));
              MapGes4.centreOn(point2Dleft4);
              break;
            case 5:
              Point2D point2Dleft5 =
                  new Point2D(
                      MapGes5.targetPointAtViewportCentre().getX(),
                      (MapGes5.targetPointAtViewportCentre().getY() - 20));
              MapGes5.centreOn(point2Dleft5);
              break;
          }
        }
      };

  EventHandler<MouseEvent> downClick =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            switch (floor) {
              case 1:
                Point2D point2Dleft1 =
                    new Point2D(
                        MapGes1.targetPointAtViewportCentre().getX(),
                        (MapGes1.targetPointAtViewportCentre().getY() + 20));
                MapGes1.centreOn(point2Dleft1);
                break;
              case 2:
                Point2D point2Dleft2 =
                    new Point2D(
                        MapGes2.targetPointAtViewportCentre().getX(),
                        (MapGes2.targetPointAtViewportCentre().getY() + 20));
                MapGes2.centreOn(point2Dleft2);
                break;
              case 3:
                Point2D point2Dleft3 =
                    new Point2D(
                        MapGes3.targetPointAtViewportCentre().getX(),
                        (MapGes3.targetPointAtViewportCentre().getY() + 20));
                MapGes3.centreOn(point2Dleft3);
                break;
              case 4:
                Point2D point2Dleft4 =
                    new Point2D(
                        MapGes4.targetPointAtViewportCentre().getX(),
                        (MapGes4.targetPointAtViewportCentre().getY() + 20));
                MapGes4.centreOn(point2Dleft4);
                break;
              case 5:
                Point2D point2Dleft5 =
                    new Point2D(
                        MapGes5.targetPointAtViewportCentre().getX(),
                        (MapGes5.targetPointAtViewportCentre().getY() + 20));
                MapGes5.centreOn(point2Dleft5);
                break;
            }
          }

      };

  @FXML
  private void one() {
    oppo.getChildren().clear();
    oppo.getChildren().add(N1);
    floor = 1;
    floorLabel.setText("1");
    MapGes1.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes1.getCurrentScale() - 3000, MapGes1.targetPointAtViewportCentre());
  }

  @FXML
  private void two() {
    oppo.getChildren().clear();
    oppo.getChildren().add(N2);
    floor = 2;
    floorLabel.setText("2");
    MapGes2.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes2.getCurrentScale() - 3000, MapGes2.targetPointAtViewportCentre());
  }

  @FXML
  private void three() {
    oppo.getChildren().clear();
    oppo.getChildren().add(N3);
    floor = 3;
    floorLabel.setText("3");
    MapGes3.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes3.getCurrentScale() - 3000, MapGes3.targetPointAtViewportCentre());
  }

  @FXML
  private void four(){
    oppo.getChildren().clear();
    oppo.getChildren().add(N4);
    floor = 4;
    floorLabel.setText("4");
    MapGes4.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes4.getCurrentScale() - 3000, MapGes4.targetPointAtViewportCentre());
  }

  @FXML
  private void five() {
    oppo.getChildren().clear();
    oppo.getChildren().add(N5);
    floor = 5;
    floorLabel.setText("5");
    MapGes5.animate(Duration.millis(200))
        .interpolateWith(Interpolator.EASE_BOTH)
        .zoomBy(MapGes5.getCurrentScale() - 3000, MapGes5.targetPointAtViewportCentre());
  }

  EventHandler<MouseEvent> rightClick =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

          switch (floor) {
            case 1:
              Point2D point2Dleft1 =
                  new Point2D(
                      (MapGes1.targetPointAtViewportCentre().getX() + 20),
                      MapGes1.targetPointAtViewportCentre().getY());
              MapGes1.centreOn(point2Dleft1);
              break;
            case 2:
              Point2D point2Dleft2 =
                  new Point2D(
                      (MapGes2.targetPointAtViewportCentre().getX() + 20),
                      MapGes2.targetPointAtViewportCentre().getY());
              MapGes2.centreOn(point2Dleft2);
              break;
            case 3:
              Point2D point2Dleft3 =
                  new Point2D(
                      (MapGes3.targetPointAtViewportCentre().getX() + 20),
                      MapGes3.targetPointAtViewportCentre().getY());
              MapGes3.centreOn(point2Dleft3);
              break;
            case 4:
              Point2D point2Dleft4 =
                  new Point2D(
                      (MapGes4.targetPointAtViewportCentre().getX() + 20),
                      MapGes4.targetPointAtViewportCentre().getY());
              MapGes4.centreOn(point2Dleft4);
              break;
            case 5:
              Point2D point2Dleft5 =
                  new Point2D(
                      (MapGes5.targetPointAtViewportCentre().getX() + 20),
                      MapGes5.targetPointAtViewportCentre().getY());
              MapGes5.centreOn(point2Dleft5);
              break;
          }
        }
      };

  @FXML private void centerOut(){
    ZoomCenterMachine(floor);
  }

  private void ZoomCenterMachine(int floor) {
    switch (floor) {
      case 1:
        MapGes1.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes1.getCurrentScale() - 3000, MapGes1.targetPointAtViewportCentre());
        break;
      case 2:
        MapGes2.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes2.getCurrentScale() - 3000, MapGes2.targetPointAtViewportCentre());
        break;
      case 3:
        MapGes3.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes3.getCurrentScale() - 3000, MapGes3.targetPointAtViewportCentre());
        break;
      case 4:
        MapGes4.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes4.getCurrentScale() - 3000, MapGes4.targetPointAtViewportCentre());
        break;
      case 5:
        MapGes5.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes5.getCurrentScale() - 3000, MapGes5.targetPointAtViewportCentre());
        break;
    }
  }

  @FXML
  private void openLoginSceneSound() {
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
  private void openNavScene(ActionEvent e) {
    App.loadPathfinding();
    // App.getGraph().update();
    DatabaseWrapper.updateGraph();
    // App.getPathfindController().drawNodes();
    ServiceRequestWrapper.pathfindDrawNodes(App.getPathfindController());
    App.getPrimaryStage().setScene(App.getPathScene());
  }

  @FXML
  private void openHelpSceneSound() {
    App.getSecurityPop().getContent().add(App.getSecurity());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getSecurityController().addRequest();
    App.getSecurityPop().show(App.getPrimaryStage());
  }

  @FXML
  private void openInformationScene(ActionEvent e){
    App.getInformationPopUp().getContent().add(App.getInformation());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getInformationPopUp().show(App.getPrimaryStage());
  }

  @FXML
  private void openInformationSound() {
    App.getInformationPopUp().getContent().add(App.getInformation());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getInformationPopUp().show(App.getPrimaryStage());
  }

  @FXML
  private void openRequestSound() {
    App.loadAdminRequests();
    App.getRequestPop().getContent().add(App.getRequest());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getRequestPop().show(App.getPrimaryStage());
  }

  @FXML
  private void openNavSceneSpeech() {
    System.out.println("List contains nav");
    App.loadPathfinding();
    DatabaseWrapper.updateGraph();
    // App.getPathfindController().drawNodes();
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
  private void startListening() {
    System.out.println("Listening started");
    if (!runThread){
      voiceLabel.setText("Done");
    }
//    if (!startT.isAlive()) {
//      // voiceLabelstart.setText(App.getSpokenWords().get(0));
//      voiceLabel.setText("Done");
//    }
    App.getSpokenWords().clear();
    speech.startlistening();
    openHelpSceneSpeechB();
    // voiceLabel.setText("Listening");
  }

  @FXML
  private void openHelpSceneSpeechB() {
    runThread = true;
    voiceLabel.setText("Listening");
    if (App.SpeechComplete){
      runThread = false;
    }
    Thread startT =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                Runnable runTask =
                    new Runnable() {
                      @Override
                      public void run() {
                        if (App.getSpokenWords().contains("help")
                            || App.getSpokenWords().contains("help")
                            || App.getSpokenWords().contains("  help")
                            || App.getSpokenWords().contains(" help")
                            || App.getSpokenWords().contains("help help help")
                            || App.getSpokenWords().contains("help help")
                            || App.getSpokenWords().contains("help help help help")) {
                          System.out.println("List contains help");
                          openHelpSceneSound();
                          App.getSpokenWords().clear();
                          runThread  = false;
                        }
                        if (App.getSpokenWords().contains(" requests")
                            || App.getSpokenWords().contains("requests")
                            || App.getSpokenWords().contains("  requests")
                            || App.getSpokenWords().contains("request")
                            || App.getSpokenWords().contains("  requestt")) {
                          openRequestSound();
                          App.getSpokenWords().clear();
                          runThread = false;
                        }
                        if (App.getSpokenWords().contains("nav")
                            || App.getSpokenWords().contains("  nav")
                            || App.getSpokenWords().contains(" nav")) {
                          openNavSceneSpeech();
                          App.getSpokenWords().clear();
                          runThread = false;
                        }
                        if (App.getSpokenWords().contains("information")
                            || App.getSpokenWords().contains("  info")
                            || App.getSpokenWords().contains(" information")
                            || App.getSpokenWords().contains("inforamtion info")
                            || App.getSpokenWords().contains("info")) {
                          openInformationSound();
                          App.getSpokenWords().clear();
                          runThread = false;
                        }
                        if (App.getSpokenWords().contains("login")
                            || App.getSpokenWords().contains("  login")
                            || App.getSpokenWords().contains(" login")
                            || App.getSpokenWords().contains("login login")
                            || App.getSpokenWords().contains("login login login")) {
                          openLoginSceneSound();
                          App.getSpokenWords().clear();
                          runThread = false;
                          System.out.println(runThread);
                        }
                        if (App.getSpokenWords().contains("weather")
                            || App.getSpokenWords().contains("  weather")
                            || App.getSpokenWords().contains(" wea")
                            || App.getSpokenWords().contains("weather weather")
                            || App.getSpokenWords().contains("weather weather weather")) {
                          openWeather();
                          App.getSpokenWords().clear();
                          runThread = false;
                        }
                        else {
                         runThread = false;
                        }
                      }
                    };
                while (runThread) {
                  try {
                    Thread.sleep(1000);
                  } catch (InterruptedException ex) {
                    ex.printStackTrace();
                  }
                  System.out.println("I am running");
                  Platform.runLater(runTask);
                }
              }
            });
    for (int i = 0; i < App.getSpokenWords().size(); i++) {
      voiceLabelend.setText(App.getSpokenWords().get(i));
    }
    startT.setDaemon(runThread);
    startT.start();
  }

  Thread startC =
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
    // add stuff to the menu items now
      menuOptions.getChildren().clear();
      menuOptions.addAnimatedNode(hamburger);
      menuOptions.addAnimatedNode(navButton);
      menuOptions.addAnimatedNode(services);
      menuOptions.addAnimatedNode(info);
      menuOptions.addAnimatedNode(help);
      menuOptions.setSpacing(20.0);

    NodesPane1.getChildren().add(new ImageView(App.getFloor1()));
    NodesPane2.getChildren().add(new ImageView(App.getFloor2()));
    NodesPane3.getChildren().add(new ImageView(App.getFloor3()));
    NodesPane4.getChildren().add(new ImageView(App.getFloor4()));
    NodesPane5.getChildren().add(new ImageView(App.getFloor5()));

    leftArrow.addEventHandler(MOUSE_PRESSED, leftClick);
    rightArrow.addEventHandler(MOUSE_PRESSED, rightClick);
    upArrow.addEventHandler(MOUSE_PRESSED, upClick);
    downArrow.addEventHandler(MOUSE_PRESSED, downClick);

    hr = LocalDateTime.now(ZoneId.of("America/New_York")).getHour();
    m = LocalDateTime.now(ZoneId.of("America/New_York")).getMinute();
    s = LocalDateTime.now(ZoneId.of("America/New_York")).getSecond();
    startC.setDaemon(true);
    startC.start();
    MapGes1.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
            Point2D pivotOnTarget =
                MapGes1.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(MapGes1.targetPointAtViewportCentre());
            // increment of scale makes more sense exponentially instead of linearly
            MapGes1.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes1.getCurrentScale(), pivotOnTarget);
          }
        });
    MapGes2.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
            Point2D pivotOnTarget =
                MapGes2.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(MapGes2.targetPointAtViewportCentre());
            // increment of scale makes more sense exponentially instead of linearly
            MapGes2.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes2.getCurrentScale(), pivotOnTarget);
          }
        });
    MapGes3.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
            Point2D pivotOnTarget =
                MapGes3.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(MapGes3.targetPointAtViewportCentre());
            // increment of scale makes more sense exponentially instead of linearly
            MapGes3.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes3.getCurrentScale(), pivotOnTarget);
          }
        });
    MapGes4.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
            Point2D pivotOnTarget =
                MapGes4.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(MapGes4.targetPointAtViewportCentre());
            // increment of scale makes more sense exponentially instead of linearly
            MapGes4.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes4.getCurrentScale(), pivotOnTarget);
          }
        });
    MapGes5.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
            Point2D pivotOnTarget =
                MapGes5.targetPointAt(new Point2D(e.getX(), e.getY()))
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
    switch (floor) {
      case 1:
        oppo.getChildren().clear();
        oppo.getChildren().add(N1);
        floor = 1;
        floorLabel.setText("1");
        upButton.setStyle("-fx-text-fill: FFFFFF");
        downButton.setStyle("-fx-text-fill: A9A9A9");
        MapGes1.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes1.getCurrentScale() - 3000, MapGes1.targetPointAtViewportCentre());

        break;
      case 2:
        oppo.getChildren().clear();
        oppo.getChildren().add(N2);
        floor = 2;
        floorLabel.setText("2");
        upButton.setStyle("-fx-text-fill: FFFFFF");
        downButton.setStyle("-fx-text-fill: FFFFFF");
        MapGes2.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes2.getCurrentScale() - 30000, MapGes2.targetPointAtViewportCentre());
        break;
      case 3:
        oppo.getChildren().clear();
        oppo.getChildren().add(N3);
        floor = 3;
        floorLabel.setText("3");
        upButton.setStyle("-fx-text-fill: FFFFFF");
        downButton.setStyle("-fx-text-fill: FFFFFF");
        MapGes3.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes3.getCurrentScale() - 3000, MapGes3.targetPointAtViewportCentre());
        break;
      case 4:
        oppo.getChildren().clear();
        oppo.getChildren().add(N4);
        floor = 4;
        floorLabel.setText("4");
        upButton.setStyle("-fx-text-fill: FFFFFF");
        downButton.setStyle("-fx-text-fill: FFFFFF");
        MapGes4.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomBy(MapGes4.getCurrentScale() - 3000, MapGes4.targetPointAtViewportCentre());
        break;
      case 5:
        oppo.getChildren().clear();
        oppo.getChildren().add(N5);
        floor = 5;
        floorLabel.setText("5");
        upButton.setStyle("-fx-text-fill: A9A9A9");
        downButton.setStyle("-fx-text-fill: FFFFFF");
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
    System.out.println(runThread);
    ZoomInMachine(floor);
  }

  private int zoomCounter = 5;
  private double zoomed = 0;

  private void ZoomInMachine(int floor) {
    switch (floor) {
      case 1:
          MapGes1.animate(Duration.millis(200))
              .interpolateWith(Interpolator.EASE_BOTH)
              .zoomBy(MapGes1.getCurrentScale(), MapGes1.targetPointAtViewportCentre());
          zoomed = MapGes1.getCurrentScale();
          zoomCounter--;
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
        //          System.out.println(MapGes1.getCurrentScale());
          MapGes1.animate(Duration.millis(200))
              .interpolateWith(Interpolator.EASE_BOTH)
              .zoomTo(zoomed / 1.5, MapGes1.targetPointAtViewportCentre());
          zoomed /= 1.5;
          // System.out.println(zoomed/2);
          zoomCounter++;
        //        System.out.println(MapGes1.getCurrentScale() - 5);
        break;
      case 2:
        MapGes2.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed / 1.5, MapGes2.targetPointAtViewportCentre());
        zoomed /= 1.5;
        zoomCounter++;
        break;
      case 3:
        MapGes3.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed / 1.5, MapGes3.targetPointAtViewportCentre());
        zoomed /= 1.5;
        zoomCounter++;
        break;
      case 4:
        MapGes4.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed / 1.5, MapGes4.targetPointAtViewportCentre());
        zoomed /= 1.5;
        zoomCounter++;
        break;
      case 5:
        MapGes5.animate(Duration.millis(200))
            .interpolateWith(Interpolator.EASE_BOTH)
            .zoomTo(zoomed / 1.5, MapGes5.targetPointAtViewportCentre());
        zoomed /= 1.5;
        zoomCounter++;
        break;
    }
  }

  @FXML
  private void zoomOut() {
    ZoomOutMachine(floor);
  }
}
