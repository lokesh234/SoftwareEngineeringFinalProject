package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ExtTransportController {

    @FXML
    public void cancelExt(ActionEvent e){
        App.getExtTransportPop().getContent().remove(0);
        App.getRequestPop().getContent().add(App.getRequest());
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().show(App.getPrimaryStage());
    }

}
