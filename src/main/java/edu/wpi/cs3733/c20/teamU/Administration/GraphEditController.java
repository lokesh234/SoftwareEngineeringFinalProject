package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.*;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class GraphEditController {

  @FXML private Button backButton, nodeModeButton, edgeModeButton, addButton, editButton, removeButton, startButton, endButton;
  @FXML private Label startLabel, endLabel, statusLabel, statusLabel1, floorLabel, locLabel;
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
  @FXML
  VBox oppo;

  private HashMap<Circle, Node> circles = new HashMap<>();
  private HashMap<Edge, Path> lines = new HashMap<>();
  private Path extraLine;
  private int extraLineFloor;
  private int floor = 1;
  private Color green = Color.web("#39ff14");
  private Edge selectedEdge;
  //Database graph;
  NodeEditController toEdit;
  boolean nodeMode = true;
  private State state = State.neutral;
  private Pos pos;
  private Node selectedNode;
  private Node selectedStartNode;
  private Node selectedEndNode;
  @FXML private GesturePane MapGes1;
  @FXML private GesturePane MapGes2;
  @FXML private GesturePane MapGes3;
  @FXML private GesturePane MapGes4;
  @FXML private GesturePane MapGes5;
  private HashMap<Node, Circle> interFloorPaths = new HashMap<>();
  private HashMap<Circle, Integer> extraFloorPaths = new HashMap<>();
  private HashMap<ImageView, Node> hitboxes = new HashMap<>();

  private Circle startSelect = new Circle();
  private Circle endSelect = new Circle();



  private enum State {
    neutral, selectStart, selectEnd, selectPos, selectNode, selectLocation;
  }



  private class Pos {
    protected int x, y;
    protected Pos(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public String toString() {
      return "("+Integer.toString(x)+", "+Integer.toString(y)+")";
    }
  }

  @FXML
  private void initialize() {
    NodesPane1.getChildren().add(new ImageView(App.getFloor1()));
    NodesPane2.getChildren().add(new ImageView(App.getFloor2()));
    NodesPane3.getChildren().add(new ImageView(App.getFloor3()));
    NodesPane4.getChildren().add(new ImageView(App.getFloor4()));
    NodesPane5.getChildren().add(new ImageView(App.getFloor5()));
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
  }


  public GraphEditController() {}



  public void setAttributes(NodeEditController editor) {
    NodesPane1.addEventHandler(MouseEvent.MOUSE_CLICKED, screenClickHandler);
    NodesPane2.addEventHandler(MouseEvent.MOUSE_CLICKED, screenClickHandler);
    NodesPane3.addEventHandler(MouseEvent.MOUSE_CLICKED, screenClickHandler);
    NodesPane4.addEventHandler(MouseEvent.MOUSE_CLICKED, screenClickHandler);
    NodesPane5.addEventHandler(MouseEvent.MOUSE_CLICKED, screenClickHandler);
    toEdit = editor;
  }
  @FXML
  private void backToAdmin() {
    state = State.neutral;
    App.getHome().setOpacity(0.5);
    App.getHome().setDisable(true);
    App.getPrimaryStage().setScene(App.getHomeScene());
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
    App.getPopup().show(App.getPrimaryStage());
  }

  @FXML
  protected void enterNodeMode() {
    nodeMode = true;
    update();
  }

  @FXML
  protected void exitNodeMode() {
    nodeMode = false;
    update();
  }

  @FXML
  protected void add() {
    if (nodeMode) { //Add node
      if (selectedNode != null) { //We're editing a node!
        if (pos != null) { //We're editing a node, and the position will default to the selected position!
          App.getEditController().selectedNodeVal(selectedNode, pos.x, pos.y);
        }
        else {
          App.getEditController().selectedNodeVal(selectedNode);
        }
        editScreen();
        updateButtons();
      }
      else { //We're adding a node!
        if (pos != null) { //Adding a node with preset position
          App.getAddNodeScreenController().setDefaultPos(pos.x, pos.y);
        }
        addNodeScreen();
        updateButtons();
      }
    }
    else { //Add edge
      DatabaseWrapper.addEdge(selectedStartNode.getID(), selectedEndNode.getID());
      removeFromPath(extraLine, extraLineFloor);
      for (Map.Entry<Circle, Integer> pair : extraFloorPaths.entrySet()) {
        removeFromPath(pair.getKey(), pair.getValue());
      }
      updateButtons();
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


  @FXML private void zoomOut(){
    ZoomOutMachine(floor);
  }


  @FXML
  protected void remove() {
    if (nodeMode) { //Remove node
      DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), App.getUser().getCred(), "Remove Node", selectedNode.getID());
      DatabaseWrapper.delNode(selectedNode.getID());
      selectedNode = null;
    }
    else { //Remove edge
     DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), App.getUser().getCred(), "Remove Edge", DatabaseWrapper.getGraph().getEdge(selectedStartNode, selectedEndNode).getID());
     if (!DatabaseWrapper.delEdge(DatabaseWrapper.getGraph().getEdge(selectedStartNode, selectedEndNode).getID())) System.out.println("oh no");
     selectedEdge = null;
     selectedStartNode = null;
     selectedEndNode = null;
    }
    updateButtons();
    redraw(!nodeMode);
  }

  @FXML
  protected void startState() {
    if (nodeMode) {
      state = State.selectNode;
      selectedNode = null;
    }
    else {
      selectedStartNode = null;
      state = State.selectStart;
    }
    startLabel.setText("None selected");
    updateButtons();
  }

  @FXML
  protected void endState() {
    if (!nodeMode) {
      state = State.selectEnd;
      endLabel.setText("None selected");
      selectedEdge = null;
    }
    else {
      state = State.selectPos;
      endLabel.setText("(X, Y)");
      pos = null;
    }
    updateButtons();
  }

  @FXML
  protected void locationState() {
    state = State.selectLocation;
    updateButtons();
    locLabel.setText("None Selected");
  }

  protected void update() {
    stateMachine(floor);
    updateButtons();
    selectedNode = null;
    pos = null;
    selectedEndNode = null;
    selectedStartNode = null;
    extraLine = null;
    selectedEdge = null;
    clearExtraLine();
    if (App.getLocation() != null) {
      locLabel.setText(App.getLocation().getLongName());
    }
    if (nodeMode) {
      startButton.setText("Select Node");
      endButton.setText("Select Position");
      startLabel.setText("None Selected");
      endLabel.setText("None Selected");



      editButton.setVisible(true);
      statusLabel.setText("Node Mode");
      redraw(false);
    }
    else {
      startButton.setText("Select Start");
      endButton.setText("Select End");
      startLabel.setText("None Selected");
      endLabel.setText("None Selected");



      editButton.setVisible(false);
      statusLabel.setText("Edge Mode");
      redraw(true);
    }
    updateButtons();
  }

  protected void redraw(boolean edges) {
    clearEdges();
    clearNodes();
    if (edges) drawEdges();
    drawNodes();
  }



  private void updateButtons() {
    removeFromAll(startSelect);
    removeFromAll(endSelect);
//    for (Map.Entry<Node, Circle> pair : interFloorPaths.entrySet()) {
//      removeFromAll(pair.getValue());
//    }
    for (Map.Entry<Circle, Integer> pair : extraFloorPaths.entrySet()) {
      removeFromAll(pair.getKey());
    }
    if (extraLine != null) removeFromAll(extraLine);
    if (selectedEdge != null) {
      if (selectedEdge.getStart().getFloor() == selectedEdge.getEnd().getFloor()) lines.get(selectedEdge).setStroke(Color.BLACK);
      else {
        if (selectedEdge.getEnd().getFloor() < selectedEdge.getStart().getFloor()) {
          interFloorPaths.get(selectedEdge.getStart()).setStroke(Color.ORCHID);
          interFloorPaths.get(selectedEdge.getEnd()).setStroke(Color.DARKGREEN);
        }
        else {
          interFloorPaths.get(selectedEdge.getStart()).setStroke(Color.DARKGREEN);
          interFloorPaths.get(selectedEdge.getEnd()).setStroke(Color.ORCHID);
        }
      }
    }
    if (nodeMode) {
      addButton.setDisable(selectedNode != null);
      editButton.setDisable(selectedNode == null);
      removeButton.setDisable(selectedNode == null);
      if (selectedNode != null) {
        startSelect.setCenterX(selectedNode.getX());
        startSelect.setCenterY(selectedNode.getY());
        startSelect.setFill(Color.TRANSPARENT);
        startSelect.setStroke(Color.YELLOW);
        startSelect.setStrokeWidth(5);
        startSelect.setRadius(20);
        addToPath(startSelect, selectedNode.getFloor());
      }
    }
    else {
      if (selectedStartNode != null) {
        startSelect.setCenterX(selectedStartNode.getX());
        startSelect.setCenterY(selectedStartNode.getY());
        startSelect.setFill(Color.TRANSPARENT);
        startSelect.setStroke(Color.YELLOW);
        startSelect.setStrokeWidth(5);
        startSelect.setRadius(20);
        addToPath(startSelect, selectedStartNode.getFloor());
      }
      if (selectedEndNode != null) {
        endSelect.setCenterX(selectedEndNode.getX());
        endSelect.setCenterY(selectedEndNode.getY());
        endSelect.setFill(Color.TRANSPARENT);
        endSelect.setStroke(Color.ORANGE);
        endSelect.setStrokeWidth(5);
        endSelect.setRadius(20);
        addToPath(endSelect, selectedEndNode.getFloor());
      }
      if (selectedStartNode != null && selectedEndNode != null) {
        clearExtraLine();
        if (!DatabaseWrapper.getGraph().getNeighborNodes(selectedStartNode).contains(selectedEndNode)) { //ya like ()?
          if (selectedStartNode.getFloor() == selectedEndNode.getFloor()) {
            extraLine = new Path(); //This is an edge that doesn't exist, so let's highlight it in green!
            extraLineFloor = selectedStartNode.getFloor();
            MoveTo move = new MoveTo(selectedEndNode.getX(), selectedEndNode.getY());
            LineTo line = new LineTo(selectedStartNode.getX(), selectedStartNode.getY());
            extraLine.getElements().add(move);
            extraLine.getElements().add(line);
            extraLine.setStroke(green);
            extraLine.setStrokeWidth(5.0);
            extraLine.getStrokeDashArray().addAll(15d, 15d);
            extraLine.setStrokeDashOffset(15d);
            addToPath(extraLine, extraLineFloor);
          }
          else {
            Node n1 = selectedEndNode;
            Node n2 = selectedStartNode;
            Circle c = new Circle();
            Circle c2 = new Circle();
            c.setCenterX(n1.getX());
            c.setCenterY(n1.getY());
            c2.setCenterY(n2.getY());
            c2.setCenterX(n2.getX());
            c.setFill(Color.TRANSPARENT);
            c2.setFill(Color.TRANSPARENT);
            c.setStroke(Color.GREEN);
            c2.setStroke(Color.GREEN);
            c.setRadius(App.getNodeSize()+5);
            c2.setRadius(App.getNodeSize()+5);
            c.getStrokeDashArray().addAll(15d, 15d);
            c.setStrokeDashOffset(15d);
            c2.getStrokeDashArray().addAll(15d, 15d);
            c2.setStrokeDashOffset(15d);
            c.setStrokeWidth(5);
            c2.setStrokeWidth(5);
            extraFloorPaths.put(c, n1.getFloor());
            extraFloorPaths.put(c2, n2.getFloor());
            addToPath(c, n1.getFloor());
            addToPath(c2, n2.getFloor());
          }
          addButton.setDisable(false);
          removeButton.setDisable(true);
        }
        else { //selectedEndNode is contained in selectedStartNode's neighbors
          selectedEdge = DatabaseWrapper.getGraph().getEdge(selectedStartNode, selectedEndNode);
          if (selectedEndNode.getFloor() == selectedStartNode.getFloor()) lines.get(selectedEdge).setStroke(Color.DARKORANGE);
          else {
            interFloorPaths.get(selectedEndNode).setStroke(Color.DARKORANGE);
            interFloorPaths.get(selectedStartNode).setStroke(Color.DARKORANGE);
          }

          addButton.setDisable(true);
          removeButton.setDisable(false);
        }
      }
      else {
        addButton.setDisable(true);
        removeButton.setDisable(true);
      }
    }

  }

  private void clearExtraLine() {
    if (extraLine != null) removeFromPath(extraLine, extraLineFloor);
    else if (extraFloorPaths.size() > 0) {
      for (Map.Entry<Circle, Integer> pair : extraFloorPaths.entrySet()) {
        removeFromPath(pair.getKey(), pair.getValue());
      }
      extraFloorPaths.clear();
    }
  }

  private void clearEdges() {
    System.out.println("no dont");
    clearExtraLine();
    if (lines.size() > 0) {
      for (Map.Entry<Edge, Path> pair : lines.entrySet()) {
        Path c = pair.getValue();
        removeFromPath(c, pair.getKey().getStart().getFloor());
      }
    }
    if (interFloorPaths.size() > 0) {
      for (Map.Entry<Node, Circle> pair : interFloorPaths.entrySet()) {
        removeFromAll(pair.getValue());
      }
    }
    interFloorPaths.clear();
    lines.clear();
    selectedEdge = null;
  }

  private void clearNodes() {
    if (circles.size() > 0) {
      for (Map.Entry<Circle, Node> pair : circles.entrySet()) {
        Circle c = pair.getKey();
        removeFromPath(c, pair.getValue().getFloor());
      }
    }
    circles.clear();
  }

  private void drawEdges() {
    DatabaseWrapper.updateGraph();
    for (Edge e : DatabaseWrapper.getGraph().getEdges()) {
      Node n1 = e.getEnd();
      Node n2 = e.getStart();
      if (e.getStart().getFloor() == e.getEnd().getFloor()) {
        MoveTo move = new MoveTo(e.getEnd().getX(),e.getEnd().getY());
        LineTo line = new LineTo(e.getStart().getX(),e.getStart().getY());
        Path pathe = new Path();
        pathe.getElements().add(move);
        pathe.getElements().add(line);
        pathe.setStroke(Color.BLACK);
        pathe.setStrokeWidth(5.0);
        addToPath(pathe, e.getStart().getFloor());
        lines.put(e, pathe);
      }
      else { //Inter-floor path!

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
            c2.setStroke(Color.DARKGREEN);
            c.setStroke(Color.ORCHID);
          }
          c.setRadius(App.getNodeSize()+5);
          c2.setRadius(App.getNodeSize()+5);
          c.setStrokeWidth(5);
          c2.setStrokeWidth(5);
          c.addEventHandler(MouseEvent.MOUSE_CLICKED, interFloorHandler);
          c2.addEventHandler(MouseEvent.MOUSE_CLICKED, interFloorHandler);

        if (!(interFloorPaths.containsKey(n1) || interFloorPaths.containsKey(n2))) { //If either ends already have one rendered, don't stack them visually
          addToPath(c, n1.getFloor());
          addToPath(c2, n2.getFloor());
        }

          interFloorPaths.put(n1, c);
          interFloorPaths.put(n2, c2);


//        c.toFront();
//        c2.toFront();


      }
    }

    for (Map.Entry<Circle, Node> pair : circles.entrySet()) {
      pair.getKey().toFront(); //Ensure that all nodes are drawn above the edges
    }
  }

  @FXML private void clickUp(ActionEvent e){
    floor++;
    stateMachine(floor);
    if(floor > 5){
      floor = 5;
    }
  }

  @FXML private void clickDown(ActionEvent e){
    floor--;
    stateMachine(floor);
    if(floor < 1){
      floor = 1;
    }
  }

  private void stateMachine(int floor){
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

  private void drawNodes() {
    ArrayList<Node> nodes = DatabaseWrapper.getGraph().getNodes();
    for (Node n : nodes) {
      try {
        ImageView i = new ImageView();
        i.setImage(App.getHitbox(n.getID()));
        i.addEventFilter(MOUSE_CLICKED, hitboxClickHandler);
        addToPath(i, n.getFloor());
        i.setOpacity(0.25);
        hitboxes.put(i, n);
      } catch (Exception e) {
        //if (!App.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");

      }
      if (!DatabaseWrapper.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
      Circle c = new Circle();
      c.setCenterX(n.getX());
      c.setCenterY(n.getY());
      c.setRadius(App.getNodeSize());
      addToPath(c, n.getFloor());
      c.addEventHandler(MouseEvent.MOUSE_PRESSED, circleClickHandler);
      c.addEventHandler(MouseEvent.MOUSE_RELEASED, circleMouseReleaseHandler);
      c.addEventFilter(MouseEvent.MOUSE_CLICKED, circleSelectHandler);
      c.setOnMouseDragged(event -> drag(event));
      App.setColor(n, c);
      circles.put(c, n);
    }
  }



  EventHandler<MouseEvent> circleClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      Circle source = (Circle) event.getSource();
      if (state != State.neutral) source.setFill(Color.YELLOW);
    }
  };

  EventHandler<MouseEvent> circleSelectHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (state == State.selectNode) {
        selectedNode = circles.get(event.getSource());
        state = State.neutral;
        startLabel.setText(selectedNode.getID());
        updateButtons();
      }
      else if (state == State.selectEnd) {
        selectedEndNode = circles.get(event.getSource());
        state = State.neutral;
        endLabel.setText(selectedEndNode.getID() + ", " + selectedEndNode.getFloor());
        updateButtons();
      }
      else if (state == State.selectStart) {
        selectedStartNode = circles.get(event.getSource());
        state = State.neutral;
        startLabel.setText(selectedStartNode.getID() + ", " + selectedStartNode.getFloor());
        updateButtons();
      }
      else if (state == State.selectLocation) {
        App.setLocation(circles.get(event.getSource()));
        state = State.neutral;
        locLabel.setText(App.getLocation().getLongName());
      }
    }
  };

  EventHandler<MouseEvent> hitboxClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (state == State.selectNode) {
        selectedNode = circles.get(event.getSource());
        state = State.neutral;
        startLabel.setText(selectedNode.getID());
        updateButtons();
      }
      else if (state == State.selectEnd) {
        selectedEndNode = circles.get(event.getSource());
        state = State.neutral;
        endLabel.setText(selectedEndNode.getID() + ", " + selectedEndNode.getFloor());
        updateButtons();
      }
      else if (state == State.selectStart) {
        selectedStartNode = circles.get(event.getSource());
        state = State.neutral;
        startLabel.setText(selectedStartNode.getID() + ", " + selectedStartNode.getFloor());
        updateButtons();
      }
      else if (state == State.selectLocation) {
        App.setLocation(circles.get(event.getSource()));
        state = State.neutral;
        locLabel.setText(App.getLocation().getLongName());
      }
    }
  };

  EventHandler<MouseEvent> lineClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (!nodeMode) {
        for (Map.Entry<Edge, Path> pair : lines.entrySet()) {
          if (pair.getValue().equals(event.getSource())) {
            selectedStartNode = pair.getKey().getStart();
            selectedEndNode = pair.getKey().getEnd();
            state = State.neutral;
            endLabel.setText(selectedEndNode.getID() + ", " + selectedEndNode.getFloor());
            startLabel.setText(selectedStartNode.getID() + ", " + selectedStartNode.getFloor());
            updateButtons();
            break;
          }
        }
      }
    }
  };

  EventHandler<MouseEvent> screenClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (state == State.selectPos) {
        pos = new Pos((int) event.getX(), (int) event.getY());
        state = State.neutral;
        updateButtons();
        endLabel.setText(pos.toString());
      }
    }
  };

  EventHandler<MouseEvent> circleMouseReleaseHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      Circle source = (Circle) event.getSource();
      App.setColor(circles.get(source), source);
       getFloorGes(floor).setGestureEnabled(true);

      if (state == State.neutral) {
        Node n = circles.get(source);
        DatabaseWrapper.editNode(n.getID(), (int) event.getX(), (int) event.getY(), n.getFloor(), n.getBuilding(), n.getNodeType(), n.getLongName(), n.getShortName());
        updateButtons();
      }
    }
  };

  private void drag(MouseEvent event) {
    if (state == State.neutral) {
      Circle c = (Circle) event.getSource();
      getFloorGes(floor).setGestureEnabled(false);
      c.setCenterX(event.getX());
      c.setCenterY(event.getY());

      Node n = circles.get(c);

      if (!nodeMode) {
        DatabaseWrapper.editNode(n.getID(), (int) event.getX(), (int) event.getY(), n.getFloor(), n.getBuilding(), n.getNodeType(), n.getLongName(), n.getShortName());
        clearEdges();
        drawEdges();
      }
    }
  }

  private void dragHitbox(MouseEvent event) {
    if (state == State.neutral) {
      ImageView c = (ImageView) event.getSource();
      getFloorGes(floor).setGestureEnabled(false);
      c.setX(event.getX());
      c.setY(event.getY());

      Node n = hitboxes.get(c);

      if (!nodeMode) {
        DatabaseWrapper.editNode(n.getID(), (int) event.getX(), (int) event.getY(), n.getFloor(), n.getBuilding(), n.getNodeType(), n.getLongName(), n.getShortName());
        clearEdges();
        drawEdges();
      }
    }
  }

  EventHandler<MouseEvent> interFloorHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      for (Map.Entry<Node, Circle> pair : interFloorPaths.entrySet()) {
        if (pair.getValue().equals(event.getSource())) {
          for (Node n : DatabaseWrapper.getGraph().getNeighborNodes(pair.getKey())) {
            if (n.getFloor() != floor) {
              floor = n.getFloor();
              break;
            }
          }
          stateMachine(floor);
        }
      }
    }
  };

  /**
   * Changes scene to Edit_node...
   */
  public void editScreen(){
      App.getPopup().getContent().clear();
      App.getPopup().getContent().add(App.getEdit());
      App.getAdminNode().setOpacity(0.5);
      App.getAdminNode().setDisable(true);
      App.getPopup().show(App.getPrimaryStage());
    } //Admin edit nodes interface

  /**
   * function to change scene to Export_CSV
   * @param event
   */
  @FXML
  public void exportScreen(ActionEvent event) {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getExport());
    App.getPopup().show(App.getPrimaryStage());
  } //Admin edit nodes interface
@FXML
  public void addNodeScreen() {
    App.getPopup().getContent().clear();
    App.getAdminNode().setOpacity(0.5);
    App.getAdminNode().setDisable(true);
    App.getPopup().getContent().add(App.getAddNode());
    App.getPopup().show(App.getPrimaryStage());
  }

  @FXML
  public void deleteNodeScreen(ActionEvent event) throws IOException {

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

  private AnchorPane getFloor(int floor) {
    switch (floor) {
      case 1:
        return NodesPane1;
      case 2:
        return NodesPane2;
      case 3:
        return NodesPane3;
      case 4:
        return NodesPane4;
      default:
        return NodesPane5;
    }
  }

  private GesturePane getFloorGes(int floor) {
    switch (floor) {
      case 1:
        return MapGes1;
      case 2:
        return MapGes2;
      case 3:
        return MapGes3;
      case 4:
        return MapGes4;
      default:
        return MapGes5;
    }
  }

  private void removeFromAll(javafx.scene.Node e) {
    removeFromPath(e, 1);
    removeFromPath(e, 2);
    removeFromPath(e, 3);
    removeFromPath(e, 4);
    removeFromPath(e, 5);
  }

}
