package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import javax.xml.crypto.Data;
import java.util.*;

public class PathfindAStar extends PathfindAStarTemplate  {


    private PathfindAStar() {
    }

    private static class s {
        private static final PathfindAStar _pathfinder = new PathfindAStar();
    }


    public static PathfindAStar getPathfinder() {
        return s._pathfinder;
    }


    @Override
    int heuristic(Node n, Node end) {
        return NavigationWrapper.dist(n, end);
    }
}
