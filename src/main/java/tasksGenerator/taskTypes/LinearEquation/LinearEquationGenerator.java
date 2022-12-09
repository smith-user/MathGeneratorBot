package tasksGenerator.taskTypes.LinearEquation;

import tasksGenerator.ProblemGenerator;

abstract public class LinearEquationGenerator
        implements ProblemGenerator<LinearEquationTaskCondition>{
    @Override
    abstract public LinearEquationTaskCondition createTaskCondition();

}
