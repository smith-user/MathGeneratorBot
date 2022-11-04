package tasksGenerator.exceptions;

/**
 * Исключения данного типа возникают при ошибке создания решения математической задачи.
 */
public class TaskSolutionException extends Exception {
    public TaskSolutionException() { super(); }
    public TaskSolutionException(String message) { super(message); }
    public TaskSolutionException(String message, Throwable cause) { super(message, cause); }
    public TaskSolutionException(Throwable cause) { super(cause); }
}
