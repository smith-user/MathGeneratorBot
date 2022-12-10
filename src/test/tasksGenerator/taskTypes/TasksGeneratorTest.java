package test.tasksGenerator.taskTypes;

import org.junit.jupiter.api.*;
import MathGeneratorBot.tasksGenerator.TasksGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class TasksGeneratorTest {
    static TasksGenerator generator;

    @BeforeAll
    static void init() {
        generator = TasksGenerator.instance();
    }

    @Test
    void testCreateTaskConditionOfCorrectClass() {
        assertEquals(generator, TasksGenerator.instance());
    }
}
