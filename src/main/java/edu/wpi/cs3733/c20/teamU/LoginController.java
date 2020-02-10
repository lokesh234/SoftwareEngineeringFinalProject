package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Popup;;


public class LoginController {

  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private Button loginEnter;

//  private Popup popup = App.getPopup();
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
    App.getPopup().getContent().remove(0);
    if (didFail) {
      App.getHome().setOpacity(1);
      App.getHome().setDisable(false);
    } else {
      App.getHome().setOpacity(1);
      App.getHome().setDisable(false);


      // uncomment this to move onto admin screen....
//      App.getPopup().getContent().add(App.getAdmin());
//      App.getPopup().show(App.getPrimaryStage());
    }
  }

    @FXML
    private void exitPopup() {
        didFail = false;
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getPopup().getContent().remove(0);
    }

    @FXML
    private void initialize() {
//        loginEnter.setDisable(true);
  }

}
