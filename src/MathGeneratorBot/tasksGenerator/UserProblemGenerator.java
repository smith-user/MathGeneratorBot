package MathGeneratorBot.tasksGenerator;

import MathGeneratorBot.tasksGenerator.exceptions.TaskConditionException;

public interface UserProblemGenerator<T extends TaskCondition> {
    T createTaskCondition(String input) throws TaskConditionException;
}
