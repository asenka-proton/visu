package fr.asenka.visu.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Node {

    private Long id;
    private String label;
    private String content;
    @Builder.Default
    private String color = "#FFFFF";
    @Builder.Default
    private Location location = Location.ORIGIN;

    public double x() {
        return location.x();
    }

    public double y() {
        return location.y();
    }

    public void setLocation(double x, double y) {
        setLocation(new Location(x, y));
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
