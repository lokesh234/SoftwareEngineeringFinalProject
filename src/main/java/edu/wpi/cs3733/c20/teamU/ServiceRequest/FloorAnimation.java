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


  public FloorAnimation() {
//    this.floorBox = floorBox;
//    for (int i = 0; i < floorBox.getChildren().size(); i++) {
//      if (i == 0 || i == floorBox.getChildren().size() - 1) continue;
//      floorList.add(((JFXButton) floorBox.getChildren().get(i)).getText());
//    }
//    for (int i = 0; i < floorBox.getChildren().size(); i++) {
//      if (i == 0 || i == floorBox.getChildren().size() - 1) continue;
//      floorButtons.add((JFXButton) floorBox.getChildren().get(i));
//    }
  }

  public void shuffleFloorList(int floorLevel, VBox floorBox) {
    while(!((JFXButton)floorBox.getChildren().get(3)).getText().equals(Integer.toString(floorLevel))) {
      JFXButton first = (JFXButton) floorBox.getChildren().get(1);
      floorBox.getChildren().remove(1);
      floorBox.getChildren().add(floorBox.getChildren().size() - 1, first);
    }

  }
}
