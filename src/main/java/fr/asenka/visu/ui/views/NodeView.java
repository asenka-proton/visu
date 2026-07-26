package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Node;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;

import static fr.asenka.visu.utils.JavaFXUtils.applyCssClass;

public class NodeView extends StackPane {

    @Getter
    private final Node model;
    private final Rectangle shape;

    public NodeView(Node model) {
        this.model = model;

        shape = new Rectangle(50, 50);
        final Text label = new Text(model.getLabel());

        applyCssClass(this, "node-container");
        applyCssClass(shape, "node-shape");
        applyCssClass(label, "node-label");

        getChildren().addAll(shape, label);

        setAlignment(Pos.CENTER);

        updatePosition();
    }

    public void updatePosition() {
        relocate(model.x(), model.y());
    }

    public double getShapeWidth() {
        return shape.getWidth();
    }

    public double getShapeHeight() {
        return shape.getHeight();
    }
}
