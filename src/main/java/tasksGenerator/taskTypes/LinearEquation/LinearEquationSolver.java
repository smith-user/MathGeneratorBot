package tasksGenerator.taskTypes.LinearEquation;

import tasksGenerator.ProblemSolver;
import tasksGenerator.ProblemSolverImpl;
import tasksGenerator.exceptions.TaskSolutionException;

abstract public class LinearEquationSolver extends
        ProblemSolverImpl<LinearEquationTaskSolution, LinearEquationTaskCondition> {
    @Override
    abstract public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition)
            throws TaskSolutionException;
}
