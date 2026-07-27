package fr.asenka.visu.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class Vector2DTest {

    @Test
    @DisplayName("Distance should be calculated correctly between two points")
    void distance_calculatesCorrectDistance() {
        Vector2D loc = new Vector2D(0, 0);
        double result = loc.distance(3, 4); // Pythagore: 3^2 + 4^2 = 5^2
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Distance squared should be calculated correctly")
    void distanceSq_calculatesCorrectSquaredDistance() {
        Vector2D loc = new Vector2D(1, 1);
        double result = loc.distanceSq(4, 5);
        // (4-1)^2 + (5-1)^2 = 3^2 + 4^2 = 9 + 16 = 25
        assertThat(result).isEqualTo(25.0);
    }

    @Test
    @DisplayName("Distance with another Vector2D object should work correctly")
    void distance_withVector2D_worksCorrectly() {
        Vector2D loc1 = new Vector2D(0, 0);
        Vector2D loc2 = new Vector2D(3, 4);

        assertThat(loc1.distance(loc2)).isEqualTo(5.0);
        assertThat(loc1.distanceSq(loc2)).isEqualTo(25.0);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when Vector2D is null")
    void distance_throwsException_whenVector2DIsNull() {
        Vector2D loc = new Vector2D(0, 0);

        assertThatThrownBy(() -> loc.distance(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("other vector is null");

        assertThatThrownBy(() -> loc.distanceSq(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("other vector is null");
    }

    @Test
    @DisplayName("Distance to the same point should be zero")
    void distance_toSamePoint_isZero() {
        Vector2D loc = new Vector2D(10, 20);
        assertThat(loc.distance(10, 20)).isZero();
        assertThat(loc.distance(loc)).isZero();
    }
}
