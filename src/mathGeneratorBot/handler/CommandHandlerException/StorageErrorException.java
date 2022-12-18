package mathGeneratorBot.handler.CommandHandlerException;

public class StorageErrorException extends Exception{
    public StorageErrorException() { super(); }
    public StorageErrorException(String message) { super(message); }
    public StorageErrorException(String message, Throwable cause) { super(message, cause); }
    public StorageErrorException(Throwable cause) { super(cause); }
}
