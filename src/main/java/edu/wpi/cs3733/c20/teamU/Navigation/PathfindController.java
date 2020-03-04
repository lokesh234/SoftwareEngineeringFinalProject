package edu.wpi.cs3733.c20.teamU.Navigation;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.MouseEvent.*;

public class PathfindController {

    private enum State {
        NEUTRAL, START, END;
    }

    private Parent root;
    int floor = 4;


    @FXML private JFXDrawer menuDrawer, legendDrawer;
    @FXML private StackPane menuBurgerContainer, legendPane;
    @FXML private JFXHamburger menuBurger;
    @FXML private VBox legendContent;

    @FXML
    private StackPane navPane;

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
//    @FXML Label floorLabel;

    @FXML private GesturePane MapGes1;
    @FXML private GesturePane MapGes2;
    @FXML private GesturePane MapGes3;
    @FXML private GesturePane MapGes4;
    @FXML private GesturePane MapGes5;
    @FXML private JFXButton upArrow, downArrow, leftArrow, rightArrow;
    @FXML private JFXTextField startBox, endBox;
    @FXML private JFXButton centerOut;

    @FXML private RadioButton fast, elev, stai;

    private Circle startSelect = new Circle();
    private Circle endSelect = new Circle();
    private ImageView startViewselect = new ImageView();
    private ImageView endViewselect = new ImageView();
    private ImageView startView = new ImageView();
    private ImageView endView = new ImageView();
    private Image startMarker = new Image("png_files/start.png");
    private Image endMarker = new Image("png_files/end.png");



    public void setAttributes(Parent root) {
        this.root = root;
        this.drawNodes();
        this.updateStatus();
    }

    private State state = State.START;
    private Node start;
    private Node end;
    private ArrayList<Node> path = new ArrayList<>();
    private boolean startReady = false;
    private boolean endReady = false;
    private boolean displayingPath = false;
    private HashMap<Circle, Node> circles = new HashMap<>();
    private HashMap<ImageView, Node> IconMap = new HashMap<>();
    private HashMap<ImageView, Node> hitboxes= new HashMap<>();
    private HashMap<Path, Integer> pathes = new HashMap<>();
    private int drawnFloor = 4;
    final ToggleGroup group = new ToggleGroup();
    private ArrayList<Integer> floorsInPath = new ArrayList<>();
    private HashMap<Circle, Node> interFloorPaths = new HashMap<>();
    PathfindTextController pathfindTextController = new PathfindTextController();
    private ArrayList<String> AllNodeNames= new ArrayList<String>();
    private int checker;

    private Image wongFront = new Image("png_files/gif/frontFly.gif");
    private Image wongBack = new Image("png_files/gif/backFly.gif");
    private Image wongLeft = new Image("png_files/gif/leftFly.gif");
    private Image wongRight = new Image("png_files/gif/rightFly.gif");

    private Thread wongThread;

    private ArrayList<Rectangle> wongs = new ArrayList<>();
    private HashMap<Rectangle, ArrayList<Node>> pathChunks = new HashMap<>();
    private HashMap<Rectangle, Node> nextNode = new HashMap<>();
    //private ArrayList<Circle> wongs = new ArrayList<>();
    private Label startNodeLabel = new Label();
    private Label endNodeLabel = new Label();
    private Label l = new Label();
    private Label l2 = new Label();
    private ArrayList<Label> nodeLabels = new ArrayList<>();

    @FXML private VBox radioBox;

