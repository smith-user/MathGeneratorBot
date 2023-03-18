package mathGeneratorBot.tasksGenerator;

import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

public interface ProblemSolver<T extends TaskSolution,U extends TaskCondition> {
    T createTaskSolution(U condition) throws TaskSolutionException;
    T createTaskSolutionForAbstractCondition(TaskCondition condition) throws TaskSolutionException;
}
