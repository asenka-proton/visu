package fr.asenka.visu;

import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Location;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.ui.views.GraphView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        final Graph graph = new Graph();

        final Node n1 = Node.builder().id(1L).label("Idée A").location(new Location(50, 50)).build();
        final Node n2 = Node.builder().id(2L).label("Idée B").location(new Location(200, 50)).build();

        graph.addNode(n1);
        graph.addNode(n2);

        final Edge e1 = Edge.builder()
                .id(100L)
                .sourceNodeId(1L)
                .targetNodeId(2L)
                .label("lié à")
                .build();
        graph.addEdge(e1);

        final GraphView graphView = new GraphView(graph);

        final Scene scene = new Scene(graphView, 800, 600);
        primaryStage.setTitle("VISU - Aperçu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
