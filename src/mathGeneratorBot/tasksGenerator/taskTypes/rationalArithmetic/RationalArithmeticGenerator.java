package mathGeneratorBot.tasksGenerator.taskTypes.rationalArithmetic;

import mathGeneratorBot.tasksGenerator.ProblemGenerator;
import mathGeneratorBot.tasksGenerator.exceptions.TaskConditionException;

abstract public class RationalArithmeticGenerator
        implements ProblemGenerator<RationalArithmeticTaskCondition> {

    @Override
    abstract public RationalArithmeticTaskCondition createTaskCondition()
            throws TaskConditionException;

}


