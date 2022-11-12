package handler;

import com.google.gson.JsonSyntaxException;

import handler.commands.AnswersCommand;
import handler.commands.GenerateTasksCommand;
import handler.commands.HelpCommand;
import handler.commands.StartCommand;
import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;

public class QueryHandler {
    private static final TasksGenerator generator = TasksGenerator.Instance();
    private JsonStorage storage;
    private ArrayList<TaskCondition> tasks = new ArrayList<>();
    private ArrayList<TaskSolution> tasksSolution = new ArrayList<>();
    public QueryHandler() {
        try {
            storage = new JsonStorage();
        } catch (IOException | InvalidPathException | JsonSyntaxException e) {
            System.exit(1);
        }
    }

    public String getResponse(String userQuery, int userId) {

        String[] userQueryArray = userQuery.split(" ", 2);
        String userCommand = userQueryArray[0];
        String arguments = (userQueryArray.length > 1) ? userQueryArray[1] : null;
        String response;
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(userCommand.substring(1).toUpperCase());
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            response = DefaultResponse.UNKNOWN_COMMAND;
            return response;
        }
        Command command;
        switch (commandType) {
            case HELP:
                command = new HelpCommand(generator);
                break;
            case START:
                command = new StartCommand(storage);
                break;
            case TASKS:
                command = new GenerateTasksCommand(generator, tasks, tasksSolution);
                break;
            case ANSWERS:
                command = new AnswersCommand(tasks, tasksSolution);
                break;
            default:
                return null;
        }
        response = command.execute(userId, arguments);
        return response;
    }
}
