package edu.wpi.cs3733.c20.teamU.Database;

import edu.wpi.cs3733.c20.teamU.Administration.Account;
import edu.wpi.cs3733.c20.teamU.Administration.Colors;
import edu.wpi.cs3733.c20.teamU.Administration.UserBacklog;
import edu.wpi.cs3733.c20.teamU.Navigation.NavigationWrapper;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.Service;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.sqlite.core.DB;

import javax.jws.soap.SOAPBinding;
import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;


//@NoArgsConstructor
public class Database {
    private Database() {}

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
            dropTable(stmt, "DestinationsDB");
            dropTable(stmt, "MapNodesU");
            dropTable(stmt, "UserBacklogDB");
            dropTable(stmt, "LoginDB");
            dropTable(stmt, "ColorsDB");
            dropTable(stmt, "ServiceFinished");
            dropTable(stmt, "SecuritySR");
            dropTable(stmt, "MedicineSR");
            dropTable(stmt, "LanguageSR");
            dropTable(stmt, "InternalTransportSR");
            dropTable(stmt, "ExternalTransportSR");
            dropTable(stmt, "DeliverySR");
            dropTable(stmt, "ClownDeliverySR");
            dropTable(stmt, "FlowersSR");
            dropTable(stmt, "ReligionSR");
            dropTable(stmt, "SanitarySR");
            dropTable(stmt, "ITSR");

            dropAllTables(stmt);

            dropTable(stmt, "DatabaseList");
            // drops database tables if they currently exist

            createDatabaseTable(stmt, "DatabaseList");


            System.out.println("service Req? " + dropTable(stmt, "ServiceRequest"));
            System.out.println("type Sr: " + dropTable(stmt, "TypesSR"));
            System.out.println("Dropped tables");



            createNodeTable(stmt, "MapNodesU");
            createEdgesTable(stmt, "MapEdgesU");
            createDestinationsTable(stmt, "DestinationsDB");
            createColorTable(stmt, "ColorsDB");
            createTypesTable(stmt, "TypesSR");
            createLoginTable(stmt, "LoginDB");
            createUserBacklogTable(stmt, "UserBacklogDB");
            createServiceRequestTable(stmt,"ServiceRequest");
            createServiceFinishedTable(stmt, "ServiceFinished");
            createMedicineSRTable(stmt, "MedicineSR");
            createSecuritySRTable(stmt, "SecuritySR");
            createLanguageSRTable(stmt, "LanguageSR");
            createIntTransportSRTable(stmt, "InternalTransportSR");
            createExtTransportSRTable(stmt, "ExternalTransportSR");
            createDeliverySRTable(stmt, "DeliverySR");
            createClownDeliverySRTable(stmt, "ClownDeliverySR");
            createFlowersSRTable(stmt, "FlowersSR");
            createReligionSRTable(stmt, "ReligionSR");
            createSanitarySRTable(stmt, "SanitarySR");
            createITSRTable(stmt, "ITSR");
            createGeneratedTables(stmt);

            populateServiceRequestTable(stmt, "ServiceRequest");
            // Creates tables again or for the first time
            System.out.println("Created Tables");

            printTable(stmt, "MapNodesU");
            printTable(stmt, "MapEdgesU");
            printTable(stmt, "DestinationsDB");
            printTable(stmt, "LoginDB");
            printTable(stmt, "UserBacklogDB");
            printTable(stmt, "ColorsDB");
            printTable(stmt, "TypesSR");
            printTable(stmt, "DatabaseList");
            printTable(stmt, "ServiceRequest");
            printTable(stmt, "ServiceFinished");
            printTable(stmt, "MedicineSR");
            printTable(stmt, "SecuritySR");
            printTable(stmt, "LanguageSR");
            printTable(stmt, "InternalTransportSR");
            printTable(stmt, "ExternalTransportSR");
            printTable(stmt, "DeliverySR");
            printTable(stmt, "ClownDeliverySR");
            printTable(stmt, "FlowersSR");
            printTable(stmt, "ReligionSR");
            printTable(stmt, "SanitarySR");
            printTable(stmt, "ITSR");

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
    static BufferedReader getBR(String tableName){
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


    private static boolean dropTable(Statement stmt, String tableName) {
        try{
            String sqldel = "DROP TABLE " + tableName;
            stmt.executeUpdate(sqldel);
            return true;
        }
        catch(SQLException SQLExcept){
            return false;
        }
    }



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

    private static void createDestinationsTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (dateSearched DATE, startNode VARCHAR(10) REFERENCES MapNodesU (nodeID), destNode VARCHAR(10) REFERENCES MapNodesU (nodeID))";

            stmt.executeUpdate(slqCreate);
            String line = "";
            String csvSplit = ",";

            try {
                BufferedReader br = getBR(tableName);
                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String date = csvString[0];
                        String startNode = csvString[1];
                        String destNode = csvString[2];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + date + "', '" + startNode + "', '" + destNode + "')");
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

    private static void createTypesTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (typeName VARCHAR(5), PRIMARY KEY (typeName))";

            stmt.executeUpdate(slqCreate);
            String line = "";
            String csvSplit = ",";

            try {
                BufferedReader br = getBR(tableName);
                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String typeName = csvString[0];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + typeName + "')");
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

    private static void dropAllTables(Statement stmt){
        String tableName = "DatabaseList";
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ArrayList<String> listDB = new ArrayList<>();
            while(rs.next()){
                String name = rs.getString(1);
                listDB.add(name);
                System.out.println(name);
            }
            rs.close();
            for(int i = 0; i < listDB.size(); i++){
                System.out.println("Drop table " + listDB.get(i) + " " + dropTable(stmt, listDB.get(i)));
            }

        } catch (SQLException e) {
             System.out.println("DatabaseList doesnt Exist yet");
        }

    }

