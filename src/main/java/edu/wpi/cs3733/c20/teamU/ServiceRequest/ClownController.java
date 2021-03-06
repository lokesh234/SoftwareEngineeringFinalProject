package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class ClownController {

    @FXML private JFXTextField locationField, nClownsField, rNameField, otherField;
    @FXML private JFXDatePicker dateField;
    @FXML private JFXButton backToRequest;
    @FXML private JFXButton submit;
    @FXML private AnchorPane clownParent;
    private NodeName nodeName = new NodeName();


    public void keyConfirm(){
        submit.fire();
    }

    @FXML
    private void getSubmission() {
        String location, rName, date;
        int nClowns;
        try {
            location = locationField.getText();
            nClowns = Integer.parseInt(nClownsField.getText());
            rName = rNameField.getText();
            date = dateField.getValue().toString();
        }
        catch (Exception e) {
            locationField.setStyle("-fx-border-color: red");
            nClownsField.setStyle("-fx-border-color: red");
            rNameField.setStyle("-fx-border-color: red");
            dateField.setStyle("-fx-border-color: red");
            return;
        }

        if(rName.isEmpty() || location.isEmpty() || date.isEmpty() || nClownsField.getText().isEmpty()) {
            locationField.setStyle("-fx-border-color: red");
            nClownsField.setStyle("-fx-border-color: red");
            rNameField.setStyle("-fx-border-color: red");
            dateField.setStyle("-fx-border-color: red");
        } else {
            locationField.setStyle("-fx-border-color:  #FFEEC9");
            nClownsField.setStyle("-fx-border-color:  #FFEEC9");
            rNameField.setStyle("-fx-border-color:  #FFEEC9");
            dateField.setStyle("-fx-border-color:  #FFEEC9");
            DatabaseWrapper.ClownDeliverySRAdd(location,nClowns,rName,date);
            Notifications.create().text("Clown will be sent!").show();
            clearField();
            backToRequest.fire();
        }
    }

    /*

    EventHandler<ActionEvent> otherHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (comboBox.getValue() != null && "Other".equals(comboBox.getValue().toString())) {
                otherField.setDisable(false);
            }
            else {
                otherField.setDisable(true);
                otherField.clear();
                otherField.setStyle("-fx-border-color:  #FFEEC9");
            }
            System.out.println("handling");
        }
    };

     */

    public void clearField() {
        locationField.clear();
        nClownsField.clear();
        rNameField.clear();
        //otherField.clear();
        dateField.getEditor().clear();
        //comboBox.getSelectionModel().clearSelection();
    }
    @FXML
    private void goBack(){
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
        App.getClownPop().getContent().clear();
        App.getRequestPop().getContent().add(App.getRequest());
        App.getRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    public void initialize() {
//        submit.setDisable(false);
        ObservableList<String> deliveryOptions =
                FXCollections.observableArrayList(
                        "Birthday",
                        "Make A Wish Foundation Visit",
                        "Other"
                );

        submit.setDisable(true);
        BooleanBinding blockCheckBox = (nClownsField.textProperty().isEmpty())
                .or(locationField.textProperty().isEmpty())
                .or(rNameField.textProperty().isEmpty())
                .or(dateField.getEditor().textProperty().isEmpty());
        submit.disableProperty().bind(blockCheckBox);
        nodeName.Populate();
        TextFields.bindAutoCompletion(locationField, nodeName.getAllNodeNames());
        //comboBox.setOnAction(otherHandler);
///        otherField.setDisable(true);
    }
}
