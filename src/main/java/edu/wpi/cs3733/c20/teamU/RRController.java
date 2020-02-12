package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RRController {
    @FXML private Button resolve;
    @FXML private Button cancel;
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

    public RRController() {}

    public void setService() {
        service = App.getService();
        field1.setText(service.getDate());
        field2.setText(service.getName());
        field3.setText(service.getRequestID());
        field4.setText(service.getRequestType());
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
        App.getPopup().getContent().add(App.getHome());
        App.getPopup().show(App.getPrimaryStage());
    }


    @FXML
    private void initialize() {}
}
