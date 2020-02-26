package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.HashMap;

public class NodeEditController {

  @FXML private JFXButton confirm;
  @FXML private JFXButton cancel;
  @FXML private JFXTextField text1, text2, text3, text4, text5, text6, text7, text8;;
  private String ID, x, y, floor, build, type, shortName, longName;
  GraphEditController nodeViewScreenController;

  public void keyConfirm(){
    confirm.fire();
  }

  public NodeEditController() {}

  /**
   * Change scene when this is called...
   */
  public void adminScreen(ActionEvent event) throws IOException {
    App.getPopup().getContent().clear();
    App.getAdminNode().setOpacity(1);
    App.getAdminNode().setDisable(false);
    App.getPopup().hide();
    nodeViewScreenController.update();
    text2.clear();
    text3.clear();
    text4.clear();
    text5.clear();
    text6.clear();
    text7.clear();
    text8.clear();
  }

  public void setAttributes(GraphEditController nodeViewScreenController1) {
    nodeViewScreenController = nodeViewScreenController1;
  }

  @FXML
  private void confirmChange() {
    overWrite();
    HashMap<String, Node> graph = new HashMap<String, Node>();
    DatabaseWrapper.getNodes(graph);
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

    DatabaseWrapper.editNode(ID, (int) Double.parseDouble(userX), (int) Double.parseDouble(userY), (int) Double.parseDouble(userFloor), userBuild, userType, userLongName, userShortName);
    DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), "NODE", "Edit", ID);
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
  public void selectedNodeVal(Node n) {
    //ID = selectedNode.getNodeID();
    ID = n.getID();
    x = Integer.toString(n.getX());
    y = Integer.toString(n.getY());
    floor = Integer.toString(n.getFloor());
    build = n.getBuilding();
    type = n.getNodeType();
    longName = n.getLongName();
    shortName = n.getShortName();

    text1.setText(ID);
    text2.setText(x);
    text3.setText(y);
    text4.setText(floor);
    text5.setText(build);
    text6.setText(type);
    text7.setText(longName);
    text8.setText(shortName);

  }

  public void selectedNodeVal(Node n, int ex, int why) { //Set parameters with selected node and position
    //ID = selectedNode.getNodeID();
    ID = n.getID();
    x = Integer.toString(ex);
    y = Integer.toString(why);
    floor = Integer.toString(n.getFloor());
    build = n.getBuilding();
    type = n.getNodeType();
    longName = n.getLongName();
    shortName = n.getShortName();

    text1.setText(ID);
    text2.setText(x);
    text3.setText(y);
    text4.setText(floor);
    text5.setText(build);
    text6.setText(type);
    text7.setText(longName);
    text8.setText(shortName);

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
