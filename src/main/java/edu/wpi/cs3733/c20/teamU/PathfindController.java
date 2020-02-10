package edu.wpi.cs3733.c20.teamU;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

import java.util.ArrayList;

public class PathfindController {
    private enum State {
        /*
        NEUTRAL is the position when the program is not selecting a start or end node
        START is the position when the program is selecting a start node
        END is the position when the program is selecting an end node
         */
        NEUTRAL, START, END;
    }

    private Parent parent;
    private Parent root;
    private Popup popup;

    public void setAttributes(Parent parent, Parent root, Popup popup) {
        this.parent = parent;
        this.popup = popup;
        this.root = root;
    }

    private State state = State.NEUTRAL;
    private Node start;
    private Node end;
    private ArrayList<Node> path;
    private Pathfinder engine = new Pathfinder();
    private boolean startReady = false;
    private boolean endReady = false;
    private int nodeSize = 10; //Radius in pixels of clickable node object

    NodesDatabase graph = new NodesDatabase();

    @FXML
    Button startButton, goButton, endButton, clearButton, backButton;
    @FXML
    Label startLabel, endLabel;

    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (state == State.NEUTRAL) return; //We're not selecting a start or end point, so we don't need to do any work
            else if (state == State.START) { //We're going to select a starting node!
                int x = (int) event.getX();
                int y = (int) event.getY();
                start = graph.getNodeInRange(x, y, nodeSize);
                startReady = start != null;
            }
            else if (state == State.END) { //We're going to select an ending node!
                int x = (int) event.getX();
                int y = (int) event.getY();
                end = graph.getNodeInRange(x, y, nodeSize);
                endReady = start != null;
            }
        }
    };

    @FXML
    private void initialize() {
        startButton = new Button("Select start point");
        endButton = new Button("Select end point");

        startButton.setOnAction((e) -> this.selectStart());
        endButton.setOnAction((e) -> this.selectEnd());


    }

    private void selectStart() {
        state = State.START;
    }
    private void selectEnd() {
        state = State.END;
    }
    private void pathfind() {
        if (startReady && endReady) {
            engine.starSingular(start, end, graph);
            path = engine.getLatestPath();
            drawPath();
        }
    }
    private void clearPath() {
        path.clear();
        drawPath();
    }
    private void drawPath() {
        if (path.size() == 0) {}//TODO: draw empty path/clear existing path
        //TODO: Draw actual path
    }
}
