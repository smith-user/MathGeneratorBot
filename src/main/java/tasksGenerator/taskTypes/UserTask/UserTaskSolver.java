package tasksGenerator.taskTypes.UserTask;

import tasksGenerator.ProblemSolver;
import tasksGenerator.ProblemSolverImpl;
import tasksGenerator.exceptions.TaskSolutionException;

abstract public class UserTaskSolver extends ProblemSolverImpl<UserTaskSolution, UserTaskCondition> {
    @Override
    abstract public UserTaskSolution createTaskSolution(UserTaskCondition condition) throws TaskSolutionException;
}
