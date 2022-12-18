package mathGeneratorBot.handler.commands;

import mathGeneratorBot.handler.CommandType;
import mathGeneratorBot.handler.DefaultResponse;
import mathGeneratorBot.storage.JsonStorage;
import mathGeneratorBot.tasksGenerator.MathTask;
import mathGeneratorBot.tasksGenerator.TaskCondition;
import mathGeneratorBot.tasksGenerator.TaskSolution;
import mathGeneratorBot.tasksGenerator.TasksGenerator;
import mathGeneratorBot.handler.HandlerState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mathGeneratorBot.tasksGenerator.exceptions.TaskCreationException;

public class SolveUserTaskCommand extends Command{
    private TasksGenerator generator;
    private HandlerState state;
    private static final Logger logger = LogManager.getLogger(SolveUserTaskCommand.class.getName());
    public SolveUserTaskCommand(JsonStorage storage, TasksGenerator generator, HandlerState state) {
        super(storage);
        this.state = state;
        this.generator = generator;
    }

    @Override
    public String execute(int userId, String arguments) {
        if (state == HandlerState.COMMAND_WAITING) {
            state = state.nextState(CommandType.SOLVE);
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
                logger.catching(e);
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
