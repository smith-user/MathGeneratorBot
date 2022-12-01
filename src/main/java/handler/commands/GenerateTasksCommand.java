package handler.commands;

import handler.Command;
import handler.DefaultResponse;
import storage.JsonStorage;
import tasksGenerator.MathTask;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskCreationException;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateTasksCommand extends Command {
    private final int DEFAULT_NUMBER_OF_TASK = 5;

    private final Map<String, TasksGenerator.MathTaskTypes> taskType =  Map.of(
            "арифметика", TasksGenerator.MathTaskTypes.RATIONAL_ARITHMETIC,
            "уравнения", TasksGenerator.MathTaskTypes.LINEAR_EQUATION
    );
    public GenerateTasksCommand(TasksGenerator generator, JsonStorage storage,
                                LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                                LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(generator, storage, tasks, tasksSolution);
    }

    @Override
    public String execute(int userId, String arguments){
        if (tasks.containsKey(userId))
            tasks.get(userId).clear();
        else
            tasks.put(userId, new ArrayList<TaskCondition>());
        if (tasksSolution.containsKey(userId))
            tasksSolution.get(userId).clear();
        else
            tasksSolution.put(userId, new ArrayList<TaskSolution>());

        StringBuilder tmpResponse = new StringBuilder();

        if (arguments == null) {
            try{
                generateTasks(userId, DEFAULT_NUMBER_OF_TASK);
            } catch (TaskCreationException e) {
                return DefaultResponse.TASK_GENERATE_FAIL;
            } catch (InvalidParameterException e) {
                return e.getMessage();
            }
        } else {
            String[] argumentsArray = arguments.split(" ");
            String strType = argumentsArray[0];
            TasksGenerator.MathTaskTypes type = taskType.get(strType);
            if (type == null)
                return DefaultResponse.ILLEGAL_TYPE_OF_TASKS;
            int number;
            try {
                number = (argumentsArray.length > 1) ? Integer.parseInt(argumentsArray[1]) : 1;
            } catch (NumberFormatException e) {
                return DefaultResponse.ILLEGAL_NUMBER_OF_TASKS;
            }
            try {
                generateTasks(userId, type, number);
            } catch (TaskCreationException e) {
                return DefaultResponse.TASK_GENERATE_FAIL;
            } catch (InvalidParameterException e) {
                return e.getMessage();
            }
        }

        try {
            storage.updateUsersGeneratedTasks(userId, tasks.get(userId).size());
        } catch (IOException ignored) {}

        for (TaskCondition task : tasks.get(userId)) {
            tmpResponse.append(task.getCondition())
                    .append("`")
                    .append(task.getExpression())
                    .append("`")
                    .append("\n");
        }
        return tmpResponse.toString();
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

}
