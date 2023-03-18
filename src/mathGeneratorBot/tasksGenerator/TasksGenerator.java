package mathGeneratorBot.tasksGenerator;

import mathGeneratorBot.logMessage.GeneratorMessage;
import mathGeneratorBot.tasksGenerator.exceptions.TaskConditionException;
import mathGeneratorBot.tasksGenerator.exceptions.TaskCreationException;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;
import mathGeneratorBot.tasksGenerator.mathClasses.MathFunctions;
import mathGeneratorBot.tasksGenerator.taskTypes.linearEquation.LinearEquationManualGenerator;
import mathGeneratorBot.tasksGenerator.taskTypes.linearEquation.LinearEquationWolframSolver;
import mathGeneratorBot.tasksGenerator.taskTypes.rationalArithmetic.RationalArithmeticManualGenerator;
import mathGeneratorBot.tasksGenerator.taskTypes.rationalArithmetic.RationalArithmeticWolframSolver;
import mathGeneratorBot.tasksGenerator.taskTypes.userTask.UserTaskAPISolver;
import mathGeneratorBot.tasksGenerator.taskTypes.userTask.UserTaskGenerator;
import mathGeneratorBot.tasksGenerator.taskTypes.userTask.UserTaskSolver;

import static mathGeneratorBot.logMessage.GeneratorMessage.MessageType.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Класс генерирующий математические задачи, типов указанных в {@link MathTaskTypes}, как объекты класса {@link MathTask}.
 * Метод {@code instance()} предоставляет доступ к экземпляру данного класса.
 */
final public class TasksGenerator {

    private static final Logger logger = LogManager.getLogger(TasksGenerator.class.getName());
    private static final TasksGenerator tasksGenerator = new TasksGenerator();
    private static final UserTaskGenerator userTaskGenerator = new UserTaskGenerator();
    private static final UserTaskSolver userTaskSolver = new UserTaskAPISolver();

    private TasksGenerator() {}

    /**
     *
     * @return объект данного класса.
     */
    public static TasksGenerator instance() {
        return tasksGenerator;
    }

    /**
     * Генерирует и возвращает одну математическую задачу типа {@code type}.
     * @param type тип задачи, которая будет сгенерирована.
     * @return математическую задачу.
     * @throws TaskCreationException если при генерации задачи возникла ошибка.
     */
    public MathTask createTaskByType(MathTaskTypes type) throws TaskCreationException {
        logger.traceEntry("type={}", type);
        logger.info(new GeneratorMessage(CREATE_TASK, Map.of("type", type.name())).getFormattedMessage());
        try {
            TaskCondition taskCondition = type.generator.createTaskCondition();
            TaskSolution taskSolution = type.solver.createTaskSolutionForAbstractCondition(taskCondition);
            return logger.traceExit(new MathTask(taskCondition, taskSolution));
        } catch (TaskConditionException | TaskSolutionException e) {
            logger.catching(Level.DEBUG, e);
            throw logger.throwing(Level.WARN, new GeneratorMessage(CREATION_ERROR, Map.of("type", type.name())).getThrowable(e));
        }
    }

    public MathTask createTask(String input) throws TaskCreationException {
        logger.traceEntry("input=\"{}\"", input);
        logger.info(new GeneratorMessage(SOLVE_TASK, Map.of("input", input)).getFormattedMessage());
        TaskCondition taskCondition = userTaskGenerator.createTaskCondition(input);
        try {
            TaskSolution taskSolution = userTaskSolver.createTaskSolutionForAbstractCondition(taskCondition);
            return logger.traceExit(new MathTask(taskCondition, taskSolution));
        } catch (TaskSolutionException e) {
            logger.catching(Level.DEBUG, e);
            throw logger.throwing(Level.WARN, new GeneratorMessage(CREATION_ERROR, Map.of("input", input)).getThrowable(e));
        }
    }

    /**
     * Генерирует и возвращает одну математическую задачу любого типа, указанного в {@link MathTaskTypes}
     * @return математическую задачу.
     * @throws TaskCreationException если при генерации задачи возникла ошибка.
     */
    public MathTask createTask() throws TaskCreationException {
        logger.traceEntry();
        logger.info(new GeneratorMessage(CREATE_TASK, null).getFormattedMessage());
        MathTaskTypes[] allTypes = MathTaskTypes.values();
        int rand = MathFunctions.intRandomUnsigned(100) % allTypes.length;
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
