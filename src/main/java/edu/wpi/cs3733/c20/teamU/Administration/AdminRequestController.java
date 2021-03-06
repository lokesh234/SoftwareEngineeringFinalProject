package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.RequestScreenController;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

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
  @FXML private JFXComboBox comboBox;
  @FXML private Label label;
  RequestScreenController requestScreenController;
  private boolean pending = true;
  private String cred = "";
  private AdminBannerController adminBannerController;

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
    Notifications.create().text("Request has been closed!").show();
  }

  @FXML
  public void cred(){
    cred = App.getUser().getCred();
    if(cred.equals("ADMIN")){
      comboBox.setDisable(false);
    }
    else {
      comboBox.setDisable(true);
    }
    comboBox.setPromptText(cred);
  }

  @FXML
  public void selection(){
    cred = comboBox.getValue().toString();
    System.out.println(cred);
  }

  public void update() {
    pending = true;
    serviceTable1.setItems(arrayToOBList());
    pending = false;
    serviceTable2.setItems(arrayToOBList());
    serviceTable1.setVisible(true);
    comboBox.getItems().clear();
    ObservableList<String> deliveryOptions =
            FXCollections.observableArrayList(
                    ServiceRequestWrapper.getAllServiceType()
            );
    System.out.println("delivery " + deliveryOptions);
    comboBox.getItems().addAll(deliveryOptions);
  }

  private ObservableList<Service> arrayToOBList() {
    ObservableList<Service> services = FXCollections.observableArrayList();
    ArrayList<Service> temp = new ArrayList<>();
    if (pending == true) {
      DatabaseWrapper.getServices(temp, cred);
    }
    else {
      DatabaseWrapper.getFinishedServices(temp, cred);
    }
    if (temp != null) {
      services.addAll(temp);
    }
    return services;
  }

  @FXML
  protected void initialize() {
    requestID1.setCellValueFactory(new PropertyValueFactory<>("date"));

    date1.setCellValueFactory(new PropertyValueFactory<>("requestID"));

    type1.setCellValueFactory(new PropertyValueFactory<>("name"));

    info1.setCellValueFactory(new PropertyValueFactory<>("requestType"));

    requestID2.setCellValueFactory(new PropertyValueFactory<>("date"));

    date2.setCellValueFactory(new PropertyValueFactory<>("requestID"));

    type2.setCellValueFactory(new PropertyValueFactory<>("name"));

    info2.setCellValueFactory(new PropertyValueFactory<>("requestType"));

    comboBox.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        selection();
        update();
        System.out.println("changes");
      }
    });


    pending = true;
    if (!arrayToOBList().isEmpty()) {
      serviceTable1.setItems(arrayToOBList());
    }
    pending = false;
    if (!arrayToOBList().isEmpty()) {
      serviceTable2.setItems(arrayToOBList());
    }
    close.setDisable(true);

    ObservableList<String> deliveryOptions =
            FXCollections.observableArrayList(
                    ServiceRequestWrapper.getAllServiceType()
            );
    comboBox.getItems().addAll(deliveryOptions);
  }

  @FXML
  private void backToAdmin() {
    App.getPopup().getContent().clear();
    if (cred.equals("ADMIN")) {
      App.getPopup().getContent().add(App.getAdmin());
      App.getPopup().show(App.getPrimaryStage());
    }
    else {
      App.getHome().setDisable(false);
      App.getHome().setOpacity(1);
      adminBannerController.kill();
      App.getPrimaryStage().setScene(App.getHomeScene());
    }
  }

  public void setBanner(AdminBannerController adminBannerController) {
    this.adminBannerController = adminBannerController;
  }
}
