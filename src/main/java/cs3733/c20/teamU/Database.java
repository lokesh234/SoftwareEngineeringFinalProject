package cs3733.c20.teamU;

import com.sun.java.accessibility.util.EventID;
import lombok.NoArgsConstructor;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


@NoArgsConstructor
public class Database {

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
            dropTable(stmt, "MedicalSR");
            dropTable(stmt, "SecuritySR");
            //drops database tables if they currently exist

            createNodeTable(stmt, "MapNodesU");
            createEdgesTable(stmt, "MapEdgesU");
            createLoginTable(stmt, "LoginDB");
            createServiceFinishedTable(stmt, "ServiceFinished");
            createMedicalSRTable(stmt, "MedicalSR");
            createSecuritySRTable(stmt, "SecuritySR");
            //Creates tables again or for the first time

            printTable(stmt, "MapNodesU");
            printTable(stmt, "MapEdgesU");
            printTable(stmt, "LoginDB");
            printTable(stmt, "ServiceFinished");
            printTable(stmt, "MedicalSR");
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

    private static void dropTable(Statement stmt, String tableName) {
        try{
            String sqldel = "DROP TABLE " + tableName;
            stmt.executeUpdate(sqldel);
        }
        catch(SQLException SQLExcept){

        }
    }
    private static void createNodeTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (nodeID VARCHAR(10), xcoord INTEGER, ycoord INTEGER, floor INTEGER, building VARCHAR(20), nodeType VARCHAR(4), longName VARCHAR(80), shortName VARCHAR(30), teamAssigned VARCHAR(6), PRIMARY KEY (nodeID), " +
                    "CONSTRAINT NT_CK CHECK (nodeType in ('DEPT','HALL','CONF','REST','STAI','ELEV','LABS','INFO','CONF','EXIT','RETL','SERV')))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            InputStream csvFile = Database.class.getResourceAsStream("/MapUnodes.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {

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
    }
    private static void createEdgesTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (edgeID VARCHAR(21), startNode VARCHAR(10) REFERENCES MapNodesU (nodeID), endNode VARCHAR(10) REFERENCES MapNodesU (nodeID), PRIMARY KEY (edgeID))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            InputStream csvFile = Database.class.getResourceAsStream("/MapUedges.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {

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

    }
    private static void createServiceFinishedTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (timeFinished DATE, reqType VARCHAR(5), info VARCHAR(255), "+
                    "CONSTRAINT SF_RT CHECK (reqType in ('MEDIC','SECUR')))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            InputStream csvFile = Database.class.getResourceAsStream("/ServiceFinished.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {

                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String timeFinished = csvString[0];
                        String reqType = csvString[1];
                        String info = csvString[2];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + timeFinished + "', '" + reqType + "', '" + info + "')");
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
    }
    private static void createMedicalSRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID VARCHAR(10), timeReq DATE, urgency VARCHAR(7), info VARCHAR(255), PRIMARY KEY (reqID), "+
                    "CONSTRAINT MSR_UR CHECK (urgency in ('EXTREME','HIGH', 'MEDIUM', 'LOW')))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            InputStream csvFile = Database.class.getResourceAsStream("/SecuritySR.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {

                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String reqID = csvString[0];
                        String timeReq = csvString[1];
                        String urgency = csvString[2];
                        String info = csvString[3];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + reqID + "', '" + timeReq + "', '" + urgency +  "', '" + info + "')");
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
    }
    private static void createSecuritySRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID VARCHAR(10), timeReq DATE, urgency VARCHAR(7), type VARCHAR(10), info VARCHAR(255), PRIMARY KEY (reqID), "+
                    "CONSTRAINT SSR_UR CHECK (urgency in ('EXTREME','HIGH', 'MEDIUM', 'LOW')), CONSTRAINT SSR_TY CHECK (type in('EMERGENCY', 'GUARD')))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            InputStream csvFile = Database.class.getResourceAsStream("/SecuritySR.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {

                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String reqID = csvString[0];
                        String timeReq = csvString[1];
                        String urgency = csvString[2];
                        String type = csvString[3];
                        String info = csvString[4];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + reqID + "', '" + timeReq + "', '" + urgency +  "', '" + type + "', '" + info + "')");
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
    }


    private static void createLoginTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (username VARCHAR(10), password VARCHAR(20), PRIMARY KEY (username))";

            stmt.executeUpdate(slqCreate);

            //String csvFile = "src/main/java/xxxx.csv"; //Hardcoded path
            InputStream csvFile = Database.class.getResourceAsStream("/LoginDB.csv");
            String line = "";
            String csvSplit = ",";

            //parses through csv file and creates a new row in the database for each row
            try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {

                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String username = csvString[0];
                        String password = csvString[1];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + username + "', '" + password + "')");
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
    }

    public static boolean printTable(Statement stmt, String tableName){
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
                String edgeID = results.getString(3);
                Node node1 = nHM.get(results.getString(1));
                Node node2 = nHM.get(results.getString(2));
                int dis = Pathfinder.dist(node1, node2);
                eHM.put(edgeID, new Edge(node1, node2, dis, edgeID));
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
    public static boolean checkCred(String inputUsername, String inputPassword){
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
                //no need to print Column Names
                //System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
            }
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            //for each line, create a node and add it to hash map
            while (results.next()) {
                if (results.getString(2).equals(inputPassword)){
                    return true;
                }
                else {
                    return false;
                }
            }

            results.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
