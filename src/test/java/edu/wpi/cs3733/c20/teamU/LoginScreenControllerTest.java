package edu.wpi.cs3733.c20.teamU;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class LoginScreenControllerTest extends ApplicationTest {
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/LoginUI.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }

    @Test
    public void testButtons() {
        verifyThat("#loginEnter", hasText("Enter"));
    }

    @Test
    public void testLoginCanClick() {
        clickOn("#usernameField").write("admin");
        clickOn("#passwordField").write("pasword");
        clickOn("#loginEnter");
    }

}
