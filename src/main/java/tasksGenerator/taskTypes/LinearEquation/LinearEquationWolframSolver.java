package tasksGenerator.taskTypes.LinearEquation;

import tasksGenerator.MathAPI.APIQueryException;
import tasksGenerator.MathAPI.wolframAlphaAPI.WolframAlphaAPI;
import tasksGenerator.exceptions.TaskSolutionException;

import static tasksGenerator.taskTypes.LinearEquation.LinearEquationTaskSolution.LinearEquationSolutionSpecialCases.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LinearEquationWolframSolver extends LinearEquationSolver {

    private static final WolframAlphaAPI wolframAPI = WolframAlphaAPI.instance();

    @Override
    public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition)
            throws TaskSolutionException {
        HashMap<String, ArrayList<String>> result;
        try {
            result = wolframAPI.performQuery(condition.getExpression());
        } catch (APIQueryException e) {
            throw new TaskSolutionException(e);
        }

        if(result.containsKey("Results")) {
            ArrayList<String> results = result.get("Results");
            if(results.get(0).equals("(no solutions exist)"))
                return new LinearEquationTaskSolution(NO_ROOTS);
            String root = results.get(0).replace("x = ", "");
            String solutionSteps = results.get(1);
            return new LinearEquationTaskSolution(solutionSteps, root);
        } else if(result.containsKey("Solution over the reals"))
            return new LinearEquationTaskSolution(INFINITE_NUMBER_OF_ROOTS);
        else
            throw new TaskSolutionException();
    }
}
