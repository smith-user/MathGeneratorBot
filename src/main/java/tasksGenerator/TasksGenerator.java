package tasksGenerator;

import tasksGenerator.taskTypes.RationalArithmeticTask;
import tasksGenerator.taskTypes.LinearEquationTask;
import tasksGenerator.taskTypes.TaskType;
import tasksGenerator.taskTypes.TypesEnum;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

public class TasksGenerator {

    public TasksGenerator() {

    }

    public String[] getNamesOfTaskTypes(){
        ArrayList<String> names = new ArrayList<>();
        for(TypesEnum i : TypesEnum.values()) {
            names.add(i.name());
        }
        return names.toArray(new String[0]);
    }

    public TaskType getNewTaskByType(String strType) throws InvalidParameterException {

        TypesEnum type;
        try {
            type = TypesEnum.valueOf(strType);
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
