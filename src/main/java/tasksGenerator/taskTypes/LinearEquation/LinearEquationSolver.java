package tasksGenerator.taskTypes.LinearEquation;

import tasksGenerator.ProblemSolver;
import tasksGenerator.exceptions.TaskSolutionException;

abstract public class LinearEquationSolver implements
        ProblemSolver<LinearEquationTaskSolution, LinearEquationTaskCondition> {
    @Override
    abstract public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition)
            throws TaskSolutionException;
}
