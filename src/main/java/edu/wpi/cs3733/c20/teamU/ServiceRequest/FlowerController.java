package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FlowerController {
    LocalDate date;

    @FXML private JFXTextField first;
    @FXML private JFXTextField last;
    @FXML private JFXTextArea giftNote;
    @FXML private JFXButton backToRequest;
    @FXML private JFXComboBox flowerCombo = new JFXComboBox();
    @FXML private JFXComboBox occasionCombo = new JFXComboBox();
    @FXML private JFXDatePicker datePick = new JFXDatePicker(date);



    @FXML
    private void submit() {
        String userLast = last.getText();
        String userNote = giftNote.getText();
        String userFirst = first.getText();

        String userFlower;
        if(flowerCombo.getValue() == null) userFlower = "";
        else userFlower = flowerCombo.getValue().toString();

        String userOccasion;
        if(occasionCombo.getValue() == null) userOccasion = "";
        else userOccasion = occasionCombo.getValue().toString();

        String userDate = datePick.getValue().toString();



        //TODO: specify which textfield to write in if empty

        // check if date is in the past. No time travel!
        boolean hasPassed = false;
        int dateCompVal = getDate().compareTo(datePick.getValue());
        if(dateCompVal <= 0 ){
            hasPassed = true;
        }

        if(userNote.isEmpty() || userLast.isEmpty() || userFirst.isEmpty() || userFlower.isEmpty() || userOccasion.isEmpty() || hasPassed) {
            // will print out some text eventually, right now nothing
            last.setStyle("-fx-border-color: red");
            giftNote.setStyle("-fx-border-color: red");
            flowerCombo.setStyle("-fx-border-color: red");
            occasionCombo.setStyle("-fx-border-color: red");
            first.setStyle("-fx-border-color: red");
            datePick.setStyle("-fx-border-color: red");
        } else {
            last.setStyle("-fx-border-color: #FFEEC9");
            giftNote.setStyle("-fx-border-color: #FFEEC9");
            flowerCombo.setStyle("-fx-border-color: #FFEEC9");
            occasionCombo.setStyle("-fx-border-color: #FFEEC9");
            first.setStyle("-fx-border-color: #FFEEC9");
            datePick.setStyle("-fx-border-color: #FFEEC9");

//            ServiceDatabase.flowerSRAdd(userFirst, userLast, userFlower, userOccasion, userDate, userNote);
            clearField();
            backToRequest.fire();
        }
    }

    public void clearField() {
        last.clear();
        giftNote.clear();
        first.clear();
        flowerCombo.getSelectionModel().clearSelection();
        occasionCombo.getSelectionModel().clearSelection();
        retrieveDate(); // will 'clear' the date field
    }
    @FXML
    private void goBack(){
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
        App.getFlowerPop().getContent().clear();
        App.getRequestPop().getContent().add(App.getRequest());
        App.getRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    public void initialize() {
        retrieveDate();
        System.out.println(getDate());
//        submit.setDisable(false);
        ObservableList<String> flowerOptions =
                FXCollections.observableArrayList(
                        "Roses",
                        "Tulips",
                        "Lillies"
                );
        ObservableList<String> occasionOptions =
                FXCollections.observableArrayList(
                        "Happy",
                        "Sad",
                        "Anytime"
                );
        flowerCombo.getItems().addAll(flowerOptions);
        occasionCombo.getItems().addAll(occasionOptions);
    }

    private void setDate(LocalDate date){
        this.date = date;
        datePick = new JFXDatePicker(date);
    }
    private LocalDate getDate(){
        return this.date;
    }

    private void retrieveDate(){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int year = now.getYear();

        LocalDate today = LocalDate.of(year, month, day);
        setDate(today);
    }
}
