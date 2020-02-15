package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NodeViewScreenController {

  @FXML private Button edit;
  @FXML private Button export;
  //tableview & columns in fxml file
  @FXML private TableView<Node> nodeTable;
  @FXML private TableColumn<Node, String> NodeID;
  @FXML private TableColumn<Node, Integer> xCoord;
  @FXML private TableColumn<Node, Integer> yCoord;
  @FXML private TableColumn<Node, Integer> floor;
  @FXML private TableColumn<Node, String> building;
  @FXML private TableColumn<Node, String> nodeType;
  @FXML private TableColumn<Node, String> longName;
  @FXML private TableColumn<Node, String> shortName;
  Database graph;
  NodeEditController toEdit;

  public NodeViewScreenController() {}

  public void refreshTable() {
    nodeTable.getItems().clear();
    nodeTable.setItems(hashToOblist());
  }

  public void setAttributes(NodeEditController editor) {
    toEdit = editor;
  }
  @FXML
  private void backToAdmin() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
    App.getPopup().show(App.getPrimaryStage());
  }

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
    Database.delNode(nodeTable.getSelectionModel().getSelectedItem().getNodeID());
    refreshTable();
  }

  @FXML
  private void detectClick() {
    if(nodeTable.getSelectionModel().getSelectedItem() != null) {
      edit.setDisable(false);
      App.setNodeEdit(nodeTable.getSelectionModel().getSelectedItem());
      toEdit.selectedNodeVal();
    }
//    else edit.setDisable(true);
//    else nodeTable.getSelectionModel().clearSelection();
  }

  /**
   * Function to create an ObservableList from nodes in Hashmap
   * @return ObservableList<edu.wpi.teamname.NOde> oblist of location nodes
   */
  private ObservableList<Node> hashToOblist(){
    ObservableList<Node> nodes = FXCollections.observableArrayList();

    //getting nodes created in Hashmap from database
    HashMap<String, Node> graph = new HashMap<String, Node>();
    Database.getNodes(graph);

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
    edit.setDisable(true);
    NodeID.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    xCoord.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
    yCoord.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
    floor.setCellValueFactory(new PropertyValueFactory<>("Floor"));
    building.setCellValueFactory(new PropertyValueFactory<>("Building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<>("NodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<>("LongName"));
    shortName.setCellValueFactory(new PropertyValueFactory<>("shortName"));
    nodeTable.setItems(hashToOblist());
  }

}
