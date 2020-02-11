package edu.wpi.cs3733.c20.teamU;


import org.junit.jupiter.api.Test;
import java.io.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class NodeEdgeTest {
    @Test
    public void testNodes(){
        Node n1 = new Node("001",0,0,4,"b1","HALL","long hall", "shall");
        Node n2 = new Node("001",0,0,4,"b1","HALL","long hall", "shall");
        assertTrue(n1.equals(n2), "Identical nodes weren't found equal");
        assertEquals(n1.toString(),n2.toString());
        assertEquals(n2.toString(),"001");
    }

    @Test
    public void testEdges(){

    }

}
