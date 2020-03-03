package edu.wpi.cs3733.c20.teamU;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import edu.wpi.cs3733.c20.teamR.AppointmentRequest;
import edu.wpi.cs3733.c20.teamU.Administration.*;
import edu.wpi.cs3733.c20.teamU.Administration.AdminRequestController;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindController;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindTextController;
import edu.wpi.cs3733.c20.teamU.Navigation.TreeViewController;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class App<loadedAdminRequests> extends Application {

  private static Stage primaryStage;

  private static ArrayList<String> textpath = new ArrayList<>();
  private static ArrayList<String> spokenWords = new ArrayList<>();

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
  private static Pane adminEmployee;
  private static Pane adminBacklog;
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
  private static Pane timeout;
  private static Pane treeView;
  private static Pane analytics;
  private static Pane scale;
  private static Pane color;
  private static Pane information;
  private static Pane credit;

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
  private static Scene adminEmployeeScene;
  private static Scene adminBacklogScene;
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
  private static Scene timeoutScene;
  private static Scene treeViewScene;
  private static Scene analyticsScene;
  private static Scene colorScene;
  private static Scene informationScene;
  private static Scene creditScene;

  private static LoginScreenController loginScreenController;
  private static HomeController homeController;
  private static PathfindController pathfindController;
  private static SecurityController securityController;
  private static RequestController requestController;
  private static MedicineController medicineController;
  private static AdminScreenController adminScreenController;
  private static AdminRequestController adminRequestController;
  private static AdminEmployeeController adminEmployeeController;
  private static AdminBacklogController adminBacklogController;
  private static GraphEditController graphEditController;
  private static NodeEditController editController;
  private static EdgeViewScreenController viewEdgeViewScreenController;
  private static EdgeEditController editEdgeController;
  private static WeatherController weatherController;
  private static EmployeeFormController employeeFormController;
  private static ReligiousController religiousController;
  private static ExtTransportController extTransportController;
  private static IntTransportController intTransportController;
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
  private static TimeoutController timeoutController;
  private static TreeViewController treeViewController;
  private static FlowerController flowerController;
  private static ITController itController;
  private static AnalyticsController analyticsController;
  private static AdminScaleController scaleController;
  private static AdminColorController colorController;
  private static InformationController informationController;
  private static CreditController creditController;


  private static boolean didChange = false;
  private static long time;

  private static boolean loadedImages = false;
  private static boolean loadedHome = false;
  private static boolean loadedAdminRequests = false;
  private static boolean loadedAdminEmployee = false;
  private static boolean loadedAdminBacklog = false;
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
  private static boolean loadedTreeView = false;
  public static boolean SpeechComplete = false;

  private static double pixFoo = 3.5;
  private static double pixMet = 11.5;
  private static boolean useFeet = true;

  public static boolean getLoadedAdminRequest() {return loadedAdminRequests;}
  public static boolean getLoadedAdminEmployee() {return loadedAdminEmployee;}
  public static boolean getLoadedAdminBacklog() {return loadedAdminBacklog;}
  public static boolean getLoadedPathFinding() {return loadedPathfinding;}
  public static boolean getLoadedWeather() {return loadedWeather;}
  public static boolean getLoadedAdminGraph() {return loadedAdminGraph;}

  public static double getPixFoo() { return pixFoo;}
  public static double getPixMet() { return pixMet;}
  public static String getUnit() {
    if (useFeet) return "feet";
    else return "meters";
  }

  public static void resetLoad() {
    loadedAdminRequests = false;
  }

  public static void setPixFoo(double x) { pixFoo = x;}
  public static void setPixMet(double x) { pixMet = x;}
  public static void setUseFeet(boolean x) { useFeet = x;}


  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeEdit;
  private static edu.wpi.cs3733.c20.teamU.Database.Node nodeAdd;
  private static long timeoutValue = 180000; // variable for timeout. 1000 = 1s, default 3 minutes
  private static Service service;
  private static Account user;
  private static Account accountEdit;
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
  private static Popup treeViewPop = new Popup();
  private static Popup informationPopUp = new Popup();
  private static Popup creditPop = new Popup();

  private static Image floor1;
  private static Image floor2;
  private static Image floor3;
  private static Image floor4;
  private static Image floor5;

  private static edu.wpi.cs3733.c20.teamU.Database.Node location;

  public static edu.wpi.cs3733.c20.teamU.Database.Node getLocation() { return location;}
  public static void setLocation(edu.wpi.cs3733.c20.teamU.Database.Node n) { location = n;}
  public static void setTimeoutValue(long timeoutDesire) { timeoutValue = timeoutDesire; }
  public static long getTimeoutValue() {return timeoutValue;}

  public static void setColor(edu.wpi.cs3733.c20.teamU.Database.Node n, Circle c) {
    if (n.getID().equals(location.getID())) {
      c.setFill(new Color(214/255., 48/255., 49/255.,1.0)); //Chi-Gong
    }
    else switch (n.getNodeType()) {
      case "HALL":
        c.setFill(new Color(223/255., 230/255., 233/255.,1.0)); //City Lights
        break;
      case "DEPT":
        c.setFill(new Color(45/255., 52/255., 54/255.,1.0)); //Dracula Orchid
        break;
      case "CONF":
        c.setFill(new Color(99/255., 110/255., 114/255.,1.0)); //America River
        break;
      case "REST":
        c.setFill(new Color(108/255., 92/255., 231/255.,1.0)); //Exodus Fruit
        break;
      case "STAI":
        c.setFill(new Color(85/255., 239/255., 196/255.,1.0)); //Light Greenish Blue
        break;
      case "ELEV":
        c.setFill(new Color(129/255., 236/255., 236/255.,1.0)); //Faded Poster
        break;
      case "LABS":
        c.setFill(new Color(116/255., 185/255., 255/255.,1.0)); //Green Darner Tail
        break;
      case "INFO":
        c.setFill(new Color(9/255., 132/255., 227/255.,1.0)); //Electron Blue
        break;
      case "EXIT":
        c.setFill(new Color(225/255., 112/255., 85/255.,1.0)); //Orangeville
        break;
      case "RETL":
        c.setFill(new Color(253/255., 121/255., 168/255.,1.0)); //Pico-8 Pink
        break;
      case "SERV":
        c.setFill(new Color(253/255., 203/255., 110/255.,1.0)); //Bright Yarrow
        break;
    }
  }

  public static void setIcon(edu.wpi.cs3733.c20.teamU.Database.Node n, ImageView imageView) {
    switch (n.getNodeType()) {
      case "DEPT":
        imageView.setImage(new Image("/png_files/department15.png"));
        break;
      case "CONF":
        imageView.setImage(new Image("/png_files/restroom15.png"));        break;
      case "REST":
        imageView.setImage(new Image("/png_files/restroom15.png"));        break;
      case "STAI":
        imageView.setImage(new Image("/png_files/stairs15.png"));        break;
      case "ELEV":
        imageView.setImage(new Image("/png_files/elevator15.png"));        break;
      case "LABS":
        imageView.setImage(new Image("/png_files/Lab15.png"));        break;
      case "INFO":
        imageView.setImage(new Image("/png_files/info15.png"));        break;
      case "EXIT":
        imageView.setImage(new Image("/png_files/exit15.png"));        break;
      case "RETL":
        imageView.setImage(new Image("/png_files/retail15.png"));        break;
      case "SERV":
        imageView.setImage(new Image("/png_files/service15.png"));        break;
    }
  }


  private static EventHandler<KeyEvent> fireKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.F) {
        App.getPrimaryStage().setScene(App.getFireScene());
      }
    }
  };

  private static EventHandler<KeyEvent> creditKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.C) {
        App.getHome().setOpacity(.5);
        App.getHome().setDisable(true);
        App.getCreditPop().getContent().add(App.getCredit());
        App.getCreditPop().show(App.getPrimaryStage());
      }
    }
  };

  private static EventHandler<KeyEvent> loginconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        loginScreenController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> addnodeconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        addNodeScreenController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> employeeformconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        employeeFormController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> exttransportconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        extTransportController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> inttransportconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        intTransportController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> languageconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        languageController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> nodeeditconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        editController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> religiousconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        religiousController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> clownconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        clownController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> flowerconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        flowerController.keyConfirm();
      }
    }
  };

  private static EventHandler<KeyEvent> giftconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        giftDeliveryController.keyConfirm();

      }
    }
  };

  private static EventHandler<KeyEvent> itconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        itController.keyConfirm();

      }
    }
  };

  private static EventHandler<KeyEvent> medicineconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        medicineController.keyConfirm();

      }
    }
  };

  private static EventHandler<KeyEvent> sanitationconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        sanRequestController.keyConfirm();

      }
    }
  };

  private static EventHandler<KeyEvent> timeoutconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        timeoutController.keyConfirm();

      }
    }
  };

  private static EventHandler<KeyEvent> verificationconfirmKey = new EventHandler<javafx.scene.input.KeyEvent>() {
    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
        verificationController.keyConfirm();

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
  public static Pane getAdminEmployee() { return adminEmployee; }
  public static Pane getAdminBacklog() { return adminBacklog; }
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
  public static Pane getTimeout() {return timeout;}
  public static Pane getTreeView() {return treeView;}
  public static Pane getAnalytics() {return analytics;}
  public static Pane getScale() { return scale;}
  public static Pane getColor() { return color;}
  public static Pane getInformation() {return information;}
  public static Pane getCredit() {return credit;}

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
  public static Scene getAdminEmployeeScene() {return adminEmployeeScene;}
  public static Scene getAdminBacklogSceneScene() {return adminBacklogScene;}
  public static Scene getResolveRequestScene() {return resolveRequestScene;}
  public static Scene getWeatherScene() {return weatherScene; }
  public static Scene getEmployeeFormScene() {return employeeFormScene; }
  public static Scene getExtTransportScene() {return extTransportScene;}
  public static Scene getReligiousScene() { return religiousScene; }
  public static Scene getIntTransportScene() { return intTransportScene; }
  public static Scene getPathFindTextScene() {return pathFindTextScene;}
  public static Scene getLanguageSRScene() {return languageSRScene;}
  public static Scene getPathChooseScene() {return PathChooseScene; }
  public static Scene getGiftScene() {return giftScene;}
  public static Scene getTimeoutScene() {return timeoutScene;}
  public static Scene getAnalyticsScene() {return analyticsScene;}
  public static Scene getColorScene() {return colorScene;}
  public static Scene getInformationScene() {return informationScene;}
  public static Scene getCreditScene() {return creditScene;}

  public static LoginScreenController getLoginScreenController() { return loginScreenController;}
  public static HomeController getHomeController() { return homeController;}
  public static PathfindController getPathfindController() { return pathfindController;}
  public static SecurityController getSecurityController() { return securityController;}
  public static RequestController getRequestController() {return requestController;}
  public static MedicineController getMedicineController() { return medicineController;}
  public static AdminScreenController getAdminScreenController() { return adminScreenController;}
  public static AddNodeScreenController getAddNodeScreenController() { return addNodeScreenController;}
  public static AdminRequestController getAdminRequestController() { return adminRequestController;}
  public static AdminEmployeeController getAdminEmployeeController() { return adminEmployeeController;}
  public static AdminBacklogController getAdminBacklogController() { return adminBacklogController;}
  public static FireController getFireController(){return fireController;}
  public static GraphEditController getGraphEditController() { return graphEditController;}
  public static WeatherController weatherController() {return weatherController; }
  public static NodeEditController getEditController() { return editController;}
  public static EmployeeFormController getEmployeeFormController() { return employeeFormController; }
  public static ExtTransportController getExtTransportController() {return  extTransportController;}
  public static IntTransportController getIntTransportController() { return intTransportController; } //marcus
  public static ReligiousController getReligiousController() { return religiousController; }
  public static PathfindTextController getPathfindTextController() {return pathfindTextController;}
  public static SanRequestController getSanRequestController() {return sanRequestController;}
  public static LanguageController getLanguageController() {return languageController;}
  public static ChoosePathController getChoosePathController() {return choosePathController; }
  public static GiftDeliveryController getGiftDeliveryController() {return giftDeliveryController;}
  public static VerificationController getVerificationController() {return verificationController;}
  public static TreeViewController getTreeViewController() {return treeViewController;}
  public static TimeoutController getTimeoutController() {return timeoutController;}
  public static AnalyticsController getAnalyticsController() {return analyticsController;}
  public static AdminScaleController getScaleController() { return scaleController;}
  public static AdminColorController getColorController() {return colorController;}
  public static InformationController getInformationController() {return informationController;}
  public static CreditController getCreditController() {return creditController;}

  public static edu.wpi.cs3733.c20.teamU.Database.Node getNodeEdit() { return nodeEdit; }
  public static edu.wpi.cs3733.c20.teamU.Database.Node getNodeAdd() { return nodeAdd; }
  public static ArrayList<String> getTextpath() { return textpath;}

  public static void setNodeEdit(edu.wpi.cs3733.c20.teamU.Database.Node userNode) { nodeEdit = userNode; }
  public static void setEdgeEdit(Edge userEdge) { edgeEdit = userEdge; }
  public static Edge getEdgeEdit() { return edgeEdit; }
  public static Account getAccountEdit() { return accountEdit; }
  public static void setServiceEdit(Service serviceSel) { service = serviceSel; }
  public static void setAccountEdit(Account accountSel) { accountEdit = accountSel; }
  public static Service getService() { return service; }
  public static ArrayList<String> getSpokenWords() {return spokenWords;}

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
  public static Popup getTreeViewPop() { return treeViewPop; }
  public static Popup getInformationPopUp() {return informationPopUp;}
  public static Popup getCreditPop() {return creditPop;}

  public static Image getFloor1() { return floor1;}
  public static Image getFloor2() { return floor2;}
  public static Image getFloor3() { return floor3;}
  public static Image getFloor4() { return floor4;}
  public static Image getFloor5() { return floor5;}

  @Override
  public void start(Stage primaryStage) {
    App.primaryStage = primaryStage;
    DatabaseWrapper.updateGraph();
    setLocation(DatabaseWrapper.getGraph().getNode("RDEPT00401"));
//    AppointmentRequest.run(0, 0, 1900, 1000, null, "hello", "hello");

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
    treeViewPop.getContent().addAll();
    informationPopUp.getContent().addAll();

    loadHome();

    primaryStage.setScene(startScene);
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/png_files/wongPFP.png")));
    try {
      ProcessBuilder processBuilder = new ProcessBuilder();
      processBuilder.redirectErrorStream(true);
      Map<String, String> env = processBuilder.environment();
      URL url = getClass().getResource("/key.json");
      String keyPath = url.getPath();
      keyPath = keyPath.replaceFirst("/","");
      keyPath = keyPath.replace("/", "\\");
      System.out.println("\\");
      System.out.println(keyPath);
      env.put("GOOGLE_APPLICATION_CREDENTIALS", keyPath);
      System.out.println("=====================================================================================================");
      for (String envName : env.keySet()) {
        System.out.format("%s=%s%n", envName, env.get(envName));
      }
    }
    catch (Exception e){
      System.out.println(e);
    }

    primaryStage.show();
//    AdministrationWrapper.setFirstTheme();
//    FoodRequest foodRequest = new FoodRequest();
//    foodRequest.run(800, 800, 800, 800, null, "something", "something");

  }

  private static void loadImages() {
    if (!loadedImages) {
//       floor1 = new Image("/png_files/Floor1LM.png");
//       floor2 = new Image("/png_files/Floor2LM.png");
//       floor3 = new Image("/png_files/Floor3LM.png");
//       floor4 = new Image("/png_files/Floor4LM.png");
//       floor5 = new Image("/png_files/Floor5LM.png");
       floor1 = new Image("/png_files/Floor1TRAN.png");
       floor2 = new Image("/png_files/Floor2TRAN.png");
       floor3 = new Image("/png_files/Floor3TRAN.png");
       floor4 = new Image("/png_files/Floor4TRAN.png");
       floor5 = new Image("/png_files/Floor5TRAN.png");

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
        FXMLLoader weatherLoader = new FXMLLoader(App.class.getResource("/light_theme/WeatherWindow.fxml"));
        FXMLLoader informationLoader = new FXMLLoader(App.class.getResource("/light_theme/InformationPage.fxml"));
        FXMLLoader creditLoader = new FXMLLoader(App.class.getResource("/light_theme/Credit.fxml"));


        home = homeLoader.load();
        start = startLoader.load();
        fire = fireLoader.load();
        security = securityLoader.load();
        weather = weatherLoader.load();
        information = informationLoader.load();
        credit = creditLoader.load();


        home.addEventHandler(KeyEvent.KEY_RELEASED, fireKey);
        start.addEventHandler(KeyEvent.KEY_RELEASED, fireKey);
        security.addEventHandler(KeyEvent.KEY_RELEASED, fireKey);
        weather.addEventHandler(KeyEvent.KEY_RELEASED, fireKey);
        information.addEventHandler(KeyEvent.KEY_RELEASED, fireKey);

        home.addEventHandler(KeyEvent.KEY_RELEASED, creditKey);
        start.addEventHandler(KeyEvent.KEY_RELEASED, creditKey);


        homeController = homeLoader.getController();
        fireController = fireLoader.getController();
        securityController = securityLoader.getController();
        weatherController = weatherLoader.getController();
        informationController = informationLoader.getController();

        homeController.setWeatherData(weatherController);
        fireController.setAttributes(fire);

        homeScene = new Scene(home);
        startScene = new Scene(start);
        fireScene = new Scene(fire);
        creditScene = new Scene(credit);

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
        FXMLLoader adminEmployeeLoader = new FXMLLoader((App.class.getResource("/light_theme/AdminEmployee.fxml")));
        FXMLLoader adminBacklogLoader = new FXMLLoader((App.class.getResource("/light_theme/AdminBacklog.fxml")));
        FXMLLoader analyticsLoader = new FXMLLoader((App.class.getResource("/light_theme/Analytics.fxml")));
        FXMLLoader choosePathLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminPathForm.fxml"));
        FXMLLoader employeeFormLoader = new FXMLLoader(App.class.getResource("/light_theme/EmployeeForm.fxml"));
        FXMLLoader exportLoader = new FXMLLoader(App.class.getResource("/light_theme/ExportForm.fxml"));
        FXMLLoader RRLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestFormResolve.fxml"));
        FXMLLoader requestLoader = new FXMLLoader(App.class.getResource("/light_theme/RequestMenu.fxml"));
        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("/light_theme/LoginForm.fxml"));
        FXMLLoader verificationLoader = new FXMLLoader(App.class.getResource("/light_theme/VerificationForm.fxml"));
        FXMLLoader timeoutLoader = new FXMLLoader(App.class.getResource("/light_theme/TimeoutForm.fxml"));
        FXMLLoader scaleLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminScaleForm.fxml"));
        FXMLLoader colorLoader = new FXMLLoader(App.class.getResource("/light_theme/AdminColor.fxml"));

        admin = adminLoader.load();
        adminRequest = adminRequestLoader.load();
        adminEmployee = adminEmployeeLoader.load();
        adminBacklog = adminBacklogLoader.load();
        employeeF = employeeFormLoader.load();
        PathChoose = choosePathLoader.load();
        resolveRequest = RRLoader.load();
        export = exportLoader.load();
        request = requestLoader.load();
        login = loginLoader.load();
        verification = verificationLoader.load();
        timeout = timeoutLoader.load();
        analytics = analyticsLoader.load();
        scale = scaleLoader.load();
        color = colorLoader.load();

        admin.setOnKeyPressed(fireKey);
        analytics.setOnKeyPressed(fireKey);
        adminRequest.setOnKeyPressed(fireKey);
        adminEmployee.setOnKeyPressed(fireKey);
        employeeF.setOnKeyPressed(fireKey);
        PathChoose.setOnKeyPressed(fireKey);
        adminBacklog.setOnKeyPressed(fireKey);
        resolveRequest.setOnKeyPressed(fireKey);
        export.setOnKeyPressed(fireKey);
        request.setOnKeyPressed(fireKey);
        login.setOnKeyPressed(fireKey);
        login.setOnKeyPressed(loginconfirmKey);
        employeeF.setOnKeyPressed(employeeformconfirmKey);
        scale.setOnKeyPressed(fireKey);




        verificationController = verificationLoader.getController();
        adminScreenController = adminLoader.getController();
        adminRequestController = adminRequestLoader.getController();
        adminEmployeeController = adminEmployeeLoader.getController();
        adminBacklogController = adminBacklogLoader.getController();
        analyticsController = analyticsLoader.getController();
        employeeFormController = employeeFormLoader.getController();
        choosePathController = choosePathLoader.getController();
        requestScreenController = RRLoader.getController();
        requestController = requestLoader.getController();
        loginScreenController = loginLoader.getController();
        timeoutController = timeoutLoader.getController();
        scaleController = scaleLoader.getController();
        colorController = colorLoader.getController();

        requestScreenController.setAttributes(adminRequestController);
        adminRequestController.setAttributes(requestScreenController);
        adminEmployeeController.setAttributes(employeeFormController);
        loginScreenController.setAttributes(employeeFormController);

        adminScene = new Scene(admin);

        loadedAdminRequests = true;

        timeout.setOnKeyPressed(timeoutconfirmKey);
        verification.setOnKeyPressed(verificationconfirmKey);

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

        medicine.setOnKeyPressed(medicineconfirmKey);
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

        flower.setOnKeyPressed(flowerconfirmKey);
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

        religious.setOnKeyPressed(religiousconfirmKey);
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

        externalTransport.setOnKeyPressed(exttransportconfirmKey);

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

        intTransportController = intTransportLoader.getController(); //marcus

        internalTransport.setOnKeyPressed(fireKey);
        internalTransport.setOnKeyPressed(inttransportconfirmKey);

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

        clown.setOnKeyPressed(clownconfirmKey);
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

        sanRequest.setOnKeyPressed(sanitationconfirmKey);
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

        languageSR.setOnKeyPressed(languageconfirmKey);
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

        giftDelivery.setOnKeyPressed(giftconfirmKey);

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

        IT.setOnKeyPressed(itconfirmKey);

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
        System.out.println(App.class.getResource("/light_theme/light.css").toExternalForm());
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
        addNode.setOnKeyPressed(addnodeconfirmKey);
        edit.setOnKeyPressed(nodeeditconfirmKey);

      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void loadTreeView() {
    if (!loadedTreeView) {
      try {
        FXMLLoader treeViewLoader = new FXMLLoader(App.class.getResource("/light_theme/TreeView.fxml"));

        treeView = treeViewLoader.load();

        treeViewController = treeViewLoader.getController();

        treeView.setOnKeyPressed(fireKey);

        loadedTreeView = true;
      }
      catch (IOException e) {
        return;
      }
    }
  }

  public static void setTheme(String theme) {
    if(!loadedTreeView) {
      loadTreeView();
    }
    if(!loadedAdminGraph) {
      loadAdminGraph();
    }
    if(!loadedAdminRequests) {
      loadAdminRequests();
    }
    if(!loadedPathfinding) {
      loadPathfinding();
    }
    if(!loadedIT) {
      loadIT();
    }
    if(!loadedIntTransport) {
      loadIntTransport();
    }
    if(!loadedExtTransport) {
      loadExtTransport();
    }
    if(!loadedClown) {
      loadClown();
    }
    if(!loadedGift) {
      loadGift();
    }
    if(!loadedImages) {
      loadImages();
    }
    if(!loadedLanguage) {
      loadLanguage();
    }
    if(!loadedMedicine) {
      loadMedicine();
    }
    if(!loadedReligious) {
      loadReligious();
    }
    if(!loadedFlower) {
      loadFlower();
    }
    if(!loadedSan) {
      loadSan();
    }
    if(resolveRequest.getStylesheets().contains(theme)){
      resolveRequest.getStylesheets().remove(theme);
      home.getStylesheets().remove(theme);
      login.getStylesheets().remove(theme);
      start.getStylesheets().remove(theme);
      path.getStylesheets().remove(theme);
      admin.getStylesheets().remove(theme);
      security.getStylesheets().remove(theme);
      request.getStylesheets().remove(theme);
      medicine.getStylesheets().remove(theme);
      adminRequest.getStylesheets().remove(theme);
      adminEmployee.getStylesheets().remove(theme);
      adminBacklog.getStylesheets().remove(theme);
      export.getStylesheets().remove(theme);
      edit.getStylesheets().remove(theme);
      addNode.getStylesheets().remove(theme);
      adminNode.getStylesheets().remove(theme);
      weather.getStylesheets().remove(theme);
      IT.getStylesheets().remove(theme);
      externalTransport.getStylesheets().remove(theme);
      internalTransport.getStylesheets().remove(theme);
      pathFindText.getStylesheets().remove(theme);
      religious.getStylesheets().remove(theme);
      employeeF.getStylesheets().remove(theme);
      flower.getStylesheets().remove(theme);
      clown.getStylesheets().remove(theme);
      sanRequest.getStylesheets().remove(theme);
      languageSR.getStylesheets().remove(theme);
      giftDelivery.getStylesheets().remove(theme);
      PathChoose.getStylesheets().remove(theme);
      verification.getStylesheets().remove(theme);
      timeout.getStylesheets().remove(theme);
      treeView.getStylesheets().remove(theme);
      analytics.getStylesheets().remove(theme);
      scale.getStylesheets().remove(theme);
      color.getStylesheets().remove(theme);
      homeScene.getStylesheets().remove(theme);
      startScene.getStylesheets().remove(theme);
      pathScene.getStylesheets().remove(theme);
      adminScene.getStylesheets().remove(theme);
      adminNodeScene.getStylesheets().remove(theme);
    }
    resolveRequest.getStylesheets().add(theme);
    home.getStylesheets().add(theme);
    login.getStylesheets().add(theme);
    start.getStylesheets().add(theme);
    path.getStylesheets().add(theme);
    admin.getStylesheets().add(theme);
    security.getStylesheets().add(theme);
    request.getStylesheets().add(theme);
    medicine.getStylesheets().add(theme);
    edit.getStylesheets().add(theme);
    adminRequest.getStylesheets().add(theme);
    adminEmployee.getStylesheets().add(theme);
    adminBacklog.getStylesheets().add(theme);
    export.getStylesheets().add(theme);
    addNode.getStylesheets().add(theme);
    adminNode.getStylesheets().add(theme);
    fire.getStylesheets().add(theme);
    weather.getStylesheets().add(theme);
    IT.getStylesheets().add(theme);
    externalTransport.getStylesheets().add(theme);
    internalTransport.getStylesheets().add(theme);
    pathFindText.getStylesheets().add(theme);
    religious.getStylesheets().add(theme);
    employeeF.getStylesheets().add(theme);
    flower.getStylesheets().add(theme);
    clown.getStylesheets().add(theme);
    sanRequest.getStylesheets().add(theme);
    languageSR.getStylesheets().add(theme);
    giftDelivery.getStylesheets().add(theme);
    PathChoose.getStylesheets().add(theme);
    verification.getStylesheets().add(theme);
    timeout.getStylesheets().add(theme);
    treeView.getStylesheets().add(theme);
    analytics.getStylesheets().add(theme);
    scale.getStylesheets().add(theme);
    color.getStylesheets().add(theme);
    homeScene.getStylesheets().add(theme);
    startScene.getStylesheets().add(theme);
    pathScene.getStylesheets().add(theme);
    adminScene.getStylesheets().add(theme);
    adminNodeScene.getStylesheets().add(theme);
  }

  public static void addToPath(Node e) {//Adds a javaFX node to the pathfinding pane
    path.getChildren().add(e);
  }

  public static void removeFromPath(Node e) { //Removes a javaFX node from the pathfinding pane
    path.getChildren().remove(e);
  }
}
