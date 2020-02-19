package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ExtTransportController {

    @FXML private JFXTextField firstNameText, lastNameText, passwordText, confirmPassText;
    @FXML private JFXButton confirm;

    @FXML
    public void cancelExt(ActionEvent e){
        App.getExtTransportPop().getContent().remove(0);
        App.getRequestPop().getContent().add(App.getRequest());
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    private void getSubmission() {
        String userLast = lastNameText.getText();
        String userpassword = passwordText.getText();
        String userFirst = firstNameText.getText();
        String userConfirm = confirmPassText.getText();

        //TODO: specify which textfield to write in if empty
        if(userLast.isEmpty() || userFirst.isEmpty() || userConfirm.isEmpty() || userpassword.isEmpty()) {
            // will print out some text eventually, right now nothing
            firstNameText.setStyle("-fx-border-color: red");
            passwordText.setStyle("-fx-border-color: red");
            lastNameText.setStyle("-fx-border-color: red");
            confirmPassText.setStyle("-fx-border-color: red");
        } else {
            firstNameText.setStyle("-fx-border-color:  #FFEEC9");
            lastNameText.setStyle("-fx-border-color:  #FFEEC9");
            passwordText.setStyle("-fx-border-color:  #FFEEC9");
            confirmPassText.setStyle("-fx-border-color:  #FFEEC9");
            //ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            clearField();
            confirm.fire();
        }
    }

    public void clearField() {
        firstNameText.clear();
        lastNameText.clear();
        passwordText.clear();
        confirmPassText.clear();
    }

}
