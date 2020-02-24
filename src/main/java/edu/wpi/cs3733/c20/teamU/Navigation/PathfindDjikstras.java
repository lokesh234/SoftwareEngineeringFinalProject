package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PathfindDjikstras extends PathfindAStarTemplate {

    private static PathfindDjikstras _pathfinder = new PathfindDjikstras();

    private PathfindDjikstras() {

    }

    public static PathfindDjikstras getPathfinder() {
        return _pathfinder;
    }

    protected int heuristic(Node n, Node end) {
        return 0;
    }
}
