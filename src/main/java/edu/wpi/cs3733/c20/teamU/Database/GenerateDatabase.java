package edu.wpi.cs3733.c20.teamU.Database;

import org.sqlite.core.DB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class GenerateDatabase {

  public static boolean generateNewDatabase(String dbName, ArrayList<String> dataTypes, String picturePath) {
    dbName = dbName.toUpperCase();
    // Creates new value in DatabaseList Database, keeps track of name for future reference
    // Creates new value name in TypesSR
    DatabaseWrapper.addDatabase(dbName, picturePath);
    Connection connection = null;
    Statement stmt = null;
    try {
      connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
      stmt = connection.createStatement();

      createNewSRTable(stmt, dbName, dataTypes);
      DatabaseWrapper.createCSV(stmt, dbName, null);

      stmt.close();
      connection.close();
      return true;
    } catch (SQLException e) {
      System.out.println("Cant Create New DB? / Already exists");
      //e.printStackTrace();
      return false;
    }
  }

  static boolean createNewSRTable(Statement stmt, String tableName, ArrayList<String> dataTypes){
    try{
    String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE";
    for (int i = 0; i < dataTypes.size(); i++) {
        switch (dataTypes.get(i)) {
          case "DATE":
            slqCreate = slqCreate + ", field" + i + " DATE";
            break;
          case "TIME":
            slqCreate = slqCreate + ", field" + i + " TIME";
            break;
          case "STRING":
            slqCreate = slqCreate + ", field" + i + " VARCHAR(200)";
            break;
        }
      }
    slqCreate = slqCreate + ")";

    System.out.println("Sql: " + slqCreate);
    stmt.executeUpdate(slqCreate);


    return true;
    } catch (SQLException e) {
      System.out.println("failed to add new db / db already exists");
      //e.printStackTrace();
      return false;
    }
  }

  public static boolean populateNewDatabase(String dbName, ArrayList<String> values) {
    String tableName = dbName.toUpperCase();
    int reqID;
    String timeReq = DatabaseWrapper.getCurrentDate();
    String SRTableName = "ServiceRequest";
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
      stmt = conn.createStatement();
      // getting UBDatabase
      stmt.executeUpdate(
          "INSERT  INTO "
              + SRTableName
              + " (dateReq, types, info) VALUES ('"
              + timeReq
              + "', '"
              + tableName
              + "', 'Generated Service')",
          Statement.RETURN_GENERATED_KEYS);
      ResultSet rs = stmt.getGeneratedKeys();
      rs.next();
      reqID = rs.getInt(1);
      String sqlCreate = "INSERT INTO " + tableName + " VALUES (" + reqID + ",'" + timeReq + "', ";
      for (int i = 0; i < values.size(); i++) {
        if (i == values.size() - 1) {
          sqlCreate = sqlCreate + "'" + values.get(i) + "')";
        } else {
          sqlCreate = sqlCreate + "'" + values.get(i) + "', ";
        }
      }
      stmt.executeUpdate(sqlCreate);
      rs.close();
      Database.CreateCSV(stmt, tableName, null);
      Database.CreateCSV(stmt, SRTableName, null);
      stmt.close();
      conn.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean populateNDBOnStartUp(Statement stmt, String dbName) {
    String SRTableName = "ServiceRequest";
    String tableName = dbName;
    String line = "";
    String csvSplit = ",";

    //System.out.println("starting StartUp populate");
    // parses through csv file and creates a new row in the database for each row
    try {
      BufferedReader br = Database.getBR(dbName);
      int starter = 0;
      int i = -1;
      while ((line = br.readLine()) != null) {

        String[] csvString = line.split(csvSplit);
        if (starter == 0) {
          starter = 1;
        } else {
          stmt.executeUpdate("INSERT  INTO " + SRTableName + " (dateReq, types, info) VALUES ('" + csvString[1] + "', '" + tableName + "', 'Generated Service')", Statement.RETURN_GENERATED_KEYS);
          ResultSet rs = stmt.getGeneratedKeys();
          rs.next();
          int reqID = rs.getInt(1);
          //System.out.println("reqID :" + reqID);
          // int reqID = Integer.parseInt(csvString[0]);
          String timeReq = csvString[1];
          String sqlStmt = "INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "'";
          int j = 2;
          //System.out.println(csvString.length);
          while (j < csvString.length) {
            String stringCSV = csvString[j];
            sqlStmt = sqlStmt + ", '" + stringCSV + "'";
            j++;
          }
          sqlStmt = sqlStmt + ")";
          System.out.println(sqlStmt);
          stmt.executeUpdate(sqlStmt);

          // System.out.println("reached update");
          rs.close();
        }
        i++;
      }
      DatabaseWrapper.createCSV(stmt, tableName, null);
      DatabaseWrapper.createCSV(stmt, SRTableName, null);

      return true;
    } catch (SQLException | IOException e) {
      e.printStackTrace();
      System.out.println("Problem in populate GenerateDB");
      return false;
    }
  }

  public static void populateAllGeneratedDB(Statement stmt, String tableName) {
    try {
      ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

      ArrayList<String> listDB = new ArrayList<>();
      while (rs.next()) {
        String DBName = rs.getString(1);
        System.out.println("DB name : " + DBName);
        listDB.add(DBName);
      }
      rs.close();
      for (int j = 0; j < listDB.size(); j++){
        populateNDBOnStartUp(stmt, listDB.get(j).toUpperCase());
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("problem getting every DB");
    }
  }


}