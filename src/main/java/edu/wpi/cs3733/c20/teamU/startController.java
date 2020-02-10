package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class startController {
    @FXML Button start;

    @FXML
    private void openHomeScreen(ActionEvent e){
        App.getPrimaryStage().setScene(App.getHomeScene());
    }
}
