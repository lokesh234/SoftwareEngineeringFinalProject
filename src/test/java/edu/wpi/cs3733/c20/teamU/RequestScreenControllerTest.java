package edu.wpi.cs3733.c20.teamU;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;

import edu.wpi.cs3733.c20.teamU.ServiceRequest.RequestScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
public class RequestScreenControllerTest extends ApplicationTest {

  public void start(Stage stage) throws IOException {
    Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/Request.fxml"));
    Scene scene1 = new Scene(sceneRoot);
    stage.setScene(scene1);
    stage.show();
  }

  @Test
  public void testButtons() {
    verifyThat("#close", hasText("Close"));
//    verifyThat("#date1", hasText("date"));
    verifyThat("#backButton", hasText("Back"));
  }

  @Test
  public void testClick() {
    clickOn("#comboBox");
  }

//  @Test
//  public void testLabels() throws IOException {
//    Pane home = FXMLLoader.load(getClass().getResource("/bigScreenv2.fxml"));
//
////    clickOn("#resolve");
////    clickOn("#cancel");
//    clickOn("#cancel").moveTo(home);
//
//  }
}
