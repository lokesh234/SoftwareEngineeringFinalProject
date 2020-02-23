package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.awt.event.MouseEvent;
import java.net.UnknownHostException;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class LoginScreenController {

  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private JFXButton loginEnter;
  @FXML private JFXButton test;
  @FXML private JFXCheckBox robotCheck;

  private int trackLoginCount;
  private boolean didFail;
  private Account whoTried;
  private String usernameTried;
  private EmployeeFormController employeeFormController;

  public void setAttributes(EmployeeFormController attribute) {
    employeeFormController = attribute;
  }


  /**
   * checks whether the user entered the correct credentials
   * @return false if incorrect, true if correct
   */
  @FXML
  private void isAuthorized() throws UnknownHostException {
    usernameTried = usernameField.getText();
    whoTried = DatabaseWrapper.checkCred(usernameTried, passwordField.getText());
    if (whoTried == null) {
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

  @FXML
  private void enterVerification() {
    usernameTried = usernameField.getText();
    whoTried = DatabaseWrapper.checkCred(usernameTried, passwordField.getText());
    if (whoTried == null) {
      clearFields();
      usernameField.setStyle("-fx-border-color: red");
      passwordField.setStyle("-fx-border-color: red");
    } else if(whoTried.getCred().equals("ADMIN")){
//      App.getPopup().getContent().clear();

      App.getVerificationController().startProcess(whoTried.getUserName(), "+17603358848");
      App.getPopup().getContent().add(App.getVerification());
      App.getPopup().show(App.getPrimaryStage());
      clearFields();
    }
  }
  /**
   * remove login screen from front page
   */
  @FXML
  private void exitPopup() {
    clearFields();
    App.getHome().setOpacity(1);
    App.getHome().setDisable(false);
    App.getPopup().getContent().clear();
  }

  private void clearFields() {
    usernameField.clear();
    passwordField.clear();
    robotCheck.setSelected(false);
    loginEnter.setDisable(false);
    test.setDisable(true);
    usernameField.setStyle("-fx-border-color: #FFEEC9");
    passwordField.setStyle("-fx-border-color: #FFEEC9");
    didFail = false;
  }
  /**
   * function will change to home screen if user failed
   * or change to admin screen
   */
  private void changeScene() {
    App.getPopup().getContent().clear();
    if (didFail) {
      App.getHome().setOpacity(1);
      App.getHome().setDisable(false);
    } else {
      App.setUser(whoTried);
      App.setUsernameTried(usernameTried);
      employeeFormController.setFields();
      if(whoTried.getCred().equals("ADMIN")) {
//        System.out.println("here");
        App.getPopup().getContent().add(App.getAdmin());
      }
      else App.getPopup().getContent().add(App.getAdminRequest());
      App.getAdminScreenController().cred();
      App.getAdminRequestController().cred();
      App.getAdminRequestController().update();
      App.getPopup().show(App.getPrimaryStage());
    }
    clearFields();
  }

  @FXML
  private void initialize() {
    loginEnter.setDisable(false);
    test.setDisable(true);
    robotCheck.addEventHandler(MOUSE_CLICKED, event -> {
      if(robotCheck.isSelected()) {
        loginEnter.setDisable(true);
        test.setDisable(false);
      }
      else {
        loginEnter.setDisable(false);
        test.setDisable(true);
      }
    });
//    BooleanBinding bind = robotCheck.selectedProperty().not();
//    loginEnter.disableProperty().bind(bind);
  }
}
