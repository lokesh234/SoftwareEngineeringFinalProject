package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

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
    @FXML
    private LineChart lineChart;
    @FXML
    private BarChart barChart;


    private void update(){
        pieChart.setData(arrayToPie());
        pieChart.setTitle(type);
    }


    private void fillLineChart(){
        NumberAxis xAxis = new NumberAxis(1960, 2020, 10);
        xAxis.setLabel("Years");
        NumberAxis yAxis = new NumberAxis(0, 350, 50);
        yAxis.setLabel("Number");
    }


    private void fillBarChart(){
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data("Desktop", 178));
        dataSeries1.getData().add(new XYChart.Data("Phone"  , 65));
        dataSeries1.getData().add(new XYChart.Data("Tablet"  , 23));


        barChart.getData().add(dataSeries1);
    }

    private ObservableList<PieChart.Data> arrayToPie(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        types = DatabaseWrapper.getDataAnalytics(temp, type);

        if(temp != null) {
            for(int i =0; i < types.size(); i++) {
                System.out.println(types.get(i) + " " + temp.get(i));
            if (temp.get(i) > 0) {
              pieChartData.add(new PieChart.Data(types.get(i) + " (" + temp.get(i) + ")", temp.get(i)));
                    }
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


        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Services");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Numbers");

        barChart = new BarChart(xAxis, yAxis);
    }



    @FXML
    public void backToAdmin(){
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        App.getPopup().show(App.getPrimaryStage());
    }
}

//    //Defining X axis
//    NumberAxis xAxis = new NumberAxis(1960, 2020, 10);
//xAxis.setLabel("Years");
//
////Defining y axis
//        NumberAxis yAxis = new NumberAxis(0, 350, 50);
//        yAxis.setLabel("No.of schools");

//        Step 3: Creating the Line Chart
//        Create a line chart by instantiating the class named LineChart of the package javafx.scene.chart. To the constructor of this class, pass the objects representing the X and Y axis created in the previous step.
//
//        LineChart linechart = new LineChart(xAxis, yAxis);
//        Step 4: Preparing the Data
//        Instantiate the XYChart.Series class. Then add the data (a series of, x and y coordinates) to the Observable list of this class as follows −
//
//        XYChart.Series series = new XYChart.Series();
//        series.setName("No of schools in an year");
//
//        series.getData().add(new XYChart.Data(1970, 15));
//        series.getData().add(new XYChart.Data(1980, 30));
//        series.getData().add(new XYChart.Data(1990, 60));
//        series.getData().add(new XYChart.Data(2000, 120));
//        series.getData().add(new XYChart.Data(2013, 240));
//        series.getData().add(new XYChart.Data(2014, 300));
//        Step 5: Add Data to the Line Chart
//        Add the data series prepared in the previous step to the line chart as follows −
//
//        //Setting the data to Line chart
//        linechart.getData().add(series);
