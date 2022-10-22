package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.UnknownCommandException;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class QueryHandler {
    private final Command commandHandler = new CommandHandler();
    public String getResponse(String userQuery) {
        String[] userQueryArray = userQuery.split(" ", 2);
        String command = userQueryArray[0];
        String arguments = (userQueryArray.length > 1) ? userQueryArray[1] : null;
        String response;
        try {
            response = commandHandler.processCommand(command, arguments);
        } catch (InvalidParameterException e) {
            response = e.getMessage();
        } catch (NoGeneratedTasksException e) {
            response = "Нет задач для которых можно вывести ответы";
        } catch (UnknownCommandException e) {
            response = "Неизветсная команда";
        }

        return response;
    }
}
