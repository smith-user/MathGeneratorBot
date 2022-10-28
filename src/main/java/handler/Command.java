package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.StorageErrorException;
import handler.CommandHandlerException.UnknownCommandException;

import java.security.InvalidParameterException;

public interface Command{
    /**
     * @param userId id пользователя
     * @param command команда, отправленная пользователем
     * @param arguments аргументы команды
     * @return строка, ответ пользователю
     * @throws UnknownCommandException   Если пользователь отправил неизвестную команду
     * @throws NoGeneratedTasksException Если перед вызовом команды {@code ANSWERS}
     *          не была вызвна команда {@code TASKS}
     * @throws InvalidParameterException Если были введены некорректные аргументы для комманды {@code TASKS}
     * @throws StorageErrorException Если возникла ошибка в хранилище
     */
    String processCommand(int userId, String command, String arguments)
            throws UnknownCommandException, NoGeneratedTasksException,
            InvalidParameterException, StorageErrorException;
}
