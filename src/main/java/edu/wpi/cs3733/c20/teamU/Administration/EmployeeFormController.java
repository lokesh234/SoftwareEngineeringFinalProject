package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import java.util.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;
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
  @FXML private JFXChipView employeeChip = new JFXChipView();
  @FXML private JFXComboBox employeeCombo = new JFXComboBox();
  @FXML private JFXCheckBox checkBox;

  @FXML
  private void setConfirm() {
    if (firstNameText.getText().isEmpty() || lastNameText.getText().isEmpty() ||
        employeeIdText.getText().isEmpty() || passwordText.getText().isEmpty() || confirmPassText
        .getText().isEmpty() || employeeCombo.getSelectionModel().isEmpty()) {
      firstNameText.setStyle("-fx-border-color: red");
      lastNameText.setStyle("-fx-border-color: red");
      employeeIdText.setStyle("-fx-border-color: red");
      passwordText.setStyle("-fx-border-color: red");
      confirmPassText.setStyle("-fx-border-color: red");
      employeeCombo.setStyle("-fx-border-color: red");
    } else {
      // add/edit to database
      cancel.fire();
      clearFields();
    }
  }

  @FXML
  private void employeeTypeAdd() {
    if (!employeeCombo.getSelectionModel().isEmpty()) {
      employeeCombo.setStyle("-fx-border-color: #FFEEC9");
      employeeChip.getChips().add(employeeCombo.getSelectionModel().getSelectedItem());
      employeeCombo.getSelectionModel().clearSelection();
    } else {
      employeeCombo.setStyle("-fx-border-color: red");
    }
  }

  @FXML
  private void returnToAdmin() {
    clearFields();
    App.getPopup().getContent().clear();
    App.getPrimaryStage().setOpacity(1);
    App.getPopup().getContent().add(App.getAdmin());
  }

  private void clearFields() {
    firstNameText.clear();
    lastNameText.clear();
    employeeIdText.clear();
    passwordText.clear();
    confirmPassText.clear();
    employeeChip.getChips().clear();
    employeeCombo.getSelectionModel().clearSelection();
    firstNameText.setStyle("-fx-border-color: #FFEEC9");
    lastNameText.setStyle("-fx-border-color: #FFEEC9");
    employeeIdText.setStyle("-fx-border-color: #FFEEC9");
    passwordText.setStyle("-fx-border-color: #FFEEC9");
    confirmPassText.setStyle("-fx-border-color: #FFEEC9");
    employeeCombo.setStyle("-fx-border-color: #FFEEC9");
  }

  @FXML
  public void initialize() {
    ObservableList<String> employee =
        FXCollections.observableArrayList(
            "ADMIN",
            "MEDIC",
            "SECUR"
        );
    employeeCombo.getItems().addAll(employee);
    checkBox.setDisable(true);
    BooleanBinding blockCheckBox = firstNameText.textProperty().isEmpty()
        .or(lastNameText.textProperty().isEmpty()).or(employeeIdText.textProperty().isEmpty())
        .or(passwordText.textProperty().isEmpty()).or(confirmPassText.textProperty().isEmpty());
    checkBox.disableProperty().bind(blockCheckBox);

//    confirm.setDisable(true);
  }

}
