package edu.wpi.cs3733.c20.teamU;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


import javafx.stage.Popup;
import javafx.stage.Stage;

public class App extends Application {

  private static Stage primaryStage;

  private static Pane home;
  private static Pane login;
  private static Pane start;
  private static Pane path;
  private static Pane admin;
  private static Pane security;

  private static Scene homeScene;
  private static Scene loginScene;
  private static Scene startScene;
  private static Scene pathScene;
  private static Scene adminScene;
  private static Scene securityScene;

  private static NodesDatabase graph = new NodesDatabase();
  private static int nodeSize = 5; //Radius in pixels of clickable node object

  private static Popup popup = new Popup();
  private static Popup securityPop = new Popup();

  public static Stage getPrimaryStage() {
    return primaryStage;
  }
  public static Pane getHome() {return home; }
  public static Pane getLogin() {return login;}
  public static Pane getStart() { return start;}
  public static Pane getPath() { return path;}
  public static Pane getAdmin() { return admin;}
  public static Pane getSecurity() { return security;}
  public static Scene getHomeScene() {return homeScene;}
  public static Scene getLoginScene() {return loginScene;}
  public static Scene getStartScene() { return startScene;}
  public static Scene getPathScene() {return pathScene;}
  public static Scene getAdminScene() {return adminScene;}
  public static Scene getSecurityScene() {return securityScene;}
  public static NodesDatabase getGraph() { return graph;}
  public static int getNodeSize(){ return nodeSize;}
  public static Popup getPopup() {
    return popup;
  }
  public static Popup getSecurityPop() {return securityPop;}

  @Override
  public void start(Stage primaryStage) throws Exception {

    App.primaryStage = primaryStage;

    try {
      FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/Tap_to_start.fxml"));
      FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/bigScreenv2.fxml"));
      FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
      FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/Admin_Service.fxml"));
      FXMLLoader pathfindLoader = new FXMLLoader((getClass().getResource("/pathfind.fxml")));
      FXMLLoader securityLoader = new FXMLLoader((getClass().getResource("/Security.fxml")));

      home = homeLoader.load();
      login = loginLoader.load();
      start = startLoader.load();
      path = pathfindLoader.load();
      security = securityLoader.load();
      admin = adminLoader.load();

      LoginController loginController = loginLoader.getController();
      HomeController homeController = homeLoader.getController();
      PathfindController pathfindController = pathfindLoader.getController();
      SecurityController securityController = securityLoader.getController();

//      homeController.setAttributes(login, home, popup, securityPop);
//      loginController.setAttributes(login, home, popup);
      pathfindController.setAttributes(path);

      //TODO: find a better way to do popups...
      popup.getContent().addAll();
      securityPop.getContent().addAll();

      homeScene = new Scene(home);
      pathScene = new Scene(path);
      startScene = new Scene(start);

      primaryStage.setScene(startScene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  protected static void addToPath(Node e) {//Adds a javaFX node to the pathfinding pane
    path.getChildren().add(e);
  }

  protected static void removeFromPath(Node e) { //Removes a javaFX node from the pathfinding pane
    path.getChildren().remove(e);
  }
}