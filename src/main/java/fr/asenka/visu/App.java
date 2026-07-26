package fr.asenka.visu;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.ui.views.GraphView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static fr.asenka.visu.utils.JavaFXUtils.setStylesheet;


public class App extends Application {

    private static final String STYLESHEET = "/css/style.css";

    @Override
    public void start(Stage primaryStage) {
        final Graph graph = new Graph();

        final Node n1 = graph.createNode("a", 50, 50);
        final Node n2 = graph.createNode("b", 50, 200);
        final Node n3 = graph.createNode("c", 200, 200);

        graph.createEdge("", n1, n2);
        graph.createEdge("", n2, n3);

        final GraphView graphView = new GraphView(graph);

        final Scene scene = new Scene(graphView, 800, 600);
        setStylesheet(scene, STYLESHEET);
        primaryStage.setTitle("VISU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