    private static void createDatabaseTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (dbName VARCHAR(5), dbPicture VARCHAR(50), PRIMARY KEY (dbName))";

            stmt.executeUpdate(slqCreate);
            String line = "";
            String csvSplit = ",";

            try {
                BufferedReader br = getBR(tableName);
                int starter = 0;
                while ((line = br.readLine()) != null) {

                    String[] csvString = line.split(csvSplit);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{
                        String name = csvString[0];
                        String picture = csvString[1];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + name + "', '" + picture + "')");
                        System.out.println("Drop table " + name + " " + dropTable(stmt, name));
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

    private static void createGeneratedTables(Statement stmt){
        String DBTableName = "DatabaseList";
        String txtSplit = ",";
        try {
            ResultSet results = stmt.executeQuery("SELECT * FROM " + DBTableName);

            ArrayList<String> dbList = new ArrayList<>();
            while (results.next()) {
                System.out.println("ResultsListIttertion");
                String DBName = results.getString(1);
                DBName = DBName.toUpperCase();
                dbList.add(DBName);
            }
            results.close();
            System.out.println(dbList);

            for (int h = 0; h < dbList.size(); h++){
                String bdName = dbList.get(h);
                String APath = System.getProperty("user.dir");
                String txtFile = APath + "/CustomRequests/" + bdName + "InputTypes.txt";
                BufferedReader br = new BufferedReader(new FileReader(txtFile));
                String line = br.readLine();
                String[] txtString = line.split(txtSplit);
                ArrayList<String> stringReturn = new ArrayList<>();
                int i = 0;
                while(i < txtString.length){
                    System.out.println("i value : " + i);
                    stringReturn.add(txtString[i]);
                    i++;
                }
                System.out.println(stringReturn);
                System.out.println(GenerateDatabase.createNewSRTable(stmt, bdName, stringReturn));
                //DatabaseWrapper.createCSV(stmt, dbList.get(h), null);
            }


        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("problem getting every DB");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createServiceRequestTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dateReq DATE, types VARCHAR(5) REFERENCES TypesSR (typeName), info VARCHAR(255), PRIMARY KEY (reqID))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

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
                    boolean containsNormal = true;
                    switch (type){
                        case "MEDIC":
                            break;
                        case "SECUR":
                            break;
                        case "LANGE":
                            break;
                        case "ITRAN":
                            break;
                        case "ETRAN":
                            break;
                        case "CLOWN":
                            break;
                        case "DELIV":
                            break;
                        case "INTEC":
                            break;
                        case "RELIG":
                            break;
                        case "SANIT":
                            break;
                        case "FLOWR":
                            break;
                        case "ADMIN":
                            break;
                        default:
                            containsNormal = false;
                            break;
                    }
                    if (containsNormal){
                        stmt.executeUpdate("INSERT INTO " + tableName + " (dateReq, types, info) VALUES ('" + reqDate + "', '" + type + "', '" + info + "')");
                    }
                    else{
                        System.out.println("not adding type: " + type + " yet");
                    }
                }
            }

            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ArrayList<Integer> Medic = new ArrayList<>();
            ArrayList<Integer> Secur = new ArrayList<>();
            ArrayList<Integer> Lange = new ArrayList<>();
            ArrayList<Integer> Itran = new ArrayList<>();
            ArrayList<Integer> Etran = new ArrayList<>();
            ArrayList<Integer> Clown = new ArrayList<>();
            ArrayList<Integer> Deliv = new ArrayList<>();
            ArrayList<Integer> Flowr = new ArrayList<>();
            ArrayList<Integer> Intec = new ArrayList<>();
            ArrayList<Integer> Relig = new ArrayList<>();
            ArrayList<Integer> Sanit = new ArrayList<>();

            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String value = rs.getString(3);
                switch (value){
                    case "MEDIC":
                        Medic.add(rs.getInt(1));
                        break;
                    case "SECUR":
                        Secur.add(rs.getInt(1));
                        break;
                    case "LANGE":
                        Lange.add(rs.getInt(1));
                        break;
                    case "ITRAN":
                        Itran.add(rs.getInt(1));
                        break;
                    case "ETRAN":
                        Etran.add(rs.getInt(1));
                        break;
                    case "CLOWN":
                        Clown.add(rs.getInt(1));
                        break;
                    case "DELIV":
                        Deliv.add(rs.getInt(1));
                        break;
                    case "INTEC":
                        Intec.add(rs.getInt(1));
                        break;
                    case "RELIG":
                        Relig.add(rs.getInt(1));
                        break;
                    case "SANIT":
                        Sanit.add(rs.getInt(1));
                        break;
                    case "FLOWR":
                        Flowr.add(rs.getInt(1));
                        break;

                }
            }
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            rs.close();
            populateMedicineSR(stmt, "MedicineSR", Medic);
            populateSecuritySR(stmt, "SecuritySR", Secur);
            populateLanguageSR(stmt, "LanguageSR", Lange);
            populateIntTransportSR(stmt, "InternalTransportSR", Itran);
            populateExtTransportSR(stmt, "ExternalTransportSR", Etran);
            populateClownDeliverySR(stmt, "ClownDeliverySR", Clown);
            populateDeliverySR(stmt, "DeliverySR", Deliv);
            populateITSR(stmt, "ITSR", Intec);
            populateReligionSR(stmt, "ReligionSR", Relig);
            populateSanitarySR(stmt, "SanitarySR", Sanit);
            populateFlowersSR(stmt, "FlowersSR", Flowr);


