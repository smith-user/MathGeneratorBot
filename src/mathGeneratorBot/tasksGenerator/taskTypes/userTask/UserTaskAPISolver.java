package mathGeneratorBot.tasksGenerator.taskTypes.userTask;

import mathGeneratorBot.tasksGenerator.mathAPI.APIQueryException;
import mathGeneratorBot.tasksGenerator.mathAPI.MathAPI;
import mathGeneratorBot.tasksGenerator.mathAPI.wolframAlphaAPI.WolframAlphaAPI;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

final public class UserTaskAPISolver extends UserTaskSolver {

    private static final Logger logger = LogManager.getLogger(UserTaskAPISolver.class.getName());
    private static final MathAPI api = WolframAlphaAPI.instance();

    @Override
    public UserTaskSolution createTaskSolution(UserTaskCondition condition) throws TaskSolutionException {
        logger.traceEntry("{}", condition);
        try {
            HashMap<String, ArrayList<String>> results = api.performQuery(condition.getExpression());
            return logger.traceExit(new UserTaskSolution(getStringResult(results)));
        } catch (APIQueryException exc) {
            throw logger.throwing(new TaskSolutionException(exc.getMessage()));
        }
    }

    private String getStringResult(HashMap<String, ArrayList<String>> results) {
        StringBuilder result = new StringBuilder();
        for(var key : results.keySet()) {
            result.append("%s: \n".formatted(key));
            result.append(String.join("\n", results.get(key)));
            result.append("\n");
        }
        return result.toString();
    }
}
