package MathGeneratorBot.handler.commands;

import MathGeneratorBot.handler.HandlerState;
import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.tasksGenerator.TaskCondition;
import MathGeneratorBot.tasksGenerator.TaskSolution;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Абстрактный класс для комманд бота, работающих с математическими задачами.
 */
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
    @Override
    public HandlerState getState() {
        return state;
    }
}
