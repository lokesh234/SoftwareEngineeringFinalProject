package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RequestController {

    @FXML
    public void closeRequestScreen(ActionEvent e){
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getRequestPop().getContent().clear();
    }

    @FXML
    public void openMedicineForm(ActionEvent e){
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getRequestPop().getContent().clear();
        App.getMedicinePop().getContent().add(App.getMedicine());
        App.getMedicinePop().show(App.getPrimaryStage());
    }

    @FXML
    public void openITRequest() {
        App.getRequestPop().getContent().remove(0);
        App.getRequestPop().getContent().clear();
        App.getITPop().getContent().add(App.getIT());
        App.getITPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openReligiousRequest() {
        App.getRequestPop().getContent().remove(0);
        App.getReligiousPop().getContent().add(App.getReligious());
        App.getReligiousPop().show(App.getPrimaryStage());
    }


    @FXML
    public void openExtTransportRequest() {
        App.getRequestPop().getContent().remove(0);
        App.getExtTransportPop().getContent().add(App.getExternalTransport());
        App.getExtTransportPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openIntTransportRequest() {
        App.getRequestPop().getContent().remove(0);
        App.getIntTransportPop().getContent().add(App.getInternalTransport());
        App.getIntTransportPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openFlowerForm() {
        App.getRequestPop().getContent().clear();
        App.getFlowerPop().getContent().add(App.getFlower());
        App.getFlowerPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openClownRequest() {
        App.getRequestPop().getContent().remove(0);
        App.getClownPop().getContent().add(App.getClown());
        App.getClownPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openSanRequest(){
        App.getRequestPop().getContent().remove(0);
        App.getSanRequestPop().getContent().add(App.getSanRequest());
        App.getSanRequestPop().show(App.getPrimaryStage());
    }

    @FXML
    public void openLangaugeRequest(){
        App.getRequestPop().getContent().remove(0);
        App.getLanguageSRPop().getContent().add(App.getLanguageSR());
        App.getLanguageSRPop().show(App.getPrimaryStage());
    }

}
