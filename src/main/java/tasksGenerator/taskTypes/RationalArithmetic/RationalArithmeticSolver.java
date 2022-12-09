package tasksGenerator.taskTypes.RationalArithmetic;

import tasksGenerator.ProblemSolverImpl;
import tasksGenerator.exceptions.TaskSolutionException;

abstract public class RationalArithmeticSolver extends
        ProblemSolverImpl<RationalArithmeticTaskSolution, RationalArithmeticTaskCondition> {
    @Override
    abstract public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException;
}
