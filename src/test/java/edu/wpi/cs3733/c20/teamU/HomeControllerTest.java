package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class HomeControllerTest extends ApplicationTest {
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/Home.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }
    @Test
    public void testLabels1() {
        verifyThat("#BWHospital", hasText("BWH Faulkner Hospital Map"));
    }

    @Test
    public void testLabels2() {
        verifyThat("#floorLabel", hasText("1"));
    }

    @Test
    public void testButtons1() {
        verifyThat("#minus", hasText("-"));
    }

    @Test
    public void testButtons2() {
        verifyThat("#login", hasText("Login"));
    }

    @Test
    public void testButtons3() {
        verifyThat("#home", hasText("Home"));
    }

    @Test
    public void testButtons4() {
        verifyThat("#navButton", hasText("Navigate"));
    }

    @Test
    public void testButtons5() {
        verifyThat("#services", hasText("Services"));
    }

    @Test
    public void testButtons6() {
        verifyThat("#info", hasText("Information"));
    }

    @Test
    public void testButtons7() {
        verifyThat("#help", hasText("Help"));
    }

    @Test
    public void testButtons8() {
        verifyThat("#plus", hasText("+"));
    }



}

