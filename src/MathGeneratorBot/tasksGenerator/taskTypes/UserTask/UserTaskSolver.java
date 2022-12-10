package MathGeneratorBot.tasksGenerator.taskTypes.UserTask;

import MathGeneratorBot.tasksGenerator.ProblemSolverImpl;
import MathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

abstract public class UserTaskSolver extends ProblemSolverImpl<UserTaskSolution, UserTaskCondition> {
    @Override
    abstract public UserTaskSolution createTaskSolution(UserTaskCondition condition) throws TaskSolutionException;
}
