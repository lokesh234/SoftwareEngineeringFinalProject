package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.util.*;

public interface PathfinderInterface {
    public abstract ArrayList<Node> getLatestPath();
    public abstract  int getLatestCost();
    public abstract void pathfind(Node start, Node end);
    public abstract void pathfind(String startID, String endID);
}
