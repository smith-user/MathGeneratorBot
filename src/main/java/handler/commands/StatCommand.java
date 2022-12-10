package handler.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.JsonStorage;
import user.User;

/**
 * Команда, возвращающая статистику пользователя.
 */
public class StatCommand extends Command {
    private static final Logger logger = LogManager.getLogger(StatCommand.class.getName());
    public StatCommand(JsonStorage storage) {
        super(storage);
    }
    @Override
    public String execute(int userId, String arguments) {
        logger.traceEntry("arguments={}, userId={}", arguments, userId);
        User user = storage.getUserById(userId);
        return logger.traceExit("Всего вы сгенерировали *%d* задач и решили из них *%d* задач".formatted(
                user.getGeneratedTasks(), user.getSolvedTasks()
        ));
    }
}
