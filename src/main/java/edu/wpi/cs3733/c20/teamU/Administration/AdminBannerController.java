package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

import java.io.IOException;

public class AdminBannerController {
//    @FXML private Label wilsonWong;
    private Pane adminBanner;
    private Popup adminBannerPop;

    protected void update(){
//        wilsonWong.setText(App.getUser().getUserName());
    }


    public void back(ActionEvent event) {
        App.loadHome();
        //App.getPathfindController().drawNodes();
        App.getTreeViewPop().getContent().clear();
        ServiceRequestWrapper.pathfindDrawNodes(App.getPathfindController());
        App.getPrimaryStage().setScene(App.getPathScene());
    }

    public void loadAdminBanner() {
        try {
            adminBanner = new Pane();
            adminBannerPop = new Popup();
            FXMLLoader adminBannerLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminSessionBanner.fxml"));
            adminBanner = adminBannerLoader.load();

            AdminBannerController adminBannerController = new AdminBannerController();
            adminBannerController = adminBannerLoader.getController();
            adminBannerController.update();

            adminBannerPop.getContent().clear();
            adminBannerPop.getContent().add(adminBanner);
            adminBannerPop.show(App.getPrimaryStage());
        }
        catch (IOException e) {
            return;
        }
    }

    protected void kill(){
//            App.loadHome();
            adminBannerPop.getContent().clear();
            adminBannerPop.show(App.getPrimaryStage());
    }
}
