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
    private final Map<Long, EdgeView> edgeViews = new HashMap<>();

    public GraphView(Graph graph) {
        this.graph = graph;
        render();
    }

    private void render() {
        // 1. Création des nœuds
        for (Node node : graph.getNodes()) {
            NodeView view = new NodeView(node);
            nodeViews.put(node.getId(), view);
            this.getChildren().add(view);
        }

        // 2. Création des liens
        for (Edge edge : graph.getEdges()) {
            EdgeView view = new EdgeView(edge);
            edgeViews.put(edge.getId(), view);
            this.getChildren().add(view);

            // Mise à jour immédiate de la position du lien
            updateEdgePosition(view);
        }
    }

    private void updateEdgePosition(EdgeView view) {
        NodeView source = nodeViews.get(view.getModel().getSourceNodeId());
        NodeView target = nodeViews.get(view.getModel().getTargetNodeId());

        if (source != null && target != null) {
            view.update(source, target);
        }
    }
}
