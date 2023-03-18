package mathGeneratorBot.tasksGenerator.taskTypes.userTask;

import mathGeneratorBot.tasksGenerator.ProblemSolverImpl;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

abstract public class UserTaskSolver extends ProblemSolverImpl<UserTaskSolution, UserTaskCondition> {
    @Override
    abstract public UserTaskSolution createTaskSolution(UserTaskCondition condition) throws TaskSolutionException;
}
