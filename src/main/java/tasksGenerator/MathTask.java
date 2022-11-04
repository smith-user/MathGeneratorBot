package tasksGenerator;

/**
 * Класс MathTask представляет собой математическую задачи:
 * каждая задача состоит из условия {@code TaskCondition}
 * и решения с пояснением {@code TaskSolution}.
 */
public class MathTask {

    protected TaskCondition condition;
    protected TaskSolution solution;

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
