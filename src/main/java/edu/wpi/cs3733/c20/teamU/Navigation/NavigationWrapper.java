package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.Edge;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.util.ArrayList;

public class NavigationWrapper {
    private static String pathType = "A*";
    public ArrayList<Edge> getPath(Node node1, Node node2) {
        return null;
    }

    public static void setSearchType(String searchType) {
        pathType = searchType;
    }
    public static String getPathType() {
        return pathType;
    }
}