package mathGeneratorBot.handler.commands;

import mathGeneratorBot.storage.JsonStorage;
import mathGeneratorBot.handler.HandlerState;

/**
 * Абстракный класс для реализации классов команд бота.
 */
abstract public class Command{

    protected JsonStorage storage;

    public Command(JsonStorage storage) {
        this.storage = storage;

    }

    /**
     * Метод, выполняющий соответсвующую команду бота
     *
     * @param userId    id пользователя
     * @param arguments аргументы команды
     * @return строка - результат выполнения команды
     */
    abstract public String execute(int userId, String arguments);

    /**
     * @return возвращает состояние обработчика
     */
    public HandlerState getState() {
        return HandlerState.COMMAND_WAITING;
    }
}
