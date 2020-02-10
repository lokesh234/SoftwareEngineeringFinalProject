package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MedicineController {

    @FXML private TextField last;
//    @FXML private TextField comments;
    @FXML private TextField frequency;
    @FXML private TextField first;
    @FXML private TextField drug;
    @FXML private Button submit;

    @FXML
    public void getSubmission() {
        String userLast = last.getText();
//        String userComment = comments.getText();
        String userFreq = frequency.getText();
        String userFirst = first.getText();
        String userDrug = drug.getText();

//        if(userComment.isEmpty() || )

    }


    @FXML
    public void closeMedicineForm(){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getMedicinePop().getContent().remove(0);
    }
}
