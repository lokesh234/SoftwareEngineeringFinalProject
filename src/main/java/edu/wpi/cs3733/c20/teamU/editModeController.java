package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class editModeController {

  @FXML private Button confirm;
  @FXML private Button cancel;
  @FXML private TextField text1, text2, text3, text4, text5, text6, text7, text8;;
  private String ID, x, y, floor, build, type, shortName, longName;
  edu.wpi.cs3733.c20.teamU.Node selectedNode;

  public editModeController() {}

  /**
   * Change scene when this is called...
   */
  public void adminScreen(ActionEvent event) throws IOException {
    Parent editMode = FXMLLoader.load(getClass().getResource("/Admin_Node.fxml"));
    Scene editModeScene = new Scene(editMode);

    Stage scene = (Stage) ((Node)event.getSource()).getScene().getWindow();

    scene.setScene(editModeScene);
    scene.show();
  }

  @FXML
  private void confirmChange() {
    overWrite();
    HashMap<String, edu.wpi.cs3733.c20.teamU.Node> nodes = new HashMap<String, edu.wpi.cs3733.c20.teamU.Node>();
    Database.getNodes(nodes);
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



//    String ID = selectedNode.getNodeID();
//    int x = (int) Double.parseDouble(text2.getText());
//    int y = (int) Double.parseDouble(text3.getText());
//    int floor = (int) Double.parseDouble(text4.getText());
//    String build = text5.getText();
//    String type = text6.getText();
//    String longName = text7.getText();
//    String shortName = text8.getText();

//    Database.editTuple(ID, x, y, floor, build, type, longName, shortName);
  }

  /**
   * function sets prompt messages for edit mode scene
   */
  public void selectedNodeVal() {

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

//    text1.setPromptText(selectedNode.getNodeID());
//    text2.setPromptText(Integer.toString(selectedNode.getX()));
//    text3.setPromptText(Integer.toString(selectedNode.getY()));
//    text4.setPromptText(Integer.toString(selectedNode.getFloor()));
//    text5.setPromptText(selectedNode.getBuilding());
//    text6.setPromptText(selectedNode.getNodeType());
//    text7.setPromptText(selectedNode.getLongName());
//    text8.setPromptText(selectedNode.getShortName());
  }

//  public void changeText1(boolean b) {
//    if(b) text1.setDisable(false);
//  }

  /**
   * function is used from admin screen to set user designated Node
   * @param designatedNode
   */
  public void setNode(edu.wpi.cs3733.c20.teamU.Node designatedNode) {selectedNode = designatedNode;}

  @FXML
  public void initialize() {
    text1.setDisable(true);

  }
}
