package fr.asenka.visu;

import fr.asenka.visu.configuration.SpringContext;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        SpringContext.setContext(SpringApplication.run(Main.class, args));
        Application.launch(VisuApplication.class, args);
    }
}
