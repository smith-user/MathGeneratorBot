package handler.commands;

import handler.CommandType;
import handler.DefaultResponse;
import handler.HandlerState;
import storage.JsonStorage;
import tasksGenerator.MathTask;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskCreationException;

public class SolveUserTaskCommand extends Command{
    private TasksGenerator generator;
    private HandlerState state;
    public SolveUserTaskCommand(JsonStorage storage, TasksGenerator generator, HandlerState state) {
        super(storage);
        this.generator = generator;
        this.state = state;
    }

    @Override
    public String execute(int userId, String arguments) {

        if (state == HandlerState.COMMAND_WAITING) {
            System.out.println(state);
            state = state.nextState(CommandType.SOLVE);
            System.out.println(state);
            return DefaultResponse.GET_USER_TASK;
        }

        else if (state == HandlerState.USERS_TASK_WAITING) {
            state = state.nextState(CommandType.SOLVE);
            try {
                MathTask task = generator.createTask(arguments);
                TaskSolution taskSolution = task.getSolution();
                TaskCondition taskCondition = task.getCondition();
                String response = "`%s`".formatted(taskSolution.getResult());
                return response;
            } catch (TaskCreationException e) {
                return DefaultResponse.TASK_SOLVE_FAIL;
            }
        }
        return null;
    }

    @Override
    public HandlerState getState() {
        return state;
    }
}
