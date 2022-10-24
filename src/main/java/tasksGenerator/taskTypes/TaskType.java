package tasksGenerator.taskTypes;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;

abstract public class TaskType {

    protected TaskCondition condition;
    protected TaskSolution solution;
    public TaskSolution getSolution() {
        return solution;
    };
    public TaskCondition getCondition() {
        return condition;
    };
    abstract protected TaskCondition generate();
    abstract protected TaskSolution solve();
}
