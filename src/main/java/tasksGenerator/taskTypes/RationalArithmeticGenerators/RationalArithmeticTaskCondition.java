package tasksGenerator.taskTypes.RationalArithmeticGenerators;

import tasksGenerator.TaskCondition;

/**
 * Данный класс расширяет и определяет {@code TaskCondition} для задач с рациональной арифметикой.
 */
public class RationalArithmeticTaskCondition extends TaskCondition {
    public RationalArithmeticTaskCondition(String expression) {
        super("Вычислить значение выражения: ", expression);
    }
}
