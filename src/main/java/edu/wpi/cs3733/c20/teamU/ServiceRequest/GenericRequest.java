package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GenericRequest {
    @FXML private Label titleLabel;
    @FXML private VBox content;
    private String type = "";

    private LinkedHashMap<Object, String> components = new LinkedHashMap<>();

    protected void setType(String type) {
        this.type = type;
        content.getChildren().clear();
        components.clear();
        Scanner f = null;
        try {
            f = new Scanner(new File("CustomRequests/"+type+".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> lines = new ArrayList<>();
        while (f.hasNext()) {
            lines.add(f.nextLine());
        }
        f.close();
        titleLabel.setText("Request " + lines.get(0));
        for (int i = 1; i < lines.size(); i++) { //Add our components!
            String[] comp = lines.get(i).split(":");
            HBox juan = new HBox();
            Label l = new Label();
            l.setText(comp[0]);
            juan.getChildren().add(l);

            switch (comp[1]) {
                case "Text Field":
                    JFXTextField t = new JFXTextField();
                    juan.getChildren().add(t);
                    components.put(t, comp[1]);
                    break;
                case "Text Area":
                    JFXTextArea a = new JFXTextArea();
                    juan.getChildren().add(a);
                    components.put(a, comp[1]);
                    break;
                case "Combo Box":
                    JFXComboBox<String> c = new JFXComboBox<>();
                    c.getItems().addAll(comp[2].split(","));
                    c.setValue(comp[2].split(",")[0]);
                    juan.getChildren().add(c);
                    components.put(c, comp[1]);
                    break;
                case "Check Box":
                    JFXCheckBox ch = new JFXCheckBox();
                    juan.getChildren().add(ch);
                    components.put(ch, comp[1]);
                    break;
                case "Radio Buttons":
                    ToggleGroup g = new ToggleGroup();
                    String[] buttons = comp[2].split(",");
                    for (String s : buttons) {
                        RadioButton b = new RadioButton();
                        b.setText(s);
                        b.setToggleGroup(g);
                        if (s.equals(buttons[0])) b.setSelected(true);
                        juan.getChildren().add(b);
                        components.put(b, comp[1]);
                    }
                    break;
                case "Date Picker":
                    JFXDatePicker d = new JFXDatePicker();
                    juan.getChildren().add(d);
                    components.put(d, comp[1]);
                    break;
                case "Time Picker":
                    JFXTimePicker ti = new JFXTimePicker();
                    juan.getChildren().add(ti);
                    components.put(ti, comp[1]);
                    break;
                case "Color Picker":
                    JFXColorPicker co = new JFXColorPicker();
                    juan.getChildren().add(co);
                    components.put(co, comp[1]);
                    break;
                case "Label":
                    break; //Do nothing!
            }
            content.getChildren().add(juan);
        }
    }

    private boolean isFilled() {
        for (Map.Entry<Object, String> pair : components.entrySet()) {
            switch (pair.getValue()) {
                case "Text Field":
                    JFXTextField t = (JFXTextField) pair.getKey();
                    if (t.getText().equals("")) return false;
                    break;
                case "Text Area":
                    JFXTextArea a = (JFXTextArea) pair.getKey();
                    if (a.getText().equals("")) return false;
                    break;
                case "Combo Box":
                    JFXComboBox<String> c = (JFXComboBox<String>) pair.getKey();
                    if (c.getValue() == null) return false;
                    break;
                case "Check Box":
                    //No matter whether or not the check box is selected, it's still a valid answer!
                    break;
                case "Radio Buttons":
                    //There will always be a radio button selected!
                    break;
                case "Date Picker":
                    JFXDatePicker d = (JFXDatePicker) pair.getKey();
                    if (d.getValue() == null) return false;
                    break;
                case "Time Picker":
                    JFXTimePicker ti = (JFXTimePicker) pair.getKey();
                    if (ti.getValue() == null) return false;
                    break;
                case "Color Picker":
                    JFXColorPicker co = (JFXColorPicker) pair.getKey();
                    if (co.getValue() == null) return false;
                    break;
                case "Label":
                    break; //Do nothing!
            }
        }
        return true;
    }

    @FXML
    private void getVals() {
        if (isFilled()) {
            ArrayList<String> output = new ArrayList<>();
            for (Map.Entry<Object, String> pair : components.entrySet()) {
                switch (pair.getValue()) {
                    case "Text Field":
                        JFXTextField t = (JFXTextField) pair.getKey();
                        output.add(t.getText());
                        break;
                    case "Text Area":
                        JFXTextArea a = (JFXTextArea) pair.getKey();
                        output.add(a.getText());
                        break;
                    case "Combo Box":
                        JFXComboBox<String> c = (JFXComboBox<String>) pair.getKey();
                        output.add(c.getValue());
                        break;
                    case "Check Box":
                        JFXCheckBox ch = (JFXCheckBox) pair.getKey();
                        if (ch.isSelected()) output.add("true");
                        else output.add("false");
                        break;
                    case "Radio Buttons":
                        RadioButton r = (RadioButton) pair.getKey();
                        if (r.isSelected()) output.add(r.getText());
                        break;
                    case "Date Picker":
                        JFXDatePicker d = (JFXDatePicker) pair.getKey();
                        output.add(d.getValue().toString());
                        break;
                    case "Time Picker":
                        JFXTimePicker ti = (JFXTimePicker) pair.getKey();
                        output.add(ti.getValue().toString());
                        break;
                    case "Color Picker":
                        JFXColorPicker co = (JFXColorPicker) pair.getKey();
                        output.add(co.getValue().toString());
                        break;
                    case "Label":
                        break; //Do nothing!
                }
            }

            DatabaseWrapper.populateNewDatabase(this.type, output);
            goBack();
        }
        else {
            //Fail condition :(
        }
    }

    protected void clearAll(){
        content.getChildren().clear();
        components.clear();
    }

    @FXML
    private void goBack(){
        clearAll();
//        App.getHome().setOpacity(1);
//        App.getHome().setDisable(false);
        App.getRequestPop().getContent().clear();
        App.getRequestPop().getContent().add(App.getRequest());
        App.getRequestPop().show(App.getPrimaryStage());
    }

}
