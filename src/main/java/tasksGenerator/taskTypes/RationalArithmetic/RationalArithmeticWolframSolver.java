package tasksGenerator.taskTypes.RationalArithmetic;

import tasksGenerator.MathAPI.APIQueryException;
import tasksGenerator.MathAPI.wolframAlphaAPI.WolframAlphaAPI;
import tasksGenerator.exceptions.TaskSolutionException;

import java.util.ArrayList;
import java.util.HashMap;

import static tasksGenerator.taskTypes.RationalArithmetic.RationalArithmeticTaskSolution.RationalArithmeticSpecialCases.*;

public class RationalArithmeticWolframSolver extends RationalArithmeticSolver {

    private static final WolframAlphaAPI wolframAPI = WolframAlphaAPI.instance();

    @Override
    public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException {
        HashMap<String, ArrayList<String>> result;
        try {
            result = wolframAPI.performQuery(condition.getExpression());
        } catch (APIQueryException e) {
            throw new TaskSolutionException(e);
        }

        if(result.containsKey("Exact result")) {
            ArrayList<String> results = result.get("Exact result");
            String value = results.get(0);
            String solutionSteps = results.get(1);
            return new RationalArithmeticTaskSolution(solutionSteps, value);
        } else if(result.containsKey("Result"))
            return new RationalArithmeticTaskSolution(result.get("Input").get(0), result.get("Result").get(0));
        else
            throw new TaskSolutionException();
    }
}
