package handler;

import handler.commandHandlers.CommandHandler;
import handler.commandHandlers.GeneratorHandler;
import handler.commandHandlers.HelpHandler;
import handler.commandHandlers.UserHandler;

public class HandlerFactory {
    public CommandHandler createHandler(String type) {
        CommandHandler commandHandler = switch (type) {
            case "/help" -> new HelpHandler();
            case "/start" -> new UserHandler();
            case "/generate" -> new GeneratorHandler();
            default -> null;
        };

        return commandHandler;
    }
}
