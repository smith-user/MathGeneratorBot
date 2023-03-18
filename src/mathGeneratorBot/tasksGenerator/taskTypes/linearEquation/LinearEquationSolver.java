package mathGeneratorBot.tasksGenerator.taskTypes.linearEquation;

import mathGeneratorBot.tasksGenerator.ProblemSolverImpl;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

abstract public class LinearEquationSolver extends
        ProblemSolverImpl<LinearEquationTaskSolution, LinearEquationTaskCondition> {
    @Override
    abstract public LinearEquationTaskSolution createTaskSolution(LinearEquationTaskCondition condition)
            throws TaskSolutionException;
}
