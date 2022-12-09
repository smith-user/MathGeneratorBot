package tasksGenerator.taskTypes.UserTask;

import tasksGenerator.MathAPI.APIQueryException;
import tasksGenerator.MathAPI.MathAPI;
import tasksGenerator.MathAPI.wolframAlphaAPI.WolframAlphaAPI;
import tasksGenerator.TaskSolution;
import tasksGenerator.exceptions.TaskSolutionException;

import java.util.ArrayList;
import java.util.HashMap;

public class UserTaskMathAPISolver extends UserTaskSolver {
    private static final MathAPI api = WolframAlphaAPI.instance();

    @Override
    public UserTaskSolution createTaskSolution(UserTaskCondition condition) throws TaskSolutionException {
        try {
            HashMap<String, ArrayList<String>> results = api.performQuery(condition.getExpression());
            return new UserTaskSolution(getStringResult(results));
        } catch (APIQueryException exc) {
            throw new TaskSolutionException(exc.getMessage());
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
