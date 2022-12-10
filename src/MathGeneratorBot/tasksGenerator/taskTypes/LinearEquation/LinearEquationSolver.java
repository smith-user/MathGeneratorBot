package MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation;

import MathGeneratorBot.tasksGenerator.ProblemSolverImpl;
import MathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

abstract public class LinearEquationSolver extends
        ProblemSolverImpl<LinearEquationTaskSolution, LinearEquationTaskCondition> {
    @Override
    abstract public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition)
            throws TaskSolutionException;
}
