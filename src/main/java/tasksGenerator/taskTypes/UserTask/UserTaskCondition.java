package tasksGenerator.taskTypes.UserTask;

import tasksGenerator.TaskCondition;

public class UserTaskCondition extends TaskCondition {
    /**
     * @param condition  строка с описанием задачи
     * @param expression строка математического выражения
     */
    public UserTaskCondition(String condition, String expression) {
        super(condition, expression);
    }

    public UserTaskCondition(String expression) {
        this("Задача.", expression);
    }
}
