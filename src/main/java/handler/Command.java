package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.StorageErrorException;
import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskCreationException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

abstract public class Command{
    protected TasksGenerator generator;
    protected JsonStorage storage;

    protected LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks;
    protected LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution;

    public Command(TasksGenerator generator, JsonStorage storage,
                   LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                   LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        this.generator = generator;
        this.storage = storage;
        this.tasks = tasks;
        this.tasksSolution = tasksSolution;
    }

    abstract public String execute(int userId, String arguments);
}
