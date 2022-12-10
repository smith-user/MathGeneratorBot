package tasksGenerator;

import tasksGenerator.exceptions.TaskSolutionException;

abstract public class ProblemSolverImpl<T extends TaskSolution,U extends TaskCondition>
        implements ProblemSolver<T, U>{
    @Override
    final public T createTaskSolutionForAbstractCondition(TaskCondition condition) throws TaskSolutionException {
        try {
            return createTaskSolution( (U) condition);
        } catch (TaskSolutionException e) {
            throw e;
        } catch (Exception e) {
            throw new TaskSolutionException();
        }
    }
}
