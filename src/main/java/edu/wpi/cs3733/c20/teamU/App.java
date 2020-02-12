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
  private static Pane request;
  private static Pane medicine;
  private static Pane export;
  private static Pane edit;
  private static Pane adminRequest;
  private static Pane adminNode;


  private static Scene homeScene;
  private static Scene loginScene;
  private static Scene startScene;
  private static Scene pathScene;
  private static Scene adminScene;
  private static Scene securityScene;
  private static Scene requestScene;
  private static Scene medicineScene;
  private static Scene editScene;
  private static Scene exportScene;
  private static Scene adminNodeScene;

  private static LoginController loginController;
  private static HomeController homeController;
  private static PathfindController pathfindController;
  private static SecurityController securityController;
  private static RequestController requestController;
  private static MedicineController medicineController;
  private static AdminController adminController;
  private static AdminRequestController adminRequestController;
  private static NodeController nodeController;
  private static editModeController editController;

  private static edu.wpi.cs3733.c20.teamU.Node nodeEdit;
  private static NodesDatabase graph = new NodesDatabase();
  private static int nodeSize = 5; //Radius in pixels of clickable node object

  private static Popup popup = new Popup();
  private static Popup securityPop = new Popup();
  private static Popup requestPop = new Popup();
  private static Popup medicinePop = new Popup();


  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public static Pane getHome() {
    return home;
  }

  public static Pane getLogin() {
    return login;
  }

  public static Pane getStart() {
    return start;
  }

  public static Pane getPath() {
    return path;
  }

  public static Pane getAdmin() {
    return admin;
  }

  public static Pane getSecurity() {
    return security;
  }

  public static Pane getRequest() {
    return request;
  }

  public static Pane getMedicine() {
    return medicine;
  }

  public static Pane getAdminRequest() {
      return adminRequest;
  }

  public static Pane getExport() {
      return export;
  }

  public static Pane getEdit() {
      return edit;
  }

    public static Pane getAdminNode() { return adminNode;}


    public static Scene getHomeScene() {
    return homeScene;
  }

  public static Scene getLoginScene() {
    return loginScene;
  }

  public static Scene getStartScene() {
    return startScene;
  }

  public static Scene getPathScene() {
    return pathScene;
  }

  public static Scene getAdminScene() {
    return adminScene;
  }

  public static Scene getSecurityScene() {
    return securityScene;
  }

  public static Scene getRequestScene() {
    return requestScene;
  }

  public static Scene getMedicineScene() {
    return medicineScene;
  }

  public static Scene getEditScene() {
    return editScene;
  }

  public static Scene getExportScene() {
    return exportScene;
  }
  public static Scene getAdminNodeScene() {return adminNodeScene;}
  public static LoginController getLoginController() { return loginController;}
  public static HomeController getHomeController() { return homeController;}
  public static PathfindController getPathfindController() { return pathfindController;}
  public static SecurityController getSecurityController() { return securityController;}
  public static RequestController getRequestController() {return requestController;}
  public static MedicineController getMedicineController() { return medicineController;}
  public static AdminController getAdminController() { return adminController;}
  public static AdminRequestController getAdminRequestController() { return adminRequestController;}

  public static edu.wpi.cs3733.c20.teamU.Node getNodeEdit() {
    return nodeEdit;
  }
  public static void setNodeEdit(edu.wpi.cs3733.c20.teamU.Node userNode) {
    nodeEdit = userNode;
  }
    public static NodesDatabase getGraph() {
    return graph;
  }

  public static int getNodeSize() {
    return nodeSize;
  }

  public static Popup getPopup() {
    return popup;
  }

  public static Popup getSecurityPop() {
    return securityPop;
  }

  public static Popup getRequestPop() {
    return requestPop;
  }

  public static Popup getMedicinePop() {
    return medicinePop;
  }

//  public static void reset() {
//    nodeController.refreshTable();
//  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    App.primaryStage = primaryStage;
    try {
      FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/Tap_to_start.fxml"));
      FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/bigScreenv2.fxml"));
      FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/LoginUI.fxml"));
      FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/AllAdmin.fxml"));
      FXMLLoader adminNodeLoader = new FXMLLoader(getClass().getResource("/Admin_Node.fxml"));
      FXMLLoader pathfindLoader = new FXMLLoader(getClass().getResource("/pathfind.fxml"));
      FXMLLoader securityLoader = new FXMLLoader(getClass().getResource("/Security.fxml"));
      FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/AllRequests.fxml"));
      FXMLLoader medicineLoader = new FXMLLoader(getClass().getResource("/MedicineRequestForm.fxml"));
      FXMLLoader exportLoader = new FXMLLoader(getClass().getResource("/Export_CSV.fxml"));
      FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/Edit_Node.fxml"));
      FXMLLoader adminRequestLoader = new FXMLLoader((getClass().getResource("/Admin_Service.fxml")));

      home = homeLoader.load();
      login = loginLoader.load();
      start = startLoader.load();
      path = pathfindLoader.load();
      security = securityLoader.load();
      admin = adminLoader.load();
      request = requestLoader.load();
      medicine = medicineLoader.load();
      export = exportLoader.load();
      edit = editLoader.load();
      adminRequest = adminRequestLoader.load();
      adminNode = adminNodeLoader.load();

      loginController = loginLoader.getController();
      homeController = homeLoader.getController();
      pathfindController = pathfindLoader.getController();
      securityController = securityLoader.getController();
      requestController = requestLoader.getController();
      medicineController = medicineLoader.getController();
      adminController = adminLoader.getController();
      adminRequestController = adminRequestLoader.getController();
      nodeController = adminNodeLoader.getController();
      editController = editLoader.getController();

      pathfindController.setAttributes(path);
      nodeController.setAttributes(editController);
      editController.setAttributes(nodeController);

      //TODO: find a better way to do popups...
      popup.getContent().addAll();
      securityPop.getContent().addAll();
      requestPop.getContent().addAll();
      medicinePop.getContent().addAll();

      homeScene = new Scene(home);
      pathScene = new Scene(path);
      startScene = new Scene(start);
      adminScene = new Scene(admin);
      adminNodeScene = new Scene(adminNode);

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
