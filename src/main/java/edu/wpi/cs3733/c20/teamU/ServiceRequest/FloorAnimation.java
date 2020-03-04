package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class FloorAnimation {

//  private ArrayList<String> floorList = new ArrayList<>();
//  private ArrayList<JFXButton> floorButtons = new ArrayList<>();


  private void floorResize(JFXButton button, int whichButton) {
    switch (whichButton) {
      case 1:
        button.resize(76, 76);
        break;
      case 2:
        button.resize(90, 90);
        break;
      case 3:
        button.resize(100, 100);
        break;
      case 4:
        button.resize(90, 90);
        break;
      case 5:
        button.resize(76, 76);
        break;
    }
  }

  public void shuffleFloorList(int floorLevel, VBox floorBox) {

    while(!((JFXButton)floorBox.getChildren().get(3)).getText().equals(Integer.toString(floorLevel))) {
      JFXButton first = (JFXButton) floorBox.getChildren().get(1);
      floorBox.getChildren().remove(1);
      floorBox.getChildren().add(floorBox.getChildren().size() - 1, first);
    }
    resizer(floorBox);

  }
  private void resizer(VBox floorBox) {
    floorBox.getChildren().get(1).setStyle("-fx-pref-height: 76");
    floorBox.getChildren().get(1).setStyle("-fx-pref-width: 76");

    floorBox.getChildren().get(2).setStyle("-fx-pref-height: 90");
    floorBox.getChildren().get(2).setStyle("-fx-pref-width: 90");

    floorBox.getChildren().get(3).setStyle("-fx-pref-height: 100");
    floorBox.getChildren().get(3).setStyle("-fx-pref-width: 100");

    floorBox.getChildren().get(4).setStyle("-fx-pref-height: 90");
    floorBox.getChildren().get(4).setStyle("-fx-pref-width: 90");

    floorBox.getChildren().get(5).setStyle("-fx-pref-height: 76");
    floorBox.getChildren().get(5).setStyle("-fx-pref-width: 76");
  }
}
