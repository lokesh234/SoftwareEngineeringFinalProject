package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.*;

public class GraphEditController {

  @FXML private Button backButton, nodeModeButton, edgeModeButton, addButton, editButton, removeButton, startButton, endButton;
  @FXML private Label startLabel, endLabel, statusLabel, statusLabel1;
  @FXML private AnchorPane NodesPane;
  private HashMap<Circle, Node> circles = new HashMap<>();
  private HashMap<Edge, Path> lines = new HashMap<>();
  private Path extraLine;
  private int drawnFloor = 4;
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
    NodesPane.addEventHandler(MouseEvent.MOUSE_CLICKED, screenClickHandler);
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
      addButton.setDisable(false);
      editButton.setDisable(selectedNode == null);
      removeButton.setDisable(selectedNode == null);
    }
    else if (selectedStartNode != null && selectedEndNode != null) {
      clearExtraLine();
      if (!DatabaseWrapper.getGraph().getNeighborNodes(DatabaseWrapper.getGraph().getNode(selectedStartNode.getID())).contains(selectedEndNode)) { //ya like ()?
        extraLine = new Path(); //This is an edge that doesn't exist, so let's highlight it in green!
        MoveTo move = new MoveTo(selectedEndNode.getX(),selectedEndNode.getY());
        LineTo line = new LineTo(selectedStartNode.getX(),selectedStartNode.getY());
        extraLine.getElements().add(move);
        extraLine.getElements().add(line);
        extraLine.setStroke(green);
        extraLine.setStrokeWidth(5.0);
        extraLine.getStrokeDashArray().addAll(15d, 15d);
        extraLine.setStrokeDashOffset(15d);
        addToPath(extraLine);
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

  private void clearExtraLine() { if (extraLine != null) removeFromPath(extraLine);}

  private void clearEdges() {
    clearExtraLine();
    if (lines.size() > 0) {
      for (Map.Entry<Edge, Path> pair : lines.entrySet()) {
        Path c = pair.getValue();
        removeFromPath(c);
      }
    }
    lines.clear();
  }

  private void clearNodes() {
    if (circles.size() > 0) {
      for (Map.Entry<Circle, Node> pair : circles.entrySet()) {
        Circle c = pair.getKey();
        removeFromPath(c);
      }
    }
    circles.clear();
  }

  private void drawEdges() {
    for (Edge e : DatabaseWrapper.getGraph().getEdges()) {
      if (e.getStart().getFloor() == e.getEnd().getFloor() && e.getEnd().getFloor() == drawnFloor) {
        MoveTo move = new MoveTo(e.getEnd().getX(),e.getEnd().getY());
        LineTo line = new LineTo(e.getStart().getX(),e.getStart().getY());
        Path pathe = new Path();
        pathe.getElements().add(move);
        pathe.getElements().add(line);
        pathe.setStroke(Color.BLACK);
        pathe.setStrokeWidth(5.0);
        addToPath(pathe);
        lines.put(e, pathe);
      }
    }
  }

  private void drawNodes() {
    ArrayList<Node> nodes = DatabaseWrapper.getGraph().getNodes();
    for (Node n : nodes) {
      //if (!App.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
      if (!DatabaseWrapper.getGraph().hasNeighbors(n)) System.out.println(n.getID() + " has no neighbors!");
      if (n.getFloor() == drawnFloor) {
        Circle c = new Circle();
        c.setCenterX(n.getX());
        c.setCenterY(n.getY());
        c.setRadius(App.getNodeSize());
        addToPath(c);
        c.addEventHandler(MouseEvent.MOUSE_PRESSED, circleClickHandler);
        c.addEventHandler(MouseEvent.MOUSE_RELEASED, circleMouseReleaseHandler);
        c.addEventFilter(MouseEvent.MOUSE_CLICKED, circleSelectHandler);
        circles.put(c, n);
      }
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
   * @param event
   * @throws IOException
   */
  public void editScreen(ActionEvent event) throws IOException {
      App.getPopup().getContent().clear();
      App.getPopup().getContent().add(App.getEdit());
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
  public void addNodeScreen(ActionEvent event) throws IOException {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAddNode());
    App.getPopup().show(App.getPrimaryStage());
  }

  @FXML
  public void deleteNodeScreen(ActionEvent event) throws IOException {

  }

  private void addToPath(javafx.scene.Node e) {
    NodesPane.getChildren().add(e);
  }
  private void removeFromPath(javafx.scene.Node e) {
    NodesPane.getChildren().remove(e);
  }

  @FXML
  private void detectClick() {

  }

  /**
   * Function to create an ObservableList from nodes in Hashmap
   * @return ObservableList<edu.wpi.teamname.NOde> oblist of location nodes
   */
  private ObservableList<Node> hashToOblist(){
    ObservableList<Node> nodes = FXCollections.observableArrayList();

    //getting nodes created in Hashmap from database
    HashMap<String, Node> graph = new HashMap<String, Node>();
    DatabaseWrapper.getNodes(graph);

    //add each node to the oblist
    Set keys = graph.keySet();
    for(Iterator i = keys.iterator(); i.hasNext();){
      String key = (String) i.next();
      nodes.add(graph.get(key));
    }
    return nodes;
  }

  /**
   * Function to initialize all the columns in Tableview
   */
  @FXML
  private void initialize() {

  }

}
