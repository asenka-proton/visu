package fr.asenka.visu.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LocationTest {

    @Test
    @DisplayName("Distance should be calculated correctly between two points")
    void distance_calculatesCorrectDistance() {
        Location loc = new Location(0, 0);
        double result = loc.distance(3, 4); // Pythagore: 3^2 + 4^2 = 5^2
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Distance squared should be calculated correctly")
    void distanceSq_calculatesCorrectSquaredDistance() {
        Location loc = new Location(1, 1);
        double result = loc.distanceSq(4, 5);
        // (4-1)^2 + (5-1)^2 = 3^2 + 4^2 = 9 + 16 = 25
        assertThat(result).isEqualTo(25.0);
    }

    @Test
    @DisplayName("Distance with another Location object should work correctly")
    void distance_withLocation_worksCorrectly() {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(3, 4);

        assertThat(loc1.distance(loc2)).isEqualTo(5.0);
        assertThat(loc1.distanceSq(loc2)).isEqualTo(25.0);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when Location is null")
    void distance_throwsException_whenLocationIsNull() {
        Location loc = new Location(0, 0);

        assertThatThrownBy(() -> loc.distance(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("other location is null");

        assertThatThrownBy(() -> loc.distanceSq(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("other location is null");
    }

    @Test
    @DisplayName("Distance to the same point should be zero")
    void distance_toSamePoint_isZero() {
        Location loc = new Location(10, 20);
        assertThat(loc.distance(10, 20)).isZero();
        assertThat(loc.distance(loc)).isZero();
    }
}
