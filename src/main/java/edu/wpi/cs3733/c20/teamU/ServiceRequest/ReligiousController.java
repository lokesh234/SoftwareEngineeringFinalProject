package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReligiousController {

    @FXML private TextField lastName;
    @FXML private TextArea comments;
    @FXML private TextField firstName;
    @FXML private ComboBox comboBox;
    @FXML private JFXButton cancel;

    @FXML
    private void getSubmission() {
        String userLast = lastName.getText();
        String userComment = comments.getText();
        String userFreq = firstName.getText();
        String userBox;
        if(comboBox.getValue() == null) userBox = "";
        else userBox = comboBox.getValue().toString();

        //TODO: specify which textfield to write in if empty
        if (userFreq.isEmpty() || userLast.isEmpty() || userBox.isEmpty() || userComment.isEmpty()) {
            // will print out some text eventually, right now nothing
            lastName.setStyle("-fx-border-color: red");
//            comments.setStyle("-fx-border-color: red");
            comments.setStyle("-fx-border-color: red");
            firstName.setStyle("-fx-border-color: red");
            comboBox.setStyle("-fx-border-color: red");
        } else {
            lastName.setStyle("-fx-border-color: clear");
            comments.setStyle("-fx-border-color: clear");
            firstName.setStyle("-fx-border-color: clear");
            comboBox.setStyle("-fx-border-color: clear");
            //TODO:
            DatabaseWrapper.ReligionSRAdd(userLast,userFreq,userBox,userComment);
            //ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            clearField();
            cancel.fire();
        }
    }

    public void clearField() {
        lastName.clear();
        comments.clear();
        firstName.clear();
        comboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void closeMedicineForm(){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getReligiousPop().getContent().clear();
    }

    @FXML
    private void goBack(){
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
        App.getReligiousPop().getContent().clear();
        App.getRequestPop().getContent().add(App.getRequest());
        App.getRequestPop().show(App.getPrimaryStage());
    }
    @FXML
    public void initialize() {
//        submit.setDisable(false);
        ObservableList<String> deliveryOptions =
                FXCollections.observableArrayList(
                        "Protestantism",
                        "Catholicism",
                        "Judaism",
                        "Mormonism",
                        "Islam",
                        "Other",
                        "No Religion"

                );
        comboBox.getItems().addAll(deliveryOptions);
    }
}
