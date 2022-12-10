package handler.commands;

import handler.CommandType;
import handler.DefaultResponse;
import handler.HandlerState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.JsonStorage;
import tasksGenerator.MathTask;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskCreationException;

public class SolveUserTaskCommand extends Command{
    private TasksGenerator generator;
    private HandlerState state;
    private static final Logger logger = LogManager.getLogger(SolveUserTaskCommand.class.getName());
    public SolveUserTaskCommand(JsonStorage storage, TasksGenerator generator, HandlerState state) {
        super(storage);
        this.generator = generator;
        this.state = state;
    }

    @Override
    public String execute(int userId, String arguments) {
        logger.traceEntry("arguments={}, userId={}", arguments, userId);
        if (state == HandlerState.COMMAND_WAITING) {
            state = state.nextState(CommandType.SOLVE);
            return logger.traceExit(DefaultResponse.GET_USER_TASK);
        }

        else if (state == HandlerState.USERS_TASK_WAITING) {
            state = state.nextState(CommandType.SOLVE);
            try {
                MathTask task = generator.createTask(arguments);
                TaskSolution taskSolution = task.getSolution();
                TaskCondition taskCondition = task.getCondition();
                String response = "`%s`".formatted(taskSolution.getResult());
                return logger.traceExit(response);
            } catch (TaskCreationException e) {
                logger.catching(e);
                return logger.traceExit(DefaultResponse.TASK_SOLVE_FAIL);
            }
        }
        logger.traceExit("null");
        return null;
    }

    @Override
    public HandlerState getState() {
        return state;
    }
}
