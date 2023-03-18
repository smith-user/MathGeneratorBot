package mathGeneratorBot.logMessage;

import mathGeneratorBot.appContext.ContextException;
import java.util.Map;

public class PropertiesMessage extends LogMessage {

    public enum MessageType {
        SET_PROPERTIES("Чтение файла с параметрами приложения."),
        READING_ERROR("Неудалось прочитать файл."),
        PROPERTY_NOT_FOUND("Параметр приложения не найден."),
        READING_WAS_SUCCESSFUL("Параметры приложения загружены.");

        private final String message;
        MessageType(String message) {
            this.message = message;
        }
    }
    private final MessageType type;

    public PropertiesMessage(MessageType type, Map<String, String> parameters) {
        super(parameters);
        this.type = type;
    }

    @Override
    public String getFormattedMessage() {
        return getFormat().formatted(type.message, parametersToString());
    }


    @Override
    public ContextException getThrowable() {
        return new ContextException(getFormattedMessage());
    }

    @Override
    public ContextException getThrowable(Throwable e) {
        return new ContextException(getFormattedMessage(), e);
    }
}
