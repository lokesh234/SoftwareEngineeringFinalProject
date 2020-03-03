package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.c20.teamU.App;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GenericRequest {
    @FXML private Label titleLabel;
    @FXML private VBox content;

    private HashMap<String, Object> components = new HashMap<>();

    protected void setType(String type) {
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
        System.out.println(lines);
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
                    components.put(comp[1], t);
                    break;
                case "Text Area":
                    JFXTextArea a = new JFXTextArea();
                    juan.getChildren().add(a);
                    components.put(comp[1], a);
                    break;
                case "Combo Box":
                    JFXComboBox<String> c = new JFXComboBox<>();
                    c.getItems().addAll(comp[2].split(","));
                    c.setValue(comp[2].split(",")[0]);
                    juan.getChildren().add(c);
                    components.put(comp[1], c);
                    break;
                case "Check Box":
                    JFXCheckBox ch = new JFXCheckBox();
                    juan.getChildren().add(ch);
                    components.put(comp[1], ch);
                    break;
                case "Radio Buttons":
                    ToggleGroup g = new ToggleGroup();
                    String[] buttons = comp[2].split(",");
                    for (String s : buttons) {
                        RadioButton b = new RadioButton();
                        b.setText(s);
                        b.setToggleGroup(g);
                        juan.getChildren().add(b);
                        components.put(comp[1], b);
                    }
                    break;
                case "Date Picker":
                    JFXDatePicker d = new JFXDatePicker();
                    juan.getChildren().add(d);
                    components.put(comp[1], d);
                    break;
                case "Time Picker":
                    JFXTimePicker ti = new JFXTimePicker();
                    juan.getChildren().add(ti);
                    components.put(comp[1], ti);
                    break;
                case "Color Picker":
                    JFXColorPicker co = new JFXColorPicker();
                    juan.getChildren().add(co);
                    components.put(comp[1], co);
                    break;
                case "Label":
                    break; //Do nothing!
            }
            content.getChildren().add(juan);
        }
    }

    protected void clearAll() {
        content.getChildren().clear();
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

    @FXML
    private void getSubmission() {

    }

}
