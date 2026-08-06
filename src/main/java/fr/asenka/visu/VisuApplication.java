package fr.asenka.visu;

import fr.asenka.visu.configuration.SpringContext;
import fr.asenka.visu.configuration.VisuProperties;
import fr.asenka.visu.ui.views.GraphView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

@Slf4j
public class VisuApplication extends Application {

    private VisuProperties properties;
    private GraphView graphView;

    @Override
    public void start(Stage primaryStage) {

        properties = SpringContext.getBean(VisuProperties.class);
        graphView = SpringContext.getBean(GraphView.class);

        final Scene scene = createScene();

        graphView.initialize();

        scene.getStylesheets().add(properties.getUi().getStylesheet());

        primaryStage.setTitle(properties.getUi().getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
        log.debug("JavaFX application started!");
    }

    private @NonNull Scene createScene() {
        final Scene scene = new Scene(
                graphView,
                properties.getUi().getWidth(),
                properties.getUi().getHeight()
        );

        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);
        return scene;
    }

    private void onKeyPressed(KeyEvent event) {
        log.trace("[KeyPressed]");
        if (event.getCode() == KeyCode.CONTROL) {
            log.debug("Layout engine: ON");
            graphView.setLayoutEngineActive(true);
        }
    }

    private void onKeyReleased(KeyEvent event) {
        log.trace("[KeyReleased]");
        if (event.getCode() == KeyCode.CONTROL) {
            log.debug("Layout engine: OFF");
            graphView.setLayoutEngineActive(false);
        }
    }
}
