package fr.asenka.visu.engine;

import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttractionForce implements Force {

    private final double strength;    // Force du ressort (k)
    private final double restLength;  // Distance idéale entre deux nœuds liés

    @Override
    public void apply(Graph graph) {

        for (Edge edge : graph.getEdges()) {

            final Node source = graph.getNode(edge.getSourceNodeId());
            final Node target = graph.getNode(edge.getTargetNodeId());

            final Vector2D posA = new Vector2D(source);
            final Vector2D posB = new Vector2D(target);

            final Vector2D direction = posA.subtract(posB);
            final double currentDistance = direction.magnitude();

            if (currentDistance > 0) {
                final double magnitude = (currentDistance - restLength) * strength;
                final Vector2D force = direction.normalize().multiply(magnitude);

                updateNodePosition(source, force.multiply(-0.5));
                updateNodePosition(target, force.multiply(0.5));
            }
        }
    }

    private void updateNodePosition(Node node, Vector2D force) {
        final double newX = node.x() + force.x();
        final double newY = node.y() + force.y();
        node.setLocation(newX, newY);
    }
}
