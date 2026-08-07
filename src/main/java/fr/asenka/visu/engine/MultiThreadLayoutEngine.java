package fr.asenka.visu.engine;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadLayoutEngine {

    private final List<Force> forces = new ArrayList<>();
    private final double damping;

    public MultiThreadLayoutEngine(double damping, Force... forces) {
        this.damping = damping;
        this.forces.addAll(List.of(forces));
    }
}
