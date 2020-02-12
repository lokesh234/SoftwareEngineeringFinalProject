package edu.wpi.cs3733.c20.teamU;

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

    protected void setWeight(int newWeight) {
        weight = newWeight;
    }

    protected int getX(){ return x;}
    protected int getY(){ return y;}
    protected int getWeight(){ return weight;}
    protected String getID(){ return ID;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) return this.ID.equals(((Node) obj).ID);
        return false;
    }

    @Override
    public String toString(){
        return this.getID();
    }

    public String getNodeID(){
        return  this.ID;
    }

    public int getFloor() { return this.floor;}
    public String getBuilding() {return this.building;}
    public String getNodeType() {return this.nodeType;}
    public String getLongName() { return this.longName;}
    public String getShortName() { return this.shortName;}
    public int getXCoord() {return this.x;}
    public int getYCoord() {return this.y;}
}
