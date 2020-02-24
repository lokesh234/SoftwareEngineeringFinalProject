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
        STRAIGHT, LEFT, RIGHT, BACKWARDS, ERROR;
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

        double startY = node1.getY();
        double endY = node2.getY();

        double deltaX = endX-startX;
        double deltaY = endY-startY;

        boolean movedNorth = (deltaY > this.turnThreshold);
        boolean movedSouth = ((deltaY < (-1)*this.turnThreshold) && (deltaY < 0)); //ensure number is negative

        boolean movedEast = (deltaX > this.turnThreshold);
        boolean movedWest = ((deltaX < (-1)*this.turnThreshold) && (deltaX < 0)); //ensure number is negative

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

    public RelativeDirection getRelativeDirection(Node node1, Node node2, Node node3){
        AbsoluteDirection dir1 = getAbsoluteDirectionTravelled(node1, node2);
        AbsoluteDirection dir2 = getAbsoluteDirectionTravelled(node2, node3);
        System.out.println("dir 1 is " + dir1);
        System.out.println("dir 2 is " + dir2);

        switch(dir1){
            case NORTH:
                switch (dir2){
                    case NORTH:
                        return RelativeDirection.STRAIGHT;
                    case SOUTH:
                        return RelativeDirection.BACKWARDS;
                    case WEST:
                        return RelativeDirection.LEFT;
                    case EAST:
                        return RelativeDirection.RIGHT;
                }
            case SOUTH:
                switch (dir2){
                    case NORTH:
                        return RelativeDirection.BACKWARDS;
                    case SOUTH:
                        return RelativeDirection.STRAIGHT;
                    case WEST:
                        return RelativeDirection.RIGHT;
                    case EAST:
                        return RelativeDirection.LEFT;
                }
            case EAST:
                switch (dir2){
                    case NORTH:
                        return RelativeDirection.LEFT;
                    case SOUTH:
                        return RelativeDirection.RIGHT;
                    case WEST:
                        return RelativeDirection.BACKWARDS;
                    case EAST:
                        return RelativeDirection.STRAIGHT;
                }
            case WEST:
                switch (dir2){
                    case NORTH:
                        return RelativeDirection.RIGHT;
                    case SOUTH:
                        return RelativeDirection.LEFT;
                    case WEST:
                        return RelativeDirection.STRAIGHT;
                    case EAST:
                        return RelativeDirection.BACKWARDS;
                }
        }
        return RelativeDirection.ERROR;
    }


}
