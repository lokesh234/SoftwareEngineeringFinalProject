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
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class GiftDeliveryControllerTest extends ApplicationTest {
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/RequestGiftDelivery.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }

    @Test
    public void testButtons() {
        verifyThat("#submit", hasText("Submit"));
        verifyThat("#back", hasText("Back"));
    }

    @Test
    public void testWriteField() {
        clickOn("#last").write("Je");
        clickOn("#first").write("Ted");
        clickOn("#room").write("Room_1");
        clickOn("#gift");
    }
}
