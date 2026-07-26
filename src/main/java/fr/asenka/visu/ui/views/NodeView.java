package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;

public class NodeView extends StackPane {

    @Getter
    private final Node model;
    private final Rectangle shape;
    private final Text label;

    public NodeView(Node model) {
        this.model = model;

        this.shape = new Rectangle(100, 50);
        this.shape.setStroke(Color.BLACK);
        this.shape.setFill(Color.BLACK);
        this.shape.setStrokeWidth(2);

        this.label = new Text(model.getLabel());
        this.label.setFont(Font.font("Arial", 12));
        this.label.setStroke(Color.WHITE);

        getChildren().addAll(shape, label);

        updatePosition();
    }

    public void updatePosition() {
        relocate(model.x(), model.y());
    }
}
