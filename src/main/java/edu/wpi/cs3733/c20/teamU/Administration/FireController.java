package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindAStar;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.util.HashMap;

public class FireController {

    private Parent root;
    @FXML
    private AnchorPane fireNodes;
    private HashMap<Node, Circle> circles = new HashMap<>();
    private PathfindAStar firePath = PathfindAStar.getPathfinder();
    @FXML
    private GesturePane FireGesPane;

    @FXML
    public void checkout(){
        App.getPrimaryStage().setScene(App.getHomeScene());}

    @FXML
    private void initialize(){
        FireGesPane.centreOn(new Point2D(1250, 1500));
        FireGesPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                Point2D pivotOnTarget = FireGesPane.targetPointAt(new Point2D(e.getX(), e.getY()))
                        .orElse(FireGesPane.targetPointAtViewportCentre());
                // increment of scale makes more sense exponentially instead of linearly
                FireGesPane.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomBy(FireGesPane.getCurrentScale(), pivotOnTarget);
            }
        });
        App.getHome().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        App.getLogin().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        App.getStart().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        App.getPath().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        })
        ;
        App.getSecurity().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });

        App.getAdmin().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
         /*
        App.getFire().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    App.getPrimaryStage().setScene(App.getFireScene());
                }
            }
        });
        */
        FireGesPane.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(FireGesPane.getCurrentScale() - 3000, FireGesPane.targetPointAtViewportCentre());
    }


    private void addToPath(javafx.scene.Node e) { fireNodes.getChildren().add(e);
    }

    public void setAttributes(Parent root) {
        this.root = root;
        this.drawPath();
    }


    private void drawPath() {
        MoveTo move = new MoveTo(1375,1215);
        LineTo line = new LineTo( 1250, 1215);
        Path pathe = new Path();
        pathe.getElements().add(move);
        pathe.getElements().add(line);
        pathe.setStroke(Color.web("#ff0000"));
        pathe.setStrokeWidth(10.0);
        MoveTo move2 = new MoveTo(1250,1215);
        LineTo line2 = new LineTo( 1250, 1035);
        Path pathe2 = new Path();
        pathe.getElements().add(move2);
        pathe.getElements().add(line2);
        pathe.setStroke(Color.web("#ff0000"));
        pathe.setStrokeWidth(10.0);
        MoveTo move3 = new MoveTo(1250,1035);
        LineTo line3 = new LineTo( 1240, 1035);
        Path pathe3 = new Path();
        pathe.getElements().add(move3);
        pathe.getElements().add(line3);
        pathe.setStroke(Color.web("#ff0000"));
        pathe.setStrokeWidth(10.0);
        addToPath(pathe);
        addToPath(pathe2);
        addToPath(pathe3);
    }

}


