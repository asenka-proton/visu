package fr.asenka.visu.configuration;

import fr.asenka.visu.engine.configuration.EngineProperties;
import fr.asenka.visu.ui.configuration.UiProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
@ConfigurationProperties(prefix = "visu")
public class VisuProperties {

    private UiProperties ui;
    private EngineProperties engine;
}
