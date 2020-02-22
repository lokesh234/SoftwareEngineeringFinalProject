package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;

public class ExtTransportController {

  @FXML private JFXTextField firstNameText;
  @FXML private JFXTextField lastNameText;
  @FXML private JFXTextField passwordText;
  @FXML private JFXTextField confirmPassText;
  @FXML private JFXTimePicker tp;
  @FXML private JFXDatePicker dp;
  @FXML private JFXButton cancel;
  @FXML private JFXButton confirm;

  @FXML
  private void getSubmission() {
    String userLast = lastNameText.getText();
    String userFirst = firstNameText.getText();
    String dest = passwordText.getText();
    String numOfPas = confirmPassText.getText().trim();
    String t = tp.getValue().toString().trim();
    String d = dp.getValue().toString().trim();

    //TODO: specify which textfield to write in if empty
    if (dest.isEmpty() || userLast.isEmpty() || userFirst.isEmpty() || numOfPas.isEmpty() || d
        .isEmpty() || t.isEmpty()) {
      // will print out some text eventually, right now nothing
      lastNameText.setStyle("-fx-border-color: red");
      confirmPassText.setStyle("-fx-border-color: red");
      firstNameText.setStyle("-fx-border-color: red");
      dp.setStyle("-fx-border-color: red");
      tp.setStyle("-fx-border-color: red");
      passwordText.setStyle("-fx-border-color: red");
    } else {
      lastNameText.setStyle("-fx-border-color:  #FFEEC9");
      confirmPassText.setStyle("-fx-border-color:  #FFEEC9");
      firstNameText.setStyle("-fx-border-color:  #FFEEC9");
      passwordText.setStyle("-fx-border-color:  #FFEEC9");
      System.out.println(Integer.parseInt(numOfPas));
      DatabaseWrapper
          .extTransportSRAdd(userLast, userFirst, dest, t, d, Integer.parseInt(numOfPas));
      clearField();
      cancel.fire();
    }
  }

  public void clearField() {
    lastNameText.clear();
    firstNameText.clear();
    passwordText.clear();
    confirmPassText.clear();
    dp.getEditor().clear();
    tp.getEditor().clear();
  }

  @FXML
  private void goBack() {
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
//    clearField();
    App.getExtTransportPop().getContent().clear();
    App.getRequestPop().getContent().add(App.getRequest());
    App.getRequestPop().show(App.getPrimaryStage());
  }

  @FXML
  public void initialize() {
    confirm.setDisable(true);
    BooleanBinding blockCheckBox = (firstNameText.textProperty().isEmpty())
            .or(lastNameText.textProperty().isEmpty()).or(passwordText.textProperty().isEmpty())
            .or(confirmPassText.textProperty().isEmpty()).or(tp.getEditor().textProperty().isEmpty())
            .or(dp.getEditor().textProperty().isEmpty());
  }

//  @FXML private JFXTextField firstNameText;
//  @FXML private JFXTextField lastNameText;
//  @FXML private JFXTextField passwordText;
//  @FXML private JFXTextField confirmPassText;
//  @FXML private JFXTimePicker tp;
//  @FXML private JFXDatePicker dp;

}
