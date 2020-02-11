package edu.wpi.cs3733.c20.teamU;

import java.awt.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MedicineController {

    @FXML private TextField last;
    @FXML private TextArea comments;
    @FXML private TextField frequency;
    @FXML private TextField first;
    @FXML private TextField drug;
    @FXML private Button closeMedicineScreen;
    @FXML private ComboBox comboBox = new ComboBox();

    @FXML
    private void getSubmission() {
        String userLast = last.getText();
        String userComment = comments.getText();
        String userFreq = frequency.getText();
        String userFirst = first.getText();
        String userDrug = drug.getText();
        String userDelivery;
        if(comboBox.getValue() == null) userDelivery = "";
        else userDelivery = comboBox.getValue().toString();

        //TODO: specify which textfield to write in if empty
        if(userFreq.isEmpty() || userLast.isEmpty() || userFirst.isEmpty() || userDrug.isEmpty() || userDelivery.isEmpty()) {
            // will print out some text eventually, right now nothing
            last.setStyle("-fx-border-color: red");
//            comments.setStyle("-fx-border-color: red");
            frequency.setStyle("-fx-border-color: red");
            drug.setStyle("-fx-border-color: red");
            comboBox.setStyle("-fx-border-color: red");
            first.setStyle("-fx-border-color: red");
        } else {
            last.setStyle("-fx-border-color: clear");
            comments.setStyle("-fx-border-color: clear");
            frequency.setStyle("-fx-border-color: clear");
            drug.setStyle("-fx-border-color: clear");
            comboBox.setStyle("-fx-border-color: clear");
            first.setStyle("-fx-border-color: clear");
            ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            closeMedicineScreen.fire();
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
    private void closeMedicineForm(){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getMedicinePop().getContent().clear();
    }

    @FXML
    public void initialize() {
//        submit.setDisable(false);
        ObservableList<String> deliveryOptions =
                FXCollections.observableArrayList(
                        "Oral",
                        "Topical",
                        "Suppository"
                );
        comboBox.getItems().addAll(deliveryOptions);
    }
}
