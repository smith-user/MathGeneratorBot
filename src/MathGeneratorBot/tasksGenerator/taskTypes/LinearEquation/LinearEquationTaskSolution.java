package MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation;

import MathGeneratorBot.tasksGenerator.TaskSolution;

final public class LinearEquationTaskSolution extends TaskSolution {

    public static enum SpecialCases {
        NO_ROOTS("Корней нет"),
        INFINITE_NUMBER_OF_ROOTS("x - любое число");

        private final String content;
        private SpecialCases(String content) {
            this.content = content;
        }
    }

    /**
     * @param solutionSteps строка пошагового решения или пояснения решения задачи
     * @param result        строка ответа к задаче (число)
     */
    public LinearEquationTaskSolution(String solutionSteps, String result) {
        super(solutionSteps, result);
    }

    public LinearEquationTaskSolution(String solutionsSteps, SpecialCases solution) {
        super(solutionsSteps, solution.content);
    }

    public LinearEquationTaskSolution(SpecialCases solution) {
        super(solution.content);
    }

    public LinearEquationTaskSolution(String result) {
        super(result);
    }
}
