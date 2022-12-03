package handler.commands;

import handler.HandlerState;
import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class TasksCommand extends Command{
    protected LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks;
    protected LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution;

    protected HandlerState state;

    public TasksCommand(JsonStorage storage, HandlerState state,
                        LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                        LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(storage);
        this.tasks = tasks;
        this.tasksSolution = tasksSolution;
        this.state = state;
    }

    public HandlerState getState() {
        return state;
    }
}
