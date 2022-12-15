package MathGeneratorBot.handler.commands;

import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        User user = storage.getUserById(userId);
        return "Всего вы сгенерировали *%d* задач и решили из них *%d* задач".formatted(
                user.getGeneratedTasks(), user.getSolvedTasks()
        );
    }
}
