package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;
import lombok.RequiredArgsConstructor;


/**
 * Force de répulsion appliquée entre tous les nœuds du graphe.
 * Les nœuds se repoussent mutuellement pour éviter les collisions.
 *
 * La force suit une loi en 1/d² (comme la gravité newtonienne inversée).
 */
@RequiredArgsConstructor
public class RepulsionForce implements Force {

    /** Force de répulsion de base (ex: 10000.0). Plus c'est élevé, plus la répulsion est forte. */
    private final double strength;

    /** Distance minimale utilisée dans les calculs pour éviter division par zéro et instabilités. */
    private final double minDistance;

    @Override
    public void apply(Graph graph) {
        for (Node nodeA : graph.getNodes()) {
            Vector2D totalAcceleration = Vector2D.ORIGIN;
            final Vector2D locationA = nodeA.getLocation();

            for (Node nodeB : graph.getNodes()) {
                // On ne se repousse pas soi-même
                if (nodeA.getId().equals(nodeB.getId())) {
                    continue;
                }

                final Vector2D acceleration = getAcceleration(nodeB, locationA);
                totalAcceleration = totalAcceleration.add(acceleration);
            }

            nodeA.incrementVelocity(totalAcceleration);
        }
    }

    private Vector2D getAcceleration(Node nodeB, Vector2D locationA) {
        final Vector2D locationB = nodeB.getLocation();

        // Vecteur allant de B vers A (direction de la répulsion)
        final Vector2D direction = locationA.subtract(locationB);
        final double distance = direction.magnitude();

        // Utilisation de minDistance comme borne inférieure pour la distance

        final double effectiveDistance = Math.max(distance, minDistance);
        final double normalizedMagnitude = strength / (effectiveDistance * effectiveDistance);

        // Appliquer la force dans la direction
        return direction.normalize().multiply(normalizedMagnitude);
    }
}
