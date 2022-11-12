package handler.commands;

import handler.Command;
import handler.DefaultResponse;
import storage.JsonStorage;
import tasksGenerator.MathTask;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskCreationException;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class GenerateTasksCommand extends Command {
    private final int DEFAULT_NUMBER_OF_TASK = 5;
    public GenerateTasksCommand(TasksGenerator generator, ArrayList<TaskCondition> tasks,
                                ArrayList<TaskSolution> tasksSolution) {
        super(generator, null, tasks, tasksSolution);
    }

    @Override
    public String execute(int userId, String arguments){
        tasks.clear();
        tasksSolution.clear();
        StringBuilder tmpResponse = new StringBuilder();

        if (arguments == null) {
            try{
                generateTasks(DEFAULT_NUMBER_OF_TASK);
            } catch (TaskCreationException e) {
                return DefaultResponse.TASK_GENERATE_FAIL;
            } catch (InvalidParameterException e) {
                return e.getMessage();
            }
        } else {
            String[] argumentsArray = arguments.split(" ");
            String type = argumentsArray[0];
            int number;
            try {
                number = (argumentsArray.length > 1) ? Integer.parseInt(argumentsArray[1]) : 1;
            } catch (NumberFormatException e) {
                return DefaultResponse.ILLEGAL_NUMBER_OF_TASKS;
            }
            try {
                generateTasks(type, number);
            } catch (TaskCreationException e) {
                return DefaultResponse.TASK_GENERATE_FAIL;
            } catch (InvalidParameterException e) {
                return e.getMessage();
            }
        }

        for (TaskCondition task : tasks) {
            tmpResponse.append(task.getCondition())
                    .append(task.getExpression())
                    .append("\n");
        }
        return tmpResponse.toString();
    }

    private void generateTasks(String type, int number) throws TaskCreationException {
        MathTask taskType;
        for (int i = 0; i < number; i++) {
            taskType = generator.getNewTaskByType(type);
            TaskCondition taskCondition = taskType.getCondition();
            TaskSolution taskSolution = taskType.getSolution();
            tasks.add(taskCondition);
            tasksSolution.add(taskSolution);
        }
    }

    private void generateTasks(int number) throws TaskCreationException {
        MathTask taskType;
        for (int i = 0; i < number; i++) {
            taskType = generator.getNewTask();
            TaskCondition taskCondition = taskType.getCondition();
            TaskSolution taskSolution = taskType.getSolution();
            tasks.add(taskCondition);
            tasksSolution.add(taskSolution);
        }
    }

}
