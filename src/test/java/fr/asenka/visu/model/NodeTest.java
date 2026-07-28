package fr.asenka.visu.model;

import fr.asenka.visu.shared.Vector2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NodeTest {

    @Test
    @DisplayName("Builder should initialize with default values")
    void builder_shouldHaveDefaultValues() {
        Node node = Node.builder().build();

        assertThat(node.getId()).isNull();
        assertThat(node.getLabel()).isNull();
        assertThat(node.getContent()).isNull();
        assertThat(node.getColor()).isNotEmpty();
        assertThat(node.getLocation()).isEqualTo(Vector2D.ORIGIN);
    }

    @Test
    @DisplayName("x() and y() should return correct coordinates")
    void xAndY_shouldReturnCorrectCoordinates() {
        Node node = Node.builder()
                .location(new Vector2D(10.5, 20.0))
                .build();

        assertThat(node.x()).isEqualTo(10.5);
        assertThat(node.y()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("distance() should calculate distance between two nodes")
    void distance_shouldCalculateCorrectDistance() {
        Node node1 = Node.builder().location(new Vector2D(0, 0)).build();
        Node node2 = Node.builder().location(new Vector2D(3, 4)).build();

        assertThat(node1.distance(node2)).isEqualTo(5.0);
    }

    @Test
    @DisplayName("distanceSq() should calculate squared distance between two nodes")
    void distanceSq_shouldCalculateCorrectSquaredDistance() {
        Node node1 = Node.builder().location(new Vector2D(1, 1)).build();
        Node node2 = Node.builder().location(new Vector2D(4, 5)).build();

        // (4-1)^2 + (5-1)^2 = 3^2 + 4^2 = 25
        assertThat(node1.distanceSq(node2)).isEqualTo(25.0);
    }

    @Test
    @DisplayName("distance() and distanceSq() should throw exception on null node")
    void distanceMethods_shouldThrowException_whenOtherNodeIsNull() {
        Node node = Node.builder().build();

        assertThatThrownBy(() -> node.distance(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("other node is null");

        assertThatThrownBy(() -> node.distanceSq(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("other node is null");
    }
}
