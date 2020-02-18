package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PathfindTextController {

    @FXML Label Directions;

    @FXML
    public void exitPopup(ActionEvent e){
        App.getTextDirectionsPop().getContent().remove(0);
        //Directions.setText("YEEHAW");
    }

    public void Populate(){
        for (int i = 0; i < App.getTextpath().size(); i++){
            System.out.println(Directions);
            Directions.setText(App.getTextpath().get(i));
        }
    }

    public void showDirections(){
        Populate();
    }
}
