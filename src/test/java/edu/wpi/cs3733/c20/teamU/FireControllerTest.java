package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

    @ExtendWith(ApplicationExtension.class)
    public class FireControllerTest extends FxRobot {
        Pane home;
        public void start(Stage stage) throws IOException {
            Parent sceneRoot = FXMLLoader.load(getClass().getResource("/pathfindEmergency.fxml"));
            home = FXMLLoader.load(getClass().getResource("/bigScreenv2.fxml"));
            Scene scene1 = new Scene(sceneRoot);
            stage.setScene(scene1);
            stage.show();
        }

    //    @Test
      //  public void goBack(FxRobot fxRobotz){
       //     fxRobotz.press(KeyCode.Z).moveTo(home);
       // }
    }
