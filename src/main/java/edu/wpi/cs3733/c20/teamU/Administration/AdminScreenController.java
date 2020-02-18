package edu.wpi.cs3733.c20.teamU.Administration;


import edu.wpi.cs3733.c20.teamU.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminScreenController {

    @FXML private AnchorPane adminScreen;
    @FXML private Button home;
    @FXML private Button editnodes;
    @FXML private Button reqPow;
    @FXML private Button editedges;
    @FXML private Button exportButton;

    /**
     * Admin + general users...
     * TODO: make it more specific to user credentials
     */
    @FXML
    private void requestPowers() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdminRequest());
        App.getAdminRequestController().update();
        App.getPopup().show(App.getPrimaryStage());
    }

    /**
     * Admin acess
     */
    @FXML
    private void editNodes() {
        App.getPopup().getContent().clear();
        App.getPrimaryStage().setScene(App.getAdminNodeScene());
        App.getPrimaryStage().setOpacity(1);
        App.getGraphEditController().enterNodeMode();
        App.getHome().setDisable(false);
        App.getPopup().hide();
    } //Admin edit nodes interface

    /**
     * Admin access
     */
    @FXML
    private void export() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getExport());
        App.getPopup().show(App.getPrimaryStage());
    }

    /**
     * Admin access
     */
    @FXML
    private void editEdges() {
        App.getPopup().getContent().clear();
        App.getPrimaryStage().setScene(App.getAdminNodeScene());
        App.getPrimaryStage().setOpacity(1);
        App.getGraphEditController().exitNodeMode();
        App.getHome().setDisable(false);
        App.getPopup().hide();
    }

    /**
     * global user access
     */
    @FXML
    private void backHome() {
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getPopup().getContent().clear();

    }

    /**
     * TODO: implement a better system.
     * enables certain access based on user credentials
     * @param user name of the user
     */
    public void enableBasedOnCred(String user) {
        switch (user) {
            case "MEDIC":
                editedges.setDisable(true);
                exportButton.setDisable(true);
                editnodes.setDisable(true);
                break;
            case "SECUR":
                editedges.setDisable(true);
                exportButton.setDisable(true);
                editnodes.setDisable(true);
                break;
            default:
                break;
        }
    }

    @FXML
    private void initialize() {
//        EventHandler<MouseEvent> checkAccess = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//            }
//        };
//        editnodes.setDisable(true);
//        reqPow.setDisable(true);
//        editedges.setDisable(true);
//        exportButton.setDisable(true);
//        adminScreen.addEventFilter(MOUSE_PRESSED, checkAccess);
    }
}
