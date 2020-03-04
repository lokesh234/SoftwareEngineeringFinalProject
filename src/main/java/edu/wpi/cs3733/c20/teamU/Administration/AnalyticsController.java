package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.*;
import com.twilio.rest.api.v2010.account.usage.record.Today;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AnalyticsController {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd.MM.yyyy") ;
    String type = "employee";
    ArrayList<String> types = new ArrayList<String>();
//    @FXML
//    JFXComboBox comboBox;
    @FXML
    PieChart pieChart, dashPie;
//    @FXML private LineChart lineChart;
    @FXML
    private BarChart barChart, dash;
    @FXML
    private JFXRadioButton employeeRadio, requestRadio, requestFinishedRadio, yesterday, lastWeek, lastMonth, lastYear, allTime;
    @FXML
    private JFXComboBox typesComboBox;
    @FXML
    private JFXChipView typesChip;

//    @FXML private Tab lineTab;
    @FXML
    private JFXDatePicker fromDP, toDP, dateDP;
    private LocalDate startTime, endTime;
    @FXML
    private JFXButton generate;
    @FXML
    private CategoryAxis xA,x;
    @FXML
    private NumberAxis yA, y;
//    @FXML private NumberAxis lineYA, lineXA;
    private ToggleGroup typesGroup, quickSelection;
    @FXML
    Accordion accordion;
    @FXML
    Label finishedSR, currentSR, SRRate, employees, serviceTypes;
//    String timePeriod = false;


    @FXML
    private void update(){
        types = new ArrayList<String>();

        for (Object s: typesChip.getChips().toArray()) {
            types.add((String) s);
        }

        pieChart.setData(arrayToPie());
        fillBarChart();
//        fillLineChart();
    }


    private void fillLineChart(){
        System.out.println("FillLInes");
        getNumber("ADMIN");
//        lineChart.getData().clear();
        int lowB = 0;
        int highB = 0;
        String unit = "";

        if((endTime.getYear() - startTime.getYear() >= 5)){
            lowB = startTime.getYear();
            highB = endTime.getYear();
            unit = "year";
        }
        else if ((endTime.getMonthValue() - startTime.getMonthValue()) >= 3){
            lowB = startTime.getMonthValue();
            highB = endTime.getMonthValue();
            unit = "month";
        }
        else {
            lowB = startTime.getDayOfYear();
            highB = endTime.getDayOfYear();
            unit = "day";
        }

        NumberAxis xAxis = new NumberAxis(lowB, highB, (highB-lowB)/10);
        xAxis.setLabel("Time");
        NumberAxis yAxis = new NumberAxis(0, 10, 2);
        yAxis.setLabel("Number");


        for (String s: types) {
            System.out.println(s + "added Line");
            System.out.println(highB + ":" + lowB);
            XYChart.Series series = new XYChart.Series();
            series.setName(s);
            for(int i = lowB; i < highB; i++) {
                    if (unit.equals("year")) {
                        series.getData().add(new XYChart.Data(i, getNumber(type, startTime, startTime.plusYears(i))));
                    }
                    else if (unit.equals("month")){
                        series.getData().add(new XYChart.Data(i, getNumber(type, startTime, startTime.plusMonths(i))));
                    }
                    else {
                        series.getData().add(new XYChart.Data(i, getNumber(type, startTime, startTime.plusDays(i))));
                    }
            }
//            lineChart.getData().add(series);
        }
//        XYChart.Series series = new XYChart.Series();
//        series.setName("No of schools in an year");
//
//        series.getData().add(new XYChart.Data(1970, 15));
//        series.getData().add(new XYChart.Data(1980, 30));
//        series.getData().add(new XYChart.Data(1990, 60));
//        series.getData().add(new XYChart.Data(2000, 120));
//        series.getData().add(new XYChart.Data(2013, 240));
//        series.getData().add(new XYChart.Data(2014, 300));

    }


    private void fillBarChart() {
        barChart.getData().clear();
        XYChart.Series dataSeries1 = new XYChart.Series();

        dataSeries1.setName("Numbers");
        for (String s : types) {
                dataSeries1.getData().add(new XYChart.Data(s, getNumber(s)));
        }
       barChart.getData().add(dataSeries1);
    }

    private ObservableList<PieChart.Data> arrayToPie(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < types.size(); i++) {
            int number = getNumber(types.get(i));
            if (number > 0) {
                pieChartData.add(new PieChart.Data(types.get(i) + " (" + number + ")", number));
            }
        }
        return pieChartData;
    }


    @FXML
    private void initialize() {
        typesGroup = new ToggleGroup();
        employeeRadio.setToggleGroup(typesGroup);
        requestRadio.setToggleGroup(typesGroup);
        requestFinishedRadio.setToggleGroup(typesGroup);
        typesGroup.selectToggle(null);

        quickSelection = new ToggleGroup();
        yesterday.setToggleGroup(quickSelection);
        lastWeek.setToggleGroup(quickSelection);
        lastMonth.setToggleGroup(quickSelection);
        lastYear.setToggleGroup(quickSelection);
        allTime.setToggleGroup(quickSelection);
        quickSelection.selectToggle(null);

        ObservableList<String> serviceTypes =
                FXCollections.observableArrayList(
                        ServiceRequestWrapper.getAllServiceType()
                );
        typesComboBox.getItems().addAll(serviceTypes);

        generate.setDisable(true);

        fromDP.setDisable(true);
        toDP.setDisable(true);
//        lineTab.setDisable(true);
        accordion.setDisable(true);

        xA = new CategoryAxis();
        xA.setLabel("Services");

        yA = new NumberAxis();
        yA.setLabel("Numbers");

//        lineXA = new NumberAxis();
//        lineXA.setLabel("Services");
//
//        lineYA = new NumberAxis();
//        lineYA.setLabel("Numbers");
//        barChart = new BarChart(xAxis, yAxis);

        accordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
            @Override
            public void changed(ObservableValue<? extends TitledPane> observable, TitledPane oldValue, TitledPane newValue){
                if(newValue.getText().equals("By Date")){
//                    timePeriod = false;
                    quickSelection.selectToggle(null);
                    fromDP.getEditor().clear();
                    toDP.getEditor().clear();
//                    lineTab.setDisable(true);
                    checkValid();
                }
                else if (newValue.getText().equals("By Recent")){
//                    timePeriod = true;
                    dateDP.getEditor().clear();
                    fromDP.getEditor().clear();
                    toDP.getEditor().clear();
//                    lineTab.setDisable(false);
                    checkValid();
                }
                else if (newValue.getText().equals("By Range")){
//                    timePeriod = true;
                    dateDP.getEditor().clear();
                    quickSelection.selectToggle(null);
//                    lineTab.setDisable(false);
                    checkValid();
                }
            }
        });
    }

    private int getNumber(String type, LocalDate start, LocalDate end){
        if(requestRadio.isSelected()){
            return DatabaseWrapper.getServiceRequestAmountRange(type, start.format(DF), end.format(DF));
        }
        else{
            return DatabaseWrapper.getServiceFinishedAmountRange(type,start.format(DF),end.format(DF));
        }
    }

    private int getNumber(String type){
        startTime = LocalDate.now();
        endTime = LocalDate.now();
        if (employeeRadio.isSelected()){
            pieChart.setTitle("Employee");
            barChart.setTitle("Employee");
            return DatabaseWrapper.getEmployeeCount(type);
        }
        else {
            String selection = accordion.getExpandedPane().getText();
            if (selection.equals("By Range")) {
                startTime = fromDP.getValue();
                endTime = toDP.getValue();
            } else if (selection.equals("By Date")) {
                startTime = dateDP.getValue();
                endTime = dateDP.getValue();
            } else if (selection.equals("By Recent")) {
                if (allTime.isSelected()) {
                    startTime = LocalDate.of(1900, Month.JANUARY, 1);
                } else if (lastYear.isSelected()) {
                    startTime = startTime.minusYears(1);
                } else if (lastMonth.isSelected()) {
                    startTime = startTime.minusMonths(1);
                } else if (lastWeek.isSelected()) {
                    startTime = startTime.minusWeeks(1);
                } else if (yesterday.isSelected()) {
                    startTime = startTime.minusDays(1);
                }
            }
        }

        if(requestRadio.isSelected()){
            pieChart.setTitle("Services");
            barChart.setTitle("Services");
//            lineChart.setTitle("Services");
            return DatabaseWrapper.getServiceRequestAmountRange(type,startTime.format(DF),endTime.format(DF));
        }
        else{
            pieChart.setTitle("Services Finished");
            barChart.setTitle("Services Finished");
//            lineChart.setTitle("Services Finished");
            return DatabaseWrapper.getServiceFinishedAmountRange(type,startTime.format(DF),endTime.format(DF));
        }
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
        checkValid();
    }

    @FXML
    public void  addAllSRType(ActionEvent event){
        typesChip.getChips().clear();
        typesChip.getChips().addAll(ServiceRequestWrapper.getAllServiceType());
        checkValid();
    }

    @FXML
    public void  removeAllSRType(ActionEvent event){
        typesChip.getChips().clear();
        checkValid();
    }

    @FXML
    public void radioButton(ActionEvent event) {
        checkValid();
        if(employeeRadio.isSelected()){
            fromDP.setValue(LocalDate.now());
            toDP.setValue(LocalDate.now());
            fromDP.setDisable(true);
            toDP.setDisable(true);
//            lineTab.setDisable(true);
            accordion.setDisable(true);
        }
        else {
            fromDP.setDisable(false);
            toDP.setDisable(false);
//            lineTab.setDisable(false);
            accordion.setDisable(false);
        }
    }

    @FXML
    private void checkValid(){
        Boolean result1 = (employeeRadio.isSelected()||requestRadio.isSelected()||requestFinishedRadio.isSelected());
        Boolean result2 = (fromDP.getValue()!= null && toDP.getValue()!= null) || (dateDP.getValue()!= null)
                    || (allTime.isSelected() || lastWeek.isSelected() || lastMonth.isSelected() || lastYear.isSelected() || yesterday.isSelected());
        Boolean result3 = (!typesChip.getChips().isEmpty());

        if (result1 && result2 && result3){
            generate.setDisable(false);
        }
        else {
            generate.setDisable(true);
        }
    }

    protected void insight(){
        x = new CategoryAxis();
        x.setLabel("Services");
        y = new NumberAxis();
        y.setLabel("Numbers");
        int totalFinishedRequest = 0;
        int totalEmployee = 0;
        int totalCurrentRequest = 0;
        int services = 0;

        typesComboBox.getItems().clear();
        ObservableList<String> serviceTypesccb =
                FXCollections.observableArrayList(
                        ServiceRequestWrapper.getAllServiceType()
                );
        typesComboBox.getItems().addAll(serviceTypesccb);

        types = ServiceRequestWrapper.getAllServiceType();
        dash.setTitle("Total Finished Requests All Time");
        dash.getData().clear();
        XYChart.Series dataSeries1 = new XYChart.Series();

        dataSeries1.setName("Requests");
        String start = LocalDate.of(1900, Month.JANUARY, 1).format(DF);
        String end = LocalDate.now().format(DF);
        for (String s : types) {
            dataSeries1.getData().add(new XYChart.Data(s, DatabaseWrapper.getServiceFinishedAmountRange(s,start,end)));
            totalFinishedRequest += DatabaseWrapper.getServiceFinishedAmountRange(s,start,end);
            totalCurrentRequest += DatabaseWrapper.getServiceRequestAmountRange(s,start, end);
            services++;
        }
        dash.getData().add(dataSeries1);


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < types.size(); i++) {
            int number = DatabaseWrapper.getEmployeeCount(types.get(i));
            if (number > 0) {
                pieChartData.add(new PieChart.Data(types.get(i) + " (" + number + ")", number));
                totalEmployee += number;
            }
        }
        dashPie.setData(pieChartData);
        double rate = (double) totalFinishedRequest / (double)(totalCurrentRequest+totalFinishedRequest);
        rate = rate*100;
        currentSR.setText(String.valueOf(totalCurrentRequest));
        finishedSR.setText(String.valueOf(totalFinishedRequest));
        employees.setText(String.valueOf(totalEmployee));
        serviceTypes.setText(String.valueOf(services));
        SRRate.setText(String.valueOf(rate).substring(0,5) + "%");
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
