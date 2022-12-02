package handler.commands;

import handler.DefaultResponse;
import storage.JsonStorage;

import java.io.IOException;

public class StartCommand extends Command {

    public StartCommand(JsonStorage storage) {
        super(storage);
    }

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
