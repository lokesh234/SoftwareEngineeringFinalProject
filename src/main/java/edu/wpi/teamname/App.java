package edu.wpi.teamname;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {

  private NodesDatabase graph;
  @Override
  public void start(Stage primaryStage) throws Exception {
    Label label = new Label("Text");
    label.setId("textLabel");
    label.setVisible(false);

    Label gImg = new Label("0 (1) 1 (1) 2\n       (1)\n        3     (1)\n       (1)\n        4 (1) 5");
    gImg.setVisible(true);

    graph = new NodesDatabase();

    graph.addNode(new Node(0, 0, "0"));
    graph.addNode(new Node(1, 0, "1"));
    graph.addNode(new Node(2, 0, "2"));
    graph.addNode(new Node(1, 1, "3"));
    graph.addNode(new Node(1, 2, "4"));
    graph.addNode(new Node(2, 2, "5"));

    graph.addEdge(new Edge(graph.getNode("0"), graph.getNode("1"), 1, "0-1"));
    graph.addEdge(new Edge(graph.getNode("1"), graph.getNode("2"), 1, "1-2"));
    graph.addEdge(new Edge(graph.getNode("1"), graph.getNode("3"), 1, "1-3"));
    graph.addEdge(new Edge(graph.getNode("3"), graph.getNode("4"), 1, "3-4"));
    graph.addEdge(new Edge(graph.getNode("4"), graph.getNode("5"), 1, "4-5"));
    graph.addEdge(new Edge(graph.getNode("2"), graph.getNode("5"), 1, "2-5"));

    Button button = new Button("Pathfind from 0 to 5");
    button.setId("showTextButton");
    button.setOnAction((e) -> this.pathfind(label));

    Button closeTwo = new Button("Toggle node 2");
    closeTwo.setOnAction((e) -> this.toggleTwo(gImg));

    BorderPane root = new BorderPane();
    root.setRight(label);
    root.setLeft(button);
    root.setBottom(gImg);
    root.setCenter(closeTwo);

    Scene scene = new Scene(root, 300, 200);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void pathfind(Label l) {
    /*
    Dummy method to pathfind between predetermined points and write the path to the given label
     */
    Pathfinder p = new Pathfinder();
    p.starSingular("0", "5", graph);
    ArrayList<Node> pathReverse = p.getLatestPath();
    String path = "";
    for (int i = pathReverse.size()-1; i > 0; i--) {
      path += pathReverse.get(i).toString();
      path += " -> ";
    }
    path += pathReverse.get(0).toString();
    path += "\nCost: ";
    path += Integer.toString(p.getLatestCost());
    l.setText(path);
    l.setVisible(true);
  }

  private void toggleTwo(Label l) {
    if (graph.getNode("2").getWeight() == 0) {
      graph.getNode("2").setWeight(999999);
      l.setText("0 (1) 1 (1000000) 2\n       (1)\n        3     (1000000)\n       (1)\n        4 (1) 5");
    }
    else {
      graph.getNode("2").setWeight(0);
      l.setText("0 (1) 1 (1) 2\n       (1)\n        3     (1)\n       (1)\n        4 (1) 5");
    }
  }
}
