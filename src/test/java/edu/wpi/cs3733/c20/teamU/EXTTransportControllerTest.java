package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class EXTTransportControllerTest extends ApplicationTest {
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/ExternalTransportForm.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }

    @Test
    public void testButtons() {
        verifyThat("#cancel", hasText("Cancel"));
    }

    @Test
    public void testLabels() {
        verifyThat("#Pass", hasText("Number Of Passengers"));
    }

    @Test
    public void testWriteField() {
        clickOn("#firstNameText").write("Service");
        clickOn("#lastNameText").write("OrangeJuiceSpill");
        clickOn("#confirmPassText").write("Room3");
    }
}
