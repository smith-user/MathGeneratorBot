package handler;

import com.google.gson.JsonSyntaxException;
import handler.CommandHandlerException.NoGeneratedTasksException;
//import handler.CommandHandlerException.StorageErrorException;
import handler.CommandHandlerException.StorageErrorException;
import handler.CommandHandlerException.UnknownCommandException;
//import storage.JsonStorage;
import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.taskTypes.TaskType;


import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class CommandHandler implements Command{
    private static final TasksGenerator generator = new TasksGenerator();

    private static StringBuilder help_text = new StringBuilder("""
            Бот может генерировать математические задачи по заданной теме.
            /getTasks <type> <number> - генерация задач типа <type> в количестве
            <number> штук.
            /getAnswers - выводит ответы к последним сгенерированным задачам
            
            Возможные типы задач: """);

    private ArrayList<TaskCondition> tasks = new ArrayList<>();
    private ArrayList<TaskSolution> tasksSolution = new ArrayList<>();
    private JsonStorage storage;

    public CommandHandler() throws StorageErrorException {
        try {
            storage = new JsonStorage();
        } catch (IOException | IllegalArgumentException | JsonSyntaxException e) {
            throw new StorageErrorException();
        }
    }

    @Override
    public String processCommand(int userId, String command, String arguments)
            throws UnknownCommandException, InvalidParameterException,
                    NoGeneratedTasksException, StorageErrorException {
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(command.substring(1).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException(DefaultResponse.UNKNOWN_COMMAND + ' ' + command);
        }
        String response = switch (commandType) {
            case START-> addUser(userId);
            case HELP -> getHelp();
            case TASKS -> getTasksByArguments(arguments);
            case ANSWERS -> getAnswers();
        };
        return response;
    }

    private String getHelp(){
        for (String i : generator.getNamesOfTaskTypes()) {
            help_text.append(" ").append(i).append(",");
        }

        return help_text.toString();
    };

    private String addUser(int userId) throws StorageErrorException{
        try {
            storage.addUser(userId);
        } catch (IOException e) {
            throw new StorageErrorException(DefaultResponse.USER_IDENTIFICATION_FAIL);
        }
        return DefaultResponse.USER_IDENTIFICATION_SUCСESS;
    }

    private String getTasksByArguments(String arguments) throws InvalidParameterException{
        tasks.clear();
        tasksSolution.clear();
        if (arguments == null) {
            throw new InvalidParameterException(DefaultResponse.NO_TASK_TYPE);
        }
        String[] argumentsArray = arguments.split(" ");
        String type = argumentsArray[0];
        int number;
        try {
            number = (argumentsArray.length > 1) ? Integer.parseInt(argumentsArray[1]) : 1;
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(DefaultResponse.ILLEGAL_NUMBER_OF_TASKS);
        }
        TaskType taskType;
        for (int i = 0; i < number; i++) {
            taskType = generator.getNewTaskByType(type);
            TaskCondition taskCondition = taskType.getCondition();
            TaskSolution taskSolution = taskType.getSolution();
            tasks.add(taskCondition);
            tasksSolution.add(taskSolution);
        }

        StringBuilder tmpResponse = new StringBuilder();
        for (TaskCondition task : tasks) {
            tmpResponse.append(task.getCondition())
                       .append(task.getExpression())
                       .append("\n");
        }
        return tmpResponse.toString();
    }

    private String getAnswers() throws NoGeneratedTasksException{
        if (tasksSolution.isEmpty())
            throw new NoGeneratedTasksException(DefaultResponse.NO_TASKS_GENERATED);

        StringBuilder tmpResponse = new StringBuilder();
        for (int i = 0; i < tasksSolution.size(); i++) {
            tmpResponse.append(i+1)
                       .append(") ")
                       .append(tasks.get(i).getExpression())
                       .append("\n")
                       .append("ответ: ")
                       .append(tasksSolution.get(i).getResult())
                       .append("\n")
                       .append(tasksSolution.get(i).getSolutionSteps())
                       .append("\n")
                       .append("\n");
        }
        tasks.clear();
        tasksSolution.clear();
        return tmpResponse.toString();
    }
}
