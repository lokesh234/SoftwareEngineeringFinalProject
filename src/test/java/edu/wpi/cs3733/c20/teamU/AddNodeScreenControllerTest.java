package edu.wpi.cs3733.c20.teamU;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
public class AddNodeScreenControllerTest extends ApplicationTest {
  public void start(Stage stage) throws IOException {
    Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/Add_Node.fxml"));
    Scene scene1 = new Scene(sceneRoot);
    stage.setScene(scene1);
    stage.show();
  }

  @Test
  public void testTextField() {
//    clickOn("#text2").write("s");
//    clickOn("#text1").write("s");
//    clickOn("#text3").write("s");
//    clickOn("#text4").write("s");
//    clickOn("#text5").write("s");
//    clickOn("#text7").write("s");
//    clickOn("#text8").write("s");
//    clickOn("#nodeType").write("s");
  }

  @Test
  public void testLabels1() {
    verifyThat("#AddNodesMenu", hasText("Add Nodes Menu"));
  }

  @Test
  public void testLabels2() {
    verifyThat("#NodeID", hasText("NODEID"));
  }

  @Test
  public void testLabels3() {
    verifyThat("#XCOORD", hasText("XCOORD"));
  }

  @Test
  public void testLabels4() {
    verifyThat("#YCOORD", hasText("YCOORD"));
  }

  @Test
  public void testLabels5() {
    verifyThat("#FLOOR", hasText("FLOOR"));
  }

  @Test
  public void testLabels6() {
    verifyThat("#building", hasText("BUILDING"));
  }

  @Test
  public void testLabels7() {
    verifyThat("#nodetype", hasText("NODETYPE"));
  }

  @Test
  public void testLabels8() {
    verifyThat("#longname", hasText("LONGNAME"));
  }

  @Test
  public void testLabels9() {
    verifyThat("#shortname", hasText("SHORTNAME"));
  }

  @Test
  public void testButtons1() {
    verifyThat("#confirm", hasText("Confirm"));
  }

  @Test
  public void testButtons2() {
    verifyThat("#cancel", hasText("Cancel"));
  }




}
