package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.UnknownCommandException;

import java.security.InvalidParameterException;

public interface Command {
    String processCommand(String command, String arguments)
            throws UnknownCommandException, NoGeneratedTasksException, InvalidParameterException;
}
