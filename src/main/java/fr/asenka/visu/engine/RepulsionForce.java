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
            final Vector2D locationA = a.getLocation();

            for (Node b : graph.getNodes()) {
                if (a.getId().equals(b.getId())) continue;

                final Vector2D locationB = b.getLocation();
                final Vector2D direction = locationA.subtract(locationB);
                final double distance = direction.magnitude();

                if (distance > 0) {
                    // Cas normal : on utilise la direction réelle
                    final double magnitude = strength / (distance * distance);
                    acceleration = acceleration.add(direction.normalize().multiply(magnitude));
                } else {
                    // CAS CRITIQUE : Les nœuds sont superposés !
                    // On génère une direction aléatoire pour les séparer
                    double randomX = Math.random() * 2 - 1;
                    double randomY = Math.random() * 2 - 1;
                    acceleration = acceleration.add(new Vector2D(randomX, randomY).multiply(strength));
                }

                // Sécurité pour ne pas diviser par zéro avec la minDistance
                // (Si tu utilises minDistance, assure-toi qu'elle est intégrée ici)
            }
            a.incrementVelocity(acceleration);
        }
    }
}
