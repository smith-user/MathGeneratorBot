package MathGeneratorBot.appContext;

public class ContextException extends RuntimeException {
    public ContextException() { super(); }
    public ContextException(String message) { super(message); }
    public ContextException(String message, Throwable cause) { super(message, cause); }
    public ContextException(Throwable cause) { super(cause); }
}