    @FXML
    Button startButton, goButton, endButton, clearButton, backButton;
    @FXML
    Label startLabel, endLabel, statusLabel;
    @FXML
//    JFXTextField SearchBox;

    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            updateStatus();
            if (state == State.NEUTRAL) return; //We're not selecting a start or end point, so we don't need to do any work
            else if (state == State.START) { //We're going to select a starting node!
                //System.out.println("Start Click");
                start = IconMap.get(event.getSource());
                startReady = (start != null) || startReady;
//                if (startReady) startLabel.setText(start.getLongName());
//                if (startReady) SearchBox.setText(start.getLongName());
                if (startReady) startBox.setText(start.getLongName());
                state = State.END;
                updateStatus();
            }
            else if (state == State.END) { //We're going to select an ending node!
                end = IconMap.get(event.getSource());
                endReady = (end != null) || endReady;
//                if (endReady) endLabel.setText(end.getLongName());
//                if (endReady) SearchBox.setText(end.getLongName());
                if (endReady) endBox.setText(end.getLongName());
                state = State.START;
                updateStatus();
            }
        }
    };

    EventHandler<MouseEvent> PathZOOOOM = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (start == null){
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                return;
            }
            else {
                System.out.println("----------------------------------------------//////////////////");
                startZoomMachine(floor);
            }
        }
    };

    EventHandler<MouseEvent> hitboxClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            updateStatus();
            if (start != null && start.equals(hitboxes.get(event.getSource()))) {
                start = null;
                startReady = false;
                state = State.START;
                updateStatus();
            }
            else if (end != null && end.equals(hitboxes.get(event.getSource()))){
                end = null;
                endReady = false;
                state = State.END;
                updateStatus();
            }
            else {
                if (state == State.NEUTRAL)
                    return; //We're not selecting a start or end point, so we don't need to do any work
                else if (state == State.START) { //We're going to select a starting node!
                    //System.out.println("Start Click");
                    start = hitboxes.get(event.getSource());
                    startReady = (start != null) || startReady;
//                if (startReady) startLabel.setText(start.getLongName());
                    if (startReady) startBox.setText(start.getLongName());
                    state = State.END;
                    updateStatus();
                } else if (state == State.END) { //We're going to select an ending node!
                    end = hitboxes.get(event.getSource());
                    endReady = (end != null) || endReady;
//                if (endReady) endLabel.setText(end.getLongName());
                    if (endReady) endBox.setText(end.getLongName());
                    state = State.START;
                    updateStatus();
                }
            }
        }
    };

    EventHandler<MouseEvent> circleClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ImageView source = (ImageView) event.getSource();
            source.setFitHeight(24);
            source.setFitWidth(24);
        }
    };

    EventHandler<MouseEvent> circleMouseReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ImageView source = (ImageView) event.getSource();
            source.setFitWidth(24);
            source.setFitWidth(24);
        }
    };

    EventHandler<MouseEvent> startClearSelect = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            start = null;
            startReady = false;
            state = State.START;
            updateStatus();
        }
    };

    EventHandler<MouseEvent> endClearSelect = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            end = null;
            endReady = false;
            state = State.END;
            updateStatus();
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

    private void Auto(JFXTextField autocomplete){
        TextFields.bindAutoCompletion(autocomplete, AllNodeNames).setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> autoCompletionEvent) -> {
            updateStatus();
            if (state == State.NEUTRAL) return; //We're not selecting a start or end point, so we don't need to do any work
//            else if (state == State.START && DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText()) != null) { //We're going to select a starting node!
//                //System.out.println("Start Click");
//                start = DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText());
//                startReady = (start != null) || startReady;
////                if (startReady) startLabel.setText(start.getLongName());
//                state = State.END;
//                updateStatus();
//            }
//            else if (state == State.END && DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText()) != null) { //We're going to select an ending node!
//                end = DatabaseWrapper.getGraph().getNodeByLongName(SearchBox.getText());
//                endReady = (end != null) || endReady;
////                if (endReady) endLabel.setText(end.getLongName());
//                state = State.START;
//                updateStatus();
//            }
        });
    }

    private void AutoStart(JFXTextField autocomplete){
        TextFields.bindAutoCompletion(autocomplete, AllNodeNames).setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> autoCompletionEvent) -> {
            updateStatus();
            if (DatabaseWrapper.getGraph().getNodeByLongName(autocomplete.getText()) != null) { //We're going to select a starting node!
                //System.out.println("Start Click");
                start = DatabaseWrapper.getGraph().getNodeByLongName(autocomplete.getText());
                startReady = (start != null) || startReady;
//                if (startReady) startLabel.setText(start.getLongName());
                state = State.END;
                updateStatus();
            }
        });
    }

    private void AutoEnd(JFXTextField autocomplete){
        TextFields.bindAutoCompletion(autocomplete, AllNodeNames).setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> autoCompletionEvent) -> {
            updateStatus();
           if (DatabaseWrapper.getGraph().getNodeByLongName(autocomplete.getText()) != null) { //We're going to select an ending node!
                end = DatabaseWrapper.getGraph().getNodeByLongName(autocomplete.getText());
                endReady = (end != null) || endReady;
//                if (endReady) endLabel.setText(end.getLongName());
                state = State.START;
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
//                floorLabel.setText("1");
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
//                floorLabel.setText("2");
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
//                floorLabel.setText("3");
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
//                floorLabel.setText("4");
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
//                floorLabel.setText("5");
                upButton.setStyle("-fx-text-fill: A9A9A9");
                downButton.setStyle("-fx-text-fill: FFFFFF");
                MapGes5.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomBy(MapGes5.getCurrentScale() - 3000, MapGes5.targetPointAtViewportCentre());
                break;
        }
    }

    @FXML private void centerOut(){
        ZoomCenterMachine(floor);
    }

    @FXML private void zoomIn() {
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

    private void startZoomMachine(int floor) {
        Point2D focus = new Point2D(start.getX(), start.getY());
        switch (floor) {
            case 1:
                MapGes1.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomTo(MapGes1.getCurrentScale() + 1.25 , focus);
                break;
            case 2:
                MapGes2.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomTo(MapGes2.getCurrentScale() + 1.25 , focus);

                break;
            case 3:
                MapGes3.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomTo(MapGes3.getCurrentScale() + 1.25 , focus);

                break;
            case 4:
                System.out.println(focus);
                MapGes4.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomTo(MapGes4.getCurrentScale() + 1.25 , focus);

                break;
            case 5:
                MapGes5.animate(Duration.millis(200))
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomTo(MapGes5.getCurrentScale() + 1.25, focus);

                break;
        }
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


    @FXML private void zoomOut(){
        ZoomOutMachine(floor);
    }


    private void updateStatus() {
        removeFromAll(startNodeLabel);
        removeFromAll(endNodeLabel);
        removeFromAll(startView);
        removeFromAll(startSelect);
        removeFromAll(endSelect);
        removeFromAll(endView);
        removeFromAll(endViewselect);
        if (state == State.NEUTRAL) {
//            if (displayingPath && path.size() == 0) statusLabel.setText("No path found :(");
//            else if (displayingPath) statusLabel.setText("Click 'Clear' to Remove This Path");
//            else if (!startReady) statusLabel.setText("Click 'Start' to Set Start Position");
//            else if (startReady && !endReady) statusLabel.setText("Click 'End' to Set Destination");
//            else if (startReady && endReady) statusLabel.setText("Click 'Go!' to Display Path");
        }
//        else if (state == State.END) statusLabel.setText("Click on a Node to Set Destination");
//        else statusLabel.setText("Click on a Node to Set Start Position");

        if (startReady) {
            removeFromPath(startSelect, start.getFloor());
            removeFromPath(startView, start.getFloor());
            removeFromPath(startViewselect, start.getFloor());
            startSelect.setCenterX(start.getX());
            startSelect.setCenterY(start.getY());
            startSelect.setFill(Color.TRANSPARENT);
            startSelect.setStroke(Color.GREEN);
            startSelect.setStrokeWidth(5);
            startSelect.setRadius(20);
            startSelect.setOnMouseClicked(startClearSelect);
            startView.setImage(startMarker);
            startView.setX(start.getX() - 10);
            startView.setY(start.getY() - 53);
            startView.setOnMouseClicked(startClearSelect);
            addToPath(startSelect, start.getFloor());
            addToPath(startView, start.getFloor());

            startNodeLabel.setText(start.getLongName());
            startNodeLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            startNodeLabel.setTextFill(Color.BLACK);
            startNodeLabel.setLayoutX(start.getX() + 30);
            startNodeLabel.setLayoutY(start.getY() - 30);
            addToPath(startNodeLabel, start.getFloor());
        }
        else if (start != null) {
            removeFromPath(startSelect, start.getFloor());
            removeFromPath(startViewselect, start.getFloor());
//            startLabel.setText("None Selected");
        }
        else {
//            startLabel.setText("None Selected");
        }

        if (endReady) {
            removeFromPath(endSelect, end.getFloor());
            removeFromPath(endView, end.getFloor());
            removeFromPath(endViewselect, end.getFloor());
            endSelect.setCenterX(end.getX());
            endSelect.setCenterY(end.getY());
            endSelect.setFill(Color.TRANSPARENT);
            endSelect.setStroke(Color.RED);
            endSelect.setStrokeWidth(5);
            endSelect.setRadius(20);
            endSelect.setOnMouseClicked(endClearSelect);
            endView.setImage(endMarker);
            endView.setX(end.getX() - 17);
            endView.setY(end.getY() - 53);
            endView.setOnMouseClicked(endClearSelect);
            addToPath(endSelect, end.getFloor());
            addToPath(endView, end.getFloor());
            addToPath(endViewselect, end.getFloor());

            endNodeLabel.setText(end.getLongName());
            endNodeLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            endNodeLabel.setTextFill(Color.BLACK);
            endNodeLabel.setLayoutX(end.getX() + 30);
            endNodeLabel.setLayoutY(end.getY() - 30);
            addToPath(endNodeLabel, end.getFloor());
        }
        else if (end != null) {
            removeFromPath(endSelect, end.getFloor());
            removeFromPath(endViewselect, end.getFloor());
//            endLabel.setText("None Selected");
        }
        else {
//            endLabel.setText("None Selected");
        }
    }

    public void drawNodes() {
        state = State.START;
        start = null;
        end = null;
        startReady = false;
        endReady = false;
//        startLabel.setText("None Selected");
//        endLabel.setText("None Selected");
        //ArrayList<Node> nodes = App.getGraph().getNodes();
        DatabaseWrapper.updateGraph();
        ArrayList<Node> nodes = DatabaseWrapper.getGraph().getNodes();
        clearPath();
        for (Map.Entry<Circle, Node> pair : circles.entrySet()) {
            removeFromAll(pair.getKey());
        }
        circles.clear();
        for (Map.Entry<ImageView, Node> pair : IconMap.entrySet()) {
            removeFromAll(pair.getKey());
        }
        IconMap.clear();
        for (Map.Entry<ImageView, Node> pair : hitboxes.entrySet()) {
            removeFromAll(pair.getKey());
        }
        hitboxes.clear();
        for (Node n : nodes) {
            try {
                ImageView i = new ImageView();
                i.setImage(App.getHitbox(n.getID()));
                i.addEventFilter(MOUSE_CLICKED, hitboxClickHandler);
                addToPath(i, n.getFloor());
                i.setOpacity(0);
                hitboxes.put(i, n);
            } catch (Exception e) {
                //if (!App.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");

            }
            if (!DatabaseWrapper.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
            if (isDrawableNode(n)) {
                ImageView imageView = new ImageView();
                imageView.setY(n.getY() - 12);
                imageView.setX(n.getX() - 12);
                Circle c = new Circle();
                c.setCenterX(n.getX());
                c.setCenterY(n.getY());
                c.setRadius(App.getNodeSize());
                addToPath(c, n.getFloor());
                imageView.addEventHandler(MOUSE_PRESSED, circleClickHandler);
                imageView.addEventHandler(MOUSE_CLICKED, clickHandler);
                imageView.addEventHandler(MOUSE_RELEASED, circleMouseReleaseHandler);
//                    c.addEventHandler(MouseEvent.MOUSE_PRESSED, circleClickHandler);
//                    c.addEventHandler(MouseEvent.MOUSE_RELEASED, circleMouseReleaseHandler);
//                    c.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
                App.setColor(n, c);
                App.setIcon(n, imageView);
                addToPath(imageView, n.getFloor());
                IconMap.put(imageView, n);
                circles.put(c, n);
            }
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
    private void initialize() {
        wongThread = new Thread(new Runnable() {
            //Thread to orient the wongs
            @Override
            public void run() {
                while (true) {
                    for (Rectangle wong : wongs) {
                        Node now = nextNode.get(wong);
                        //System.out.println("Wong is at X: " + (wong.getX() + wong.getTranslateX() + (wong.getWidth()/2)) + ", Y: " + (wong.getY() + wong.getTranslateY() + (wong.getHeight()/2)) + ". Target node, " + now.getID() + ", is at X: " + now.getX() + ", Y: " + now.getY());
                        if (hasReached(wong, now)) { //Rectangle has reached its destination, time to update its angle
                            Node next;
                            if (pathChunks.get(wong).indexOf(now) == 0) { //The node we just reached is the first one, so we wrap around to the last
                                next = pathChunks.get(wong).get(pathChunks.get(wong).size() - 1);
                            }
                            else {
                                next = pathChunks.get(wong).get(pathChunks.get(wong).indexOf(now) - 1);
                                if (now.getX() == next.getX() && now.getY() == next.getY()) { //Nodes are at the same place
                                    goRight(wong);
                                } else {
                                    double angle = Math.atan2(now.getX() - next.getX(), now.getY() - next.getY());
                                    double pi4 = Math.PI / 4;
                                    double pi34 = pi4 * 3;
                                    if (pi34 >= angle && pi4 <= angle) {
                                        goLeft(wong);
                                    } else if (-pi34 <= angle && -pi4 >= angle) {
                                        goRight(wong);
                                    } else if ((-Math.PI <= angle && -pi34 >= angle) || (Math.PI >= angle && angle >= pi34)) {
                                        goDown(wong);
                                    } else {
                                        goUp(wong);
                                    }
                                }
                            }
                            //We now need to get our direction


                            nextNode.replace(wong, next);
                        }
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        wongThread.setDaemon(true);
        wongThread.start();

        leftArrow.addEventHandler(MOUSE_PRESSED, leftClick);
        rightArrow.addEventHandler(MOUSE_PRESSED, rightClick);
        upArrow.addEventHandler(MOUSE_PRESSED, upClick);
        downArrow.addEventHandler(MOUSE_PRESSED, downClick);
        centerOut.addEventHandler(MOUSE_PRESSED, PathZOOOOM );


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
//        Auto(SearchBox);
        AutoStart(startBox);
        AutoEnd(endBox);

        oppo.getChildren().clear();
        oppo.getChildren().add(N4);

        // construct the menu drawer

        menuDrawer.setSidePane(menuBurgerContainer);
        menuDrawer.setDefaultDrawerSize(200);
        menuDrawer.setDirection(DrawerDirection.LEFT);

        legendDrawer.setSidePane(legendPane);
        legendDrawer.setDefaultDrawerSize(200);
        legendDrawer.setDirection(DrawerDirection.LEFT);
        legendDrawer.setBackground(Background.EMPTY);
        legendPane.setBackground(Background.EMPTY);
        legendContent.setBackground(Background.EMPTY);

        menuBurger.setOnMouseClicked(e -> {
//            System.out.println("clicked button");
            menuDrawer.toggle();
            legendDrawer.toggle();
        });
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
            DatabaseWrapper.addDestination(start.getID(),end.getID());
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
        for (Rectangle wong : wongs) {
            removeFromAll(wong);
        }
        for (Label l : nodeLabels) {
            removeFromAll(l);
        }
        removeFromAll(startNodeLabel);
        removeFromAll(endNodeLabel);

        displayingPath = false;
        floorsInPath.clear();
        pathes.clear();
        interFloorPaths.clear();
        wongs.clear();
        nodeLabels.clear();
        nextNode.clear();
        pathChunks.clear();
        updateStatus();
    }

    @FXML
    public void clearSelect() {
        start = null;
        end = null;
        startReady = false;
        endReady = false;
        removeFromAll(startSelect);
        removeFromAll(startViewselect);
        removeFromAll(startView);
        removeFromAll(endSelect);
        removeFromAll(endViewselect);
        removeFromAll(endView);
        startBox.clear();
        endBox.clear();
        clearPath();
    }

    private void fastestTo(String nodeType) {
        ArrayList<Node> nodesOfType = DatabaseWrapper.getGraph().getByType(nodeType);
        int pathLen = -1;
        ArrayList<Node> finalPath = new ArrayList<>();
        Node finalNode = null;
        for (Node n : nodesOfType) {
            if (!App.getLocation().getID().equals(n.getID())) {
                NavigationWrapper.pathfind(App.getLocation(), n);
                ArrayList<Node> p = NavigationWrapper.getPath();
                if ((pathLen < 0 || getLen(p) < pathLen)) {
                    pathLen = getLen(p);
                    finalPath.clear();
                    finalPath.addAll(p);
                    finalNode = n;
                }
            }
        }

        start = App.getLocation();
        end = finalNode;
        endReady = true;
        startReady = true;

//        startLabel.setText(start.getLongName());
//        endLabel.setText(end.getLongName());
        updateStatus();
        pathfind();
    }

    @FXML
    private void nearestBathroom() {
        fastestTo("REST");
    }

    @FXML
    private void nearestStairs() {
        fastestTo("STAI");
    }

    @FXML
    private void nearestElevator() {
        fastestTo("ELEV");
    }

    @FXML
    private void nearestFood() {
        fastestTo("RETL");
    }

    @FXML
    private void nearestExit() {
        fastestTo("EXIT");
    }

    private int getLen(ArrayList<Node> p) { //Length of the path - for now just the number of nodes in the path
        return p.size();
    }

    private void drawPath() {
        System.out.println();
        clearPath();
        if (path.size() == 0){
            displayingPath = true;
            updateStatus();
            return; //No path to draw
        }
       // System.out.println("a");
 //       for(int i = path.size() - 1; i >=0 ; i--)
        //The path can be modeled as a long string of nodes on the same floor, over which we want a circle to travel, broken up by pairs of nodes on different floors
        int i = 0;
        int startX = 0;
        int startY = 0;
        int startFloor = 1;
        floorsInPath.add(start.getFloor());
        floorsInPath.add(end.getFloor());
        System.out.println(path);
        while (i < path.size() - 1) { //Iterate over every adjacent pair in the path
            Node n1 = path.get(i);
            Node n2 = path.get(i+1);
            i++;
            if (!floorsInPath.contains(n1.getFloor()) && (i == 0 || (!n1.getNodeType().equals("STAI") && !n1.getNodeType().equals("ELEV")))) floorsInPath.add(n1.getFloor());
            if (!floorsInPath.contains(n2.getFloor()) && (i+2 == path.size() || (!n2.getNodeType().equals("STAI") && !n2.getNodeType().equals("ELEV")))) floorsInPath.add(n2.getFloor());

            Path pathe = new Path();
            boolean firstTime = true;
            ArrayList<Node> pathChunk = new ArrayList<>();
            System.out.println("N1: " + n1.getID() + ", N2: " + n2.getID());
            while (n1.getFloor() == n2.getFloor()) { //Iterate over every adjacent pair in our pathChunk
                System.out.println(n1.getFloor());
                MoveTo move;
                LineTo move2;
                if (firstTime) {
                    startX = n1.getX();
                    startY = n1.getY();
                    startFloor = n1.getFloor();
                    move = new MoveTo(startX, startY);
                    pathe.getElements().add(move);
                    pathChunk.add(n1);
                }
                else {
                    move2 = new LineTo(n1.getX(), n2.getY());
                    pathe.getElements().add(move2);
                    if (!pathChunk.contains(n1)) pathChunk.add(n1);
                }
                LineTo line = new LineTo(n2.getX(), n2.getY());
                pathe.getElements().add(line);
                if (!pathChunk.contains(n2)) pathChunk.add(n2);
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
                if (!firstTime) i++;

                if (i + 1 < path.size()) { //If we've not reached the end of the given path, we need to keep going
                    n1 = path.get(i);
                    n2 = path.get(i+1);
                    firstTime = false;
                }
                else {
                    break;
                }
            }
            if (i + 1 < path.size()) { //If we're out here and we still have room to go, it's because there's a multi-floor path
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
                } else {
                    c.setStroke(Color.ORCHID);
                    c2.setStroke(Color.DARKGREEN);
                }
                c.setRadius(App.getNodeSize() + 5);
                c2.setRadius(App.getNodeSize() + 5);
                c.setStrokeWidth(10);
                c2.setStrokeWidth(10);
                c.addEventHandler(MouseEvent.MOUSE_CLICKED, interFloorPathHandler);
                c2.addEventHandler(MouseEvent.MOUSE_CLICKED, interFloorPathHandler);
                interFloorPaths.put(c, n1);
                interFloorPaths.put(c2, n2);
                addToPath(c, n1.getFloor());
                addToPath(c2, n2.getFloor());

                l.setText(n1.getLongName());
                l.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                l.setTextFill(Color.BLACK);
                l.setLayoutX(n1.getX() + 30);
                l.setLayoutY(n1.getY() - 30);
                addToPath(l, n1.getFloor());
                nodeLabels.add(l);

                l2.setText(n2.getLongName());
                l2.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                l2.setTextFill(Color.BLACK);
                l2.setLayoutX(n2.getX() + 30);
                l2.setLayoutY(n2.getY() - 30);
                addToPath(l2, n2.getFloor());
                nodeLabels.add(l2);
            }

            if (pathChunk.size() > 0) { //If we've touched the inner while loop, we wanna run this to animate wong on our pathChunk
                PathTransition pathTransition = new PathTransition();
                //Circle wong = new Circle();
                Rectangle wong = new Rectangle();
                ImagePattern imagePattern = new ImagePattern(wongFront);
                //wong.setX(start.getX());
                //wong.setY(start.getY());
                //wong.setX(n2.getX());
                //wong.setY(n2.getY());
                wong.setX(-10000);
                wong.setY(-10000);
                wong.setHeight(80);
                wong.setWidth(80);
                wong.setFill(imagePattern);
                pathTransition.setNode(wong);
                //System.out.println("size: " + (path.size() + (path.size() / 4)));
                //25 is an alright value

                pathTransition.setDuration(Duration.seconds(pathChunk.size() * 0.8));
                pathTransition.setCycleCount(PathTransition.INDEFINITE);

                pathTransition.setPath(pathe);
                pathTransition.setAutoReverse(false);
                pathTransition.setRate(-1);


                pathChunks.put(wong, pathChunk);
                System.out.println(pathChunk);
                nextNode.put(wong, pathChunks.get(wong).get(pathChunks.get(wong).size() - 1));
                wongs.add(wong);
                addToPath(wong, startFloor);
                pathTransition.play();
//            pathTransition.jumpTo("end");
            }

        }

        displayingPath = true;
        if (floor != start.getFloor()) {
            floor = start.getFloor();
            stateMachine(floor);
        }
        updateStatus();
    }

    @FXML
    private void swap() {
        Node buf = start;
        if(buf == null) return;
        start = end;
        end = buf;
        startBox.setText(start.getLongName());
        endBox.setText(end.getLongName());
        updateStatus();
        pathfind();
    }


    private boolean hasReached(Rectangle wong, Node dest) {
        return Math.abs(wong.getTranslateX() + wong.getX() + (wong.getWidth()/2) - dest.getX()) < 5 && Math.abs(wong.getTranslateY() + wong.getY() + (wong.getHeight()/2) - dest.getY()) < 5;
    }

    private void goRight(Rectangle wong) {
        wong.setFill(new ImagePattern(wongRight));
    }

    private void goDown(Rectangle wong) {
        wong.setFill(new ImagePattern(wongFront));
    }

    private void goUp(Rectangle wong) {
        wong.setFill(new ImagePattern(wongBack));
    }

    private void goLeft(Rectangle wong) {
        wong.setFill(new ImagePattern(wongLeft));
    }

    //path is an arraylist of nodes
    private void getTextPath() {
        if (path.size() == 0) {
            return;
        }
        App.getTextpath().clear();

        TextPathBuilder tpb = new TextPathBuilder(20,App.getPixMet(),App.getPixFoo(), App.getUnit());

        // the path is given in the reverse order...
        ArrayList<Node> reversedPath = new ArrayList<Node>();
        for(int index = path.size()-1; index >= 0; index --){
            Node n = path.get(index);
            reversedPath.add(n);
        }


        tpb.setNodes(reversedPath);
        tpb.generateTextDirections();
        String directions = tpb.getCleanTextDirections();
        App.getTextpath().add(directions);

//
//        String direction = "";
//        boolean didContinue = false;
//        for(int i = path.size() - 1; i >= 0; i--) {
//            String name = path.get(i).getLongName();
//            if(i == 0) {
//                App.getTextpath().add("Arrived at: " + name);
//                continue;
//            }
//            if(direction.equals(getDirection(path.get(i), path.get(i - 1)))) {
//                if(!didContinue) {
//                    App.getTextpath().add("Continue through " + name);
//                    didContinue = true;
//                }
//                continue;
//            }
//            didContinue = false;
//            direction = getDirection(path.get(i), path.get(i - 1));
//            App.getTextpath().add(direction + name);
//        }
    }

    // this function will be deprecated
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
        clearSelect();
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
    private void removeFromAll(javafx.scene.Node e) {
        NodesPane1.getChildren().remove(e);
        NodesPane2.getChildren().remove(e);
        NodesPane3.getChildren().remove(e);
        NodesPane4.getChildren().remove(e);
        NodesPane5.getChildren().remove(e);
    }

    @FXML
    public void openTreeView(ActionEvent event) {
        App.loadTreeView();
        App.getTreeViewPop().getContent().clear();
        App.getTreeViewPop().getContent().add(App.getTreeView());
        App.getTreeViewController().setMaster(this);
        App.getTreeViewPop().show(App.getPrimaryStage());
    }

    public void getSelectionFromDir(String longName) {
//        SearchBox.setText(longName);
    }

    @FXML
    private void one(){
        oppo.getChildren().clear();
        oppo.getChildren().add(N1);
        floor = 1;
//        floorLabel.setText("1");
        MapGes1.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes1.getCurrentScale() - 3000, MapGes1.targetPointAtViewportCentre());
    }

    @FXML
    private void two(){
        oppo.getChildren().clear();
        oppo.getChildren().add(N2);
        floor = 2;
//        floorLabel.setText("2");
        MapGes2.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes2.getCurrentScale() - 3000, MapGes2.targetPointAtViewportCentre());
    }

    @FXML
    private void three(){
        oppo.getChildren().clear();
        oppo.getChildren().add(N3);
        floor = 3;
//        floorLabel.setText("3");
        MapGes3.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes3.getCurrentScale() - 3000, MapGes3.targetPointAtViewportCentre());
    }

    @FXML
    private void four(){
        oppo.getChildren().clear();
        oppo.getChildren().add(N4);
        floor = 4;
//        floorLabel.setText("4");
        MapGes4.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes4.getCurrentScale() - 3000, MapGes4.targetPointAtViewportCentre());
    }

    @FXML
    private void five(){
        oppo.getChildren().clear();
        oppo.getChildren().add(N5);
        floor = 5;
//        floorLabel.setText("5");
        MapGes5.animate(Duration.millis(200))
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(MapGes5.getCurrentScale() - 3000, MapGes5.targetPointAtViewportCentre());
    }

}
