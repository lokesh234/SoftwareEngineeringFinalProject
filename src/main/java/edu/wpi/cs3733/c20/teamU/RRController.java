package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Timestamp;
import java.util.Date;

public class RRController {
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
    private edu.wpi.cs3733.c20.teamU.Service service;
    AdminRequestController adminRequestController;

    private String date, name, Id, reqType;

    public RRController() {}

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
                ServiceDatabase.medicineSRDel(Integer.parseInt(date), userName);
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
