package edu.wpi.cs3733.c20.teamU.Database;

public class Edge {
    private Node start;
    private Node end;
    private int dist;
    private String ID;

    public Edge(Node startNode, Node endNode, int dist, String ID) {
        /*
        Class to represent an edge between two nodes
        dist is the physical distance between the two nodes, and should be automatically calculated
        Specific adjustments should be done through Node's weight attribute
         */
        start = startNode;
        end = endNode;
        this.dist = dist;
        this.ID = ID;
    }

    public Node getStart() { return start;}
    public Node getEnd() { return end;}
    public String getStartID() { return start.getID();}
    public String getEndID() { return end.getID();}
    public int getDist() { return dist;}
    public String getID() {return ID;}

    protected Node getOther(Node n) {
        /*
        Returns the endpoint NOT passed to this function.
        For an edge between nodes A and B, getOther(A) returns B and getOther(B) returns A
        Assumes n is one of the edge's endpoints, otherwise returns Null
         */
        if (n.equals(start)) return end;
        if (n.equals(end)) return start;
        return null;
    }

    protected boolean hasNode(Node n) {
        /*
        Checks if either endpoint is n
         */
        return (start.equals(n)) || (end.equals(n));
    }
}
