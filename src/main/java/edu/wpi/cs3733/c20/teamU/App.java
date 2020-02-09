package edu.wpi.cs3733.c20.teamU;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class App extends Application {

  private static Stage primaryStage;
  private static Popup popup;
//  private static StackPane

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    App.primaryStage = primaryStage;
    App.popup = new Popup();

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
      FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/bigScreenv2.fxml"));
//    Pane login = FXMLLoader.load(getClass().getResource("/LoginUI.fxml"));

      Parent home = (Parent) loader2.load();
      Parent login = (Parent) loader.load();
      LoginController loginController = loader.getController();
      homeController homeController = loader2.getController();

      homeController.setAttributes(login, home, popup);
      loginController.setAttributes(login, home, popup);

      popup.getContent().addAll(login);

      Scene scene = new Scene(home);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }
}
