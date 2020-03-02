package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreditController {
    @FXML
    public void checkout(){
        App.getPrimaryStage().setScene(App.getHomeScene());}

    @FXML
    public void closeHelpScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getCreditPop().getContent().remove(0);
    }

}
