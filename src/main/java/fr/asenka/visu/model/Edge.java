package fr.asenka.visu.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Edge {
    private long id;
    private String label;
    private Long sourceNodeId;
    private Long targetNodeId;
    @Builder.Default
    private String color = "#FFFFF";
}
