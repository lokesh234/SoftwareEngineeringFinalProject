package edu.wpi.cs3733.c20.teamU.Database;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Navigation.PathfindAStar;

import java.util.ArrayList;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;



public class NodesDatabase {
    private HashMap<String, Node> nodes;
    private HashMap<String, Edge> edges;

    protected static NodesDatabase getGraph() { return s._graph;}

    private static class s {
        private static final NodesDatabase _graph = new NodesDatabase();
    }

    private NodesDatabase() { //TODO: add relevant database stuff
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

    public void addNode(Node n) {
        nodes.put(n.getID(), n);
    }

    public void addEdge(Edge e) {
        edges.put(e.getID(), e);
    }

    public void removeNode(String ID) { nodes.remove(ID);}

    public void removeEdge(String ID) { edges.remove(ID);}

    public void removeNode(Node n) { nodes.remove(n.getID());}

    public void removeEdge(Edge e) { edges.remove(e.getID());}

    public Node getNodeByLongName(String longName) {
        for (Map.Entry<String, Node> pair : nodes.entrySet()) {
            if (pair.getValue().getLongName().equals(longName)) {
                return pair.getValue();
            }
        }
        return null;
    }

    public Node getNode(String ID) {
        /*
        Returns the node with matching ID, or Null if not found
         */
        if (!nodes.containsKey(ID)) return null;
        return nodes.get(ID);
    }

    public ArrayList<Node> getNodes() {

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

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> l = new ArrayList<>();
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge n = pair.getValue();
            l.add(n);
        }
        return l;
    }


    public Edge getEdge(String ID) {
        /*
        Returns the edge with matching ID, or Null if not found
         */
        if (!edges.containsKey(ID)) return null;
        return edges.get(ID);
    }

    public Edge getEdge(Node start, Node end) {
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

    public Edge getEdge(String startID, String endID) {
        return getEdge(getNode(startID), getNode(endID));
    }

    public Node getNode(int xPos, int yPos) {
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

    public boolean hasNode(Node n) {
        return (getNode(DatabaseWrapper.getNodeID(n)) != null);
    }


    public ArrayList<Edge> getNeighbors(Node n) {
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

    public ArrayList<Edge> getNeighborsNoStairs(Node n) {
        if (!hasNode(n)) return null;
        ArrayList<Edge> neighbors = new ArrayList<Edge>();
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge e = pair.getValue();
            if (e.hasNode(n) && !(n.getNodeType().equals("STAI") && e.getOther(n).getNodeType().equals("STAI"))) neighbors.add(e);
        }
        return neighbors;
    }

    public ArrayList<Edge> getNeighborsNoElev(Node n) {
        if (!hasNode(n)) return null;
        ArrayList<Edge> neighbors = new ArrayList<Edge>();
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge e = pair.getValue();
            if (e.hasNode(n) && !(n.getNodeType().equals("ELEV") && e.getOther(n).getNodeType().equals("ELEV"))) neighbors.add(e);
        }
        return neighbors;
    }

    public ArrayList<Node> getByType(String type) {
        ArrayList<Node> n = new ArrayList<>();
        for (Map.Entry<String, Node> pair : nodes.entrySet()) {
            if (pair.getValue().getNodeType().equals(type)) n.add(pair.getValue());
        }
        return n;
    }

    public ArrayList<Node> getNeighborNodes(Node n) {
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

    public ArrayList<Node> getNeighborNodesNoStairs(Node n) {
        /*
        Similar behavior to getNeighbors(), but returns list of nodes and not edges
         */
        if (!hasNode(n)) return null;
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge e = pair.getValue();
            if (e.hasNode(n) && !(n.getNodeType().equals("STAI") && e.getOther(n).getNodeType().equals("STAI"))) neighbors.add(e.getOther(n));
        }
        return neighbors;
    }

    public ArrayList<Node> getNeighborNodesNoElev(Node n) {
        /*
        Similar behavior to getNeighbors(), but returns list of nodes and not edges
         */
        if (!hasNode(n)) return null;
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for (Map.Entry<String, Edge> pair : edges.entrySet()) {
            Edge e = pair.getValue();
            if (e.hasNode(n) && !(n.getNodeType().equals("ELEV") && e.getOther(n).getNodeType().equals("ELEV"))) neighbors.add(e.getOther(n));
        }
        return neighbors;
    }

    public boolean hasNeighbors(Node n) {
        /*
        Checks if there is are edges associated with n
        Returns false if n not in graph
         */
        if (!hasNode(n)) return false;
        return getNeighbors(n).size() > 0;
    }

    public int cost(Node start, Node end) {
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

    public int costNoWeight(Node start, Node end) {
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

    public boolean hasNode(int xPos, int yPos) {
        /*
        Checks whether a node exists with the given coordinates
         */
        return getNode(xPos, yPos) != null;
    }

    public boolean hasNodeInRange(int xPos, int yPos, int rad) {
        /*
        Checks whether a node exists within a given distance of the given coordinates.
        Useful for checking whether a mouseclick happened on a node, if rad is given as the radius of a node graphical object in pixels
         */
        return getNodeInRange(xPos, yPos, rad) != null;
    }

    public Node getNodeInRange(int xPos, int yPos, int rad) {
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
