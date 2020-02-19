package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class LanguageControllerTest extends ApplicationExtension {
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/LanguageForm.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }

    @Test
    public void testButtons() {
        verifyThat("#confirm", hasText("Confirm"));
    }

    @Test
    public void testLabels() {
        verifyThat("#FirstNameLabel", hasText("First Name"));
    }

    @Test
    public void testWriteField() {
        clickOn("#firstNameText").write("Jon");
        clickOn("#lastNameText").write("Doe");
        clickOn("#passwordText").write("Bring me Jesus !");
    }

}