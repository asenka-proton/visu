package fr.asenka.visu;

import fr.asenka.visu.configuration.SpringContext;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        log.debug("Application starting...");
        SpringContext.setContext(SpringApplication.run(Main.class, args));
        Application.launch(VisuApplication.class, args);
    }
}
