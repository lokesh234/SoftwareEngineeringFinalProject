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
}
