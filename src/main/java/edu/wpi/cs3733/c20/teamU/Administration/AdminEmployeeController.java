package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.ServiceRequest.RequestScreenController;
import javafx.fxml.FXML;

public class AdminEmployeeController {

    EmployeeFormController employeeFormController;

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
    public void detectClick(){}
    @FXML
    public void save(){}
    @FXML
    public void selectEnd(){}
    @FXML
    public void selectStart(){}
    @FXML
    public void back(){}
    @FXML
    private void initialize(){

    }
}
