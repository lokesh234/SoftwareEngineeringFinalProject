package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class AdminRequestController {
    @FXML
    private TableView<Service> serviceTable;
    @FXML private TableColumn<Service, String> date;
    @FXML private TableColumn<Service, String> requestID;
    @FXML private TableColumn<Service, String> name;
    @FXML private TableColumn<Service, String> requestType;
    @FXML private Button edit;
    @FXML private Button backButton;

//    private Popup popup = App.getPopup();

    @FXML
    private void detectClick() {
        if(serviceTable.getSelectionModel().getSelectedItem() != null) {
//            edit.setDisable(false);
//            selectedNode = serviceTable.getSelectionModel().getSelectedItem();
        }
    }

    private ObservableList<Service> arrayToOBList(){
        ObservableList<Service> services = FXCollections.observableArrayList();
        ArrayList<Service> temp = Database.getServices();
        if(temp != null) {
            services.addAll(temp);
        }
        return services;
    }

    @FXML
    private void initialize() {
        if(!arrayToOBList().isEmpty()) {
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            requestID.setCellValueFactory(new PropertyValueFactory<>("RequestID"));
            name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            requestType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            serviceTable.setItems(arrayToOBList());
        }
    }

    @FXML
    private void backToAdmin() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        App.getPopup().show(App.getPrimaryStage());
    }
}
