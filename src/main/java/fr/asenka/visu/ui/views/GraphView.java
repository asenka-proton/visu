package fr.asenka.visu.ui.views;

import fr.asenka.visu.engine.LayoutEngine;
import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


public class GraphView extends Pane {

    private final Map<Long, NodeView> nodeViews = new HashMap<>();
    private final Map<Long, EdgeView> edgeViews = new HashMap<>();
    private final Pane edgeLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    private final Graph graph;
    private final LayoutEngine engine;

    @Setter
    private boolean layoutEngineActive = false;

    public GraphView(Graph graph, LayoutEngine engine) {
        this.graph = graph;
        this.engine = engine;
    }

    public void initialize() {
        render();
        createAnimatedTimerLayout().start();
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
            edgeViews.put(edge.getId(), edgeView);
            edgeLayer.getChildren().add(edgeView);
        }
        getChildren().addAll(edgeLayer, nodeLayer);
    }

    private AnimationTimer createAnimatedTimerLayout() {
        return new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (layoutEngineActive) {
                    engine.update(graph);
                }

                for (NodeView nodeView : nodeViews.values()) {
                    nodeView.update();
                }
            }
        };
    }
}
