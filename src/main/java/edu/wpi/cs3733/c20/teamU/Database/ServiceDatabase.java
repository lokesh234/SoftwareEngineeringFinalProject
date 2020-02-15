package edu.wpi.cs3733.c20.teamU.Database;

import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

            Database.CreateCSV(stmt, tableName, null);
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



    //securitySRAdd: adds a row to ServiceRequests and to securitySR. does't take in any parameters because its implied / generated?
    //ServiceDatabase.securitySRAdd(); Example
    public static boolean securitySRAdd(){
        int reqID;
        String timeReq = getCurrentDate();
        String location = "Terminal 1"; //place holder for when terminal is given


        String SSRTableName = "SecuritySR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, type, info) VALUES ('" + timeReq + "', 'SECUR', 'Security request at " + location + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + SSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + location + "')");

            rs.close();
            Database.CreateCSV(stmt, SSRTableName, null);
            Database.CreateCSV(stmt, SRTableName, null);
            stmt.close();
            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //gets current date
    public static String getCurrentDate(){
        String timeReq;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj));
        timeReq = df.format(dateobj);
        return timeReq;
    }

    //ServiceDatabase.medicineSRAdd("Marcus", "Chalmers", "Sadness", "Everyday", "IV", "sad boi hours"); Example
    public static boolean medicineSRAdd(String patentFirstName, String patentLastName, String drugName, String frequency, String deliveryMethod, String comment){
        int reqID;
        String timeReq = getCurrentDate();


        String MSRTableName = "MedicineSR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, type, info) VALUES ('" + timeReq + "', 'MEDIC', '" + patentFirstName + " " + patentLastName + " drug: " + drugName + " " + comment + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + MSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + patentFirstName + "', '" + patentLastName + "', '" + drugName + "', '" + frequency + "', '" + deliveryMethod + "', '" + comment + "')");

            rs.close();
            Database.CreateCSV(stmt, MSRTableName, null);
            Database.CreateCSV(stmt, SRTableName, null);
            stmt.close();
            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


//    //Deletes service object from tables
//    public static boolean servDelete(String reqType, int reqID){
//        return false;
  public static boolean securitySRDel(int reqID, String adminsName){
    String STable = "SecuritySR";
    String SRTable = "ServiceRequest";
    String SFTable = "ServiceFinished";
    String curDate = getCurrentDate();
    String location;

    Connection conn = null;
    Statement stmt = null;
        try {
          conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
          stmt = conn.createStatement();

            ResultSet results = stmt.executeQuery("SELECT  * FROM " + STable + " WHERE reqID = " + reqID);
            results.next();
            location = results.getString(3);
            results.close();

            //delete from table SecuritySR first (needs to be before servicereuest)
            stmt.executeUpdate("DELETE FROM " + STable + " WHERE reqID = " + reqID);

            //delete from table servicerequest
            stmt.executeUpdate("DELETE FROM " + SRTable + " WHERE reqID = " + reqID);

            stmt.executeUpdate("INSERT INTO " + SFTable + " VALUES ('" + curDate + "', 'SECUR', '" + adminsName + "', 'Location: " + location + "')");

            Database.CreateCSV(stmt, STable, null);
            Database.CreateCSV(stmt, SRTable, null);
            Database.CreateCSV(stmt, SFTable, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

  public static boolean medicineSRDel(int reqID, String adminsName) {
    String MTable = "MedicineSR";
    String SRTable = "ServiceRequest";
    String SFTable = "ServiceFinished";
    String curDate = getCurrentDate();
    String info = "";

    Connection conn = null;
    Statement stmt = null;
    try {
      conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
      stmt = conn.createStatement();

      ArrayList<Service> services = new ArrayList<>();
      Database.getServices(services);
      for (Service s : services) {
          if (Integer.parseInt(s.getDate()) == reqID) {
              info = s.getName() + " needed: " + s.getRequestType();
              break;
          }
      }


      // delete from table SecuritySR first (needs to be before servicereuest)
      stmt.executeUpdate("DELETE FROM " + MTable + " WHERE reqID = " + reqID);

      // delete from table servicerequest
      stmt.executeUpdate("DELETE FROM " + SRTable + " WHERE reqID = " + reqID);

      stmt.executeUpdate(
          "INSERT INTO "
              + SFTable
              + " VALUES ('"
              + curDate
              + "', 'MEDIC', '"
              + adminsName
              + "', '"
              + info
              + "')");

      Database.CreateCSV(stmt, MTable, null);
      Database.CreateCSV(stmt, SRTable, null);
      Database.CreateCSV(stmt, SFTable, null);
      stmt.close();
      conn.close();
      return true;
        } catch (SQLException e) {
          e.printStackTrace();
          return false;
        }
    }

}
