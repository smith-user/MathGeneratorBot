package tasksGenerator.taskTypes;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;

/**
 * Абстрактный класс TaskType представляет собой класс тип математической задачи:
 * каждая задача состоит из условия и решения с пояснением.
 */
abstract public class TaskType {

    protected TaskCondition condition;
    protected TaskSolution solution;

    /**
     *
     * @return решение задачи
     */
    public TaskSolution getSolution() {
        return solution;
    };

    /**
     *
     * @return условия задачи
     */
    public TaskCondition getCondition() {
        return condition;
    };

    /**
     * Генерирует условие задачи, прдеставленное как объект класса {@code TaskCondition}
     * @return условие задачи
     */
    abstract protected TaskCondition generate();

    /**
     * Генерирует решение задачи, прдеставленное как объект класса {@code TaskSolution}
     * @return решение задачи
     */
    abstract protected TaskSolution solve();
}
