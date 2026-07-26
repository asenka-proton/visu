package fr.asenka.visu.utils;

import javafx.css.Styleable;
import javafx.scene.Scene;

import java.net.URL;

public final class JavaFXUtils {

    public static void applyCssClass(Styleable view, String className) {
        view.getStyleClass().add(className);
    }

    public static void setCssStylesheet(Scene scene, String stylesheet) {

        final URL url = JavaFXUtils.class.getResource(stylesheet);

        if (url == null) {
            throw new IllegalStateException("Unable to load: " + stylesheet);
        }
        final String css = url.toExternalForm();

        scene.getStylesheets().add(css);
    }

    private JavaFXUtils() {
    }
}
