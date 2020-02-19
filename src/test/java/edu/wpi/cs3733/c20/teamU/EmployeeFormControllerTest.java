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
public class EmployeeFormControllerTest extends ApplicationTest {
  public void start(Stage stage) throws IOException {
    Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/EmployeeForm.fxml"));
    Scene scene1 = new Scene(sceneRoot);
    stage.setScene(scene1);
    stage.show();
  }

  @Test
  public void testButtons() {
    verifyThat("#confirm", hasText("Confirm"));
    verifyThat("#cancel", hasText("Cancel"));
    verifyThat("#checkBox", hasText("I Promise Not To Be A Robot"));
    verifyThat("#checkBox1", hasText("Add Employee"));
    verifyThat("#delete", hasText("Delete Employee"));
  }

  @Test
  public void testTextFields() {
    clickOn("#firstNameText").write("hello");
    clickOn("#lastNameText").write("hi");
    clickOn("#employeeIdText").write("hi");
    clickOn("#passwordText").write("h");
    clickOn("#confirmPassText").write("h");
  }

  @Test
  public void testClicks() {
    clickOn("#checkBox");
    clickOn("#checkBox1");
    clickOn("#delete");
    clickOn("#employeeCombo");
  }

}
