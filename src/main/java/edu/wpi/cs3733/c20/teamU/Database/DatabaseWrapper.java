package edu.wpi.cs3733.c20.teamU.Database;


import edu.wpi.cs3733.c20.teamU.Administration.Account;
import edu.wpi.cs3733.c20.teamU.Administration.Colors;
import edu.wpi.cs3733.c20.teamU.Administration.UserBacklog;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;

import javax.xml.crypto.Data;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseWrapper {
//  private Database database;
//  private Edge edge;
//  private Node node;
//  private NodesDatabase nodeDB;
//  private ServiceDatabase serviceDB;

//  public DatabaseWrapper() {
//    database = new Database();
//    // edge = new Edge();
//    // node = new Node();
//    nodeDB = new NodesDatabase();
//    serviceDB = new ServiceDatabase();
//  }

  //EDGE CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  public static Edge createEdge(Node startNode, Node endNode, int dist, String ID){
      Edge ed = new Edge(startNode, endNode, dist, ID);
      return ed;
  }

  private static NodesDatabase graph = NodesDatabase.getGraph();

  //assuming that the protected methods will still work outside of this wrapper if not add them.
    public static Node edgeGetStart(Edge edge) { return edge.getStart();}
    public static Node edgeGetEnd(Edge edge) { return edge.getEnd();}
    public static String edgeGetStartID(Edge edge) {
    return edge.getStartID();
  }
    public static String edgeGetEndID(Edge edge) { return edge.getEndID();}
    public static int edgeGetDist(Edge edge) { return edge.getDist();}
    public static String edgeGetID(Edge edge) {return edge.getID();}

    //NODE CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static  Node createNode(int xPos, int yPos, String ID) {
      Node node = new Node(xPos, yPos, ID);
      return node;
    }

    public static Node createNode(String nodeID, int xcoord, int ycoord, int floor, String building, String nodeType, String longName, String shortName){
      Node node = new Node(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName);
      return node;
    }

    public static boolean nodeEqualsNode(Object obj, Node node) {
      return node.equals(obj);
    }

    public static String nodeToString(Node node){
      return node.getID();
    }

    public static String getNodeID(Node node){
      return  node.getID();
    }




