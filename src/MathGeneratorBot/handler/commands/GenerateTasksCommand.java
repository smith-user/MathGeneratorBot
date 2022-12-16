package MathGeneratorBot.handler.commands;

import MathGeneratorBot.handler.CommandType;
import MathGeneratorBot.handler.DefaultResponse;
import MathGeneratorBot.handler.HandlerState;
import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.tasksGenerator.MathTask;
import MathGeneratorBot.tasksGenerator.TaskCondition;
import MathGeneratorBot.tasksGenerator.TaskSolution;
import MathGeneratorBot.tasksGenerator.TasksGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import MathGeneratorBot.tasksGenerator.exceptions.TaskCreationException;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateTasksCommand extends TasksCommand {
    private final int DEFAULT_NUMBER_OF_TASK = 1;
    private static TasksGenerator generator = TasksGenerator.instance();
    private final Map<String, TasksGenerator.MathTaskTypes> taskType =  Map.of(
            "арифметика", TasksGenerator.MathTaskTypes.RATIONAL_ARITHMETIC,
            "уравнения", TasksGenerator.MathTaskTypes.LINEAR_EQUATION
    );

    private static final Logger logger = LogManager.getLogger(GenerateTasksCommand.class.getName());
    public GenerateTasksCommand(JsonStorage storage, HandlerState state,
                                LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                                LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(storage,state, tasks, tasksSolution);
    }

    @Override
    public String execute(int userId, String userQuery){
        int numberOfTasks;
        TasksGenerator.MathTaskTypes type = null;
        if (state == HandlerState.COMMAND_WAITING) {
            state = state.nextState(CommandType.TASKS);
            return DefaultResponse.GET_TASK_TYPE;
        }

        if (state == HandlerState.TASK_TYPE_WAITING) {
            String[] userQueryArray = userQuery.split(" ");

            type = taskType.get(userQueryArray[0]);
            if (type == null)
                return DefaultResponse.ILLEGAL_TYPE_OF_TASKS;

            try {
                numberOfTasks = userQueryArray.length > 1 ? Integer.parseInt(userQueryArray[1]) : DEFAULT_NUMBER_OF_TASK;
            } catch (NumberFormatException e) {
                return DefaultResponse.ILLEGAL_NUMBER_OF_TASKS;
            }

            clearUserPreviousTasks(userId);

            try {
                generateTasks(userId, type, numberOfTasks);
            } catch (TaskCreationException e) {
                logger.catching(Level.WARN, e);
                return DefaultResponse.TASK_GENERATE_FAIL;
            } catch (InvalidParameterException e) {
                logger.catching(Level.WARN, e);
                return e.getMessage();
            }

            try {
                storage.updateUsersGeneratedTasks(userId, numberOfTasks);
            } catch (IOException e) {
                logger.catching(Level.ERROR, e);
            }

            state = state.nextState(CommandType.TASKS);
            return getAnswerString(userId);
        }
        return null;
    }

    /**
     * @param userId id пользователя
     * @param type тип задач, которые нужно сгенерировать {@code TaskGenerator.MathTaskType}
     * @param number количество задач, которые нужно сгенерировать
     * @throws TaskCreationException Если при генерации возникла ошибка
     */
    private void generateTasks(int userId, TasksGenerator.MathTaskTypes type, int number) throws TaskCreationException {
        logger.traceEntry("userId={}, taskType={}, number={}", userId, type, number);
        MathTask taskType;
        for (int i = 0; i < number; i++) {
            taskType = generator.createTaskByType(type);
            TaskCondition taskCondition = taskType.getCondition();
            TaskSolution taskSolution = taskType.getSolution();
            tasks.get(userId).add(taskCondition);
            tasksSolution.get(userId).add(taskSolution);
        }
    }

    /**
     * очищает массив задач которые генерировал пользователь,
     * и создает пустой массив если пользователь еще ни разу не генерировал задачи
     * @param userId id пользователя
     */
    private void clearUserPreviousTasks(int userId) {
        if (tasks.containsKey(userId))
            tasks.get(userId).clear();
        else
            tasks.put(userId, new ArrayList<TaskCondition>());
        if (tasksSolution.containsKey(userId))
            tasksSolution.get(userId).clear();
        else
            tasksSolution.put(userId, new ArrayList<TaskSolution>());
    }

    /**
     * @param userId
     * @return результат выполнения команды в виде строки
     */
    private String getAnswerString(int userId) {
        StringBuilder tmpResponse = new StringBuilder();
        for (TaskCondition task : tasks.get(userId)) {
            tmpResponse.append(task.getCondition())
                    .append("`")
                    .append(task.getExpression())
                    .append("`")
                    .append("\n");
        }
        return tmpResponse.toString();
    }
}
