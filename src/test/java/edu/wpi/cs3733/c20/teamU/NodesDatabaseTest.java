package edu.wpi.cs3733.c20.teamU;

import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Database.NodesDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodesDatabaseTest {
    @Test
    public void testAddAndRemoveNode(){
        NodesDatabase nd = new NodesDatabase();
        Node testNode1 = new Node("1",1,2,3 ,"1","1","1","1");
        Node testNode2 = new Node("12",1,2,3 ,"1","1","1","1");
        nd.addNode(testNode1);
        assertEquals(testNode1, nd.getNode("1"));
        assertNull(nd.getNode("12"));
        nd.removeNode("1");
        assertNull(nd.getNode("1"));
    }


    @Test
    public void testAddAndRemoveEdge(){
        NodesDatabase nd = new NodesDatabase();
        Node testNode1 = new Node("1",1,2,3 ,"1","1","1","1");
        Node testNode2 = new Node("12",1,2,3 ,"1","1","1","1");
        Edge testEdge1 = new Edge(testNode1, testNode2, 10, "edge1");
        Edge testEdge2 = new Edge(testNode2, testNode1, 5, "edge2");

        nd.addEdge(testEdge1);
        assertEquals(testEdge1, nd.getEdge("edge1"));
        assertNull(nd.getEdge("edge2"));
        nd.removeEdge("edge1");
        assertNull(nd.getEdge(testNode1, testNode2));
    }
}
