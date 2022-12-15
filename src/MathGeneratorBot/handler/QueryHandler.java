package MathGeneratorBot.handler;

import MathGeneratorBot.handler.commands.*;
import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.tasksGenerator.TaskCondition;
import MathGeneratorBot.tasksGenerator.TaskSolution;
import MathGeneratorBot.tasksGenerator.TasksGenerator;
import MathGeneratorBot.PDF.PDFAnswersFile;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс обработчика пользовательского запроса.
 */
public class QueryHandler {
    private static final TasksGenerator generator = TasksGenerator.instance();
    private JsonStorage storage;

    private PDFAnswersFile answersFile = new PDFAnswersFile();
    /**
     * Таблица содержащая состояние обработчика для каждого пользователя.
     */
    private LinkedHashMap<Integer, HandlerState> state = new LinkedHashMap<Integer, HandlerState>() {
        @Override
        protected boolean removeEldestEntry(final Map.Entry eldest){
            return size() > 5;
        }
    };
    /**
     * Таблица содержащая массив последних сгенерированных задач для каждого пользователя.
     */
    private final LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks = new LinkedHashMap<Integer, ArrayList<TaskCondition>>() {
        @Override
        protected boolean removeEldestEntry(final Map.Entry eldest){
            return size() > 5;
        }
    };
    /**
     * Таблица содержащая массив ответов для последних сгенерированных задач для каждого пользователя.
     */
    private LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution = new LinkedHashMap<Integer, ArrayList<TaskSolution>>() {
        @Override
        protected boolean removeEldestEntry(final Map.Entry eldest){
            return size() > 5;
        }
    };

    private static final Logger logger = LogManager.getLogger(QueryHandler.class.getName());

    public QueryHandler() {
        try {
            storage = new JsonStorage();
        } catch (IOException | InvalidPathException | JsonSyntaxException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    /**
     * Метод, возвращающий одино из возможных состояний обработчика {@code HandlerState}
     * для каждого пользователя.
     * @param userId id пользователя.
     * @return состояние обработчика.
     */
    public HandlerState getState(int userId) {
        return state.get(userId);
    }

    public PDFAnswersFile getAnswersFile() {
        return answersFile;
    }

    /**
     * Метод, обрабатывающий пользовательский запрос.
     * @param userQuery строка пользовательского запроса
     * @param userId id пользователя
     * @return строка - ответ пользователю
     */
    public String getResponse(String userQuery, Integer userId) {
        Command command;
        CommandType commandType = null;
        logger.info("id={}, query={}", userId, userQuery);
        if (!state.containsKey(userId)) {
            state.put(userId, HandlerState.COMMAND_WAITING);
        }
        if (state.get(userId) == HandlerState.GIVE_ANSWER_FILE) {
            state.put(userId, state.get(userId).nextState(null));
        }
        if (state.get(userId) == HandlerState.COMMAND_WAITING) {
            commandType = CommandType.valueByQuery(userQuery);
            if (commandType == null)
                return DefaultResponse.UNKNOWN_COMMAND;
        } else if (state.get(userId) == HandlerState.ANSWER_WAITING) {
            commandType = CommandType.ANSWERS;
        } else if (state.get(userId) == HandlerState.TASK_TYPE_WAITING)
            commandType = CommandType.TASKS;
        else if (state.get(userId) == HandlerState.USERS_TASK_WAITING)
            commandType = CommandType.SOLVE;

        switch (commandType) {
            case HELP:
                command = new HelpCommand();
                break;
            case START:
                command = new StartCommand(storage);
                break;
            case TASKS:
                command = new GenerateTasksCommand(generator, storage, state.get(userId), tasks, tasksSolution);
                break;
            case ANSWERS:
                command = new AnswersCommand(storage, state.get(userId), answersFile, tasks, tasksSolution);
                break;
            case STAT:
                command = new StatCommand(storage);
                break;
            case SOLVE:
                command = new SolveUserTaskCommand(storage, generator, state.get(userId));
                break;
            default:
                return null;
        }
        String response = command.execute(userId, userQuery);
        state.put(userId, command.getState());
        return response;
    }
}
