package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

//import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;

public class MedicineController {

    @FXML private JFXTextArea comments;
    @FXML private JFXTextField last;
    @FXML private JFXTextField frequency;
    @FXML private JFXTextField first;
    @FXML private JFXTextField drug;
    @FXML private JFXButton backToRequest;
    @FXML private JFXButton submit;
    @FXML private JFXComboBox comboBox = new JFXComboBox();

    public void keyConfirm(){
        submit.fire();
    }

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
        if(userFreq.isEmpty() || userLast.isEmpty() || userFirst.isEmpty() || userDrug.isEmpty() || userDelivery.isEmpty() || userComment.isEmpty()) {
            // will print out some text eventually, right now nothing
            last.setStyle("-fx-border-color: red");
            comments.setStyle("-fx-border-color: red");
            frequency.setStyle("-fx-border-color: red");
            drug.setStyle("-fx-border-color: red");
            comboBox.setStyle("-fx-border-color: red");
            first.setStyle("-fx-border-color: red");
        } else {
            last.setStyle("-fx-border-color:  #FFEEC9");
            comments.setStyle("-fx-border-color:  #FFEEC9");
            frequency.setStyle("-fx-border-color:  #FFEEC9");
            drug.setStyle("-fx-border-color:  #FFEEC9");
            comboBox.setStyle("-fx-border-color:  #FFEEC9");
            first.setStyle("-fx-border-color:  #FFEEC9");
            DatabaseWrapper.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            //ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            clearField();
            backToRequest.fire();
        }
    }

    public void clearField() {
        last.clear();
        comments.clear();
        frequency.clear();
        first.clear();
        drug.clear();
        comboBox.getSelectionModel().clearSelection();
    }
    @FXML
    private void goBack(){
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
        App.getMedicinePop().getContent().clear();
        App.getRequestPop().getContent().add(App.getRequest());
        App.getRequestPop().show(App.getPrimaryStage());
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

        submit.setDisable(true);
        BooleanBinding blockCheckBox = comments.textProperty().isEmpty()
                .or(last.textProperty().isEmpty()).or(frequency.textProperty().isEmpty())
                .or(first.textProperty().isEmpty()).or(drug.textProperty().isEmpty()).or(comboBox.getSelectionModel().selectedItemProperty().isNull());
        submit.disableProperty().bind(blockCheckBox);
    }

//
//    @FXML private JFXTextArea comments;
//    @FXML private JFXTextField last;
//    @FXML private JFXTextField frequency;
//    @FXML private JFXTextField first;
//    @FXML private JFXTextField drug;
//    @FXML private JFXButton backToRequest;
//    @FXML private JFXButton submit;
//    @FXML private JFXComboBox comboBox = new JFXComboBox();
}
