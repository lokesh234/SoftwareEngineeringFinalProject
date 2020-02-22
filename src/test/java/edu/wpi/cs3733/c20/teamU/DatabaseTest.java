package edu.wpi.cs3733.c20.teamU;

import edu.wpi.cs3733.c20.teamU.Database.Database;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class DatabaseTest {
    //please dont kill my tests :(
    @Test
    public void testDatabaseExists() {

        DatabaseWrapper.Initializer();
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            stmt = conn.createStatement();

            assertTrue(DatabaseWrapper.printTable(stmt, "MapNodesU"), "Table MapNodesU Not Found");
            assertTrue(DatabaseWrapper.printTable(stmt, "MapEdgesU"), "Table MapEdgesU Not Found");

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

        DatabaseWrapper.getNodes(nhm);
        DatabaseWrapper.getEdges(ehm, nhm);

        assertTrue(!nhm.isEmpty());
        assertTrue(!ehm.isEmpty());
    }

    @Test
    public void testCheckCred(){
        assertNotNull(DatabaseWrapper.checkCred("admin","password"));
        assertNull(DatabaseWrapper.checkCred("a","k"));
        assertNull(DatabaseWrapper.checkCred("admin", "wrong"));
    }


}
