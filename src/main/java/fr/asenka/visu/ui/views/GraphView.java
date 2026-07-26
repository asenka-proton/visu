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
    private final Pane edgeLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    public GraphView(Graph graph) {
        this.graph = graph;
        render();
    }

    private void render() {
        for (Node node : graph.getNodes()) {
            final NodeView nodeView = new NodeView(node);
            nodeViews.put(node.getId(), nodeView);
            nodeLayer.getChildren().add(nodeView);
        }

        for (Edge edge : graph.getEdges()) {
            final EdgeView edgeView = new EdgeView(edge);

            final NodeView source = nodeViews.get(edgeView.getSourceNodeId());
            final NodeView target = nodeViews.get(edgeView.getTargetNodeId());

            if (source != null && target != null) {
                edgeView.update(source, target);
                source.getListeners().add(() -> edgeView.update(source, target));
                target.getListeners().add(() -> edgeView.update(source, target));
            }
            edgeLayer.getChildren().add(edgeView);
        }
        getChildren().addAll(edgeLayer, nodeLayer);
    }
}
