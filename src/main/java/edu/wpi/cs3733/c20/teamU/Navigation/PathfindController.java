package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Database.NodesDatabase;
import edu.wpi.cs3733.c20.teamU.Navigation.Pathfinder;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PathfindController {
    private enum State {
        NEUTRAL, START, END;
    }

    private Parent root;

    @FXML
    private AnchorPane NodesPane;
    @FXML
    private GesturePane PathGes;

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
    private Pathfinder engine = Pathfinder.getPathfinder();
    private boolean startReady = false;
    private boolean endReady = false;
    private boolean displayingPath = false;
    private HashMap<Node, Circle> circles = new HashMap<>();
    private ArrayList<Path> pathes = new ArrayList<>();
    final ToggleGroup group = new ToggleGroup();


    @FXML
    Button startButton, goButton, endButton, clearButton, backButton;
    @FXML
    Label startLabel, endLabel, statusLabel;

    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            updateStatus();
            int x = (int) event.getX();
            int y = (int) event.getY();
            Node temp = getClickedNode(x, y);
            if (state == State.NEUTRAL) return; //We're not selecting a start or end point, so we don't need to do any work
            else if (state == State.START) { //We're going to select a starting node!
                //System.out.println("Start Click");
                start = temp;
                startReady = (start != null) || startReady;
                if (startReady) startLabel.setText(start.getID());
                state = State.NEUTRAL;
                updateStatus();
            }
            else if (state == State.END) { //We're going to select an ending node!
                end = temp;
                endReady = (end != null) || endReady;
                if (endReady) endLabel.setText(end.getID());
                state = State.NEUTRAL;
                updateStatus();
            }
        }
    };

    EventHandler<MouseEvent> circleClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Circle source = (Circle) event.getSource();
            source.setFill(Color.YELLOW);
        }
    };

    EventHandler<MouseEvent> circleMouseReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Circle source = (Circle) event.getSource();
            source.setFill(Color.BLACK);
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

    public void drawNodes() {
        state = State.NEUTRAL;
        start = null;
        end = null;
        startReady = false;
        endReady = false;
        ArrayList<Node> nodes = App.getGraph().getNodes();
        if (circles.size() > 0) {
            for (Map.Entry<Node, Circle> pair : circles.entrySet()) {
                Node n = pair.getKey();
                Circle c = pair.getValue();
                App.removeFromPath(c);
            }
        }
        circles.clear();
        for (Node n : nodes) {
            if (!App.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
            if (isDrawableNode(n)) {
                Circle c = new Circle();
                c.setCenterX(n.getX());
                c.setCenterY(n.getY());
                c.setRadius(App.getNodeSize());
                addToPath(c);
                c.addEventHandler(MouseEvent.MOUSE_PRESSED, circleClickHandler);
                c.addEventHandler(MouseEvent.MOUSE_RELEASED, circleMouseReleaseHandler);
                circles.put(n, c);
            }
        }
    }

    private boolean isDrawableNode(Node n) { //Which nodes do we want to draw?
        return !n.getNodeType().equals("HALL"); //If ID is shorter than 6, it's not a hallway node
        //return true; //Everything!
    }

    @FXML
    private void initialize() {
        PathGes.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                Point2D pivotOnTarget = PathGes.targetPointAt(new Point2D(e.getX(), e.getY()))
                        .orElse(PathGes.targetPointAtViewportCentre());
                // increment of scale makes more sense exponentially instead of linearly
                PathGes.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomBy(PathGes.getCurrentScale(), pivotOnTarget);
            }
        });
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
            //System.out.println("PathfindPress");
            path.clear();
            pathes.clear();
            engine.starSingular(start, end, App.getGraph());
            path = engine.getLatestPath();
            //System.out.println(path.size());
            drawPath();
        }
        updateStatus();
    }
    @FXML
    private void clearPath() {
        if (pathes.size() == 0) return;
        for (Path p : pathes) {
            removeFromPath(p);
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
       // System.out.println("a");
        for (int i = 0; i < path.size()-1; i++) { //Iterate over every adjacent pair in the path
            Node n1 = path.get(i);
            Node n2 = path.get(i+1);

            MoveTo move = new MoveTo(n1.getX(),n1.getY());
            LineTo line = new LineTo(n2.getX(),n2.getY());
            Path pathe = new Path();
            pathe.getElements().add(move);
            pathe.getElements().add(line);
            pathe.setStroke(Color.web("#39ff14"));
            pathe.setStrokeWidth(5.0);
            pathes.add(pathe);
            addToPath(pathe);

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
