package mathGeneratorBot.tasksGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;

abstract public class ProblemSolverImpl<T extends TaskSolution,U extends TaskCondition>
        implements ProblemSolver<T, U> {
    private static final Logger logger = LogManager.getLogger(TasksGenerator.class.getName());
    @Override
    final public T createTaskSolutionForAbstractCondition(TaskCondition condition) throws TaskSolutionException {
        logger.traceEntry("{}", condition);
        try {
            return logger.traceExit(createTaskSolution( (U) condition));
        } catch (TaskSolutionException e) {
            throw logger.throwing(e);
        } catch (Exception e) {
            logger.catching(e);
            throw logger.throwing(new TaskSolutionException());
        }
    }
}
