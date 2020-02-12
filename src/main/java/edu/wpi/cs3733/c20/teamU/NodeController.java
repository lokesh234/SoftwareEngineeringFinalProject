package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NodeController {

  @FXML private Button edit;
  @FXML private Button export;
  //tableview & columns in fxml file
  @FXML private TableView<edu.wpi.cs3733.c20.teamU.Node> nodeTable;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, String> NodeID;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, Integer> xCoord;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, Integer> yCoord;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, Integer> floor;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, String> building;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, String> nodeType;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, String> longName;
  @FXML private TableColumn<edu.wpi.cs3733.c20.teamU.Node, String> shortName;
  Database graph;
  editModeController toEdit;

  public NodeController() {}

  public void refreshTable() {
    nodeTable.setItems(hashToOblist());
    nodeTable.refresh();
  }

  public void setAttributes(editModeController editor) {
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
  private ObservableList<edu.wpi.cs3733.c20.teamU.Node> hashToOblist(){
    ObservableList<edu.wpi.cs3733.c20.teamU.Node> nodes = FXCollections.observableArrayList();

    //getting nodes created in Hashmap from database
    HashMap<String, edu.wpi.cs3733.c20.teamU.Node> graph = new HashMap<String, edu.wpi.cs3733.c20.teamU.Node>();
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
