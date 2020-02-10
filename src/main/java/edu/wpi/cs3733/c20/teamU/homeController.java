package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Popup;

import javax.swing.*;
import java.io.IOException;

public class homeController {

  @FXML private Button test;
  @FXML private Button navButton;
  private Parent parent;
  private Parent root;
  private Popup popup;
  private Popup secuirtyPop;

  public void setAttributes(Parent parent, Parent root, Popup popup, Popup secuirtyPop) {
    this.parent = parent;
    this.popup = popup;
    this.secuirtyPop = secuirtyPop;
    this.root = root;
  }

  @FXML
  private void openLoginScene(ActionEvent e) {
    if (!popup.isShowing()) {
      root.setOpacity(.5);
      root.setDisable(true);
      popup.show(App.getPrimaryStage());
    } else popup.hide();
    //    App.getPrimaryStage().getScene().setRoot(parent);
  }

  @FXML
  private void openNavScene(ActionEvent e) throws IOException {
    App.getPrimaryStage().setScene(App.getPathScene());
  }

  @FXML
  private void initialize() {

  }

  @FXML
  private void openHelpScene(ActionEvent e){
    System.out.println("hello");
    if(!secuirtyPop.isShowing()){
      root.setOpacity(.5);
      root.setDisable(true);
      secuirtyPop.show(App.getPrimaryStage());
    }
    else popup.hide();
  }
}
