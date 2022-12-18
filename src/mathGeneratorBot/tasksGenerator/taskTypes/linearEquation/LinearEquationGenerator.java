package mathGeneratorBot.tasksGenerator.taskTypes.linearEquation;

import mathGeneratorBot.tasksGenerator.ProblemGenerator;

abstract public class LinearEquationGenerator
        implements ProblemGenerator<LinearEquationTaskCondition> {
    @Override
    abstract public LinearEquationTaskCondition createTaskCondition();

}
