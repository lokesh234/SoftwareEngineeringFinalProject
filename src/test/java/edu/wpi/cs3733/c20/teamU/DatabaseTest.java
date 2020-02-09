package edu.wpi.cs3733.c20.teamU;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DatabaseTest {
    //please dont kill my tests :(
    @Test
    public void testDatabaseExists() {
        Database db = new Database();
        db.UDBInitializer();
        Connection conn = null;
        Statement stmt = null;
    try{
        conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
        stmt = conn.createStatement();

        assertTrue(db.printTable(stmt, "MapNodesU"), "Table MapNodesU Not Found");
        assertTrue(db.printTable(stmt, "MapEdgesU"), "Table MapEdgesU Not Found");

        stmt.close();
        conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
