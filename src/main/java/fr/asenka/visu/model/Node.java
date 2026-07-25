package fr.asenka.visu.model;

import lombok.Builder;
import lombok.Data;

import static fr.asenka.visu.utils.ColorUtils.BLACK;

@Data
@Builder
public class Node {
    private long id;
    private String label;
    private String content;
    @Builder.Default
    private String color = BLACK;
    @Builder.Default
    private Location location = new Location(0d, 0d);

    public double x() {
        return location.x();
    }

    public double y() {
        return location.y();
    }

    public double distance(Node other) {
        if (other == null) {
            throw new IllegalArgumentException("other node is null");
        }
        return location.distance(other.location);
    }

    public double distanceSq(Node other) {
        if (other == null) {
            throw new IllegalArgumentException("other node is null");
        }
        return location.distanceSq(other.location);
    }
}
