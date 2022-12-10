package MathGeneratorBot.appContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@Scope("singleton")
public class AppProperties {
    private static final Logger logger = LogManager.getLogger(AppProperties.class.getName());
    private final Map<PropertyNames, String> mapProperties;

    public AppProperties(@Autowired Properties properties) {
        mapProperties = new HashMap<>();
        for(PropertyNames property : PropertyNames.values()) {
            try {
                mapProperties.put(property, properties.getProperty(property.name));
            } catch (Exception e) {
                logger.catching(e);
                throw logger.throwing(Level.FATAL, new ContextException("Отсутствует свойство \"%s\"".formatted( property.name)));
            }
        }
        logger.traceExit();
    }

    public String getProperty(PropertyNames property) {
        return mapProperties.get(property);
    }

    public enum PropertyNames {
        BOT_NAME("bot.name"),
        BOT_TOKEN("bot.token"),
        WOLFRAM_API_ID("wolframAPI.token");
        private final String name;
        PropertyNames(String name) {
            this.name = name;
        }
    }
}
