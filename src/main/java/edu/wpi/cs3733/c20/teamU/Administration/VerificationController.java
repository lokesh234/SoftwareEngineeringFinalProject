package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;

public class VerificationController {
  @FXML private JFXButton confirm;
  @FXML private JFXButton cancel;
  @FXML private JFXTextField code;
  private TwoFactorSecurity twoFactorSecurity;

  @FXML
  private void goToAdmin() {
    if(!code.getText().isEmpty()) {
      if (twoFactorSecurity.checkKey(code.getText())) {
        cancel.fire();
      } else {
        code.setStyle("-fx-border-color: red");
        System.out.println(false);
      }
    }
  }

  /**
   * removes verification scene
   */
  @FXML
  private void returnToLogin() {
    App.getPopup().getContent().remove(1);
  }

  private void sendAnotherCode() {
    twoFactorSecurity.sendOutSMS();
  }

  /**
   * params will be used for future purposes
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
