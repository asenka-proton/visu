package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Edge;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import lombok.Getter;

import static fr.asenka.visu.utils.JavaFXUtils.applyCssClass;

public class EdgeView extends Group {

    @Getter
    private final Edge model;
    private final Line line;

    public EdgeView(Edge model) {
        this.model = model;

        line = new Line();
        applyCssClass(this, "edge");
        getChildren().add(line);
    }

    public void update(NodeView source, NodeView target) {
        if (source != null && target != null) {
            line.setStartX(source.getLayoutX());
            line.setStartY(source.getLayoutY());
            line.setEndX(target.getLayoutX());
            line.setEndY(target.getLayoutY());
        }
    }
}
