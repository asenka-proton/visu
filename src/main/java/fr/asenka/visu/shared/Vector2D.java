package fr.asenka.visu.shared;

import fr.asenka.visu.model.Node;

public record Vector2D(double x, double y) {

    public static final Vector2D ORIGIN = new Vector2D(0d,0d);

    public Vector2D(Node node) {
        this(node.x(), node.y());
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize() {
        double mag = magnitude();
        return mag == 0 ? new Vector2D(0, 0) : multiply(1.0 / mag);
    }

    public double distance(double x, double y) {
        return Math.sqrt(distanceSq(x, y));
    }

    public double distanceSq(double x, double y) {
        final double dx = x - this.x;
        final double dy = y - this.y;
        return (dx * dx + dy * dy);
    }

    public double distance(Vector2D other) {
        if (other == null) {
            throw new IllegalArgumentException("other vector is null");
        }
        return distance(other.x, other.y);
    }

    public double distanceSq(Vector2D other) {
        if (other == null) {
            throw new IllegalArgumentException("other vector is null");
        }
        return distanceSq(other.x, other.y);
    }

}
