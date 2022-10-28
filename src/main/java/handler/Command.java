package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.StorageErrorException;
import handler.CommandHandlerException.UnknownCommandException;

import java.security.InvalidParameterException;

public interface Command{
    String processCommand(int userId, String command, String arguments)
            throws UnknownCommandException, NoGeneratedTasksException,
            InvalidParameterException, StorageErrorException;
}
