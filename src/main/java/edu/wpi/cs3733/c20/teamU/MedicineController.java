package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MedicineController {

    @FXML private TextField last;
    @FXML private TextArea comments;
    @FXML private TextField frequency;
    @FXML private TextField first;
    @FXML private TextField drug;
    @FXML private Button submit; // check if user wants to submit stuff
    @FXML private ComboBox comboBox = new ComboBox();


    @FXML
    public void getSubmission() {
        String userLast = last.getText();
        String userComment = comments.getText();
        String userFreq = frequency.getText();
        String userFirst = first.getText();
        String userDrug = drug.getText();
        String userDelivery = comboBox.getValue().toString();

//        System.out.println(userDelivery);
        if(userFreq.isEmpty() || userLast.isEmpty() || userFirst.isEmpty() || userDrug.isEmpty() || userDelivery.isEmpty()) {
            // will print out some text eventually, right now nothing
        } else {
            ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            closeMedicineForm();
        }
//
//      if (userFreq.isEmpty()) {
//        frequency.setStyle("-fx-border-color: red");
//        emptyText = true;
//      }
//      if (userLast.isEmpty()) {
//        last.setStyle("-fx-border-color: red");
//        emptyText = true;
//      }
//      if (userFirst.isEmpty()) {
//        first.setStyle("-fx-border-color: red");
//        emptyText = true;
//      }
//      if (userDrug.isEmpty()) {
//        drug.setStyle("-fx-border-color: red");
//        emptyText = true;
//      }
//      if (userDelivery.isEmpty()) {
//        comboBox.setStyle("-fx-border-color: red");
//        emptyText = true;
//      }
    }


    @FXML
    public void closeMedicineForm(){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getMedicinePop().getContent().clear();
    }

    @FXML
    public void initialize() {
        ObservableList<String> deliveryOptions =
                FXCollections.observableArrayList(
                        "Oral",
                        "Topical",
                        "Suppository"
                );
        comboBox.getItems().addAll(deliveryOptions);
    }
}
