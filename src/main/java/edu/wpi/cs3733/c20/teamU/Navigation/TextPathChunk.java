package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.Node;

public class TextPathChunk {
    private TextPathBuilder tpb;
    private Node node1;
    private Node node2;
    private Node node3;

    private TextPathBuilder.RelativeDirection dir;
    private double humanDist;

    public Node getNode1(){
        return this.node1;
    }
    public Node getNode2(){
        return this.node2;
    }
    public Node getNode3(){
        return this.node3;
    }

    public TextPathChunk(Node n1, Node n2, Node n3, TextPathBuilder tpb){
        this.node1 = n1;
        this.node2 = n2;
        this.node3 = n3;

        this.tpb = tpb;

        this.humanDist = tpb.getHumanDistance(tpb.getPixelDistance(n1, n2));
        this.dir = tpb.getRelativeDirection(n1, n2, n3);
    }

    public double getHumanDist() {
        return humanDist;
    }
    public void setHumanDist(double newDist){
        this.humanDist = newDist;
    }

    public TextPathBuilder.RelativeDirection getDir(){
        return this.dir;
    }

    public String toString(){
        String answer = "";
        //@TODO we assume elevators/stairs are perfectly alinged
        if(getHumanDist() > 0.0){
            answer = "Go " + getHumanDist() + " " + tpb.getDistanceUnit();
        }

        // we only want to say turn if its neither straight or diagonal or error
        if(((!(getDir() == TextPathBuilder.RelativeDirection.STRAIGHT)) && !(getDir() == TextPathBuilder.RelativeDirection.DIAGONALLY)) && !(getDir() == TextPathBuilder.RelativeDirection.ERROR)){
            answer += "\nTurn " + getDir();
        }

        // if node 2 is stairs/elevator, and node 1 WASNT stairs/elevator we're entering them
        if(!(this.node1.getNodeType().equals("STAI")) && this.node2.getNodeType().equals("STAI")){
            answer += "\n" + "Enter the stairs";
        }
        if(!(this.node1.getNodeType().equals("ELEV")) && this.node2.getNodeType().equals("ELEV")){
            answer += "\n" + "Enter the elevator";
        }

        //@TODO currently assuming you're only taking all stairs or all elevator
        // if node 1 was stairs/elevator and we're not on it, we're exiting them
        if((this.node1.getNodeType().equals("STAI")) && !this.node2.getNodeType().equals("STAI")){
            answer += "\n" + "Exit the stairs";
        }

        if((this.node1.getNodeType().equals("ELEV")) && !this.node2.getNodeType().equals("ELEV")){
            answer += "\n" + "Exit the elevator";
        }
        return answer;
    }

    public String lastPairToString(){
        String answer = "Go " + tpb.getHumanDistance(tpb.getPixelDistance(this.node2, this.node3)) + " " + tpb.getDistanceUnit();
        return answer;
    }
}
