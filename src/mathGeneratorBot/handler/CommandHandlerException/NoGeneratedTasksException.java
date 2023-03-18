package mathGeneratorBot.handler.CommandHandlerException;

public class NoGeneratedTasksException extends Exception{
    public NoGeneratedTasksException() { super(); }
    public NoGeneratedTasksException(String message) { super(message); }
    public NoGeneratedTasksException(String message, Throwable cause) { super(message, cause); }
    public NoGeneratedTasksException(Throwable cause) { super(cause); }
}
