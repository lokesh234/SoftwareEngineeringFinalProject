package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewCustomRequestController {
    @FXML private TableView<CustReq> reqTable;
    @FXML private TableColumn<CustReq, String> name;
    @FXML private TableColumn<CustReq, Integer> reqs, emps;

    public class CustReq {
        private String name;
        private int emps, reqs;
        public String getName() { return name;}
        public int getEmps() { return emps;}
        public int getReqs() { return reqs;}
        public CustReq(String n, int e, int r) {
            name = n;
            emps = e;
            reqs = r;
        }
    }

    @FXML
    private void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        reqs.setCellValueFactory(new PropertyValueFactory<>("reqs"));
        emps.setCellValueFactory(new PropertyValueFactory<>("emps"));
    }

    protected void update() {
        reqTable.getItems().clear();
        File dir = new File("CustomRequests");
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                if (!f.getName().contains("InputTypes.txt")) {
                    String n = f.getName().split("\\.")[0];
                    int e = DatabaseWrapper.getEmployeeCount(n);
                    int r = DatabaseWrapper.getServiceRequestAll(n);
                    reqTable.getItems().add(new CustReq(n, e, r));
                }
            }
        }
    }

    @FXML
    private void delete() {
        if (reqTable.getSelectionModel().getSelectedItem() != null) {
            CustReq c = reqTable.getSelectionModel().getSelectedItem();
            reqTable.getItems().remove(c);
            DatabaseWrapper.delType(c.name);
            File f = new File("CustomRequests/"+c.name+".txt");
            File f2 = new File("CustomRequests/"+c.name+"InputTypes.txt");
            if (!f.exists()) System.out.println("not seeing " + "CustomRequests/"+c.name+".txt");
            System.gc();
            if (!f.delete()) System.out.println("oh no");
            f2.delete();
        }
    }

    @FXML
    private void adminScreen(ActionEvent event) throws IOException {
        App.getPopup().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
        App.getPopup().show(App.getPrimaryStage());
    }
}
