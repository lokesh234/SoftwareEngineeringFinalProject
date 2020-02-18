package edu.wpi.cs3733.c20.teamU.Database;

import edu.wpi.cs3733.c20.teamU.Navigation.Pathfinder;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import lombok.NoArgsConstructor;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


@NoArgsConstructor
public class Database {
    /**
     * Initializes every database table according to the CSV's either found in the jar file or
     * externally in DatabaseBackup
     *
     */
    public static void UDBInitializer(){
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        //Try statement sets up AD Register to ensure UDB can be created
        System.out.println("== Apache Derby Register Complete ==");

        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //connect to UDB

            dropTable(stmt, "MapEdgesU");
            dropTable(stmt, "MapNodesU");
            dropTable(stmt, "LoginDB");
            dropTable(stmt, "ServiceFinished");
            dropTable(stmt, "SecuritySR");
            dropTable(stmt, "MedicineSR");
            dropTable(stmt, "ServiceRequest");
            // drops database tables if they currently exist
            System.out.println("Dropped tables");

            createNodeTable(stmt, "MapNodesU");
            createEdgesTable(stmt, "MapEdgesU");
            createLoginTable(stmt, "LoginDB");
            createServiceRequestTable(stmt,"ServiceRequest");
            createServiceFinishedTable(stmt, "ServiceFinished");
            createMedicineSRTable(stmt, "MedicineSR");
            createSecuritySRTable(stmt, "SecuritySR");
            populateServiceRequestTable(stmt, "ServiceRequest");
            // Creates tables again or for the first time
            System.out.println("Created Tables");

            printTable(stmt, "MapNodesU");
            printTable(stmt, "MapEdgesU");
            printTable(stmt, "LoginDB");
            printTable(stmt, "ServiceRequest");
            printTable(stmt, "ServiceFinished");
            printTable(stmt, "MedicineSR");
            printTable(stmt, "SecuritySR");
            //print tables to test

            System.out.println("== Apache Derby Databases Established! ==");
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    /**
     * @param csvName
     * TODO：finish commenting
     * @return
     */
    private static boolean getFileDir(String csvName){
        String directoryName = "DatabaseBackup";

        File directory = new File(directoryName);
        File file = new File(directoryName + "/" + csvName);
        if (! directory.exists()){
            directory.mkdir();
            // if there isn't a dir create one and then return false
            return false;
        } else if(! file.exists()){
                return false;
        } else{
            return true;
        }
    }
    //csvName ex: "MapNodes.csv" returns true if folder and csv exist
    private static BufferedReader getBR(String tableName){
        BufferedReader br;
        try{
        if (getFileDir(tableName + ".csv")){
            String APath = System.getProperty("user.dir");
            String csvFile = APath + "/DatabaseBackup/" + tableName + ".csv";
            //String csvFile = "DatabaseBackup/" + tableName + ".csv";
            br = new BufferedReader(new FileReader(csvFile));
        }else{
            InputStream csvStream = Database.class.getResourceAsStream("/csv_files/" + tableName + ".csv");
            br = new BufferedReader(new InputStreamReader(csvStream));
        }
        return br;
        }catch (Exception e){
        }
        return null;
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void dropTable(Statement stmt, String tableName) {
        try{
            String sqldel = "DROP TABLE " + tableName;
            stmt.executeUpdate(sqldel);
        }
        catch(SQLException SQLExcept){

        }
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void createNodeTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (nodeID VARCHAR(10), xcoord INTEGER, ycoord INTEGER, floor INTEGER, building VARCHAR(20), nodeType VARCHAR(4), longName VARCHAR(80), shortName VARCHAR(30), teamAssigned VARCHAR(6), PRIMARY KEY (nodeID), " +
                    "CONSTRAINT NT_CK CHECK (nodeType in ('DEPT','HALL','CONF','REST','STAI','ELEV','LABS','INFO','CONF','EXIT','RETL','SERV')))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
           // InputStream csvFile = databaseLocation(getFileDir("MapNodesU.csv"),"MapNodesU.csv");

            //InputStream csvFile = Database.class.getResourceAsStream("/csv_files/MapNodesU.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row

            try {
                BufferedReader br = getBR(tableName);

            //try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {

                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String nodeID = csvString[0];
                        int xcoord = Integer.parseInt(csvString[1]);
                        int ycoord = Integer.parseInt(csvString[2]);
                        int floor = Integer.parseInt(csvString[3]);
                        String building = csvString[4];
                        String nodeType = csvString[5];
                        String longName = csvString[6];
                        String shortName = csvString[7];
                        String teamAssigned = csvString[8];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + nodeID + "', " + xcoord + ", " + ycoord + ", " + floor + ", '" + building + "', '" + nodeType + "', '" + longName + "', '" + shortName + "', '" + teamAssigned + "')");
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
        CreateCSV(stmt, tableName, null);
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void createEdgesTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (edgeID VARCHAR(21), startNode VARCHAR(10) REFERENCES MapNodesU (nodeID), endNode VARCHAR(10) REFERENCES MapNodesU (nodeID), PRIMARY KEY (edgeID))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path

            //InputStream csvFile = Database.class.getResourceAsStream("/csv_files/MapEdgesU.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try {
                BufferedReader br = getBR(tableName);
                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String edgeID = csvString[0];
                        String startNode = csvString[1];
                        String endNode = csvString[2];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + edgeID + "', '" + startNode + "', '" + endNode + "')");
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
        CreateCSV(stmt, tableName, null);
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void createServiceRequestTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dateReq DATE, type VARCHAR(5), info VARCHAR(255), PRIMARY KEY (reqID), "+
                    "CONSTRAINT SR_TY CHECK (type in ('MEDIC','SECUR')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void populateServiceRequestTable(Statement stmt, String tableName){
        //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
        //InputStream csvFile = Database.class.getResourceAsStream("/csv_files/ServiceRequest.csv");
        String line = "";
        String csvSplit = ",";

        //parses through csv file and creates a new row in the database for each row
        try {
            BufferedReader br = getBR(tableName);
            int starter = 0;
            while ((line = br.readLine()) != null) {

                String[] csvString = line.split(csvSplit);
                if (starter == 0){
                    starter = 1;
                }
                else{
                    // int reqID = Integer.parseInt(csvString[0]);
                    String reqDate = csvString[1];
                    String type = csvString[2];
                    String info = csvString[3];
                    stmt.executeUpdate("INSERT INTO " + tableName + " (dateReq, type, info) VALUES ('" + reqDate + "', '" + type + "', '" + info + "')");
                }
            }

            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ArrayList<Integer> Medic = new ArrayList<>();
            ArrayList<Integer> Secur = new ArrayList<>();

            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                if (rs.getString(3).equals("MEDIC")){
                    Medic.add(rs.getInt(1));
                }
                else if (rs.getString(3).equals("SECUR")){
                    Secur.add(rs.getInt(1));
                }
                //System.out.print(rs.getInt(1) + "\t");
                //System.out.println(rs.getString(3));
            }
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            rs.close();
            populateMedicineSR(stmt, "MedicineSR", Medic);
            populateSecuritySR(stmt, "SecuritySR", Secur);

        }
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void createServiceFinishedTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (timeFinished DATE, reqType VARCHAR(5), completedBy VARCHAR(10), info VARCHAR(255), "+
                    "CONSTRAINT SF_RT CHECK (reqType in ('MEDIC','SECUR')))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            //InputStream csvFile = Database.class.getResourceAsStream("/csv_files/ServiceFinished.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try {
                BufferedReader br = getBR(tableName);
                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String timeFinished = csvString[0];
                        String reqType = csvString[1];
                        String complete = csvString[2];
                        String info = csvString[3];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + timeFinished + "', '" + reqType + "', '" + complete + "', '" + info + "')");
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
        CreateCSV(stmt, tableName, null);
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void createMedicineSRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, patentFirstName VARCHAR(20), patentLastName VARCHAR(20), drugName VARCHAR(20), "+
                    "frequency VARCHAR(20), deliveryMethod VARCHAR(20), comment VARCHAR(200), CONSTRAINT MSSR_UR CHECK (deliveryMethod in ('IV','Oral', 'Topical')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void createSecuritySRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, location VARCHAR(20))";

            stmt.executeUpdate(slqCreate);


        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     * @param Medic
     */
    private static void populateMedicineSR(Statement stmt, String tableName, ArrayList<Integer> Medic){
        String line = "";
        String csvSplit = ",";

        //parses through csv file and creates a new row in the database for each row
        try {
            BufferedReader br = getBR(tableName);
            int starter = 0;
            int i = -1;
            while ((line = br.readLine()) != null) {

                String[] csvString = line.split(csvSplit);
                if (starter == 0){
                    starter = 1;
                }
                else{
                    //int reqID = Integer.parseInt(csvString[0]);
                    int reqID;
                    reqID = Medic.get(i);
                    String timeReq = csvString[1];
                    String patentFirstName = csvString[2];
                    String patentLastName = csvString[3];
                    String drugName = csvString[4];
                    String frequency = csvString[5];
                    String deliveryMethod = csvString[6];
                    String comment = csvString[7];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + patentFirstName +  "', '" + patentLastName + "', '" + drugName +  "', '" + frequency + "', '" + deliveryMethod + "', '" + comment + "')");
          System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     * @param Secur
     */
    private static void populateSecuritySR(Statement stmt, String tableName, ArrayList<Integer> Secur){
        //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
        //InputStream csvFile = Database.class.getResourceAsStream("/csv_files/SecuritySR.csv");
        String line = "";
        String csvSplit = ",";
        //parses through csv file and creates a new row in the database for each row
        try {
            BufferedReader br = getBR(tableName);
            int starter = 0;
            int i = -1;
            while ((line = br.readLine()) != null) {

                String[] csvString = line.split(csvSplit);
                if (starter == 0){
                    starter = 1;
                }
                else{
                    //int reqID = Integer.parseInt(csvString[0]);
                    int reqID = Secur.get(i);
                    String timeReq = csvString[1];
                    String location = csvString[2];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + location + "')");
                    System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    /**
     *
     * TODO：finish commenting
     * @param stmt
     * @param tableName
     */
    private static void createLoginTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (username VARCHAR(10), password VARCHAR(20), firstName VARCHAR(20), lastName VARCHAR(20), position VARCHAR(5), PRIMARY KEY (username), CONSTRAINT LI_PO CHECK (position in ('ADMIN','MEDIC','SECUR')))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            //InputStream csvFile = Database.class.getResourceAsStream("/csv_files/LoginDB.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try {
                BufferedReader br = getBR(tableName);
                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String username = csvString[0];
                        String password = csvString[1];
                        String firstName = csvString[2];
                        String lastName = csvString[3];
                        String position = csvString[4];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" + position + "')");
                    }
                }
                //INSERT INTO TABLENAME VALUES ('STRING', '...
            }
            catch (IOException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
        CreateCSV(stmt, tableName, null);
    }

    /**
     * Generate a csv file in a given path from the table
     * @param stmt statement
     * @param tableName the table to generate csv from
     * @param path the path to store the csv (if null, store in /DatabaseBackup)
     * @return true if it successfully generates the help
     */
    public static boolean CreateCSV(Statement stmt, String tableName, String path){
        //creates csv file that contains data with given query
        try {
            String CSVPath;
            //determine the destination path
            if(path == null) {
                String APath = System.getProperty("user.dir");
                CSVPath = APath + "/DatabaseBackup/" + tableName + ".csv";
            }
            else {
                CSVPath = path;
            }

            System.out.println(CSVPath);
            PrintWriter pw = new PrintWriter(new File(CSVPath));
            StringBuilder sb = new StringBuilder();

            try {
                String sql1 = "SELECT * FROM " + tableName;
                ResultSet results = stmt.executeQuery(sql1);
                ResultSetMetaData rsmd = results.getMetaData();
                int columns = rsmd.getColumnCount();
                for (int i = 1; i <= columns; i++) {
                    //add columns to csv file
                    if (i < columns) {
                        sb.append(rsmd.getColumnLabel(i) + ",");
                    }
                    else {
                        sb.append(rsmd.getColumnLabel(i) + "\n");
                    }
                }

                while (results.next()) {
                    //add data to each column
                    for (int i = 1; i <= columns; i++) {
                        if (i < columns) {
                            sb.append(results.getString(i) + ",");
                        }
                        else {
                            sb.append(results.getString(i) + "\n");
                        }

                    }

                }

                pw.write(sb.toString());
                pw.close();
                results.close();
                return true;

            } catch (SQLException e) {
                System.out.println("Connection failed. Check output console.");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e){
            System.out.println("Print Writer did not work");
            return false;
        }
    }

    /**
     * Print a given database table
     *
     * @param stmt Statement (sql)
     * @param tableName the name of the table you wish to print
     * @return true if method was able to successfully print table
     */
    public static boolean printTable(Statement stmt, String tableName) {
        try {
            String sql1 = "SELECT * FROM " + tableName;

            ResultSet results = stmt.executeQuery(sql1);
            ResultSetMetaData rsmd = results.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i) + "\t");
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (results.next()) {
                for (int i = 1; i <= columns; i++){
                    System.out.print(results.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            results.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates the HashMap for edges from the table
     * @param eHM the HashMap to store all the edge
     * @param nHM the HashMap of nodes to get all the nodes object
     */
    public static void getEdges(HashMap<String, Edge> eHM, HashMap<String, Node> nHM){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "MapEdgesU";

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            String sql1 = "SELECT * FROM " + tableName;
            ResultSet results = stmt.executeQuery(sql1);
            ResultSetMetaData rsmd = results.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                //no need to print Column Names
                //System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            //for each line, create a node and add it to hash map
            while (results.next()) {
                //get nodeId and edgeId from the table, and find the node objects from the HashMap
                String edgeID = results.getString(1);
                Node node1 = nHM.get(results.getString(2));
                Node node2 = nHM.get(results.getString(3));
                //calculate the distance, create a new Edge(), put it into the result HashMap
                int dis = Pathfinder.dist(node1, node2);
                eHM.put(edgeID, new Edge(node1, node2, dis, edgeID));
           }

            results.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Fill in the HashMap with nodes from the table
     * @param nHM the HashMap to store the nodes
     */
    public static void getNodes(HashMap<String,Node> nHM){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "MapNodesU";

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            String sql1 = "SELECT * FROM " + tableName;
            ResultSet results = stmt.executeQuery(sql1);
            ResultSetMetaData rsmd = results.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                //no need to print Column Names
                //System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            //for each line, create a node and add it to hash map
            while (results.next()) {
                String _nodeID = results.getString(1);
                int _xcoord = results.getInt(2);
                int _ycoord = results.getInt(3);
                int _floor = results.getInt(4);
                String _building = results.getString(5);
                String _nodeType = results.getString(6);
                String _longName = results.getString(7);
                String _shortName = results.getString(8);
                Node node = new Node(_nodeID, _xcoord, _ycoord, _floor, _building, _nodeType, _longName, _shortName);
                nHM.put(node.getID(), node);
            }

            results.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Creates a new tuple in the edges database for the given information
     *
     * @param startID first nodeID for the table
     * @param endID second nodeID for the table
     * @return true if the tuple was able to be created
     */
    public static boolean addEdge(String startID, String endID){
        Statement stmt;
        Connection conn;
        String tableName = "MapEdgesU";
        String edgeID = startID + "_" + endID;
        try{
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + edgeID + "', '" + startID + "', '" + endID + "')");
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException SQLExcept) {
            return false;
        }
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
    public static boolean addNode(String nodeID, int xcoord, int ycoord, int floor, String building, String nodeType, String longName, String shortName){
        String teamAssigned = "Team U";
        Statement stmt;
        Connection conn;
        String tableName = "MapNodesU";
        try{
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + nodeID + "', " + xcoord + ", " + ycoord + ", " + floor + ", '" + building + "', '" + nodeType + "', '" + longName + "', '" + shortName + "', '" + teamAssigned + "')");
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException SQLExcept) {
            SQLExcept.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes the given edge tuple using edgeID
     *
     * @param edgeID Primary Key of the edges table
     * @return true if tuple was deleted
     */
    public static boolean delEdge(String edgeID){
        Statement stmt;
        Connection conn;
        String tableName = "MapEdgesU";
        try{
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE edgeID = '" + edgeID + "'");
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException SQLExcept) {
            return false;
        }

    }

    /**
     * Deletes the given node tuple given the nodeID
     *
     * @param nodeID the Primary key of the nodes table
     * @return true if tuple was deleted
     */
    public static boolean delNode(String nodeID){
        Statement stmt;
        Connection conn;
        String tableName = "MapNodesU";
        String EdgeTable = "MapEdgesU";
        try{
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM " + EdgeTable + " WHERE startNode = '" + nodeID + "' OR endNode = '" + nodeID + "'");
            while (results.next()) {
                delEdge(results.getString(1));
            }

            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE nodeID = '" + nodeID + "'");
            CreateCSV(stmt, tableName, null);
            CreateCSV(stmt, EdgeTable, null);
            results.close();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException SQLExcept) {
            return false;
        }
    }


    /**
     * Fill in the HashMap for all Services from the table
     * @param servicesList the ArrayList to store the services
     */
    public static void getServices(ArrayList<Service> servicesList){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceRequest";
        //printTable(stmt, "MedicalSR");
//        Statement stmt1 = null;
//        Statement stmt2 = null;
//        String tableName1 = "MedicineSR";
//        String tableName2 = "SecuritySR";
//        //printTable(stmt, "MedicineSR");
        //printTable(stmt, "SecuritySR");

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();
            String sql = "SELECT * FROM " + tableName;
            ResultSet results = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();

            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                //no need to print Column Names
                //System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (results.next()) {
                String date = results.getString(1);
                String requestID = results.getString(2);
                String name = results.getString(3);
                String requestType = results.getString(4);
//                System.out.println(date);
//                System.out.println(requestID);
//                System.out.println(name);
//                System.out.println(requestType);
                Service s = new Service(date, requestID, name, requestType);
                servicesList.add(s);
                System.out.println("service list: " + servicesList);
                //System.out.println(_nodeID + "\t\t\t" + _xcoord + "\t\t\t" + _ycoord + "\t\t\t" + _floor + "\t\t\t" + _building + "\t\t\t" + _nodeType + "\t\t\t" + _longName + "\t\t\t" + _shortName );
            }
            results.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }


    /**
     * Check the if the username and password are valid for login
     * @param inputUsername username
     * @param inputPassword password
     * @return true if they match in the table
     */
    public static String checkCred(String inputUsername, String inputPassword){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "loginDB";

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            String sql1 = "SELECT * FROM " + tableName + " WHERE username = '" + inputUsername + "'";
            ResultSet results = stmt.executeQuery(sql1);
            ResultSetMetaData rsmd = results.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
            }

            while (results.next()) {
                //check if the password matches
                if (results.getString(2).equals(inputPassword)){
                    return results.getString(5);
                }
                else {
                    return "FALSE";
                }
            }
            results.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return "FALSE";
        }
        return "FALSE";
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
    public static boolean editTuple(String nodeIDN, int xcoordN, int ycoordN, int floorN, String buildingN, String nodeTypeN, String longNameN, String shortNameN ) {
        Connection conn = null;
        Statement stmt = null;
        String tableName = "MapNodesU";
        //prints out the whole table
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            String sql1 = "UPDATE " + tableName + " SET xcoord = " + xcoordN + ", ycoord = " + ycoordN + ", floor = " + floorN + ", building = '" + buildingN + "', nodeType = '" + nodeTypeN + "', longName = '" + longNameN + "', shortName = '" + shortNameN + "' WHERE nodeID = '" + nodeIDN + "'";

            stmt.executeUpdate(sql1);
            //System.out.println(result);
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return false;
        }
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
        Connection conn = null;
        Statement stmt = null;
        String tableName = "MapEdgesU";

        //prints out the whole table
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            String sql1 = "UPDATE "  + tableName + " SET edgeID = " + edgeIDN + ", startNode = " + startNodeN + ", endNode = " + endNodeN + "' WHERE edgeID = '" + edgeIDN + "'";

            int result = stmt.executeUpdate(sql1);
            System.out.println(result);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return false;
        }
    }
}
