package edu.wpi.cs3733.c20.teamU;

public class Service {
    private String date;
    private String requestID;
    private String name;
    private String requestType;

    public Service(String date, String requestId, String name,String requestType){
        this.date = date;
        this.requestID = requestId;
        this.name = name;
        this.requestType = requestType;
    }
}
