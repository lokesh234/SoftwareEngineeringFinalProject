package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.controlsfx.control.Notifications;

public class FlowerController {
    LocalDate today;
    LocalDate selected;

    @FXML private JFXTextField first;
    @FXML private JFXTextField last;
    @FXML private JFXTextArea giftNote;
    @FXML private JFXButton backToRequest;
    @FXML private JFXButton submit;
    @FXML private JFXComboBox flowerCombo = new JFXComboBox();
    @FXML private JFXComboBox occasionCombo = new JFXComboBox();
    @FXML private JFXDatePicker datePick;
    @FXML private JFXChipView flowerChip = new JFXChipView();
    @FXML private JFXTextField room;

    public void keyConfirm(){
        submit.fire();
    }

    @FXML
    private void flowerTypeAdd() {
        if (!flowerCombo.getSelectionModel().isEmpty()) {
            flowerCombo.setStyle("-fx-border-color: #FFEEC9");
            flowerChip.getChips().add(flowerCombo.getSelectionModel().getSelectedItem());
            flowerCombo.getSelectionModel().clearSelection();
        } else {
            flowerCombo.setStyle("-fx-border-color: red");
        }
    }



    @FXML
    private void getSubmission() {
        String userLast = last.getText();
        String userNote = giftNote.getText();
        String userFirst = first.getText();
        String userRoom = room.getText();

        String userFlower;
        if(flowerChip.getChips().toString() == null) userFlower = "";
        else userFlower = flowerChip.getChips().toString();

        String userOccasion;
        if(occasionCombo.getValue() == null) userOccasion = "";
        else userOccasion = occasionCombo.getValue().toString();

        setSelected(datePick.getValue());
        String flowerChipString = flowerChip.getChips().toString();

        boolean hasRoses = flowerChipString.contains("Roses");
        boolean hasLillies = flowerChipString.contains("Lillies");
        boolean hasTulips = flowerChipString.contains("Tulips");

        String userDate;
        if(datePick.getValue() == null) userDate = "";
        else userDate = datePick.getValue().toString();

        //TODO: specify which textfield to write in if empty

        // check if date is in the past. No time travel!
        // getDate is the current date
        // datePick is the selected date
        // if getDate (today) is *after* the selected date,
        // that means they have selected a date in the past

        boolean hasPassed = datePick.getValue() != null && getToday().isAfter(datePick.getValue()); //Short-circuiting is the best!

        if(userNote.isEmpty() || userLast.isEmpty() || userFirst.isEmpty() || userFlower.isEmpty() || userOccasion.isEmpty() || userRoom.isEmpty()|| hasPassed) {
            // will print out some text eventually, right now nothing
            last.setStyle("-fx-border-color: red");
            giftNote.setStyle("-fx-border-color: red");
            flowerCombo.setStyle("-fx-border-color: red");
            occasionCombo.setStyle("-fx-border-color: red");
            first.setStyle("-fx-border-color: red");
            datePick.setStyle("-fx-border-color: red");
            room.setStyle("-fx-border-color: red");
        } else {
            Notifications.create().text("Flower Request has been submitted!").show();

//            ServiceDatabase.flowerSRAdd(userLast, userFirst, hasRoses, hasTulips, hasLillies, userOccasion, userDate, userNote, userRoom);
            DatabaseWrapper.FlowersSRAdd(userFirst,userLast,hasRoses,hasTulips,hasLillies,userOccasion,userDate,userNote,userRoom);
            clearField();
            backToRequest.fire();
        }
    }

    public void clearField() {
        last.setStyle("-fx-border-color: #FFEEC9");
        giftNote.setStyle("-fx-border-color: #FFEEC9");
        flowerCombo.setStyle("-fx-border-color: #FFEEC9");
        occasionCombo.setStyle("-fx-border-color: #FFEEC9");
        first.setStyle("-fx-border-color: #FFEEC9");
        datePick.setStyle("-fx-border-color: #FFEEC9");
        room.setStyle("-fx-border-color: #FFEEC9");

        last.clear();
        giftNote.clear();
        first.clear();
        flowerCombo.getSelectionModel().clearSelection();
        occasionCombo.getSelectionModel().clearSelection();
        datePick.getEditor().clear();
        flowerChip.getChips().clear();
        room.clear();
    }
    @FXML
    private void goBack(){
        clearField();
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
        App.getFlowerPop().getContent().clear();
        App.getRequestPop().getContent().add(App.getRequest());
        App.getRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    public void initialize() {
        //first set what today is, then dont touch it
        LocalDate today = retrieveDate();
        setToday(today);

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

        submit.setDisable(true);
        BooleanBinding blockCheckBox = (first.textProperty().isEmpty())
                .or(last.textProperty().isEmpty()).or(giftNote.textProperty().isEmpty())
                .or(room.textProperty().isEmpty())
                .or(flowerChip.converterProperty().isNull())
                .or(occasionCombo.getSelectionModel().selectedItemProperty().isNull())
                .or(datePick.getEditor().textProperty().isEmpty());
        submit.disableProperty().bind(blockCheckBox);

//        @FXML private JFXTextField first;
//        @FXML private JFXTextField last;
//        @FXML private JFXTextArea giftNote;
//        @FXML private JFXButton backToRequest;
//        @FXML private JFXButton submit;
//        @FXML private JFXComboBox flowerCombo = new JFXComboBox();
//        @FXML private JFXComboBox occasionCombo = new JFXComboBox();
//        @FXML private JFXDatePicker datePick;
//        @FXML private JFXChipView flowerChip = new JFXChipView();
//        @FXML private JFXTextField room;
    }

    private void setSelected(LocalDate date){
        this.selected = date;
    }


    private LocalDate getSelected(){
        return this.selected;
    }

    private LocalDate retrieveDate(){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int year = now.getYear();

        LocalDate today = LocalDate.of(year, month, day);
        return today;
    }
    private void setTodayDate(){
        LocalDate today = retrieveDate();
        datePick = new JFXDatePicker(today);
    }

    private void setToday(LocalDate today){
        this.today = today;
    }
    private LocalDate getToday(){
        return this.today;
    }
}
