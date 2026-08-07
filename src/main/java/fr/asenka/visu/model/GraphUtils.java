package fr.asenka.visu.model;

import fr.asenka.visu.shared.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GraphUtils {

    /**
     * Effectue une séparation d'un graph en plusieurs sous-graphes avec un
     * algorithme de découpage vertical
     *
     * @param graph le graph à diviser
     * @param n     le nombre de sous-graphes désirés
     * @return une {@link List} de {@code n} sous-{@link Graph} (non modifiable)
     */
    public static List<Graph> split(Graph graph, int n) {

        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        if (graph.getNodes().size() < n) {
            throw new IllegalArgumentException("graph cannot be split in %s sub-graphs".formatted(n));
        }
        final List<Graph> subGraphs = getEmptyGraphs(n);
        final Vector2D hBounds = getHorizontalBounds(graph);
        final double minX = hBounds.first();
        final double maxX = hBounds.second();
        final double bandWidth = (maxX - minX) / n;

        for (Node node : graph.getNodes()) {
            final int band = (int) ((node.x() - minX) / bandWidth);
            final int index = Math.min(n - 1, band);
            subGraphs.get(index).addNode(node);
        }

        for (Edge edge : graph.getEdges()) {
            final long sourceId = edge.getSourceNodeId();
            final long targetId = edge.getTargetNodeId();

            for (Graph subGraph : subGraphs) {
                if (subGraph.contains(sourceId) && subGraph.contains(targetId)) {
                    subGraph.addEdge(edge);
                    break;
                }
            }
        }
        return subGraphs;
    }

    public static Vector2D getHorizontalBounds(Graph graph) {

        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        double maxX = Double.NEGATIVE_INFINITY;
        double minX = Double.POSITIVE_INFINITY;

        for (Node node : graph.getNodes()) {
            final double nodeX = node.x();
            if (nodeX > maxX) {
                maxX = nodeX;
            }
            if (nodeX < minX) {
                minX = nodeX;
            }
        }
        return new Vector2D(minX, maxX);
    }

    private static List<Graph> getEmptyGraphs(int n) {
        final List<Graph> subGraphs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            subGraphs.add(new Graph("sub-graph:" + (i + 1)));
        }
        return Collections.unmodifiableList(subGraphs);
    }

    private GraphUtils() {
    }
}
