package edu.wpi.cs3733.c20.teamU.Database;


import edu.wpi.cs3733.c20.teamU.Administration.Account;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;

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

  /**
   * create a new user or edit and existing one
   * @param username username for login (PK)
   * @param password password for login
   * @param firstName FirstName
   * @param lastName LastName
   * @param position Position: one of ('ADMIN' 'MEDIC' ...)
   * @return boolean if loginSR is updated or edited
   */
  public static boolean addLoginSR(String username, String password, String firstName, String lastName, String position, String email){
    return (Database.addLoginSR(username, password, firstName, lastName, position, email));
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
      return  (ServiceDatabase.languageSRDel(reqID, adminsName, user));
    }

    public static boolean intTransportSRAdd(String patentLastName, String patentFirstName, String startLocation, String endLocation, String equipment){
      return (ServiceDatabase.intTransportSRAdd(patentLastName, patentFirstName, startLocation, endLocation, equipment));
    }

  public static boolean extTransportSRAdd(String patentLastName, String patentFirstName, String destination, String departureTime, String departureDate, int nPassengers){
      return (ServiceDatabase.extTransportSRAdd(patentLastName, patentFirstName, destination, departureTime, departureDate, nPassengers));
  }

  public static boolean deliverySRAdd(String patentLastName, String patentFirstName, String gift, String room){
      return (ServiceDatabase.deliverySRAdd(patentLastName, patentFirstName, gift, room));
  }

  public static boolean ClownDeliverySRAdd(String location, int nClowns, String recipientName, String deliveryDate){
      return (ServiceDatabase.ClownDeliverySRAdd(location, nClowns, recipientName, deliveryDate));
  }

  public static boolean FlowersSRAdd(String lastName, String firstName, boolean roses, boolean tulips, boolean lilies, String occasion, String deliveryDate, String giftNote, String room){
      return (ServiceDatabase.FlowersSRAdd(lastName,firstName,roses,tulips,lilies,occasion,deliveryDate,giftNote,room));
  }

  public static boolean ITSRAdd(String patentLastName, String patentFirstName, String helpType, String comments){
      return (ServiceDatabase.ITSRAdd(patentLastName,patentFirstName,helpType,comments));
  }

  public static boolean ReligionSRAdd(String patentName, String religiousAffiliation, String explanation){
      return (ServiceDatabase.ReligionSRAdd(patentName,religiousAffiliation,explanation));
  }

  public static boolean SanitarySRAdd(String service, String location, String nature, String info){
      return (ServiceDatabase.SanitarySRAdd(service,location,nature,info));
  }

    public static boolean serviceRequestDel(int reqID, String adminsName, String user, String tableName, String jobType){
      return (ServiceDatabase.serviceRequestDel(reqID, adminsName, user, tableName, jobType));
    }


  //NODESDATABSE CLASS  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  public static NodesDatabase getGraph() {
      return graph; //Other nodesDatabase functions should be accessed through DatabaseWrapper.getGraph().x();
  }
  public static void updateGraph() {
      graph.update();
  }


}
