package mathGeneratorBot.appContext;

import mathGeneratorBot.logMessage.PropertiesMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static mathGeneratorBot.logMessage.PropertiesMessage.MessageType.*;

@Component
@Scope("singleton")
public class AppProperties {

    public enum PropertyNames {
        BOT_NAME("bot.name"),
        BOT_TOKEN("bot.token"),
        WOLFRAM_API_ID("wolframAPI.token");
        private final String name;
        PropertyNames(String name) {
            this.name = name;
        }
    }

    private static final Logger logger = LogManager.getLogger(AppProperties.class.getName());
    private final Map<PropertyNames, String> mapProperties;

    public AppProperties(@Autowired Properties properties) {
        logger.traceEntry();
        mapProperties = new HashMap<>();
        for(PropertyNames property : PropertyNames.values()) {
            String value = properties.getProperty(property.name);
            if (value != null) {
                mapProperties.put(property, value);
            } else {
                throw logger.throwing(new PropertiesMessage(PROPERTY_NOT_FOUND, Map.of("property", property.name)).getThrowable());
            }
        }
        logger.info(new PropertiesMessage(READING_WAS_SUCCESSFUL, null).getFormattedMessage());
        logger.traceExit();
    }

    public String getProperty(PropertyNames property) {
        return mapProperties.get(property);
    }


}
