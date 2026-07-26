package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Edge;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Getter;

public class EdgeView extends Group {

    @Getter
    private final Edge model;
    private final Line line;

    public EdgeView(Edge model) {
        this.model = model;

        this.line = new Line();

        this.line.setStroke(Color.BLACK);
        this.line.setStrokeWidth(2);

        this.getChildren().add(line);
    }

    public void update(NodeView source, NodeView target) {
        if (source != null && target != null) {
            // On utilise getLayoutX/Y car NodeView hérite de StackPane (qui est un Region)
            line.setStartX(source.getLayoutX());
            line.setStartY(source.getLayoutY());
            line.setEndX(target.getLayoutX());
            line.setEndY(target.getLayoutY());
        }
    }
}
