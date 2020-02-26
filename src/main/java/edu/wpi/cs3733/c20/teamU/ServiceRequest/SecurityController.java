package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;


public class SecurityController {

    @FXML
    public void closeHelpScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getSecurityPop().getContent().remove(0);
    }

    public void addRequest() {
        Notifications.create().text("SERCURITY WILL ARRIVE AT KIOSK!").showWarning();
        ServiceDatabase.securitySRAdd();
    }

}
