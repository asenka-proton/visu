package fr.asenka.visu.engine;

import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import fr.asenka.visu.shared.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class LayoutEngine {

    // Une force de répulsion robuste pour éviter les collisions
    public static final Force REPULSION_FORCE = new RepulsionForce(2000, 50);

    // Une force d'attraction plus marquée pour créer des clusters visibles
    public static final Force ATTRACTION_FORCE = new AttractionForce(0.05, 300);

    // Une gravité douce pour maintenir l'ensemble au centre (ajuste la force selon tes besoins)
    public static final Force GRAVITY_FORCE = new GravityForce(new Vector2D(200, 250), 0.01);

    // Un amortissement fluide pour un mouvement naturel
    public static final double DAMPING = 0.9;

    private final List<Force> forces = new ArrayList<>();
    private final double damping;

    public LayoutEngine() {
        this(DAMPING, REPULSION_FORCE, ATTRACTION_FORCE, GRAVITY_FORCE);
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

            final Vector2D nodeVelocity = node.getVelocity().multiply(damping);
            node.setVelocity(nodeVelocity);

            final double newX = node.x() + nodeVelocity.x();
            final double newY = node.y() + nodeVelocity.y();

            node.setLocation(newX, newY);
        }
    }
}
