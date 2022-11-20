package tasksGenerator.wolframAlphaAPI;

public class APIQueryException extends Exception{
    public APIQueryException() { super(); }
    public APIQueryException(String message) { super(message); }
    public APIQueryException(String message, Throwable cause) { super(message, cause); }
    public APIQueryException(Throwable cause) { super(cause); }
}
