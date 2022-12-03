package handler.commands;

import handler.CommandType;
import handler.DefaultResponse;
import handler.HandlerState;
import storage.JsonStorage;
import tasksGenerator.MathTask;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskCreationException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateTasksCommand extends TasksCommand {
    private final int DEFAULT_NUMBER_OF_TASK = 1;
    private TasksGenerator generator;

    private final Map<String, TasksGenerator.MathTaskTypes> taskType =  Map.of(
            "арифметика", TasksGenerator.MathTaskTypes.RATIONAL_ARITHMETIC,
            "уравнения", TasksGenerator.MathTaskTypes.LINEAR_EQUATION
    );
    public GenerateTasksCommand(TasksGenerator generator, JsonStorage storage, HandlerState state,
                                LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                                LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(storage,state, tasks, tasksSolution);
        this.generator = generator;
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

            if (tasks.containsKey(userId))
                tasks.get(userId).clear();
            else
                tasks.put(userId, new ArrayList<TaskCondition>());
            if (tasksSolution.containsKey(userId))
                tasksSolution.get(userId).clear();
            else
                tasksSolution.put(userId, new ArrayList<TaskSolution>());

            try {
                generateTasks(userId, type, numberOfTasks);
            } catch (TaskCreationException e) {
                return DefaultResponse.TASK_GENERATE_FAIL;
            } catch (InvalidParameterException e) {
                return e.getMessage();
            }
            state = state.nextState(CommandType.TASKS);
            return getAnswerString(userId);
        }
        return null;
        }
    private void generateTasks(int userId, TasksGenerator.MathTaskTypes type, int number) throws TaskCreationException {
        MathTask taskType;
        for (int i = 0; i < number; i++) {
            taskType = generator.createTaskByType(type);
            TaskCondition taskCondition = taskType.getCondition();
            TaskSolution taskSolution = taskType.getSolution();
            tasks.get(userId).add(taskCondition);
            tasksSolution.get(userId).add(taskSolution);
        }
    }

    private void generateTasks(int userId, int number) throws TaskCreationException {
        MathTask taskType;
        for (int i = 0; i < number; i++) {
            taskType = generator.createTask();
            TaskCondition taskCondition = taskType.getCondition();
            TaskSolution taskSolution = taskType.getSolution();
            tasks.get(userId).add(taskCondition);
            tasksSolution.get(userId).add(taskSolution);
        }
    }

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
