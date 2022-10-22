package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.UnknownCommandException;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.taskTypes.TaskType;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class CommandHandler implements Command {

    private static final String HELP_TEXT = "help here";
    private final TasksGenerator generator = new TasksGenerator();
    private ArrayList<TaskCondition> tasks = new ArrayList<>();
    private ArrayList<TaskSolution> tasksSolution = new ArrayList<>();

    @Override
    public String processCommand(String command, String arguments)
            throws UnknownCommandException, InvalidParameterException, NoGeneratedTasksException{
        String response = null;
        switch (command) {
            case "/start":
                break;
            case "/help":
                response = getHelp();
                break;
            case "/getTasks":
                response = getTasksByArguments(arguments);
                break;
            case "/getAnswers":
                response = getAnswers();
                break;
            default:
                throw new UnknownCommandException();
        }
        return response;
    }

    private String getHelp(){
        return HELP_TEXT;
    };

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
                       .append(task.getExpression());
        }
        return tmpResponse.toString();
    }

    private String getAnswers() throws NoGeneratedTasksException{
        if (tasksSolution.isEmpty())
            throw new NoGeneratedTasksException();

        StringBuilder tmpResponse = new StringBuilder();
        for (TaskSolution taskSolution: tasksSolution) {
            tmpResponse.append(taskSolution.getResult())
                       .append(taskSolution.getSolutionSteps());
        }
        return tmpResponse.toString();
    }
}
