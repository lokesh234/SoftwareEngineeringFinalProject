package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.fxml.FXML;

public class ChoosePathController {

    @FXML
    private void closePop(){
        App.getChoosePathPop().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
    }
}
