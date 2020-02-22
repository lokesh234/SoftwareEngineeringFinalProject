package edu.wpi.cs3733.c20.teamU;

import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.HashMap;

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

    @Test
    public void testGetEdgesAndNodes(){
        HashMap<String, Node> nhm = new HashMap<String, Node>();
        HashMap<String, Edge> ehm = new HashMap<String, Edge>();

        Database.getNodes(nhm);
        Database.getEdges(ehm, nhm);

        assertTrue(!nhm.isEmpty());
        assertTrue(!ehm.isEmpty());
    }

    @Test
    public void testCheckCred(){
        assertTrue(Database.checkCred("admin","password"));
        assertTrue(!Database.checkCred("a","k"));
    }


}
