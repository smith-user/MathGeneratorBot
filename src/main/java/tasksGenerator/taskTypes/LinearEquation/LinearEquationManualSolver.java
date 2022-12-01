package tasksGenerator.taskTypes.LinearEquation;

import tasksGenerator.mathClasses.Fraction;

import static tasksGenerator.taskTypes.LinearEquation.LinearEquationTaskSolution.LinearEquationSolutionSpecialCases.*;

final public class LinearEquationManualSolver extends LinearEquationSolver{
    @Override
    public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition) {
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

        if (Math.abs(coefficientAtX.toDouble()) < 10e-6) {
            if (Math.abs(freeCoefficient.toDouble()) < 10e-6)
                return new LinearEquationTaskSolution(steps, INFINITE_NUMBER_OF_ROOTS);
            else
                return new LinearEquationTaskSolution(steps, NO_ROOTS);
        }
        Fraction result = freeCoefficient.div(coefficientAtX);
        steps += "\n%s / %s = %s".formatted(freeCoefficient, coefficientAtX, result);
        return new LinearEquationTaskSolution(steps, result.getString());
    }
}
