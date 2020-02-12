package edu.wpi.cs3733.c20.teamU;

import javax.xml.crypto.Data;
import java.util.ArrayList;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class NodesDatabase {
    private HashMap<String, Node> nodes;
    private HashMap<String, Edge> edges;

    public NodesDatabase() { //TODO: add relevant database stuff
        /*
        Automatically constructs the nodes and edges from Database
         */
        nodes = new HashMap<String, Node>();
        edges = new HashMap<String, Edge>();
        Database.getNodes(nodes);
        Database.getEdges(edges, nodes);
    }

    public void readCSV(String filename){} //TODO: stub
    private void createCSV(String path){} //TODO: stub

    protected void addNode(Node n) {
        nodes.put(n.getID(), n);
    }

    protected void addEdge(Edge e) {
        edges.put(e.getID(), e);
    }

    protected void removeNode(String ID) { nodes.remove(ID);}

    protected void removeEdge(String ID) { edges.remove(ID);}

    protected void removeNode(Node n) { nodes.remove(n.getID());}

    protected void removeEdge(Edge e) { edges.remove(e.getID());}

    protected Node getNode(String ID) {
        /*
        Returns the node with matching ID, or Null if not found
         */
        if (!nodes.containsKey(ID)) return null;
        return nodes.get(ID);
    }

    protected ArrayList<Node> getNodes() {
        /*
        Returns an ArrayList of all nodes
         */
        ArrayList<Node> l = new ArrayList<>();
        for (Map.Entry<String, Node> pair : nodes.entrySet()) {
            Node n = pair.getValue();
            l.add(n);
        }
        return l;
    }

    protected Edge getEdge(String ID) {
        /*
        Returns the edge with matching ID, or Null if not found
         */
        if (!edges.containsKey(ID)) return null;
        return edges.get(ID);
    }

    protected Edge getEdge(Node start, Node end) {
        /*
        Returns the edge with endpoints start and end
        order of start and end does not matter
        Returns Null if not found
         */
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge e = pair.getValue();
            if (e.hasNode(start) && e.hasNode(end)) return e;
        }
        return null;
    }

    protected Edge getEdge(String startID, String endID) {
        return getEdge(this.getNode(startID), this.getNode(endID));
    }

    protected Node getNode(int xPos, int yPos) {
        /*
        Returns the node at given (x,y) coords, or Null if not found
        NOTE: undefined behavior if multiple nodes share the same x,y coords
         */
        for (Map.Entry<String, Node> pair : nodes.entrySet()) {
            Node n = pair.getValue();
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
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge e = pair.getValue();
            if (e.hasNode(n)) neighbors.add(e);
        }
        return neighbors;
    }

    protected ArrayList<Node> getNeighborNodes(Node n) {
        /*
        Similar behavior to getNeighbors(), but returns list of nodes and not edges
         */
        if (!hasNode(n)) return null;
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge e = pair.getValue();
            if (e.hasNode(n)) neighbors.add(e.getOther(n));
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

    protected int costNoWeight(Node start, Node end) {
         /*
        Gets the cost of travelling from start to end
        NOTE - DOES NOT TAKE NODE WEIGHT INTO ACCOUNT
        Assumes that there is a limit of one edge connecting two given nodes
        Returns 999999 if no connection
         */
        if (!hasNeighbors(start)) return 999999;
        for (Edge e : getNeighbors(start)) {
            if (e.getOther(start).equals(end)) return e.getDist();
        }
        return 999999;
    }

    protected boolean hasNode(int xPos, int yPos) {
        /*
        Checks whether a node exists with the given coordinates
         */
        return getNode(xPos, yPos) != null;
    }

    protected boolean hasNodeInRange(int xPos, int yPos, int rad) {
        /*
        Checks whether a node exists within a given distance of the given coordinates.
        Useful for checking whether a mouseclick happened on a node, if rad is given as the radius of a node graphical object in pixels
         */
        return getNodeInRange(xPos, yPos, rad) != null;
    }

    protected Node getNodeInRange(int xPos, int yPos, int rad) {
        /*
        Returns the node within a given distance of the given coordinates.
        Useful for determining the result of a mouseclick, if rad is given as the radius of a node graphical object in pixels
        Returns null if no node found
         */
        for (Map.Entry<String, Node> pair : nodes.entrySet()) {
            Node n = pair.getValue();
            if (dist(xPos, yPos, n.getX(), n.getY()) <= rad) return n;
        }
        return null;
    }

    public void update() {
        nodes.clear();
        edges.clear();
        Database.getNodes(nodes);
        Database.getEdges(edges, nodes);
    }

    public static int dist(int x1, int y1, int x2, int y2) {
        /*
        Returns Pythagorean distance between two points
         */
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1- y2), 2));
    }
}
