package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.StorageErrorException;
import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskCreationException;

import java.util.ArrayList;

abstract public class Command{
    protected TasksGenerator generator;
    protected JsonStorage storage;

    protected ArrayList<TaskCondition> tasks;
    protected ArrayList<TaskSolution> tasksSolution;

    public Command(TasksGenerator generator, JsonStorage storage,
                   ArrayList<TaskCondition> tasks, ArrayList<TaskSolution> tasksSolution) {
        this.generator = generator;
        this.storage = storage;
        this.tasks = tasks;
        this.tasksSolution = tasksSolution;
    }

    abstract public String execute(int userId, String arguments);
}
