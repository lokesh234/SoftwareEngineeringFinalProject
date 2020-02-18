package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PathfindTextController {
    @FXML
    public void exitPopup(ActionEvent e){
        App.getTextDirectionsPop().getContent().remove(0);
    }

}
