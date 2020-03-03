package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.twilio.rest.api.v2010.account.usage.record.Today;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AnalyticsController {
    String type = "employee";
    ArrayList<String> types = new ArrayList<String>();
//    @FXML
//    JFXComboBox comboBox;
    @FXML
    PieChart pieChart;
//    @FXML
//    private LineChart lineChart;
    @FXML
    private BarChart barChart;
    @FXML
    private JFXComboBox typesComboBox, majorTypesComboBox;
    @FXML
    private JFXChipView typesChip;
    @FXML
    private JFXDatePicker fromDP, toDP;
    private LocalDate startTime, endTime;
    @FXML
    private JFXButton generate;
    @FXML
    private CategoryAxis xA;
    @FXML
    private NumberAxis yA;


    @FXML
    private void update(){
        types = new ArrayList<String>();

        for (Object s: typesChip.getChips().toArray()) {
            types.add((String) s);
        }

        pieChart.setData(arrayToPie());
        pieChart.setTitle(type);
        fillBarChart();
    }


//    private void fillLineChart(){
//        NumberAxis xAxis = new NumberAxis(1960, 2020, 10);
//        xAxis.setLabel("Time");
//        NumberAxis yAxis = new NumberAxis(0, 350, 50);
//        yAxis.setLabel("Number");
//
//        lineChart = new LineChart(xAxis,yAxis);
//    }


    private void fillBarChart() {
        barChart.getData().clear();
        XYChart.Series dataSeries1 = new XYChart.Series();

        dataSeries1.setName("Numbers");
        for (String s : types) {
            if (majorTypesComboBox.getSelectionModel().getSelectedItem().equals("employee")) {
                dataSeries1.getData().add(new XYChart.Data(s, DatabaseWrapper.getEmployeeCount(s)));
                System.out.println("Bar:" + s + ":" + DatabaseWrapper.getEmployeeCount(s));
            } else {
                dataSeries1.getData().add(new XYChart.Data(s, ServiceDatabase.getServiceRequestAmountRange(s, startTime.toString(), endTime.toString())));
            }
        }
        barChart.getData().add(dataSeries1);
    }

    private ObservableList<PieChart.Data> arrayToPie(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if(majorTypesComboBox.getSelectionModel().getSelectedItem().equals("employee")){
            for (int i = 0; i < types.size(); i++) {
                System.out.println(types.get(i) + DatabaseWrapper.getEmployeeCount(types.get(i)));
                if (DatabaseWrapper.getEmployeeCount(types.get(i)) > 0) {
                    pieChartData.add(new PieChart.Data(types.get(i) + " (" + DatabaseWrapper.getEmployeeCount(types.get(i)) + ")", DatabaseWrapper.getEmployeeCount(types.get(i))));
                }
            }
        }
        else {
            for (int i = 0; i < types.size(); i++) {
                int number = ServiceDatabase.getServiceRequestAmountRange(types.get(i), startTime.toString(), endTime.toString());
                pieChartData.add(new PieChart.Data(types.get(i) + " (" + number + ")", number));
            }
        }
        return pieChartData;
    }


    @FXML
    private void initialize(){
        ObservableList<String> serviceTypes =
                FXCollections.observableArrayList(
                        ServiceRequestWrapper.getAllServiceType()
                );
        typesComboBox.getItems().addAll(serviceTypes);


        ObservableList<String> majorTypes =
                FXCollections.observableArrayList(
                        "employee",
                        "service",
                        "serviceFinished"
                );
        majorTypesComboBox.getItems().addAll(majorTypes);
//
//        ObservableList<String> deliveryOptions =
//                FXCollections.observableArrayList(
//                        "employee",
//                        "service",
//                        "serviceFinished"
//                );
//        comboBox.getItems().addAll(deliveryOptions);


        generate.setDisable(true);
        BooleanBinding blockCheckBox =
                (typesChip.converterProperty().isNull())
                .or(majorTypesComboBox.getSelectionModel().selectedItemProperty().isNull())
                .or(fromDP.getEditor().textProperty().isEmpty())
                .or(toDP.getEditor().textProperty().isEmpty());
        generate.disableProperty().bind(blockCheckBox);

        fromDP.setDisable(true);
        toDP.setDisable(true);
        majorTypesComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(majorTypesComboBox.getSelectionModel().getSelectedItem().equals("employee")){
                    fromDP.setValue(LocalDate.now());
                    toDP.setValue(LocalDate.now());
                    fromDP.setDisable(true);
                    toDP.setDisable(true);
                }
                else {
                    fromDP.setDisable(false);
                    toDP.setDisable(false);
                }
            }
        });


        xA = new CategoryAxis();
        xA.setLabel("Services");

        yA = new NumberAxis();
        yA.setLabel("Numbers");

//        barChart = new BarChart(xAxis, yAxis);
    }



    @FXML
    public void backToAdmin(){
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        App.getPopup().show(App.getPrimaryStage());
    }

    @FXML
    public void addSRType(ActionEvent event) {
        if (!typesComboBox.getSelectionModel().isEmpty() &&
        !typesChip.getChips().contains(typesComboBox.getSelectionModel().getSelectedItem())) {
//            flowerCombo.setStyle("-fx-border-color: #FFEEC9");

            typesChip.getChips().add(typesComboBox.getSelectionModel().getSelectedItem());
            typesComboBox.getSelectionModel().clearSelection();
        } else {
            typesComboBox.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    public void  addAllSRType(ActionEvent event){
        typesChip.getChips().clear();
        typesChip.getChips().addAll(ServiceRequestWrapper.getAllServiceType());
    }

    @FXML
    public void  removeAllSRType(ActionEvent event){
        typesChip.getChips().clear();
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
