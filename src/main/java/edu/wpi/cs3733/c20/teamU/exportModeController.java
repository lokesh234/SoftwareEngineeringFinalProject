package edu.wpi.cs3733.c20.teamU;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class exportModeController {

  @FXML private Button cancel;
  @FXML private Button save;
  @FXML private TextField location1;
  @FXML private Button browse;
  private Stage scene;
  public exportModeController() {}

  /**
   * Change to scene to Admin_Screen...
   */
  public void adminScreen(ActionEvent event) throws IOException {
    Parent editMode = FXMLLoader.load(getClass().getResource("/Admin_Node.fxml"));
    Scene editModeScene = new Scene(editMode);

    scene = (Stage) ((Node)event.getSource()).getScene().getWindow();

    scene.setScene(editModeScene);
    scene.show();
  }

  @FXML
  public void browse() throws IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose path to save");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("csv file", "*.csv"));
    File selectedFile = fileChooser.showSaveDialog(scene);
    location1.setText(selectedFile.getAbsolutePath());
  }

  @FXML
  public void save() throws IOException {
      File file = new File(location1.getText());

    Connection conn = null;
    Statement stmt = null;
    try {
      conn = DriverManager.getConnection("jdbc:derby:UDB;create=true");
      stmt = conn.createStatement();
      //getting UBDatabase
      Database.CreateCSV(stmt,"MapNodesU", file.toString());
      stmt.close();
      conn.close();
    }
    catch (SQLException e){
      e.printStackTrace();
      return;
    }
  }

  @FXML
  public void initialize() {
    location1.setText(System.getProperty("user.dir"));
  }
}
