package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.RequestScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.RequestScreenController;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import org.controlsfx.control.Notifications;

public class AdminEmployeeController {
//
//            <TableView fx:id="employeeTable" onMouseClicked="#detectClick" BorderPane.alignment="CENTER">
//               <columns>
//                  <TableColumn fx:id="NodeID" prefWidth="75.0" text="NodeID" />
//                  <TableColumn prefWidth="75.0" text="X-coord" fx:id="xCoord" />
//                  <TableColumn fx:id="yCoord" prefWidth="75.0" text="Y-coord" />
//                  <TableColumn fx:id="floor" prefWidth="75.0" text="Floor" />
//                  <TableColumn fx:id="building" prefWidth="75.0" text="Building" />
//                  <TableColumn fx:id="nodeType" prefWidth="75.0" text="NodeType" />
//                  <TableColumn fx:id="longName" prefWidth="123.0" text="LongName" />
//                  <TableColumn fx:id="shortName" prefWidth="76.0" text="ShortName" />

    EmployeeFormController employeeFormController;
    @FXML
    JFXButton add_button, edit_button, remove_button, export_button, back_button;
    @FXML
    TableView<Account> employeeTable;
    @FXML
    javafx.scene.control.TableColumn<Account, String> userName;
    @FXML
    TableColumn<Account, String> firstName;
    @FXML
    TableColumn<Account, String> lastName;
    @FXML
    TableColumn<Account, String> cred;
    @FXML
    TableColumn<Account, String> number;


    public void setAttributes(EmployeeFormController e) {
        employeeFormController = e;
        e.setMaster(this);
    }
// <children>
//                        <JFXButton fx:id="startNode" buttonType="RAISED" onAction="#save" text="Add" />
//                        <JFXButton fx:id="endNode" buttonType="RAISED" onAction="#selectEnd" text="Edit" />
//                        <JFXButton fx:id="saveButton" buttonType="RAISED" onAction="#selectStart" style="-fx-background-color: #db6e6e;" text="Remove" />
//                        <JFXButton fx:id="cancelButton" buttonType="RAISED" onAction="#back" text="Export" />
//                     </children>

   //         <TableView fx:id="employeeTable" onMouseClicked="#detectClick" BorderPane.alignment="CENTER">

    @FXML
    private void detectClick() {
        if(employeeTable.getSelectionModel().getSelectedItem() != null) {
            App.setAccountEdit(employeeTable.getSelectionModel().getSelectedItem());
            edit_button.setDisable(false);
            remove_button.setDisable(false);
            employeeFormController.setAccountEdit();
        }
        else {
            remove_button.setDisable(true);
            edit_button.setDisable(true);
        }
    }

    @FXML
    public void add(){
        App.setAccountEdit(null);
        employeeFormController.setAccountEdit();
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getEmployeeForm());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    public void edit(){
        employeeFormController.setAccountEdit();
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getEmployeeForm());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    public void remove(){
        if (App.getAccountEdit() != null) {
            Notifications.create().text(App.getUser().getUserName() + " has been deleted!").show();
            DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), "EMPLOYEE", "Remove", App.getAccountEdit().getUserName());
            DatabaseWrapper.delLoginSR(App.getAccountEdit().getUserName());
            update();
        }
    }

    @FXML
    public void backToAdmin(){
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    protected void update(){
        employeeTable.setItems(arrayToOBList());
    }

    private ObservableList<Account> arrayToOBList() {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        ArrayList<Account> temp = new ArrayList<Account>();
        DatabaseWrapper.getAccounts(temp);

        if(temp != null){
            for (Account a: temp) {
                System.out.println(a.getCred() + a.getNumber());
                accounts.add(a);
            }
        }
        return accounts;
    }

    @FXML
    private void initialize(){
        remove_button.setDisable(true);
        edit_button.setDisable(true);
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        cred.setCellValueFactory(new PropertyValueFactory<>("cred"));

        number.setCellValueFactory(new PropertyValueFactory<>("number"));

        update();
    }
}
