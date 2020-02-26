package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.RequestScreenController;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyticsController {
    String type = "employee";
    ArrayList<String> types = new ArrayList<String>(Arrays.asList("SECUR",
            "MEDIC",
            "FLOWR",
            "DELIV",
            "ITRAN",
            "ETRAN",
            "CLOWN",
            "RELIG",
            "SANIT",
            "LANGE",
            "INTEC"));
    @FXML
    JFXComboBox comboBox;
    @FXML
    PieChart pieChart;


    private void update(){
        pieChart.setData(arrayToPie());
        pieChart.setTitle(type);
    }

    private ObservableList<PieChart.Data> arrayToPie(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        DatabaseWrapper.getDataAnalytics(temp, type);

        if(temp != null) {
            for(int i =0; i < types.size(); i++) {
                System.out.println(types.get(i) + " " + temp.get(i));
                pieChartData.add(new PieChart.Data(types.get(i), temp.get(i)));
            }
        }
        return pieChartData;
    }


    @FXML
    private void initialize(){
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    type = comboBox.getSelectionModel().getSelectedItem().toString();
                    update();
//                System.out.println("changes");
            }
        });

        ObservableList<String> deliveryOptions =
                FXCollections.observableArrayList(
                        "employee",
                        "service",
                        "serviceFinished"
                );
        comboBox.getItems().addAll(deliveryOptions);
    }



    @FXML
    public void backToAdmin(){
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        App.getPopup().show(App.getPrimaryStage());
    }
}
