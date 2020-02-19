package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.fxml.FXML;

public class LanguageController {

    @FXML
    private void cancelExt(){
        App.getLanguageSRPop().getContent().remove(0);
        App.getRequestPop().getContent().add(App.getRequest());
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().show(App.getPrimaryStage());
    }
}
