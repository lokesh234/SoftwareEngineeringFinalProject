package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;

public class InformationController {
    @FXML
    public void closeHelpScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getInformationPopUp().getContent().remove(0);
    }

}
