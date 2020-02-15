package edu.wpi.cs3733.c20.teamU.Database;

import lombok.Getter;

@Getter
public class Node {
    private int x;
    private int y;
    private int weight;
    private String ID;
    private int floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;

    public Node(int xPos, int yPos, String ID) {
        /*
        Class to represent a node in an undirected graph
        Weight can be set to represent closures or hazards
         */
        x = xPos;
        y = yPos;
        this.ID = ID;
    }

    public Node(String nodeID, int xcoord, int ycoord, int floor, String building, String nodeType, String longName, String shortName) {
        this.ID = nodeID;
        this.x = xcoord;
        this.y = ycoord;
        this.floor= floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }

    protected void setWeight(Node node, int newWeight) { node.weight = newWeight; }
    protected int getX(Node node){ return node.x; }
    protected int getY(Node node){ return node.y;}
    protected int getWeight(Node node){ return node.weight;}
    protected String getID(Node node){ return node.ID;}
    protected String toString(Node node){ return node.getID(); }
    protected int getFloor(Node node) { return node.floor;}
    protected String getBuilding(Node node) {return node.building;}
    protected String getNodeType(Node node) {return node.nodeType;}
    protected String getLongName(Node node) { return node.longName;}
    protected String getShortName(Node node) { return node.shortName;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) return this.ID.equals(((Node) obj).ID);
        return false;
    }
}
