package MathGeneratorBot.tasksGenerator.taskTypes.UserTask;

import MathGeneratorBot.tasksGenerator.UserProblemGenerator;
import MathGeneratorBot.tasksGenerator.exceptions.TaskConditionException;

final public class UserTaskGenerator implements UserProblemGenerator<UserTaskCondition> {

    @Override
    public UserTaskCondition createTaskCondition(String input) throws TaskConditionException {
        return new UserTaskCondition(input);
    }
}
