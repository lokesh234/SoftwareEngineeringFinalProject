package edu.wpi.cs3733.c20.teamU;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
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
  private static FXMLLoader startLoader;
  private static FXMLLoader homeLoader;
  private static FXMLLoader loginLoader;
  private static FXMLLoader adminLoader;
  private static FXMLLoader pathfindLoader;
//  private static StackPane

  public static Stage getPrimaryStage() {
    return primaryStage;
  }
  public static FXMLLoader getStartLoader() { return startLoader;}
  public static FXMLLoader getHomeLoader() { return homeLoader;}
  public static FXMLLoader getLoginLoader() { return loginLoader;}
  public static FXMLLoader getAdminLoader() { return adminLoader;}
  public static FXMLLoader getPathfindLoader() { return pathfindLoader;}

  @Override
  public void start(Stage primaryStage) throws Exception {

    App.primaryStage = primaryStage;
    App.popup = new Popup();

    try {
      startLoader = new FXMLLoader(getClass().getResource("/Tap to start.fxml"));
      homeLoader = new FXMLLoader(getClass().getResource("/bigScreenv2.fxml"));
      loginLoader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
      adminLoader = new FXMLLoader(getClass().getResource("/Admin_Service.fxml")); // not integrated
      pathfindLoader = new FXMLLoader((getClass().getResource("/pathfind.fxml")));

      Parent home = (Parent) homeLoader.load();
      Parent login = (Parent) loginLoader.load();
      Parent start = (Parent) startLoader.load();

      LoginController loginController = loginLoader.getController();
      homeController homeController = homeLoader.getController();

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
