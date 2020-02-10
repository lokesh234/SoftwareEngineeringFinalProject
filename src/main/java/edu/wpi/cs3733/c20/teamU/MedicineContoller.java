package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXML;

public class MedicineContoller {
    @FXML
    public void closeMedicineForm(){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getMedicinePop().getContent().remove(0);
    }
}
