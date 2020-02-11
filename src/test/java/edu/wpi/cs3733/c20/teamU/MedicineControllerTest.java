package edu.wpi.cs3733.c20.teamU;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class MedicineControllerTest extends ApplicationTest {
    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/MedicineRequestForm.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }

    @Test
    public void testButtons() {
        verifyThat("#submit", hasText("Submit"));
    }

    @Test
    public void testWriteField() {
        clickOn("#last").write("Je");
        clickOn("#frequency").write("immediate");
        clickOn("#first").write("Ted");
        clickOn("#drug").write("Tylenol");
        clickOn("#comments").write("I need this right now");
        clickOn("#comboBox");
    }
    @Test
    public void testSubmitCanClick() {
        //waiting on database
//        clickOn("#submit");
    }

}
