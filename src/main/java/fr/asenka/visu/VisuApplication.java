package fr.asenka.visu;

import fr.asenka.visu.configuration.SpringContext;
import fr.asenka.visu.configuration.VisuProperties;
import fr.asenka.visu.ui.views.GraphView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class VisuApplication extends Application {

    private VisuProperties properties;
    private GraphView graphView;

    @Override
    public void start(Stage primaryStage) {

        properties = SpringContext.getBean(VisuProperties.class);
        graphView = SpringContext.getBean(GraphView.class);

        final Scene scene = new Scene(
                graphView,
                properties.getUi().getWidth(),
                properties.getUi().getHeight()
        );

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                graphView.toggleLayout();
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                graphView.toggleLayout();
            }
        });

        graphView.startTimer();

        scene.getStylesheets().add(properties.getUi().getStylesheet());

        primaryStage.setTitle(properties.getUi().getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
