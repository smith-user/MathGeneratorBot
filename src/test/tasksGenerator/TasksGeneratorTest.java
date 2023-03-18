package test.tasksGenerator;

import mathGeneratorBot.tasksGenerator.*;
import mathGeneratorBot.tasksGenerator.TasksGenerator.MathTaskTypes;
import mathGeneratorBot.tasksGenerator.exceptions.TaskConditionException;
import mathGeneratorBot.tasksGenerator.exceptions.TaskCreationException;
import mathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;
import mathGeneratorBot.tasksGenerator.taskTypes.userTask.UserTaskCondition;
import mathGeneratorBot.tasksGenerator.taskTypes.userTask.UserTaskSolution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TasksGeneratorTest {

    static TasksGenerator tasksGenerator;

    /**
     * Иницилизация генератора задач {@link TasksGenerator}
     */
    @BeforeAll
    static void init() {
        tasksGenerator = TasksGenerator.instance();
    }

    /**
     * Создание и инициализация типа задачи {@link MathTaskTypes} с помощью {@link Mockito},
     * и определение его полей {@code generator} и {@code solver}.
     * @param condition значение, которое должен возвращать {@code generator.createTaskCondition};
     *                  если {@code null}, то при вызове метода выбрасывается исключение {@link TaskConditionException};
     * @param solution значение, которое должен возвращать {@code solver.createTaskSolution};
     *                 если {@code null}, то при вызове метода выбрасывается исключение {@link TaskSolutionException};
     * @return Mock объект типа задачи {@link MathTaskTypes}.
     */
    MathTaskTypes getMathTaskType(TaskCondition condition, TaskSolution solution) {
        MathTaskTypes taskType = Mockito.mock(MathTaskTypes.class);
        Mockito.doReturn("test").when(taskType).name();
        ProblemGenerator generator = Mockito.mock(ProblemGenerator.class);
        ProblemSolverImpl solver = Mockito.mock(ProblemSolverImpl.class);
        try {
            if (condition != null) {
                Mockito.doReturn(condition).when(generator).createTaskCondition();
            } else {
                Mockito.doThrow(TaskConditionException.class).when(generator).createTaskCondition();
            }

            if (solution != null) {
                Mockito.doReturn(solution).when(solver).createTaskSolutionForAbstractCondition(Mockito.any());
            } else {
                Mockito.doThrow(TaskSolutionException.class).when(solver).createTaskSolutionForAbstractCondition(Mockito.any());
            }
            Field generatorField = MathTaskTypes.class.getDeclaredField("generator");
            Field solverField = MathTaskTypes.class.getDeclaredField("solver");
            ReflectionUtils.makeAccessible(generatorField);
            ReflectionUtils.setField(generatorField, taskType, generator);
            ReflectionUtils.makeAccessible(solverField);
            ReflectionUtils.setField(solverField, taskType, solver);
        } catch (Exception exception) {
            fail();
        }
        return taskType;
    }

    /**
     * Решение математической задачи создается с экземплярами классов {@link TaskCondition} и {@link TaskSolution},
     * которые возвращают методы полей {@code generator} и {@code solver} экземпляра класса {@link MathTaskTypes},
     * переданного в метод {@code createTaskByType(MathTaskTypes)}.
     */
    @Test
    void testCreateTaskByType() {
        UserTaskCondition condition = new UserTaskCondition("expression");
        UserTaskSolution solution = new UserTaskSolution("result");
        MathTaskTypes taskType = getMathTaskType(condition, solution);
        assertDoesNotThrow(() -> {
            MathTask task = tasksGenerator.createTaskByType(taskType);
            assertEquals(condition, task.getCondition());
            assertEquals(solution, task.getSolution());
        });
    }

    /**
     * Если в методе {@code createTaskByType(MathTaskTypes)} происходит исключение {@link TaskConditionException},
     * то выбрасывается исключение {@link TaskCreationException}.
     */
    @Test
    void testCreateTaskByTypeCatchGeneratorException() {
        UserTaskSolution solution = new UserTaskSolution("result");
        MathTaskTypes taskType = getMathTaskType(null, solution);
        assertThrows(TaskCreationException.class, () -> {
            tasksGenerator.createTaskByType(taskType);
        });
    }

    /**
     * Если в методе {@code createTaskByType(MathTaskTypes)} происходит исключение {@link TaskSolutionException},
     * то выбрасывается исключение {@link TaskCreationException}.
     */
    @Test
    void testCreateTaskByTypeCatchSolverException() {
        UserTaskCondition condition = new UserTaskCondition("expression");
        MathTaskTypes taskType = getMathTaskType(condition, null);
        assertThrows(TaskCreationException.class, () -> {
            tasksGenerator.createTaskByType(taskType);
        });
    }

    /**
     * При создании произвольной задачи должен вызываться метод {@code createTaskByType},
     * и возвращаться результат его вызова.
     */
    @Test
    void testCreateRandomTask() {
        TasksGenerator spyGenerator = Mockito.spy(tasksGenerator);
        MathTask mathTask = new MathTask(new UserTaskCondition(""), new UserTaskSolution(""));
        assertDoesNotThrow(() -> {
            Mockito.doReturn(mathTask).when(spyGenerator).createTaskByType(Mockito.any());
            assertEquals(mathTask, spyGenerator.createTask());
        });
    }

    /**
     * При создании произвольной задачи должен вызываться метод {@code createTaskByType};
     * если при вызове проиходит исключение, оно пробрасывается дальше.
     */
    @Test
    void testCreateRandomTaskWithException() {
        TasksGenerator spyGenerator = Mockito.spy(tasksGenerator);
        assertDoesNotThrow(() -> {
            Mockito.doThrow(new TaskCreationException("Test Exception")).when(spyGenerator).createTaskByType(Mockito.any());
        });
        assertThrows(TaskCreationException.class, spyGenerator::createTask, "Test Exception");
    }
}