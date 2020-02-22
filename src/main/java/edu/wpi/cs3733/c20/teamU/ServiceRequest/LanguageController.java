package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class LanguageController {


    @FXML
    JFXTextField firstNameText, lastNameText, passwordText;
    @FXML
    JFXButton cancel, confirm;
    @FXML
    JFXRadioButton Japenese, Russian, Hindi, Spanish, Chinese, Ethiopian;

    @FXML
    private void cancelExt(){
        clearField();
        App.getLanguageSRPop().getContent().remove(0);
        App.getRequestPop().getContent().add(App.getRequest());
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    private void getSubmission() {
        String userFirst = firstNameText.getText();
        String userLast = lastNameText.getText();
        String userPass = passwordText.getText();
        String userLanguage = "";

        //TODO: specify which textfield to write in if empty
        if (userFirst.isEmpty() || userLast.isEmpty() || userPass.isEmpty() || (!Japenese.isSelected() && !Russian.isSelected() && !Hindi.isSelected() && !Spanish.isSelected() && !Chinese.isSelected() && !Ethiopian.isSelected())) {
            // will print out some text eventually, right now nothing
            firstNameText.setStyle("-fx-border-color: red");
//            comments.setStyle("-fx-border-color: red");
            lastNameText.setStyle("-fx-border-color: red");
            passwordText.setStyle("-fx-border-color: red");
            Japenese.setStyle("-fx-border-color: red");
            Russian.setStyle("-fx-border-color: red");
            Hindi.setStyle("-fx-border-color: red");
            Spanish.setStyle("-fx-border-color: red");
            Chinese.setStyle("-fx-border-color: red;");
            Ethiopian.setStyle("-fx-border-color: red");
        } else {
            firstNameText.setStyle("-fx-border-color: #FFEEC9");
            lastNameText.setStyle("-fx-border-color: #FFEEC9");
            passwordText.setStyle("-fx-border-color: #FFEEC9");
            Japenese.setStyle("-fx-border-color: #FFEEC9");
            Russian.setStyle("-fx-border-color: #FFEEC9");
            Hindi.setStyle("-fx-border-color: #FFEEC9");
            Spanish.setStyle("-fx-border-color: #FFEEC9");
            Chinese.setStyle("-fx-border-color: #FFEEC9");
            Ethiopian.setStyle("-fx-border-color: #FFEEC9");
            if (Japenese.isSelected()){
                userLanguage = "Japense";
            }
            if (Russian.isSelected()){
                userLanguage = "Russian";
            }
            if (Spanish.isSelected()){
                userLanguage = "Spanish";
            }
            if (Hindi.isSelected()){
                userLanguage = "Hindi";
            }
            if (Chinese.isSelected()){
                userLanguage = "Chinese";
            }
            if (Ethiopian.isSelected()){
                userLanguage = "Ethiopian";
            }

            //TODO:
            //ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            DatabaseWrapper.languageSRAdd(userLast,userFirst,userLanguage, userPass);
            clearField();
            cancel.fire();
        }
    }

    public void clearField() {
        lastNameText.clear();
        firstNameText.clear();
        passwordText.clear();
        initialize();
        firstNameText.setStyle("-fx-border-color: null");
        lastNameText.setStyle("-fx-border-color: null");
        passwordText.setStyle("-fx-border-color: null");
        Japenese.setStyle("-fx-border-color: null");
        Russian.setStyle("-fx-border-color: null");
        Hindi.setStyle("-fx-border-color: null");
        Spanish.setStyle("-fx-border-color: null");
        Chinese.setStyle("-fx-border-color: null");
        Ethiopian.setStyle("-fx-border-color: null");
    }

    @FXML
    public void initialize(){
        ToggleGroup equipmentGroup = new ToggleGroup();
        Japenese.setToggleGroup(equipmentGroup);
        Russian.setToggleGroup(equipmentGroup);
        Spanish.setToggleGroup(equipmentGroup);
        Hindi.setToggleGroup(equipmentGroup);
        Chinese.setToggleGroup(equipmentGroup);
        Ethiopian.setToggleGroup(equipmentGroup);
        equipmentGroup.selectToggle(null);

        confirm.setDisable(true);
        BooleanBinding blockCheckBox = (firstNameText.textProperty().isEmpty())
                .or(lastNameText.textProperty().isEmpty()).or(passwordText.textProperty().isEmpty())
                //.or(flowerChip.getTypeSelector().isEmpty())
                .or(Japenese.selectedProperty().and(Russian.selectedProperty()).and(Hindi.selectedProperty())
                        .and(Spanish.selectedProperty()).and(Chinese.selectedProperty()).and(Ethiopian.selectedProperty()));
        confirm.disableProperty().bind(blockCheckBox);
//        @FXML
//        JFXTextField firstNameText, lastNameText, passwordText;
//        @FXML
//        JFXButton cancel, confirm;
//        @FXML
//        JFXRadioButton Japenese, Russian, Hindi, Spanish, Chinese, Ethiopian;
//
    }

}
