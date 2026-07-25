package fr.asenka.visu.model;

import lombok.Builder;
import lombok.Data;

import static fr.asenka.visu.utils.ColorUtils.BLACK;

@Data
@Builder
public class Edge {
    private long id;
    private String label;
    private Long sourceNodeId;
    private Long targetNodeId;
    @Builder.Default
    private String color = BLACK;
}
