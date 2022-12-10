package MathGeneratorBot.tasksGenerator.exceptions;

/**
 * Исключения данного типа возникают при ошибке создания математической задачи:
 * ее условия и/или решения.
 */
public class TaskCreationException extends Exception {
    public TaskCreationException() { super(); }
    public TaskCreationException(String message) { super(message); }
    public TaskCreationException(String message, Throwable cause) { super(message, cause); }
    public TaskCreationException(Throwable cause) { super(cause); }
}
