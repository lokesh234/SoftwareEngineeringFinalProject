package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.util.ArrayList;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;

public class VerificationController {

  @FXML private JFXButton confirm;
  @FXML private JFXButton cancel;
  @FXML private JFXTextField code;
  private boolean atAdmin = true;
  private TwoFactorSecurity twoFactorSecurity;
  private ArrayList<String> accountDetails;

  public void keyConfirm(){
    confirm.fire();
  }

  /**
   * function used to check if the user put in the correct verification code
   */
  @FXML
  private void goToAdmin() {
    if (!code.getText().isEmpty()) {
      if (twoFactorSecurity.checkKey(code.getText())) {
        if (atAdmin) {
          App.getLoginScreenController().changeScene();
        } else {
          DatabaseWrapper
              .addLoginSR(accountDetails.get(0), accountDetails.get(1), accountDetails.get(2),
                  accountDetails.get(3), accountDetails.get(4), accountDetails.get(5));
          App.getAdminEmployeeController().update();
          returnToEmployee();
        }
      } else {
        code.setStyle("-fx-border-color: red");
      }
    }
  }

  /**
   * setter to get user account info
   * @param accountDetails first, last, user, pass, pass2, number
   */
  public void setAccountDetails(ArrayList<String> accountDetails) {
    this.accountDetails = accountDetails;
  }

  /**
   * function used to determine if the screen was at admin or add employee
   * @param atAdmin
   */
  public void setAtAdmin(boolean atAdmin) {
    this.atAdmin = atAdmin;
  }

  /**
   * removes verification scene
   */
  @FXML
  private void returnToLogin() {
    App.getHome().setOpacity(1);
    App.getHome().setDisable(false);
    if (atAdmin) {
      App.getPopup().getContent().clear();
    } else {
      returnToEmployee();
    }
  }

  /**
   * function used to send out another verification code
   */
  @FXML
  private void sendAnotherCode() {
    twoFactorSecurity.sendOutSMS();
  }

  /**
   * function used to return to the employee screen
   */
  private void returnToEmployee() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdminEmployee());
    atAdmin = true;
  }

  /**
   * params will be used for future purposes
   * this function starts the two factor verification process
   * @param userName
   * @param number
   */
  public void startProcess(String userName, String number) {
    twoFactorSecurity = new TwoFactorSecurity(userName, number);
    twoFactorSecurity.sendOutSMS();
  }

  @FXML
  private void initialize() {
    BooleanBinding bind = code.textProperty().isEmpty();
    confirm.disableProperty().bind(bind);
  }
}
