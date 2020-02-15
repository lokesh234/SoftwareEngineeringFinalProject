package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Navigation.Pathfinder;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.util.ArrayList;
import java.util.HashMap;

public class FireController {

    private Parent root;
    @FXML
    private AnchorPane fireNodes;
    private HashMap<Node, Circle> circles = new HashMap<>();
    private Pathfinder firePath = Pathfinder.getPathfinder();
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
        /*
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
         */
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
    }

    private void drawNodes() {
        ArrayList<Node> nodes = App.getGraph().getNodes();
        for (Node n : nodes) {
            if (isDrawableNode(n)) {
                Circle c = new Circle();
                c.setCenterX(n.getX());
                c.setCenterY(n.getY());
                c.setRadius(App.getNodeSize());
                addToPath(c);
                circles.put(n, c);
            }
        }
    }

    private void addToPath(javafx.scene.Node e) { fireNodes.getChildren().add(e);
    }

    private boolean isDrawableNode(Node n) { //Which nodes do we want to draw?
        return !n.getNodeType().equals("HALL"); //Everything except hallway nodes
        //return true; //Everything!
    }

    public void setAttributes(Parent root) {
        this.root = root;
        this.drawNodes();
        this.drawPath();
    }

    /*

    private void drawPath() {
        if (path.size() == 0) return; //No path to draw
        System.out.println("a");
        for (int i = 0; i < path.size()-1; i++) { //Iterate over every adjacent pair in the path
            Node n1 = path.get(i);
            Node n2 = path.get(i+1);

            Line l = new Line();
            l.setStartX(n1.getX());
            l.setStartY(n1.getY());
            l.setEndX(n2.getX());
            l.setEndY(n2.getY());
            lines.add(l);
            addToPath(l);
        }
        updateStatus();
    }

    */

    private void drawPath() {
            Line l = new Line();
            l.setStartX(1250);
            l.setStartY(997);
            l.setEndX(1250);
            l.setEndY(1035);
            Line w = new Line();
            w.setStartX(1250);
            w.setStartY(1035);
            w.setEndX(1225);
            w.setEndY(1035);
            addToPath(l);
            addToPath(w);
        }

}


