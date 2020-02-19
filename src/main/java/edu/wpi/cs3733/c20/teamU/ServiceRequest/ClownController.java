package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ClownController {

    @FXML private JFXTextField locationField, nClownsField, rNameField, otherField;
    @FXML private JFXDatePicker dateField;
    @FXML private JFXButton backToRequest;
    @FXML private JFXComboBox comboBox = new JFXComboBox();
    @FXML private AnchorPane clownParent;

    @FXML
    private void getSubmission() {
        String location = locationField.getText();
        String nClowns = nClownsField.getText();
        String rName = rNameField.getText();
        //String other = otherField.getText();
        String date = dateField.getValue().toString();
        //String userDelivery;
        //if(comboBox.getValue() == null) userDelivery = "";
        //else userDelivery = comboBox.getValue().toString();

        //TODO: specify which textfield to write in if empty
        if(rName.isEmpty() || location.isEmpty() || date.isEmpty() || nClowns.isEmpty()) {
            // will print out some text eventually, right now nothing
            locationField.setStyle("-fx-border-color: red");
            nClownsField.setStyle("-fx-border-color: red");
            rNameField.setStyle("-fx-border-color: red");
            //if (!otherField.isDisable()) otherField.setStyle("-fx-border-color: red");
            //else otherField.setStyle("-fx-border-color:  #FFEEC9");
            //comboBox.setStyle("-fx-border-color: red");
            dateField.setStyle("-fx-border-color: red");
        } else {
            locationField.setStyle("-fx-border-color:  #FFEEC9");
            nClownsField.setStyle("-fx-border-color:  #FFEEC9");
            rNameField.setStyle("-fx-border-color:  #FFEEC9");
            //otherField.setStyle("-fx-border-color:  #FFEEC9");
            //comboBox.setStyle("-fx-border-color:  #FFEEC9");
            dateField.setStyle("-fx-border-color:  #FFEEC9");
            //DatabaseWrapper.clownAdd();
            DatabaseWrapper.ClownDeliverySRAdd(location,Integer.getInteger(nClowns),rName,date);
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
        //comboBox.setOnAction(otherHandler);
///        otherField.setDisable(true);
    }
}
