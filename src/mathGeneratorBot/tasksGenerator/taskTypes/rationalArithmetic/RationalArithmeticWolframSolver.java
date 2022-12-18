package mathGeneratorBot.tasksGenerator.taskTypes.rationalArithmetic;

import mathGeneratorBot.tasksGenerator.mathAPI.APIQueryException;
import mathGeneratorBot.tasksGenerator.mathAPI.wolframAlphaAPI.WolframAlphaAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

import java.util.ArrayList;
import java.util.HashMap;

public class RationalArithmeticWolframSolver extends RationalArithmeticSolver {

    private static final Logger logger = LogManager.getLogger(RationalArithmeticWolframSolver.class.getName());

    private static final WolframAlphaAPI wolframAPI = WolframAlphaAPI.instance();

    @Override
    public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException {
        logger.traceEntry("{}", condition);

        HashMap<String, ArrayList<String>> result;
        try {
            result = wolframAPI.performQuery(condition.getExpression());
        } catch (APIQueryException e) {
            logger.catching(e);
            throw logger.throwing(new TaskSolutionException(e));
        }

        String keyOfResult = getKeyOfResult(result);
        RationalArithmeticTaskSolution solution;
        if(keyOfResult != null) {
            ArrayList<String> results = result.get(keyOfResult);
            if (results.size() > 1)
                solution = new RationalArithmeticTaskSolution(results.get(1), results.get(0));
            else
                solution = new RationalArithmeticTaskSolution(result.get("Input").get(0), results.get(0));
        } else {
            throw logger.throwing(new TaskSolutionException("Неизвестный формат ответа WolframAPI."));
        }
        return logger.traceExit(solution);
    }

    private String getKeyOfResult(HashMap<String, ArrayList<String>> result) {
        if (result.containsKey("Exact result")) {
            return  "Exact result";
        } else if (result.containsKey("Results")) {
            return "Results";
        } else if(result.containsKey("Result")) {
            return "Result";
        } else
            return null;
    }
}
