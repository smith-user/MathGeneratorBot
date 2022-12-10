package MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import MathGeneratorBot.tasksGenerator.mathClasses.Fraction;

import static MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation.LinearEquationTaskSolution.SpecialCases.*;

final public class LinearEquationManualSolver extends LinearEquationSolver {
    private static final Logger logger = LogManager.getLogger(LinearEquationManualSolver.class.getName());
    @Override
    public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition) {
        logger.traceEntry("{}", condition);
        Fraction freeCoefficient = condition.getFreeCoefficient();
        Fraction coefficientAtX = condition.getCoefficientAtX();
        String steps = "";
        freeCoefficient = freeCoefficient.multiplication(new Fraction(-1));

        if(Math.abs(Math.abs(coefficientAtX.toDouble()) - 1) < 10e-6) {
            if (coefficientAtX.toDouble() > 0)
                steps += "x = %s".formatted(freeCoefficient);
            else
                steps += "-x = %s".formatted(freeCoefficient);
        } else
            steps += "%sx = %s".formatted(coefficientAtX, freeCoefficient);

        LinearEquationTaskSolution solution;
        if (Math.abs(coefficientAtX.toDouble()) < 10e-6) {
            if (Math.abs(freeCoefficient.toDouble()) < 10e-6)
                solution = new LinearEquationTaskSolution(steps, INFINITE_NUMBER_OF_ROOTS);
            else
                solution = new LinearEquationTaskSolution(steps, NO_ROOTS);
        } else {
            Fraction result = freeCoefficient.div(coefficientAtX);
            steps += "\n%s / %s = %s".formatted(freeCoefficient, coefficientAtX, result);
            solution = new LinearEquationTaskSolution(steps, result.getString());
        }
        return logger.traceExit(solution);
    }
}
