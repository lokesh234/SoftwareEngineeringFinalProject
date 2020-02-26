package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

public class ReligiousController {

    @FXML private TextField lastName;
    @FXML private TextArea comments;
    @FXML private TextField firstName;
    @FXML private ComboBox comboBox;
    @FXML private JFXButton cancel;
    @FXML private JFXButton confirm;

    public void keyConfirm(){
        confirm.fire();
    }

    @FXML
    private void getSubmission() {
        String userLast = lastName.getText();        String userComment = comments.getText();
        String userFreq = firstName.getText();
        String userBox;
        if(comboBox.getValue() == null) userBox = "";
        else userBox = comboBox.getValue().toString();

        if (userFreq.isEmpty() || userLast.isEmpty() || userBox.isEmpty() || userComment.isEmpty()) {
            lastName.setStyle("-fx-border-color: red");
            comments.setStyle("-fx-border-color: red");
            firstName.setStyle("-fx-border-color: red");
            comboBox.setStyle("-fx-border-color: red");
        } else {
            lastName.setStyle("-fx-border-color: clear");
            comments.setStyle("-fx-border-color: clear");
            firstName.setStyle("-fx-border-color: clear");
            comboBox.setStyle("-fx-border-color: clear");
            //TODO:
            String name = userFreq + " " + userLast;
            DatabaseWrapper.ReligionSRAdd(name,userBox,userComment);
            Notifications.create().text("Religious Request has been submitted!").show();
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
        confirm.setDisable(true);
        BooleanBinding blockCheckBox = (firstName.textProperty().isEmpty())
                .or(lastName.textProperty().isEmpty()).or(comments.textProperty().isEmpty())
                .or(comboBox.getSelectionModel().selectedItemProperty().isNull());
        confirm.disableProperty().bind(blockCheckBox);
//
//        @FXML private TextField lastName;
//        @FXML private TextArea comments;
//        @FXML private TextField firstName;
//        @FXML private ComboBox comboBox;
//        @FXML private JFXButton cancel;
//        @FXML private JFXButton confirm;
    }
}
