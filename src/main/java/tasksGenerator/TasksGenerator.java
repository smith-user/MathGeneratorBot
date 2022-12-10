package tasksGenerator;

import org.apache.logging.log4j.Level;
import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.exceptions.TaskCreationException;
import tasksGenerator.exceptions.TaskSolutionException;
import tasksGenerator.mathClasses.MathFunctions;
import tasksGenerator.taskTypes.LinearEquation.*;
import tasksGenerator.taskTypes.RationalArithmetic.*;
import tasksGenerator.taskTypes.UserTask.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Класс генерирующий математические задачи, типов указанных в {@link MathTaskTypes}, как объекты класса {@link MathTask}.
 * Метод {@code instance()} предоставляет доступ к экземпляру данного класса.
 */
final public class TasksGenerator {

    private static TasksGenerator tasksGenerator;

    private final UserTaskGenerator userTaskGenerator = new UserTaskGenerator();
    private final UserTaskSolver userTaskSolver = new UserTaskMathAPISolver();
    private static final Logger logger = LogManager.getLogger(TasksGenerator.class.getName());


    private TasksGenerator() {
        logger.traceEntry();
        logger.traceExit();
    }

    /**
     *
     * @return объект данного класса.
     */
    public static TasksGenerator instance() {
        logger.traceEntry("Получение экземпляра");
        if (tasksGenerator == null) {
            tasksGenerator = new TasksGenerator();
        }
        return logger.traceExit(tasksGenerator);
    }

    /**
     * Генерирует и возвращает одну математическую задачу типа {@code type}.
     * @param type тип задачи, которая будет сгенерирована.
     * @return математическую задачу.
     * @throws TaskCreationException если при генерации задачи возникла ошибка.
     */
    public MathTask createTaskByType(MathTaskTypes type) throws TaskCreationException {
        logger.traceEntry("type={}", type);
        try {
            TaskCondition taskCondition = type.generator.createTaskCondition();
            TaskSolution taskSolution = type.solver.createTaskSolutionForAbstractCondition(taskCondition);
            return logger.traceExit(new MathTask(taskCondition, taskSolution));
        } catch (TaskConditionException | TaskSolutionException e) {
            logger.catching(e);
            throw logger.throwing(new TaskCreationException(e));
        }
    }

    public MathTask createTask(String input) throws TaskCreationException {
        logger.traceEntry("input=\"{}\"", input);
        try {
            TaskCondition taskCondition = userTaskGenerator.createTaskCondition(input);
            TaskSolution taskSolution = userTaskSolver.createTaskSolutionForAbstractCondition(taskCondition);
            return logger.traceExit(new MathTask(taskCondition, taskSolution));
        } catch (TaskConditionException | TaskSolutionException e) {
            logger.catching(e);
            throw logger.throwing(new TaskCreationException(e));
        }
    }


    /**
     * Генерирует и возвращает одну математическую задачу любого типа, указанного в {@link MathTaskTypes}
     * @return математическую задачу.
     * @throws TaskCreationException если при генерации задачи возникла ошибка.
     */
    public MathTask createTask() throws TaskCreationException {
        logger.traceEntry();
        MathTaskTypes[] allTypes = MathTaskTypes.values();
        int rand = MathFunctions.intRandomUnsigned(allTypes.length);
        return logger.traceExit(createTaskByType(allTypes[rand]));
    }

    /**
     * Перечисление типов математических задач, которые способен сгенировать объект класса {@link TasksGenerator}.
     */
    public enum MathTaskTypes {
        LINEAR_EQUATION(new LinearEquationManualGenerator(), new LinearEquationWolframSolver()),
        RATIONAL_ARITHMETIC(new RationalArithmeticManualGenerator(), new RationalArithmeticWolframSolver());
        private final ProblemGenerator<? extends TaskCondition> generator;
        private final ProblemSolverImpl<? extends TaskSolution, ? extends TaskCondition> solver;

        MathTaskTypes(ProblemGenerator<? extends TaskCondition> generator,
                      ProblemSolverImpl<? extends TaskSolution, ? extends TaskCondition> solver) {
            this.generator = generator;
            this.solver = solver;
        }
    }
}
