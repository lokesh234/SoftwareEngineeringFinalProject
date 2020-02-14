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

public class AddNodeScreenController {

  @FXML
  private Button confirm;
  @FXML
  private Button cancel;
  @FXML
  private TextField text1, text2, text3, text4, text5, text6, text7, text8;
  private String ID, x, y, floor, build, type, shortName, longName;
  //  Node selectedNode;
  NodeViewScreenController nodeViewScreenController;

  public AddNodeScreenController() {
  }

  public void setAttributes(NodeViewScreenController nodeViewScreenController1) {
    nodeViewScreenController = nodeViewScreenController1;
  }

  /**
   * Change scene when this is called...
   */
  @FXML
  public void adminScreen(ActionEvent event) throws IOException {
    App.getPopup().getContent().clear();
    App.getNodeViewScreenController().refreshTable();
    App.getPopup().getContent().add(App.getAdminNode());
    App.getPopup().show(App.getPrimaryStage());
  }

  @FXML
  private void confirmChange() {
    overWrite();
    HashMap<String, Node> graph = new HashMap<String, Node>();
    Database.getNodes(graph);
//    System.out.println(graph.get(userID).getFloor());
    nodeViewScreenController.refreshTable();
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
    String userType = text6.getText();
    String userLongName = text7.getText();
    String userShortName = text8.getText();
//    System.out.println(userID);

     if (!Database.addNode(userID, (int) Double.parseDouble(userX), (int) Double.parseDouble(userY),
          (int) Double.parseDouble(userFloor), userBuild, userType, userLongName,
          userShortName)) System.out.println("oh no 2"); // THIS IS FAILING
      System.out.println("something");

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
    text6.clear();
    text7.clear();
    text8.clear();
    cancel.fire();

  }

  @FXML
  public void initialize() {
  }
}
