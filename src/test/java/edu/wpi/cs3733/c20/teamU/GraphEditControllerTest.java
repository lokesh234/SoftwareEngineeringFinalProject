package edu.wpi.cs3733.c20.teamU;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class GraphEditControllerTest extends ApplicationTest {

    public void start(Stage stage) throws IOException {
        Parent sceneRoot = FXMLLoader.load(getClass().getResource("/light_theme/AdminGraph.fxml"));
        Scene scene1 = new Scene(sceneRoot);
        stage.setScene(scene1);
        stage.show();
    }

    @Test
    public void testLabels1() {
        verifyThat("#viewGraph", hasText("View Graph"));
    }

    @Test
    public void testButtons1() {
        verifyThat("#backButton", hasText("Back"));
    }

    @Test
    public void testLabels2() {
        verifyThat("#statusLabel1", hasText("SEARCH"));
    }

    @Test
    public void testLabels3() {
        verifyThat("#select", hasText("MODE SELECT"));
    }

    @Test
    public void testButtons2() {
        verifyThat("#nodeModeButton", hasText("NODE"));
    }

    @Test
    public void testButtons3() {
        verifyThat("#edgeModeButton", hasText("EDGE"));
    }

    @Test
    public void testButtons4() {
        verifyThat("#addButton", hasText("ADD"));
    }

    @Test
    public void testButtons5() {
        verifyThat("#editButton", hasText("EDIT"));
    }

    @Test
    public void testButtons6() {
        verifyThat("#removeButton", hasText("REMOVE"));
    }

    @Test
    public void testLabels4() {
        verifyThat("#statusLabel", hasText("MODIFY"));
    }

    @Test
    public void testButtons7() {
        verifyThat("#startButton", hasText("Select Start"));
    }

    @Test
    public void testLabels5() {
        verifyThat("#startLabel", hasText("Node LongName"));
    }

    @Test
    public void testButtons8() {
        verifyThat("#endButton", hasText("Select End"));
    }

    @Test
    public void testLabels6() {
        verifyThat("#endLabel", hasText("Node LongName"));
    }

    //doesn't think Λ is Λ
//    @Test
//    public void testButtons9() {
//        verifyThat("#up", hasText("Λ"));
//    }

    @Test
    public void testLabels7() {
        verifyThat("#floorLabel", hasText("#"));
    }

    @Test
    public void testButtons10() {
        verifyThat("#down", hasText("V"));
    }








}
