package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.RequestScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class AdminRequestController {
  @FXML private TableView<Service> serviceTable1;
  @FXML private TableColumn<Service, String> date1;
  @FXML private TableColumn<Service, String> requestID1;
  @FXML private TableColumn<Service, String> type1;
  @FXML private TableColumn<Service, String> info1;
  @FXML private TableView<Service> serviceTable2;
  @FXML private TableColumn<Service, String> date2;
  @FXML private TableColumn<Service, String> requestID2;
  @FXML private TableColumn<Service, String> type2;
  @FXML private TableColumn<Service, String> info2;
  @FXML private JFXButton close;
  @FXML private JFXButton backButton;
  RequestScreenController requestScreenController;
  private boolean pending = true;

  public void setAttributes(RequestScreenController requestScreenController1) {
    requestScreenController = requestScreenController1;
  }

  @FXML
  private void detectClick() {
    if (serviceTable1.getSelectionModel().getSelectedItem() != null) {
      close.setDisable(false);
      App.setServiceEdit(serviceTable1.getSelectionModel().getSelectedItem());
//      System.out.println(App.getService().getName());
      requestScreenController.setService();
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
    pending = true;
    serviceTable1.setItems(arrayToOBList());
    pending = false;
    serviceTable2.setItems(arrayToOBList());
    serviceTable1.setVisible(true);
  }

  private ObservableList<Service> arrayToOBList() {
    ObservableList<Service> services = FXCollections.observableArrayList();
    ArrayList<Service> temp = new ArrayList<>();
    String user = App.getUser();
    System.out.println("init with user" + user);
    if (pending == true) {
      Database.getServices(temp, user);
    }
    else {
      Database.getFinishedServices(temp, user);
    }
    if (temp != null) {
      services.addAll(temp);
    }
    return services;
  }

  @FXML
  private void initialize() {
    requestID1.setCellValueFactory(new PropertyValueFactory<>("date"));

    date1.setCellValueFactory(new PropertyValueFactory<>("requestID"));

    type1.setCellValueFactory(new PropertyValueFactory<>("name"));

    info1.setCellValueFactory(new PropertyValueFactory<>("requestType"));

    requestID2.setCellValueFactory(new PropertyValueFactory<>("date"));

    date2.setCellValueFactory(new PropertyValueFactory<>("requestID"));

    type2.setCellValueFactory(new PropertyValueFactory<>("name"));

    info2.setCellValueFactory(new PropertyValueFactory<>("requestType"));

    pending = true;
    if (!arrayToOBList().isEmpty()) {
      serviceTable1.setItems(arrayToOBList());
    }
    pending = false;
    if (!arrayToOBList().isEmpty()) {
      serviceTable2.setItems(arrayToOBList());
    }
    close.setDisable(true);
  }

  @FXML
  private void backToAdmin() {
    App.getPopup().getContent().clear();
    if (App.getUser().equals("ADMIN")) {
      App.getPopup().getContent().add(App.getAdmin());
      App.getPopup().show(App.getPrimaryStage());
    }
    else {
      App.getHome().setDisable(false);
      App.getHome().setOpacity(1);
      App.getPrimaryStage().setScene(App.getHomeScene());
    }
  }
}
