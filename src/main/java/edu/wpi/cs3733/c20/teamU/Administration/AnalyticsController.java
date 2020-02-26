package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.fxml.FXML;

public class AnalyticsController {
    @FXML
    private void initialize(){

    }

    @FXML
    public void backToAdmin(){
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        App.getPopup().show(App.getPrimaryStage());
    }
}
