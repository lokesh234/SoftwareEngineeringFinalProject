package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Popup;

public class homeController {

  @FXML private Button test;
  private Parent parent;
  private Parent root;
  private Popup popup;

  public void setAttributes(Parent parent, Parent root, Popup popup) {
    this.parent = parent;
    this.popup = popup;
    this.root = root;
  }

  @FXML
  private void openLoginScene(ActionEvent e) {
    if (!popup.isShowing()) {
      root.setOpacity(.5);
      //      root.setDisable(true); TODO: set appropriate disable system
      popup.show(App.getPrimaryStage());
    } else popup.hide();
    //    App.getPrimaryStage().getScene().setRoot(parent);
  }
}
