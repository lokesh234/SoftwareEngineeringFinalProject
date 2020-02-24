package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
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
    JFXButton add_button, edit_button, remove_button, export_button;
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
    TableColumn<Account, Integer> number;


    public void setAttributes(EmployeeFormController e) {
        employeeFormController = e;
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
            employeeFormController.setAccountEdit();
        }
        else {
            edit_button.setDisable(true);
        }
    }
    @FXML
    public void add(){}
    @FXML
    public void edit(){}
    @FXML
    public void remove(){}
    @FXML
    public void export(){}

    @FXML
    public void back(){
            App.getPopup().getContent().clear();
                App.getPopup().getContent().add(App.getAdmin());
                App.getPopup().show(App.getPrimaryStage());

    }

    @FXML
    private void initialize(){

    }
}
