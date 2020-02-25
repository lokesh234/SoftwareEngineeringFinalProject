package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import javax.xml.crypto.Data;
import java.util.*;

public class PathfindAStar extends PathfindAStarTemplate  {

    private static PathfindAStar _pathfinder = new PathfindAStar();

    private PathfindAStar() {
    }


    public static PathfindAStar getPathfinder() {
        return _pathfinder;
    }


    @Override
    int heuristic(Node n, Node end) {
        return NavigationWrapper.dist(n, end);
    }
}
