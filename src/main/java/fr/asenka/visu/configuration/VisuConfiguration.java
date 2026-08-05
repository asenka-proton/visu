package fr.asenka.visu.configuration;

import fr.asenka.visu.engine.LayoutEngine;
import fr.asenka.visu.engine.configuration.EngineConfiguration;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.ui.views.GraphView;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@EnableConfigurationProperties(VisuProperties.class)
@Import({
        EngineConfiguration.class
})
@RequiredArgsConstructor
public class VisuConfiguration {

    private final VisuProperties properties;

    @Bean
    public GraphView graphView(Graph graph, LayoutEngine layoutEngine) {
        return new GraphView(
                graph,
                layoutEngine,
                properties.getUi().getZoomSensitivity(),
                properties.getUi().getZoomMinScale(),
                properties.getUi().getZoomMaxScale()
        );
    }


    @Bean
    public Graph testGraph() {

        final Graph graph = new Graph();

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
        return graph;
    }
}
