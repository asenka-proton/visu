package fr.asenka.visu.model;

import fr.asenka.visu.shared.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GraphUtilsTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
    }

    @Nested
    @DisplayName("Tests pour getHorizontalBounds")
    class BoundsTests {
        @Test
        @DisplayName("Doit calculer correctement les bornes avec des coordonnées positives")
        void testPositiveBounds() {
            graph.createNode("A", 10, 0);
            graph.createNode("B", 50, 0);
            graph.createNode("C", 100, 0);

            Vector2D bounds = GraphUtils.getHorizontalBounds(graph);

            assertThat(bounds.first()).isEqualTo(10.0);
            assertThat(bounds.second()).isEqualTo(100.0);
        }

        @Test
        @DisplayName("Doit gérer les coordonnées négatives")
        void testNegativeBounds() {
            graph.createNode("A", -50, 0);
            graph.createNode("B", -10, 0);
            graph.createNode("C", -100, 0);

            Vector2D bounds = GraphUtils.getHorizontalBounds(graph);

            assertThat(bounds.first()).isEqualTo(-100.0);
            assertThat(bounds.second()).isEqualTo(-10.0);
        }
    }

    @Nested
    @DisplayName("Tests pour split")
    class SplitTests {
        @Test
        @DisplayName("Doit lever une exception si le graphe est null")
        void testNullGraph() {
            assertThrows(IllegalArgumentException.class, () -> GraphUtils.split(null, 2));
        }

        @Test
        @DisplayName("Doit lever une exception si le nombre de nœuds est inférieur à n")
        void testInsufficientNodes() {
            graph.createNode("A", 10, 0);
            assertThrows(IllegalArgumentException.class, () -> GraphUtils.split(graph, 2));
        }

        @Test
        @DisplayName("Doit diviser correctement les nœuds et conserver les liens internes")
        void testSuccessfulSplit() {
            // Création de 4 nœuds espacés pour 2 sous-graphes
            // Bande 1 (0-50), Bande 2 (50-100)
            Node n1 = graph.createNode("N1", 10, 0);
            Node n2 = graph.createNode("N2", 20, 0);
            Node n3 = graph.createNode("N3", 80, 0);
            Node n4 = graph.createNode("N4", 90, 0);

            // Lien interne au sous-graphe 1
            graph.createEdge("link1", n1, n2);
            // Lien interne au sous-graphe 2
            graph.createEdge("link2", n3, n4);
            // Lien traversant les deux sous-graphes (doit être supprimé)
            graph.createEdge("cross", n2, n3);

            List<Graph> subGraphs = GraphUtils.split(graph, 2);

            assertThat(subGraphs).hasSize(2);

            // Vérification Sous-graphe 1
            assertThat(subGraphs.get(0).getNodes()).containsExactlyInAnyOrder(n1, n2);
            assertThat(subGraphs.get(0).getEdges()).hasSize(1); // Seulement link1

            // Vérification Sous-graphe 2
            assertThat(subGraphs.get(1).getNodes()).containsExactlyInAnyOrder(n3, n4);
            assertThat(subGraphs.get(1).getEdges()).hasSize(1); // Seulement link2
        }

    }
}
