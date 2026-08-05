package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GravityForce implements Force {

    private final Vector2D center; // Le point vers lequel attirer (ex: 0,0)
    private final double strength; // Force de la gravité

    @Override
    public void apply(Graph graph) {
        for (Node node : graph.getNodes()) {
            final Vector2D location = node.getLocation();

            // Calcul du vecteur vers le centre
            final Vector2D direction = center.subtract(location);
            final double distance = direction.magnitude();

            if (distance > 0) {
                // On applique une force qui augmente avec la distance
                // pour ramener les nœuds éloignés vers le centre.
                final double magnitude = distance * strength;
                final Vector2D acceleration = direction.normalize().multiply(magnitude);

                node.incrementVelocity(acceleration);
            }
        }
    }
}
