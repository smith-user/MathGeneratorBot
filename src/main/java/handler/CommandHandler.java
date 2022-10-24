package handler;

import com.google.gson.JsonSyntaxException;
import handler.CommandHandlerException.NoGeneratedTasksException;
//import handler.CommandHandlerException.StorageErrorException;
import handler.CommandHandlerException.UnknownCommandException;
//import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.taskTypes.TaskType;
import tasksGenerator.taskTypes.TypesEnum;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandHandler implements Command {
    private static final TasksGenerator generator = new TasksGenerator();

    private static StringBuilder help_text = new StringBuilder("""
            Бот может генерировать математические задачи по заданной теме.
            /getTasks <type> <number> - генерация задач типа <type> в количестве
            <number> штук.
            /getAnswers - выводит ответы к последним сгенерированным задачам
            
            Возможные типы задач: """);

    private ArrayList<TaskCondition> tasks = new ArrayList<>();
    private ArrayList<TaskSolution> tasksSolution = new ArrayList<>();
    //private JsonStorage storage;
    /*
    public CommandHandler() throws StorageErrorException {
        try {
            storage = new JsonStorage();
        } catch (IOException | IllegalArgumentException | JsonSyntaxException e) {
            throw new StorageErrorException();
        }
    }

     */

    @Override
    public String processCommand(int userId, String command, String arguments)
            throws UnknownCommandException, InvalidParameterException,
                    NoGeneratedTasksException {
        String response = switch (command) {
            case "/start" -> addUser(userId);
            case "/help" -> getHelp();
            case "/getTasks" -> getTasksByArguments(arguments);
            case "/getAnswers" -> getAnswers();
            default -> throw new UnknownCommandException();
        };
        return response;
    }

    private String getHelp(){
        for (String i : generator.getNamesOfTaskTypes()) {
            help_text.append(" ").append(i.toString()).append(",");
        }

        return help_text.toString();
    };

    private String addUser(int userId) {
        return null;
    }

    private String getTasksByArguments(String arguments) throws InvalidParameterException{
        if (arguments == null) {
            throw new InvalidParameterException("Введите категорию");
        }
        String[] argumentsArray = arguments.split(" ");
        String type = argumentsArray[0];
        int number;
        try {
            number = (argumentsArray.length > 1) ? Integer.parseInt(argumentsArray[1]) : 1;
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Некорректное число задач");
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
            throw new NoGeneratedTasksException();

        StringBuilder tmpResponse = new StringBuilder();
        for (TaskSolution taskSolution: tasksSolution) {
            tmpResponse.append(taskSolution.getResult())
                       .append("\n")
                       .append(taskSolution.getSolutionSteps())
                       .append("\n");
        }
        return tmpResponse.toString();
    }
}
