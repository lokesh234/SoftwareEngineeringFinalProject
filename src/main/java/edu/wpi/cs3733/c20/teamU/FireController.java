package edu.wpi.cs3733.c20.teamU;

import com.sun.javafx.css.StyleCache;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FireController {

    private Parent root;
    @FXML
    private AnchorPane fireNodes;
    private HashMap<Node, Circle> circles = new HashMap<>();

    @FXML
    private void checkout() {
        App.getPrimaryStage().setScene(App.getHomeScene());
    }

    @FXML
    private void initialize(){
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
            if (isDrawableNode(n.getID())) {
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

    private boolean isDrawableNode(String nID) { //Which nodes do we want to draw?
        return !nID.substring(0, 5).equals("UHALL"); //Everything except hallway nodes
        //return true; //Everything!
    }

    public void setAttributes(Parent root) {
        this.root = root;
        this.drawNodes();
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


}