            GenerateDatabase.populateAllGeneratedDB(stmt, "DatabaseList");


        }
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }


    private static void createServiceFinishedTable(Statement stmt, String tableName) {
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (timeFinished DATE, reqType VARCHAR(5) REFERENCES TypesSR (typeName), completedBy VARCHAR(20), info VARCHAR(255))";

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

    private static void createMedicineSRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, patentFirstName VARCHAR(20), patentLastName VARCHAR(20), drugName VARCHAR(20), "+
                    "frequency VARCHAR(20), deliveryMethod VARCHAR(20), comment VARCHAR(200), CONSTRAINT MSSR_UR CHECK (deliveryMethod in ('Suppository','Oral', 'Topical')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createIntTransportSRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, firstName VARCHAR(20), lastName VARCHAR(20), startLocation VARCHAR(20), "+
                    "endLocation VARCHAR(20), equipment VARCHAR(20), CONSTRAINT ITSR_CK CHECK (equipment in ('Wheelchair','Crutches')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createExtTransportSRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, firstName VARCHAR(20), lastName VARCHAR(20), destination VARCHAR(20), "+
                    "departureTime TIME, departureDate DATE, nPassengers INTEGER)";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createClownDeliverySRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, location VARCHAR(40), nClowns INTEGER , recipientName VARCHAR(30), deliveryDate DATE)";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createSanitarySRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, service VARCHAR(60), location VARCHAR(30), nature VARCHAR(60), info VARCHAR(200))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createDeliverySRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, firstName VARCHAR(20), lastName VARCHAR(20), gift VARCHAR(20), room VARCHAR(20), " +
                "CONSTRAINT DSR_CK CHECK (gift in ('TeddyBear', 'Cookies', 'Chocolate')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createITSRTable(Statement stmt, String tableName){
        try{
      String slqCreate =
          "CREATE TABLE "
              + tableName
              + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, firstName VARCHAR(20), lastName VARCHAR(20), helpType VARCHAR(30), comments VARCHAR(200), "
              + "CONSTRAINT ISR_CK CHECK (helpType in ('WIFI', 'Kiosk', 'Software', 'Hardware', 'Other', 'Dont Know')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createReligionSRTable(Statement stmt, String tableName){
        try{
      String slqCreate =
          "CREATE TABLE "
              + tableName
              + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, name VARCHAR(50), religiousAffiliation VARCHAR(30), explanation VARCHAR(200), "
              + "CONSTRAINT RRSR_CK CHECK (religiousAffiliation in ('Protestantism', 'Catholicism', 'Judaism', 'Mormonism', 'Islam', 'Other', 'No Religion')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

    private static void createFlowersSRTable(Statement stmt, String tableName) {
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, lastName VARCHAR(20), firstName VARCHAR(20), roses BOOLEAN, tulips BOOLEAN, lilies BOOLEAN, occasion VARCHAR(20), "+
                    "deliveryDate DATE, giftNote VARCHAR(200), room VARCHAR(30), CONSTRAINT FSR_CK CHECK (occasion in ('Happy','Sad', 'Anytime')))";

            stmt.executeUpdate(slqCreate);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }

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

    private static void createLanguageSRTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (reqID int REFERENCES ServiceRequest (reqID), timeReq DATE, lastName VARCHAR(20), firstName VARCHAR(20), language VARCHAR(20), location VARCHAR(20), " +
                    "CONSTRAINT LT_CK CHECK (language in ('Chinese','Hindi','Japanese','Spanish','Russian','Ethiopian')))";

            stmt.executeUpdate(slqCreate);


        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;

        }
    }



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
          //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateIntTransportSR(Statement stmt, String tableName, ArrayList<Integer> Itran){
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
                    reqID = Itran.get(i);
                    String timeReq = csvString[1];
                    String patentFirstName = csvString[2];
                    String patentLastName = csvString[3];
                    String startLocation = csvString[4];
                    String endLocation = csvString[5];
                    String equipment = csvString[6];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + patentFirstName +  "', '" + patentLastName + "', '" + startLocation +  "', '" + endLocation + "', '" + equipment + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateExtTransportSR(Statement stmt, String tableName, ArrayList<Integer> Etran){
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
                    reqID = Etran.get(i);
                    String timeReq = csvString[1];
                    String patentFirstName = csvString[2];
                    String patentLastName = csvString[3];
                    String destination = csvString[4];
                    String departureTime = csvString[5];
                    String departureDate = csvString[6];
                    Integer nPassengers = Integer.getInteger(csvString[7]);
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + patentFirstName +  "', '" + patentLastName + "', '" + destination +  "', '" + departureTime + "', '" + departureDate + "', '" + nPassengers + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateClownDeliverySR(Statement stmt, String tableName, ArrayList<Integer> Clown){
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
                    reqID = Clown.get(i);
                    String timeReq = csvString[1];
                    String location = csvString[2];
                    String nClowns = csvString[3];
                    String recipientName = csvString[4];
                    String deliveryDate = csvString[5];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + location +  "', " + nClowns + ", '" + recipientName + "', '" + deliveryDate + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateDeliverySR(Statement stmt, String tableName, ArrayList<Integer> Deliv){
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
                    reqID = Deliv.get(i);
                    String timeReq = csvString[1];
                    String patentFirstName = csvString[2];
                    String patentLastName = csvString[3];
                    String gift = csvString[4];
                    String room = csvString[5];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + patentFirstName +  "', '" + patentLastName + "', '" + gift +  "', '" + room + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateFlowersSR(Statement stmt, String tableName, ArrayList<Integer> Flowr){
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
                    reqID = Flowr.get(i);
                    String timeReq = csvString[1];
                    String patentFirstName = csvString[2];
                    String patentLastName = csvString[3];
                    boolean roses = Boolean.getBoolean(csvString[4]);
                    boolean tulips = Boolean.getBoolean(csvString[5]);
                    boolean lilies = Boolean.getBoolean(csvString[6]);
                    String occasion = csvString[7];
                    String deliveryDate = csvString[8];
                    String giftNote = csvString[9];
                    String room = csvString[10];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + patentFirstName +  "', '" + patentLastName + "', '" + roses +  "', '" + tulips + "', '" + lilies + "', '" +
                            occasion + "', '" + deliveryDate + "', '" + giftNote + "', '" + room + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateITSR(Statement stmt, String tableName, ArrayList<Integer> Intec){
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
                    reqID = Intec.get(i);
                    String timeReq = csvString[1];
                    String patentFirstName = csvString[2];
                    String patentLastName = csvString[3];
                    String helpType = csvString[4];
                    String comments = csvString[5];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + patentFirstName +  "', '" + patentLastName + "', '" + helpType +  "', '" + comments + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateReligionSR(Statement stmt, String tableName, ArrayList<Integer> Relig){
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
                    reqID = Relig.get(i);
                    String timeReq = csvString[1];
                    String patentName = csvString[2];
                    String religiousAffiliation = csvString[3];
                    String explanation = csvString[4];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + patentName + "', '" + religiousAffiliation +  "', '" + explanation +  "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateSanitarySR(Statement stmt, String tableName, ArrayList<Integer> Sanit){
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
                    reqID = Sanit.get(i);
                    String timeReq = csvString[1];
                    String service = csvString[2];
                    String location = csvString[3];
                    String nature = csvString[4];
                    String info = csvString[5];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + service + "', '" + location + "', '" + nature + "', '" + info + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

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
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }

    private static void populateLanguageSR(Statement stmt, String tableName, ArrayList<Integer> Lange){
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
                    int reqID = Lange.get(i);
                    String timeReq = csvString[1];
                    String lastName = csvString[2];
                    String firstName = csvString[3];
                    String language = csvString[4];
                    String location = csvString[5];
                    stmt.executeUpdate("INSERT INTO " + tableName + " VALUES (" + reqID + ", '" + timeReq + "', '" + lastName + "', '" + firstName + "', '" + language + "', '" + location + "')");
                    //System.out.println("reached update");
                }
                i++;
            }
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        CreateCSV(stmt, tableName, null);
    }



    private static void createLoginTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (username VARCHAR(10), password VARCHAR(20), firstName VARCHAR(20), lastName VARCHAR(20), position VARCHAR(5) REFERENCES TypesSR (typeName), number VARCHAR(20), PRIMARY KEY (username))";

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
                  //  System.out.println(csvString);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{

                        String username = csvString[0];
                        String password = csvString[1];
                        String firstName = csvString[2];
                        String lastName = csvString[3];
                        String position = csvString[4];
                        String number = csvString[5];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" + position + "', '" + number + "')");
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

    private static void createColorTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (colorTheme VARCHAR(20), firstColor VARCHAR(6), secondColor VARCHAR(6), thirdColor VARCHAR(6), fourthColor VARCHAR(6), fifthColor VARCHAR(6), firstTextColor VARCHAR(6), secondTextColor VARCHAR(6), PRIMARY KEY (colorTheme))";

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
                    //System.out.println(csvString);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{

                        String colorTheme = csvString[0];
                        String color1 = csvString[1];
                        String color2 = csvString[2];
                        String color3 = csvString[3];
                        String color4 = csvString[4];
                        String color5 = csvString[5];
                        String colorTxt1 = csvString[6];
                        String colorTxt2 = csvString[7];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + colorTheme + "', '" + color1 + "', '" + color2 + "', '" + color3 + "', '" + color4 + "', '" + color5 + "', '" + colorTxt1 + "', '" + colorTxt2 + "')");
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

    private static void createUserBacklogTable(Statement stmt, String tableName){
        try{
            String slqCreate = "CREATE TABLE " + tableName + " (username VARCHAR(10) REFERENCES LoginDB (username), dateCompleted DATE, timeCompleted TIME, serviceType VARCHAR(5) REFERENCES TypesSR (typeName), operations VARCHAR(200), addInfo VARCHAR(200))";

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
                    //System.out.println(csvString);
                    if (starter == 0){
                        starter = 1;
                    }
                    else{

                        String username = csvString[0];
                        String dateComp = csvString[1];
                        String timeComp = csvString[2];
                        String SType = csvString[3];
                        String operations = csvString[4];
                        String info = csvString[5];
                        stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + username + "', '" + dateComp + "', '" + timeComp + "', '" + SType + "', '" + operations + "', '" + info + "')");
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



    public static boolean addUserBacklog(String username, String serviceType, String operations, String info){
        String tableName = "UserBacklogDB";
        Connection conn = null;
        Statement stmt = null;
        String date = ServiceDatabase.getCurrentDate();
        String time = ServiceDatabase.getCurrentTime();
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + username + "', '" + date + "', '" + time + "', '" + serviceType + "', '" + operations + "', '" + info + "')");
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("couldn't update User Backlog table");
            return false;
        }
    }

    public static boolean addUserBacklog(String username, String dateCompleted, String timeCompleted, String serviceType, String operations, String info){
        String tableName = "UserBacklogDB";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + username + "', '" + dateCompleted + "', '" + timeCompleted + "', '" + serviceType + "', '" + operations + "', '" + info + "')");
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("couldn't update employee table");
            return false;
        }
    }

    public static void getAllUserBacklog(ArrayList<UserBacklog> userBacklogs){
        Connection conn = null;
        Statement stmt = null;
        String tableName = "UserBacklogDB";
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            String sql1 = "SELECT * FROM " + tableName;

            ResultSet results = stmt.executeQuery(sql1);

            while (results.next()) {
                String username = results.getString(1);
                String date = results.getString(2);
                String time = results.getString(3);
                String position = results.getString(4);
                String operation = results.getString(5);
                String info = results.getString(6);

                UserBacklog newUserB = new UserBacklog(username, date, time, position, operation, info);
                userBacklogs.add(newUserB);
            }

            results.close();
            stmt.close();
            conn.close();
            return;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }



    public static boolean addColor(String colorName, String color1, String color2, String color3, String color4, String color5, String textColor1, String textColor2){
        String tableName = "ColorsDB";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + colorName + "', '" + color1 + "', '" + color2 + "', '" + color3 + "', '" + color4 + "', '" + color5 + "', '" + textColor1 + "', '" + textColor2 + "')");
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("couldn't update color table > make sure color name is unique");
            return false;
        }
    }

    public static ArrayList<Colors> getAllColors(){
        Connection conn = null;
        Statement stmt = null;
        String tableName = "ColorsDB";
        ArrayList<Colors> colorArr = new ArrayList<>();
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            String sql1 = "SELECT * FROM " + tableName;

            ResultSet results = stmt.executeQuery(sql1);

            while (results.next()) {
                String colorTheme = results.getString(1);
                String c1 = results.getString(2);
                String c2 = results.getString(3);
                String c3 = results.getString(4);
                String c4 = results.getString(5);
                String c5 = results.getString(6);
                String ct1 = results.getString(7);
                String ct2 = results.getString(8);

                Colors newColor = new Colors(colorTheme, c1, c2, c3, c4, c5, ct1, ct2);
                colorArr.add(newColor);
            }

            results.close();
            stmt.close();
            conn.close();
            return colorArr;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return null;
        }
    }

    public static Colors getColor(String colorTheme){
        Connection conn = null;
        Statement stmt = null;
        String tableName = "ColorsDB";
        Colors newColor = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            String sql1 = "SELECT * FROM " + tableName + " WHERE colorTheme = '" + colorTheme + "'";

            ResultSet results = stmt.executeQuery(sql1);

            while (results.next()) {
                String c1 = results.getString(2);
                String c2 = results.getString(3);
                String c3 = results.getString(4);
                String c4 = results.getString(5);
                String c5 = results.getString(6);
                String ct1 = results.getString(7);
                String ct2 = results.getString(8);

                newColor = new Colors(colorTheme, c1, c2, c3, c4, c5, ct1, ct2);
            }

            results.close();
            stmt.close();
            conn.close();
            return newColor;
        } catch (SQLException e) {
            //color not found
            return null;
        }
    }

    public static boolean delColor(String colorName){
        Statement stmt;
        Connection conn;
        String tableName = "ColorsDB";
        try{
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE colorTheme = '" + colorName + "'");
            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException SQLExcept) {
            return false;
        }
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
                int dis = 75;
                if (node1.getNodeType().equals("STAI") && node2.getNodeType().equals("STAI")) dis = 150;
                else if (!node1.getNodeType().equals("ELEV") && !node2.getNodeType().equals("ELEV")) dis = NavigationWrapper.dist(node1, node2);
                eHM.put(edgeID, new Edge(node1, node2, dis, edgeID));
           }

            results.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console. Are Tables Loaded?");
            //e.printStackTrace();
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
            System.out.println("Connection failed. Check output console. Are Tables Loaded?");
            //e.printStackTrace();
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
    public static void getServices(ArrayList<Service> servicesList, String user){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceRequest";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            if (user == null){
                return;
            }
            else if (user.equals("ADMIN")) {
                sql = "SELECT * FROM " + tableName;
            }
            else {
                sql = "SELECT  * FROM " + tableName + " WHERE types = '"+ user + "'";

               // ResultSet results = stmt.executeQuery("SELECT  * FROM " + STable + " WHERE reqID = " + reqID);
            }
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

    public static void getFinishedServices(ArrayList<Service> servicesList, String user){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "ServiceFinished";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            if (user == null){
                return;
            }
            else if (user.equals("ADMIN")) {
                sql = "SELECT * FROM " + tableName;
            }
            else {
                sql = "SELECT  * FROM " + tableName + " WHERE reqType = '"+ user + "'";

                // ResultSet results = stmt.executeQuery("SELECT  * FROM " + STable + " WHERE reqID = " + reqID);
            }
            ResultSet results = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();


            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                //no need to print Column Names
                //System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (results.next()) {
                String date = results.getString(3);
                String requestID = results.getString(1);
                String name = results.getString(2);
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

    public static void getAccounts(ArrayList<Account> accounts){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "LoginDB";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT  * FROM " + tableName;
            ResultSet results = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();


            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                //no need to print Column Names
                //System.out.print(rsmd.getColumnLabel(i) + "\t\t\t");
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (results.next()) {
                String username = results.getString(1);
                String password = results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                String cred = results.getString(5);
                String number = results.getString(6);
//                System.out.println(date);
//                System.out.println(requestID);
//                System.out.println(name);
//                System.out.println(requestType);
                Account a = new Account(username, password, firstName, lastName, cred, number);
                accounts.add(a);
                System.out.println("Account list: " + accounts);
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
    public static Account checkCred(String inputUsername, String inputPassword){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "loginDB";

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            String sql1 = "SELECT * FROM " + tableName + " WHERE username = '" + inputUsername + "'";
            ResultSet results = stmt.executeQuery(sql1);
//            ResultSetMetaData rsmd = results.getMetaData();
//            int columns = rsmd.getColumnCount();
//            for (int i = 1; i <= columns; i++) {
//            }
            String username = "";
            String password = "";
            String firstName = "";
            String lastName = "";
            String cred = "";
            String number = "";
            while (results.next()) {
                //check if the password matches
                if (results.getString(2).equals(inputPassword)){
                    username = results.getString(1);
                    password = results.getString(2);
                    firstName = results.getString(3);
                    lastName = results.getString(4);
                    cred = results.getString(5).toUpperCase();
                    number = results.getString(6);
//                System.out.println(date);
//                System.out.println(requestID);
//                System.out.println(name);
//                System.out.println(requestType);
                } else {
                    results.close();
                    stmt.close();
                    connection.close();
                    return null;
                }
                Account a = new Account(username, password, firstName, lastName, cred, number);
                results.close();
                stmt.close();
                connection.close();
                return a;
            }
            results.close();
            stmt.close();
            connection.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return null;
        }
    }

  /**
   * create a new user or edit and existing one
   *
   * @param username username for login (PK)
   * @param password password for login
   * @param firstName FirstName
   * @param lastName LastName
   * @param position Position: one of ('ADMIN' 'MEDIC' ...)
   * @param number
   * @return boolean if loginSR is updated or edited
   */
  public static boolean addLoginSR(String username, String password, String firstName, String lastName, String position, String number) {
    String tableName = "LoginDB";
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
      stmt = conn.createStatement();
      // getting UBDatabase
      // check to see if username exists in LoginDB:
      ResultSet results = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE username = '" + username + "'");
      if (results != null) {
        while (results.next()) {
          username = results.getString(1);
        }
        stmt.executeUpdate("DELETE FROM " + tableName + " WHERE username = '" + username + "'");
      }

      stmt.executeUpdate(
          "INSERT INTO " + tableName + " VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" + position + "', '" + number + "')");

      Database.CreateCSV(stmt, tableName, null);
      stmt.close();
      conn.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

    /**
     * give the username as a string and it will return an array list with the following strings in this order
     * username, password, firstName, lastName, position
     * @param username used as the PK
     * @return returns arrayList of strings (U,P,F,L,P)
     */
  public static Account getLoginSR(String username){
      String tableName = "LoginDB";
      Connection conn = null;
      Statement stmt = null;
      try {
          conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
          stmt = conn.createStatement();
          // getting UBDatabase
          // check to see if username exists in LoginDB:
          ResultSet results = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE username = '" + username + "'");
          String password = "";
          String firstName = "";
          String lastName = "";
          String position = "";
          String number = "";
          while (results.next()) {
            password = results.getString(2);
            firstName = results.getString(3);
            lastName = results.getString(4);
            position = results.getString(5);
            number = results.getString(6);
              }
          Account a = new Account(username, password, firstName, lastName, position, number);
          stmt.close();
          conn.close();
          return a;
      } catch (SQLException e) {
          e.printStackTrace();
          return null;
      }
  }

    /**
     * Deletes a row from the database
      * @param username give unique Username for deletion
     * @return returns true if row is deleted
     */
  public static boolean delLoginSR(String username){
        String tableName = "LoginDB";
        String tableNameUB = "UserBacklogDB";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            // getting UBDatabase
            // check to see if username exists in LoginDB:
            stmt.executeUpdate("DELETE FROM " + tableNameUB + " WHERE username = '" + username + "'");
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE username = '" + username + "'");
            CreateCSV(stmt, tableName, null);
            CreateCSV(stmt, tableNameUB, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            String sql1 = "UPDATE "  + tableName + " SET edgeID = " + edgeIDN + ", startNode = " + startNodeN + ", endNode = " + endNodeN + " WHERE edgeID = '" + edgeIDN + "'";

            int result = stmt.executeUpdate(sql1);
            System.out.println(result);
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

    public static boolean addDestination(String startNode, String destNode){
        Connection conn = null;
        Statement stmt = null;
        String tableName = "DestinationsDB";
        String date = DatabaseWrapper.getCurrentDate();

        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            String sql1 = "INSERT INTO " + tableName + " VALUES ('" + date + "', '" + startNode + "', '" + destNode + "')";

            int result = stmt.executeUpdate(sql1);
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

    public static void getNodesByFloor(ArrayList<Node> nodes, int floor){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "MapNodesU";

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            String sql1 = "SELECT * FROM " + tableName + " WHERE floor = " + floor + " AND nodeType <> 'HALL'";
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
                nodes.add(node);
            }

            results.close();
            stmt.close();
            connection.close();
            return;

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    public static boolean addDatabase(String name, String picture){
        String tableName = "DatabaseList";
        Connection conn = null;
        Statement stmt = null;
        name = name.toUpperCase();
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + name + "', '" + picture + "')");
            System.out.println("add type? " + name + " " + addType(name));

            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("(add DB) Please insert a name with less than 6 letters / make sure value doesn't already exist");
            return false;
        }
    }

    public static boolean addType(String typeName){
        String tableName = "TypesSR";
        Connection conn = null;
        Statement stmt = null;
        typeName = typeName.toUpperCase();
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO " + tableName + " VALUES ('" + typeName + "')");

            CreateCSV(stmt, tableName, null);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("(Add Type) Please insert a name with less than 6 letters / make sure value doesn't already exist");
            return false;
        }
    }

    public static boolean delType(String typeName){
        String tableName = "TypesSR";
        String loginTable = "LoginDB";
        String UserBacklogTable = "UserBacklogDB";
        String ServiceTable = "ServiceRequest";
        String FinishedTable = "ServiceFinished";
        String DatabaseTable = "DatabaseList";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();
            // getting UBDatabase
            // check to see if username exists in LoginDB:

            stmt.executeUpdate("DELETE FROM " + typeName);
            dropTable(stmt, tableName.toUpperCase());
            stmt.executeUpdate("DELETE FROM " + DatabaseTable + " WHERE dbName = '" + typeName + "'");
            stmt.executeUpdate("DELETE FROM " + UserBacklogTable + " WHERE serviceType = '" + typeName + "'");
            stmt.executeUpdate("DELETE FROM " + ServiceTable + " WHERE types = '" + typeName + "'");
            stmt.executeUpdate("DELETE FROM " + FinishedTable + " WHERE reqType = '" + typeName + "'");
            stmt.executeUpdate("DELETE FROM " + loginTable + " WHERE position = '" + typeName + "'");
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE typeName = '" + typeName + "'");
            CreateCSV(stmt, tableName, null);
            CreateCSV(stmt, DatabaseTable, null);
            CreateCSV(stmt, loginTable, null);
            CreateCSV(stmt, UserBacklogTable, null);
            CreateCSV(stmt, ServiceTable, null);
            CreateCSV(stmt, FinishedTable, null);
            String APath = System.getProperty("user.dir");
            String csvPath = APath + "/DatabaseBackup/" + typeName + ".csv";
            File csvDel = new File(csvPath);
            csvDel.delete();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("value not found? Idk how this would have gone wrong otherwise");
            return false;
        }
    }

    public static ArrayList<String> getTypes(){
        ArrayList<String> typesList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        String tableName = "TypesSR";
        String sql = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT  * FROM " + tableName;
            ResultSet results = stmt.executeQuery(sql);

            while (results.next()) {
                String typeName = results.getString(1);

                typesList.add(typeName);
            }
            results.close();
            stmt.close();
            connection.close();
            return typesList;
        } catch (SQLException e) {
            System.out.println("Failed. Check output console. unable to get list of types");
            e.printStackTrace();
            return null;
        }
    }

    //Shouldn't really be used anymore?
    public static ArrayList<String> getDataAnalytics(ArrayList<Integer> frequency, String type){
        ArrayList<String> types = new ArrayList<String>(Arrays.asList("SECUR",
                "MEDIC",
                "FLOWR",
                "DELIV",
                "ITRAN",
                "ETRAN",
                "CLOWN",
                "RELIG",
                "SANIT",
                "LANGE",
                "INTEC"));
        Connection connection = null;
        Statement stmt = null;
        String tableName;
        String dataType;

        if (type.equals("employee")) {
            tableName = "LoginDB";
            dataType = "position";
            types.add("ADMIN");
        }
        else if (type.equals("service")){
            tableName = "ServiceRequest";
            dataType = "types";
        }
        else {
            tableName = "ServiceFinished";
            dataType = "reqType";
        }

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            for(int i = 0; i < types.size(); i++) {
                int temp =0;
                String category = types.get(i);
                String sql1 = "SELECT * FROM " + tableName + " WHERE " + dataType + " = '" + category + "'";
                System.out.println(sql1);
                ResultSet results = stmt.executeQuery(sql1);
                ResultSetMetaData rsmd = results.getMetaData();
                int columns = rsmd.getColumnCount();
                //for each line, create a node and add it to hash map
                while (results.next()) {
                    //System.out.println("temp");
                    temp++;
                }
                frequency.add(temp);
                results.close();
            }
            stmt.close();
            connection.close();
            return types;
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Node> getDestinationAmount(String dateType, String day, String month, String year){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "DestinationsDB";
        String tableNodes = "MapNodesU";
        String sql = null;
        ArrayList<Node> nodeList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            if (dateType.equals("day")) {
                sql = "SELECT * FROM " + tableName + " WHERE dateSearched = '" + year + "-" + month + "-" + day + "'";
            }
            else if (dateType.equals("month")) {
                sql = "SELECT * FROM " + tableName + " WHERE MONTH(dateSearched) = " + month + " AND YEAR(dateSearched) = " + year;
            }
            else if (dateType.equals("year")){
                sql = "SELECT * FROM " + tableName + " WHERE YEAR(dateSearched) = " + year;
            }
            else { return null; }
            ResultSet results = stmt.executeQuery(sql);

            ArrayList<String> stringList = new ArrayList<>();
            while (results.next()) {
                String destNode = results.getString(3);
                stringList.add(destNode);
            }
            results.close();

            for (int i = 0; i < stringList.size(); i++){
                ResultSet resultNode = stmt.executeQuery("SELECT * FROM " + tableNodes + " WHERE nodeID = '" + stringList.get(i) + "'");
                while (resultNode.next()){
                    String _nodeID = resultNode.getString(1);
                    int _xcoord = resultNode.getInt(2);
                    int _ycoord = resultNode.getInt(3);
                    int _floor = resultNode.getInt(4);
                    String _building = resultNode.getString(5);
                    String _nodeType = resultNode.getString(6);
                    String _longName = resultNode.getString(7);
                    String _shortName = resultNode.getString(8);
                    Node node = new Node(_nodeID, _xcoord, _ycoord, _floor, _building, _nodeType, _longName, _shortName);
                    nodeList.add(node);
                }
                resultNode.close();
            }
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return null;
        }
        return nodeList;
    }

    public static ArrayList<Node> getDestinationAmountRange(String startDate, String endDate){
        Connection connection = null;
        Statement stmt = null;
        String tableName = "DestinationsDB";
        String tableNodes = "MapNodesU";
        String sql = null;
        ArrayList<Node> nodeList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT * FROM " + tableName + " WHERE dateSearched >= '" + startDate + "' AND dateSearched <= '" + endDate + "'";
            ResultSet results = stmt.executeQuery(sql);

            ArrayList<String> stringList = new ArrayList<>();
            while (results.next()) {
                String destNode = results.getString(3);
                stringList.add(destNode);
            }
            results.close();

            for (int i = 0; i < stringList.size(); i++){
                ResultSet resultNode = stmt.executeQuery("SELECT * FROM " + tableNodes + " WHERE nodeID = '" + stringList.get(i) + "'");
                while (resultNode.next()){
                    String _nodeID = resultNode.getString(1);
                    int _xcoord = resultNode.getInt(2);
                    int _ycoord = resultNode.getInt(3);
                    int _floor = resultNode.getInt(4);
                    String _building = resultNode.getString(5);
                    String _nodeType = resultNode.getString(6);
                    String _longName = resultNode.getString(7);
                    String _shortName = resultNode.getString(8);
                    Node node = new Node(_nodeID, _xcoord, _ycoord, _floor, _building, _nodeType, _longName, _shortName);
                    nodeList.add(node);
                }
                resultNode.close();
            }
            stmt.close();
            connection.close();
            //System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return null;
        }
        return nodeList;
    }

    public static ArrayList<Node> getDARToday(){
        String date = DatabaseWrapper.getCurrentDate();
        return getDestinationAmountRange(date, date);
    }
    public static ArrayList<Node> getDARLastWeek(){
        String dateNow = DatabaseWrapper.getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String datePast = dateFormat.format(cal.getTime());

        return getDestinationAmountRange(datePast, dateNow);
    }
    public static ArrayList<Node> getDARLastMonth(){
        String dateNow = DatabaseWrapper.getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String datePast = dateFormat.format(cal.getTime());

        return getDestinationAmountRange(datePast, dateNow);
    }
    public static ArrayList<Node> getDARLastYear(){
        String dateNow = DatabaseWrapper.getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        String datePast = dateFormat.format(cal.getTime());

        return getDestinationAmountRange(datePast, dateNow);
    }


    public static int getEmployeeCount(String EmployeeType){
        int retNumber = 0;
        Connection connection = null;
        Statement stmt = null;
        String tableName = "LoginDB";
        String sql = null;


        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = connection.createStatement();

            sql = "SELECT * FROM " + tableName + " WHERE position = '"+ EmployeeType + "'";

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
