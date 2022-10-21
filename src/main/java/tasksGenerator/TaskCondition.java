package tasksGenerator;

public class TaskCondition {
    private final String condition;
    private final String expression;

    public TaskCondition(String condition, String expression) {
        this.condition = condition;
        this.expression = expression;
    }

    public String getCondition() {
        return condition;
    }
    public String getExpression() {return expression; }
}
