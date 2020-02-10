package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {

  @FXML private Button test;
  @FXML private Button navButton;

  @FXML
  private void openLoginScene(ActionEvent e) {
    App.getPopup().getContent().add(App.getLogin());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getPopup().show(App.getPrimaryStage());

  }

  @FXML
  private void openNavScene(ActionEvent e) throws IOException {
    App.getPrimaryStage().setScene(App.getPathScene());
  }

  @FXML
  private void openHelpScene(ActionEvent e){
    System.out.println("hello");
//    if(!secuirtyPop.isShowing()){
//      root.setOpacity(.5);
//      root.setDisable(true);
//      secuirtyPop.show(App.getPrimaryStage());
//    }
//    else popup.hide();
  }
  @FXML
  private void initialize() {

  }
}
