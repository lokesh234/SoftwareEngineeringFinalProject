package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.util.ArrayList;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class EmployeeFormController {

  @FXML private JFXTextField firstNameText;
  @FXML private JFXTextField lastNameText;
  @FXML private JFXTextField employeeIdText;
  @FXML private JFXTextField passwordText;
  @FXML private JFXTextField confirmPassText;
  @FXML private JFXButton confirm;
  @FXML private JFXButton cancel;
//  @FXML private JFXChipView employeeChip = new JFXChipView();
  @FXML private JFXComboBox employeeCombo = new JFXComboBox();
  @FXML private JFXCheckBox checkBox;
  @FXML private JFXCheckBox checkBox1;
  @FXML private JFXCheckBox delete;
  private String first, last, user, pass, checkPass, position;
  private String userOG;

  public EmployeeFormController() {}
  //TODO: code needs refactoring, will do iteration 3

  /**
   * function gets the user's inputs and adds/edits/deletes based on conditions
   */
  @FXML
  private void setConfirm() {
    first = firstNameText.getText();
    last = lastNameText.getText();
    user = employeeIdText.getText();
    pass = passwordText.getText();
    checkPass = confirmPassText.getText();
    position = employeeCombo.getSelectionModel().getSelectedItem().toString();

    if (first.isEmpty() || last.isEmpty() ||
        user.isEmpty() || pass.isEmpty() || checkPass.isEmpty() || position.isEmpty()) {
      firstNameText.setStyle("-fx-border-color: red");
      lastNameText.setStyle("-fx-border-color: red");
      employeeIdText.setStyle("-fx-border-color: red");
      passwordText.setStyle("-fx-border-color: red");
      confirmPassText.setStyle("-fx-border-color: red");
      employeeCombo.setStyle("-fx-border-color: red");
    }
    if (!checkPasswords(pass, checkPass)) {
      clearFields();
      passwordText.setStyle("-fx-border-color: red");
      confirmPassText.setStyle("-fx-border-color: red");
    }
    else if(user.equals(userOG)) employeeIdText.setStyle("-fx-border-color: red");
    else {
      if(delete.isSelected()) DatabaseWrapper.delLoginSR(user);
      else DatabaseWrapper.addLoginSR(user, pass, first, last, position);
      cancel.fire();
    }
  }

//  @FXML
//  private void employeeTypeAdd() {
//    if (!employeeCombo.getSelectionModel().isEmpty()) {
//      employeeCombo.setStyle("-fx-border-color: #FFEEC9");
//      employeeChip.getChips().add(employeeCombo.getSelectionModel().getSelectedItem());
//      employeeCombo.getSelectionModel().clearSelection();
//    } else {
//      employeeCombo.setStyle("-fx-border-color: red");
//    }
//  }

  /**
   * function to return to admin screen
   */
  @FXML
  private void returnToAdmin() {
    clearFields();
    setFields();
    App.getPopup().getContent().clear();
    App.getPrimaryStage().setOpacity(1);
    App.getPopup().getContent().add(App.getAdmin());
  }

  /**
   * funciton to clear all fields and reset them
   */
  private void clearFields() {
    firstNameText.clear();
    lastNameText.clear();
    employeeIdText.clear();
    passwordText.clear();
    confirmPassText.clear();
    checkBox.setSelected(false);
    checkBox1.setSelected(false);
    delete.setSelected(false);
//    employeeChip.getChips().clear();
    employeeCombo.getSelectionModel().clearSelection();
    firstNameText.setStyle("-fx-border-color: #FFEEC9");
    lastNameText.setStyle("-fx-border-color: #FFEEC9");
    employeeIdText.setStyle("-fx-border-color: #FFEEC9");
    passwordText.setStyle("-fx-border-color: #FFEEC9");
    confirmPassText.setStyle("-fx-border-color: #FFEEC9");
    employeeCombo.setStyle("-fx-border-color: #FFEEC9");
  }

  /**
   * checks to see if the two different passwords are correct
   * @param password String of first password
   * @param password1 String of copy of second password
   * @return false if they are different, true if same
   */
  private boolean checkPasswords(String password, String password1) {
    return password.equals(password1);
  }

  /**
   * function to set the fields of the UI to the user's data
   */
  public void setFields() {
    ArrayList<String> userData = DatabaseWrapper.getLoginSR(App.getUsernameTried());
    userOG = userData.get(0);
//     user, pass, first, last, posiiton
    employeeIdText.setText(userData.get(0));
    firstNameText.setText(userData.get(2));
    lastNameText.setText(userData.get(3));
    employeeCombo.setValue(userData.get(4));
    BooleanBinding bind = employeeIdText.textProperty().isEqualTo(userOG);
    confirm.disableProperty().bind(bind);
  }

//  private void willAdd() {
//    if(checkBox1.isSelected()) {
//      clearFields();
//      setFields();
//      delete.setDisable(true);
//    }
//  }

  @FXML
  public void initialize() {
    ObservableList<String> employee =
        FXCollections.observableArrayList(
            "ADMIN",
            "MEDIC",
            "SECUR",
            "FLOWR",
            "DELIV",
            "ITRAN",
            "ETRAN",
            "CLOWN",
            "RELIG",
            "SANIT",
            "LANGE",
            "INTEC"
        );
    employeeCombo.getItems().addAll(employee);
    checkBox.setDisable(true);
    BooleanBinding blockCheckBox = firstNameText.textProperty().isEmpty()
        .or(lastNameText.textProperty().isEmpty()).or(employeeIdText.textProperty().isEmpty())
        .or(passwordText.textProperty().isEmpty()).or(confirmPassText.textProperty().isEmpty());
    checkBox.disableProperty().bind(blockCheckBox);
    checkBox1.addEventHandler(MOUSE_CLICKED, event -> {
      if(checkBox1.isSelected()) {
        clearFields();
        delete.setDisable(true);
      } else {
        clearFields();
        setFields();
        delete.setDisable(false);
      }
    });
//    employeeIdText.setDisable(true);
//    firstNameText.setDisable(true);
//    lastNameText.setDisable(true);
//    confirm.setDisable(true);
  }

}
