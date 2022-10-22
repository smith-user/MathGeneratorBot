package handler.CommandHandlerException;

public class UnknownCommandException extends Exception{
    public UnknownCommandException() { super(); }
    public UnknownCommandException(String message) { super(message); }
    public UnknownCommandException(String message, Throwable cause) { super(message, cause); }
    public UnknownCommandException(Throwable cause) { super(cause); }
}
