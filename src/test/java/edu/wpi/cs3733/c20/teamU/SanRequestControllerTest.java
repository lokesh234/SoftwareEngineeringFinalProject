package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class SanRequestControllerTest extends ApplicationExtension {
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/SanitationRequest.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }

    @Test
    public void testButtons() {
        verifyThat("#confirm", hasText("Submit Request"));
    }

    @Test
    public void testLabels() {
        verifyThat("#Spill", hasText("Nature of spill"));
    }

    @Test
    public void testWriteField() {
        clickOn("#a").write("Jon");
        clickOn("#b").write("Doe");
        clickOn("#c").write("3");
    }

}
