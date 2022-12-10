package MathGeneratorBot.appContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@Configuration
@ComponentScan
public class ApplicationContextConfiguration {

    private static final Logger logger = LogManager.getLogger(ApplicationContextConfiguration.class.getName());
    @Bean
    public Properties properties() {
        logger.info("Чтение свойств приложения.");
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream("src/MathGeneratorBot/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            logger.catching(e);
            throw logger.throwing(Level.FATAL, new ContextException(e));
        }
        return logger.traceExit(properties);
    }

}
