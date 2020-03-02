package edu.wpi.cs3733.c20.teamU.ServiceRequest;

import edu.wpi.cs3733.c20.teamU.Navigation.PathfindController;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceRequestWrapper {

    public static ArrayList<String> serviceType = new ArrayList<>(
            Arrays.asList(
                    "ADMIN",
                    "SECUR",
                    "MEDIC",
                    "FLOWR",
                    "DELIV",
                    "ITRAN",
                    "ETRAN",
                    "CLOWN",
                    "RELIG",
                    "SANIT",
                    "LANGE",
                    "INTEC"
            ));
    public static ArrayList<String> getAllServiceType(){
        return serviceType;
    }
    public static String serviceGetDate(Service service) { return service.getDate();}
    public static String serviceGetRequestID(Service service) { return service.getRequestID();}
    public static String serviceGetName(Service service) {return service.getName();}
    public static String serviceGetRequestType(Service service) { return service.getRequestType();}

    public static void pathfindDrawNodes(PathfindController pathfind){pathfind.drawNodes();}

}
