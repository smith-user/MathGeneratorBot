package tasksGenerator.taskTypes.RationalArithmetic;

import tasksGenerator.TaskSolution;

final public class RationalArithmeticTaskSolution extends TaskSolution {

    public static enum RationalArithmeticSpecialCases {
        DIVISION_BY_ZERO("Деление на ноль.");

        private final String content;
        private RationalArithmeticSpecialCases(String content) {
            this.content = content;
        }
    }

    /**
     * @param solutionSteps строка пошагового решения или пояснения решения задачи
     * @param result        строка ответа к задаче (число)
     */
    public RationalArithmeticTaskSolution(String solutionSteps, String result) {
        super(solutionSteps, result);
    }

    public RationalArithmeticTaskSolution(String solutionsSteps,
                                          RationalArithmeticSpecialCases specialCase,
                                          String fstOperand,
                                          String sndOperand) {
        super(solutionsSteps, "%s %s / %s".formatted(specialCase.content, fstOperand, sndOperand));
    }

    public RationalArithmeticTaskSolution(String solutionsSteps, RationalArithmeticSpecialCases specialCase) {
        super(solutionsSteps, specialCase.content);
    }
}
