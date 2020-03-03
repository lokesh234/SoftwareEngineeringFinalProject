package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

public class AddCustomRequestController {
    @FXML private JFXTextField formName, shortName, fieldName;
    @FXML private JFXComboBox<String> fieldType;
    @FXML private JFXChipView<String> contents;
    @FXML private TableView<Component> components;
    @FXML private TableColumn<Component, String> nameCol, typeCol;
    @FXML private TableColumn<Component, String> contCol;
    @FXML private JFXButton addButton, removeButton, submitButton, backButton;

    private class Component {
        private String name, type;
        private ArrayList<String> components = new ArrayList<>();
        public String getName() { return name;}
        public String getType() { return type;}
        public String getComponents() { return components.toString();}
        public ArrayList<String> getComponents2() { return components;}
        public Component(String n, String t, Collection<String> c) {
            name = n;
            type = t;
            components.addAll(c);
        }
    }

    @FXML
    private void initialize() {
        fieldType.getItems().addAll("Text Field", "Text Area", "Combo Box", "Check Box", "Radio Buttons", "Date Picker", "Time Picker", "Color Picker");
        fieldType.setValue("Text Field");
        typeCol.setSortable(false);
        nameCol.setSortable(false);
        contCol.setSortable(false);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("components"));
    }

    @FXML
    private void addComponent() {
        if (fieldName.getText().length() == 0 || !isValidComp(fieldName.getText(), fieldType.getValue(), contents.getChips())) {
            //Fail condition :(
        }
        else {
            components.getItems().add(new Component(fieldName.getText(), fieldType.getValue(), contents.getChips()));
        }
    }

    @FXML
    private void delComponent() {
        if (components.getSelectionModel().getSelectedItem() != null) {
            components.getItems().remove(components.getSelectionModel().getSelectedItem());
        }
    }

    private boolean isValidComp(String name, String type, ObservableList<String> contents) {
        if ((type.equals("Combo Box") || type.equals("Radio Buttons")) && contents.size() == 0) return false;
        return true;
    }

    @FXML
    private void back() {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        formName.clear();
        shortName.clear();
        fieldName.clear();
        contents.getChips().clear();
        fieldType.setValue("Text Field");
    }

    @FXML
    private void validate() {

    }
}
