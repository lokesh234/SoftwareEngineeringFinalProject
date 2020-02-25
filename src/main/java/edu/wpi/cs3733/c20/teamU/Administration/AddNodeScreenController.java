package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;

public class AddNodeScreenController {

  @FXML
  private Button confirm;
  @FXML
  private Button cancel;
  @FXML
  private TextField text1, text2, text3, text4, text5, text7, text8;
  @FXML private JFXComboBox nodeType;
  private String ID, x, y, floor, build, type, shortName, longName;
  //  Node selectedNode;
  GraphEditController nodeViewScreenController;

  public AddNodeScreenController() {
  }

  public void setAttributes(GraphEditController nodeViewScreenController1) {
    nodeViewScreenController = nodeViewScreenController1;
  }

  public void keyConfirm(){
    confirm.fire();
  }

  /**
   * Change scene when this is called...
   */
  @FXML
  public void adminScreen(ActionEvent event) throws IOException {
    App.getPopup().getContent().clear();
    App.getAdminNode().setOpacity(1);
    App.getAdminNode().setDisable(false);
    App.getPopup().hide();
    nodeViewScreenController.update();
    text1.clear();
    text2.clear();
    text3.clear();
    text4.clear();
    text5.clear();
    text7.clear();
    text8.clear();
  }

  @FXML
  private void confirmChange() {
    try {
      overWrite();
    } catch (Exception e) {
      return; //If this fails, there's a problem with the text input
    }
    HashMap<String, Node> graph = new HashMap<String, Node>();
    DatabaseWrapper.getNodes(graph);
//    System.out.println(graph.get(userID).getFloor());
    cancel.fire();
  }

  /**
   * function makes changes to node values
   */
  private void overWrite() { // does not count in for empty values
//    boolean boo = false;
    String userID = text1.getText();
    String userX = text2.getText();
    String userY = text3.getText();
    String userFloor = text4.getText();
    String userBuild = text5.getText();
    String userType = nodeType.getValue().toString();
    String userLongName = text7.getText();
    String userShortName = text8.getText();
//    System.out.println(userID);

    if(userX.isEmpty()) userX = x;
    if(userY.isEmpty()) userY = y;

      if (!DatabaseWrapper.addNode(userID, (int) Double.parseDouble(userX), (int) Double.parseDouble(userY),
              (int) Double.parseDouble(userFloor), userBuild, userType, userLongName,
              userShortName)) System.out.println("oh no 2"); // THIS IS FAILING

      //System.out.println("something");

//    if (boo) {
//      System.out.println(1);
//    } else {
//      System.out.println(0);
//    }
    text1.clear();
    text2.clear();
    text3.clear();
    text4.clear();
    text5.clear();
    text7.clear();
    text8.clear();
    cancel.fire();

  }

  public void setDefaultPos(int ex, int why) {
    //ID = selectedNode.getNodeID();
    x = Integer.toString(ex);
    y = Integer.toString(why);

    text2.setText(x);
    text3.setText(y);

  }

  @FXML
  public void initialize() {
    ObservableList<String> typeOptions = FXCollections.observableArrayList("DEPT","HALL","CONF","REST","STAI","ELEV","LABS","INFO","CONF","EXIT","RETL","SERV");
    nodeType.getItems().addAll(typeOptions);
    nodeType.setValue("HALL");
  }
}
