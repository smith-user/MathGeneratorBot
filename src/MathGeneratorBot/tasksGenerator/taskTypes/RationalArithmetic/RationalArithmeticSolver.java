package MathGeneratorBot.tasksGenerator.taskTypes.RationalArithmetic;

import MathGeneratorBot.tasksGenerator.ProblemSolverImpl;
import MathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

abstract public class RationalArithmeticSolver extends
        ProblemSolverImpl<RationalArithmeticTaskSolution, RationalArithmeticTaskCondition> {
    @Override
    abstract public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException;
}
