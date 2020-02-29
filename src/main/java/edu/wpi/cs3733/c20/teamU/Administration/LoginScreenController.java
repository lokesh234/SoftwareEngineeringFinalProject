package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.net.UnknownHostException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.Notifications;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class LoginScreenController {

  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private JFXButton loginEnter;
  @FXML private JFXButton test;
  @FXML private JFXButton cancel;
  @FXML private Label title;
  @FXML private JFXCheckBox robotCheck;
  @FXML private Label userName;
  @FXML private Label password;

  private int trackLoginCount;
  private boolean didFail;
  private Account whoTried;
  private String usernameTried;
  private EmployeeFormController employeeFormController;

  public void setAttributes(EmployeeFormController attribute) {
    employeeFormController = attribute;
  }

  public void keyConfirm(){
    loginEnter.fire();
  }


  /**
   * checks whether the user entered the correct credentials
   * @return false if incorrect, true if correct
   */
  @FXML
  private void isAuthorized() {
    usernameTried = usernameField.getText();
    whoTried = DatabaseWrapper.checkCred(usernameTried, passwordField.getText());
    if (whoTried == null) {
      if (trackLoginCount == 3) {
        Notifications.create().text("All attempts tried...").show();
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
      App.getColorController().setUser(whoTried.getUserName());
      Notifications.create().text("Logged in as: " + whoTried.getUserName()).show();
      changeScene();
    }
  }

  /**
   * function used to enter the verification form
   */
  @FXML
  private void enterVerification() {
    usernameTried = usernameField.getText();
    whoTried = DatabaseWrapper.checkCred(usernameTried, passwordField.getText());
    if (whoTried == null) {
      clearFields();
      usernameField.setStyle("-fx-border-color: red");
      passwordField.setStyle("-fx-border-color: red");
    } else {
//      App.getPopup().getContent().clear();

      App.getVerificationController().startProcess(whoTried.getUserName(), whoTried.getNumber());
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

  /**
   * function used to clear all the fields back to default
   */
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
  public void changeScene() {
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
//  public void setDefaultSetting() {
//    userName.setStyle("-fx-border-color: #FFEEC9");
//    password.setStyle("-fx-border-color: #FFEEC9");
//    usernameField.setFont(Font.font("Black", 20));
//    passwordField.setFont(Font.font("Black", 20));
//    robotCheck.setStyle("-fx-border-color: #FFEEC9");
//    cancel.setStyle("-fx-border-color: #FFEEC9");
//    loginEnter.setStyle("-fx-border-color: #FFEEC9");
//    title.setStyle("-fx-border-color: #FFEEC9");
//  }
//  public void setDarkSetting() {
//    userName.setStyle("-fx-border-color: white");
//    password.setStyle("-fx-border-color: white");
//    usernameField.setFont(Font.font("White", 20));
//    passwordField.setFont(Font.font("White", 20));
//    robotCheck.setStyle("-fx-border-color: white");
//    cancel.setStyle("-fx-border-color: white");
//    loginEnter.setStyle("-fx-border-color: white");
//    title.setStyle("-fx-border-color: white");
//  }
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
