package tasksGenerator;

/**
 * Класс MathTask представляет собой математическую задачи:
 * каждая задача состоит из условия {@code TaskCondition}
 * и решения с пояснением {@code TaskSolution}.
 */
final public class MathTask {

    private final TaskCondition condition;
    private final TaskSolution solution;

    public MathTask(TaskCondition condition, TaskSolution solution) {
        this.condition = condition;
        this.solution = solution;
    }

    /**
     *
     * @return решение задачи
     */
    public TaskSolution getSolution() {
        return solution;
    };

    /**
     *
     * @return условие задачи
     */
    public TaskCondition getCondition() {
        return condition;
    };
}
