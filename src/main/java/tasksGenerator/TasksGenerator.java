package tasksGenerator;

import tasksGenerator.taskTypes.RationalArithmeticTask;
import tasksGenerator.taskTypes.LinearEquationTask;
import tasksGenerator.taskTypes.TaskType;
import tasksGenerator.taskTypes.Types;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Класс генерирующий математические задачи, как объекты класса {@code TaskType}
 */
public class TasksGenerator {

    public TasksGenerator() {}

    /**
     *
     * @return список строк - названия существующих типов задач
     */
    public String[] getNamesOfTaskTypes(){
        ArrayList<String> names = new ArrayList<>();
        for(Types i : Types.values()) {
            names.add(i.name());
        }
        return names.toArray(new String[0]);
    }

    /**
     *
     * @param strType название типа задачи
     * @return задача, как как объект класса {@code TaskType}
     * @throws InvalidParameterException если задачи типа {@code strType} не существует
     */
    public TaskType getNewTaskByType(String strType) throws InvalidParameterException {

        Types type;
        try {
            type = Types.valueOf(strType);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(
                    "Неверный тип задачи: \"%s\"".formatted(strType)
            );
        }

        TaskType newTask = switch (type) {
            case RATIONAL_ARITHMETIC -> new RationalArithmeticTask();
            case LINEAR_EQUATION -> new LinearEquationTask();
        };

        return newTask;
    }
}
