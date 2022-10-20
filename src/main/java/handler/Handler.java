package handler;

import handler.commandHandlers.CommandHandler;

public class Handler {

    private final HandlerFactory handlerFactory = new HandlerFactory();
    public String getResponse(String userQuery) {
        String[] userQueryArray = userQuery.split(" ", 1);
        String command = userQueryArray[0];
        String data = (userQueryArray.length > 1) ? userQueryArray[1] : null;
        CommandHandler commandHandler = handlerFactory.createHandler(command);
        String response = commandHandler.processCommand(data);
        return response;
    }
}