//DATABASE CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  /**
   * Initializes every database table according to the CSV's either found in the jar file or
   * externally in DatabaseBackup
   *
   */
  public static void Initializer() {
    Database.UDBInitializer();
  }

  /**
   * Generate a csv file in a given path from the table
   *
   * @param stmt statement
   * @param tableName the table to generate csv from
   * @param path the path to store the csv (if null, store in /DatabaseBackup)
   * @return true if it successfully generates the help
   */
  public static boolean createCSV(Statement stmt, String tableName, String path) {
    return (Database.CreateCSV(stmt, tableName, path));
  }

  public static void getAccounts(ArrayList<Account> accounts){
    Database.getAccounts(accounts);
  }

  /**
   * create a new user or edit and existing one
   * @param username username for login (PK)
   * @param password password for login
   * @param firstName FirstName
   * @param lastName LastName
   * @param position Position: one of ('ADMIN' 'MEDIC' ...)
   * @return boolean if loginSR is updated or edited
   */
  public static boolean addLoginSR(String username, String password, String firstName, String lastName, String position, String number){
    return (Database.addLoginSR(username, password, firstName, lastName, position, number));
  }

  /**
   * give the username as a string and it will return an array list with the following strings in this order
   * username, password, firstName, lastName, position
   * @param username used as the PK
   * @return returns arrayList of strings (U,P,F,L,P)
   */
  public static Account getLoginSR(String username){
    return (Database.getLoginSR(username));
  }

  /**
   * Deletes a row from the database
   * @param username give unique Username for deletion
   * @return returns true if row is deleted
   */
  public static boolean delLoginSR(String username){
    return (Database.delLoginSR(username));
  }

  /**
   * Print a given database table
   *
   * @param stmt Statement (sql)
   * @param tableName the name of the table you wish to print
   * @return true if method was able to successfully print table
   */
  public static boolean printTable(Statement stmt, String tableName) {
    return (Database.printTable(stmt, tableName));
  }

  /**
   * Creates the HashMap for edges from the table
   *
   * @param eHM the HashMap to store all the edge
   * @param nHM the HashMap of nodes to get all the nodes object
   */
  public static void getEdges(HashMap<String, Edge> eHM, HashMap<String, Node> nHM) {
    Database.getEdges(eHM, nHM);
  }

  /**
   * Fill in the HashMap with nodes from the table
   *
   * @param nHM the HashMap to store the nodes
   */
  public static void getNodes(HashMap<String, Node> nHM) {
    Database.getNodes(nHM);
  }

  /**
   * Creates a new tuple in the edges database for the given information
   *
   * @param startID first nodeID for the table
   * @param endID second nodeID for the table
   * @return true if the tuple was able to be created
   */
  public static boolean addEdge(String startID, String endID) {
    boolean r = (Database.addEdge(startID, endID));
    updateGraph();
    return r;
  }

  /**
   * Creates a new tuple in the nodes database using the given info
   *
   * @param nodeID Primary Key nodeID
   * @param xcoord x coordinate of the graph
   * @param ycoord y coordinate of the graph
   * @param floor floor number
   * @param building string of building name
   * @param nodeType type of area ("HALL, DEPT, STAI...)
   * @param longName full name of the location
   * @param shortName short abv. name of location
   * @return true if the tuple was able to be created
   */
  public static boolean addNode( String nodeID, int xcoord, int ycoord, int floor, String building, String nodeType, String longName, String shortName) {
    boolean r = (Database.addNode(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName));
    updateGraph();
    return r;
  }

  /**
   * Deletes the given edge tuple using edgeID
   *
   * @param edgeID Primary Key
   * @return true if tuple was deleted
   */
  public static boolean delEdge(String edgeID) {
    boolean r = (Database.delEdge(edgeID));
    updateGraph();
    return r;
  }

  /**
   * Deletes the given node tuple given the nodeID
   *
   * @param nodeID the Primary key of the nodes table
   * @return true if tuple was deleted
   */
  public static boolean delNode(String nodeID) {
    boolean r = (Database.delNode(nodeID));
    updateGraph();
    return r;
  }

  /**
   * Fill in the HashMap for all Services from the table
   * @param servicesList the ArrayList to store the services
   */
  public static void getServices(ArrayList<Service> servicesList, String user) {
    Database.getServices(servicesList, user);
  }

  public static void getFinishedServices(ArrayList<Service> servicesList, String user) {
    Database.getFinishedServices(servicesList, user);
  }

    /**
     * Check the if the username and password are valid for login
     * @param inputUsername username
     * @param inputPassword password
     * @return true if they match in the table
     */
    public static Account checkCred(String inputUsername, String inputPassword){
        return (Database.checkCred(inputUsername, inputPassword));
    }


    /**
     *
     * Edits an existing Node tuple
     * @param nodeIDN Primary Key
     * @param xcoordN x coordinate
     * @param ycoordN y coordinate
     * @param floorN floor number
     * @param buildingN building name
     * @param nodeTypeN type of area ('HALL' 'STAI' ...)
     * @param longNameN long string name
     * @param shortNameN short string name
     * @return true if tuple was able to be edited
     */
    public static boolean editNode(String nodeIDN, int xcoordN, int ycoordN, int floorN, String buildingN, String nodeTypeN, String longNameN, String shortNameN ) {
        boolean r = (Database.editTuple(nodeIDN, xcoordN, ycoordN, floorN, buildingN, nodeTypeN, longNameN, shortNameN));
        updateGraph();
        return r;
    }

    /**
     *
     * Edits an existing Edge tuple
     * @param edgeIDN Primary Key
     * @param startNodeN Foreign key
     * @param endNodeN Foreign key
     * @return true if tuple was able to be edited
     */
    public static boolean editEdge(String edgeIDN, String startNodeN, String endNodeN ) {
        boolean r = (Database.editEdge(edgeIDN, startNodeN, endNodeN));
        updateGraph();
        return r;
    }

    public static boolean addDestination(String startNode, String destNode){
      return Database.addDestination(startNode, destNode);
    }

  /**
   * adds a row to the userBacklog table
   * @param username input username
   * @param serviceType service type completed
   * @param operations operations string
   * @param info general info string
   * @return true if tuple was created
   *
   * Optional: insert Date and Time if you dont want time/date to be auto implemented
   */
    public static boolean addUserBacklog(String username, String serviceType, String operations, String info){
      return Database.addUserBacklog(username, serviceType, operations, info);
    }
    public static boolean addUserBacklog(String username, String dateCompleted, String timeCompleted, String serviceType, String operations, String info){
      return Database.addUserBacklog(username, dateCompleted, timeCompleted, serviceType, operations, info);
    }

    /**
     * return all values in userBacklog table
     * @return returns array list of userbacklog class. containing all items from database
     */
    public static void getAllUserBacklog( ArrayList<UserBacklog> a){
          Database.getAllUserBacklog(a);
      }

    /**
     * add a color (hex values) to the list
     * @param colorName unique name of color list
     * @param color1
     * @param color2
     * @param color3
     * @param color4
     * @param color5
     * @return true if color list was added
     */
      public static boolean addColor(String colorName, String color1, String color2, String color3, String color4, String color5, String textColor1, String textColor2){
          return Database.addColor(colorName, color1, color2, color3, color4, color5, textColor1, textColor2);
      }

      /**
       * get ALL colors in DB
       * @return returns Array list of all colors (check colors class)
       */
      public static ArrayList<Colors> getAllColors(){
            return Database.getAllColors();
      }

      /**
       * get spesific color
       * @param colorTheme "colorType" of spesific color
       * @return returns Colors for given color
       */
      public static Colors getColor(String colorTheme){
            return Database.getColor(colorTheme);
      }

      /**
       * Delete a row from ColorsDB
       * @param colorName  unique name of colors
       * @return true if colors were deleted
       */
      public static boolean delColor(String colorName){
                return Database.delColor(colorName);
          }

  /**
   * Get an array list of Nodes for a given time frame
   * @param dateType ONE OF 3 types "day" "month" "year" (selects what value is most important)
   * @param day if dateType is day eneter date as shown: "01" | "12" |  "03" ...
   * @param month if date type is day or month enter month as: "01" ...
   * @param year enter year as: "2000" ...
   * @return returns an array list of nodes for a given time frame
   *  Examples:
   *    DatabaseWrapper.getDestinationAmount("day", "31", "12", "1999") - for just one day
   *    DatabaseWrapper.getDestinationAmount("month", null, "12", "1999") - for the whole month of dec in 1999
   *    DatabaseWrapper.getDestinationAmount("year", null, null, "1999") - for the whole year of 1999
   */
  public static ArrayList<Node> getDestinationAmount(String dateType, String day, String month, String year){
        return Database.getDestinationAmount(dateType, day, month, year);
      }

  /**
   * Function takes in a date range and returns an array list of nodes for that given time frame (includes that day)
   * @param startDate Starting date ( stndrd date format) this is the earlier date
   * @param endDate Ending date this is the later date
   * @return returns an array list of nodes
   * EX:
   *  DatabaseWrapper.getDestinationAmountRange("3/2/2020", "3/2/2020");
   *  DatabaseWrapper.getDestinationAmountRange("2/22/2020", "3/22/2020");
   */
  public static ArrayList<Node> getDestinationAmountRange(String startDate, String endDate){
    return Database.getDestinationAmountRange(startDate, endDate);
  }


  /**
   * ALL THESE FUNCTIONS BEHAVE THE SAME WAY (self explanatory)
   * @return arraylist of nodes for the given time frame (day week month year) goes based off of current date
   * if you want to get the past (day week month or year use these functions)
   */
  public static ArrayList<Node> getDARToday(){
    return Database.getDARToday();
  }
  public static ArrayList<Node> getDARLastWeek(){
    return Database.getDARLastWeek();
  }
  public static ArrayList<Node> getDARLastMonth(){
    return Database.getDARLastMonth();
  }
  public static ArrayList<Node> getDARLastYear(){
    return Database.getDARLastYear();
  }

  /**
   *  Get the number of employees that have a certian job type
   * @param EmployeeType String of job type (only "ADMIN" , "MEDIC" , "CLOWN", "ITRAN" ...)
   * @return returns int number of current employees with that job type
   * ex:
   *    DatabaseWrapper.getEmployeeCount("ETRAN");
   */
  public static int getEmployeeCount(String EmployeeType){
    return Database.getEmployeeCount(EmployeeType);
  }

  //add a type (auto converts to CAPS)
  public static boolean addType(String typeName){
    return Database.addType(typeName);
  }

  //remove a type (from every DB)
  public static boolean delType(String typeName){
    return Database.delType(typeName);
  }

  /**
   * @return returns a list of the Types (Strings)
   */
  public static ArrayList<String> getTypes(){
    return Database.getTypes();
  }

  /**
   * add a value to Database
   * @param name String less than 5 char (will be set to all caps)
   * @param picture String of path to picture (either added or default)
   * @return true if added
   */
  public static boolean addDatabase(String name, String picture){
    return Database.addDatabase(name, picture);
  }

  // SERVICE DATABASE
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

  /**
   * takes in a date finds the number of requests for a given range
   *
   * @param serviceRequestType (MEDIC, INTEC, CLOWN ...)
   * @param dateType either "day" "month" or "year"
   * @param day if dateType is day eneter date as shown: "01"
   * @param month if date type is day or month enter month as: "01"
   * @param year enter year as: "2000"
   * @return Int corresponding to the number of rows in that date time frame be sure to input
   *     correct strings otherwise the method wont work at all. all single digit numbers require
   *     leading 0 for 2 places | strings inputted into day or month dont matter if you chose a
   *     dateType larger than it. EX:
   *     getServiceRequestAmount("SECUR", "day", "23", "02","2020");
   *     getServiceRequestAmount("SECUR", "month", "00", "02", "2020");
   *     getServiceRequestAmount("SECUR", "year", "00", "00", "2020");
   */
  public static int getServiceRequestAmount(String serviceRequestType, String dateType, String day, String month, String year) {
      return ServiceDatabase.getServiceRequestAmount(serviceRequestType, dateType, day, month, year);
  }

  /**
   * Function takes in a date range and a type of service request and displays the number of times a request happened in that time frame
   * @param serviceRequestType String ONLY REQUEST TYPES ("SECUR", "MEDIC" ...)
   * @param startDate date format only! String for the earlier date
   * @param endDate date format only! String of the date that is later in time.
   * @return int ( number of service requests for that time frame )
   * Ex: DatabaseWrapper.getServiceRequestAmountRange("SECUR", "2/22/2020", "3/1/2020");
   */
  public static int getServiceRequestAmountRange(String serviceRequestType, String startDate, String endDate){
    return ServiceDatabase.getServiceRequestAmountRange(serviceRequestType, startDate, endDate);
  }

  /**
   * ALL THESE FUNCTIONS BEHAVE THE SAME WAY (self explanatory)
   * @param serviceRequestType STRING ONLY SERVICE REQUEST KEYS ALLOWED ("MEDIC" , "SECUR" ..)
   * @return Int that shows the number of instances of the given service request
   * if you want to get the past (day week month or year use these functions)
   */
  public static int getSRAToday(String serviceRequestType){
    return ServiceDatabase.getSRAToday(serviceRequestType);
  }
  public static int getSRALastWeek(String serviceRequestType){
    return ServiceDatabase.getSRALastWeek(serviceRequestType);
  }
  public static int getSRALastMonth(String serviceRequestType){
    return ServiceDatabase.getSRALastMonth(serviceRequestType);
  }
  public static int getSRALastYear(String serviceRequestType){
    return ServiceDatabase.getSRALastYear(serviceRequestType);
  }

  //USED THE SAME WAY AS ABOVE FUNCTIONS
  public static int getServiceFinishedAmount(String serviceRequestType, String dateType, String day, String month, String year){
    return ServiceDatabase.getServiceFinishedAmount(serviceRequestType, dateType, day, month, year);
  }
  public static int getServiceFinishedAmountRange(String serviceRequestType, String startDate, String endDate){
    return ServiceDatabase.getServiceFinishedAmountRange(serviceRequestType, startDate, endDate);
  }
  public static int getSFinishedAToday(String serviceRequestType){
    return ServiceDatabase.getSFinishedAToday(serviceRequestType);
  }
  public static int getSFinishedALastWeek(String serviceRequestType){
    return ServiceDatabase.getSFinishedALastWeek(serviceRequestType);
  }
  public static int getSFinishedALastMonth(String serviceRequestType){
    return ServiceDatabase.getSFinishedALastMonth(serviceRequestType);
  }
  public static int getSFinishedALastYear(String serviceRequestType){
    return ServiceDatabase.getSFinishedALastYear(serviceRequestType);
  }

  /**
   * Add a value to the Servie Finished DatabaseTable
   * @param timeFinished String of DATE
   * @param reqType String of type (ie MEDIC)
   * @param completedBy string cof DATE
   * @param info String of any information
   * @return returns true if value was added
   */
    public static boolean serviceFinishedAdd(String timeFinished, String reqType, String completedBy, String info){
        return (ServiceDatabase.serviceFinishedAdd(timeFinished, reqType, completedBy, info));
    }

  /**
   * add a value to the security table (for current time)
   * @return returns true if value was added
   */
    public static boolean securitySRAdd(){
        return (ServiceDatabase.securitySRAdd());
    }

  /**
   * add a value to the Medical request table
   * @param patentFirstName String of first name
   * @param patentLastName String of last name
   * @param drugName String of Drug
   * @param frequency String of reqency
   * @param deliveryMethod String of delm (ie: IV )
   * @param comment String of any comment made by requester
   * @return returns true if added correctly
   */
    public static boolean medicineSRAdd(String patentFirstName, String patentLastName, String drugName, String frequency, String deliveryMethod, String comment){
        return (ServiceDatabase.medicineSRAdd(patentFirstName, patentLastName, drugName,frequency, deliveryMethod, comment));
    }

  /**
   * Delete a value from the Security table
   * @param reqID ID value from the table (primary key)
   * @param adminsName String of the name of the admin user that completed the task
   * @return returns true if value was deleted
   */
    public static boolean securitySRDel(int reqID, String adminsName,String user){
        return (ServiceDatabase.securitySRDel(reqID, adminsName));
    }

  /**
   * Delete a value from the medical table
   * @param reqID ID value from table (PK)
   * @param adminsName String of admin user that competed task
   * @return returns true if it was deleted
   */
    public static boolean medicineSRDel(int reqID, String adminsName, String user) {
        return (ServiceDatabase.medicineSRDel(reqID, adminsName, user));
    }


    //Use functions to update all database tables: "[Name]SR, ServiceRequest, ServiceFinished"
    //ex input: languageSRAdd("Chalmers", "Marcus", "Chinese");
    //ex input: languageSRDel(2, "adminUsername", "LANGE");
    public static boolean languageSRAdd(String patentLastName, String patentFirstName, String language, String location){
      return (ServiceDatabase.languageSRAdd(patentLastName, patentFirstName, language, location));
    }

    public static boolean languageSRDel(int reqID, String adminsName, String user) {
      return ServiceDatabase.languageSRDel(reqID, adminsName, user);
    }

    public static boolean intTransportSRAdd(String patentLastName, String patentFirstName, String startLocation, String endLocation, String equipment){
      return ServiceDatabase.intTransportSRAdd(patentLastName, patentFirstName, startLocation, endLocation, equipment);
    }

  public static boolean extTransportSRAdd(String patentLastName, String patentFirstName, String destination, String departureTime, String departureDate, int nPassengers){
      return ServiceDatabase.extTransportSRAdd(patentLastName, patentFirstName, destination, departureTime, departureDate, nPassengers);
  }

  public static boolean deliverySRAdd(String patentLastName, String patentFirstName, String gift, String room){
      return ServiceDatabase.deliverySRAdd(patentLastName, patentFirstName, gift, room);
  }

  public static boolean ClownDeliverySRAdd(String location, int nClowns, String recipientName, String deliveryDate){
      return ServiceDatabase.ClownDeliverySRAdd(location, nClowns, recipientName, deliveryDate);
  }

  public static boolean FlowersSRAdd(String lastName, String firstName, boolean roses, boolean tulips, boolean lilies, String occasion, String deliveryDate, String giftNote, String room){
      return ServiceDatabase.FlowersSRAdd(lastName,firstName,roses,tulips,lilies,occasion,deliveryDate,giftNote,room);
  }

  public static boolean ITSRAdd(String patentLastName, String patentFirstName, String helpType, String comments){
      return ServiceDatabase.ITSRAdd(patentLastName,patentFirstName,helpType,comments);
  }

  public static boolean ReligionSRAdd(String patentName, String religiousAffiliation, String explanation){
      return ServiceDatabase.ReligionSRAdd(patentName,religiousAffiliation,explanation);
  }

  public static boolean SanitarySRAdd(String service, String location, String nature, String info){
      return ServiceDatabase.SanitarySRAdd(service,location,nature,info);
  }

    public static boolean serviceRequestDel(int reqID, String adminsName, String user, String tableName, String jobType){
      return ServiceDatabase.serviceRequestDel(reqID, adminsName, user, tableName, jobType);
    }

   public static String getCurrentDate(){
      return ServiceDatabase.getCurrentDate();
   }

  //NODESDATABSE CLASS  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  public static NodesDatabase getGraph() {
      return graph; //Other nodesDatabase functions should be accessed through DatabaseWrapper.getGraph().x();
  }
  public static void updateGraph() {
      graph.update();
  }

  public static void getNodesByFloor(ArrayList<Node> nodes, int floor){
      Database.getNodesByFloor(nodes, floor);
  }


  //============

  public static ArrayList<String> getDataAnalytics(ArrayList<Integer> fre, String type){
      return Database.getDataAnalytics(fre, type);
  }

  //GENERATE DATABASE ==============================================================================================================

  public static boolean generateNewDatabase(String dbName, ArrayList<String> dataTypes, String picturePath){
      return GenerateDatabase.generateNewDatabase(dbName, dataTypes, picturePath);
  }

  public static boolean populateNewDatabase(String dbName, ArrayList<String> values){
      return GenerateDatabase.populateNewDatabase(dbName, values);
  }
}
