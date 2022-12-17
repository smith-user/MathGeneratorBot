package MathGeneratorBot.logMessage;

import MathGeneratorBot.tasksGenerator.MathAPI.APIQueryException;

import java.util.Map;

public class MathAPIMessage extends LogMessage {

    public enum MessageType {
        PERFORM_QUERY("Отправка API-запроса"),
        QUERY_EXCEPTION("Ошибка отпрвки API-запроса."),
        RESPONSE_RECEIVED("Ответ получен.");

        private final String message;
        MessageType(String message) {
            this.message = message;
        }
    }
    private final MessageType type;

    public MathAPIMessage(MessageType type, Map<String, String> parameters) {
        super(parameters);
        this.type = type;
    }

    @Override
    public String getFormattedMessage() {
        return getFormat().formatted(type.message, parametersToString());
    }

    @Override
    public APIQueryException getThrowable() {
        return new APIQueryException(getFormattedMessage());
    }

    @Override
    public APIQueryException getThrowable(Throwable e) {
        return new APIQueryException(getFormattedMessage(), e);
    }
}
