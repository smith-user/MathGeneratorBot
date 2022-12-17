package MathGeneratorBot.logMessage;

import MathGeneratorBot.tasksGenerator.exceptions.TaskCreationException;

import java.util.Map;

public class GeneratorMessage extends LogMessage {

    public enum MessageType {
        CREATE_TASK("Создание новой математической задачи."),
        CREATION_ERROR("Неудалось создать задачу."),
        SOLVE_TASK("Решение математической задачи пользователя.");

        private final String message;
        MessageType(String message) {
            this.message = message;
        }
    }
    private final MessageType type;

    public GeneratorMessage(MessageType type, Map<String, String> parameters) {
        super(parameters);
        this.type = type;
    }

    @Override
    public String getFormattedMessage() {
        return getFormat().formatted(type.message, parametersToString());
    }

    @Override
    public TaskCreationException getThrowable() {
        return new TaskCreationException(getFormattedMessage());
    }

    @Override
    public TaskCreationException getThrowable(Throwable e) {
        return new TaskCreationException(getFormattedMessage(), e);
    }
}
