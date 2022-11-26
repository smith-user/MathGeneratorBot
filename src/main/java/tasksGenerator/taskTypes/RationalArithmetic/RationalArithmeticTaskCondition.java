package tasksGenerator.taskTypes.RationalArithmetic;

import tasksGenerator.TaskCondition;

import java.util.Map;

/**
 * Данный класс расширяет и определяет {@code TaskCondition} для задач с рациональной арифметикой.
 */
final public class RationalArithmeticTaskCondition extends TaskCondition {
    private static final Map<Character, Integer> operationsWithPriorities = Map.of(
            '+', 0,
            '-', 0,
            '*', 1,
            '/', 1,
            '^', 2
    );
    public RationalArithmeticTaskCondition(String expression) {
        super("Вычислить значение выражения: ", expression);
    }

    public static Map<Character, Integer> getOperationsWithPriorities() {
        return operationsWithPriorities;
    }
}
