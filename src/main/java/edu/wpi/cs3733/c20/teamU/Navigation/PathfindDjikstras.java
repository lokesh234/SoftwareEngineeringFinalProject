package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PathfindDjikstras extends PathfindAStarTemplate {


    private PathfindDjikstras() {

    }

    public static PathfindDjikstras getPathfinder() {
        return s._pathfinder;
    }

    private static class s {
        private static final PathfindDjikstras _pathfinder = new PathfindDjikstras();
    }

    protected int heuristic(Node n, Node end) {
        return 0;
    }
}
