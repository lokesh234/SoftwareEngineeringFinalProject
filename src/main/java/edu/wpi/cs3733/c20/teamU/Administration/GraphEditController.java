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

import java.io.IOException;
import java.util.*;

public class GraphEditController {

  @FXML private Button backButton, nodeModeButton, edgeModeButton, addButton, editButton, removeButton, startButton, endButton, posButton;
  @FXML private Label startLabel, endLabel, posLabel, statusLabel;
  @FXML private AnchorPane NodesPane;
  private HashMap<Circle, Node> circles = new HashMap<>();
  private HashMap<Path, Edge> lines = new HashMap<>();
  private int drawnFloor = 4;
  private Color green = Color.web("#39ff14");
  //Database graph;
  NodeEditController toEdit;
  boolean nodeMode = true;
  private State state = State.neutral;

  private enum State {
    neutral, selectStart, selectEnd, selectPos;
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
    state = State.selectStart;
  }

  @FXML
  protected void endState() {
    state = State.selectEnd;
  }

  @FXML
  protected void posState() {
    state = State.selectPos;
  }

  protected void update() {
    if (nodeMode) {
      startButton.setVisible(false);
      endButton.setVisible(false);
      startLabel.setVisible(false);
      endLabel.setVisible(false);
      posButton.setVisible(true);
      posLabel.setVisible(true);
      editButton.setVisible(true);
      statusLabel.setText("Node Mode");
      clearEdges();
      clearNodes();
      drawNodes();
    }
    else {
      startButton.setVisible(true);
      endButton.setVisible(true);
      startLabel.setVisible(true);
      endLabel.setVisible(true);
      posButton.setVisible(false);
      posLabel.setVisible(false);
      editButton.setVisible(false);
      statusLabel.setText("Edge Mode");
      clearEdges();
      clearNodes();
      drawEdges();
      drawNodes();
    }
  }

  private void clearEdges() {
    if (lines.size() > 0) {
      for (Map.Entry<Path, Edge> pair : lines.entrySet()) {
        Edge n = pair.getValue();
        Path c = pair.getKey();
        removeFromPath(c);
      }
    }
    lines.clear();
  }

  private void clearNodes() {
    if (circles.size() > 0) {
      for (Map.Entry<Circle, Node> pair : circles.entrySet()) {
        Node n = pair.getValue();
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
        lines.put(pathe, e);
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
      System.out.println("Circle clicked");
    }
  };

  EventHandler<MouseEvent> screenClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      System.out.println("Screen clicked!");
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
