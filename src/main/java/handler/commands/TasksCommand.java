package handler.commands;

import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class TasksCommand extends Command{
    protected LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks;
    protected LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution;

    public TasksCommand(JsonStorage storage,
                        LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                        LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(storage);
        this.tasks = tasks;
        this.tasksSolution = tasksSolution;
    }
}
