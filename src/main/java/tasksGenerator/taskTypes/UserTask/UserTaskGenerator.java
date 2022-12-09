package tasksGenerator.taskTypes.UserTask;

import tasksGenerator.UserProblemGenerator;
import tasksGenerator.exceptions.TaskConditionException;

public class UserTaskGenerator implements UserProblemGenerator<UserTaskCondition> {

    @Override
    public UserTaskCondition createTaskCondition(String input) throws TaskConditionException {
        return new UserTaskCondition(input);
    }
}
