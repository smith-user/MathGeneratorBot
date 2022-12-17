package MathGeneratorBot;


import MathGeneratorBot.bot.TelegramBot;
import MathGeneratorBot.handler.QueryHandler;
import MathGeneratorBot.handler.commands.GenerateTasksCommand;
import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.tasksGenerator.TasksGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(GenerateTasksCommand.class.getName());
    public static void main(String[] args) {

        JsonStorage storage = null;
        try {
            storage = new JsonStorage();
        } catch (IOException | IllegalArgumentException e) {
            logger.catching(e);
        }
        TasksGenerator generator = TasksGenerator.instance();
        QueryHandler handler = new QueryHandler(storage, generator);
        TelegramBot bot = new TelegramBot(handler);
        bot.run();
    }
}