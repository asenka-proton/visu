package fr.asenka.visu.model;

import fr.asenka.visu.shared.Vector2D;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Node {

    @EqualsAndHashCode.Include
    private Long id;
    private String label;
    private String content;
    @Builder.Default
    private String color = "#FFFFF";
    @Builder.Default
    private Vector2D location = Vector2D.ORIGIN;
    @Builder.Default
    private Vector2D velocity = Vector2D.ORIGIN;

    public double x() {
        return location.x();
    }

    public double y() {
        return location.y();
    }

    public void setLocation(double x, double y) {
        setLocation(new Vector2D(x, y));
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

    public void addVelocity(Vector2D acceleration) {
        velocity = velocity.add(acceleration);
    }
}
