package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportScreenController {

  @FXML private JFXButton cancel;
  @FXML private JFXButton save;
  @FXML private JFXTextField location1;
  @FXML private JFXButton browse;
  private Stage scene;
  public ExportScreenController() {}

  /**
   * Change to scene to Admin_Screen...
   */
  public void adminScreen(ActionEvent event) throws IOException {
      App.getPopup().getContent().clear();
      App.getPopup().getContent().add(App.getAdmin());
      App.getPopup().show(App.getPrimaryStage());
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
      DatabaseWrapper.CreateCSV(stmt,"MapNodesU", file.toString());
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
