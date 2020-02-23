package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.lang.reflect.Array;
import java.util.*;

public class PathfindBFS implements PathfinderInterface {
    private ArrayList<Node> latestPath = new ArrayList<>();
    private int latestCost = -999999;
    private PathfindBFS() {}
    private static PathfindBFS _pathfinder = new PathfindBFS();
    public static PathfindBFS getInstance() { return _pathfinder;}

    public int getLatestCost() {
        /*
        Returns the total cost of the most recent path.
        Returns -999999 if there was no path
        A value in excess of 999999 should be interpreted as one or more nodes along the way being closed, so there is no valid path
         */
        return latestCost;
    }

    public ArrayList<Node> getLatestPath() {
        /*
        Returns the most recent path generated, in reverse order (latestPath.get(0) is the endpoint, and it works backwards from there)
        Returns an empty list if there was no path
         */
        return latestPath;
    }

    private class PriNode implements Comparable<PathfindBFS.PriNode> {
        /*
        Wrapper to hold a node in such a way to allow nodes to be stored in a PriorityQueue
         */
        private Node n;
        private int priority;
        private PriNode(Node x, int pri) {
            n = x;
            priority = pri;
        }

        protected Node getN() { return n;}

        @Override
        public int compareTo(PathfindBFS.PriNode o) {
            /*
            Assumes that there will not be any overflow shenanigans
            please don't prove me wrong
             */
            return this.priority - o.priority;
        }
    }

    public void pathfind(String startID, String endID) {
        pathfind(DatabaseWrapper.getGraph().getNode(startID), DatabaseWrapper.getGraph().getNode(endID));
    }

    public void pathfind(Node start, Node end) {
        /*
        Finds a path from start to end using BFS. To retrieve your results, use getLatestPath() *after* calling this function
         *
         */
        ArrayList<Node> shortestPathList  = new ArrayList<Node>();
        ArrayList<Node> visited =  new ArrayList<Node>();
        if (start == end){
            return;
        }
        Queue<Node> queue = new LinkedList<Node>();
        HashMap<Node, Node> cameFrom = new HashMap<Node, Node>(); //All nodes we've seen, and the node we rode in on, in order (Child, Parent)
        queue.add(start);
        cameFrom.put(start, null);

        while(!queue.isEmpty()){
            Node current = queue.poll();
            if (current.equals(end)) { //This Is The End ,Ed-Boy
                latestPath.clear();
                latestPath.add(end);
                Node next = cameFrom.get(end);
                while (!latestPath.contains(start)) {
                    latestPath.add(next);
                    next = cameFrom.get(next);
                }
                return; //That's everything!
            }
            ArrayList<Node> neighbors;
            if (NavigationWrapper.getStatus() == 0) neighbors = DatabaseWrapper.getGraph().getNeighborNodes(current);
            else if (NavigationWrapper.getStatus() == 1) neighbors = DatabaseWrapper.getGraph().getNeighborNodesNoStairs(current);
            else neighbors = DatabaseWrapper.getGraph().getNeighborNodesNoElev(current);
            for (Node n : neighbors) {
                if(!visited.contains(n)){
                    queue.add(n);
                    visited.add(n);
                    cameFrom.put(n, current);
                }
            }
        }
        latestCost = -999999; //No path found
        latestPath.clear();
    }


}
