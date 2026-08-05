package fr.asenka.visu.ui;

import fr.asenka.visu.shared.Vector2D;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


public final class JavaFXUtils {

    /**
     * Check if a {@link MouseEvent} occurs on a node of class nodeClass
     * @param event the {@link MouseEvent}
     * @param nodeClass the Node type to check
     * @return true if the event is targeting a node of type nodeClass or one of its parent (recursive)
     */
    public static boolean targetNodeIs(MouseEvent event, Class<? extends Node> nodeClass) {

        if (event == null) {
            throw new IllegalArgumentException("null event");
        }
        Node target = event.getPickResult().getIntersectedNode();

        while (target != null && !nodeIsA(target, nodeClass)) {
            target = target.getParent();
        }
        return target != null;
    }

    public static boolean nodeIsA(Node node, Class<? extends Node> nodeClass) {
        if (node == null) {
            return false;
        }
        return node.getClass().equals(nodeClass);
    }

    public static Point2D point2D(Vector2D vector2D) {

        if (vector2D == null) {
            return null;
        }
        return new Point2D(vector2D.x(), vector2D.y());
    }

    private JavaFXUtils() {}
}
