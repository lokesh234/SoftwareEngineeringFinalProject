package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class SecurityController {
    @FXML private Button cancelRequest;

    @FXML
    public void closeHelpScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getSecurityPop().getContent().remove(0);
    }

}
