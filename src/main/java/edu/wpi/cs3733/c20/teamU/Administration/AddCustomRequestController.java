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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public class Component {
        private String name, type;
        private ArrayList<String> components = new ArrayList<>();
        public String getName() { return name;}
        public String getType() { return type;}
        public String getComponents() { return components.toString();}
        public ArrayList<String> getComponents2() { return components;}
        private String getComps() {
            String s = "";
            for (int i = 0; i < components.size()-1; i++) {
                s += components.get(i);
                s += ",";
            }
            s += components.get(components.size()-1);
            return s;
        }
        public Component(String n, String t, Collection<String> c) {
            name = n;
            type = t;
            components.addAll(c);
        }
        public String toString() {
            if (components.size() > 0) return name+":"+type+":"+getComps();
            else return name+":"+type;
        }
    }


    @FXML
    private void initialize() {
        fieldType.getItems().addAll("Text Field", "Text Area", "Combo Box", "Check Box", "Radio Buttons", "Date Picker", "Time Picker", "Color Picker", "Label");
        fieldType.setValue("Text Field");
        typeCol.setSortable(false);
        nameCol.setSortable(false);
        contCol.setSortable(false);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("components"));
        //contents.getChips().add("Hello");

        fileSetup();
    }

    private void fileSetup() { //Checks if the CustomRequests folder exists, and creates it if it doesn't
        File dir = new File("CustomRequests");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private void saveFile(String output, String dest) {
        File file = new File("CustomRequests/"+dest+".txt");
        try {
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter("CustomRequests/"+dest+".txt");
            fw.write(output);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addComponent() {
        if (fieldName.getText().length() == 0 || !isValidComp(fieldName.getText(), fieldType.getValue(), contents.getChips())) {
            //Fail condition :(
        }
        else {
            components.getItems().add(new Component(fieldName.getText(), fieldType.getValue(), contents.getChips()));
            fieldName.setText("");
            contents.getChips().clear();
        }
    }

    @FXML
    private void delComponent() {
        if (components.getSelectionModel().getSelectedItem() != null) {
            components.getItems().remove(components.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void addRequest() {
        if (isValidRequest(shortName.getText(), formName.getText(), components.getItems())) {
            //Do database things!
            String output = formName.getText()+"\n";
            for (Component c : components.getItems()) {
                output += c.toString() + "\n";
            }
            System.out.println(output);
            saveFile(output, shortName.getText());
            App.getRequestController().updateButtons();
            back();
        }
    }

    private boolean isValidComp(String name, String type, Collection<String> contents) {
        if ((type.equals("Combo Box") || type.equals("Radio Buttons")) && contents.size() == 0) return false;
        if (name.contains(":") || type.contains(":")) return false;
        for (String s : contents) {
            if (s.contains(",")) return false;
        }
        return true;
    }

    private boolean isValidComp(Component c) {
        return isValidComp(c.name, c.type, c.components);
    }

    private boolean isValidRequest(String sname, String lname, ObservableList<Component> comps) {
        if (sname.length() == 0 || lname.length() == 0 || comps.size() == 0) return false;
        if (lname.contains("\n")) return false;
        if (sname.contains("'") || sname.contains("\"") || sname.contains(",") || sname.contains(";") || sname.contains("(") || sname.contains(")") || sname.contains(".")) return false;
        //if (databaseWrapper.hasRequestType(sname)) return false;
        for (Component c : comps) {
            if (!isValidComp(c)) return false;
        }
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
        components.getItems().clear();
        fieldType.setValue("Text Field");
    }

    @FXML
    private void validate() {

    }
}
