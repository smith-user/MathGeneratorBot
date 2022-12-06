package handler.commands;

import handler.HandlerState;
import storage.JsonStorage;
import user.User;

/**
 * Команда, возвращающая статистику пользователя.
 */
public class StatCommand extends Command {
    public StatCommand(JsonStorage storage) {
        super(storage);
    }
    @Override
    public String execute(int userId, String arguments) {
        User user = storage.getUserById(userId);
        return "Всего вы сгенерировали *%d* задач и решили из них *%d* задач".formatted(
                user.getGeneratedTasks(), user.getSolvedTasks()
        );
    }

    @Override
    public HandlerState getState() {
        return null;
    }
}
