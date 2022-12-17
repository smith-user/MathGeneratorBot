package test.tasksGenerator;

import static MathGeneratorBot.tasksGenerator.TasksGenerator.MathTaskTypes.*;

import MathGeneratorBot.tasksGenerator.MathTask;
import MathGeneratorBot.tasksGenerator.TaskCondition;
import MathGeneratorBot.tasksGenerator.TasksGenerator;
import MathGeneratorBot.tasksGenerator.TasksGenerator.MathTaskTypes;
import MathGeneratorBot.tasksGenerator.exceptions.TaskCreationException;
import MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation.LinearEquationTaskCondition;
import MathGeneratorBot.tasksGenerator.taskTypes.UserTask.UserTaskCondition;
import MathGeneratorBot.tasksGenerator.taskTypes.UserTask.UserTaskSolution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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