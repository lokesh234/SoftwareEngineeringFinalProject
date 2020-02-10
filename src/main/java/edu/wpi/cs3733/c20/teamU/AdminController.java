package edu.wpi.cs3733.c20.teamU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import javafx.stage.Popup;

public class AdminController {
    @FXML private TableView<Service> serviceTable;
    @FXML private TableColumn<Service, String> date;
    @FXML private TableColumn<Service, String> requestID;
    @FXML private TableColumn<Service, String> name;
    @FXML private TableColumn<Service, String> requestType;
    @FXML private Button edit;
    @FXML private Button backButton;

//    private Parent parent;
//    private Parent home;
    private Popup popup = App.getPopup();

//    public void setAttributes(Parent parent, Parent home, Popup popup) {
//        this.popup = popup;
//        this.parent = parent;
//        this.home = home;
//    }

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
        for(Service s : temp){
            if(temp.size() == 0) break;
            services.add(s);
        }
        return services;
    }

    @FXML
    private void initialize() {
//        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        requestID.setCellValueFactory(new PropertyValueFactory<>("RequestID"));
//        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        requestType.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        serviceTable.setItems(arrayToOBList());
    }
}
