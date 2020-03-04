package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.Node;

public class TextPathChunk {
    private TextPathBuilder tpb;
    private Node node1;
    private Node node2;
    private Node node3;

    private TextPathBuilder.RelativeDirection dir;
    private double humanDist;

    public TextPathChunk(Node n1, Node n2, Node n3){
        this.node1 = n1;
        this.node2 = n2;
        this.node3 = n3;

        //this.humanDist = tpb.getHumanDistance(tpb.getPixelDistance(n1, n2));
        //this.dir = tpb.getRelativeDirection(n1, n2, n3);

    }

    public TextPathChunk(Node n1, Node n2, Node n3, TextPathBuilder tpb){
        this.node1 = n1;
        this.node2 = n2;
        this.node3 = n3;

        this.tpb = tpb;

        this.humanDist = tpb.getHumanDistance(tpb.getPixelDistance(n1, n2));
        this.dir = tpb.getRelativeDirection(n1, n2, n3);
    }

    public Node getNode1(){
        return this.node1;
    }
    public Node getNode2(){
        return this.node2;
    }
    public Node getNode3(){
        return this.node3;
    }

    public void setNode3(Node newNode){
        this.node3 = newNode;
    }

    public void setTextPathBuilder(TextPathBuilder tpb){
        this.tpb = tpb;
    }

    public int getHumanDist() {
        return (int)Math.floor(humanDist*100)/100;
    }
    public void setHumanDist(double newDist){
        this.humanDist = newDist;
    }

    public TextPathBuilder.RelativeDirection getDir(){
        return this.dir;
    }

    public String toString(){
        String answer = "";
        // exit the stairs before we travel more
        boolean firstIsStair = this.node1.getNodeType().equals("STAI");
        boolean secondIsStair = this.node2.getNodeType().equals("STAI");
        boolean thirdIsStair = this.node3.getNodeType().equals("STAI");

        boolean firstIsElev = this.node1.getNodeType().equals("ELEV");
        boolean secondIsElev = this.node2.getNodeType().equals("ELEV");
        boolean thirdIsElev = this.node3.getNodeType().equals("ELEV");

        //@TODO currently assuming you're only taking all stairs or all elevator
        // if node 1 was stairs/elevator and we're not on it, we're exiting them
        if((firstIsStair && secondIsStair) && !thirdIsStair){
            answer += "Exit the stairs at floor " + this.node3.getFloor() + "\n";
            answer += "FLOOR " + this.node3.getFloor() + " DIRECTIONS\n";
        }

        if((firstIsElev && secondIsElev) && !thirdIsElev){
            answer += "Exit the elevator at floor " + this.node3.getFloor() + "\n";
            answer += "FLOOR " + this.node3.getFloor() + " DIRECTIONS\n";
        }


        //@TODO we assume elevators/stairs are perfectly aligned
        if(getHumanDist() > 0.0){
            answer += "Go " + getHumanDist() + " " + App.getUnit();
        }

        // we only want to say turn if its neither straight or diagonal or error
        if(((!(getDir() == TextPathBuilder.RelativeDirection.STRAIGHT)) && !(getDir() == TextPathBuilder.RelativeDirection.DIAGONALLY)) && !(getDir() == TextPathBuilder.RelativeDirection.ERROR)){
            answer += "\nTurn " + getDir() + " at " + this.node2.getLongName();
        }

        // if node 2 is stairs/elevator, and node 1 WASNT stairs/elevator we're entering them
        if((!firstIsStair && secondIsStair) && thirdIsStair){
            answer += "\n" + "Enter the stairs";
        }
        if((!firstIsElev && secondIsElev) && thirdIsElev){
            answer += "\n" + "Enter the elevator";
        }

        return answer;
    }


    public String lastPairToString(){
        String answer = "";

//         if node 2 was stairs/elevator and we're not on it, we're exiting them
        if((this.node2.getNodeType().equals("STAI")) && !this.node3.getNodeType().equals("STAI")){
            answer += "Exit the stairs at floor " + this.node3.getFloor() + "\n";
            answer += "FLOOR " + this.node3.getFloor() + " DIRECTIONS\n";

        }

        if((this.node2.getNodeType().equals("ELEV")) && !this.node3.getNodeType().equals("ELEV")){
            answer += "Exit the elevator at floor " + this.node3.getFloor() + "\n";
            answer += "FLOOR " + this.node3.getFloor() + " DIRECTIONS\n";

        }

        double dist = tpb.getHumanDistance(tpb.getPixelDistance(this.node2, this.node3));
        dist = Math.floor(dist*100)/100;
        int intDist = (int)dist;
        if(intDist > 0.0){
            answer += "Go " + intDist + " " + App.getUnit();
        }

        return answer;
    }
}
