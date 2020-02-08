package edu.wpi.teamname;

import java.util.ArrayList;

import java.lang.Math;

public class NodesDatabase {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    public NodesDatabase() { //TODO: add relevant database stuff
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
    }

    public void readCSV(String filename){} //TODO: stub
    private void createCSV(String path){} //TODO: stub

    protected void addNode(Node n) {
        nodes.add(n);
    }

    protected void addEdge(Edge e) {
        edges.add(e);
    }

    protected Node getNode(String ID) {
        /*
        Returns the node with matching ID, or Null if not found
         */
        for (Node node : nodes) {
            if (node.getID().equals(ID)) return node;
        }
        return null;
    }

    protected Edge getEdge(String ID) {
        /*
        Returns the edge with matching ID, or Null if not found
         */
        for (Edge edge : edges) {
            if (edge.getID().equals(ID)) return edge;
        }
        return null;
    }

    protected Node getNode(int xPos, int yPos) {
        /*
        Returns the node at given (x,y) coords, or Null if not found
        NOTE: undefined behavior if multiple nodes share the same x,y coords
         */
        for (Node n : nodes) {
            if (n.getX() == xPos && n.getY() == yPos) return n;
        }
        return null;
    }

    protected boolean hasNode(Node n) {
        return (getNode(n.getID()) != null);
    }

    protected ArrayList<Edge> getNeighbors(Node n) {
        /*
        Returns list of all edges connecting to the given node
        Returns Null if n not in graph
        Returns empty ArrayList if n has no edges connected
         */
        if (!hasNode(n)) return null;
        ArrayList<Edge> neighbors = new ArrayList<Edge>();
        for (Edge e : edges) {
            if (e.hasNode(n)) neighbors.add(e);
        }
        return neighbors;
    }

    protected boolean hasNeighbors(Node n) {
        /*
        Checks if there is are edges associated with n
        Returns false if n not in graph
         */
        if (!hasNode(n)) return false;
        return getNeighbors(n).size() > 0;
    }

    protected int cost(Node start, Node end) {
        /*
        Gets the cost of travelling from start to end
        NOTE - ONLY END'S WEIGHT ATTRIBUTE COUNTS TOWARDS THIS MEASURE
        Assumes that there is a limit of one edge connecting two given nodes
        Returns 999999 if no connection
         */
        if (!hasNeighbors(start)) return 999999;
        for (Edge e : getNeighbors(start)) {
            if (e.getOther(start).equals(end)) return e.getDist() + end.getWeight();
        }
        return 999999;
    }

    public int dist(Node start, Node end) {
        /*
        Calculates pythagorean distance between two nodes, rounded to the nearest pixel
        These nodes need not be in the graph, nor have an edge connecting them
         */
        return (int) Math.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
    }
}
