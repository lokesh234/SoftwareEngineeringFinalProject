package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Navigation.Pathfinder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.*;

public class GraphEditController {

  @FXML private Button backButton, nodeModeButton, edgeModeButton, addButton, editButton, removeButton, startButton, endButton;
  @FXML private Label startLabel, endLabel, statusLabel, statusLabel1, floorLabel;
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

  private enum State {
    neutral, selectStart, selectEnd, selectPos, selectNode;
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
        update();
      }
      else { //We're adding a node!
        if (pos != null) { //Adding a node with preset position
          App.getAddNodeScreenController().setDefaultPos(pos.x, pos.y);
        }
        addNodeScreen();
        update();
      }
    }
    else { //Add edge
      DatabaseWrapper.addEdge(selectedStartNode.getID(), selectedEndNode.getID());
      removeFromPath(extraLine, extraLineFloor);
      update();
    }
  }

  @FXML
  protected void remove() {
    if (nodeMode) { //Remove node
      DatabaseWrapper.delNode(selectedNode.getID());
    }
    else { //Remove edge
      DatabaseWrapper.delEdge(DatabaseWrapper.getGraph().getEdge(selectedStartNode, selectedEndNode).getID());
    }
    update();
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
    updateStatus();
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
    updateStatus();
  }

  protected void update() {
    stateMachine(floor);
    selectedNode = null;
    pos = null;
    selectedEndNode = null;
    selectedStartNode = null;
    extraLine = null;
    selectedEdge = null;
    if (nodeMode) {
      startButton.setText("Select Node");
      endButton.setText("Select Position");
      startLabel.setText("None Selected");
      endLabel.setText("None Selected");



      editButton.setVisible(true);
      statusLabel.setText("Node Mode");
      clearEdges();
      clearNodes();
    }
    else {
      startButton.setText("Select Start");
      endButton.setText("Select End");
      startLabel.setText("None Selected");
      endLabel.setText("None Selected");



      editButton.setVisible(false);
      statusLabel.setText("Edge Mode");
      clearEdges();
      clearNodes();
      drawEdges();
    }
    drawNodes();
    updateButtons();
  }

  private void updateStatus() { //Updates only the statusLabel1 text
    updateButtons();
//    switch (state) {
//      case neutral:
//        if (nodeMode) statusLabel1.setText("SEARCH");
//        else statusLabel1.setText("SEARCH");
//        break;
//      case selectEnd:
//        statusLabel1.setText("Select Endpoint Of Edge");
//        break;
//      case selectPos:
//        statusLabel1.setText("Select Position Of New Node");
//        break;
//      case selectNode:
//        statusLabel1.setText("Select Node");
//        break;
//      case selectStart:
//        statusLabel1.setText("Select Startpoint Of Edge");
//        break;
//    }
  }

  private void updateButtons() {
    if (selectedEdge != null) {
      lines.get(selectedEdge).setStroke(Color.BLACK);
    }
    if (nodeMode) {
      addButton.setDisable(selectedNode != null);
      editButton.setDisable(selectedNode == null);
      removeButton.setDisable(selectedNode == null);
    }
    else if (selectedStartNode != null && selectedEndNode != null) {
      clearExtraLine();
      if (!DatabaseWrapper.getGraph().getNeighborNodes(DatabaseWrapper.getGraph().getNode(selectedStartNode.getID())).contains(selectedEndNode) && selectedStartNode.getFloor() == selectedEndNode.getFloor()) { //ya like ()?
        extraLine = new Path(); //This is an edge that doesn't exist, so let's highlight it in green!
        extraLineFloor = selectedStartNode.getFloor();
        MoveTo move = new MoveTo(selectedEndNode.getX(),selectedEndNode.getY());
        LineTo line = new LineTo(selectedStartNode.getX(),selectedStartNode.getY());
        extraLine.getElements().add(move);
        extraLine.getElements().add(line);
        extraLine.setStroke(green);
        extraLine.setStrokeWidth(5.0);
        extraLine.getStrokeDashArray().addAll(15d, 15d);
        extraLine.setStrokeDashOffset(15d);
        addToPath(extraLine, extraLineFloor);
        addButton.setDisable(false);
        removeButton.setDisable(true);
      }
      else { //selectedEndNode is contained in selectedStartNode's neighbors
        selectedEdge = DatabaseWrapper.getGraph().getEdge(selectedStartNode, selectedEndNode);
        lines.get(selectedEdge).setStroke(Color.DARKORANGE);

        addButton.setDisable(true);
        removeButton.setDisable(false);
      }
    }
    else {
      addButton.setDisable(true);
      removeButton.setDisable(true);
    }
  }

  private void clearExtraLine() { if (extraLine != null) removeFromPath(extraLine, extraLineFloor);}

  private void clearEdges() {
    clearExtraLine();
    if (lines.size() > 0) {
      for (Map.Entry<Edge, Path> pair : lines.entrySet()) {
        Path c = pair.getValue();
        removeFromPath(c, pair.getKey().getStart().getFloor());
      }
    }
    lines.clear();
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
      //if (!App.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
      if (!DatabaseWrapper.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
      Circle c = new Circle();
      c.setCenterX(n.getX());
      c.setCenterY(n.getY());
      c.setRadius(App.getNodeSize());
      addToPath(c, n.getFloor());
      c.addEventHandler(MouseEvent.MOUSE_PRESSED, circleClickHandler);
      c.addEventHandler(MouseEvent.MOUSE_RELEASED, circleMouseReleaseHandler);
      c.addEventFilter(MouseEvent.MOUSE_CLICKED, circleSelectHandler);
      circles.put(c, n);
    }
  }

  EventHandler<MouseEvent> circleClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      Circle source = (Circle) event.getSource();
      source.setFill(Color.YELLOW);
    }
  };

  EventHandler<MouseEvent> circleSelectHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (state == State.selectNode) {
        selectedNode = circles.get(event.getSource());
        state = State.neutral;
        startLabel.setText(selectedNode.getID());
        updateStatus();
      }
      else if (state == State.selectEnd) {
        selectedEndNode = circles.get(event.getSource());
        state = State.neutral;
        endLabel.setText(selectedEndNode.getID() + ", " + selectedEndNode.getFloor());
        updateStatus();
      }
      else if (state == State.selectStart) {
        selectedStartNode = circles.get(event.getSource());
        state = State.neutral;
        startLabel.setText(selectedStartNode.getID() + ", " + selectedStartNode.getFloor());
        updateStatus();
      }
    }
  };

  EventHandler<MouseEvent> screenClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      if (state == State.selectPos) {
        pos = new Pos((int) event.getX(), (int) event.getY());
        state = State.neutral;
        updateStatus();
        endLabel.setText(pos.toString());
      }
    }
  };

  EventHandler<MouseEvent> circleMouseReleaseHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      Circle source = (Circle) event.getSource();
      source.setFill(Color.BLACK);
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
   * @throws IOException
   */
  @FXML
  public void exportScreen(ActionEvent event) throws IOException {
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
