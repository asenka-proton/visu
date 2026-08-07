package fr.asenka.visu.engine.configuration;

import fr.asenka.visu.configuration.VisuProperties;
import fr.asenka.visu.engine.*;
import fr.asenka.visu.shared.Vector2D;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EngineConfiguration {

    private final VisuProperties properties;

    @Bean
    public RepulsionForce repulsionForce() {
        return new RepulsionForce(
                properties.getEngine().getForces().getRepulsion().getStrength(),
                properties.getEngine().getForces().getRepulsion().getMinDistance()
        );
    }

    @Bean
    public AttractionForce attractionForce() {
        return new AttractionForce(
                properties.getEngine().getForces().getAttraction().getStrength(),
                properties.getEngine().getForces().getAttraction().getRestLength()
        );
    }

    @Bean
    public GravityForce gravityForce() {

        final Vector2D gravityCenter = new Vector2D(
                properties.getUi().getWidth() / 2,
                properties.getUi().getHeight() / 2
        );
        return new GravityForce(
                gravityCenter,
                properties.getEngine().getForces().getGravity().getStrength()
        );
    }

    @Bean
    public ForcesLayoutEngine layoutEngine(RepulsionForce repulsion,
                                           AttractionForce attraction,
                                           GravityForce gravity) {
        return new ForcesLayoutEngine(
                properties.getEngine().getLayout().getDamping(),
                repulsion,
                attraction,
                gravity
        );
    }
}
