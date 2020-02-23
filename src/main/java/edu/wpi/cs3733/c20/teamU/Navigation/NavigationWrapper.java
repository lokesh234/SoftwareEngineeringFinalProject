package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.util.ArrayList;

public class NavigationWrapper {
    private static String pathType = "A*";
    private static ArrayList<Node> path;
    private static int cost;
    private static int status = 0;
    public ArrayList<Edge> getPath(Node node1, Node node2) {
        return null;
    }

    public static int getStatus() { return status;}
    public static void setStatus(int x) { status = x;}

    /*
    A*: A* Algorithm, unmodified
    BFS: Breadth First Search
    DFS: Depth First Search
    DJI: Dijkstra's Algorithm
    */

    /*
    Status:
     - 0: no preference
     - 1: no stairs
     - 2: no elevators
     */

    public static void setSearchType(String searchType) {
        pathType = searchType;
        switch (pathType) {
            case "A*":
                engine = PathfindAStar.getPathfinder();
                break;
            case "BFS:":
                engine = PathfindBFS.getInstance();
                break;
            case "DFS":
                engine = PathfindDFS.getInstance();
                break;
            case "DJI":
                engine = PathfindDjikstras.getPathfinder();
        }
    }
    public static void pathfind(Node start, Node end) {
        engine.pathfind(start, end);
        path = engine.getLatestPath();
        cost = engine.getLatestCost();
    }
    public static void pathfind(String startID, String endID) {
        engine.pathfind(startID, endID);
        path = engine.getLatestPath();
        cost = engine.getLatestCost();
    }
    public static String getPathType() {
        return pathType;
    }
    public static ArrayList<Node> getPath() { return path;}
    public static int getCost() { return cost;}
    public static PathfinderInterface engine = PathfindAStar.getPathfinder();
    public static int dist(Node start, Node end) {
        /*
        Calculates pythagorean distance between two nodes, rounded to the nearest pixel
        These nodes need not be in the graph, nor have an edge connecting them
         */
        return (int) Math.sqrt(Math.pow((start.getX() - end.getX()), 2) + Math.pow((start.getY() - end.getY()), 2));
    }
}