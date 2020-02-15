package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.Administration.AdminRequestController;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RequestScreenController {
    @FXML private Button resolve;
    @FXML private Button cancel;
    @FXML private Button back;
    @FXML private Label field1;
    @FXML private Label field2;
    @FXML private Label field3;
    @FXML private Label field4;
    @FXML private Label field5;
    @FXML private Label field6;
    @FXML private Label field7;
    @FXML private Label field8;
    @FXML private Label field9;
    @FXML private Label field10;
    private Service service;
    AdminRequestController adminRequestController;

    private String date, name, Id, reqType;

    public RequestScreenController() {}

    public void setAttributes(AdminRequestController adminRequestController1) {
        adminRequestController = adminRequestController1;
    }

    public void setService() {
//        System.out.println(App.getUser());
        service = App.getService();
        date = service.getDate();
        name = service.getName();
        Id = service.getRequestID();
        reqType = service.getRequestType();
        field1.setText(date);
        field2.setText(name);
        field3.setText(Id);
        field4.setText(reqType);
    }

    @FXML
    private void resolveRequest() {

        String userName = App.getUser();
//        System.out.println(Id + date + name + reqType + " jjjjjjjjjj");
//        System.out.println(name);
        switch (name) {
            case "MEDIC":
                if (!ServiceDatabase.medicineSRDel(Integer.parseInt(date), userName)) System.out.println("oh no");
                break;
            case "SECUR":
                ServiceDatabase.securitySRDel(Integer.parseInt(date), userName);
                break;
        }
        adminRequestController.update();
        back.fire();
    }

    @FXML
    private void returnToResolve() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdminRequest());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void returnToHome() {
        App.getPopup().getContent().clear();
        App.getPrimaryStage().setScene(App.getHomeScene());
        App.getHome().setDisable(false);
        App.getHome().setOpacity(1);
    }


    @FXML
    private void initialize() {}
}
