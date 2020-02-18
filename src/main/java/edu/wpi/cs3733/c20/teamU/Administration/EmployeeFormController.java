package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class EmployeeFormController {

  @FXML private JFXTextField firstNameText;
  @FXML private JFXTextField lastNameText;
  @FXML private JFXTextField employeeIdText;
  @FXML private JFXTextField passwordText;
  @FXML private JFXTextField confirmPassText;
  @FXML private JFXButton confirm;
  @FXML private JFXButton cancel;
  @FXML private JFXButton add;
//  @FXML private JFXChipView<String> chipView;
  @FXML private JFXComboBox jfxComboBox = new JFXComboBox();
  @FXML private JFXCheckBox checkBox;

  @FXML
  private void setConfirm() {
    if (firstNameText.getText().isEmpty() || lastNameText.getText().isEmpty() ||
        employeeIdText.getText().isEmpty() || passwordText.getText().isEmpty() || confirmPassText
        .getText().isEmpty() || jfxComboBox.getSelectionModel().isEmpty()) {
      firstNameText.setStyle("-fx-border-color: red");
      lastNameText.setStyle("-fx-border-color: red");
      employeeIdText.setStyle("-fx-border-color: red");
      passwordText.setStyle("-fx-border-color: red");
      confirmPassText.setStyle("-fx-border-color: red");
      jfxComboBox.setStyle("-fx-border-color: red");
    } else {
      // add/edit to database
      firstNameText.clear();
      lastNameText.clear();
      employeeIdText.clear();
      passwordText.clear();
      confirmPassText.clear();
      // clear chip view here
      firstNameText.setStyle("-fx-border-color: skyblue");
      lastNameText.setStyle("-fx-border-color: skyblue");
      employeeIdText.setStyle("-fx-border-color: skyblue");
      passwordText.setStyle("-fx-border-color: skyblue");
      confirmPassText.setStyle("-fx-border-color: skyblue");
      jfxComboBox.setStyle("-fx-border-color: skyblue");
    }
  }

  @FXML
  private void returnToAdmin() {
    App.getPopup().getContent().clear();
    App.getPrimaryStage().setOpacity(1);
    App.getPopup().getContent().add(App.getAdmin());
  }

  @FXML
  public void initialize() {
    ObservableList<String> employee =
        FXCollections.observableArrayList(
            "ADMIN",
            "MEDIC",
            "SECUR"
        );
    jfxComboBox.getItems().addAll(employee);
    checkBox.setDisable(true);
//    confirm.setDisable(true);
  }

}
