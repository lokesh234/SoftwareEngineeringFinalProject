package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;

import java.util.ArrayList;
import java.util.LinkedList;

public class TextPathBuilder {
    private LinkedList<Node> landmarks;
    private LinkedList<String> directions;
    private String distanceUnit;

    private double turnThreshold = 5;
    private double pixelsPerMeter = 10;
    private double pixelsPerFoot = 10;

    enum AbsoluteDirection{
        NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, ERROR;
    }

    enum RelativeDirection{
        STRAIGHT, LEFT, RIGHT, ERROR;
    }

    //...admin sets the units here...
    public void setDistanceUnit(String unitType){
        this.distanceUnit = unitType;
    }
    public String getDistanceUnit(){
        return this.distanceUnit;
    }

    LinkedList<String> getTextDirections(){
        generateTextDirections();
        return this.directions;
    }

    private void generateTextDirections(){
        ArrayList<Node> nodes = DatabaseWrapper.getGraph().getNodes();

        Node lastNode;
        Node thisNode;

    }

    private double getPixelDistance(Node node1, Node node2){
        double startX = node1.getX();
        double endX = node2.getX();

        double startY = node1.getX();
        double endY = node2.getY();

        double deltaX = Math.abs(endX-startX);
        double deltaY = Math.abs(endY-startY);

        return Math.hypot(deltaX, deltaY);
    }

    private double getHumanDistance(double pixelDistance){
        switch(getDistanceUnit()){
            case "feet":
                return pixelDistance*this.pixelsPerFoot;
            case "meters":
                return pixelDistance*this.pixelsPerMeter;
        }
        return -999.99; //something bad...
    }

    public AbsoluteDirection getAbsoluteDirectionTravelled(Node node1, Node node2){
        double startX = node1.getX();
        double endX = node2.getX();

        double startY = node1.getX();
        double endY = node2.getY();

        double deltaX = endX-startX;
        double deltaY = endY-startY;

        boolean movedNorth = (deltaY > this.turnThreshold);
        boolean movedSouth = (deltaY < (-1)*this.turnThreshold);

        boolean movedEast = (deltaX > this.turnThreshold);
        boolean movedWest = (deltaX < (-1)*this.turnThreshold);

        //check combination directions first
        if(movedNorth && movedEast){
            return AbsoluteDirection.NORTHEAST;
        }
        if(movedNorth && movedWest){
            return AbsoluteDirection.NORTHWEST;
        }
        if(movedSouth && movedEast){
            return AbsoluteDirection.SOUTHEAST;
        }
        if(movedSouth && movedWest){
            return AbsoluteDirection.SOUTHWEST;
        }

        //if not a combination direction, is a cardinal direction
        if(movedNorth){
            return AbsoluteDirection.NORTH;
        }
        if(movedSouth){
            return AbsoluteDirection.SOUTH;
        }
        if(movedEast){
            return AbsoluteDirection.EAST;
        }
        if(movedWest){
            return AbsoluteDirection.WEST;
        }
        return AbsoluteDirection.ERROR;
    }


}
