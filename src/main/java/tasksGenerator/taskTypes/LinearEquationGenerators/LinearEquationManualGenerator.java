package tasksGenerator.taskTypes.LinearEquationGenerators;

import tasksGenerator.TaskSolution;
import tasksGenerator.mathClasses.Fraction;


public class LinearEquationManualGenerator extends LinearEquationGenerator {

    @Override
    protected LinearEquationTaskCondition createTaskConditionForLinearEquation() {
        int maxNumber = 5;
        Fraction coefficientAtX = Fraction.randomSignFraction(maxNumber);
        Fraction freeCoefficient = Fraction.randomSignFraction(maxNumber);
        return new LinearEquationTaskCondition(coefficientAtX, freeCoefficient);
    }

    @Override
    protected TaskSolution createTaskSolutionForLinearEquation(LinearEquationTaskCondition condition) {
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
                return new TaskSolution(steps, "x - любое число");
            else
                return new TaskSolution(steps, "Корней нет");
        }
        Fraction result = freeCoefficient.div(coefficientAtX);
        steps += "\n%s / %s = %s".formatted(freeCoefficient, coefficientAtX, result);
        return new TaskSolution(steps, "x = %s".formatted(result.getString()));
    }
}
