package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
//import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class IntTransportController {

    @FXML private JFXTextField firstNameText;
    @FXML private JFXTextField lastNameText;
    @FXML private JFXTextField start;
    @FXML private JFXTextField end;
    @FXML private JFXRadioButton crutches;
    @FXML private JFXRadioButton wheelchair;
    @FXML private JFXButton cancel;


    @FXML
    private void getSubmission() {
        String userFirst = firstNameText.getText();
        String userLast = lastNameText.getText();
        String userStart = start.getText();
        String userEnd = end.getText();
        String userEquipment;



        //TODO: specify which textfield to write in if empty
        if(userFirst.isEmpty() || userLast.isEmpty() || userStart.isEmpty() || userEnd.isEmpty() || (!wheelchair.isSelected() && !crutches.isSelected())) {
            // will print out some text eventually, right now nothing
            firstNameText.setStyle("-fx-border-color: red");
            lastNameText.setStyle("-fx-border-color: red");
            start.setStyle("-fx-border-color: red");
            end.setStyle("-fx-border-color: red");
            wheelchair.setStyle("-fx-border-color: red");
            crutches.setStyle("-fx-border-color: red");
        } else {
            firstNameText.setStyle("-fx-border-color:  #FFEEC9");
            lastNameText.setStyle("-fx-border-color:  #FFEEC9");
            start.setStyle("-fx-border-color:  #FFEEC9");
            end.setStyle("-fx-border-color:  #FFEEC9");
            wheelchair.setStyle("-fx-border-color:  #FFEEC9");
            crutches.setStyle("-fx-border-color:  #FFEEC9");
            if (wheelchair.isSelected()){
                userEquipment = "Wheelchair";
            }
            else {
                userEquipment = "Crutches";
            }

            //ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            clearField();
            cancel.fire();
        }
    }

    public void clearField() {
        firstNameText.clear();
        lastNameText.clear();
        start.clear();
        end.clear();
        initialize();
        firstNameText.setStyle("-fx-border-color:  null");
        lastNameText.setStyle("-fx-border-color:  null");
        start.setStyle("-fx-border-color:  null");
        end.setStyle("-fx-border-color:  null");
        wheelchair.setStyle("-fx-border-color:  null");
        crutches.setStyle("-fx-border-color:  null");
    }
    @FXML
    private void cancelExt(){
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
        clearField();
        App.getIntTransportPop().getContent().clear();
        App.getRequestPop().getContent().add(App.getRequest());
        App.getRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    public void initialize() {
//        submit.setDisable(false);
        ToggleGroup equipmentGroup = new ToggleGroup();
        wheelchair.setToggleGroup(equipmentGroup);
        crutches.setToggleGroup(equipmentGroup);
        equipmentGroup.selectToggle(null);
    }

}
