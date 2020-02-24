package edu.wpi.cs3733.c20.teamU;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Navigation.TextPathBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TextPathBuilderTest {
    static Node center = new Node("1",0,0,3 ,"1","1","center","1");
    static Node left = new Node("2",-10,0,3 ,"1","1","left","1");
    static Node right = new Node("2",10,0,3 ,"1","1","right","1");
    static Node up = new Node("2",0,10,3 ,"1","1","up","1");
    static Node down = new Node("2",0,-10,3 ,"1","1","down","1");

    static Node up_left = new Node("2",-10,10,3 ,"1","1","up_left","1");
    static Node up_right = new Node("2",10,10,3 ,"1","1","up_right","1");
    static Node down_left = new Node("2",-10,-10,3 ,"1","1","down_left","1");
    static Node down_right = new Node("2",10,-10,3 ,"1","1","down_right","1");

    @Test
    public void testAbsUp(){
        TextPathBuilder tpb = new TextPathBuilder();
        assertEquals("NORTH", ""+tpb.getAbsoluteDirectionTravelled(center, up));
    }
    @Test
    public void testAbsDown(){
        TextPathBuilder tpb = new TextPathBuilder();
        assertEquals("SOUTH", ""+tpb.getAbsoluteDirectionTravelled(center, down));
    }
    @Test
    public void testAbsLeft(){
        TextPathBuilder tpb = new TextPathBuilder();
        assertEquals("WEST", ""+tpb.getAbsoluteDirectionTravelled(center, left));
    }
    @Test
    public void testAbsRight(){
        TextPathBuilder tpb = new TextPathBuilder();
        assertEquals("EAST", ""+tpb.getAbsoluteDirectionTravelled(center, right));
    }
    @Test
    public void testAbsUpRight(){
        TextPathBuilder tpb = new TextPathBuilder();
        assertEquals("NORTHEAST", ""+tpb.getAbsoluteDirectionTravelled(down_left, up_right));
    }




}
