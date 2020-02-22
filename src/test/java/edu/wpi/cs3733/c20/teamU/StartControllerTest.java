package edu.wpi.cs3733.c20.teamU;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class StartControllerTest extends ApplicationTest {
  public void start(Stage stage) throws IOException {
    Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/Home.fxml"));
    Scene scene1 = new Scene(sceneRoot);
    stage.setScene(scene1);
    stage.show();
  }

  @Test
  public void testButtons() {
    verifyThat("#login", hasText("Login"));
    verifyThat("#navButton", hasText("Navigate"));
    verifyThat("#plus", hasText("+"));
    verifyThat("#minus", hasText("-"));

  }



}
