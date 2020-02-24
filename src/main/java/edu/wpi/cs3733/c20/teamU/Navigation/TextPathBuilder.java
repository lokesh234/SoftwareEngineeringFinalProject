package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.Node;
import java.util.ArrayList;
import java.util.LinkedList;

public class TextPathBuilder {
    private ArrayList<Node> nodes;
    private LinkedList<TextPathChunk> chunks;
    private String directions;
    private String distanceUnit;

    private double turnThreshold = 5;
    private double pixelsPerMeter = 10;
    private double pixelsPerFoot = 10;

    public TextPathBuilder(){
        this.chunks = new LinkedList<TextPathChunk>();
        this.distanceUnit = "feet"; //SAE units by default
    }
    public TextPathBuilder(ArrayList<Node> nodes){
        this.chunks = new LinkedList<TextPathChunk>();
        this.nodes = nodes;
        this.distanceUnit = "feet"; //SAE units by default
    }

    private ArrayList<Node> getNodes(){
        return this.nodes;
    }

    enum AbsoluteDirection{
        NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST, ERROR;
    }

    enum RelativeDirection{
        STRAIGHT, LEFT, RIGHT, BACKWARDS, DIAGONALLY, ERROR;
    }


    //...admin sets the units here...
    public void setDistanceUnit(String unitType){
        this.distanceUnit = unitType;
    }
    public String getDistanceUnit(){
        return this.distanceUnit;
    }

    public String getTextDirections(){
        return this.directions;
    }
    public LinkedList<TextPathChunk> getChunks(){
        return this.chunks;
    }

    public void generateChunks(){
        //@TODO retrieve actual nodes from database
//        ArrayList<Node> nodes = DatabaseWrapper.getGraph().getNodes();
        ArrayList<Node> nodes = getNodes();

        //reset the chunks list in case this was called multiple times
        this.chunks = new LinkedList<TextPathChunk>();

        Node lastNode;
        Node thisNode;
        Node nextNode;
        TextPathChunk thisChunk;

        for(int index = 1; index <= nodes.size()-2; index++){
            lastNode = nodes.get(index-1);
            thisNode = nodes.get(index);
            nextNode = nodes.get(index+1);

            thisChunk = new TextPathChunk(lastNode, thisNode, nextNode, this);
            this.chunks.add(thisChunk);
        }
    }

    public void generateTextDirections(){
        generateChunks();
        this.directions = "";
        LinkedList<TextPathChunk> cleanedChunks = new LinkedList<TextPathChunk>();
        TextPathChunk lastChunk;
        TextPathChunk thisChunk;

        String start = getChunks().getFirst().getNode1().getLongName();
        String destination = getChunks().getLast().getNode3().getLongName();

//        for(int index = 1; index < getChunks().size(); index++){
//            // compare the last chunk and this one...
//            lastChunk = this.chunks.get(index-1);
//            thisChunk = this.chunks.get(index);
//
//            //if the directions are the same, we want to change last chunk to add the distance
//            //and remove thisChunk from chunks
//            //@TODO consolidate chunks without deleting stairs/elevators
//            if(lastChunk.getDir().equals(thisChunk.getDir())){
//                double lastDist = lastChunk.getHumanDist();
//                double thisDist = thisChunk.getHumanDist();
//
//                lastChunk.setHumanDist(lastDist+thisDist);
//
//                this.chunks.remove(thisChunk);
//                index --;
//                continue;
//            }
//        }

        // go through and get the distances...
        this.directions += "Start at " + start + "\n";
        for(int index = 0; index < getChunks().size(); index++){
            TextPathChunk c = this.chunks.get(index);
            this.directions += c.toString() + "\n";

            // if its the last chunk, we need the last pair to the destination
            if((index == getChunks().size()-1)){
                this.directions += c.lastPairToString();
            }
        }
        this.directions += "\nArrive at " + destination;
    }

    public double getPixelDistance(Node node1, Node node2){
        double startX = node1.getX();
        double endX = node2.getX();

        double startY = node1.getY();
        double endY = node2.getY();

        double deltaX = Math.abs(endX-startX);
        double deltaY = Math.abs(endY-startY);

        return Math.hypot(deltaX, deltaY);
    }

    public double getHumanDistance(double pixelDistance){
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
                    default:
                        return RelativeDirection.DIAGONALLY;
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
                    default:
                        return RelativeDirection.DIAGONALLY;
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
                    default:
                        return RelativeDirection.DIAGONALLY;
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
                    default:
                        return RelativeDirection.DIAGONALLY;
                }
            case NORTHEAST:
                switch(dir2){
                    case NORTHEAST:
                        return RelativeDirection.STRAIGHT;
                    case SOUTHWEST:
                        return RelativeDirection.BACKWARDS;
                    case SOUTHEAST:
                        return RelativeDirection.RIGHT;
                    case NORTHWEST:
                        return RelativeDirection.LEFT;
                    default:
                        return RelativeDirection.DIAGONALLY;
                }
            case SOUTHEAST:
                switch(dir2){
                    case NORTHEAST:
                        return RelativeDirection.LEFT;
                    case SOUTHWEST:
                        return RelativeDirection.RIGHT;
                    case SOUTHEAST:
                        return RelativeDirection.STRAIGHT;
                    case NORTHWEST:
                        return RelativeDirection.BACKWARDS;
                    default:
                        return RelativeDirection.DIAGONALLY;
                }
            case SOUTHWEST:
                switch(dir2){
                    case NORTHEAST:
                        return RelativeDirection.BACKWARDS;
                    case SOUTHWEST:
                        return RelativeDirection.STRAIGHT;
                    case SOUTHEAST:
                        return RelativeDirection.LEFT;
                    case NORTHWEST:
                        return RelativeDirection.RIGHT;
                    default:
                        return RelativeDirection.DIAGONALLY;
                }
            case NORTHWEST:
                switch(dir2){
                    case NORTHEAST:
                        return RelativeDirection.RIGHT;
                    case SOUTHWEST:
                        return RelativeDirection.LEFT;
                    case SOUTHEAST:
                        return RelativeDirection.BACKWARDS;
                    case NORTHWEST:
                        return RelativeDirection.STRAIGHT;
                    default:
                        return RelativeDirection.DIAGONALLY;
                }


        }
        return RelativeDirection.ERROR;
    }


}
