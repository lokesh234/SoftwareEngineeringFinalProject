package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.RRController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class AdminRequestController {
  @FXML private TableView<Service> serviceTable;
  @FXML private TableColumn<Service, String> date;
  @FXML private TableColumn<Service, String> requestID;
  @FXML private TableColumn<Service, String> type;
  @FXML private TableColumn<Service, String> info;
  @FXML private Button close;
  @FXML private Button backButton;
  RRController rrController;

  public void setAttributes(RRController rrController1) {
    rrController = rrController1;
  }

  @FXML
  private void detectClick() {
    if (serviceTable.getSelectionModel().getSelectedItem() != null) {
      close.setDisable(false);
      App.setServiceEdit(serviceTable.getSelectionModel().getSelectedItem());
//      System.out.println(App.getService().getName());
      rrController.setService();
      //            selectedNode = serviceTable.getSelectionModel().getSelectedItem();
    }
  }


  @FXML
  private void closeRequest() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getResolveRequest());
    App.getPopup().show(App.getPrimaryStage());
  }

  public void update() {
    serviceTable.setItems(arrayToOBList());
    serviceTable.setVisible(true);
  }

  private ObservableList<Service> arrayToOBList() {
    ObservableList<Service> services = FXCollections.observableArrayList();
    ArrayList<Service> temp = new ArrayList<>();
    Database.getServices(temp);
    if (temp != null) {
      services.addAll(temp);
    }
    return services;
  }

  @FXML
  private void initialize() {
    requestID.setCellValueFactory(new PropertyValueFactory<>("date"));

    date.setCellValueFactory(new PropertyValueFactory<>("requestID"));

    type.setCellValueFactory(new PropertyValueFactory<>("name"));

    info.setCellValueFactory(new PropertyValueFactory<>("requestType"));
    if (!arrayToOBList().isEmpty()) {


      serviceTable.setItems(arrayToOBList());
    }
    close.setDisable(true);
  }

  @FXML
  private void backToAdmin() {
    App.getPopup().getContent().clear();
    App.getPopup().getContent().add(App.getAdmin());
    App.getPopup().show(App.getPrimaryStage());
  }
}
