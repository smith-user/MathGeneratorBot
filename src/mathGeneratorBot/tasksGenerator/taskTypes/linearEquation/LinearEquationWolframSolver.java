package mathGeneratorBot.tasksGenerator.taskTypes.linearEquation;

import mathGeneratorBot.tasksGenerator.mathAPI.APIQueryException;
import mathGeneratorBot.tasksGenerator.mathAPI.wolframAlphaAPI.WolframAlphaAPI;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

import java.util.ArrayList;
import java.util.HashMap;

public class LinearEquationWolframSolver extends LinearEquationSolver {

    private static final Logger logger = LogManager.getLogger(LinearEquationWolframSolver.class.getName());
    private static final WolframAlphaAPI wolframAPI = WolframAlphaAPI.instance();

    @Override
    public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition)
            throws TaskSolutionException {
        logger.traceEntry("{}", condition);
        HashMap<String, ArrayList<String>> result;
        try {
            result = wolframAPI.performQuery(condition.getExpression());
        } catch (APIQueryException e) {
            throw logger.throwing(Level.WARN, new TaskSolutionException(e));
        }

        LinearEquationTaskSolution solution;
        if(result.containsKey("Results")) {
            ArrayList<String> results = result.get("Results");
            if(results.get(0).equals("(no solutions exist)"))
                solution = new LinearEquationTaskSolution(LinearEquationTaskSolution.SpecialCases.NO_ROOTS);
            else {
                String root = results.get(0).replace("x = ", "");
                String solutionSteps = results.get(1);
                solution = new LinearEquationTaskSolution(solutionSteps, root);
            }
        } else if(result.containsKey("Solution over the reals"))
            solution = new LinearEquationTaskSolution(LinearEquationTaskSolution.SpecialCases.INFINITE_NUMBER_OF_ROOTS);
        else
            throw logger.throwing(Level.WARN, new TaskSolutionException("Неизвестный формат ответа WolframAPI."));
        return logger.traceExit(solution);
    }
}
