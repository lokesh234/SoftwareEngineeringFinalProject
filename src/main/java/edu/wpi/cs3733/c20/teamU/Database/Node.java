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

    public void setWeight(int newWeight) { this.weight = newWeight; }
    public int getX(){ return x; }
    public int getY(){ return y;}
    public int getWeight(){ return weight;}
    public String getID(){ return ID;}
    public String toString(){ return getID(); }
    public int getFloor() { return floor;}
    public String getBuilding() {return building;}
    public String getNodeType() {return nodeType;}
    public String getLongName() { return longName;}
    public String getShortName() { return shortName;}
//    protected void setWeight(int newWeight) {
//        weight = newWeight;
//    }
//
//    public int getX(){ return x;}
//    public int getY(){ return y;}
//    protected int getWeight(){ return weight;}
//    public String getID(){ return ID;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) return this.ID.equals(((Node) obj).ID);
        return false;
    }
}
