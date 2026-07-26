package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class GraphView extends Pane {

    private final Graph graph;
    private final Map<Long, NodeView> nodeViews = new HashMap<>();
//    private final Map<Long, EdgeView> edgeViews = new HashMap<>();

    public GraphView(Graph graph) {
        this.graph = graph;
        render();
    }

    private void render() {
        for (Node node : graph.getNodes()) {
            NodeView view = new NodeView(node);
            nodeViews.put(node.getId(), view);
            getChildren().add(view);
        }

        for (Edge edge : graph.getEdges()) {
            EdgeView view = new EdgeView(edge);
            getChildren().add(view);
            updateEdgePosition(view);
        }
    }

    private void updateEdgePosition(EdgeView edgeView) {
        NodeView source = nodeViews.get(edgeView.getModel().getSourceNodeId());
        NodeView target = nodeViews.get(edgeView.getModel().getTargetNodeId());

        if (source != null && target != null) {
            edgeView.update(source, target);
        }
    }
}
