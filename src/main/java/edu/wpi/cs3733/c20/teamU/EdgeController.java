package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class EdgeController {

  @FXML private Button addButton, editButton, removeButton, backButton;
  @FXML private TableView<Edge> edges;
  @FXML private TableColumn<Edge, String> edgeIdColumn, startNodeColumn, endNodeColumn;

  Database graph;
  EdgeEditController toEdit;
  private Edge selected;

  public EdgeController() {}

  public void setAttributes(EdgeEditController c) { toEdit = c;}


  @FXML
  private void backToAdmin() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
    App.getPopup().show(App.getPrimaryStage());
    toEdit.update();
  }

  /**
   * Changes scene to Edit_node...
   * @param event
   * @throws IOException
   */
  @FXML
  public void editScreen(ActionEvent event) throws IOException {
      App.getPopup().getContent().clear();
      App.getPopup().getContent().add(App.getEditEdge());
      App.getPopup().show(App.getPrimaryStage());
      toEdit.update();
    } //Admin edit nodes interface

  public void addScreen() {
    App.setEdgeEdit(null);
    toEdit.update();
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getEditEdge());
    App.getPopup().show(App.getPrimaryStage());
  }



  @FXML
  private void detectClick() {
    if(edges.getSelectionModel().getSelectedItem() != null) {
      App.setEdgeEdit(edges.getSelectionModel().getSelectedItem());
      toEdit.update();
      editButton.setDisable(false);
      selected = edges.getSelectionModel().getSelectedItem();
    }
      else editButton.setDisable(true);
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

  private void update() {
    edges.setItems(hashToOblist());
  }

  @FXML
  public void remove() {
    if (selected != null) {
      Database.delEdge(selected.getID());
      update();
    }
  }

  /**
   * Function to initialize all the columns in Tableview
   */
  @FXML
  private void initialize() {
    editButton.setDisable(true);
    edgeIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
    startNodeColumn.setCellValueFactory(new PropertyValueFactory<>("startID"));
    endNodeColumn.setCellValueFactory(new PropertyValueFactory<>("endID"));
    edges.setItems(hashToOblist());
  }
}
