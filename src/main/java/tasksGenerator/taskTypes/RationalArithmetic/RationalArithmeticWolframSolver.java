package tasksGenerator.taskTypes.RationalArithmetic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tasksGenerator.MathAPI.APIQueryException;
import tasksGenerator.MathAPI.wolframAlphaAPI.WolframAlphaAPI;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskSolutionException;

import java.util.ArrayList;
import java.util.HashMap;

public class RationalArithmeticWolframSolver extends RationalArithmeticSolver {

    private static final Logger logger = LogManager.getLogger(TasksGenerator.class.getName());

    private static final WolframAlphaAPI wolframAPI = WolframAlphaAPI.instance();

    @Override
    public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException {
        logger.traceEntry("{}", condition);
        logger.debug("input task expression=\"{}\"", condition.getExpression());

        HashMap<String, ArrayList<String>> result;
        try {
            result = wolframAPI.performQuery(condition.getExpression());
        } catch (APIQueryException e) {
            logger.catching(e);
            throw logger.throwing(new TaskSolutionException(e));
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
            throw new TaskSolutionException("Неизвестный формат ответа WolframAPI.");
        }
    }
}
