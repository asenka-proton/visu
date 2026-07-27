package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RepulsionForce implements Force {

    private final double strength; // Force de répulsion (ex: 100.0)
    private final double minDistance; // Pour éviter la division par zéro

    @Override
    public void apply(Graph graph) {

        for (Node a : graph.getNodes()) {

            Vector2D acceleration = Vector2D.ORIGIN;

            for (Node b : graph.getNodes()) {

                if (a.equals(b)) continue;

                final Vector2D locationA = a.getLocation();
                final Vector2D locationB = b.getLocation();

                final Vector2D direction = locationA.subtract(locationB);
                final double distance = Math.max(direction.magnitude(), minDistance);
                final double magnitude = strength / (distance * distance);

                acceleration = acceleration.add(direction
                        .normalize()
                        .multiply(magnitude)
                );
            }
            a.addVelocity(acceleration);
        }
    }
}
