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

        String keyOfResult = "";
        if (result.containsKey("Exact result")) {
            keyOfResult = "Exact result";
        } else if (result.containsKey("Results")) {
            keyOfResult = "Results";
        }

        if(!keyOfResult.equals("")) {
            ArrayList<String> results = result.get(keyOfResult);
            String value = results.get(0);
            if (results.size() > 1)
                return new RationalArithmeticTaskSolution(results.get(1), value);
            else
                return new RationalArithmeticTaskSolution(result.get("Input").get(0), value);
        } else if(result.containsKey("Result"))
            return new RationalArithmeticTaskSolution(result.get("Input").get(0), result.get("Result").get(0));
        else {
            System.out.println("RationalArithmeticWolframSolver");
            throw new TaskSolutionException();
        }

    }
}
