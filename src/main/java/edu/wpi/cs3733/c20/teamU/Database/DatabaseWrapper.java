package edu.wpi.cs3733.c20.teamU.Database;


import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;

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

    //Nodes class:
    public static int nodeGetFloor(Node node) { return node.getFloor();}
    public static String nodeGetBuilding(Node node) {return node.getBuilding();}
    public static String nodeGetNodeType(Node node) {return node.getNodeType();}
    public static String nodeGetLongName(Node node) { return node.getLongName();}
    public static String nodeGetShortName(Node node) { return node.getShortName();}
    public static int nodeGetXCoord(Node node) {return node.getX();}
    public static int nodeGetYCoord(Node node) {return node.getY();}


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
  public static boolean addNode( String nodeID, int xcoord, int ycoord, int floor, String building, String nodeType, String longName, String shortName) {
    return (Database.addNode(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName));
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

  //SERVICE DATABASE ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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
    public static boolean securitySRDel(int reqID, String adminsName){
        return (ServiceDatabase.securitySRDel(reqID, adminsName));
    }

  /**
   * Delete a value from the medical table
   * @param reqID ID value from table (PK)
   * @param adminsName String of admin user that competed task
   * @return returns true if it was deleted
   */
    public static boolean medicineSRDel(int reqID, String adminsName) {
        return (ServiceDatabase.medicineSRDel(reqID, adminsName));
    }


  //NODESDATABSE CLASS  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  //add node
    public static void nodeDatabaseAddNode(Node n, NodesDatabase ndb) { ndb.addNode(n,ndb);}
  //add edge
    public static void nodeDatabaseAddEdge(Edge e, NodesDatabase ndb) {ndb.addEdge(e, ndb);}
  //remove node
    public static void nodeDatabaseRemoveNode(String ID, NodesDatabase ndb) { ndb.removeNode(ID, ndb);}
  //remove edge
    public static void nodeDatabaseRemoveEdge(String ID, NodesDatabase ndb) { ndb.removeEdge(ID, ndb);}
  // ""
    public static void nodeDatabaseRemoveNode(Node n, NodesDatabase ndb) { ndb.removeNode(n, ndb);}
  //  ""
    public static void nodeDatabaseRemoveEdge(Edge e, NodesDatabase ndb) { ndb.removeEdge(e,ndb);}
  //get node id
    public static Node nodeDatabaseGetNode(String ID, NodesDatabase ndb) { return ndb.getNode(ID, ndb);}
  //get nodes
    public static ArrayList<Node> nodeDatabaseGetNodes(NodesDatabase ndb) { return ndb.getNodes(ndb);}
  //get edge id
    public static Edge nodeDatabaseGetEdge(String ID, NodesDatabase ndb) { return ndb.getEdge(ID, ndb);}
  //get edge value type
    public static Edge nodeDatabaseGetEdge(Node start, Node end, NodesDatabase ndb) {return ndb.getEdge(start, end, ndb);}
  //get edge start and end it?
    public static Edge nodeDatabaseGetEdge(String startID, String endID, NodesDatabase ndb) {return ndb.getEdge(startID, endID, ndb);}
  //get node pos
    public static Node nodeDatabaseGetNode(int xPos, int yPos, NodesDatabase ndb) { return ndb.getNode(xPos,yPos,ndb);}
  //check if node is there
    public static boolean nodeDatabaseHasNode(Node n, NodesDatabase ndb) {return ndb.hasNode(n,ndb);}
  //get neighbors Edge
    public static ArrayList<Edge> nodeDatabaseGetNeighbors(Node n, NodesDatabase ndb) {return ndb.getNeighbors(n, ndb);}
  //get nodes (neighbors)
    public static ArrayList<Node> nodeDatabaseGetNeighborNodes(Node n, NodesDatabase ndb) { return ndb.getNeighborNodes(n,ndb);}
  //check if there are neighbors
    public static boolean nodeDatabaseHasNeighbors(Node n, NodesDatabase ndb) {return ndb.hasNeighbors(n,ndb);}
  //show weight start??
    public static int nodeDatabaseCost(Node start, Node end, NodesDatabase ndb) { return ndb.cost(start, end, ndb);}
  //no weight start ??
    public static int nodeDatabaseCostNoWeight(Node start, Node end, NodesDatabase ndb) { return ndb.costNoWeight(start, end,ndb); }
  //if db has node
    public static boolean nodeDatabaseHasNode(int xPos, int yPos, NodesDatabase ndb) { return ndb.hasNode(xPos, yPos, ndb); }
  //has a node in range x.y
    public static boolean nodeDatabaseHasNodeInRange(int xPos, int yPos, int rad, NodesDatabase ndb) {return ndb.hasNodeInRange(xPos, yPos, rad, ndb); }
  //get that node in range
    public static Node nodeDatabaseGetNodeInRange(int xPos, int yPos, int rad, NodesDatabase ndb) { return ndb.getNodeInRange(xPos, yPos, rad, ndb);}
  //update DB
    public static void nodeDatabaseUpdate(NodesDatabase ndb) { ndb.update(ndb); }
  //get distance between two points
    public static int nodeDatabaseDist(int x1, int y1, int x2, int y2) {return NodesDatabase.dist(x1, y1, x2, y2);}
}
