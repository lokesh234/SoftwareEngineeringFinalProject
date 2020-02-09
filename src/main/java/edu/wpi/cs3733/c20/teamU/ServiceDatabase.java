package edu.wpi.cs3733.c20.teamU;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceDatabase {

//    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
//    Date dateobj = new Date();
//    System.out.println(df.format(dateobj));
    //how to import a date for SQL table

    public static boolean serviceFinishedAdd(String timeFinished, String reqType, String completedBy, String info){
        String tableName = "ServiceFinished";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + timeFinished + "', '" + reqType + "', '" + completedBy + "', '" + info + "')");

            stmt.close();
            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //serviceDisplay method currently prints values, will be edited later to save values
    // to some kind of list or map

    public static boolean serviceDisplay(String tableName){

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
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
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return false;
        }
    }



    public static boolean securitySRAdd(String reqID, String timeReq,String urgency, String type, String info){
        String tableName = "SecuritySR";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + reqID + "','" + timeReq + "', '" + urgency +"','" + type + "', '" + info + "')");

            stmt.close();
            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public static boolean medicalSRAdd(String reqID, String timeReq, String urgency, String info){
        String tableName = "MedicalSR";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + reqID + "','" + timeReq + "', '" + urgency +"', '" + info + "')");

            stmt.close();
            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }



}
