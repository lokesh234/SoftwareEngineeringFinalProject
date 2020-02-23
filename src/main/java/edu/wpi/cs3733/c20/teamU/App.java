package edu.wpi.cs3733.c20.teamU;

import foodRequest.FoodRequest;
import foodRequest.ServiceException;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.cs3733.c20.teamU.Administration.*;
import edu.wpi.cs3733.c20.teamU.Administration.AdminRequestController;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindController;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindTextController;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Popup;
import javafx.stage.Stage;

public class App<loadedAdminRequests> extends Application {

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
  private static Pane sanRequest;
  private static Pane religious;
  private static Pane employeeF;
  private static Pane externalTransport;
  private static Pane internalTransport;
  private static Pane pathFindText;
  private static Pane languageSR;
  private static Pane PathChoose;
  private static Pane giftDelivery;
  private static Pane verification;

  private static Scene verificationScene;
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
  private static Scene extTransportScene;
  private static Scene intTransportScene;
  private static Scene pathFindTextScene;
  private static Scene sanSRScene;
  private static Scene languageSRScene;
  private static Scene PathChooseScene;
  private static Scene giftScene;


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
  private static ExtTransportController extTransportController;
  private static IntTransportController intTranspoerController;
  private static PathfindTextController pathfindTextController;
  private static ClownController clownController;
  private static LanguageController languageController;
  private static ChoosePathController choosePathController;
  private static GiftDeliveryController giftDeliveryController;
  private static VerificationController verificationController;
  private static Edge edgeEdit;
  private static RequestScreenController requestScreenController;
  private static FireController fireController;
  private static AddNodeScreenController addNodeScreenController;
  private static SanRequestController sanRequestController;

  private static boolean didChange = false;
  private static long time;

  private static boolean loadedImages = false;
  private static boolean loadedHome = false;
  private static boolean loadedAdminRequests = false;
  private static boolean loadedPathfinding = false;
  private static boolean loadedWeather = false;
  private static boolean loadedAdminGraph = false;
  private static boolean loadedMedicine = false;
  private static boolean loadedFlower = false;
  private static boolean loadedReligious = false;
  private static boolean loadedExtTransport = false;
  private static boolean loadedIntTransport = false;
  private static boolean loadedClown = false;
  private static boolean loadedSan = false;
  private static boolean loadedLanguage = false;
  private static boolean loadedGift = false;
  private static boolean loadedIT = false;

  public static boolean getLoadedAdmin() {return loadedAdminRequests;}
  public static boolean getLoadedPathFinding() {return loadedPathfinding;}
  public static boolean getLoadedWeather() {return loadedWeather;}
  public static boolean getLoadedAdminGraph() {return loadedAdminGraph;}


  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeEdit;
  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeAdd;
  private static Service service;
  private static Account user;
  private static String usernameTried;
  private static int nodeSize = 10; //Radius in pixels of clickable node object

  private static Popup popup = new Popup();
  private static Popup securityPop = new Popup();
  private static Popup requestPop = new Popup();
  private static Popup medicinePop = new Popup();
  private static Popup ITPop = new Popup();
  private static Popup religiousPop = new Popup();
  private static Popup extTransportPop = new Popup(); //anir
  private static Popup intTransportPop = new Popup(); //marcus
  private static Popup flowerPop = new Popup();
  private static Popup TextDirectionsPop = new Popup();
  private static Popup clownPop = new Popup();
  private static Popup sanRequestPop = new Popup();
  private static Popup languageSRPop = new Popup();
  private static Popup ChoosePathPop = new Popup();
  private static Popup weatherPop = new Popup();

  private static Image floor1;
  private static Image floor2;
  private static Image floor3;
  private static Image floor4;
  private static Image floor5;

