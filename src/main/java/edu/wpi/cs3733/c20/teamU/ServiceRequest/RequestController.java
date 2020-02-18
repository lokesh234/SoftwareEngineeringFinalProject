package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RequestController {

    @FXML
    public void closeRequestScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getRequestPop().getContent().remove(0);
    }

    @FXML
    public void openMedicineForm(ActionEvent e){
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().getContent().remove(0);
        App.getMedicinePop().getContent().add(App.getMedicine());
        App.getMedicinePop().show(App.getPrimaryStage());
    }

    @FXML
    public void openITRequest() {
        App.getRequestPop().getContent().remove(0);
        App.getITPop().getContent().add(App.getIT());
        App.getITPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openReligiousRequest() {
        App.getRequestPop().getContent().remove(0);
        App.getReligiousPop().getContent().add(App.getReligious());
        App.getReligiousPop().show(App.getPrimaryStage());
    }



}
