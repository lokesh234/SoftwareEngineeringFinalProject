package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;

public class DeleteNodeController {

  @FXML private Button confirm;
  @FXML private Button cancel;
  @FXML private TextField text1, text2, text3, text4, text5, text6, text7, text8;;
  private String ID, x, y, floor, build, type, shortName, longName;
  Node selectedNode;
  NodeController nodeController;

  public DeleteNodeController() {}

  /**
   * Change scene when this is called...
   */
  public void adminScreen(ActionEvent event) throws IOException {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdminNode());
    App.getPopup().show(App.getPrimaryStage());
  }

  public void setAttributes(NodeController nodeController1) {
    nodeController = nodeController1;
  }

  @FXML
  private void confirmChange() {
    overWrite();
    HashMap<String, Node> graph = new HashMap<String, Node>();
    Database.getNodes(graph);
    nodeController.refreshTable();
    cancel.fire();
  }

  /**
   * function makes changes to node values
   */
  private void overWrite() { // does not count in for empty values
    Database.delNode(ID);
    text1.clear();
    text2.clear();
    text3.clear();
    text4.clear();
    text5.clear();
    text6.clear();
    text7.clear();
    text8.clear();
  }

  /**
   * function sets prompt messages for edit mode scene
   */
  public void selectedNodeVal() {
    selectedNode = App.getNodeEdit();
    //ID = selectedNode.getNodeID();
    ID = DatabaseWrapper.getNodeID(selectedNode);
    x = Integer.toString(selectedNode.getX());
    y = Integer.toString(selectedNode.getY());
    floor = Integer.toString(selectedNode.getFloor());
    build = selectedNode.getBuilding();
    type = selectedNode.getNodeType();
    longName = selectedNode.getLongName();
    shortName = selectedNode.getShortName();

    text1.setPromptText(ID);
    text2.setPromptText(x);
    text3.setPromptText(y);
    text4.setPromptText(floor);
    text5.setPromptText(build);
    text6.setPromptText(type);
    text7.setPromptText(longName);
    text8.setPromptText(shortName);
  }

//  /**
//   * function is used from admin screen to set user designated Node
//   * @param designatedNode
//   */
//  public void setNode(edu.wpi.cs3733.c20.teamU.Database.Node designatedNode) {selectedNode = designatedNode;}

  @FXML
  public void initialize() {
    text1.setDisable(true);
    text2.setDisable(true);
    text3.setDisable(true);
    text4.setDisable(true);
    text5.setDisable(true);
    text6.setDisable(true);
    text7.setDisable(true);
    text8.setDisable(true);
  }
}
