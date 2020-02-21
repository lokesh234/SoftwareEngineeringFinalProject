package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class EdgeEditController {
  @FXML private TableView<Node> table;
  @FXML private TableColumn<Node, String> idColumn, buildingColumn, nodeTypeColumn, shortNameColumn, longNameColumn;
  @FXML private TableColumn<Node, Integer> xColumn, yColumn, floorColumn;
  @FXML private Button startNode, endNode, saveButton, cancelButton;
  @FXML private Label startLabel, endLabel, idLabel;
  private Edge selectedEdge;
  private Node selectedStartNode, selectedEndNode;
  private State state;
  private String id;
  private boolean editing = false;
  private EdgeViewScreenController master;

  private enum State {
    n, s, e;
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

  public void update() {
    table.getItems().clear();
    startLabel.setText("No Selection");
    endLabel.setText("No Selection");
    idLabel.setText("");
    table.setItems(hashToOblist());
    if (App.getEdgeEdit() != null) { //We're editing a selected edge
      selectedEdge = App.getEdgeEdit();
      //selectedStartNode = selectedEdge.getStart();
      //selectedEndNode = selectedEdge.getEnd();
      selectedStartNode = DatabaseWrapper.edgeGetStart(selectedEdge);
      selectedEndNode = DatabaseWrapper.edgeGetEnd(selectedEdge);
      startLabel.setText(selectedStartNode.getID());
      endLabel.setText(selectedEndNode.getID());
      id = selectedEdge.getID();
      idLabel.setText(id);
      editing = true;
    }
    else {
      id = "";
      selectedEdge = null;
      selectedEndNode = null;
      selectedStartNode = null;
      editing = false;
    }
  }

  public void setMaster(EdgeViewScreenController m) { master = m;}

  @FXML
  public void selectStart() {
    startLabel.setText("Select a node");
    state = State.s;
  }

  @FXML
  public void selectEnd() {
    endLabel.setText("Select a node");
    state = State.e;
  }

  @FXML
  public void save() {
    if (editing) {
      //Database.editEdge(selectedEdge.getID(), selectedStartNode.getNodeID(), selectedEndNode.getNodeID());
      DatabaseWrapper.editEdge(selectedEdge.getID(), DatabaseWrapper.getNodeID(selectedStartNode), DatabaseWrapper.getNodeID(selectedEndNode));
      update();
    }
    else if (selectedEndNode != null && selectedStartNode != null) {
      DatabaseWrapper.addEdge(selectedStartNode.getID(), selectedEndNode.getID());
      update();
    }
    else { //Not ready to save, don't do anything
      return;
    }
    master.update();
    back();
  }

  @FXML
  private void detectClick() {
    if(table.getSelectionModel().getSelectedItem() != null) {
      if (state == State.s) {
        selectedStartNode = table.getSelectionModel().getSelectedItem();
        startLabel.setText(selectedStartNode.getID());
        state = State.n;
      }
      else if (state == state.e) {
        selectedEndNode = table.getSelectionModel().getSelectedItem();
        endLabel.setText(selectedEndNode.getID());
        state = State.n;
      }
    }
    if (state == State.n && selectedStartNode != null && selectedEndNode != null) {
      id = selectedStartNode.getID() + "_" + selectedEndNode.getID();
      idLabel.setText(id);
    }
//    else nodeTable.getSelectionModel().clearSelection();
  }


  public void back() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdminEdge());
    App.getPopup().show(App.getPrimaryStage());
  }

  public void initialize() {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    xColumn.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
    yColumn.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
    floorColumn.setCellValueFactory(new PropertyValueFactory<>("Floor"));
    buildingColumn.setCellValueFactory(new PropertyValueFactory<>("Building"));
    nodeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("NodeType"));
    longNameColumn.setCellValueFactory(new PropertyValueFactory<>("LongName"));
    shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));
    table.setItems(hashToOblist());
  }
}
