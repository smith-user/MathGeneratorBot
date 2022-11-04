package tasksGenerator.taskTypes.LinearEquationGenerators;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.exceptions.TaskSolutionException;
import tasksGenerator.taskTypes.MathTaskGenerator;


abstract public class LinearEquationGenerator extends MathTaskGenerator {

    @Override
    public final TaskCondition createTaskCondition() {
        return createTaskConditionForLinearEquation();
    }

    @Override
    public final TaskSolution createTaskSolution(TaskCondition condition) throws TaskSolutionException {
        if (condition instanceof LinearEquationTaskCondition)
            return createTaskSolutionForLinearEquation((LinearEquationTaskCondition) condition);
        else
            throw new TaskSolutionException("Неверный тип условия задачи.");
    }

    abstract protected LinearEquationTaskCondition createTaskConditionForLinearEquation();
    abstract protected TaskSolution createTaskSolutionForLinearEquation(LinearEquationTaskCondition condition);

}
