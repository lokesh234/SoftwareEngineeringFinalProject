package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;

public class AdminController {


    @FXML
    private void requestPowers() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdminRequest());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void editPowers() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdminEdit());
        App.getPopup().show(App.getPrimaryStage());
    } //Admin edit nodes interface

    @FXML
    private void export() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdminExport());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    private void backHome() {
        App.getHome().setOpacity(1);
        App.getHome().setDisable(false);
        App.getPopup().getContent().remove(0);
    }
}
