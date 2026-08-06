package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LayoutEngine {

    private final List<Force> forces = new ArrayList<>();
    private final double damping;

    public LayoutEngine(double damping, Force... forces) {
        this.damping = damping;
        this.forces.addAll(List.of(forces));
    }


    public void update(Graph graph) {
        log.trace("Update graph");
        for (Force force : forces) {
            force.apply(graph);
        }

        for (Node node : graph.getNodes()) {

            final Vector2D nodeVelocity = node.getVelocity().multiply(damping);
            node.setVelocity(nodeVelocity);

            final double newX = node.x() + nodeVelocity.x();
            final double newY = node.y() + nodeVelocity.y();

            node.setLocation(newX, newY);
        }
    }
}
