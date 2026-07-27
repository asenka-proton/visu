package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LayoutEngineTest {

    private static final double EXPECTED_DISTANCE_BETWEEN_NODES = 100.0;

    @Test
    void update() {

        final Graph graph = new Graph();

        final Node n1 = graph.createNode("a", 0, 0);
        final Node n2 = graph.createNode("b", 10, 10);
        final Node n3 = graph.createNode("c", 200, 20);

        graph.connect(n1, n2);
        graph.connect(n2, n3);

        final LayoutEngine engine = new LayoutEngine(
                new RepulsionForce(EXPECTED_DISTANCE_BETWEEN_NODES, 20.0),
                new AttractionForce(0.1, 100.0)
        );

        System.out.println("Start simulation...");
        System.out.println("Initial state: \n" + graph);

        for (int i = 0; i < 120; i++) {
            engine.update(graph);

            if (i % 20 == 0) {
                System.out.println("\n--- Tick " + i + " ---");
                System.out.println(graph);
            }
        }

        // In this simple graph, all the edges length must converge towards EXPECTED_DISTANCE_BETWEEN_NODES
        assertThat(graph.getEdges()).allSatisfy(edge -> {
            assertThat(edge.length(graph)).isCloseTo(EXPECTED_DISTANCE_BETWEEN_NODES, Offset.offset(0.4));
        });
    }
}
