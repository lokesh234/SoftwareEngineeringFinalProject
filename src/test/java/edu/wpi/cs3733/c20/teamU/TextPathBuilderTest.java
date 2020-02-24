package edu.wpi.cs3733.c20.teamU;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.Navigation.TextPathBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

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

    static Node node1 = new Node("1",0,0,3 ,"1","HALL","node1","1");
    static Node node2 = new Node("2",10,0,3 ,"1","HALL","node2","1");
    static Node node3 = new Node("3",10,10,3 ,"1","HALL","node3","1");
    static Node node4 = new Node("4",10,20,3 ,"1","HALL","node4","1");
    static Node node5 = new Node("5",20,20,3 ,"1","ELEV","node5","1");
    static Node node6 = new Node("6",20,20,4 ,"1","ELEV","node6","1");
    static Node node7 = new Node("7",30,20,4 ,"1","HALL","node7","1");
    static Node node8 = new Node("8",30,10,4 ,"1","HALL","node8","1");

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

    @Test
    public void testStraight(){
        TextPathBuilder tpb = new TextPathBuilder();
        String answer = ""+tpb.getRelativeDirection(left, center, right);
        assertEquals("STRAIGHT", answer);
    }

    @Test
    public void testLeft(){
        TextPathBuilder tpb = new TextPathBuilder();
        String answer = ""+tpb.getRelativeDirection(down, center, left);
        assertEquals("LEFT", answer);
    }

    @Test
    public void testRight(){
        TextPathBuilder tpb = new TextPathBuilder();
        String answer = ""+tpb.getRelativeDirection(up, center, left);
        assertEquals("RIGHT", answer);
    }

    @Test
    public void testDiagonal(){
        TextPathBuilder tpb = new TextPathBuilder();
        String answer = ""+tpb.getRelativeDirection(up_left , center, down);
        assertEquals("DIAGONALLY", answer);
    }


    @Test
    public void testStraightSecondary(){
        TextPathBuilder tpb = new TextPathBuilder();
        String answer = ""+tpb.getRelativeDirection(up_left , center, down_right);
        assertEquals("STRAIGHT", answer);
    }

    @Test
    public void testRightSecondary(){
        TextPathBuilder tpb = new TextPathBuilder();
        String answer = ""+tpb.getRelativeDirection(up_left , center, down_left);
        assertEquals("RIGHT", answer);
    }

    @Test
    public void testLeftSecondary(){
        TextPathBuilder tpb = new TextPathBuilder();
        String answer = ""+tpb.getRelativeDirection(up_right , center, down_right);
        assertEquals("LEFT", answer);
    }
    @Test
    public void testOneChunk(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        TextPathBuilder tpb = new TextPathBuilder(nodes);
        tpb.generateTextDirections();

        String actual = tpb.getTextDirections();

        assertEquals("Start at node1\nGo 100.0 feet\nTurn LEFT\nGo 100.0 feet\nArrive at node3", actual);
    }
    @Test
    public void testTwoChunks(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);

        TextPathBuilder tpb = new TextPathBuilder(nodes);
        tpb.generateTextDirections();

        String actual = tpb.getTextDirections();

        assertEquals("Start at node1\nGo 100.0 feet\nTurn LEFT\nGo 100.0 feet\nGo 100.0 feet\nArrive at node4", actual);
    }
    @Test
    public void testThreeChunks(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);

        TextPathBuilder tpb = new TextPathBuilder(nodes);
        tpb.generateTextDirections();

        String actual = tpb.getTextDirections();

        assertEquals("Start at node1\nGo 100.0 feet\nTurn LEFT\nGo 200.0 feet\nGo 100.0 feet\nArrive at node5", actual);
    }

    @Test
    public void goToButDoNotEnterElevator(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);

        TextPathBuilder tpb = new TextPathBuilder(nodes);
        tpb.generateTextDirections();

        String actual = tpb.getTextDirections();

        assertEquals("Start at node3\nGo 100.0 feet\nTurn RIGHT\nGo 100.0 feet\nArrive at node5", actual);
    }

    @Test
    public void enterAndExitElevator(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);
        nodes.add(node6);
        nodes.add(node7);

        TextPathBuilder tpb = new TextPathBuilder(nodes);
        tpb.generateTextDirections();

        String actual = tpb.getTextDirections();

        assertEquals("Go 100.0 feet\nEnter the elevator\nGo 100.0 feet\nExit the elevator\nArrive at your destination, node7", actual);

    }
}
