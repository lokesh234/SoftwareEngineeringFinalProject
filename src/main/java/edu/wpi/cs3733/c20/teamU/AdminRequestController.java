package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.management.StandardEmitterMBean;
import java.util.ArrayList;

public class AdminRequestController {
    @FXML
    private TableView<Service> serviceTable;
    @FXML private TableColumn<Service, String> date;
    @FXML private TableColumn<Service, String> requestID;
    @FXML private TableColumn<Service, String> type;
    @FXML private TableColumn<Service, String> info;
    @FXML private Button close;
    @FXML private Button backButton;

//    private Popup popup = App.getPopup();

    @FXML
    private void detectClick() {
        if(serviceTable.getSelectionModel().getSelectedItem() != null) {
//            edit.setDisable(false);
//            selectedNode = serviceTable.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void closeRequest() {
        if (serviceTable.getSelectionModel().getSelectedItem() != null){

            System.out.println(serviceTable.getSelectionModel().getSelectedItem().getDate());
        }
    }

    protected void update() {
        serviceTable.setItems(arrayToOBList());
        serviceTable.setVisible(true);
    }

    private ObservableList<Service> arrayToOBList(){
        ObservableList<Service> services = FXCollections.observableArrayList();
        ArrayList<Service> temp = new ArrayList<>();
        Database.getServices(temp);
        if(temp != null) {
            services.addAll(temp);
        }
        return services;
    }

    @FXML
    private void initialize() {
        if(!arrayToOBList().isEmpty()) {
            requestID.setCellValueFactory(new PropertyValueFactory<>("date"));

            date.setCellValueFactory(new PropertyValueFactory<>("requestID"));

            type.setCellValueFactory(new PropertyValueFactory<>("name"));

            info.setCellValueFactory(new PropertyValueFactory<>("requestType"));

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
