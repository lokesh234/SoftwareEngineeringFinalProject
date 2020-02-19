package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.util.*;

public class PathfindAStar implements PathfinderInterface {
    /*
    Class to pathfind
     */
    private int latestCost = -999999;
    private ArrayList<Node> latestPath = new ArrayList<>();
    private static PathfindAStar _pathfinder = new PathfindAStar();

    private PathfindAStar() {
        latestCost = -999999;
        latestPath = new ArrayList<Node>();
    }

    public static PathfindAStar getPathfinder() {
        return _pathfinder;
    }

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

    private class PriNode implements Comparable<PriNode> {
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
        public int compareTo(PriNode o) {
            /*
            Assumes that there will not be any overflow shenanigans
            please don't prove me wrong
             */
            return this.priority - o.priority;
        }
    }

    public void pathfind(String startID, String endID) {
        //starSingular(graph.getNode(startID), graph.getNode(endID), graph);
        pathfind(DatabaseWrapper.getGraph().getNode(startID), DatabaseWrapper.getGraph().getNode(endID));
    }



    public void pathfind(Node start, Node end){
        /*
        Finds a path from start to end using A*. To retrieve your results, use getLatestCost() or getLatestPath() *after* calling this function
         */
        PriorityQueue<PriNode> frontier = new PriorityQueue<PriNode>(); //All nodes we should explore, in order from least cost to most
        frontier.add(new PriNode(start, 0));

        HashMap<Node, Node> cameFrom = new HashMap<Node, Node>(); //All nodes we've seen, and the node we rode in on, in order (Child, Parent)
        HashMap<Node, Integer> costSoFar = new HashMap<Node, Integer>(); //All nodes we've seen, and how expensive it was to get there

        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll().getN();
            if (current.equals(end)) {
                latestCost = costSoFar.get(end); //We've reached the end! We just need to update latestCost and latestPath
                latestPath.clear();
                latestPath.add(end);
                Node next = cameFrom.get(end);
                while (!latestPath.contains(start)) {
                    latestPath.add(next);
                    next = cameFrom.get(next);
                }
                return; //That's everything!
            }
            //Most likely, we have to go through the search process before we reach the goal
            //graph.getNeighborNodes(current))
            for (Node n : DatabaseWrapper.getGraph().getNeighborNodes(current)) {
                //int newCost = costSoFar.get(current) + graph.cost(current, n);
                int newCost = costSoFar.get(current) + DatabaseWrapper.getGraph().cost(current, n);
                if (!costSoFar.containsKey(n) || newCost < costSoFar.get(n)) { //Either we've been here before, but this time we got here cheaper, or this is new
                    costSoFar.put(n, newCost);
                    cameFrom.put(n, current);
                    int priority = newCost + NavigationWrapper.dist(n, end);
                    frontier.add(new PriNode(n, priority));
                }
            }
        }
        latestCost = -999999; //We've looked everywhere without reaching the end - we need to update latestCost and latestPath to reflect this
        latestPath.clear();
        return;
    }




}
