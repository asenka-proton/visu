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

import static fr.asenka.visu.ui.JavaFXUtils.point2D;

public class NodeView extends StackPane {

    private final Group parent;
    @Getter
    private final Node model;
    private final Rectangle shape;
    private final Text label;

    private Point2D clickedLocation;     // Position du nœud dans l'espace local au moment du clic
    private Point2D mouseStartPosition;   // Position de la souris dans l'espace local au moment du clic

    public NodeView(Node model, GraphView graphView) {
        this.model = model;
        this.parent = graphView.getContentGroup();
        this.getStyleClass().add("node-container");

        shape = new Rectangle(50, 50);
        shape.getStyleClass().add("node-shape");

        label = new Text(model.getLabel());
        label.getStyleClass().add("node-label");

        getChildren().addAll(shape, label);

        setOnMousePressed(this::onMousePressed);
        setOnMouseDragged(this::onMouseDragged);

        updatePosition();
    }

    public void updatePosition() {
        final double newX = model.x() - (shape.getWidth() / 2);
        final double newY = model.y() - (shape.getHeight() / 2);
        relocate(newX, newY);
    }

    private void onMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Stocker la position du nœud (x et y sont des doubles, on crée un Point2D)
            clickedLocation = point2D(model.getLocation());

            // Convertir les coordonnées de la souris dans l'espace local du contentGroup
            mouseStartPosition = parent.sceneToLocal(event.getSceneX(), event.getSceneY());
        }
    }

    private void onMouseDragged(MouseEvent event) {
        // Convertir les coordonnées actuelles dans l'espace local du contentGroup
        final Point2D currentLocalPoint = parent.sceneToLocal(event.getSceneX(), event.getSceneY());

        // Calculer le décalage (delta) en utilisant la soustraction de Point2D
        final double deltaX = currentLocalPoint.getX() - mouseStartPosition.getX();
        final double deltaY = currentLocalPoint.getY() - mouseStartPosition.getY();

        model.setLocation(clickedLocation.getX() + deltaX, clickedLocation.getY() + deltaY);
        updatePosition();
    }


    public final double getShapeWidth() {
        return shape.getWidth();
    }

    public final double getShapeHeight() {
        return shape.getHeight();
    }
}
