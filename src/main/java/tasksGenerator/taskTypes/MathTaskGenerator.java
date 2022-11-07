package tasksGenerator.taskTypes;

import tasksGenerator.MathTask;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.exceptions.TaskCreationException;
import tasksGenerator.exceptions.TaskSolutionException;

/**
 * Абстрактный класс предоставляет интерфейс для создания различных задач генераторами,
 * являющимися наследниками данного класса.
 */
abstract public class MathTaskGenerator {

    /**
     * Генерирует и возвращает математическую задачу {@link MathTask}.
     * При работе с данным классом предпочтительно использование данного метода.
     * @return сгенерированную математическую задачу.
     * @throws TaskCreationException если не удалось создать корректную задачу.
     */
    public MathTask createMathTask() throws TaskCreationException {
        TaskCondition condition;
        TaskSolution solution;
        try {
            condition = createTaskCondition();
            solution = createTaskSolution(condition);
        } catch (TaskConditionException | TaskSolutionException exc) {
            throw new TaskCreationException(
                    "Не удалось создать математическую задачу: ошибка при генерации уловия или/и решения."
            );
        }
        return new MathTask(condition, solution);
    }

    /**
     * Генерирует и возвращает условие математической задачи.
     * Предпочтительно использование метода {@link #createMathTask()},
     * так как при вызове данной функции сгенерированное уловие задачи не проверяется на возможность решения.
     * @return условие математической задачи.
     * @throws TaskConditionException если при генерации условия возникла ошибка.
     */
    abstract public TaskCondition createTaskCondition() throws TaskConditionException;

    /**
     * Генерирует и возвращает решение математической задачи.
     * Предпочтительно использование метода {@link #createMathTask()}.
     * @param taskCondition условие задачи. Использовать условие, сгенерированное этим же классом,
     *                      иначе возникнет исключение.
     * @return решение математической задачи.
     * @throws TaskSolutionException если при генерации решения для данного условия возникла ошибка.
     */
    abstract public TaskSolution createTaskSolution(TaskCondition taskCondition) throws TaskSolutionException;
}
