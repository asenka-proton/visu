package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RepulsionForceTest {


    @Test
    void apply_isDeterministic_sameOutputMultipleTimes() {
        final Graph graph1 = new Graph();
        graph1.createNode("A", 0, 0);
        graph1.createNode("B", 50, 50);

        final Graph graph2 = new Graph();
        graph2.createNode("A", 0, 0);
        graph2.createNode("B", 50, 50);

        final RepulsionForce force = new RepulsionForce(500.0, 10.0);

        force.apply(graph1);
        force.apply(graph2);

        // Mêmes positions initiales → mêmes vitesses finales
        assertThat(graph1.getNode(1L).getVelocity()).isEqualTo(graph2.getNode(1L).getVelocity());
    }

    @Test
    void apply_withVerySmallDistance_returnsFiniteValues() {
        final Graph graph = new Graph();
        // Nœuds très proches, mais pas superposés
        graph.createNode("A", 0, 0);
        graph.createNode("B", 0.001, 0);  // Distance quasi-nulle

        final RepulsionForce force = new RepulsionForce(1000.0, 20.0);
        force.apply(graph);

        // La vitesse ne doit pas être NaN ou Inf
        for (Node node : graph.getNodes()) {
            assertThat(node.getVelocity().x()).isFinite();
            assertThat(node.getVelocity().y()).isFinite();
        }
    }
}
