package fr.asenka.visu.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GraphTest {

    private Graph graph;
    private Node node1;
    private Node node2;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        node1 = Node.builder().id(1L).label("Node 1").build();
        node2 = Node.builder().id(2L).label("Node 2").build();
    }

    @Test
    @DisplayName("Should add nodes successfully")
    void addNode_Success() {
        graph.addNode(node1);
        assertThat(graph.getNodes()).hasSize(1);
        assertThat(graph.getNode(1L)).isEqualTo(node1);
    }

    @Test
    @DisplayName("Should throw exception when adding null node")
    void addNode_Null_ThrowsException() {
        assertThatThrownBy(() -> graph.addNode(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null node not allowed");
    }

    @Test
    @DisplayName("Should throw exception when adding duplicate node ID")
    void addNode_DuplicateId_ThrowsException() {
        graph.addNode(node1);
        assertThatThrownBy(() -> graph.addNode(Node.builder().id(1L).build()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already has node with id: 1");
    }

    @Test
    @DisplayName("Should add edges successfully when nodes exist")
    void addEdge_Success() {
        graph.addNode(node1);
        graph.addNode(node2);

        // Utilisation du Builder pour Edge
        Edge edge = Edge.builder()
                .id(100L)
                .sourceNodeId(1L)
                .targetNodeId(2L)
                .label("relation")
                .build();

        graph.addEdge(edge);

        assertThat(graph.getEdges()).hasSize(1);
        assertThat(graph.getEdge(100L)).isEqualTo(edge);
    }

    @Test
    @DisplayName("Should throw exception when adding edge with missing nodes")
    void addEdge_MissingNodes_ThrowsException() {
        // On crée une edge dont les IDs de noeuds n'existent pas dans le graphe
        Edge edge = Edge.builder()
                .id(100L)
                .sourceNodeId(99L)
                .targetNodeId(100L)
                .build();

        assertThatThrownBy(() -> graph.addEdge(edge))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has no node with id: 99");
    }

    @Test
    @DisplayName("Should throw exception when getting non-existent node")
    void getNode_NotFound_ThrowsException() {
        assertThatThrownBy(() -> graph.getNode(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("node id not found: 999");
    }

    @Test
    @DisplayName("Should remove nodes and edges successfully")
    void remove_Success() {
        graph.addNode(node1);
        graph.removeNode(1L);
        assertThat(graph.getNodes()).isEmpty();

        graph.addNode(node2);
        graph.addNode(Node.builder().id(3L).build());
        graph.removeNode(3L);
        assertThat(graph.getNodes()).hasSize(1);
    }

    @Test
    @DisplayName("Should throw exception when removing non-existent element")
    void remove_NotFound_ThrowsException() {
        assertThatThrownBy(() -> graph.removeNode(99L))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> graph.removeEdge(99L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Collections should be unmodifiable")
    void collections_AreUnmodifiable() {
        graph.addNode(node1);
        var nodes = graph.getNodes();

        assertThatThrownBy(() -> nodes.clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
