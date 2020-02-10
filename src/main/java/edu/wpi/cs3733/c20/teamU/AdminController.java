package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

public class AdminController {
    @FXML private TableView<Service> serviceTable;
    @FXML private TableColumn<Service, String> date;
    @FXML private TableColumn<Service, String> requestID;
    @FXML private TableColumn<Service, String> name;
    @FXML private TableColumn<Service, String> requestType;

    private ObservableList<Service> arrayToOBList(){
        ObservableList<Service> services = FXCollections.observableArrayList();
        ArrayList<Service> temp = Database.getServices();
        for(Service s : temp){
            services.add(s);
        }
        return services;
    }

    @FXML
    private void initialize() {
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        requestID.setCellValueFactory(new PropertyValueFactory<>("RequestID"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        requestType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        serviceTable.setItems(arrayToOBList());
    }
}
