package edu.wpi.cs3733.c20.teamU;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PathfindController {
    private enum State {
        /*
        NEUTRAL is the position when the program is not selecting a start or end node
        START is the position when the program is selecting a start node
        END is the position when the program is selecting an end node
         */
        NEUTRAL, START, END;
    }

    private Parent root;

    @FXML
    private AnchorPane NodesPane;

    public void setAttributes(Parent root) {
        this.root = root;
        NodesPane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
        this.drawNodes();
        this.updateStatus();
    }

    private State state = State.NEUTRAL;
    private Node start;
    private Node end;
    private ArrayList<Node> path = new ArrayList<>();
    private Pathfinder engine = new Pathfinder();
    private boolean startReady = false;
    private boolean endReady = false;
    private boolean displayingPath = false;
    private HashMap<Node, Circle> circles = new HashMap<>();
    private ArrayList<Line> lines = new ArrayList<>();


    @FXML
    Button startButton, goButton, endButton, clearButton, backButton;
    @FXML
    Label startLabel, endLabel, statusLabel;

    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            updateStatus();
            if (state == State.NEUTRAL) return; //We're not selecting a start or end point, so we don't need to do any work
            else if (state == State.START) { //We're going to select a starting node!
                System.out.println("Start click");
                int x = (int) event.getX();
                int y = (int) event.getY();
                start = getClickedNode(x, y);
                startReady = (start != null) || startReady;
                if (startReady) startLabel.setText(start.getID());
                state = State.NEUTRAL;
                updateStatus();
            }
            else if (state == State.END) { //We're going to select an ending node!
                int x = (int) event.getX();
                int y = (int) event.getY();
                end = getClickedNode(x, y);
                endReady = (end != null) || endReady;
                if (endReady) endLabel.setText(end.getID());
                state = State.NEUTRAL;
                updateStatus();
            }
        }
    };

    private void updateStatus() {
        if (state == State.NEUTRAL) {
            if (displayingPath && path.size() == 0) statusLabel.setText("No path found :(");
            else if (displayingPath) statusLabel.setText("Click 'Clear' to Remove This Path");
            else if (!startReady) statusLabel.setText("Click 'Start' to Set Start Position");
            else if (startReady && !endReady) statusLabel.setText("Click 'End' to Set Destination");
            else if (startReady && endReady) statusLabel.setText("Click 'Go!' to Display Path");
        }
        else if (state == State.END) statusLabel.setText("Click on a Node to Set Destination");
        else statusLabel.setText("Click on a Node to Set Start Position");
    }

    private Node getClickedNode(int x, int y) {
        /*
        Enhancement of NodeDatabase's getNodeInRange() method that only looks through rendered nodes
         */
        for (Map.Entry<Node, Circle> pair : circles.entrySet()) {
            Node n = pair.getKey();
            if (NodesDatabase.dist(x, y, n.getX(), n.getY()) <= App.getNodeSize()) return n;
        }
        return null;
    }

    private void drawNodes() {
        ArrayList<Node> nodes = App.getGraph().getNodes();
        for (Node n : nodes) {
            if (!App.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
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
    private boolean isDrawableNode(String nID) { //Which nodes do we want to draw?
        return !nID.substring(0, 5).equals("UHALL"); //Everything except hallway nodes
        //return true; //Everything!
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void selectStart() {
        state = State.START;
        updateStatus();
    }
    @FXML
    private void selectEnd() {
        state = State.END;
        updateStatus();
    }
    @FXML
    private void pathfind() {
        if (startReady && endReady) {
            clearPath();
            path.clear();
            lines.clear();
            engine.starSingular(start, end, App.getGraph());
            path = engine.getLatestPath();
            System.out.println(path.size());
            drawPath();
        }
        updateStatus();
    }
    @FXML
    private void clearPath() {
        if (lines.size() == 0) return;
        for (Line l : lines) {
            removeFromPath(l);
        }
        displayingPath = false;
        updateStatus();
    }
    private void drawPath() {
        clearPath();
        if (path.size() == 0){
            displayingPath = true;
            updateStatus();
            return; //No path to draw
        }
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
        displayingPath = true;
        updateStatus();
    }

    @FXML
    private void backHome() {
        App.getPrimaryStage().setScene(App.getHomeScene());
    }

    private void addToPath(javafx.scene.Node e) {
        NodesPane.getChildren().add(e);
    }
    private void removeFromPath(javafx.scene.Node e) {
        NodesPane.getChildren().remove(e);
    }
}
