package tasksGenerator;

import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.exceptions.TaskCreationException;
import tasksGenerator.exceptions.TaskSolutionException;
import tasksGenerator.mathClasses.MathFunctions;
import tasksGenerator.taskTypes.LinearEquation.*;
import tasksGenerator.taskTypes.RationalArithmetic.*;

/**
 * Класс генерирующий математические задачи, типов указанных в {@link MathTaskTypes}, как объекты класса {@link MathTask}.
 * Метод {@code instance()} предоставляет доступ к экземпляру данного класса.
 */
final public class TasksGenerator {

    private static TasksGenerator tasksGenerator;

    private static final LinearEquationGenerator linearEquationGenerator = new LinearEquationManualGenerator();
    private static final LinearEquationSolver linearEquationSolver = new LinearEquationWolframSolver();

    private static final RationalArithmeticGenerator rationalArithmeticGenerator = new RationalArithmeticManualGenerator();
    private static final RationalArithmeticSolver rationalArithmeticSolver = new RationalArithmeticWolframSolver();


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
     * Генерирует и возвращает одну математическую задачу типа {@code type}.
     * @param type тип задачи, которая будет сгенерирована.
     * @return математическую задачу.
     * @throws TaskCreationException если при генерации задачи возникла ошибка.
     */
    public MathTask createTaskByType(MathTaskTypes type) throws TaskCreationException {
        TaskCondition taskCondition = null;
        TaskSolution taskSolution = null;
        switch (type) {
            case LINEAR_EQUATION -> {
                try {
                    LinearEquationTaskCondition condition = linearEquationGenerator.createTaskCondition();
                    taskSolution = linearEquationSolver.createTaskSolution(condition);
                    taskCondition = condition;
                } catch (TaskSolutionException e) {
                    throw new TaskCreationException(e);
                }
            }
            case RATIONAL_ARITHMETIC -> {
                try {
                    RationalArithmeticTaskCondition condition = rationalArithmeticGenerator.createTaskCondition();
                    taskSolution = rationalArithmeticSolver.createTaskSolution(condition);
                    taskCondition = condition;
                } catch (TaskConditionException | TaskSolutionException e) {
                    throw new TaskCreationException(e);
                }
            }
        }
        return new MathTask(taskCondition, taskSolution);
    }


    /**
     * Генерирует и возвращает одну математическую задачу любого типа, указанного в {@link MathTaskTypes}
     * @return математическую задачу.
     * @throws TaskCreationException если при генерации задачи возникла ошибка.
     */
    public MathTask createTask() throws TaskCreationException {
        MathTaskTypes[] allTypes = MathTaskTypes.values();
        int rand = MathFunctions.intRandomUnsigned(allTypes.length - 1);
        return createTaskByType(allTypes[rand]);
    }

    /**
     * Перечисление типов математических задач, которые способен сгенировать объект класса {@link TasksGenerator}.
     */
    public static enum MathTaskTypes {
        LINEAR_EQUATION,
        RATIONAL_ARITHMETIC
    }
}
