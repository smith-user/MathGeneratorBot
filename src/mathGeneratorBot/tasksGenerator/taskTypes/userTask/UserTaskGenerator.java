package mathGeneratorBot.tasksGenerator.taskTypes.userTask;

import mathGeneratorBot.tasksGenerator.UserProblemGenerator;

final public class UserTaskGenerator implements UserProblemGenerator<UserTaskCondition> {

    @Override
    public UserTaskCondition createTaskCondition(String input) {
        return new UserTaskCondition(input);
    }
}
