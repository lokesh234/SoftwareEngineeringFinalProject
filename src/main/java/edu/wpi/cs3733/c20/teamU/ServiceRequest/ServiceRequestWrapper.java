package edu.wpi.cs3733.c20.teamU.ServiceRequest;

public class ServiceRequestWrapper {

    public static String serviceGetDate(Service service) { return service.getDate();}
    public static String serviceGetRequestID(Service service) { return service.getRequestID();}
    public static String serviceGetName(Service service) {return service.getName();}
    public static String serviceGetRequestType(Service service) { return service.getRequestType();}

    public static void pathfindDrawNodes(PathfindController pathfind){pathfind.drawNodes();}

}
