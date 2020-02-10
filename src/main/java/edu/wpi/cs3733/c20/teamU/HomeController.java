package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Window;

public class HomeController {

  @FXML private Button test;
  private Parent toScene;
  private Parent root;
  private Popup popup;

  public void setAttributes(Parent toScene, Parent root, Popup popup) {
    this.toScene = toScene;
    this.popup = popup;
    this.root = root;
  }

//  public void setAttributes(Parent toScene, Parent root) {
//    this.toScene = toScene;
//    this.root = root;
//  }

  @FXML
  private void openLoginScene(ActionEvent e) {
    popup.getContent().add(toScene);
    if (!popup.isShowing()) {
      root.setOpacity(.5);
      root.setDisable(true);
      popup.show(App.getPrimaryStage());
    }
  }

  @FXML
  private void initialize() {

  }
}
