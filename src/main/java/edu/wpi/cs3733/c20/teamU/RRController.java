package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Timestamp;
import java.util.Date;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;

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

    public RRController() {}

    public void setService() {
        System.out.println(App.getUser());
        service = App.getService();
        field1.setText(service.getDate());
        field2.setText(service.getName());
        field3.setText(service.getRequestID());
        field4.setText(service.getRequestType());
    }

    @FXML
    private void resolveRequest() {
//        Date time = new Date();
//        ServiceDatabase.serviceFinishedAdd(new Timestamp(time.getTime()).toString(), service.getRequestType(), App.getUser(), service.getRequestID());
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
//        App.getPopup().getContent().add(App.getHome());
//        App.getPopup().show(App.getPrimaryStage());
        App.getPrimaryStage().setScene(App.getHomeScene());
        App.getHome().setDisable(false);
        App.getHome().setOpacity(1);
    }


    @FXML
    private void initialize() {}
}
