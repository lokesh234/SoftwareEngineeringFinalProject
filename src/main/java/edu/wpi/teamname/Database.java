package edu.wpi.teamname;

import lombok.NoArgsConstructor;
import java.io.*;
import java.sql.*;


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
            //drops database tables if they currently exist

            createNodeTable(stmt, "MapNodesU");
            createEdgesTable(stmt, "MapEdgesU");
            //Creates tables again or for the first time

            printTable(stmt, "MapNodesU");
            printTable(stmt, "MapEdgesU");
            //print tables to test

            System.out.println("== Apache Derby Database Established! ==");
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


}
