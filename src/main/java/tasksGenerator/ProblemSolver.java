package tasksGenerator;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.exceptions.TaskSolutionException;

public interface ProblemSolver<T extends TaskSolution,U extends TaskCondition> {
    T createTaskSolution(U condition) throws TaskSolutionException;
}
