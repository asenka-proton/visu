package fr.asenka.visu.utils;

import fr.asenka.visu.model.Graph;

public final class GraphUtils {

    private static long idCount = 1L;

    public static long getId(Graph graph) {

        long id;
        do {
            id = idCount++;
        } while (graph.contains(id));
        return id;
    }

    private GraphUtils() {
    }
}
