package MathGeneratorBot.tasksGenerator.taskTypes.RationalArithmetic;

import MathGeneratorBot.tasksGenerator.ProblemGenerator;
import MathGeneratorBot.tasksGenerator.exceptions.TaskConditionException;

abstract public class RationalArithmeticGenerator
        implements ProblemGenerator<RationalArithmeticTaskCondition> {

    @Override
    abstract public RationalArithmeticTaskCondition createTaskCondition()
            throws TaskConditionException;

}


