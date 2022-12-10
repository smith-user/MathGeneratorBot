package MathGeneratorBot.tasksGenerator;

/**
 * Класс, представлющий собой условие математической задачи,
 * которое состоит из описание того, что требуется (найти, вычислить, доказать и т.п.)
 * и математического выражения.
 */
abstract public class TaskCondition {
    private final String condition;
    private final String expression;

    /**
     *
     * @param condition строка с описанием задачи
     * @param expression строка математического выражения
     */
    public TaskCondition(String condition, String expression) {
        this.condition = condition;
        this.expression = expression;
    }

    /**
     *
     * @return строку с описанием задачи
     */
    public String getCondition() {
        return condition;
    }

    /**
     *
     * @return строку математического выражения
     */
    public String getExpression() {return expression; }

    @Override
    public String toString() {
        return "%s: %s".formatted(getReducedString(condition, 10), expression);
    }

    private String getReducedString(String input, int length) {
        if (input.length() > length) {
            return "%s <...>".formatted(input.substring(0, length));
        } else
            return input;
    }
}
