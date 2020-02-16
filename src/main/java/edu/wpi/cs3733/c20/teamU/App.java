package edu.wpi.cs3733.c20.teamU;

import java.io.IOException;

import edu.wpi.cs3733.c20.teamU.Administration.*;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.NodesDatabase;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindController;
//import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


import javafx.stage.Popup;
import javafx.stage.Stage;

//import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;

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
  private static Pane fire;
  private static Pane export;
  private static Pane edit;
  private static Pane editEdge;
  private static Pane adminRequest;
  private static Pane adminNode;
  private static Pane adminEdge;
  private static Pane addNode;
  private static Pane resolveRequest;
  private static Pane weather;

  private static Scene homeScene;
  private static Scene loginScene;
  private static Scene startScene;
  private static Scene pathScene;
  private static Scene adminScene;
  private static Scene securityScene;
  private static Scene requestScene;
  private static Scene medicineScene;
  private static Scene fireScene;
  private static Scene editScene;
  private static Scene exportScene;
  private static Scene adminNodeScene;
  private static Scene addNodeScene;
  private static Scene resolveRequestScene;
  private static Scene weatherScene;

  private static LoginScreenController loginScreenController;
  private static HomeController homeController;
  private static PathfindController pathfindController;
  private static SecurityController securityController;
  private static RequestController requestController;
  private static MedicineController medicineController;
  private static AdminScreenController adminScreenController;
  private static AdminRequestController adminRequestController;
  private static NodeViewScreenController nodeViewScreenController;
  private static NodeEditController editController;
  private static EdgeViewScreenController viewEdgeViewScreenController;
  private static EdgeEditController editEdgeController;
  private static WeatherController weatherController;

  private static Edge edgeEdit;
  private static RequestScreenController requestScreenController;
  private static FireController fireController;
  private static AddNodeScreenController addNodeScreenController;

  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeEdit;
  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeAdd;
  private static Service service;
  private static String user;
  private static NodesDatabase graph = new NodesDatabase();
  private static int nodeSize = 10; //Radius in pixels of clickable node object

  private static Popup popup = new Popup();
  private static Popup securityPop = new Popup();
  private static Popup requestPop = new Popup();
  private static Popup medicinePop = new Popup();

  public static Stage getPrimaryStage() { return primaryStage; }
  public static Pane getResolveRequest() {return resolveRequest;}
  public static Pane getHome() { return home; }
  public static Pane getLogin() { return login; }
  public static Pane getStart() { return start;}
  public static Pane getPath() { return path;}
  public static Pane getAdmin() { return admin; }
  public static Pane getSecurity() { return security; }
  public static Pane getRequest() { return request;}
  public static Pane getMedicine() { return medicine; }
  public static Pane getEditEdge() { return editEdge;}
  public static Pane getAdminEdge() { return adminEdge; }
  public static Pane getAdminRequest() { return adminRequest; }
  public static Pane getExport() { return export; }
  public static Pane getEdit() { return edit;}
  public static Pane getAddNode() { return addNode; }
  public static Pane getAdminNode() { return adminNode;}
  public static Pane getFire(){ return fire; }
  public static Pane getWeather() {return weather;}

  public static Scene getHomeScene() { return homeScene; }
  public static Scene getLoginScene() { return loginScene; }
  public static Scene getStartScene() { return startScene; }
  public static Scene getPathScene() { return pathScene; }
  public static Scene getAdminScene() { return adminScene; }
  public static Scene getSecurityScene() { return securityScene; }
  public static Scene getRequestScene() { return requestScene; }
  public static Scene getMedicineScene() { return medicineScene; }
  public static Scene getEditScene() { return editScene; }
  public static Scene getExportScene() { return exportScene; }
  public static Scene getaddNodeScene() { return addNodeScene; }
  public static Scene getFireScene(){ return fireScene; }
  public static Scene getAdminNodeScene() {return adminNodeScene;}
  public static Scene getResolveRequestScene() {return resolveRequestScene;}
  public static Scene getWeatherScene() {return weatherScene; }

  public static LoginScreenController getLoginScreenController() { return loginScreenController;}
  public static HomeController getHomeController() { return homeController;}
  public static PathfindController getPathfindController() { return pathfindController;}
  public static SecurityController getSecurityController() { return securityController;}
  public static RequestController getRequestController() {return requestController;}
  public static MedicineController getMedicineController() { return medicineController;}
  public static AdminScreenController getAdminScreenController() { return adminScreenController;}
  public static AddNodeScreenController getAddNodeScreenController() { return addNodeScreenController;}
  public static AdminRequestController getAdminRequestController() { return adminRequestController;}
  public static FireController getFireController(){return fireController;}
  public static NodeViewScreenController getNodeViewScreenController() { return nodeViewScreenController;}
  public static WeatherController weatherController() {return weatherController; }

  public static edu.wpi.cs3733.c20.teamU.Database.Node getNodeEdit() { return nodeEdit; }
  public static edu.wpi.cs3733.c20.teamU.Database.Node getNodeAdd() { return nodeAdd; }

  public static void setNodeEdit(edu.wpi.cs3733.c20.teamU.Database.Node userNode) { nodeEdit = userNode; }
  public static void setEdgeEdit(Edge userEdge) { edgeEdit = userEdge; }
  public static Edge getEdgeEdit() { return edgeEdit; }
  public static void setServiceEdit(Service serviceSel) { service = serviceSel; }
  public static Service getService() { return service; }

  public static void setUser(String user1) { user = user1; }
  public static String getUser() { return user; }
  public static NodesDatabase getGraph() { return graph; }
  public static int getNodeSize() { return nodeSize; }

  public static Popup getPopup() { return popup; }
  public static Popup getSecurityPop() { return securityPop; }
  public static Popup getRequestPop() { return requestPop; }
  public static Popup getMedicinePop() { return medicinePop; }

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
      FXMLLoader fireLoader = new FXMLLoader(getClass().getResource("/pathfindEmergency.fxml"));
      FXMLLoader exportLoader = new FXMLLoader(getClass().getResource("/Export_CSV.fxml"));
      FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/Edit_Node.fxml"));
      FXMLLoader addNodeLoader = new FXMLLoader(getClass().getResource("/Add_Node.fxml"));
      FXMLLoader adminRequestLoader = new FXMLLoader((getClass().getResource("/Admin_Service.fxml")));
      FXMLLoader editEdgeLoader = new FXMLLoader(getClass().getResource("/Edit_Edge.fxml"));
      FXMLLoader adminEdgeLoader = new FXMLLoader(getClass().getResource("/View_Edges.fxml")); //TODO: add correct fxml
      FXMLLoader RRLoader = new FXMLLoader(getClass().getResource("/Resolve_Request.fxml"));
      FXMLLoader weatherLoader = new FXMLLoader(getClass().getResource("/WeatherWindow.fxml"));


      home = homeLoader.load();
      login = loginLoader.load();
      start = startLoader.load();
      path = pathfindLoader.load();
      security = securityLoader.load();
      admin = adminLoader.load();
      request = requestLoader.load();
      medicine = medicineLoader.load();
      fire = fireLoader.load();
      weather = weatherLoader.load();

      /*
      LoginController loginController = loginLoader.getController();
      HomeController homeController = homeLoader.getController();
      PathfindController pathfindController = pathfindLoader.getController();
      SecurityController securityController = securityLoader.getController();
      RequestController requestController = requestLoader.getController();
      MedicineContoller medicineContoller = medicineLoader.getController();
      startController startControl = startLoader.getController();
      FireController fireController = fireLoader.getController();
       */

      export = exportLoader.load();
      edit = editLoader.load();
      adminRequest = adminRequestLoader.load();
      adminNode = adminNodeLoader.load();
      editEdge = editEdgeLoader.load();
      adminEdge = adminEdgeLoader.load();
      addNode = addNodeLoader.load();
      resolveRequest = RRLoader.load();

      loginScreenController = loginLoader.getController();
      homeController = homeLoader.getController();
      pathfindController = pathfindLoader.getController();
      securityController = securityLoader.getController();
      requestController = requestLoader.getController();
      medicineController = medicineLoader.getController();
      adminScreenController = adminLoader.getController();
      adminRequestController = adminRequestLoader.getController();
      nodeViewScreenController = adminNodeLoader.getController();
      editController = editLoader.getController();
      editEdgeController = editEdgeLoader.getController();
      viewEdgeViewScreenController = adminEdgeLoader.getController();
      addNodeScreenController = addNodeLoader.getController();
      requestScreenController = RRLoader.getController();
      fireController = fireLoader.getController();
      weatherController = weatherLoader.getController();

      pathfindController.setAttributes(path);
      fireController.setAttributes(fire);
      nodeViewScreenController.setAttributes(editController);
      editController.setAttributes(nodeViewScreenController);
      viewEdgeViewScreenController.setAttributes(editEdgeController);
      requestScreenController.setAttributes(adminRequestController);
      adminRequestController.setAttributes(requestScreenController);
      addNodeScreenController.setAttributes(nodeViewScreenController);

      popup.getContent().addAll();
      securityPop.getContent().addAll();
      requestPop.getContent().addAll();
      medicinePop.getContent().addAll();

      homeScene = new Scene(home);
      pathScene = new Scene(path);
      startScene = new Scene(start);
      fireScene = new Scene(fire);
      adminScene = new Scene(admin);
      adminNodeScene = new Scene(adminNode);
//      addNodeScene = new Scene(addNode);
//      deleteNodeScene = new Scene(deleteNode);

      primaryStage.setScene(startScene);
      primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/png_files/Icons/pharmacy.png")));
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  public static void addToPath(Node e) {//Adds a javaFX node to the pathfinding pane
    path.getChildren().add(e);
  }

  public static void removeFromPath(Node e) { //Removes a javaFX node from the pathfinding pane
    path.getChildren().remove(e);
  }
}
