package handler.commands;

import handler.Command;
import handler.CommandHandlerException.StorageErrorException;
import handler.DefaultResponse;
import storage.JsonStorage;
import tasksGenerator.exceptions.TaskCreationException;

import java.io.IOException;

public class StartCommand extends Command {

    public StartCommand(JsonStorage storage) {
        super(null, storage, null, null);
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
