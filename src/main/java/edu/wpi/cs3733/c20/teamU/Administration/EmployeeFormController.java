package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import java.util.ArrayList;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class EmployeeFormController {

  @FXML private JFXTextField firstNameText;
  @FXML private JFXTextField lastNameText;
  @FXML private JFXTextField employeeIdText;
  @FXML private JFXTextField passwordText;
  @FXML private JFXTextField confirmPassText;
  @FXML private JFXTextField employeeNumber;
  @FXML private JFXButton confirm;
  @FXML private JFXButton cancel;
  //  @FXML private JFXChipView employeeChip = new JFXChipView();
  @FXML private JFXComboBox employeeCombo = new JFXComboBox();
  @FXML private JFXCheckBox checkBox;
  private String first, last, user, pass, checkPass, position, number, oldUsername;
  private String userOG;
  private AdminEmployeeController master;
  private ArrayList<String> accountDetails;
  boolean edit = false;

  public void keyConfirm(){
    confirm.fire();
  }

  public EmployeeFormController() {
  }

  public void setMaster(AdminEmployeeController a) {
    master = a;
  }

  /**
   * function gets the user's inputs and adds/edits/deletes based on conditions
   */
  @FXML
  private void setConfirm() {
    first = firstNameText.getText();
    last = lastNameText.getText();
    user = employeeIdText.getText();
    number = employeeNumber.getText();
    pass = passwordText.getText();
    checkPass = confirmPassText.getText();
    position = employeeCombo.getSelectionModel().getSelectedItem().toString();

    if (first.isEmpty() || last.isEmpty() ||
        user.isEmpty() || pass.isEmpty() || checkPass.isEmpty() || position.isEmpty() || number
        .isEmpty()) {
      firstNameText.setStyle("-fx-border-color: red");
      lastNameText.setStyle("-fx-border-color: red");
      employeeIdText.setStyle("-fx-border-color: red");
      employeeNumber.setStyle("-fx-border-color: red");
      passwordText.setStyle("-fx-border-color: red");
      confirmPassText.setStyle("-fx-border-color: red");
      employeeCombo.setStyle("-fx-border-color: red");
    }
    if (!checkPasswords(pass, checkPass)) {
      clearFields();
      passwordText.setStyle("-fx-border-color: red");
      confirmPassText.setStyle("-fx-border-color: red");
    } else if (user.equals(userOG)) {
      employeeIdText.setStyle("-fx-border-color: red");
    } else if (edit) {
      DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), "EMPLOYEE", "Edit", oldUsername);
      DatabaseWrapper.delLoginSR(oldUsername);
      DatabaseWrapper.addLoginSR(user, pass, first, last, position, number);
      Notifications.create().text(first + " " + last + " has been edited!").show();
      returnToAdminEmployee();
    } else {
      accountDetails = new ArrayList<>();
      accountDetails.add(user);
      accountDetails.add(pass);
      accountDetails.add(first);
      accountDetails.add(last);
      accountDetails.add(position);
      accountDetails.add(number);
      DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), "EMPLOYEE", "Add", user);
      App.getPopup().getContent().add(App.getVerification());
      App.getVerificationController().setAccountDetails(accountDetails);
      App.getVerificationController().setAtAdmin(false);
      App.getVerificationController().startProcess(user, number.replaceAll("\\s+",""));
      App.getPopup().show(App.getPrimaryStage());
//      DatabaseWrapper.addLoginSR(user, pass, first, last, position, number);
//      returnToAdminEmployee();
    }
  }

  /**
   * function to return to admin screen
   */
  @FXML
  private void returnToAdminEmployee() {
    master.update();
    clearFields();
    setFields();
    App.getPopup().getContent().clear();
    App.getPrimaryStage().setOpacity(1);
    App.getPopup().getContent().add(App.getAdminEmployee());
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
    employeeNumber.clear();
    checkBox.setSelected(false);
    employeeCombo.getSelectionModel().clearSelection();
    firstNameText.setStyle("-fx-border-color: #FFEEC9");
    lastNameText.setStyle("-fx-border-color: #FFEEC9");
    employeeIdText.setStyle("-fx-border-color: #FFEEC9");
    passwordText.setStyle("-fx-border-color: #FFEEC9");
    employeeNumber.setStyle("-fx-border-color: #FFEEC9");
    confirmPassText.setStyle("-fx-border-color: #FFEEC9");
    employeeCombo.setStyle("-fx-border-color: #FFEEC9");
  }

  /**
   * checks to see if the two different passwords are correct
   *
   * @param password  String of first password
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
    Account userData = DatabaseWrapper.getLoginSR(App.getUsernameTried());
    userOG = userData.getUserName();
    employeeIdText.setText(userData.getUserName());
    firstNameText.setText(userData.getFirstName());
    lastNameText.setText(userData.getLastName());
    employeeNumber.setText(userData.getNumber());
    employeeCombo.setValue(userData.getCred());
    BooleanBinding bind = employeeIdText.textProperty().isEqualTo(userOG);
    confirm.disableProperty().bind(bind);
  }

  @FXML
  public void initialize() {
    firstNameText.setPromptText("John");
    lastNameText.setPromptText("Doe");
    employeeIdText.setPromptText("DJohn");
    passwordText.setPromptText("password");
    confirmPassText.setPromptText("password");
    employeeNumber.setPromptText("+09998887777");
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
  }

  public void setAccountEdit() {
    Account account = App.getAccountEdit();
    if (account == null) {
      clearFields();
      edit = false;
    } else {
      oldUsername = account.getUserName();
      first = account.getFirstName();
      last = account.getLastName();
      user = account.getUserName();
      number = account.getNumber();
      employeeCombo.getSelectionModel().select(account.getCred());

      firstNameText.setText(first);
      lastNameText.setText(last);
      employeeIdText.setText(user);
      employeeNumber.setText(number);
      edit = true;
    }
  }
}
