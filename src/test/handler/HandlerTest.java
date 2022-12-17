package test.handler;

import MathGeneratorBot.handler.DefaultResponse;
import MathGeneratorBot.handler.HandlerState;
import MathGeneratorBot.handler.QueryHandler;
import MathGeneratorBot.handler.commands.HelpCommand;
import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.tasksGenerator.MathTask;
import MathGeneratorBot.tasksGenerator.TaskCondition;
import MathGeneratorBot.tasksGenerator.TaskSolution;
import MathGeneratorBot.tasksGenerator.TasksGenerator;
import MathGeneratorBot.tasksGenerator.exceptions.TaskCreationException;
import MathGeneratorBot.tasksGenerator.mathClasses.Fraction;
import MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation.LinearEquationTaskCondition;
import MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation.LinearEquationTaskSolution;
import MathGeneratorBot.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandlerTest {
    final int USER_ID = -1;
    QueryHandler handler;
    static JsonStorage storage;
    static TasksGenerator generator;
    static MathTask task;

    /**
     * Создаются макеты классов {@code JsonStorage} и {@code TaskGenerator}.
     * Также задается поведение метода {@code createTaskByType} для тестирования
     * команд обработчика, которые генерируют задачи или ответы к ним.
     */
    @BeforeAll
    static void init() {
        storage = Mockito.mock(JsonStorage.class);
        generator = Mockito.mock(TasksGenerator.class);

        TaskCondition taskCondition = new LinearEquationTaskCondition(new Fraction(5), new Fraction(1));
        TaskSolution taskSolution = new LinearEquationTaskSolution("solution steps", "result");
        task = new MathTask(taskCondition, taskSolution);


    }

    /**
     * Создание объекта обработчика перед каждым тестом.
     * Нужно для того чтобы состояние обработчика было одинаковым для всех тестов.
     */
    @BeforeEach
    void handlerInit() {
        handler = new QueryHandler(storage, generator);
        handler.getResponse("/start", USER_ID);
    }

    /**
     * Проверка овтета на неизвествую команду.
     */
    @Test
    void testWrongCommand() {
        assertEquals(handler.getResponse("wrong command", USER_ID), DefaultResponse.UNKNOWN_COMMAND);
    }

    /**
     * Проверка состояний обработчкиа при вызове команды /tasks.
     * При первом вызове состояние {@code HandlerState.COMMAND_WAITING} должно
     * поменяться на {@code HandlerState.TASK_TYPE_WAITING}, и после ввода
     * типа и количества задач обратно на {@code HandlerState.COMMAND_WAITING}
     */
    @Test
    void testHandlerStatesInTasksCommand() throws TaskCreationException {
        Mockito.when(generator.createTaskByType(TasksGenerator.MathTaskTypes.LINEAR_EQUATION))
                .thenReturn(task);

        assertEquals(handler.getState(USER_ID), HandlerState.COMMAND_WAITING);
        handler.getResponse("/tasks", USER_ID);
        assertEquals(handler.getState(USER_ID), HandlerState.TASK_TYPE_WAITING);
        handler.getResponse("уравнения 1", USER_ID);
        assertEquals(handler.getState(USER_ID), HandlerState.COMMAND_WAITING);

    }

    /**
     * Проверка состояний обработчкиа при вызове команды /answers.
     * С начала генерируются задачи.
     * При первом вызове состояние {@code HandlerState.COMMAND_WAITING} должно
     * поменяться на {@code HandlerState.ANSWERS_WAITING}, после ввода
     * ответов на {@code HandlerState.GIVE_ANSWER_FILE}, и потом после любой команды
     * обратно на {@code HandlerState.COMMAND_WAITING}
     */
    @Test
    void testHandlerStatesInAnswersCommand() throws TaskCreationException {
        Mockito.when(generator.createTaskByType(TasksGenerator.MathTaskTypes.LINEAR_EQUATION))
                .thenReturn(task);

        handler.getResponse("/tasks", USER_ID);
        handler.getResponse("уравнения 1", USER_ID);

        assertEquals(handler.getState(USER_ID), HandlerState.COMMAND_WAITING);
        handler.getResponse("/answers", USER_ID);
        assertEquals(handler.getState(USER_ID), HandlerState.ANSWER_WAITING);
        handler.getResponse("userAnswer", USER_ID);
        assertEquals(handler.getState(USER_ID), HandlerState.GIVE_ANSWER_FILE);
        handler.getResponse("any command", USER_ID);
        assertEquals(handler.getState(USER_ID), HandlerState.COMMAND_WAITING);
    }

    /**
     * Проверка команды /help
     */
    @Test
    void testHelpCommand() {
        String testCommand = "/help";
        HelpCommand helpCommand = new HelpCommand();
        assertEquals(handler.getResponse(testCommand, USER_ID), helpCommand.execute(USER_ID, null));
    }

    /**
     * Проверка команды /stat
     * С начала создается объект пользователя и
     * задается поведение метода {@code getUserById}.
     */
    @Test
    void testStatCommand(){
        String testCommand = "/stat";
        User user = new User(-1);
        user.addGeneratedTasks(1);
        Mockito.when(storage.getUserById(USER_ID)).thenReturn(user);
        assertEquals(handler.getResponse(testCommand, USER_ID),
                "Всего вы сгенерировали *%d* задач и решили из них *%d* задач".formatted(
                user.getGeneratedTasks(), user.getSolvedTasks()));
    }

    /**
     * Проверка команды /tasks.
     */
    @Test
    void testTasksCommand() throws TaskCreationException {
        String testCommand = "/tasks";
        String testInput = "уравнения 1";
        Mockito.when(generator.createTaskByType(TasksGenerator.MathTaskTypes.LINEAR_EQUATION))
                .thenReturn(task);

        assertEquals(handler.getResponse(testCommand, USER_ID),
                     DefaultResponse.GET_TASK_TYPE);
        assertEquals(handler.getResponse(testInput, USER_ID).split(":")[0],
                "Найдите все корни или убедитесь, что их нет");
    }

    /**
     * Првоерка команды /tasks при неверном вводе типа задач.
     */
    @Test
    void testWrongTasksType() {
        String testCommand = "/tasks";
        String testInput = "wrong input";
        assertEquals(handler.getResponse(testCommand, USER_ID),
                DefaultResponse.GET_TASK_TYPE);
        assertEquals(handler.getResponse(testInput, USER_ID),
                DefaultResponse.ILLEGAL_TYPE_OF_TASKS);
    }

    /**
     * Проверка команды /tasks при ошибке генерации задач.
     */
    @Test
    void testErrorTaskGeneration() throws TaskCreationException {
        String testCommand = "/tasks";
        String testInput = "арифметика 1";
        Mockito.when(generator.createTaskByType(TasksGenerator.MathTaskTypes.RATIONAL_ARITHMETIC))
                .thenThrow(TaskCreationException.class);
        handler.getResponse(testCommand, USER_ID);
        assertEquals(handler.getResponse(testInput, USER_ID), DefaultResponse.TASK_GENERATE_FAIL);
    }

    /**
     * Проверка команды /answers.
     * C начада генерирются задачи командной /tasks.
     */
    @Test
    void testAnswersCommand() throws TaskCreationException {
        String testCommand = "/tasks";
        String testInput = "уравнения 1";
        Mockito.when(generator.createTaskByType(TasksGenerator.MathTaskTypes.LINEAR_EQUATION))
                .thenReturn(task);

        handler.getResponse(testCommand, USER_ID);
        handler.getResponse(testInput, USER_ID);
        assertEquals(handler.getResponse(testCommand, USER_ID),
                DefaultResponse.GET_ANSWERS);
        assertEquals(handler.getResponse("-", USER_ID).split("\n")[0],
                "Решены 0 из 1 задач");
    }

    /**
     * Проверка команды /answers без заранее сгенерированных задач
     */
    @Test
    void testWrongAnswersCommand() {
        String testCommand = "/answers";
        assertEquals(handler.getResponse(testCommand, USER_ID),
                DefaultResponse.NO_TASKS_GENERATED);
    }

    /**
     * Проверка команды /solve.
     * С начала создается задача и задаается поведение метода
     * {@code createTask}, вызываемого командой /solve.
     */
    @Test
    void testSolveUserInputTask() throws TaskCreationException {
        String testCommand = "/solve";
        String testInput = "test string";
        Mockito.when(generator.createTask(testInput)).thenReturn(task);

        handler.getResponse(testCommand, USER_ID);
        assertEquals(handler.getResponse(testInput, USER_ID), "`%s`".formatted(task.getSolution()));
    }

    /**
     * Проверка команды /solve при вводе некоректной задачи.
     */
    @Test
    void testSolveUserInputWrongTask() throws TaskCreationException {
        String testCommand = "/solve";
        String testInput = "wrong test string";
        Mockito.when(generator.createTask(testInput)).thenThrow(TaskCreationException.class);

        handler.getResponse(testCommand, USER_ID);
        assertEquals(handler.getResponse(testInput, USER_ID), DefaultResponse.TASK_SOLVE_FAIL);
    }
}
