package handler.commands;

import handler.Command;
import storage.JsonStorage;
import user.User;

public class StatCommand extends Command {
    public StatCommand(JsonStorage storage) {
        super(null, storage, null, null);
    }

    @Override
    public String execute(int userId, String arguments) {
        User user = storage.getUserById(userId);
        return "Всего вы сгенерировали *%d* задач и решили из них *%d* задач".formatted(
                user.getGeneratedTasks(), user.getSolvedTasks()
        );
    }
}
