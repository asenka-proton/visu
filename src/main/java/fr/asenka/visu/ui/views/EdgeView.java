package fr.asenka.visu.ui.views;

import fr.asenka.visu.model.Edge;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import lombok.Getter;

public class EdgeView extends Group {

    @Getter
    private final Edge model;
    private final Line line;
    private final Text label;

    public EdgeView(Edge model) {
        this.model = model;

        line = new Line();
        line.getStyleClass().add("edge-line");
        getChildren().add(line);

        label = new Text("");
        label.getStyleClass().add("edge-label");
        getChildren().add(label);
    }

    public long getSourceNodeId() {
        return model.getSourceNodeId();
    }

    public long getTargetNodeId() {
        return model.getTargetNodeId();
    }

    public void update(NodeView source, NodeView target) {
        if (source == null || target == null) {
            return;
        }
        final double startX = source.getLayoutX() + (source.getShapeWidth() / 2);
        final double startY = source.getLayoutY() + (source.getShapeHeight() / 2);
        final double endX = target.getLayoutX() + (target.getShapeWidth() / 2);
        final double endY = target.getLayoutY() + (target.getShapeHeight() / 2);
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);

        final double midX = (startX + endX) / 2;
        final double midY = (startY + endY) / 2;
        label.setLayoutX(midX - (label.getBoundsInLocal().getWidth() / 2));
        label.setLayoutY(midY - (label.getBoundsInLocal().getHeight() / 2));
        label.setText(String.format("%.2f", source.distance(target)));
    }
}
