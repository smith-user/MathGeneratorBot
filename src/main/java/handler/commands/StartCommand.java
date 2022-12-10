package handler.commands;

import handler.DefaultResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.JsonStorage;

import java.io.IOException;

/**
 * Команда, добавляющая пользователя в хранилище {@code JsonStorage}.
 */
public class StartCommand extends Command {

    public StartCommand(JsonStorage storage) {
        super(storage);
    }
    private static final Logger logger = LogManager.getLogger(StartCommand.class.getName());

    @Override
    public String execute(int userId, String arguments) {
        logger.traceEntry("arguments={}, userId={}", arguments, userId);
        try {
            storage.addUser(userId);
        } catch (IOException e) {
            return logger.traceExit(DefaultResponse.USER_IDENTIFICATION_FAIL);
        }
        return logger.traceExit(DefaultResponse.USER_IDENTIFICATION_SUCCESS);
    }
}
