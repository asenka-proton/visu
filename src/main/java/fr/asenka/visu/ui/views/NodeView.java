package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;

import static fr.asenka.visu.utils.JavaFXUtils.applyCssClass;

public class NodeView extends StackPane {

    @Getter
    private final Node model;
    private final Rectangle shape;
    private final Text label;

    public NodeView(Node model) {
        this.model = model;

        shape = new Rectangle(50, 50);
        label = new Text(model.getLabel());

        applyCssClass(this, "node-container");
        applyCssClass(shape, "node-shape");
        applyCssClass(label, "node-label");

        getChildren().addAll(shape, label);

        updatePosition();
    }

    public void updatePosition() {
        relocate(model.x(), model.y());
    }
}
