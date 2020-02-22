package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ITController {
        @FXML
        private TextField last;
        @FXML private TextArea comments;
        @FXML private TextField first;
        @FXML private Button closeITScreen;
        @FXML private Button submit;
        @FXML private ComboBox comboBox = new ComboBox();

        @FXML
        private void getSubmission() {
            String userLast = last.getText();
            String userComment = comments.getText();
            String userFirst = first.getText();
            String userDelivery;
            if(comboBox.getValue() == null) userDelivery = "";
            else userDelivery = comboBox.getValue().toString();

            //TODO: specify which textfield to write in if empty
            if( userLast.isEmpty() || userFirst.isEmpty() || userDelivery.isEmpty() || userComment.isEmpty()) {
                // will print out some text eventually, right now nothing
                last.setStyle("-fx-border-color: red");
                comboBox.setStyle("-fx-border-color: red");
                first.setStyle("-fx-border-color: red");
            } else {
                last.setStyle("-fx-border-color:  #FFEEC9");
                comments.setStyle("-fx-border-color:  #FFEEC9");
                comboBox.setStyle("-fx-border-color:  #FFEEC9");
                first.setStyle("-fx-border-color:  #FFEEC9");
                DatabaseWrapper.ITSRAdd(userLast,userFirst,userDelivery,userComment);
                //ServiceDatabase.ITSRAdd(userFirst, userLast, issueType, userComment); TODO:Add method in database class
                clearField();
                goBack();
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

        public void clearField() {
            last.clear();
            comments.clear();
            first.clear();
            comboBox.getSelectionModel().clearSelection();
        }
        @FXML
        private void closeITForm(){
            App.getITPop().getContent().clear();
        }

        @FXML
        private void goBack(){
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
            App.getITPop().getContent().clear();
            App.getRequestPop().getContent().add(App.getRequest());
            App.getRequestPop().show(App.getPrimaryStage());
        }
        @FXML
        public void initialize() {
//        submit.setDisable(false);
            ObservableList<String> deliveryOptions =
                    FXCollections.observableArrayList(
                            "WIFI",
                            "Kiosk",
                            "Software",
                            "Hardware",
                            "Other",
                            "Don'tknow"
                    );
            comboBox.getItems().addAll(deliveryOptions);
            submit.setDisable(true);
            BooleanBinding blockCheckBox = (first.textProperty().isEmpty())
                    .or(last.textProperty().isEmpty()).or(comments.textProperty().isEmpty())
                    .or(comboBox.getSelectionModel().selectedItemProperty().isNull());
            submit.disableProperty().bind(blockCheckBox);
//            @FXML
//            private TextField last;
//            @FXML private TextArea comments;
//            @FXML private TextField first;
//            @FXML private Button closeITScreen;
//            @FXML private Button submit;
//            @FXML private ComboBox comboBox = new ComboBox();
        }

}
