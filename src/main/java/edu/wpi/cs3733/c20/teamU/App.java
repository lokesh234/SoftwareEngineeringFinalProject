package edu.wpi.cs3733.c20.teamU;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

//    FXMLLoader loader = FXMLLoader.load(getClass().getResource("/LoginUI.fxml"));
    Parent root = FXMLLoader.load(getClass().getResource("/LoginUI.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
