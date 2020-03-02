package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

import java.io.IOException;

public class AdminBannerController {
//    @FXML private Label wilsonWong;
    private Pane adminBanner;
    private Popup adminBannerPop;
    @FXML
    Label userName;

    @FXML
    protected void update(String username){
        this.userName.setText(username);
    }

    public void loadAdminBanner(String username) {
        try {
            adminBanner = new Pane();
            adminBannerPop = new Popup();
            FXMLLoader adminBannerLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminSessionBanner.fxml"));
            adminBanner = adminBannerLoader.load();

            AdminBannerController adminBannerController = new AdminBannerController();
            adminBannerController = adminBannerLoader.getController();
            adminBannerController.update(username);

            adminBannerPop.getContent().clear();
            adminBannerPop.setX(App.getPrimaryStage().getX() + 1500);
            adminBannerPop.setY(App.getPrimaryStage().getY() + 40);
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
