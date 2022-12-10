package MathGeneratorBot.tasksGenerator.taskTypes.UserTask;

import MathGeneratorBot.tasksGenerator.TaskSolution;

final public class UserTaskSolution extends TaskSolution {
    /**
     * @param solutionSteps строка пошагового решения или пояснения решения задачи
     * @param result        строка ответа к задаче
     */
    public UserTaskSolution(String solutionSteps, String result) {
        super(solutionSteps, result);
    }

    /**
     * @param result строка ответа к задаче
     */
    public UserTaskSolution(String result) {
        super(result);
    }
}
