package edu.wpi.cs3733.c20.teamU.Administration;

import com.jfoenix.controls.JFXTreeView;
import edu.wpi.cs3733.c20.teamU.App;
import edu.wpi.cs3733.c20.teamU.Database.DatabaseWrapper;
import edu.wpi.cs3733.c20.teamU.Database.Node;
import edu.wpi.cs3733.c20.teamU.ServiceRequest.ServiceRequestWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public class TreeViewController {
    @FXML
    private JFXTreeView<String> tree;



    private TreeItem fillTreeItem(String name, int floor){
        TreeItem<String> result = new TreeItem(name);
        ArrayList<edu.wpi.cs3733.c20.teamU.Database.Node> nodes = new ArrayList<edu.wpi.cs3733.c20.teamU.Database.Node>();

        DatabaseWrapper.getNodesByFloor(nodes,floor);
        for (Node node :
                nodes) {
            result.getChildren().add(new TreeItem(node.getLongName()));
        }
        return result;
    }

    @FXML
    private void initialize(){
        TreeItem rootItem = new TreeItem("Location Directories");

        rootItem.getChildren().add(fillTreeItem("First Floor", 1));
        rootItem.getChildren().add(fillTreeItem("Second Floor", 2));
        rootItem.getChildren().add(fillTreeItem("Third Floor", 3));
        rootItem.getChildren().add(fillTreeItem("Fourth Floor", 4));
        rootItem.getChildren().add(fillTreeItem("Fifth Floor", 5));

        tree.setRoot(rootItem);

    }

    @FXML
    public void back(ActionEvent event) {
        App.loadPathfinding();
        //App.getGraph().update();
        DatabaseWrapper.updateGraph();
        //App.getPathfindController().drawNodes();
        App.getTreeViewPop().getContent().clear();
        ServiceRequestWrapper.pathfindDrawNodes(App.getPathfindController());
        App.getPrimaryStage().setScene(App.getPathScene());
    }
}


