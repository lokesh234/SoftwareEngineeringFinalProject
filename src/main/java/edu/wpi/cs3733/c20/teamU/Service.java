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

    public String getDate() { return this.date;}
    public String getRequestID() { return this.requestID;}
    public String getName() {return this.name;}
    public String  getRequestType() { return this.requestType;}
}
