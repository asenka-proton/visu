package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;

import java.util.ArrayList;
import java.util.List;

public class LayoutEngine {

    private final List<Force> forces = new ArrayList<>();
    private final double damping = 0.9;

    public LayoutEngine() {
        this(
                new RepulsionForce(100.0, 20.0),
                new AttractionForce(0.1, 100.0)
        );
    }

    public LayoutEngine(Force... forces) {
        this.forces.addAll(List.of(forces));
    }


    public void update(Graph graph) {

        for (Force force : forces) {
            force.apply(graph);
        }

        for (Node node : graph.getNodes()) {
            double newX = node.x() * damping;
            double newY = node.y() * damping;
            // Note: Le damping est souvent appliqué sur la VELOCITY.
            // Ici, pour simplifier, on réduit la position relative.
            // Une approche plus précise serait de stocker une vitesse (vx, vy) par nœud.
        }
    }
}
