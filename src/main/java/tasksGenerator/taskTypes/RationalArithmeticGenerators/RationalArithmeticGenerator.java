package tasksGenerator.taskTypes.RationalArithmeticGenerators;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.exceptions.TaskSolutionException;
import tasksGenerator.taskTypes.MathTaskGenerator;

abstract public class RationalArithmeticGenerator extends MathTaskGenerator {

    @Override
    public final TaskCondition createTaskCondition() throws TaskConditionException {
        return createTaskConditionForRationalArithmetic();
    }

    @Override
    public final TaskSolution createTaskSolution(TaskCondition condition) throws TaskSolutionException {
        if (condition instanceof RationalArithmeticTaskCondition)
            return createTaskSolutionForRationalArithmetic((RationalArithmeticTaskCondition) condition);
        else
            throw new TaskSolutionException("Неверный тип условия задачи.");
    }

    abstract protected RationalArithmeticTaskCondition createTaskConditionForRationalArithmetic()
            throws TaskConditionException;
    abstract protected TaskSolution createTaskSolutionForRationalArithmetic(RationalArithmeticTaskCondition condition)
            throws TaskSolutionException;

}


