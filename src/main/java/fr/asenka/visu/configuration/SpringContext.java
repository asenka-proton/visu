package fr.asenka.visu.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class SpringContext {

    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext context) {
        SpringContext.context = context;
    }

    public static <T> T getBean(Class<T> beanClass) {
        if (context == null) {
            throw new IllegalStateException("Spring context not initialized");
        }
        log.debug("Requesting spring bean {}", beanClass);
        return context.getBean(beanClass);
    }
}

