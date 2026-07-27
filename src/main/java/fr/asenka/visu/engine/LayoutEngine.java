package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class LayoutEngine {

    public static final Force REPULSION_FORCE = new RepulsionForce(100.0, 20.0);
    public static final Force ATTRACTION_FORCE = new AttractionForce(0.1, 100.0);
    public static final double DAMPING = 0.9;

    private final List<Force> forces = new ArrayList<>();
    private final double damping;

    public LayoutEngine() {
        this(DAMPING, REPULSION_FORCE, ATTRACTION_FORCE);
    }

    public LayoutEngine(double damping, Force... forces) {
        this.damping = damping;
        this.forces.addAll(List.of(forces));
    }


    public void update(Graph graph) {

        for (Force force : forces) {
            force.apply(graph);
        }

        for (Node node : graph.getNodes()) {

            Vector2D currentVelocity = node.getVelocity().multiply(damping);
            node.setVelocity(currentVelocity);

            double newX = node.x() + currentVelocity.x();
            double newY = node.y() + currentVelocity.y();

            node.setLocation(newX, newY);
        }
    }
}
