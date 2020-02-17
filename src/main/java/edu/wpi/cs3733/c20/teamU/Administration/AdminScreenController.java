package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;

public class AdminScreenController {


    @FXML
    private void requestPowers() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdminRequest());
        App.getAdminRequestController().update();
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void editNodes() {
        App.getPopup().getContent().clear();
        App.getPrimaryStage().setScene(App.getAdminNodeScene());
        App.getPrimaryStage().setOpacity(1);
        App.getGraphEditController().enterNodeMode();
        App.getHome().setDisable(false);
        App.getPopup().hide();
    } //Admin edit nodes interface

    @FXML
    private void export() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getExport());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void editEdges() {
        App.getPopup().getContent().clear();
        App.getPrimaryStage().setScene(App.getAdminNodeScene());
        App.getPrimaryStage().setOpacity(1);
        App.getGraphEditController().exitNodeMode();
        App.getHome().setDisable(false);
        App.getPopup().hide();
    }

    @FXML
    private void detectClick() {

    }

    @FXML
    private void backHome() {
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getPopup().getContent().remove(0);
    }
}
