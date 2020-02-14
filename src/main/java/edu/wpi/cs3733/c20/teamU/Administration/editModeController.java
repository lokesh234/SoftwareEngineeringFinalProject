package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;

public class editModeController {

  @FXML private Button confirm;
  @FXML private Button cancel;
  @FXML private TextField text1, text2, text3, text4, text5, text6, text7, text8;;
  private String ID, x, y, floor, build, type, shortName, longName;
  Node selectedNode;
  NodeController nodeController;

  public editModeController() {}

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
    String userX = text2.getText();
    String userY = text3.getText();
    String userFloor = text4.getText();
    String userBuild = text5.getText();
    String userType = text6.getText();
    String userLongName = text7.getText();
    String userShortName = text8.getText();

    if(userX.isEmpty()) userX = x;
    if(userY.isEmpty()) userY = y;
    if(userFloor.isEmpty()) userFloor = floor;
    if(userBuild.isEmpty()) userBuild = build;
    if(userType.isEmpty()) userType = type;
    if(userLongName.isEmpty()) userLongName = build;
    if(userShortName.isEmpty()) userShortName = shortName;

    Database.editTuple(ID, (int) Double.parseDouble(userX), (int) Double.parseDouble(userY), (int) Double.parseDouble(userFloor), userBuild, userType, userLongName, userShortName);
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
    ID = selectedNode.getNodeID();
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

  }
}
