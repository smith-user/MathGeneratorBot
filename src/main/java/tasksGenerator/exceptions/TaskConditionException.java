package tasksGenerator.exceptions;

/**
 * Исключения данного типа возникают при ошибке создания условия математической задачи.
 */
public class TaskConditionException extends Exception {
    public TaskConditionException() { super(); }
    public TaskConditionException(String message) { super(message); }
    public TaskConditionException(String message, Throwable cause) { super(message, cause); }
    public TaskConditionException(Throwable cause) { super(cause); }
}
