package fr.asenka.visu.model;

import fr.asenka.visu.shared.Vector2D;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Graph {

    private final AtomicLong idCount = new AtomicLong(1L);
    private final Map<Long, Node> nodes = new HashMap<>();
    private final Map<Long, Edge> edges = new HashMap<>();

    public Node createNode(String label, double x, double y) {
        return createNode(label, new Vector2D(x, y));
    }

    public Node createNode(String label, Vector2D location) {
        log.debug("create node: {} at {}", label, location);
        final Node node = Node.builder()
                .id(nextId())
                .label(label)
                .location(location)
                .build();
        addNode(node);
        return node;
    }

    public Edge connect(Node source, Node target) {
        return createEdge(null, source.getId(), target.getId());
    }

    public Edge createEdge(String label, Node source, Node target) {
        return createEdge(label, source.getId(), target.getId());
    }

    public Edge createEdge(String label, long sourceId, long targetId) {
        log.debug("create edge: {} -> {} : {}", sourceId, targetId, label);
        final Edge edge = Edge.builder()
                .id(nextId())
                .label(label)
                .sourceNodeId(sourceId)
                .targetNodeId(targetId)
                .build();
        addEdge(edge);
        return edge;
    }

    public void addNode(Node node) {
        log.debug("add node: {}", node);
        if (node == null) {
            throw new IllegalArgumentException("null node not allowed");
        }
        if (nodes.containsKey(node.getId())) {
            throw new IllegalStateException("The graph already has node with id: " + node.getId());
        }
        nodes.put(node.getId(), node);
    }

    public void addEdge(Edge edge) {
        log.debug("add edge: {}", edge);
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

    public boolean contains(long id) {
        return edges.containsKey(id) || nodes.containsKey(id);
    }

    public void removeNode(long id) {
        log.debug("remove node: {}", id);
        if (nodes.remove(id) == null) {
            throw new IllegalArgumentException("node id not found: " + id);
        }
    }

    public void removeEdge(long id) {
        log.debug("remove edge: {}", id);
        if (edges.remove(id) == null) {
            throw new IllegalArgumentException("edge id not found: " + id);
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        nodes.values().forEach(node -> {
            builder.append("Node %s/%s : (%.2f, %.2f)%n".formatted(
                    node.getId(),
                    node.getLabel(),
                    node.getLocation().x(),
                    node.getLocation().y()
            ));
        });
        edges.values().forEach(edge -> {
            final var source = nodes.get(edge.getSourceNodeId());
            final var target = nodes.get(edge.getTargetNodeId());

            builder.append("%s --- %.2f ---> %s%n".formatted(
                    source.getId(),
                    source.distance(target),
                    target.getId()
            ));
        });
        return builder.toString();
    }

    private long nextId() {
        return idCount.getAndIncrement();
    }
}
