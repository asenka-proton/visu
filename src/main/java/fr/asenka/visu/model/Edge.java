package fr.asenka.visu.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Edge {
    @EqualsAndHashCode.Include
    private long id;
    private String label;
    private Long sourceNodeId;
    private Long targetNodeId;
    @Builder.Default
    private String color = "#FFFFF";

    public double length(Graph graph) {
        if (!graph.contains(id)) {
            throw new IllegalArgumentException("graph does no contain this edge: " + this);
        }
        if (sourceNodeId == null || targetNodeId == null || sourceNodeId.equals(targetNodeId)) {
            return 0d;
        }
        return graph.getNode(sourceNodeId).distance(graph.getNode(targetNodeId));
    }
}
