package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RequestController {

    @FXML
    public void closeRequestScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getRequestPop().getContent().remove(0);
    }

    @FXML
    public void openMedicineForm(ActionEvent e){
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getMedicinePop().getContent().add(App.getMedicine());
    }
}
