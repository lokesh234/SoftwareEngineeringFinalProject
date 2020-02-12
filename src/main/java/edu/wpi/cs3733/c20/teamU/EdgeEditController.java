package edu.wpi.cs3733.c20.teamU;

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

public class EdgeController {

  @FXML private Button edit;
  @FXML private Button export;
  //tableview & columns in fxml file
  @FXML private TableView<Edge> edgeTable;
  @FXML private TableColumn<Edge, String> EdgeID;
  @FXML private TableColumn<Edge, String> StartID;
  @FXML private TableColumn<Edge, String> EndID;

  Database graph;
  editModeController toEdit;

  public EdgeController() {}

  public void refreshTable() {
    edgeTable.refresh();
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
    if(edgeTable.getSelectionModel().getSelectedItem() != null) {
      edit.setDisable(false);
      App.setEdgeEdit(edgeTable.getSelectionModel().getSelectedItem());
      toEdit.selectedNodeVal();
    }
//    else edit.setDisable(true);
//    else nodeTable.getSelectionModel().clearSelection();
  }

  /**
   * Function to create an ObservableList from nodes in Hashmap
   * @return ObservableList<edu.wpi.teamname.NOde> oblist of location nodes
   */
  private ObservableList<Edge> hashToOblist(){
    ObservableList<Edge> nodes = FXCollections.observableArrayList();

    //getting nodes created in Hashmap from database
    HashMap<String, Edge> graph = new HashMap<String, Edge>();
    HashMap<String, Node> nodent = new HashMap<String, Node>();
    Database.getNodes(nodent);
    Database.getEdges(graph, nodent);

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
    EdgeID.setCellValueFactory(new PropertyValueFactory<>("edgeID"));
    StartID.setCellValueFactory(new PropertyValueFactory<>("startID"));
    EndID.setCellValueFactory(new PropertyValueFactory<>("endID"));
    edgeTable.setItems(hashToOblist());
  }
}
