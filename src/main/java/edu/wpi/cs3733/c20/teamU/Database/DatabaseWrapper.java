package edu.wpi.cs3733.c20.teamU.Database;

import edu.wpi.cs3733.c20.teamU.Database.*;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;

public class DatabaseWrapper {
  private Database database;
  private Edge edge;
  private Node node;
  private NodesDatabase nodeDB;
  private ServiceDatabase serviceDB;

  public DatabaseWrapper() {
    database = new Database();
    // edge = new Edge();
    // node = new Node();
    nodeDB = new NodesDatabase();
    serviceDB = new ServiceDatabase();
  }

  public static Edge createEdge(Node startNode, Node endNode, int dist, String ID){
      Edge ed = new Edge(startNode, endNode, dist, ID);
      return ed;
  }

  public static String getStartID(Edge edge) {
      return edge.getStartID();
  }

  //assuming that the protected methods will still work outside of this wrapper if not add them.
  //public static Node getStart(Edge edge) { return edge.getStart();}
  //public static Node getEnd(Edge edge) { return end;}

    public static String getEndID(Edge edge) { return edge.getEndID();}
    public static int getDist(Edge edge) { return edge.getDist();}
    public static String getID(Edge edge) {return edge.getID();}

    public static  Node createNode(int xPos, int yPos, String ID) {
      Node node = new Node(xPos, yPos, ID);
      return node;
    }

    public static Node createNode(String nodeID, int xcoord, int ycoord, int floor, String building, String nodeType, String longName, String shortName){
      Node node = new Node(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName);
      return node;
    }

    public static boolean equalsNode(Object obj) {

    }
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
    return (Database.addEdge(startID, endID));
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
  public static boolean addNode(
      String nodeID,
      int xcoord,
      int ycoord,
      int floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    return (Database.addNode(
        nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName));
  }

  /**
   * Deletes the given edge tuple using edgeID
   *
   * @param edgeID Primary Key
   * @return true if tuple was deleted
   */
  public static boolean delEdge(String edgeID) {
    return (Database.delEdge(edgeID));
  }

  /**
   * Deletes the given node tuple given the nodeID
   *
   * @param nodeID the Primary key of the nodes table
   * @return true if tuple was deleted
   */
  public static boolean delNode(String nodeID) {
    return (Database.delNode(nodeID));
  }

  /**
   * Fill in the HashMap for all Services from the table
   * @param servicesList the ArrayList to store the services
   */
  public static void getServices(ArrayList<Service> servicesList) {
    Database.getServices(servicesList);
  }

    /**
     * Check the if the username and password are valid for login
     * @param inputUsername username
     * @param inputPassword password
     * @return true if they match in the table
     */
    public static boolean checkCred(String inputUsername, String inputPassword){
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
        return (Database.editTuple(nodeIDN, xcoordN, ycoordN, floorN, buildingN, nodeTypeN, longNameN, shortNameN));
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
        return (Database.editEdge(edgeIDN, startNodeN, endNodeN));
    }

    public static boolean serviceFinishedAdd(String timeFinished, String reqType, String completedBy, String info){
        return (ServiceDatabase.serviceFinishedAdd(timeFinished, reqType, completedBy, info));
    }

    public static boolean securitySRAdd(){
        return (ServiceDatabase.securitySRAdd());
    }

    public static boolean medicineSRAdd(String patentFirstName, String patentLastName, String drugName, String frequency, String deliveryMethod, String comment){
        return (ServiceDatabase.medicineSRAdd(patentFirstName, patentLastName, drugName,frequency, deliveryMethod, comment));
    }

    public static boolean securitySRDel(int reqID, String adminsName){
        return (ServiceDatabase.securitySRDel(reqID, adminsName));
    }

    public static boolean medicineSRDel(int reqID, String adminsName) {
        return (ServiceDatabase.medicineSRDel(reqID, adminsName));
    }


    //Node Class
    public void setWeight(Node node, int newWeight) { setWeight(node, newWeight);}
    public int getX(Node node){ return getX(node); }
    public int getY(Node node){ return getY(node);}
    public int getWeight(Node node){ return getWeight(node);}
    public static String getID(Node node){ return getID(node);}
    public String toString(Node node){ return node.getID(); }
    public int getFloor(Node node) { return getFloor(node);}
    public String getBuilding(Node node) {return getBuilding(node);}
    public String getNodeType(Node node) {return getNodeType(node);}
    public String getLongName(Node node) { return getLongName(node);}
    public String getShortName(Node node) { return getShortName(node);}

    //NodesDatabase Class
    public void addNode(Node n, NodesDatabase ndb) { addNode(n, ndb);}
    public void addEdge(Edge e, NodesDatabase ndb) {addEdge(e,ndb);}
    public void removeNode(String ID, NodesDatabase ndb) { removeNode(ID, ndb);}
    public void removeEdge(String ID, NodesDatabase ndb) { removeEdge(ID, ndb);}
    public void removeNode(Node n, NodesDatabase ndb) { removeNode(n, ndb);}
    public void removeEdge(Edge e, NodesDatabase ndb) { removeEdge(e,ndb);}
    public Node getNode(String ID, NodesDatabase ndb) { return getNode(ID, ndb);}
    public ArrayList<Node> getNodes(NodesDatabase ndb) { return getNodes(ndb);}
    public Edge getEdge(String ID, NodesDatabase ndb) { return getEdge(ID, ndb);}
    public Edge getEdge(Node start, Node end, NodesDatabase ndb) {return getEdge(start, end, ndb);}
    public Edge getEdge(String startID, String endID, NodesDatabase ndb) {return getEdge(startID, endID, ndb);}
    public Node getNode(int xPos, int yPos, NodesDatabase ndb) { return getNode(xPos,yPos,ndb);}
    public boolean hasNode(Node n, NodesDatabase ndb) {return hasNode(n,ndb);}
    public ArrayList<Edge> getNeighbors(Node n, NodesDatabase ndb) {return getNeighbors(n, ndb);}
    public ArrayList<Node> getNeighborNodes(Node n, NodesDatabase ndb) { return getNeighborNodes(n,ndb);}
    public boolean hasNeighbors(Node n, NodesDatabase ndb) {return hasNeighbors(n,ndb);}
    public int cost(Node start, Node end, NodesDatabase ndb) { return cost(start, end, ndb);}
    public int costNoWeight(Node start, Node end, NodesDatabase ndb) { return costNoWeight(start, end,ndb); }
    public boolean hasNode(int xPos, int yPos, NodesDatabase ndb) { return hasNode(xPos, yPos, ndb); }
    public boolean hasNodeInRange(int xPos, int yPos, int rad, NodesDatabase ndb) {return hasNodeInRange(xPos, yPos, rad, ndb); }
    public Node getNodeInRange(int xPos, int yPos, int rad, NodesDatabase ndb) { return getNodeInRange(xPos, yPos, rad, ndb);}
    public void update(NodesDatabase ndb) {update(ndb); }
    public static int dist(int x1, int y1, int x2, int y2) {return dist(x1, y1, x2, y2);}
}