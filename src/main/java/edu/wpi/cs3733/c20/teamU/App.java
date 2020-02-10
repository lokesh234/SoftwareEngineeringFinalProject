package edu.wpi.cs3733.c20.teamU;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class App extends Application {

  private static Stage primaryStage;
  private static Popup popup;

  private static Parent home;
  private static Parent login;
  private static Parent start;
  private static Parent path;
  private static Parent admin;
//  private static StackPane

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public static Parent getHome() {return home; }
  public static Parent getLogin() {return login;}
  public static Parent getStart() { return start;}
  public static Parent getPath() { return path;}
  public static Parent getAdmin() { return admin;}

  @Override
  public void start(Stage primaryStage) throws Exception {

    App.primaryStage = primaryStage;
    App.popup = new Popup();

    try {
      FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/Tap to start.fxml"));
      FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/bigScreenv2.fxml"));
      FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
      FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/Admin_Service.fxml"));
      FXMLLoader pathfindLoader = new FXMLLoader((getClass().getResource("/pathfind.fxml")));

      home = (Parent) homeLoader.load();
      login = (Parent) loginLoader.load();
      start = (Parent) startLoader.load();
      path = (Parent) pathfindLoader.load();

      LoginController loginController = loginLoader.getController();
      HomeController homeController = homeLoader.getController();
      AdminController adminController = adminLoader.getController();

      adminController.setAttributes(admin, home, popup);
//      homeController.setAttributes(login, home, popup);
      loginController.setAttributes(admin, home, popup);

//      login.setDisable(true);
//      admin.setDisable(true);W

      Scene scene = new Scene(home);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
//
//  public void scene(String scene) {
//    switch (scene) {
//      case "login":
//
//    }
//  }

}
