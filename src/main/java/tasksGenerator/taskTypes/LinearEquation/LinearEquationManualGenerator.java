package tasksGenerator.taskTypes.LinearEquation;

import tasksGenerator.mathClasses.Fraction;

final public class LinearEquationManualGenerator extends LinearEquationGenerator {

    @Override
    public LinearEquationTaskCondition createTaskCondition() {
        int maxNumber = 5;
        Fraction coefficientAtX = Fraction.randomSignFraction(maxNumber);
        Fraction freeCoefficient = Fraction.randomSignFraction(maxNumber);
        return new LinearEquationTaskCondition(coefficientAtX, freeCoefficient);
    }
}
