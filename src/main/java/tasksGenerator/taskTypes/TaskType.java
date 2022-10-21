package tasksGenerator.taskTypes;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;

abstract public class TaskType {

    TaskCondition condition;
    TaskSolution solution;
    public TaskSolution getSolution() {
        return solution;
    };
    public TaskCondition getCondition() {
        return condition;
    };
    abstract protected void generate();
    abstract protected void solve();
}
