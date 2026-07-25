package fr.asenka.visu.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private final Map<Long, Node> nodes = new HashMap<>();
    private final Map<Long, Edge> edges = new HashMap<>();

    public void addNode(Node node) {

        if (node == null) {
            throw new IllegalArgumentException("null node not allowed");
        }
        if (nodes.containsKey(node.getId())) {
            throw new IllegalStateException("The graph already has node with id: " + node.getId());
        }
        nodes.put(node.getId(), node);
    }

    public void addEdge(Edge edge) {

        if (edge == null) {
            throw new IllegalArgumentException("null edge not allowed");
        }
        if (edges.containsKey(edge.getId())) {
            throw new IllegalStateException("The graph already has edge with id: " + edge.getId());
        }
        if (!nodes.containsKey(edge.getSourceNodeId())) {
            throw new IllegalStateException("The graph has no node with id: %s (edge: %s)".formatted(edge.getSourceNodeId(), edge));
        }
        if (!nodes.containsKey(edge.getTargetNodeId())) {
            throw new IllegalStateException("The graph has no node with id: %s (edge: %s)".formatted(edge.getTargetNodeId(), edge));
        }
        edges.put(edge.getId(), edge);
    }

    public Node getNode(long id) {
        final var node = nodes.get(id);

        if (node == null) {
            throw new IllegalArgumentException("node id not found: " + id);
        }
        return node;
    }

    public Edge getEdge(long id) {
        final var edge = edges.get(id);

        if (edge == null) {
            throw new IllegalArgumentException("edge id not found: " + id);
        }
        return edge;
    }

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    public Collection<Edge> getEdges() {
        return Collections.unmodifiableCollection(edges.values());
    }

    public void removeNode(long id) {
        if (nodes.remove(id) == null) {
            throw new IllegalArgumentException("node id not found: " + id);
        }
    }

    public void removeEdge(long id) {
        if (edges.remove(id) == null) {
            throw new IllegalArgumentException("edge id not found: " + id);
        }
    }
}
