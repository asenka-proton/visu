package fr.asenka.visu.engine.configuration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngineProperties {

    private Layout layout;
    private Forces forces;

    @Data
    @Builder
    public static class Layout {
        private double damping;
    }

    @Data
    @Builder
    public static class Forces {
        private Repulsion repulsion;
        private Attraction attraction;
        private Gravity gravity;
    }

    @Data
    @Builder
    public static class Repulsion {
        private double strength;
        private double minDistance;
    }

    @Data
    @Builder
    public static class Attraction {
        private double strength;
        private double restLength;
    }

    @Data
    @Builder
    public static class Gravity {
        private double strength;
        private double centerX;
        private double centerY;
    }
}
