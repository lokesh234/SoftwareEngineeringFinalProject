package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Navigation.NavigationWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class AdminScaleController {

    @FXML private JFXTextField pixMet, pixFoo;
    @FXML private JFXComboBox options;

    @FXML
    private void closePop(){
        App.getChoosePathPop().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
    }

    @FXML
    private void set() {
        double pF = 0;
        double pM = 0;
        try {
            pF = Double.parseDouble(pixFoo.getText());
            pM = Double.parseDouble(pixMet.getText());
        }
        catch (Exception e) {
            pixMet.setStyle("-fx-border-color: red");
            pixFoo.setStyle("-fx-border-color: red");
            return;
        }
        App.setPixFoo(pF);
        App.setPixMet(pM);
        App.setUseFeet(options.getValue().toString().equals("feet"));
        closePop();
    }

    public void update() {
        initialize();
    }

    @FXML
    private void initialize() {
        pixMet.setText(Double.toString(App.getPixMet()));
        pixFoo.setText(Double.toString(App.getPixFoo()));
        pixFoo.setStyle("-fx-border-color:  #FFEEC9");
        pixMet.setStyle("-fx-border-color:  #FFEEC9");
        options.getItems().clear();
        options.getItems().addAll("feet", "meters");
        if(App.getUnit().equals("feet")){
            options.setValue("feet");
        }
        else{
            options.setValue("meters");
        }

    }
}
