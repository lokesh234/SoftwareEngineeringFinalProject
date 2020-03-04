package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.Administration.AdminRequestController;
import edu.wpi.cs3733.c20.teamU.Administration.AdministrationWrapper;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

//import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;

public class RequestScreenController {
    @FXML private JFXButton resolveButton;
    @FXML private JFXButton cancelButton;
    @FXML private JFXButton backButton;
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
        field6.setText("Resolve Request as: " + App.getUsernameTried());
    }

    @FXML
    private void resolveRequest() {

        //String userName = App.getUser();
        String userName = App.getUsernameTried();
//        System.out.println(Id + date + name + reqType + " jjjjjjjjjj");
//        System.out.println(name);
        switch (name) {
            case "MEDIC":
                if (!DatabaseWrapper.medicineSRDel(Integer.parseInt(date), userName, "MEDIC")) System.out.println("oh no | medicDEL broken");
                break;
            case "SECUR":
                DatabaseWrapper.securitySRDel(Integer.parseInt(date), userName, "SECUR");
                break;
            case "ITRAN":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "ITRAN", "InternalTransportSR", "ITRAN");
                break;
            case "ETRAN":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "ETRAN", "ExternalTransportSR", "ETRAN");
                break;
            case "DELIV":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "DELIV", "DeliverySR", "DELIV");
                break;
            case "FLOWR":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "FLOWR", "FlowersSR", "FLOWR");
                break;
            case "CLOWN":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "CLOWN", "ClownDeliverySR", "CLOWN");
                break;
            case "INTEC":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "INTEC", "ITSR", "INTEC");
                break;
            case "RELIG":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "RELIG", "ReligionSR", "RELIG");
                break;
            case "SANIT":
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, "SANIT", "SanitarySR", "SANIT");
                break;
            case "LANGE":
                DatabaseWrapper.languageSRDel(Integer.parseInt(date), userName, "LANGE");
                break;
            default:
                System.out.println(name);
                DatabaseWrapper.serviceRequestDel(Integer.parseInt(date), userName, name, name, name);
                break;
        }

        DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), name, "Resolved", " ");
        //adminRequestController.update();
        AdministrationWrapper.adminRequestControllerUpdate(adminRequestController);
        backButton.fire();
    }

    @FXML
    private void returnToResolve() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdminRequest());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void returnToAdmin() {
        App.getPopup().getContent().clear();
        if(App.getUser().equals("ADMIN")) {
            App.getPopup().getContent().add(App.getAdmin());
        } else {
            App.getPopup().getContent().add(App.getAdminRequest());
//            App.getHome().setDisable(false);
//            App.getHome().setOpacity(1);
        }
    }

    @FXML
    private void initialize() {

    }
}
