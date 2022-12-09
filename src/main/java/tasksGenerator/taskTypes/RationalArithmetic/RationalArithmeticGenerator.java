package tasksGenerator.taskTypes.RationalArithmetic;

import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.ProblemGenerator;

abstract public class RationalArithmeticGenerator
        implements ProblemGenerator<RationalArithmeticTaskCondition> {

    @Override
    abstract public RationalArithmeticTaskCondition createTaskCondition()
            throws TaskConditionException;

}


