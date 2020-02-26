package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controlsfx.control.Notifications;

public class TimeoutController {

  @FXML private JFXButton confirm;
  @FXML private JFXButton cancel;
  @FXML private JFXTextField newTime;
  @FXML private Label currentSetting;

  @FXML
  private void updateTimeout() {
    long userTime = 0;
    try {
      userTime = Long.parseLong(newTime.getText());
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    if(userTime < 5) {
      clearFields();
      newTime.setStyle("-fx-border-color: red");
    } else {
      App.setTimeoutValue(userTime * 1000);
      Notifications.create().text("Timeout set to: " + userTime + "s").show();
      clearFields();
      cancel.fire();
    }
  }


  @FXML
  private void returnToAdmin() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
  }

  @FXML
  private void clearFields() {
    newTime.clear();
    newTime.setStyle("-fx-border-color: #FFF9E9");
  }

  @FXML
  private void initialize() {
    newTime.setPromptText("Assign a value greater than 5 seconds");
  }
}
