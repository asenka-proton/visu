package fr.asenka.visu;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {

        context = SpringApplication.run(Main.class, args);
        Application.launch(VisuApplication.class, args);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
