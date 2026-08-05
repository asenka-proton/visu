package fr.asenka.visu.ui.configuration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UiProperties {

    private String title;
    private double width;
    private double height;
    private String stylesheet;
    private double zoomMinScale;
    private double zoomMaxScale;
    private double zoomSensitivity;
}
