package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Database.NodesDatabase;
import edu.wpi.cs3733.c20.teamU.Navigation.Pathfinder;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import javax.xml.crypto.Data;
import javax.xml.crypto.NodeSetData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PathfindController {
    private enum State {
        NEUTRAL, START, END;
    }

    private Parent root;
    int floor = 1;

    @FXML
    private GesturePane PathGes;
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

    @FXML private AnchorPane NodesPane1, NodesPane2, NodesPane3, NodesPane4, NodesPane5;
    @FXML VBox oppo;
    @FXML Label floorLabel;

    public void setAttributes(Parent root) {
        this.root = root;
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
    private HashMap<Circle, Node> circles = new HashMap<>();
    private HashMap<Path, Integer> pathes = new HashMap<>();
    private int drawnFloor = 4;
    final ToggleGroup group = new ToggleGroup();
    private ArrayList<Integer> floorsInPath = new ArrayList<>();
    private HashMap<Circle, Integer> interFloorPaths = new HashMap<>();


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
                //System.out.println("Start Click");
                start = circles.get(event.getSource());
                startReady = (start != null) || startReady;
                if (startReady) startLabel.setText(start.getID());
                state = State.NEUTRAL;
                updateStatus();
            }
            else if (state == State.END) { //We're going to select an ending node!
                end = circles.get(event.getSource());
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


    @FXML private void clickUp(ActionEvent e){
        if (displayingPath && floorsInPath.contains(floor) && floorsInPath.indexOf(floor) < (floorsInPath.size()-1)) {
            floor = floorsInPath.get(floorsInPath.indexOf(floor)+1);
        }
        else {
            floor++;
            if(floor > 5) {
                floor = 5;
            }
        }
        stateMachine(floor);
    }

    @FXML private void clickDown(ActionEvent e){
        if (displayingPath && floorsInPath.contains(floor) && floorsInPath.indexOf(floor) > 0) {
            floor = floorsInPath.get(floorsInPath.indexOf(floor)-1);
        }
        else {
            floor--;
            if (floor < 1) {
                floor = 1;
            }
        }
        stateMachine(floor);
    }

    @FXML private void stateMachine(int floor){
        switch (floor){
            case 1:
                oppo.getChildren().clear();
                oppo.getChildren().add(N1);
                floor = 1;
                floorLabel.setText("1");
                break;
            case 2:
                oppo.getChildren().clear();
                oppo.getChildren().add(N2);
                floor = 2;
                floorLabel.setText("2");
                break;
            case 3:
                oppo.getChildren().clear();
                oppo.getChildren().add(N3);
                floor = 3;
                floorLabel.setText("3");
                break;
            case 4:
                oppo.getChildren().clear();
                oppo.getChildren().add(N4);
                floor = 4;
                floorLabel.setText("4");
                break;
            case 5:
                oppo.getChildren().clear();
                oppo.getChildren().add(N5);
                floor = 5;
                floorLabel.setText("5");
                break;
        }
    }



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

    public void drawNodes() {
        state = State.NEUTRAL;
        start = null;
        end = null;
        startReady = false;
        endReady = false;
        startLabel.setText("None Selected");
        endLabel.setText("None Selected");
        //ArrayList<Node> nodes = App.getGraph().getNodes();
        DatabaseWrapper.updateGraph();
        ArrayList<Node> nodes = DatabaseWrapper.getGraph().getNodes();
        clearPath();
        circles.clear();
        for (Node n : nodes) {
            //if (!App.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
            if (!DatabaseWrapper.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
            if (isDrawableNode(n)) {
                Circle c = new Circle();
                c.setCenterX(n.getX());
                c.setCenterY(n.getY());
                c.setRadius(App.getNodeSize());
                addToPath(c, n.getFloor());
                c.addEventHandler(MouseEvent.MOUSE_PRESSED, circleClickHandler);
                c.addEventHandler(MouseEvent.MOUSE_RELEASED, circleMouseReleaseHandler);
                c.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
                circles.put(c, n);
            }
        }
    }

    private boolean isDrawableNode(Node n) { //Which nodes do we want to draw?
        return !n.getNodeType().equals("HALL"); //no hallway nodes
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
        oppo.getChildren().clear();
        oppo.getChildren().add(N1);
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
            engine.starSingular(start, end);
            path = engine.getLatestPath();
            //System.out.println(path.size());
            drawPath();
        }
        updateStatus();
    }
    @FXML
    private void clearPath() {
        for (Map.Entry<Path, Integer> pair : pathes.entrySet()) {
            removeFromPath(pair.getKey(), pair.getValue());
        }
        for (Map.Entry<Circle, Integer> pair : interFloorPaths.entrySet()) {
            removeFromPath(pair.getKey(), pair.getValue());
        }
        displayingPath = false;
        floorsInPath.clear();
        pathes.clear();
        interFloorPaths.clear();
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
            if (!floorsInPath.contains(n1.getFloor())) floorsInPath.add(n1.getFloor());

            if (n1.getFloor() == n2.getFloor()) {

                MoveTo move = new MoveTo(n1.getX(), n1.getY());
                LineTo line = new LineTo(n2.getX(), n2.getY());
                Path pathe = new Path();
                pathe.getElements().add(move);
                pathe.getElements().add(line);
                pathe.setStroke(Color.web("#39ff14"));
                pathe.setStrokeWidth(5.0);
                pathes.put(pathe, n1.getFloor());
                addToPath(pathe, n1.getFloor());
            }
            else {
                Circle c = new Circle();
                Circle c2 = new Circle();
                c.setCenterX(n1.getX());
                c.setCenterY(n1.getY());
                c2.setCenterY(n2.getY());
                c2.setCenterX(n2.getX());
                c.setFill(Color.TRANSPARENT);
                c2.setFill(Color.TRANSPARENT);
                c.setStroke(Color.GREEN);
                c2.setStroke(Color.ORCHID);
                c.setRadius(App.getNodeSize()+5);
                c2.setRadius(App.getNodeSize()+5);
                interFloorPaths.put(c, n1.getFloor());
                interFloorPaths.put(c2, n2.getFloor());
                addToPath(c, n1.getFloor());
                addToPath(c2, n2.getFloor());
            }

        }
        displayingPath = true;
        updateStatus();
    }

    private void getTextPath(){
        App.getTextpath().clear();
        Node n2 = path.get(path.size() - 1);
        for (int i = 0; i < path.size() - 1; i++) { //Iterate over every adjacent pair in the path
            Node n1 = path.get(i);
            App.getTextpath().add(n1.getLongName());
        }
        App.getTextpath().add(n2.getLongName());
        Collections.reverse(App.getTextpath());
    }

    @FXML
    private void backHome() {
        App.getPrimaryStage().setScene(App.getHomeScene());
    }
    private void addToPath(javafx.scene.Node e, int floor) {
        switch (floor) {
            case 1:
                NodesPane1.getChildren().add(e);
                break;
            case 2:
                NodesPane2.getChildren().add(e);
                break;
            case 3:
                NodesPane3.getChildren().add(e);
                break;
            case 4:
                NodesPane4.getChildren().add(e);
                break;
            case 5:
                NodesPane5.getChildren().add(e);
                break;
        }
    }
    private void removeFromPath(javafx.scene.Node e, int floor) {
        switch (floor) {
            case 1:
                NodesPane1.getChildren().remove(e);
                break;
            case 2:
                NodesPane2.getChildren().remove(e);
                break;
            case 3:
                NodesPane3.getChildren().remove(e);
                break;
            case 4:
                NodesPane4.getChildren().remove(e);
                break;
            case 5:
                NodesPane5.getChildren().remove(e);
                break;
        }
    }
}
