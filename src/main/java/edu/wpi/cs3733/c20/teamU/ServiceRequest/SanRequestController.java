package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SanRequestController {

   // @FXML private JFXTextArea service;
    //@FXML private JFXTextField location;
    //@FXML private JFXTextField nature;
   // @FXML private JFXTextArea additional;
   // @FXML private JFXButton submit;


    @FXML private TextArea d;
    @FXML private TextField a;
    @FXML private TextField b;
    @FXML private TextField c;
    @FXML private Button back;
    @FXML private Button confirm;

    @FXML
    private void getSubmission() {
        String SanService = a.getText();
        String location = c.getText();
        String nature = b.getText();
        String comment = d.getText();
        //TODO: specify which textfield to write in if empty
        if(SanService.isEmpty() || location.isEmpty() || nature.isEmpty() || comment.isEmpty()) {
            // will print out some text eventually, right now nothing
            a.setStyle("-fx-border-color: red");
            b.setStyle("-fx-border-color: red");
            c.setStyle("-fx-border-color: red");
            d.setStyle("-fx-border-color: red");
        } else {
            a.setStyle("-fx-border-color:  #FFEEC9");
            b.setStyle("-fx-border-color:  #FFEEC9");
            c.setStyle("-fx-border-color:  #FFEEC9");
            d.setStyle("-fx-border-color:  #FFEEC9");
            //DatabaseWrapper.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            //ServiceDatabase.medicineSRAdd(userFirst, userLast, userDrug, userFreq, userDelivery, userComment);
            DatabaseWrapper.SanitarySRAdd(SanService,location,nature,comment);
            clearField();
            back.fire();
        }
    }

    public void clearField() {
        a.clear();
        b.clear();
        c.clear();
        d.clear();
    }

    @FXML
    private void closeSanSR(){
        App.getSanRequestPop().getContent().remove(0);
        App.getRequestPop().getContent().add(App.getRequest());
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    public void initialize() {
        confirm.setDisable(true);
        BooleanBinding blockCheckBox = (a.textProperty().isEmpty())
                .or(b.textProperty().isEmpty()).or(c.textProperty().isEmpty())
                .or(d.textProperty().isEmpty());
        confirm.disableProperty().bind(blockCheckBox);
//        @FXML private TextArea d;
//        @FXML private TextField a;
//        @FXML private TextField b;
//        @FXML private TextField c;
//            @FXML
//            private TextField last;
//            @FXML private TextArea comments;
//            @FXML private TextField first;
//            @FXML private Button closeITScreen;
//            @FXML private Button submit;
//            @FXML private ComboBox comboBox = new ComboBox();
    }

}