package fr.asenka.visu.ui.views;

import fr.asenka.visu.engine.ForcesLayoutEngine;
import fr.asenka.visu.engine.LayoutEngine;
import fr.asenka.visu.model.Edge;
import fr.asenka.visu.model.Graph;
import fr.asenka.visu.model.Node;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static fr.asenka.visu.ui.JavaFXUtils.targetNodeIs;

@Slf4j
@RequiredArgsConstructor
public class GraphView extends Pane {


    private final Map<Long, NodeView> nodeViews = new HashMap<>();
    private final Map<Long, EdgeView> edgeViews = new HashMap<>();
    @Getter
    private final Group contentGroup = new Group();
    private final Pane edgeLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    private final Graph graph;
    private final LayoutEngine engine;
    private final double zoomSensitivity;
    private final double zoomMinScale;
    private final double zoomMaxScale;

    private boolean moving;
    private double mouseX, mouseY;
    private double initialTranslateX, initialTranslateY;

    @Setter
    private boolean layoutEngineActive = false;

    public void initialize() {
        render();
        createAnimatedTimerLayout().start();
    }

    private void render() {

        renderGraph();

        setOnMousePressed(this::onMousePressed);
        setOnMouseDragged(this::onMouseDragged);
        setOnMouseReleased(this::onMouseReleased);
        setOnScroll(this::onScroll);
    }

    private void renderGraph() {
        for (Node node : graph.getNodes()) {
            final NodeView nodeView = new NodeView(node, this);
            nodeViews.put(node.getId(), nodeView);
            nodeLayer.getChildren().add(nodeView);
        }

        for (Edge edge : graph.getEdges()) {
            final EdgeView edgeView = new EdgeView(edge);

            final NodeView source = nodeViews.get(edgeView.getSourceNodeId());
            final NodeView target = nodeViews.get(edgeView.getTargetNodeId());

            if (source != null && target != null) {
                edgeView.update(source, target);
            }
            edgeViews.put(edge.getId(), edgeView);
            edgeLayer.getChildren().add(edgeView);
        }
        contentGroup.getChildren().addAll(edgeLayer, nodeLayer);

        getChildren().addAll(contentGroup);
    }

    private void onMousePressed(MouseEvent mouseEvent) {

        log.trace("[MousePressed]");
        // Si on a cliqué sur un NodeView, on ignore le déplacement
        if (targetNodeIs(mouseEvent, NodeView.class)) return;

        moving = true;
        mouseX = mouseEvent.getSceneX();
        mouseY = mouseEvent.getSceneY();
        initialTranslateX = contentGroup.getTranslateX();
        initialTranslateY = contentGroup.getTranslateY();
    }

    private void onMouseDragged(MouseEvent mouseEvent) {

        if (!moving) return;

        log.trace("[MouseDragged]");

        final double deltaX = mouseEvent.getSceneX() - mouseX;
        final double deltaY = mouseEvent.getSceneY() - mouseY;

        contentGroup.setTranslateX(initialTranslateX + deltaX);
        contentGroup.setTranslateY(initialTranslateY + deltaY);
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        log.trace("[MouseRelease]");
        mouseX = 0;
        mouseY = 0;
        initialTranslateX = 0;
        initialTranslateY = 0;
        moving = false;
    }

    private void onScroll(ScrollEvent event) {

        log.trace("[Scroll]");

        double currentScale = contentGroup.getScaleX();

        // Facteur de zoom
        double zoomDelta = event.getDeltaY() * zoomSensitivity;
        double newScale = currentScale * (1.0 + zoomDelta);
        // Limite newScale entre minScale et maxScale (pour éviter le risque de valeur trop grande ou négative)
        newScale = Math.clamp(newScale, zoomMinScale, zoomMaxScale);

        if (newScale == currentScale) return;

        // Conversion manuelle : position de la souris dans le repère local du contentGroup AVANT zoom
        // On utilise screenToLocal pour obtenir les coordonnées du curseur dans le contentGroup
        final Point2D localPoint = contentGroup.screenToLocal(event.getScreenX(), event.getScreenY());

        // Appliquer le nouveau zoom
        contentGroup.setScaleX(newScale);
        contentGroup.setScaleY(newScale);

        // Après le zoom, ce même point local est maintenant à une autre position à l'écran
        // On ajuste la translation pour qu'il revienne sous la souris.
        final Point2D newPosOnScreen = contentGroup.localToScreen(localPoint);

        double dx = event.getScreenX() - newPosOnScreen.getX();
        double dy = event.getScreenY() - newPosOnScreen.getY();

        contentGroup.setTranslateX(contentGroup.getTranslateX() + dx);
        contentGroup.setTranslateY(contentGroup.getTranslateY() + dy);

        event.consume();
    }

    private AnimationTimer createAnimatedTimerLayout() {
        log.debug("Create AnimationTimer for layout engine");
        return new AnimationTimer() {

            @Override
            public void handle(long now) {

                // On recalcule la position des nœuds dans le modèle (si le layout est activé)
                if (layoutEngineActive) {
                    engine.update(graph);
                }

                // On met à jour la position des liens entre les nœuds
                for (EdgeView edgeView : edgeViews.values()) {
                    final NodeView source = nodeViews.get(edgeView.getSourceNodeId());
                    final NodeView target = nodeViews.get(edgeView.getTargetNodeId());
                    edgeView.update(source, target);
                }
                // On met à jour la position des nœuds en conséquence
                for (NodeView nodeView : nodeViews.values()) {
                    nodeView.updatePosition();
                }
            }
        };
    }
}
