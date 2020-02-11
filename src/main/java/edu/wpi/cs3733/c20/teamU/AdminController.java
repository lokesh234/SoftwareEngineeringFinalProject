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
    @FXML private TableView<Service> serviceTable;
    @FXML private TableColumn<Service, String> date;
    @FXML private TableColumn<Service, String> requestID;
    @FXML private TableColumn<Service, String> name;
    @FXML private TableColumn<Service, String> requestType;
    @FXML private Button edit;
    @FXML private Button backButton;
    private ArrayList<Service> services;

//    private Popup popup = App.getPopup();

    @FXML
    private void detectClick() {
        if(serviceTable.getSelectionModel().getSelectedItem() != null) {
//            edit.setDisable(false);
//            selectedNode = serviceTable.getSelectionModel().getSelectedItem();
        }
    }

    private ObservableList<Service> arrayToOBList(){
        ArrayList<Service> temp = new ArrayList<Service>();
        ObservableList<Service> resultServices = FXCollections.observableArrayList();
   //     Database.getMedServices(temp);
        Database.getServices(temp);

        if(temp != null) {
            for (Service s : temp) {
                resultServices.add(s);
            }
        }

        return resultServices;
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
}
