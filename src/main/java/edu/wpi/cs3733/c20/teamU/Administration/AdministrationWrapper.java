package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.App;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AdministrationWrapper {

    public static void adminRequestControllerUpdate(AdminRequestController app){app.update();}

    public static void ColorInitializer() throws MalformedURLException {
        AdministrationWrapper.CreateCSS("light", null, "light.css");
        System.out.println("== CSS File Bank Created! ==");
    }

    public static void setFirstTheme() throws MalformedURLException {
        String cssFilePath = System.getProperty("user.dir") + "/CSS/light.css";
        File cssFile = new File(cssFilePath);
        URL urlCss = null;
        urlCss = cssFile.toURL();
        App.setTheme(urlCss.toExternalForm());
    }

    public static boolean CreateCSS(String cssName, String pathTo, String pathFrom){
        return Colors.CreateCSS(cssName, pathTo, pathFrom);
    }

}
