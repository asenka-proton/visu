package fr.asenka.visu.model;

public record Location(double x, double y) {

    public double distance(double x, double y) {
        return Math.sqrt(distanceSq(x, y));
    }

    public double distanceSq(double x, double y) {
        final double dx = x - this.x;
        final double dy = y - this.y;
        return (dx * dx + dy * dy);
    }

    public double distance(Location other) {
        if (other == null) {
            throw new IllegalArgumentException("other location is null");
        }
        return distance(other.x, other.y);
    }

    public double distanceSq(Location other) {
        if (other == null) {
            throw new IllegalArgumentException("other location is null");
        }
        return distanceSq(other.x, other.y);
    }
}
