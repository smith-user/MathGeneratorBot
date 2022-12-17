package MathGeneratorBot.appContext;

import MathGeneratorBot.logMessage.PropertiesMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static MathGeneratorBot.logMessage.PropertiesMessage.MessageType.*;


@Configuration
@ComponentScan
public class ApplicationContextConfiguration {

    private static final Logger logger = LogManager.getLogger(ApplicationContextConfiguration.class.getName());
    @Bean
    public Properties properties() {
        logger.traceEntry();
        final String filename = "src/MathGeneratorBot/resources/application.properties";
        logger.info(new PropertiesMessage(SET_PROPERTIES, Map.of("filename", filename)).getFormattedMessage());
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
        } catch (IOException e) {
            logger.catching(Level.DEBUG, e);
            throw logger.throwing(new PropertiesMessage(READING_ERROR, Map.of("filename", filename)).getThrowable(e));
        }
        logger.traceExit();
        return properties;
    }

}
