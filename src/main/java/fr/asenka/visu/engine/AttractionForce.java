package fr.asenka.visu.engine;

import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttractionForce implements Force {

    private final double strength;    // Force du ressort (k)
    @Getter
    private final double restLength;  // Distance idéale entre deux nœuds liés

    @Override
    public void apply(Graph graph) {

        for (Edge edge : graph.getEdges()) {

            final Node source = graph.getNode(edge.getSourceNodeId());
            final Node target = graph.getNode(edge.getTargetNodeId());

            final Vector2D locationA = source.getLocation();
            final Vector2D locationB = target.getLocation();

            final Vector2D direction = locationA.subtract(locationB);
            final double currentDistance = direction.magnitude();

            if (currentDistance > 0) {
                final double magnitude = (currentDistance - restLength) * strength;
                final Vector2D acceleration = direction.normalize().multiply(magnitude);

                source.incrementVelocity(acceleration.multiply(-0.5));
                target.incrementVelocity(acceleration.multiply(0.5));
            }
        }
    }
}
