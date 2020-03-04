package edu.wpi.cs3733.c20.teamU.Database;

import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;

import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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

    //displays map (not necessary?)
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
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'SECUR', 'Security request at " + location + "')",Statement.RETURN_GENERATED_KEYS);
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

    //languageSRAdd("Chalmers", "Marcus", "Chinese");
    public static boolean languageSRAdd(String patentLastName, String patentFirstName, String language, String location){
        int reqID;
        String timeReq = getCurrentDate();


        String LSRTableName = "LanguageSR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'LANGE', 'Language request of " + language + " at location: " + location + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + LSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + patentLastName + "', '" + patentFirstName + "', '" + language + "', '" + location + "')");

            rs.close();
            Database.CreateCSV(stmt, LSRTableName, null);
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

    public static boolean intTransportSRAdd(String patentLastName, String patentFirstName, String startLocation, String endLocation, String equipment){
        int reqID;
        String timeReq = getCurrentDate();
        String ITSRTableName = "InternalTransportSR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'ITRAN', 'Transport from: " + startLocation + " to: " + endLocation + " via: " + equipment + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + ITSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + patentFirstName + "', '" + patentLastName + "', '" + startLocation + "', '" + endLocation + "', '" + equipment + "')");

            rs.close();
            Database.CreateCSV(stmt, ITSRTableName, null);
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

    public static boolean extTransportSRAdd(String patentLastName, String patentFirstName, String destination, String departureTime, String departureDate, int nPassengers){
        int reqID;
        String timeReq = getCurrentDate();
        String ETSRTableName = "ExternalTransportSR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'ETRAN', 'Transport to: " + destination + " at: " + departureDate + " " + departureTime + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + ETSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + patentFirstName + "', '" + patentLastName + "', '" + destination + "', '" + departureTime + "', '" + departureDate + "', '" + nPassengers + "')");

            rs.close();
            Database.CreateCSV(stmt, ETSRTableName, null);
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

    public static boolean deliverySRAdd(String patentLastName, String patentFirstName, String gift, String room){
        int reqID;
        String timeReq = getCurrentDate();
        String DSRTableName = "DeliverySR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'DELIV', 'Deliver: " + gift + " to: " + room + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + DSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + patentFirstName + "', '" + patentLastName + "', '" + gift + "', '" + room + "')");

            rs.close();
            Database.CreateCSV(stmt, DSRTableName, null);
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

    public static boolean ClownDeliverySRAdd(String location, int nClowns, String recipientName, String deliveryDate){
        int reqID;
        String timeReq = getCurrentDate();
        String CDSRTableName = "ClownDeliverySR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'CLOWN', 'Number of clowns: " + nClowns + " for: " + recipientName + " on: " + deliveryDate + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + CDSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + location + "', " + nClowns + ", '" + recipientName + "', '" + deliveryDate + "')");

            rs.close();
            Database.CreateCSV(stmt, CDSRTableName, null);
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

    public static boolean FlowersSRAdd(String lastName, String firstName, boolean roses, boolean tulips, boolean lilies, String occasion, String deliveryDate, String giftNote, String room){
        int reqID;
        String timeReq = getCurrentDate();
        String FSRTableName = "FlowersSR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'FLOWR', 'Deliver flowers to: " + room + " on: " + deliveryDate + " : " + giftNote + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + FSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + lastName + "', '" + firstName + "', '" + roses + "', '" + tulips + "', '" + lilies + "', '" + occasion + "', '" + deliveryDate + "', '" + giftNote + "', '" + room + "')");

            rs.close();
            Database.CreateCSV(stmt, FSRTableName, null);
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

    public static boolean ITSRAdd(String patentLastName, String patentFirstName, String helpType, String comments){
        int reqID;
        String timeReq = getCurrentDate();
        String ITSRTableName = "ITSR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'INTEC', 'Help on: " + helpType + " where: " + comments + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + ITSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + patentFirstName + "', '" + patentLastName + "', '" + helpType + "', '" + comments + "')");

            rs.close();
            Database.CreateCSV(stmt, ITSRTableName, null);
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

    public static boolean ReligionSRAdd(String patentName, String religiousAffiliation, String explanation){
        int reqID;
        String timeReq = getCurrentDate();
        String RSRTableName = "ReligionSR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'RELIG', 'Service for : " + religiousAffiliation + " where: " + explanation + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + RSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + patentName + "', '" + religiousAffiliation + "', '" + explanation + "')");

            rs.close();
            Database.CreateCSV(stmt, RSRTableName, null);
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

    public static boolean SanitarySRAdd(String service, String location, String nature, String info){
        int reqID;
        String timeReq = getCurrentDate();
        String SSRTableName = "SanitarySR";
        String SRTableName = "ServiceRequest";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            //getting UBDatabase
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'SANIT', 'Service: " + service + " needed: " + location + " where: " + info + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            reqID = rs.getInt(1);
            stmt.executeUpdate("INSERT INTO " + SSRTableName + " VALUES (" + reqID + ",'" + timeReq + "', '" + service + "', '" + location + "', '" + nature + "', '" + info + "')");

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
    static String getCurrentDate(){
        String timeReq;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date dateobj = new Date();
        //System.out.println(df.format(dateobj));
        timeReq = df.format(dateobj);
        return timeReq;
    }

    static String getCurrentTime(){
        String curTime;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        curTime = dtf.format(now);
        return curTime;
    }


    //ServiceDatabase.medicineSRAdd("Marcus", "Chalmers", "Sadness", "Everyday", "Oral", "sad boi hours"); Example
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
            stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + timeReq + "', 'MEDIC', '" + patentFirstName + " " + patentLastName + " drug: " + drugName + " " + comment + "')",Statement.RETURN_GENERATED_KEYS);
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
    String SRTime;

    Connection conn = null;
    Statement stmt = null;
        try {
          conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
          stmt = conn.createStatement();

            ResultSet results = stmt.executeQuery("SELECT  * FROM " + STable + " WHERE reqID = " + reqID);
            results.next();
            location = results.getString(3);
            SRTime = results.getString(2);
            results.close();

            //delete from table SecuritySR first (needs to be before servicereuest)
            stmt.executeUpdate("DELETE FROM " + STable + " WHERE reqID = " + reqID);

            //delete from table servicerequest
            stmt.executeUpdate("DELETE FROM " + SRTable + " WHERE reqID = " + reqID);

            stmt.executeUpdate("INSERT INTO " + SFTable + " VALUES ('" + curDate + "', 'SECUR', '" + adminsName + "', 'Location: " + location + " Time: " + SRTime + "')");

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

  public static boolean medicineSRDel(int reqID, String adminsName, String user) {
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
      Database.getServices(services, user);
      for (Service s : services) {
          if (Integer.parseInt(s.getDate()) == reqID) {
              info = s.getName() + " | request: " + s.getRequestType();
              break;
          }
      }


      // delete from table SecuritySR first (needs to be before servicereuest)
      stmt.executeUpdate("DELETE FROM " + MTable + " WHERE reqID = " + reqID);

      // delete from table servicerequest
      stmt.executeUpdate("DELETE FROM " + SRTable + " WHERE reqID = " + reqID);

      stmt.executeUpdate(
          "INSERT INTO " + SFTable + " VALUES ('" + curDate + "', 'MEDIC', '" + adminsName + "', '" + info + "')");

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

    public static boolean languageSRDel(int reqID, String adminsName, String user) {
        String LTable = "LanguageSR";
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
            Database.getServices(services, user);
            for (Service s : services) {
                if (Integer.parseInt(s.getDate()) == reqID) {
                    info = s.getName() + " | request: " + s.getRequestType();
                    break;
                }
                else{
                    info = "*Request information not found*";
                }
            }


            // delete from table SecuritySR first (needs to be before servicereuest)
            stmt.executeUpdate("DELETE FROM " + LTable + " WHERE reqID = " + reqID);

            // delete from table servicerequest
            stmt.executeUpdate("DELETE FROM " + SRTable + " WHERE reqID = " + reqID);

            stmt.executeUpdate(
                    "INSERT INTO " + SFTable + " VALUES ('" + curDate + "', 'LANGE', '" + adminsName + "', '" + info + "')");

            Database.CreateCSV(stmt, LTable, null);
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

    //Ex: serviceRequestDel(1, "admin", "ADMIN", "InternalTransportSR", "ITRAN");
    public static boolean serviceRequestDel(int reqID, String adminsName, String user, String tableName, String jobType) {
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
            Database.getServices(services, user);
            for (Service s : services) {
                if (Integer.parseInt(s.getDate()) == reqID) {
                    info = s.getName() + " | request: " + s.getRequestType();
                    break;
                }
                else{
                    info = "*Request information not found*";
                }
            }


            // delete from table SecuritySR first (needs to be before servicereuest)
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE reqID = " + reqID);

            // delete from table servicerequest
            stmt.executeUpdate("DELETE FROM " + SRTable + " WHERE reqID = " + reqID);

            stmt.executeUpdate(
                    "INSERT INTO " + SFTable + " VALUES ('" + curDate + "', '" + jobType + "', '" + adminsName + "', '" + info + "')");

            Database.CreateCSV(stmt, tableName, null);
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

    public static int getServiceRequestAmount(String serviceRequestType, String dateType, String day, String month, String year){
        int retNumber = 0;
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceRequest";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            if (dateType.equals("day")) {
                sql = "SELECT * FROM " + tableName + " WHERE types = '" + serviceRequestType + "' AND dateReq = '" + year + "-" + month + "-" + day + "'";
            }
            else if (dateType.equals("month")) {
                sql = "SELECT * FROM " + tableName + " WHERE types = '"+ serviceRequestType + "' AND MONTH(dateReq) = " + month + " AND YEAR(dateReq) = " + year;
            }
            else if (dateType.equals("year")){
                sql = "SELECT * FROM " + tableName + " WHERE types = '"+ serviceRequestType + "' AND YEAR(dateReq) = " + year;
            }
            ResultSet results = stmt.executeQuery(sql);


            while (results.next()) {
//                String requestID = results.getString(1);
//                String date = results.getString(2);
//                String requestType = results.getString(3);
//                System.out.println("Tuple: " + requestID + " " + date + " " + requestType);
                retNumber++;
                //System.out.println(_nodeID + "\t\t\t" + _xcoord + "\t\t\t" + _ycoord + "\t\t\t" + _floor + "\t\t\t" + _building + "\t\t\t" + _nodeType + "\t\t\t" + _longName + "\t\t\t" + _shortName );
            }
            results.close();
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return 0;
        }
        return retNumber;
    }

    public static int getServiceRequestAmountRange(String serviceRequestType, String startDate, String endDate){
        int retNumber = 0;
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceRequest";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT * FROM " + tableName + " WHERE types = '"+ serviceRequestType + "' AND dateReq >= '" + startDate + "' AND dateReq <= '" + endDate + "'";

            ResultSet results = stmt.executeQuery(sql);
            while (results.next()) {
//                String requestID = results.getString(1);
//                String date = results.getString(2);
//                String requestType = results.getString(3);
//                System.out.println("Tuple: " + requestID + " " + date + " " + requestType);
                retNumber++;
                //System.out.println(_nodeID + "\t\t\t" + _xcoord + "\t\t\t" + _ycoord + "\t\t\t" + _floor + "\t\t\t" + _building + "\t\t\t" + _nodeType + "\t\t\t" + _longName + "\t\t\t" + _shortName );
            }
            results.close();
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return 0;
        }
        return retNumber;
    }

    public static int getSRAToday(String serviceRequestType){
        String date = getCurrentDate();
        return getServiceRequestAmountRange(serviceRequestType, date, date);
    }
    public static int getSRALastWeek(String serviceRequestType){
        String dateNow = getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String datePast = dateFormat.format(cal.getTime());

        return getServiceRequestAmountRange(serviceRequestType, datePast, dateNow);
    }
    public static int getSRALastMonth(String serviceRequestType){
        String dateNow = getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String datePast = dateFormat.format(cal.getTime());

        return getServiceRequestAmountRange(serviceRequestType, datePast, dateNow);
    }
    public static int getSRALastYear(String serviceRequestType){
        String dateNow = getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        String datePast = dateFormat.format(cal.getTime());

        return getServiceRequestAmountRange(serviceRequestType, datePast, dateNow);
    }

    public static int getServiceFinishedAmount(String serviceRequestType, String dateType, String day, String month, String year){
        int retNumber = 0;
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceFinished";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            if (dateType.equals("day")) {
                sql = "SELECT * FROM " + tableName + " WHERE reqType = '" + serviceRequestType + "' AND timeFinished = '" + year + "-" + month + "-" + day + "'";
            }
            else if (dateType.equals("month")) {
                sql = "SELECT * FROM " + tableName + " WHERE reqType = '"+ serviceRequestType + "' AND MONTH(timeFinished) = " + month + " AND YEAR(timeFinished) = " + year;
            }
            else if (dateType.equals("year")){
                sql = "SELECT * FROM " + tableName + " WHERE reqType = '"+ serviceRequestType + "' AND YEAR(timeFinished) = " + year;
            }
            ResultSet results = stmt.executeQuery(sql);


            while (results.next()) {
//                String requestID = results.getString(1);
//                String date = results.getString(2);
//                String requestType = results.getString(3);
//                System.out.println("Tuple: " + requestID + " " + date + " " + requestType);
                retNumber++;
                //System.out.println(_nodeID + "\t\t\t" + _xcoord + "\t\t\t" + _ycoord + "\t\t\t" + _floor + "\t\t\t" + _building + "\t\t\t" + _nodeType + "\t\t\t" + _longName + "\t\t\t" + _shortName );
            }
            results.close();
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return 0;
        }
        return retNumber;
    }
    public static int getServiceFinishedAmountRange(String serviceRequestType, String startDate, String endDate){
        int retNumber = 0;
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceFinished";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT * FROM " + tableName + " WHERE reqType = '"+ serviceRequestType + "' AND timeFinished >= '" + startDate + "' AND timeFinished <= '" + endDate + "'";

            ResultSet results = stmt.executeQuery(sql);
            while (results.next()) {
//                String requestID = results.getString(1);
//                String date = results.getString(2);
//                String requestType = results.getString(3);
//                System.out.println("Tuple: " + requestID + " " + date + " " + requestType);
                retNumber++;
                //System.out.println(_nodeID + "\t\t\t" + _xcoord + "\t\t\t" + _ycoord + "\t\t\t" + _floor + "\t\t\t" + _building + "\t\t\t" + _nodeType + "\t\t\t" + _longName + "\t\t\t" + _shortName );
            }
            results.close();
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return 0;
        }
        return retNumber;
    }

    public static int getSFinishedAToday(String serviceRequestType){
        String date = getCurrentDate();
        return getServiceFinishedAmountRange(serviceRequestType, date, date);
    }
    public static int getSFinishedALastWeek(String serviceRequestType){
        String dateNow = getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String datePast = dateFormat.format(cal.getTime());

        return getServiceFinishedAmountRange(serviceRequestType, datePast, dateNow);
    }
    public static int getSFinishedALastMonth(String serviceRequestType){
        String dateNow = getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String datePast = dateFormat.format(cal.getTime());

        return getServiceFinishedAmountRange(serviceRequestType, datePast, dateNow);
    }
    public static int getSFinishedALastYear(String serviceRequestType){
        String dateNow = getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        String datePast = dateFormat.format(cal.getTime());

        return getServiceFinishedAmountRange(serviceRequestType, datePast, dateNow);
    }

    public static int getServiceFinishedAll(String serviceRequestType){
        int retNumber = 0;
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceFinished";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT * FROM " + tableName + " WHERE reqType = '"+ serviceRequestType + "'";

            ResultSet results = stmt.executeQuery(sql);
            while (results.next()) {
                retNumber++;
                //System.out.println(_nodeID + "\t\t\t" + _xcoord + "\t\t\t" + _ycoord + "\t\t\t" + _floor + "\t\t\t" + _building + "\t\t\t" + _nodeType + "\t\t\t" + _longName + "\t\t\t" + _shortName );
            }
            results.close();
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return 0;
        }
        return retNumber;
    }

    public static int getServiceRequestAll(String serviceRequestType){
        int retNumber = 0;
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceRequest";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT * FROM " + tableName + " WHERE reqType = '"+ serviceRequestType + "'";

            ResultSet results = stmt.executeQuery(sql);
            while (results.next()) {
                retNumber++;
                //System.out.println(_nodeID + "\t\t\t" + _xcoord + "\t\t\t" + _ycoord + "\t\t\t" + _floor + "\t\t\t" + _building + "\t\t\t" + _nodeType + "\t\t\t" + _longName + "\t\t\t" + _shortName );
            }
            results.close();
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return 0;
        }
        return retNumber;
    }
}
