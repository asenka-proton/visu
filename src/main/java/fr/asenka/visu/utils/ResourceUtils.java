package fr.asenka.visu.utils;

import javafx.scene.Scene;

import java.net.URL;

public final class ResourceUtils {


    public static String getResourcePath(String resource) {
        final URL url = ResourceUtils.class.getResource(resource);

        if (url == null) {
            throw new IllegalArgumentException("resource not found: " + resource);
        }
        return url.toExternalForm();
    }

    private ResourceUtils() {
    }
}
