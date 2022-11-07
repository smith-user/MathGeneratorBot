package tasksGenerator;

import tasksGenerator.exceptions.TaskCreationException;
import tasksGenerator.mathClasses.MathFunctions;
import tasksGenerator.taskTypes.LinearEquationGenerators.LinearEquationManualGenerator;
import tasksGenerator.taskTypes.MathTaskGenerator;
import tasksGenerator.taskTypes.MathTaskTypes;
import tasksGenerator.taskTypes.RationalArithmeticGenerators.RationalArithmeticManualGenerator;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Класс генерирующий математические задачи, как объекты класса {@link MathTask}.
 * Метод {@code Instance} предоставляет доступ к экземпляру данного класса.
 */
public class TasksGenerator {

    private static TasksGenerator tasksGenerator;
    private static final Map<MathTaskTypes, MathTaskGenerator> generatorsMap = Map.of(
            MathTaskTypes.RATIONAL_ARITHMETIC, new RationalArithmeticManualGenerator(),
            MathTaskTypes.LINEAR_EQUATION, new LinearEquationManualGenerator()
    );

    private TasksGenerator() {}

    /**
     *
     * @return объект данного класса.
     */
    public static TasksGenerator instance() {
        if (tasksGenerator == null)
            tasksGenerator = new TasksGenerator();
        return tasksGenerator;
    }

    /**
     *
     * @return список строк - названия существующих типов задач
     */
    public String[] getNamesOfTaskTypes(){
        ArrayList<String> names = new ArrayList<>();
        for(MathTaskTypes i : MathTaskTypes.values()) {
            names.add(i.name());
        }
        return names.toArray(new String[0]);
    }

    /**
     *
     * @param type тип задачи, которая будет сгенерирована.
     * @return математическую задачу.
     * @throws TaskCreationException если класс-генератор для данного типа задач не определен или
     * при генерации задачи возникла ошибка.
     */
    public MathTask createTaskByType(MathTaskTypes type) throws TaskCreationException {
        if (generatorsMap.containsKey(type))
            return generatorsMap.get(type).createMathTask();
        else
            throw new TaskCreationException(
                    "Класс-генератор для данного типа задач \"%s\" не определен.".formatted(type.name())
            );
    }

    /**
     * Генерирует и возвращает одну математическую задачу любого типа, указанного в {@link MathTaskTypes}
     * @return математическую задачу.
     * @throws TaskCreationException если класс-генератор для данного типа задач не определен или
     * при генерации задачи возникла ошибка.
     */
    public MathTask createTask() throws TaskCreationException {
        int rand = MathFunctions.intRandomUnsigned(generatorsMap.size() - 1);
        return createTaskByType(MathTaskTypes.values()[rand]);
    }
}
