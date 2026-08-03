package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Node;
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
    @Getter
    private final List<Runnable> listeners = new ArrayList<>();

    private double clickedX, clickedY;
    private double mouseStartX, mouseStartY;


    public NodeView(Node model, Group parent) {
        this.model = model;
        this.parent = parent;
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
        final double newX = model.x() - (shape.getWidth() / 2);
        final double newY = model.y() - (shape.getHeight() / 2);
        relocate(newX, newY);
        listeners.forEach(Runnable::run);
    }

    private void onMouseClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            clickedX = this.model.x();
            clickedY = this.model.y();

            mouseStartX = event.getSceneX() - parent.getTranslateX();
            mouseStartY = event.getSceneY() - parent.getTranslateY();
        }
    }

    private void onMouseDragged(MouseEvent event) {

        final double deltaX = (event.getSceneX() - mouseStartX) - parent.getTranslateX();
        final double deltaY = (event.getSceneY() - mouseStartY) - parent.getTranslateY();

        model.setLocation(clickedX + deltaX, clickedY + deltaY);
        update();
    }


    public double getShapeWidth() {
        return shape.getWidth();
    }

    public double getShapeHeight() {
        return shape.getHeight();
    }
}
