package MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import MathGeneratorBot.tasksGenerator.mathClasses.Fraction;

final public class LinearEquationManualGenerator extends LinearEquationGenerator {
    private static final Logger logger = LogManager.getLogger(LinearEquationManualGenerator.class.getName());

    @Override
    public LinearEquationTaskCondition createTaskCondition() {
        logger.traceEntry();
        int maxNumber = 5;
        Fraction coefficientAtX = Fraction.randomSignFraction(maxNumber);
        Fraction freeCoefficient = Fraction.randomSignFraction(maxNumber);
        return logger.traceExit(new LinearEquationTaskCondition(coefficientAtX, freeCoefficient));
    }
}
