package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Navigation.NavigationWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

import java.util.NavigableMap;

public class ChoosePathController {

    @FXML private JFXRadioButton sta, bfs, dfs, djikstra;
    private ToggleGroup group = new ToggleGroup();

    @FXML
    private void closePop(){
        App.getChoosePathPop().getContent().clear();
        App.getPopup().getContent().add(App.getAdmin());
    }

    @FXML
    private void set() {
        if (sta.isSelected()) NavigationWrapper.setSearchType("A*");
        else if (bfs.isSelected()) NavigationWrapper.setSearchType("BFS");
        else if (dfs.isSelected()) NavigationWrapper.setSearchType("DFS");
        else if (djikstra.isSelected()) NavigationWrapper.setSearchType("DJI");
        closePop();
    }

    public void update() {
        switch (NavigationWrapper.getPathType()) {
            case "A*":
                sta.setSelected(true);
                break;
            case "BFS":
                bfs.setSelected(true);
                break;
            case "DFS":
                dfs.setSelected(true);
                break;
            case "DJI":
                djikstra.setSelected(true);
        }
        DatabaseWrapper.addUserBacklog(App.getUser().getUserName(), "PATH", "CHANGE", NavigationWrapper.getPathType());
    }

    @FXML
    private void initialize() {
        sta.setToggleGroup(group);
        bfs.setToggleGroup(group);
        dfs.setToggleGroup(group);
        djikstra.setToggleGroup(group);
    }
}
