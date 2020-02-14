package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

  @FXML private TextField usernameField;
  @FXML private TextField passwordField;

  private int trackLoginCount;
  private boolean didFail;

  /**
   * checks whether is the user entered the correct credentials
   *
   * @return false if incorrect, true if correct
   */
  @FXML
  private void isAuthorized() {

    boolean haveAccess = Database.checkCred(usernameField.getText(), passwordField.getText());
    if (!haveAccess) {
      if (trackLoginCount == 3) {
        trackLoginCount = 0;
        changeScene();
        return;
      }
      didFail = true;
      trackLoginCount++;
      usernameField.setPromptText("");
      passwordField.setPromptText("");
      usernameField.setStyle("-fx-border-color: red");
      passwordField.setStyle("-fx-border-color: red");
    } else {
      trackLoginCount = 0;
      didFail = false;
      changeScene();
    }
  }

  private void changeScene() {
    usernameField.setStyle("-fx-border-color: black");
    passwordField.setStyle("-fx-border-color: black");
    App.getPopup().getContent().clear();
    if (didFail) {
      App.getHome().setOpacity(1);
      App.getHome().setDisable(false);
    } else {
      App.setUser(usernameField.getText());
      // uncomment this to move onto admin screen....
      App.getPopup().getContent().add(App.getAdmin());
      App.getPopup().show(App.getPrimaryStage());
    }
    usernameField.clear();
    passwordField.clear();
  }

  @FXML
  private void exitPopup() {
    didFail = false;
    usernameField.setStyle("-fx-border-color: black");
    passwordField.setStyle("-fx-border-color: black");
    App.getHome().setOpacity(1);
    App.getHome().setDisable(false);
    App.getPopup().getContent().remove(0);
  }

  @FXML
  private void initialize() {
    //        loginEnter.setDisable(true);
  }
}
