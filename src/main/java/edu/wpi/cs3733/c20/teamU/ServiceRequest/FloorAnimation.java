package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class FloorAnimation {
  @FXML JFXButton floor1;
  @FXML JFXButton floor2;
  @FXML JFXButton floor3;
  @FXML JFXButton floor4;
  @FXML JFXButton floor5;
  @FXML VBox floorBox;
  private ArrayList<String> floorList = new ArrayList<String>();
  private int floorLevel = 0;


  public FloorAnimation(VBox floorBox, int floorLevel) {
    this.floorLevel = floorLevel;
    this.floorBox = floorBox;
    for (int i = 0; i < floorBox.getChildren().size(); i++) {
      if (i == 0 || i == floorBox.getChildren().size() - 1) continue;
      floorList.add(((JFXButton) floorBox.getChildren().get(i)).getText());
    }
  }

//  public void rename(VBox floorBox, int floorLevel) {
//    shuffleFloorList(floorLevel);
//    for(int i = 0; i < floorBox.getChildren().size();i ++) {
//      if(i == 0 || i == floorBox.getChildren().size() - 1) continue;
//      floorBox.getChildren().set(i, )
//    }
//  }

  private void shuffleFloorList(int floorLevel) {
    while(!floorList.get(2).equals(Integer.toString(floorLevel))) {
      String first = floorList.get(0);
      floorList.remove(0);
      floorList.add(first);
    }
  }
}
