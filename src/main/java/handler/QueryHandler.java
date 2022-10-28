package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.StorageErrorException;
import handler.CommandHandlerException.UnknownCommandException;

import java.security.InvalidParameterException;

public class QueryHandler {
    private Command commandHandler;
    public String getResponse(String userQuery, int userId) {
        try {
            commandHandler = new CommandHandler();
        } catch (StorageErrorException e) {
            System.exit(1);
        }
        String[] userQueryArray = userQuery.split(" ", 2);
        String command = userQueryArray[0];
        String arguments = (userQueryArray.length > 1) ? userQueryArray[1] : null;
        String response;
        try {
            response = commandHandler.processCommand(userId, command, arguments);
        } catch (InvalidParameterException | StorageErrorException |
                NoGeneratedTasksException | UnknownCommandException e) {
            response = e.getMessage();
        }

        return response;
    }
}
