package MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation;

import MathGeneratorBot.tasksGenerator.ProblemGenerator;

abstract public class LinearEquationGenerator
        implements ProblemGenerator<LinearEquationTaskCondition> {
    @Override
    abstract public LinearEquationTaskCondition createTaskCondition();

}
