package tasksGenerator.taskTypes.RationalArithmetic;

import tasksGenerator.exceptions.TaskSolutionException;
import tasksGenerator.ProblemSolver;

abstract public class RationalArithmeticSolver implements
        ProblemSolver<RationalArithmeticTaskSolution, RationalArithmeticTaskCondition> {
    @Override
    abstract public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException;
}
