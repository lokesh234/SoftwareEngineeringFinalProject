package edu.wpi.cs3733.c20.teamU.Navigation;

import edu.wpi.cs3733.c20.teamU.Database.Node;

public class TextPathChunk {
    private TextPathBuilder tpb;
    private Node node1;
    private Node node2;
    private Node node3;

    private TextPathBuilder.RelativeDirection dir;
    private double humanDist;

    public TextPathChunk(Node n1, Node n2, Node n3, TextPathBuilder tpb){
        this.node1 = n1;
        this.node2 = n2;
        this.node3 = n3;

        this.tpb = tpb;

        this.humanDist = tpb.getHumanDistance(tpb.getPixelDistance(n1, n2) + tpb.getPixelDistance(n2, n3));
        this.dir = tpb.getRelativeDirection(n1, n2, n3);
    }

    public double getHumanDist() {
        return humanDist;
    }

    public TextPathBuilder.RelativeDirection getDir(){
        return this.dir;
    }

    public String toString(){
        String answer = "";
        answer += "Go " + getDir() + " for " + getHumanDist() + " " + tpb.getDistanceUnit();

        if((this.node3.getID().equals("STAI"))){
            answer += "\n" + "Enter the stairs";
        }
        if((this.node3.getID().equals("ELEV"))){
            answer += "\n" + "Enter the elevator";
        }
        return answer;
    }
}
