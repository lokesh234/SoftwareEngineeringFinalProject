package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;

public class AddNodeController {

  @FXML private Button confirm;
  @FXML private Button cancel;
  @FXML private TextField text1, text2, text3, text4, text5, text6, text7, text8;;
  private String ID, x, y, floor, build, type, shortName, longName;
  Node selectedNode;
  NodeController nodeController;

  public AddNodeController() {}

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
    String userId = text1.getText();
    String userX = text2.getText();
    String userY = text3.getText();
    String userFloor = text4.getText();
    String userBuild = text5.getText();
    String userType = text6.getText();
    String userLongName = text7.getText();
    String userShortName = text8.getText();

    Database.addNode(userId, (int) Double.parseDouble(userX), (int) Double.parseDouble(userY), (int) Double.parseDouble(userFloor), userBuild, userType, userLongName, userShortName);
    text1.clear();
    text2.clear();
    text3.clear();
    text4.clear();
    text5.clear();
    text6.clear();
    text7.clear();
    text8.clear();
  }


//  /**
//   * function is used from admin screen to set user designated Node
//   * @param designatedNode
//   */
//  public void setNode(edu.wpi.cs3733.c20.teamU.Node designatedNode) {selectedNode = designatedNode;}

  @FXML
  public void initialize() {
  }
}
