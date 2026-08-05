package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Node;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NodeView extends StackPane {

    private final Group parent;
    @Getter
    private final Node model;
    private final Rectangle shape;
    private final Text label;

    private double clickedX, clickedY;
    private double mouseStartX, mouseStartY;


    public NodeView(Node model, GraphView graphView) {
        this.model = model;
        this.parent = graphView.getContentGroup();
        this.getStyleClass().add("node-container");

        shape = new Rectangle(50, 50);
        shape.getStyleClass().add("node-shape");

        label = new Text(model.getLabel());
        label.getStyleClass().add("node-label");

        getChildren().addAll(shape, label);

        setOnMouseClicked(this::onMouseClicked);
        setOnMouseDragged(this::onMouseDragged);

        updatePosition();
    }

    public void updatePosition() {
        final double newX = model.x() - (shape.getWidth() / 2);
        final double newY = model.y() - (shape.getHeight() / 2);
        relocate(newX, newY);
    }

    private void onMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            clickedX = this.model.x();
            clickedY = this.model.y();

            // Convertir les coordonnées de la souris dans l'espace local du contentGroup
            final Point2D localPoint = parent.sceneToLocal(event.getSceneX(), event.getSceneY());
            mouseStartX = localPoint.getX();
            mouseStartY = localPoint.getY();
        }
    }

    private void onMouseDragged(MouseEvent event) {

        // Convertir les coordonnées actuelles dans l'espace local du contentGroup
        final Point2D localPoint = parent.sceneToLocal(event.getSceneX(), event.getSceneY());

        final double deltaX = localPoint.getX() - mouseStartX;
        final double deltaY = localPoint.getY() - mouseStartY;

        model.setLocation(clickedX + deltaX, clickedY + deltaY);
        updatePosition();
    }


    public double getShapeWidth() {
        return shape.getWidth();
    }

    public double getShapeHeight() {
        return shape.getHeight();
    }
}
