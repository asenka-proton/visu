package fr.asenka.visu;

import fr.asenka.visu.engine.LayoutEngine;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.ui.views.GraphView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.asenka.visu.utils.JavaFXUtils.setStylesheet;


public class App extends Application {

    private static final String STYLESHEET = "/css/style.css";

    private final Graph graph = new Graph();

    @Override
    public void start(Stage primaryStage) {

        // 1. Configuration des paramètres
        int nodeCount = 35;           // Nombre de nœuds
        int edgeCount = 40;          // Nombre de connexions
        Random random = new Random();

        List<Node> nodes = new ArrayList<>();

        // 2. Création automatique des nœuds avec positions aléatoires
        for (int i = 0; i < nodeCount; i++) {
            // On crée des nœuds avec des labels "A", "B", "C"...
            Node n = graph.createNode(String.valueOf((char) ('A' + (i % 26))),
                    random.nextInt(750),
                    random.nextInt(550));
            nodes.add(n);
        }

        // 3. Création automatique des edges (connexions)
        // On connecte chaque nœud à quelques autres de manière aléatoire
        for (int i = 0; i < edgeCount; i++) {
            int index1 = random.nextInt(nodes.size());
            int index2 = random.nextInt(nodes.size());

            // On évite de connecter un nœud à lui-même
            if (index1 != index2) {
                graph.connect(nodes.get(index1), nodes.get(index2));
            }
        }

        final GraphView graphView = new GraphView(graph);

        final Scene scene = new Scene(graphView, 800, 600);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                graphView.toggleLayout();
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                graphView.toggleLayout();
            }
        });

        setStylesheet(scene, STYLESHEET);
        primaryStage.setTitle("VISU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
