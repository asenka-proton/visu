package fr.asenka.visu.ui.views;

import fr.asenka.visu.engine.LayoutEngine;
import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static fr.asenka.visu.ui.JavaFXUtils.targetNodeIs;


public class GraphView extends Pane {

    private final Map<Long, NodeView> nodeViews = new HashMap<>();
    private final Map<Long, EdgeView> edgeViews = new HashMap<>();
    private final Group contentGroup = new Group();
    private final Pane edgeLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    private final Graph graph;
    private final LayoutEngine engine;

    private boolean moving;
    private double mouseX, mouseY;
    private double initialTranslateX, initialTranslateY;

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

        renderGraph();

        setOnMousePressed(this::onMousePressed);
        setOnMouseDragged(this::onMouseDragged);
        setOnMouseReleased(this::onMouseReleased);
    }

    private void renderGraph() {
        for (Node node : graph.getNodes()) {
            final NodeView nodeView = new NodeView(node, contentGroup);
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
        contentGroup.getChildren().addAll(edgeLayer, nodeLayer);

        getChildren().addAll(contentGroup);
    }

    private void onMousePressed(MouseEvent mouseEvent) {

        if (targetNodeIs(mouseEvent, NodeView.class)) {
            // Si on a cliqué sur un NodeView, on ignore le déplacement
            return;
        }
        moving = true;
        mouseX = mouseEvent.getSceneX();
        mouseY = mouseEvent.getSceneY();
        initialTranslateX = contentGroup.getTranslateX();
        initialTranslateY = contentGroup.getTranslateY();
    }

    private void onMouseDragged(MouseEvent mouseEvent) {

        if (!moving) {
            return;
        }
        final double deltaX = mouseEvent.getSceneX() - mouseX;
        final double deltaY = mouseEvent.getSceneY() - mouseY;

        contentGroup.setTranslateX(initialTranslateX + deltaX);
        contentGroup.setTranslateY(initialTranslateY + deltaY);
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        mouseX = 0;
        mouseY = 0;
        initialTranslateX = 0;
        initialTranslateY = 0;
        moving = false;
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
