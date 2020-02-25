package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class PathfindTextController {

    @FXML private AnchorPane DirectionsPane;
    private ArrayList<Label> direction = new ArrayList<>();
//    private TextPathBuilder tpb = new TextPathBuilder(10.0, 11.5, 3.5);

    @FXML
    public void exitPopup(ActionEvent e){
        wipeDirections();
        App.getTextDirectionsPop().getContent().remove(0);
//        Directions.setText("YEEHAW");
    }

    public void Populate(){
        String directions = "";
        for (int i = 0; i < App.getTextpath().size(); i++){
//            System.out.println(this.Directions);
            directions = directions + "\n" + App.getTextpath().get(i);
        }
        Label l = new Label();
        l.setText(directions);
        direction.add(l);
        addToPath(l);
    }

    private void wipeDirections() {
        for (Label l : direction) {
            removeFromPath(l);
        }
        direction.clear();
    }

    private void addToPath(Node e) {
        DirectionsPane.getChildren().add(e);
    }

    private void removeFromPath(Node e) {
        DirectionsPane.getChildren().remove(e);
    }

    @FXML
    private void initialize() {}
}
