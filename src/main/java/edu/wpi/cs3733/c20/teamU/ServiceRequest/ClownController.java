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

public class ClownController {

    @FXML private JFXTextField locationField, nClownsField, rNameField, otherField;
    @FXML private JFXDatePicker dateField;
    @FXML private JFXButton backToRequest;
    @FXML private JFXButton submit;
    @FXML private JFXComboBox comboBox = new JFXComboBox();
    @FXML private AnchorPane clownParent;

    public void keyConfirm(){
        submit.fire();
    }

    @FXML
    private void getSubmission() {
        String location = locationField.getText();
        String nClowns = nClownsField.getText();
        String rName = rNameField.getText();
        String date = dateField.getValue().toString();

        if(rName.isEmpty() || location.isEmpty() || date.isEmpty() || nClowns.isEmpty()) {
            locationField.setStyle("-fx-border-color: red");
            nClownsField.setStyle("-fx-border-color: red");
            rNameField.setStyle("-fx-border-color: red");
            dateField.setStyle("-fx-border-color: red");
        } else {
            locationField.setStyle("-fx-border-color:  #FFEEC9");
            nClownsField.setStyle("-fx-border-color:  #FFEEC9");
            rNameField.setStyle("-fx-border-color:  #FFEEC9");
            dateField.setStyle("-fx-border-color:  #FFEEC9");
            DatabaseWrapper.ClownDeliverySRAdd(location,Integer.parseInt(nClowns),rName,date);
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
        comboBox.getItems().addAll(deliveryOptions);

        submit.setDisable(true);
        BooleanBinding blockCheckBox = (nClownsField.textProperty().isEmpty())
                .or(locationField.textProperty().isEmpty()).or(rNameField.textProperty().isEmpty())
                .or(comboBox.getSelectionModel().selectedItemProperty().isNull());
        submit.disableProperty().bind(blockCheckBox);
        //comboBox.setOnAction(otherHandler);
///        otherField.setDisable(true);
    }
}
