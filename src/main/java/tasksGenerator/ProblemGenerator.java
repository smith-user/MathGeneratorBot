package tasksGenerator;

import tasksGenerator.exceptions.TaskConditionException;

/**
 * Интерфейс для создания условий различных математических задач - объектов класса {@link TaskCondition} - генераторами,
 * реализующими данный интерфейс.
 */
public interface ProblemGenerator<T extends TaskCondition> {
    /**
     *
     * @return условие математической задачи - объект класса {@link TaskCondition}.
     * @throws TaskConditionException если при генерации условия возникла ошибка.
     */
    T createTaskCondition() throws TaskConditionException;


}
