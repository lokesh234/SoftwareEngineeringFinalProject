package edu.wpi.cs3733.c20.teamU;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import javax.print.DocFlavor;
import java.io.IOException;

public class HomeController {

  @FXML private Button test;
  @FXML private Button navButton;
  SecurityController securityController;

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
    App.getSecurityPop().getContent().add(App.getSecurity());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getSecurityPop().show(App.getPrimaryStage());
  }

  @FXML
  private void initialize() {
//    Duration timeout = new Duration(6000);
//    if(timeout.toSeconds() > 5){
//      App.getPrimaryStage().setScene(App.getStartScene());
//    }
  }


  /*
  TimerTask task = new TimerTask() {
    @Override
    public void run() {
      openstartScene();
    }
  };

  @FXML
  private void openstartScene(){
    App.getPrimaryStage().setScene(App.getStartScene());
  }

  */

  @FXML
  private void openRequestScene(ActionEvent e){
    App.getRequestPop().getContent().add(App.getRequest());
    App.getHome().setOpacity(.5);
    App.getHome().setDisable(true);
    App.getRequestPop().show(App.getPrimaryStage());
  }


}
