package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginScreenController {

  @FXML private TextField usernameField;
  @FXML private TextField passwordField;

  private int trackLoginCount;
  private boolean didFail;
  private String whoTried;
  private AdminScreenController adminScreenController;

  public void setAttributes(AdminScreenController attributes) {
    adminScreenController = attributes;
  }


  /**
   * checks whether the user entered the correct credentials
   * @return false if incorrect, true if correct
   */
  @FXML
  private void isAuthorized() {

    whoTried = Database.checkCred(usernameField.getText(), passwordField.getText());
    if (whoTried.equals("FALSE")) {
      if (trackLoginCount == 3) {
        trackLoginCount = 0;
        changeScene();
        return;
      }
      didFail = true;
      trackLoginCount++;
      usernameField.clear();
      passwordField.clear();
      usernameField.setStyle("-fx-border-color: red");
      passwordField.setStyle("-fx-border-color: red");
    } else {
      trackLoginCount = 0;
      didFail = false;
      changeScene();
    }
  }

  /**
   * function will change to home screen if user failed
   * or change to admin screen
   */

  private void changeScene() {
    usernameField.setStyle("-fx-border-color: skyblue");
    passwordField.setStyle("-fx-border-color: skyblue");
    App.getPopup().getContent().clear();
    if (didFail) {
      App.getHome().setOpacity(1);
      App.getHome().setDisable(false);
    } else {
      App.setUser(whoTried);
//      adminScreenController.enableBasedOnCred(whoTried);
      if(whoTried.equals("ADMIN")) App.getPopup().getContent().add(App.getAdmin());
      else App.getPopup().getContent().add(App.getAdminRequest());
      App.getAdminRequestController().update();
      App.getPopup().show(App.getPrimaryStage());
    }
    usernameField.clear();
    passwordField.clear();
  }

  /**
   * remove login screen from front page
   */
  @FXML
  private void exitPopup() {
    didFail = false;
    usernameField.setStyle("-fx-border-color: skyblue");
    passwordField.setStyle("-fx-border-color: skyblue");
    App.getHome().setOpacity(1);
    App.getHome().setDisable(false);
    App.getPopup().getContent().clear();
  }

  @FXML
  private void initialize() {
    //        loginEnter.setDisable(true);
  }
}
