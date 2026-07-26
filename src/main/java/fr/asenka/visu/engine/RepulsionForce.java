package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Location;
import fr.asenka.visu.model.Node;
import lombok.RequiredArgsConstructor;

import java.beans.VetoableChangeListener;

@RequiredArgsConstructor
public class RepulsionForce implements Force {

    private final double strength; // Force de répulsion (ex: 100.0)
    private final double minDistance; // Pour éviter la division par zéro

    @Override
    public void apply(Graph graph) {

        for (Node a : graph.getNodes()) {

            Vector2D totalForce = Vector2D.ORIGIN;

            for (Node b : graph.getNodes()) {

                if (a.equals(b)) continue;

                final Vector2D posA = new Vector2D(a);
                final Vector2D posB = new Vector2D(b);

                final Vector2D direction = posA.subtract(posB);
                final double distance = Math.max(direction.magnitude(), minDistance);
                final double magnitude = strength / (distance * distance);

                totalForce = totalForce.add(direction
                        .normalize()
                        .multiply(magnitude)
                );
            }
            updateNodeLocation(a, totalForce);
        }
    }

    private void updateNodeLocation(Node node, Vector2D force) {
        double newX = node.getLocation().x() + force.x();
        double newY = node.getLocation().y() + force.y();
        node.setLocation(new Location(newX, newY));
    }
}
