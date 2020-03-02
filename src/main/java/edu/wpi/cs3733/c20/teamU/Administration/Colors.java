package edu.wpi.cs3733.c20.teamU.Administration;

import edu.wpi.cs3733.c20.teamU.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import javax.swing.text.html.CSS;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

@Setter @Getter @AllArgsConstructor
public class Colors {
    private String colorTheme;
    private String firstColor;
    private String secondColor;
    private String thirdColor;
    private String fourthColor;
    private String fifthColor;
    private String firstTextColor;
    private String secondTextColor;

    private static boolean getFileDir(String cssName){
        String directoryName = "CSS";

        File directory = new File(directoryName);
        File file = new File(directoryName + "/" + cssName);
        if (! directory.exists()){
            directory.mkdir();
            // if there isn't a dir create one and then return false
            return false;
        } else if(! file.exists()){
            System.out.println("file.exists doesnt exist");
            return false;
        } else{
            return true;
        }
    }


    public static boolean CreateCSS(String cssName, String pathTo, String pathFrom){
        try {
            String CSSPath;
            boolean fileExist;
            fileExist = getFileDir("light.css");
            System.out.println("fileExist : " + fileExist);
            //determine the destination path
            if(pathTo == null) {
                String APath = System.getProperty("user.dir");
                CSSPath = APath + "/CSS/" + cssName + ".css";
            }
            else {
                CSSPath = pathTo;
            }
          if (!fileExist) {
                String line = "";
                PrintWriter pw = new PrintWriter(new File(CSSPath));
                StringBuilder sb = new StringBuilder();
                BufferedReader br;
                InputStream cssStream = Colors.class.getResourceAsStream("/light_theme/" + pathFrom);
                br = new BufferedReader(new InputStreamReader(cssStream));
                // try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile))) {
                while ((line = br.readLine()) != null) {
                  sb.append(line);
                  sb.append("\n");
                }
                // FileUtils.copyFile(src, des);
                pw.write(sb.toString());
                pw.close();
                return true;
          }
          else return true;
        } catch (Exception e){
            System.out.println("Print Writer did not work");
            return false;
        }
    }


}
