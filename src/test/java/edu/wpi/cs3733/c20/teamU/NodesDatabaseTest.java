package edu.wpi.cs3733.c20.teamU;

import edu.wpi.cs3733.c20.teamU.Database.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodesDatabaseTest {
    @Test
    public void testAddAndRemoveNode(){
        NodesDatabase nd = DatabaseWrapper.getGraph();
        Node testNode1 = new Node("1",1,2,3 ,"1","1","1","1");
        Node testNode2 = new Node("12",1,2,3 ,"1","1","1","1");
       //nd.addNode(testNode1);
        nd.addNode(testNode1);
        //assertEquals(testNode1, nd.getNode("1"));
        assertEquals(testNode1, nd.getNode("1"));
        //assertNull(nd.getNode("12"));
        assertNull(nd.getNode("12"));
        //nd.removeNode("1");
        nd.getNode("1");
        //assertNull(nd.getNode("1"));
        assertNull(nd.getNode("1"));
    }


    @Test
    public void testAddAndRemoveEdge(){
        NodesDatabase nd = DatabaseWrapper.getGraph();
        Node testNode1 = new Node("1",1,2,3 ,"1","1","1","1");
        Node testNode2 = new Node("12",1,2,3 ,"1","1","1","1");
        Edge testEdge1 = new Edge(testNode1, testNode2, 10, "edge1");
        Edge testEdge2 = new Edge(testNode2, testNode1, 5, "edge2");

        nd.addEdge(testEdge1);
//        nd.nodeDatabaseAddEdge(testEdge1, nd);
        assertEquals(testEdge1, nd.getEdge("edge1"));
        assertNull(nd.getEdge("edge2"));
        nd.removeEdge("edge1");
//        assertEquals(testEdge1, DatabaseWrapper.nodeDatabaseGetEdge("edge1", nd));
//        assertNull(DatabaseWrapper.nodeDatabaseGetEdge("edge2", nd));
//        DatabaseWrapper.nodeDatabaseRemoveEdge("edge1", nd);
        assertNull(nd.getEdge(testNode1, testNode2));
//        assertNull(DatabaseWrapper.nodeDatabaseGetEdge(testNode1, testNode2, nd));
    }
}