  private static EventHandler<KeyEvent> fireKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.F) {
        App.getPrimaryStage().setScene(App.getFireScene());
      }
    }
  };

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
  public static Pane getExternalTransport() {return externalTransport;}
  public static Pane getInternalTransport() { return internalTransport;}
  public static Pane getPathFindText() {return pathFindText;}
  public static Pane getReligious() { return religious;}
  public static Pane getEmployeeForm() {return employeeF; }
  public static Pane getFlower(){return  flower;}
  public static Pane getClown() { return clown;}
  public static Pane getSanRequest(){return sanRequest;}
  public static Pane getLanguageSR() {return languageSR;}
  public static Pane getGiftDelivery() {return giftDelivery;}
  public static Pane getPathChoose() { return PathChoose; }
  public static Pane getVerification() { return verification;}

  public static Scene getVerificationScene() {return verificationScene;}
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
  public static Scene getExtTransportScene() {return extTransportScene;}
  public static Scene getReligiousScene() {
        return religiousScene;
    }
  public static Scene getIntTransportScene() { return intTransportScene; }
  public static Scene getPathFindTextScene() {return pathFindTextScene;}
  public static Scene getLanguageSRScene() {return languageSRScene;}
  public static Scene getPathChooseScene() {return PathChooseScene; }
  public static Scene getGiftScene() {return giftScene;}

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
  public static IntTransportController getIntTransportController() { return intTranspoerController; } //marcus
  public static ReligiousController getReligiousController() { return religiousController; }
  public static PathfindTextController getPathfindTextController() {return pathfindTextController;}
  public static SanRequestController getSanRequestController() {return sanRequestController;}
  public static LanguageController getLanguageController() {return languageController;}
  public static ChoosePathController getChoosePathController() {return choosePathController; }
  public static GiftDeliveryController getGiftDeliveryController() {return giftDeliveryController;}
  public static VerificationController getVerificationController() {return verificationController;}

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
  public static void setUser(Account user1) { user = user1; }
  public static Account getUser() { return user; }
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
  public static Popup getIntTransportPop() { return intTransportPop;} //marcus
  public static Popup getTextDirectionsPop() {return TextDirectionsPop;}
  public static Popup getClownPop() { return clownPop;}
  public static Popup getFlowerPop(){return flowerPop;}
  public static Popup getSanRequestPop(){ return sanRequestPop;}
  public static Popup getLanguageSRPop() {return languageSRPop;}
  public static Popup getChoosePathPop() { return ChoosePathPop; }
  public static Popup getWeatherPop() { return weatherPop; }

  public static Image getFloor1() { return floor1;}
  public static Image getFloor2() { return floor2;}
  public static Image getFloor3() { return floor3;}
  public static Image getFloor4() { return floor4;}
  public static Image getFloor5() { return floor5;}

  @Override
  public void start(Stage primaryStage) throws ServiceException {

    App.primaryStage = primaryStage;
    DatabaseWrapper.updateGraph();

    popup.getContent().addAll();
    securityPop.getContent().addAll();
    requestPop.getContent().addAll();
    medicinePop.getContent().addAll();
    ITPop.getContent().addAll();
    religiousPop.getContent().addAll();
    extTransportPop.getContent().addAll();
    intTransportPop.getContent().addAll();
    TextDirectionsPop.getContent().addAll();
    sanRequestPop.getContent().addAll();
    languageSRPop.getContent().addAll();
    ChoosePathPop.getContent().addAll();

    loadHome();

    primaryStage.setScene(startScene);
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/png_files/wwong2.jpg")));
    primaryStage.show();
    FoodRequest foodRequest = new FoodRequest();
    foodRequest.run(800, 800, 800, 800, null, "something", "something");

  }

  private static void loadImages() {
    if (!loadedImages) {
       floor1 = new Image("/png_files/Floor1LM.png");
       floor2 = new Image("/png_files/Floor2LM.png");
       floor3 = new Image("/png_files/Floor3LM.png");
       floor4 = new Image("/png_files/Floor4LM.png");
       floor5 = new Image("/png_files/Floor5LM.png");

      loadedImages = true;
    }
  }

  public static void loadHome() {
    if (!loadedHome) {
      try {
        loadImages();

        FXMLLoader startLoader = new FXMLLoader(App.class.getResource("/light_theme/HomeStart.fxml"));
        FXMLLoader homeLoader = new FXMLLoader(App.class.getResource("/light_theme/Home.fxml"));
        FXMLLoader fireLoader = new FXMLLoader(App.class.getResource("/light_theme/PathfindEmergency.fxml"));
        FXMLLoader securityLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestSecurityForm.fxml"));

        home = homeLoader.load();
        start = startLoader.load();
        fire = fireLoader.load();
        security = securityLoader.load();

        home.setOnKeyPressed(fireKey);
        start.setOnKeyPressed(fireKey);
        security.setOnKeyPressed(fireKey);

        homeController = homeLoader.getController();
        fireController = fireLoader.getController();
        securityController = securityLoader.getController();

        fireController.setAttributes(fire);

        homeScene = new Scene(home);
        startScene = new Scene(start);
        fireScene = new Scene(fire);

      }
      catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }
  }

  public static void loadAdminRequests() {
    if (!loadedAdminRequests) {
      try {
        FXMLLoader adminLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminMenu.fxml"));
        FXMLLoader adminRequestLoader = new FXMLLoader((App.class.getResource("/light_theme/Request.fxml")));
        FXMLLoader choosePathLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminPathForm.fxml"));
        FXMLLoader employeeFormLoader = new FXMLLoader(App.class.getResource("/light_theme/EmployeeForm.fxml"));
        FXMLLoader exportLoader = new FXMLLoader(App.class.getResource("/light_theme/ExportForm.fxml"));
        FXMLLoader RRLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestFormResolve.fxml"));
        FXMLLoader requestLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestMenu.fxml"));
        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("/light_theme/LoginForm.fxml"));
        FXMLLoader verificationLoader = new FXMLLoader(App.class.getResource("/light_theme/VerificationForm.fxml"));

        admin = adminLoader.load();
        adminRequest = adminRequestLoader.load();
        employeeF = employeeFormLoader.load();
        PathChoose = choosePathLoader.load();
        resolveRequest = RRLoader.load();
        export = exportLoader.load();
        request = requestLoader.load();
        login = loginLoader.load();
        verification = verificationLoader.load();

        admin.setOnKeyPressed(fireKey);
        adminRequest.setOnKeyPressed(fireKey);
        employeeF.setOnKeyPressed(fireKey);
        PathChoose.setOnKeyPressed(fireKey);
        resolveRequest.setOnKeyPressed(fireKey);
        export.setOnKeyPressed(fireKey);
        request.setOnKeyPressed(fireKey);
        login.setOnKeyPressed(fireKey);

        verificationController = verificationLoader.getController();
        adminScreenController = adminLoader.getController();
        adminRequestController = adminRequestLoader.getController();
        employeeFormController = employeeFormLoader.getController();
        choosePathController = choosePathLoader.getController();
        requestScreenController = RRLoader.getController();
        requestController = requestLoader.getController();
        loginScreenController = loginLoader.getController();

        requestScreenController.setAttributes(adminRequestController);
        adminRequestController.setAttributes(requestScreenController);
        loginScreenController.setAttributes(employeeFormController);

        adminScene = new Scene(admin);

        loadedAdminRequests = true;

      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadMedicine() {
    if (!loadedMedicine) {
      try {
        FXMLLoader medicineLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestMedicineForm.fxml"));

        medicine = medicineLoader.load();

        medicineController = medicineLoader.getController();

        medicine.setOnKeyPressed(fireKey);

        loadedMedicine = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadFlower() {
    if (!loadedFlower) {
      try {
        FXMLLoader flowerLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestFlowerForm.fxml"));

        flower = flowerLoader.load();

        flower.setOnKeyPressed(fireKey);

        loadedFlower = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadReligious() {
    if (!loadedReligious) {
      try {
        FXMLLoader religiousLoader = new FXMLLoader(App.class.getResource("/light_theme/ReligiousRequest.fxml"));

        religious = religiousLoader.load();

        religiousController = religiousLoader.getController();

        religious.setOnKeyPressed(fireKey);

        loadedReligious = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadExtTransport() {
    if (!loadedExtTransport) {
      try {
        FXMLLoader extTransportLoader = new FXMLLoader(App.class.getResource("/light_theme/ExternalTransportForm.fxml")); //anir

        externalTransport = extTransportLoader.load();

        extTransportController = extTransportLoader.getController();

        externalTransport.setOnKeyPressed(fireKey);

        loadedExtTransport = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadIntTransport() {
    if (!loadedIntTransport) {
      try {
        FXMLLoader intTransportLoader = new FXMLLoader(App.class.getResource("/light_theme/InternalTransportForm.fxml")); //marcus

        internalTransport = intTransportLoader.load();

        intTranspoerController = intTransportLoader.getController(); //marcus

        internalTransport.setOnKeyPressed(fireKey);

        loadedIntTransport = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadClown() {
    if (!loadedClown) {
      try {
        FXMLLoader clownDeliveryLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestClownForm.fxml"));

        clown = clownDeliveryLoader.load();

        clownController = clownDeliveryLoader.getController();

        clown.setOnKeyPressed(fireKey);

        loadedClown = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadSan() {
    if (!loadedSan){
      try {
        FXMLLoader sanRequestLoader = new FXMLLoader(App.class.getResource("/light_theme/SanitationRequests.fxml"));

        sanRequest = sanRequestLoader.load();

        sanRequest.setOnKeyPressed(fireKey);

        loadedSan = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadLanguage() {
    if (!loadedLanguage) {
      try {
        FXMLLoader languageLoader = new FXMLLoader(App.class.getResource("/light_theme/LanguageForm.fxml"));

        languageSR = languageLoader.load();

        languageController = languageLoader.getController();

        languageSR.setOnKeyPressed(fireKey);

        loadedLanguage = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadGift() {
    if (!loadedGift) {
      try {
        FXMLLoader giftDeliveryLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestGiftDelivery.fxml"));

        giftDelivery = giftDeliveryLoader.load();

        giftDeliveryController = giftDeliveryLoader.getController();

        giftDelivery.setOnKeyPressed(fireKey);

        loadedGift = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadIT() {
    if (!loadedIT) {
      try {
        FXMLLoader ITLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestIT.fxml"));

        IT = ITLoader.load();

        IT.setOnKeyPressed(fireKey);

        loadedIT = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadPathfinding() {
    if (!loadedPathfinding) {
      try {
        FXMLLoader pathfindLoader = new FXMLLoader(App.class.getResource("/light_theme/Pathfind.fxml"));
        FXMLLoader pathfindtextLoader = new FXMLLoader(App.class.getResource("/light_theme/PathfindTextDirections.fxml"));

        path = pathfindLoader.load();
        pathFindText = pathfindtextLoader.load();

        path.setOnKeyPressed(fireKey);
        pathFindText.setOnKeyPressed(fireKey);

        pathfindController = pathfindLoader.getController();
        pathfindTextController = pathfindtextLoader.getController();

        pathfindController.setAttributes(path);

        pathScene = new Scene(path);

        loadedPathfinding = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadWeather() {
    if (!loadedWeather) {
      try {
        FXMLLoader weatherLoader = new FXMLLoader(App.class.getResource("/light_theme/WeatherWindow.fxml"));

        weather = weatherLoader.load();

        weather.setOnKeyPressed(fireKey);

        weatherController = weatherLoader.getController();

        homeController.setWeatherData(weatherController);

        loadedWeather = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadAdminGraph() {
    if (!loadedAdminGraph) {
      try {
        FXMLLoader adminGraphLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminGraph.fxml"));
        FXMLLoader addNodeLoader = new FXMLLoader(App.class.getResource("/light_theme/Add_Node.fxml"));
        FXMLLoader editLoader = new FXMLLoader(App.class.getResource("/light_theme/NodeForm.fxml"));

        edit = editLoader.load();
        adminNode = adminGraphLoader.load();
        addNode = addNodeLoader.load();

        edit.setOnKeyPressed(fireKey);
        adminNode.setOnKeyPressed(fireKey);
        addNode.setOnKeyPressed(fireKey);

        graphEditController = adminGraphLoader.getController();
        editController = editLoader.getController();
        addNodeScreenController = addNodeLoader.getController();

        graphEditController.setAttributes(editController);
        editController.setAttributes(graphEditController);

        addNodeScreenController.setAttributes(graphEditController);

        adminNodeScene = new Scene(adminNode);

        loadedAdminGraph = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void addToPath(Node e) {//Adds a javaFX node to the pathfinding pane
    path.getChildren().add(e);
  }

  public static void removeFromPath(Node e) { //Removes a javaFX node from the pathfinding pane
    path.getChildren().remove(e);
  }
}
