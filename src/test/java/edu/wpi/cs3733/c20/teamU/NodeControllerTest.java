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
public class NodeControllerTest extends ApplicationTest {
  public void start(Stage stage) throws IOException {
    Parent sceneRoot = FXMLLoader.load(getClass().getResource("/Admin_Node.fxml"));
    Scene scene1 = new Scene(sceneRoot);
    stage.setScene(scene1);
    stage.show();
  }

  @Test
  public void testButtons() {
    verifyThat("#addButton", hasText("Add"));
    verifyThat("#removeButton", hasText("Remove"));
    verifyThat("#export", hasText("Export"));
//    verifyThat("#NodeID", hasText("NodeID"));
//    verifyThat("#yCoord", hasText("Y-coord"));
//    verifyThat("xCoord", hasText("X-coord"));
//    verifyThat("#floor", hasText("floor"));
//    verifyThat("#nodeType", hasText("NodeType"));
//    verifyThat("#building", hasText("building"));
//    verifyThat("#longName", hasText("LongName"));
//    verifyThat("#shortName", hasText("ShortName"));
  }

}
