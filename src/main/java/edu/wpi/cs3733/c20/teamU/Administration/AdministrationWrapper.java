package edu.wpi.cs3733.c20.teamU.Administration;

public class AdministrationWrapper {

    public static void adminRequestControllerUpdate(AdminRequestController app){app.update();}

    public static boolean CreateCSS(String cssName, String pathTo, String pathFrom){
        return Colors.CreateCSS(cssName, pathTo, pathFrom);
    }

}
