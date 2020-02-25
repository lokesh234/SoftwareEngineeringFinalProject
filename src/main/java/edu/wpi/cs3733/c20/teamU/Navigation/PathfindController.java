package edu.wpi.cs3733.c20.teamU.Navigation;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PathfindController {
    private enum State {
        NEUTRAL, START, END;
    }

    private Parent root;
    int floor = 4;

    @FXML
    private JFXButton upButton, downButton;

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

    @FXML private GesturePane MapGes1;
    @FXML private GesturePane MapGes2;
    @FXML private GesturePane MapGes3;
    @FXML private GesturePane MapGes4;
    @FXML private GesturePane MapGes5;

    @FXML private RadioButton fast, elev, stai;

    private Circle startSelect = new Circle();
    private Circle endSelect = new Circle();


    public void setAttributes(Parent root) {
        this.root = root;
        this.drawNodes();
        this.updateStatus();
    }

    private State state = State.NEUTRAL;
    private Node start;
    private Node end;
    private ArrayList<Node> path = new ArrayList<>();
    private boolean startReady = false;
    private boolean endReady = false;
    private boolean displayingPath = false;
    private HashMap<Circle, Node> circles = new HashMap<>();
    private HashMap<Path, Integer> pathes = new HashMap<>();
    private int drawnFloor = 4;
    final ToggleGroup group = new ToggleGroup();
    private ArrayList<Integer> floorsInPath = new ArrayList<>();
    private HashMap<Circle, Node> interFloorPaths = new HashMap<>();
    PathfindTextController pathfindTextController = new PathfindTextController();
    private ArrayList<String> AllNodeNames= new ArrayList<String>();
    private int checker;
    private Circle wong = new Circle();

    @FXML private VBox radioBox;

    @FXML
    Button startButton, goButton, endButton, clearButton, backButton;
    @FXML
    Label startLabel, endLabel, statusLabel;
    @FXML
    TextField SearchBox;


    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            updateStatus();
            if (state == State.NEUTRAL) return; //We're not selecting a start or end point, so we don't need to do any work
            else if (state == State.START) { //We're going to select a starting node!
                //System.out.println("Start Click");
                start = circles.get(event.getSource());
                startReady = (start != null) || startReady;
                if (startReady) startLabel.setText(start.getLongName());
                if (startReady) SearchBox.setText(start.getLongName());
                state = State.NEUTRAL;
                updateStatus();
            }
            else if (state == State.END) { //We're going to select an ending node!
                end = circles.get(event.getSource());
                endReady = (end != null) || endReady;
                if (endReady) endLabel.setText(end.getLongName());
                if (endReady) SearchBox.setText(end.getLongName());
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
            App.setColor(circles.get(source), source);
        }
    };

    EventHandler<MouseEvent> interFloorPathHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) { //We know that one of the adjacent nodes is on a different floor (might be both of them, but then we can go to either)
            Node n1 = interFloorPaths.get(event.getSource());
            int circleFloor = n1.getFloor();
            if (path.indexOf(n1) > 0 && path.get(path.indexOf(n1)-1).getFloor() != circleFloor) { //The node before us is on a different floor
                floor = path.get(path.indexOf(n1)-1).getFloor();
                stateMachine(floor);
            }
            else { //Given that: 1. there is a node adjacent to us on a different floor and 2. the node before us (if it exists) is not on a different floor, we can conclude that there is a node behind us and it is on a different floor
                floor = path.get(path.indexOf(n1)+1).getFloor();
                stateMachine(floor);
            }
        }
    };



    @FXML private void clickUp(ActionEvent e){
        Collections.sort(floorsInPath);
        if (displayingPath && floorsInPath.contains(floor)) {
            if (floorsInPath.indexOf(floor) < (floorsInPath.size()-1)) floor = floorsInPath.get(floorsInPath.indexOf(floor)+1);
            else return;
        }
        else {
            floor++;
            if(floor > 5) {
                floor = 5;
            }
        }
        stateMachine(floor);
        checker = 1;
    }

    @FXML private void clickDown(ActionEvent e){
        Collections.sort(floorsInPath);
        if (displayingPath && floorsInPath.contains(floor)) {
            if (floorsInPath.indexOf(floor) > 0) floor = floorsInPath.get(floorsInPath.indexOf(floor)-1);
            else return;
        }
        else {
            floor--;
            if (floor < 1) {
                floor = 1;
            }
        }
        stateMachine(floor);
        checker = 2;
    }

    private void Auto(){
        TextFields.bindAutoCompletion(SearchBox, AllNodeNames).setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> autoCompletionEvent) -> {
            updateStatus();
            if (state == State.NEUTRAL) return; //We're not selecting a start or end point, so we don't need to do any work
            else if (state == State.START && DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText()) != null) { //We're going to select a starting node!
                //System.out.println("Start Click");
                start = DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText());
                startReady = (start != null) || startReady;
                if (startReady) startLabel.setText(start.getLongName());
                state = State.NEUTRAL;
                updateStatus();
            }
            else if (state == State.END && DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText()) != null) { //We're going to select an ending node!
                end = DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText());
                endReady = (end != null) || endReady;
                if (endReady) endLabel.setText(end.getLongName());
                state = State.NEUTRAL;
                updateStatus();
            }
        });
    }

    @FXML private void stateMachine(int floor){
        switch (floor){
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

    @FXML private void zoomIn() {
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


    @FXML private void zoomOut(){
        ZoomOutMachine(floor);
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

        if (startReady) {
            removeFromPath(startSelect, start.getFloor());
            startSelect.setCenterX(start.getX());
            startSelect.setCenterY(start.getY());
            startSelect.setFill(Color.TRANSPARENT);
            startSelect.setStroke(Color.YELLOW);
            startSelect.setStrokeWidth(5);
            startSelect.setRadius(20);
            addToPath(startSelect, start.getFloor());
        }
        else if (start != null) {
            removeFromPath(startSelect, start.getFloor());
        }

        if (endReady) {
            removeFromPath(endSelect, end.getFloor());
            endSelect.setCenterX(end.getX());
            endSelect.setCenterY(end.getY());
            endSelect.setFill(Color.TRANSPARENT);
            endSelect.setStroke(Color.ORANGE);
            endSelect.setStrokeWidth(5);
            endSelect.setRadius(20);
            addToPath(endSelect, end.getFloor());
        }
        else if (end != null) {
            removeFromPath(endSelect, end.getFloor());
        }
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
                App.setColor(n, c);
                circles.put(c, n);
            }
        }
    }

    @FXML
    private void MapLeft(){
        MoveLeftMachine(floor);
    }

    @FXML
    private void MapRight(){
        MoveRightMachine(floor);
    }

    @FXML
    private void MapUp(){
        MoveUpMachine(floor);
    }

    @FXML
    private void MapDown(){
        MoveDownMachine(floor);
    }


    private void MoveLeftMachine(int floor){
        switch (floor) {
            case 1:
                Point2D point2Dleft1 = new Point2D((MapGes1.targetPointAtViewportCentre().getX() - 20), MapGes1.targetPointAtViewportCentre().getY());
                MapGes1.centreOn(point2Dleft1);
                break;
            case 2:
                Point2D point2Dleft2 = new Point2D((MapGes2.targetPointAtViewportCentre().getX() - 20), MapGes2.targetPointAtViewportCentre().getY());
                MapGes2.centreOn(point2Dleft2);
                break;
            case 3:
                Point2D point2Dleft3 = new Point2D((MapGes3.targetPointAtViewportCentre().getX() - 20), MapGes3.targetPointAtViewportCentre().getY());
                MapGes3.centreOn(point2Dleft3);
                break;
            case 4:
                Point2D point2Dleft4 = new Point2D((MapGes4.targetPointAtViewportCentre().getX() - 20), MapGes4.targetPointAtViewportCentre().getY());
                MapGes4.centreOn(point2Dleft4);
                break;
            case 5:
                Point2D point2Dleft5 = new Point2D((MapGes5.targetPointAtViewportCentre().getX() - 20), MapGes5.targetPointAtViewportCentre().getY());
                MapGes5.centreOn(point2Dleft5);
                break;
        }
    }

    private void MoveRightMachine(int floor){
        switch (floor) {
            case 1:
                Point2D point2Dleft1 = new Point2D((MapGes1.targetPointAtViewportCentre().getX() + 20), MapGes1.targetPointAtViewportCentre().getY());
                MapGes1.centreOn(point2Dleft1);
                break;
            case 2:
                Point2D point2Dleft2 = new Point2D((MapGes2.targetPointAtViewportCentre().getX() + 20), MapGes2.targetPointAtViewportCentre().getY());
                MapGes2.centreOn(point2Dleft2);
                break;
            case 3:
                Point2D point2Dleft3 = new Point2D((MapGes3.targetPointAtViewportCentre().getX() + 20), MapGes3.targetPointAtViewportCentre().getY());
                MapGes3.centreOn(point2Dleft3);
                break;
            case 4:
                Point2D point2Dleft4 = new Point2D((MapGes4.targetPointAtViewportCentre().getX() + 20), MapGes4.targetPointAtViewportCentre().getY());
                MapGes4.centreOn(point2Dleft4);
                break;
            case 5:
                Point2D point2Dleft5 = new Point2D((MapGes5.targetPointAtViewportCentre().getX() + 20), MapGes5.targetPointAtViewportCentre().getY());
                MapGes5.centreOn(point2Dleft5);
                break;
        }
    }

    private void MoveUpMachine(int floor){
        switch (floor) {
            case 1:
                Point2D point2Dleft1 = new Point2D(MapGes1.targetPointAtViewportCentre().getX(), (MapGes1.targetPointAtViewportCentre().getY() - 20));
                MapGes1.centreOn(point2Dleft1);
                break;
            case 2:
                Point2D point2Dleft2 = new Point2D(MapGes2.targetPointAtViewportCentre().getX(), (MapGes2.targetPointAtViewportCentre().getY() - 20));
                MapGes2.centreOn(point2Dleft2);
                break;
            case 3:
                Point2D point2Dleft3 = new Point2D(MapGes3.targetPointAtViewportCentre().getX(), (MapGes3.targetPointAtViewportCentre().getY() - 20));
                MapGes3.centreOn(point2Dleft3);
                break;
            case 4:
                Point2D point2Dleft4 = new Point2D(MapGes4.targetPointAtViewportCentre().getX(), (MapGes4.targetPointAtViewportCentre().getY() - 20));
                MapGes4.centreOn(point2Dleft4);
                break;
            case 5:
                Point2D point2Dleft5 = new Point2D(MapGes5.targetPointAtViewportCentre().getX(), (MapGes5.targetPointAtViewportCentre().getY() - 20));
                MapGes5.centreOn(point2Dleft5);
                break;
        }
    }

    private void MoveDownMachine(int floor){
        switch (floor) {
            case 1:
                Point2D point2Dleft1 = new Point2D(MapGes1.targetPointAtViewportCentre().getX(), (MapGes1.targetPointAtViewportCentre().getY() + 20));
                MapGes1.centreOn(point2Dleft1);
                break;
            case 2:
                Point2D point2Dleft2 = new Point2D(MapGes2.targetPointAtViewportCentre().getX(), (MapGes2.targetPointAtViewportCentre().getY() + 20));
                MapGes2.centreOn(point2Dleft2);
                break;
            case 3:
                Point2D point2Dleft3 = new Point2D(MapGes3.targetPointAtViewportCentre().getX(), (MapGes3.targetPointAtViewportCentre().getY() + 20));
                MapGes3.centreOn(point2Dleft3);
                break;
            case 4:
                Point2D point2Dleft4 = new Point2D(MapGes4.targetPointAtViewportCentre().getX(), (MapGes4.targetPointAtViewportCentre().getY() + 20));
                MapGes4.centreOn(point2Dleft4);
                break;
            case 5:
                Point2D point2Dleft5 = new Point2D(MapGes5.targetPointAtViewportCentre().getX(), (MapGes5.targetPointAtViewportCentre().getY() + 20));
                MapGes5.centreOn(point2Dleft5);
                break;
        }
    }



    private boolean isDrawableNode(Node n) { //Which nodes do we want to draw?
        return !n.getNodeType().equals("HALL"); //no hallway nodes
        //return true; //Everything!
    }

    private EventHandler<MouseEvent> radioHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (fast.isSelected()) NavigationWrapper.setStatus(0);
            else if (elev.isSelected()) NavigationWrapper.setStatus(1);
            else NavigationWrapper.setStatus(2);

            if (displayingPath) pathfind();
        }
    };

    @FXML
    private void initialize() {
        fast.setToggleGroup(group);
        stai.setToggleGroup(group);
        elev.setToggleGroup(group);
        fast.setSelected(true);

        radioBox.addEventFilter(MouseEvent.MOUSE_CLICKED, radioHandler);

        NodesPane1.getChildren().add(new ImageView(App.getFloor1()));
        NodesPane2.getChildren().add(new ImageView(App.getFloor2()));
        NodesPane3.getChildren().add(new ImageView(App.getFloor3()));
        NodesPane4.getChildren().add(new ImageView(App.getFloor4()));
        NodesPane5.getChildren().add(new ImageView(App.getFloor5()));

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
        Populate();
        Auto();
        oppo.getChildren().clear();
        oppo.getChildren().add(N4);
    }

    private void Populate(){
        for (int i = 0; i < DatabaseWrapper.getGraph().getNodes().size(); i++){
            AllNodeNames.add(DatabaseWrapper.getGraph().getNodes().get(i).getLongName());
        }
    }

    @FXML
    private void selectStart() {
        state = State.START;
        updateStatus();
    }

    @FXML private void textPopUp(){
        App.getTextDirectionsPop().getContent().add(App.getPathFindText());
        App.getTextDirectionsPop().show(App.getPrimaryStage());
        App.getPathfindTextController().Populate();
        //pathfindTextController.Directions = new Label();
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
            NavigationWrapper.pathfind(start, end);
            path = NavigationWrapper.getPath();
            //System.out.print
            // ln(path.size());
            drawPath();
            getTextPath();
        }
        updateStatus();
        //pathfindTextController.Directions = new Label();
    }


    @FXML
    private void clearPath() {
        for (Map.Entry<Path, Integer> pair : pathes.entrySet()) {
            removeFromPath(pair.getKey(), pair.getValue());
        }
        for (Map.Entry<Circle, Node> pair : interFloorPaths.entrySet()) {
            removeFromPath(pair.getKey(), pair.getValue().getFloor());
        }
        displayingPath = false;
        floorsInPath.clear();
        pathes.clear();
        interFloorPaths.clear();
        updateStatus();
    }
    private void drawPath() {
        clearPath();
        Path pathe = new Path();
        if (path.size() == 0){
            displayingPath = true;
            updateStatus();
            return; //No path to draw
        }
       // System.out.println("a");
 //       for(int i = path.size() - 1; i >=0 ; i--)
        for (int i = 0; i < path.size()-1; i++) { //Iterate over every adjacent pair in the path
            Node n1 = path.get(i);
            Node n2 = path.get(i+1);
            if (!floorsInPath.contains(n1.getFloor()) && (i == 0 || (!n1.getNodeType().equals("STAI") && !n1.getNodeType().equals("ELEV")))) floorsInPath.add(n1.getFloor());
            if (!floorsInPath.contains(n2.getFloor()) && (i+2 == path.size() || (!n2.getNodeType().equals("STAI") && !n2.getNodeType().equals("ELEV")))) floorsInPath.add(n2.getFloor());

            if (n1.getFloor() == n2.getFloor()) {

                MoveTo move = new MoveTo(n1.getX(), n1.getY());
                LineTo line = new LineTo(n2.getX(), n2.getY());
                pathe.getElements().add(move);
                pathe.getElements().add(line);
                pathe.setStroke(Color.web("#7851a9"));
                pathe.setStrokeWidth(10.0);
                pathe.getStrokeDashArray().addAll(15d, 15d);
                pathe.setStrokeLineCap(StrokeLineCap.ROUND);
                pathe.setStrokeDashOffset(10);
                final double maxOffset =
                        pathe.getStrokeDashArray().stream()
                                .reduce(
                                        0d,
                                        (a, b) -> a + b
                                );
                Timeline timeline = new Timeline(
                        new KeyFrame(
                                Duration.ZERO,
                                new KeyValue(
                                        pathe.strokeDashOffsetProperty(),
                                        0,
                                        Interpolator.LINEAR
                                )
                        ),
                        new KeyFrame(
                                Duration.seconds(7),
                                new KeyValue(
                                        pathe.strokeDashOffsetProperty(),
                                        maxOffset,
                                        Interpolator.LINEAR
                                )
                        )
                );
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
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
                if (n1.getFloor() < n2.getFloor()) {
                    c.setStroke(Color.DARKGREEN);
                    c2.setStroke(Color.ORCHID);
                }
                else {
                    c.setStroke(Color.ORCHID);
                    c2.setStroke(Color.DARKGREEN);
                }
                c.setRadius(App.getNodeSize()+5);
                c2.setRadius(App.getNodeSize()+5);
                c.setStrokeWidth(10);
                c2.setStrokeWidth(10);
                c.addEventHandler(MouseEvent.MOUSE_CLICKED, interFloorPathHandler);
                c2.addEventHandler(MouseEvent.MOUSE_CLICKED, interFloorPathHandler);
                interFloorPaths.put(c, n1);
                interFloorPaths.put(c2, n2);
                addToPath(c, n1.getFloor());
                addToPath(c2, n2.getFloor());
            }
        }
        PathTransition pathTransition = new PathTransition();
        wong.setCenterX(start.getX());
        wong.setCenterY(start.getY());
        wong.setRadius(15);
        pathTransition.setNode(wong);
        pathTransition.setDuration(Duration.seconds(7));
        pathTransition.setPath(pathe);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
        displayingPath = true;
        addToPath(wong, start.getFloor());
        updateStatus();
    }

    private void getTextPath() {
        if (path.size() == 0) {
            return;
        }
        App.getTextpath().clear();
//        String pathWay = "";
//        Node n2 = path.get(path.size() - 1);
//        for (int i = 0; i < path.size() - 1; i++) { //Iterate over every adjacent pair in the path
//            Node n1 = path.get(i);
//            if (i != path.size() - 2) {
//                pathWay = getDirection(path.get(i), path.get(i + 1));
//                System.out.println(pathWay);
//            }
//            App.getTextpath().add(pathWay + ": " + n1.getLongName());
//        }
        String direction = "";
        boolean didContinue = false;
        for(int i = path.size() - 1; i >= 0; i--) {
            String name = path.get(i).getLongName();
//            System.out.println(path.get(i).getID().substring(1, 5));
            if(i == 0) {
//                App.getTextpath().add(getDirection(path.get(i + 1), path.get(i)) + path.get(i + 1).getLongName());
                App.getTextpath().add("Arrived at: " + name);
                continue;
            }
            if(direction.equals(getDirection(path.get(i), path.get(i - 1)))) {
                if(!didContinue) {
                    App.getTextpath().add("Continue through " + name);
                    didContinue = true;
                }
                continue;
            }
            didContinue = false;
            direction = getDirection(path.get(i), path.get(i - 1));
//            System.out.println(nodeType);
            App.getTextpath().add(direction + name);
        }
//        App.getTextpath().add("Arrived at: " + n2.getLongName());
//        Collections.reverse(App.getTextpath());
    }

    private String getDirection(Node one, Node two) {
        int x = one.getX() - two.getX();
//        System.out.println(x);
        int y = one.getY() - two.getY();
//        System.out.println(y);
        int pixTol = 20;
//        if(Math.abs(x) < pixTol && Math.abs(y) < pixTol) {
//            System.out.println("here");
//            return "Keep Moving...";
//        }

        if(x > 0) return "Go Left on ";
        if(x < 0) return "Go Right on ";
        if(y < 0) return "Go Down on ";
        if(y > 0) return "Go Up on ";
        return "Nothing";

    }
    @FXML
    private void backHome() {
        App.getPrimaryStage().setScene(App.getHomeScene());
    }
    private void addToPath(javafx.scene.Node e, int floor) {
        switch (floor) {
            case 1:
                if (!NodesPane1.getChildren().contains(e)) NodesPane1.getChildren().add(e);
                break;
            case 2:
                if (!NodesPane2.getChildren().contains(e)) NodesPane2.getChildren().add(e);
                break;
            case 3:
                if (!NodesPane3.getChildren().contains(e)) NodesPane3.getChildren().add(e);
                break;
            case 4:
                if (!NodesPane4.getChildren().contains(e)) NodesPane4.getChildren().add(e);
                break;
            case 5:
                if (!NodesPane5.getChildren().contains(e)) NodesPane5.getChildren().add(e);
                break;
        }
    }
    private void removeFromPath(javafx.scene.Node e, int floor) {
        switch (floor) {
            case 1:
                if (NodesPane1.getChildren().contains(e)) NodesPane1.getChildren().remove(e);
                break;
            case 2:
                if (NodesPane2.getChildren().contains(e)) NodesPane2.getChildren().remove(e);
                break;
            case 3:
                if (NodesPane3.getChildren().contains(e)) NodesPane3.getChildren().remove(e);
                break;
            case 4:
                if (NodesPane4.getChildren().contains(e)) NodesPane4.getChildren().remove(e);
                break;
            case 5:
                if (NodesPane5.getChildren().contains(e)) NodesPane5.getChildren().remove(e);
                break;
        }
    }
}
