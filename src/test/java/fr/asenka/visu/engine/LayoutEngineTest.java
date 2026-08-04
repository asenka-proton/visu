package fr.asenka.visu.engine;

import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LayoutEngineTest {

    private static final Force REPULSION_FORCE = new RepulsionForce(2000, 50);
    private static final Force ATTRACTION_FORCE = new AttractionForce(0.05, 300);
    private static final Force GRAVITY_FORCE = new GravityForce(new Vector2D(200, 250), 0.01);
    private static final double EXPECTED_DISTANCE_BETWEEN_NODES = 100.0;

    @Test
    void update_simple_3nodes_graph() {

        final Graph graph = new Graph();

        final Node n1 = graph.createNode("a", 0, 0);
        final Node n2 = graph.createNode("b", 10, 10);
        final Node n3 = graph.createNode("c", 200, 20);

        graph.connect(n1, n2);
        graph.connect(n2, n3);

        final LayoutEngine engine = new LayoutEngine(
                LayoutEngine.DAMPING,
                new RepulsionForce(100.0, 20.0),
                new AttractionForce(0.1, EXPECTED_DISTANCE_BETWEEN_NODES)
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
//        assertThat(graph.getEdges()).allSatisfy(edge -> {
//            assertThat(edge.length(graph)).isCloseTo(EXPECTED_DISTANCE_BETWEEN_NODES, Offset.offset(0.4));
//        });
    }

    @Test
    void update_star_graph() {

        final Graph graph = new Graph();

        graph.addNode(Node.builder().id(0L).label("CENTER").location(Vector2D.ORIGIN).build());

        for (long i = 1; i <= 5; i++) {
            graph.addNode(Node.builder().id(i).label("P" + i).location(new Vector2D(i * 2, i * 2)).build());
            graph.addEdge(Edge.builder().id(100 + i).sourceNodeId(0L).targetNodeId(i).build());
        }

        final LayoutEngine engine = new LayoutEngine(LayoutEngine.DAMPING, REPULSION_FORCE, ATTRACTION_FORCE, GRAVITY_FORCE);

        System.out.println("Start simulation...");
        System.out.println("Initial state: \n" + graph);

        for (int i = 0; i < 200; i++) {
            engine.update(graph);

            if (i % 20 == 0) {
                System.out.println("\n--- Tick " + i + " ---");
                System.out.println(graph);
            }
        }
    }
}
