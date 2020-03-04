package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;

import java.util.ArrayList;

public class NodeName {
    private ArrayList<String> AllNodeNames= new ArrayList<String>();
    public void Populate(){
        for (int i = 0; i < DatabaseWrapper.getGraph().getNodes().size(); i++){
            AllNodeNames.add(DatabaseWrapper.getGraph().getNodes().get(i).getLongName());
        }
    }
    public ArrayList<String> getAllNodeNames(){
        return AllNodeNames;
    }

}
