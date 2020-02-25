package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class AdminBacklogController {
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

    @FXML
    TableView<Record> recordTable;
    @FXML
    TableColumn<Record, String> userName;
    @FXML
    TableColumn<Record, String> time;
    @FXML
    TableColumn<Record, String> type;
    @FXML
    TableColumn<Record, String> operation;
    @FXML
    TableColumn<Record, String> info;

// <children>
//                        <JFXButton fx:id="startNode" buttonType="RAISED" onAction="#save" text="Add" />
//                        <JFXButton fx:id="endNode" buttonType="RAISED" onAction="#selectEnd" text="Edit" />
//                        <JFXButton fx:id="saveButton" buttonType="RAISED" onAction="#selectStart" style="-fx-background-color: #db6e6e;" text="Remove" />
//                        <JFXButton fx:id="cancelButton" buttonType="RAISED" onAction="#back" text="Export" />
//                     </children>

   //         <TableView fx:id="employeeTable" onMouseClicked="#detectClick" BorderPane.alignment="CENTER">


    @FXML
    protected void update(){
        recordTable.setItems(arrayToOBList());
    }

    private ObservableList<Record> arrayToOBList() {
        ObservableList<Record> records = FXCollections.observableArrayList();
        ArrayList<Record> temp = new ArrayList<Record>();
//        DatabaseWrapper.getRecords(temp);

        if(temp != null){
            for (Record r: temp) {
                records.add(r);
            }
        }
        return records;
    }

    @FXML
    private void initialize(){
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));

        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        operation.setCellValueFactory(new PropertyValueFactory<>("operation"));

        info.setCellValueFactory(new PropertyValueFactory<>("info"));

        update();
    }
}
