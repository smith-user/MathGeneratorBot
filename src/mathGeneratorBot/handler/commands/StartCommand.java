package mathGeneratorBot.handler.commands;

import mathGeneratorBot.handler.DefaultResponse;
import mathGeneratorBot.storage.JsonStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        try {
            storage.addUser(userId);
        } catch (IOException e) {
            return DefaultResponse.USER_IDENTIFICATION_FAIL;
        }
        return DefaultResponse.USER_IDENTIFICATION_SUCCESS;
    }
}
