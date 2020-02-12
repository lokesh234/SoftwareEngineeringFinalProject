package edu.wpi.cs3733.c20.teamU;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
public class RRControllerTest extends ApplicationTest {

  public void start(Stage stage) throws IOException {
    Parent home = FXMLLoader.load(getClass().getResource("/bigScreenv2.fxml"));
    FXMLLoader RRLoader = new FXMLLoader(getClass().getResource("/Resolve_Request.fxml"));
    Pane sceneRoot = RRLoader.load();
    RRController rrController = RRLoader.getController();

    Popup popup = new Popup();
    popup.getContent().add(sceneRoot);
    Scene scene1 = new Scene(home);
    stage.setScene(scene1);
    stage.show();
    popup.show(stage);
  }

  @Test
  public void testButtons() {
    verifyThat("#resolve", hasText("Resolve"));
    verifyThat("#cancel", hasText("Cancel"));
    verifyThat("#back", hasText("Back"));
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
