package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.ServiceDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class SanRequestController {

    @FXML private JFXTextArea service;
    @FXML private JFXTextField location;
    @FXML private JFXTextField nature;
    @FXML private JFXTextArea additional;
    @FXML private JFXButton submit;
}