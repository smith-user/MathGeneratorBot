package mathGeneratorBot.tasksGenerator.taskTypes.rationalArithmetic;

import mathGeneratorBot.tasksGenerator.ProblemSolverImpl;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

abstract public class RationalArithmeticSolver extends
        ProblemSolverImpl<RationalArithmeticTaskSolution, RationalArithmeticTaskCondition> {
    @Override
    abstract public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException;
}
