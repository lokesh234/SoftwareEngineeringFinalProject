package edu.wpi.cs3733.c20.teamU;

import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.cs3733.c20.teamU.Administration.*;
import edu.wpi.cs3733.c20.teamU.Administration.AdminRequestController;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindController;
//import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindTextController;
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

  private static ArrayList<String> textpath = new ArrayList<>();

  private static Pane home;
  private static Pane login;
  private static Pane start;
  private static Pane path;
  private static Pane admin;
  private static Pane security;
  private static Pane request;
  private static Pane medicine;
  private static Pane flower;
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
  private static Pane IT;
  private static Pane clown;

  private static Pane religious;

  private static Pane employeeF;

  private static Pane externalTransport;  // This is anir
  private static Pane pathFindText;


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
  private static Scene employeeFormScene;
  private static Scene religiousScene;
  private static Scene extTransportScene; //this is anir
  private static Scene pathFindTextScene;

  private static LoginScreenController loginScreenController;
  private static HomeController homeController;
  private static PathfindController pathfindController;
  private static SecurityController securityController;
  private static RequestController requestController;
  private static MedicineController medicineController;
  private static AdminScreenController adminScreenController;
  private static AdminRequestController adminRequestController;
  private static GraphEditController graphEditController;
  private static NodeEditController editController;
  private static EdgeViewScreenController viewEdgeViewScreenController;
  private static EdgeEditController editEdgeController;
  private static WeatherController weatherController;
  private static EmployeeFormController employeeFormController;
  private static ReligiousController religiousController;
  private static ExtTransportController extTransportController; //anir
  private static PathfindTextController pathfindTextController;
  private static ClownController clownController;

  private static Edge edgeEdit;
  private static RequestScreenController requestScreenController;
  private static FireController fireController;
  private static AddNodeScreenController addNodeScreenController;

  private static boolean didChange = false;
  private static long time;

  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeEdit;
  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeAdd;
  private static Service service;
  private static String user;
  private static String usernameTried;
  private static int nodeSize = 10; //Radius in pixels of clickable node object

  private static Popup popup = new Popup();
  private static Popup securityPop = new Popup();
  private static Popup requestPop = new Popup();
  private static Popup medicinePop = new Popup();
  private static Popup ITPop = new Popup();
  private static Popup religiousPop = new Popup();
  private static Popup extTransportPop = new Popup(); //anir
  private static Popup flowerPop = new Popup();
  private static Popup TextDirectionsPop = new Popup();
  private static Popup clownPop = new Popup();

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
  public static Pane getIT() { return IT;}
  public static Pane getExternalTransport() {return externalTransport;} //anir
  public static Pane getPathFindText() {return pathFindText;}

  public static Pane getReligious() { return religious;}

  public static Pane getEmployeeForm() {return employeeF; }
  public static Pane getFlower(){return  flower;}
  public static Pane getClown() { return clown;}


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
  public static Scene getEmployeeFormScene() {return employeeFormScene; }
  public static Scene getExtTransportScene() {return extTransportScene;} //anir
  public static Scene getReligiousScene() { return religiousScene; }
  public static Scene getPathFindTextScene() {return pathFindTextScene;}

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
  public static GraphEditController getGraphEditController() { return graphEditController;}
  public static WeatherController weatherController() {return weatherController; }
  public static NodeEditController getEditController() { return editController;}
  public static EmployeeFormController getEmployeeFormController() { return employeeFormController; }
  public static ExtTransportController getExtTransportController() {return  extTransportController;}
  public static ReligiousController getReligiousController() { return religiousController; }
  public static PathfindTextController getPathfindTextController() {return pathfindTextController;}

  public static edu.wpi.cs3733.c20.teamU.Database.Node getNodeEdit() { return nodeEdit; }
  public static edu.wpi.cs3733.c20.teamU.Database.Node getNodeAdd() { return nodeAdd; }
  public static ArrayList<String> getTextpath() { return textpath;}

  public static void setNodeEdit(edu.wpi.cs3733.c20.teamU.Database.Node userNode) { nodeEdit = userNode; }
  public static void setEdgeEdit(Edge userEdge) { edgeEdit = userEdge; }
  public static Edge getEdgeEdit() { return edgeEdit; }
  public static void setServiceEdit(Service serviceSel) { service = serviceSel; }
  public static Service getService() { return service; }

  public static void change(boolean hey) { didChange = hey; }
  public static boolean getChange() { return didChange; }
  public static void setTime(long sys) { time = sys; }
  public static long getTime() { return time; }
  public static void setUser(String user1) { user = user1; }
  public static String getUser() { return user; }
  public static void setUsernameTried(String username1) { usernameTried = username1; }
  public static String getUsernameTried() { return usernameTried; }
  public static int getNodeSize() { return nodeSize; }

  public static Popup getPopup() { return popup; }
  public static Popup getSecurityPop() { return securityPop; }
  public static Popup getRequestPop() { return requestPop; }
  public static Popup getMedicinePop() { return medicinePop; }
  public static Popup getITPop() { return ITPop;}
  public static Popup getReligiousPop() { return religiousPop;}
  public static Popup getExtTransportPop() {return extTransportPop;}
  public static Popup getTextDirectionsPop() {return TextDirectionsPop;}
  public static Popup getClownPop() { return clownPop;}
  public static Popup getFlowerPop(){return flowerPop;}


  @Override
  public void start(Stage primaryStage) throws Exception {

    App.primaryStage = primaryStage;
    DatabaseWrapper.updateGraph();
    try {

      FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/light_theme/HomeStart.fxml"));
      FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/light_theme/Home.fxml"));
      FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/light_theme/LoginForm.fxml"));
      FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/light_theme/AdminMenu.fxml"));
//      FXMLLoader adminNodeLoader = new FXMLLoader(getClass().getResource("/light_theme/Node.fxml"));
      FXMLLoader pathfindLoader = new FXMLLoader(getClass().getResource("/light_theme/Pathfind.fxml"));
      FXMLLoader securityLoader = new FXMLLoader(getClass().getResource("/light_theme/RequestSecurityForm.fxml"));
      FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/light_theme/RequestMenu.fxml"));
      FXMLLoader medicineLoader = new FXMLLoader(getClass().getResource("/light_theme/RequestMedicineForm.fxml"));
      FXMLLoader flowerLoader = new FXMLLoader(getClass().getResource("/light_theme/RequestFlowerForm.fxml"));
      FXMLLoader exportLoader = new FXMLLoader(getClass().getResource("/light_theme/ExportForm.fxml"));
      FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/light_theme/NodeForm.fxml"));
      FXMLLoader adminRequestLoader = new FXMLLoader((getClass().getResource("/light_theme/Request.fxml")));
      FXMLLoader editEdgeLoader = new FXMLLoader(getClass().getResource("/light_theme/Edit_Edge.fxml"));
      FXMLLoader adminEdgeLoader = new FXMLLoader(getClass().getResource("/light_theme/View_Edges.fxml")); //TODO: add correct fxml
      FXMLLoader RRLoader = new FXMLLoader(getClass().getResource("/light_theme/RequestFormResolve.fxml"));
      FXMLLoader weatherLoader = new FXMLLoader(getClass().getResource("/light_theme/WeatherWindow.fxml"));
      FXMLLoader ITLoader = new FXMLLoader(getClass().getResource("/light_theme/RequestIT.fxml"));
      FXMLLoader fireLoader = new FXMLLoader(getClass().getResource("/light_theme/PathfindEmergency.fxml"));
      FXMLLoader adminGraphLoader = new FXMLLoader(getClass().getResource("/light_theme/AdminGraph.fxml"));
//      FXMLLoader adminGraphLoader = new FXMLLoader(getClass().getResource("/light_theme/Node.fxml"));
      FXMLLoader addNodeLoader = new FXMLLoader(getClass().getResource("/light_theme/Add_Node.fxml"));
      FXMLLoader employeeFormLoader = new FXMLLoader(getClass().getResource("/light_theme/EmployeeForm.fxml"));
      FXMLLoader religiousLoader = new FXMLLoader(getClass().getResource("/light_theme/ReligiousRequest.fxml"));


      FXMLLoader extTransportLoader = new FXMLLoader(getClass().getResource("/light_theme/ExternalTransportForm.fxml")); //anir
      FXMLLoader pathfindtextLoader = new FXMLLoader(getClass().getResource("/light_theme/PathfindTextDirections.fxml"));
      FXMLLoader clownDeliveryLoader = new FXMLLoader(getClass().getResource("/light_theme/RequestClownForm.fxml"));



      home = homeLoader.load();
      login = loginLoader.load();
      start = startLoader.load();
      path = pathfindLoader.load();
      security = securityLoader.load();
      admin = adminLoader.load();
      request = requestLoader.load();
      medicine = medicineLoader.load();
      flower = flowerLoader.load();
      fire = fireLoader.load();
      weather = weatherLoader.load();
      religious = religiousLoader.load();
      IT = ITLoader.load();
      export = exportLoader.load();
      edit = editLoader.load();
      adminRequest = adminRequestLoader.load();
      adminNode = adminGraphLoader.load();
      editEdge = editEdgeLoader.load();
      adminEdge = adminEdgeLoader.load();
      addNode = addNodeLoader.load();
      resolveRequest = RRLoader.load();
      employeeF = employeeFormLoader.load();
      externalTransport = extTransportLoader.load();
      pathFindText = pathfindtextLoader.load();
      clown = clownDeliveryLoader.load();

      loginScreenController = loginLoader.getController();
      homeController = homeLoader.getController();
      pathfindController = pathfindLoader.getController();
      securityController = securityLoader.getController();
      requestController = requestLoader.getController();
      medicineController = medicineLoader.getController();
      adminScreenController = adminLoader.getController();
      adminRequestController = adminRequestLoader.getController();
      graphEditController = adminGraphLoader.getController();
      editController = editLoader.getController();
      editEdgeController = editEdgeLoader.getController();
      viewEdgeViewScreenController = adminEdgeLoader.getController();
      addNodeScreenController = addNodeLoader.getController();
      requestScreenController = RRLoader.getController();
      fireController = fireLoader.getController();
      weatherController = weatherLoader.getController();
      employeeFormController = employeeFormLoader.getController();
      religiousController = religiousLoader.getController();
//      editEdgeController = adminNodeLoader.getController();
      extTransportController = extTransportLoader.getController();
      pathfindController = pathfindLoader.getController();
      clownController = clownDeliveryLoader.getController();

      pathfindController.setAttributes(path);
      fireController.setAttributes(fire);
      graphEditController.setAttributes(editController);
      editController.setAttributes(graphEditController);
      viewEdgeViewScreenController.setAttributes(editEdgeController);
      requestScreenController.setAttributes(adminRequestController);
      adminRequestController.setAttributes(requestScreenController);
      addNodeScreenController.setAttributes(graphEditController);
      loginScreenController.setAttributes(adminScreenController);


      popup.getContent().addAll();
      securityPop.getContent().addAll();
      requestPop.getContent().addAll();
      medicinePop.getContent().addAll();
      ITPop.getContent().addAll();
      religiousPop.getContent().addAll();
      extTransportPop.getContent().addAll();
      TextDirectionsPop.getContent().addAll();


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
