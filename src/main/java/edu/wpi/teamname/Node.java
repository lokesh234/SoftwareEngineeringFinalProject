package edu.wpi.teamname;

public class Node {
    private int x;
    private int y;
    private int weight;
    private String ID;

    public Node(int xPos, int yPos, String ID) {
        /*
        Class to represent a node in an undirected graph
        Weight can be set to represent closures or hazards
         */
        x = xPos;
        y = yPos;
        this.ID = ID;
    }

    protected void setWeight(int newWeight) {
        weight = newWeight;
    }

    protected int getX(){ return x;}
    protected int getY(){ return y;}
    protected int getWeight(){ return weight;}
    protected String getID(){ return ID;}
}
