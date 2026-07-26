package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NodeView extends StackPane {

    @Getter
    private final Node model;
    private final Rectangle shape;
    private final Text label;
    @Getter
    private final List<Runnable> listeners = new ArrayList<>();

    private double clickedX, clickedY;
    private double mouseStartX, mouseStartY;


    public NodeView(Node model) {
        this.model = model;
        this.getStyleClass().add("node-container");

        shape = new Rectangle(50, 50);
        shape.getStyleClass().add("node-shape");

        label = new Text(model.getLabel());
        label.getStyleClass().add("node-label");

        getChildren().addAll(shape, label);

        setOnMouseClicked(this::onMouseClicked);
        setOnMouseDragged(this::onMouseDragged);

        update();
    }

    public void update() {
        double x = model.x() - (shape.getWidth() / 2);
        double y = model.y() - (shape.getHeight() / 2);
        relocate(x, y);
        listeners.forEach(Runnable::run);
    }

    public double getShapeWidth() {
        return shape.getWidth();
    }

    public double getShapeHeight() {
        return shape.getHeight();
    }

    public double distance(NodeView other) {
        if (other == null) {
            throw new IllegalArgumentException("other node-view is null");
        }
        return model.distance(other.model);
    }

    public double distanceSq(NodeView other) {
        if (other == null) {
            throw new IllegalArgumentException("other node-view is null");
        }
        return model.distanceSq(other.model);
    }

    private void onMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            clickedX = this.model.x();
            clickedY = this.model.y();

            mouseStartX = event.getSceneX();
            mouseStartY = event.getSceneY();
        }
    }

    private void onMouseDragged(MouseEvent event) {

        final double deltaX = event.getSceneX() - mouseStartX;
        final double deltaY = event.getSceneY() - mouseStartY;

        model.setLocation(clickedX + deltaX, clickedY + deltaY);

        update();
    }
}
